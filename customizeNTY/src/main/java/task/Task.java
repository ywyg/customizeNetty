package task;

import channel.NIOChannel;
import future.NtFuture;

import java.util.concurrent.TimeUnit;

/**
 * @author saijie.gao
 * @date 2021/12/14
 */
public class Task {

    /**
     * 服务监听端口
     */
    private int port;

    /**
     * task配置
     */
    private TaskConfig taskConfig;

    public Task(int port, TaskConfig taskConfig) {
        this.port = port;
        this.taskConfig = taskConfig;
    }

    /**
     * 同步启动
     */
    public NtFuture sync() {
        NtFuture ntFuture = new NtFuture();
        this.taskConfig.getNtChannel().addObserver(ntFuture);
        Runnable connect = this.taskConfig.getNtChannel().connectThreadPool();
        Runnable read = this.taskConfig.getNtChannel().readThreadPool();

        NIOChannel nioChannel = (NIOChannel) this.taskConfig.getNtChannel();
        nioChannel.setNtHandler(this.taskConfig.getNtHandler());
        nioChannel.setPort(port);
        nioChannel.init();
        //将循环任务放到线程池
        this.taskConfig.getConnect().scheduleAtFixedRate(connect, 3000, 300, TimeUnit.MILLISECONDS);
        this.taskConfig.getConnect().scheduleAtFixedRate(read, 3000, 300, TimeUnit.MILLISECONDS);
        return ntFuture;
    }

}
