package future;

import enumm.ListenerTypeEnum;
import listener.ListenerEvent;
import start.NtEvent;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @author saijie.gao
 * @date 2021/12/13
 */
public class NtFuture implements ListenerEvent {


    private NtEvent connectEvent;
    private NtEvent disconnectEvent;
    private SocketChannel socketChannel;

    @Override
    public void listener(ListenerTypeEnum listenerTypeEnum) {
        if (ListenerTypeEnum.CONNECTED == listenerTypeEnum) {
            connectEvent.event();
        }
        if (ListenerTypeEnum.DISCONNECTED == listenerTypeEnum) {
            disconnectEvent.event();
        }
    }

    public String getIp() {
        try {
            return this.socketChannel.getRemoteAddress().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 该方法需要被唤醒，当连接事件发生
    public void connectListener(NtEvent ntEvent) {
        connectEvent = ntEvent;
    }

    // 该方法需要被唤醒，当连接断开事件发生
    public void disConnectListener(NtEvent ntEvent) {
        disconnectEvent = ntEvent;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }
}
