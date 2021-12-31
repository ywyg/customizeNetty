package start;

import channel.NIOChannel;
import customize_handler.Dhandler;
import future.NtFuture;
import guide.ServerBootStrap;
import threadPool.EventLoopGroup;

/**
 * @author saijie.gao
 * @date 2021/12/3
 */
public class Start {

    private final int port;

    private Start(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new Start(9000).start();
    }

    private void start() {
        //启动引导类
        ServerBootStrap serverBootStrap = new ServerBootStrap();
        //添加连接线程池和交互线程池
        EventLoopGroup connectPool = new EventLoopGroup(1);
        EventLoopGroup readPool = new EventLoopGroup(2);

        serverBootStrap.group(connectPool, readPool)
                //选择IO模型
                .channel(NIOChannel.class)
                //选择handle
                .handle(new Dhandler());

        //开始监听
        NtFuture sync = serverBootStrap.bind(port).sync();
        sync.connectListener(new NtEvent() {
            @Override
            public void event() {
                System.out.println(String.format("客户端连接建立完成"));
            }
        });
    }

}
