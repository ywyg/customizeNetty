package start;

import channel.NIOChannel;
import future.NtFuture;
import guide.ServerBootStrap;
import handle.MyHandler;
import threadPool.EventLoopGroup;

/**
 * @author saijie.gao
 * @date 2021/12/3
 */
public class Start {

    public static void main(String[] args) {

        //启动引导类
        ServerBootStrap serverBootStrap = new ServerBootStrap();
        //添加连接线程池和交互线程池
        EventLoopGroup connectPool = new EventLoopGroup(1);
        EventLoopGroup readPool = new EventLoopGroup(2);
        serverBootStrap.group(connectPool, readPool)
                //选择IO模型
                .channel(NIOChannel.class)
                //选择handle
                .handle(new MyHandler());
        //开始监听
        NtFuture sync = serverBootStrap.start(9090).sync();
        sync.connectListener(new NtEvent() {
            @Override
            public void event() {
                System.out.println(String.format("客户端连接建立完成"));
            }
        });

        sync.disConnectListener(new NtEvent() {
            @Override
            public void event() {
                System.out.println(String.format("客户端连接断开"));

            }
        });
    }

}
