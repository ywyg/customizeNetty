package channel;

import enumm.ListenerTypeEnum;
import handle.NtContext;
import handle.NtHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author saijie.gao
 * @date 2021/12/3
 */
public class NIOChannel extends NtChannel {

    private static Selector selector;
    private int port;
    private NtHandler ntHandler;

    static {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setNtHandler(NtHandler ntHandler) {
        this.ntHandler = ntHandler;
    }

    public void init(){
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.socket().bind(new InetSocketAddress(port), 1024);
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Server is start in port :" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Runnable connectThreadPool() {
        return () -> {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                            SocketChannel socket = channel.accept();
                            socket.configureBlocking(false);
                            socket.register(selector, SelectionKey.OP_READ);
                            notifyListener(ListenerTypeEnum.CONNECTED);
                            iterator.remove();
                        }
                    } else {
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Override
    public Runnable readThreadPool() {
        return () -> {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    if (key.isValid()) {
                        if (key.isReadable()) {
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int line = channel.read(byteBuffer);
                            if (line > 0) {
                                byteBuffer.flip();
                                byte[] bytes = new byte[byteBuffer.remaining()];
                                byteBuffer.get(bytes);
                                String body = new String(bytes, "UTF-8");
                                NtContext ntContext = new NtContext(channel);
                                ntHandler.read(ntContext, body);
                            }
                            iterator.remove();
                        }
                    } else {
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

    }
}
