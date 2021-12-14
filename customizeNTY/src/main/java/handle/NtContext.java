package handle;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * @author saijie.gao
 * @date 2021/12/13
 */
public class NtContext  {

    private SocketChannel socketChannel;

    public NtContext(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }


    public void writeAndFlush(String send){
        ByteBuffer write = ByteBuffer.allocate(send.getBytes().length);
        write.put(send.getBytes());
        write.flip();
        try {
            socketChannel.write(write);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
