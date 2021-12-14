package threadPool;

import enumm.TaskTypeEnum;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author saijie.gao
 * @date 2021/12/3
 */
public class TaskAndPool<T> {


    private Selector selector;
    private ThreadPoolExecutor threadPoolExecutor;

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    //处理连接
    private Runnable connable() throws IOException {
        return new Runnable() {
            @Override
            public void run()  {
                try {
                    selector.select(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key;
                if(iterator.hasNext()){
                    key = iterator.next();
                    if(key.isValid()){
                        iterator.remove();
                        if(key.isAcceptable()){
                            ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                            SocketChannel accept = null;
                            try {
                                accept = channel.accept();
                                accept.configureBlocking(false);
                                accept.register(selector,SelectionKey.OP_READ);
                                System.out.println("connected" + accept.getRemoteAddress());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        };
    }

    private Runnable readable(){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    selector.select(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key;
                if(iterator.hasNext()){
                    key = iterator.next();
                    if(key.isValid()){
                        if(key.isReadable()){
                            iterator.remove();
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                            int line = 0;
                            try {
                                line = channel.read(byteBuffer);
                                if (line > 0) {
                                    byteBuffer.flip();
                                    byte[] bytes = new byte[byteBuffer.remaining()];
                                    byteBuffer.get(bytes);
                                    String body = new String(bytes, "UTF-8");
                                    System.out.println("The server receive order : " + body);
                                    String currentTime = new Date(System.currentTimeMillis()).toString();
                                    ByteBuffer write = ByteBuffer.allocate(currentTime.getBytes().length);
                                    write.put(currentTime.getBytes());
                                    write.flip();
                                    channel.write(write);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        };
    }

    public Future<T> exec(TaskTypeEnum taskTypeEnum) throws IOException {
        if(TaskTypeEnum.connect == taskTypeEnum){
            threadPoolExecutor.execute(connable());
        }else if(TaskTypeEnum.read == taskTypeEnum){
            threadPoolExecutor.execute(readable());
        }
        return null;
    }

}
