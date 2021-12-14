package guide;

import channel.BIOChannel;
import channel.NIOChannel;
import channel.NtChannel;
import future.NtFuture;
import handle.NtHandler;
import task.Task;
import task.TaskConfig;
import threadPool.EventLoopGroup;

/**
 * @author saijie.gao
 * @date 2021/12/3
 * <p>
 * MyNetty启动引导类
 */

public class ServerBootStrap {

    private TaskConfig taskConfig = new TaskConfig();
    Task task;
    /**
     * 设置执行任务的线程池
     *
     * @param connect
     * @param read
     * @return
     */
    public ServerBootStrap group(EventLoopGroup connect, EventLoopGroup read) {
        taskConfig.setConnect(connect.getThreadPoolExecutor());
        taskConfig.setRead(read.getThreadPoolExecutor());
        return this;
    }

    /**
     * 选择通讯类型
     *
     * @param channelClass
     * @return
     */
    public ServerBootStrap channel(Class channelClass) {
        NtChannel ntChannel = null;
        if (NIOChannel.class.isAssignableFrom(channelClass)) {
            ntChannel = new NIOChannel();
        }else{
            ntChannel = new BIOChannel();
        }
        taskConfig.setChannel(ntChannel);
        return this;
    }

    /**
     * 设置处理方法
     *
     * @param handler
     * @return
     */
    public ServerBootStrap handle(NtHandler handler) {
        taskConfig.setNtHandler(handler);
        return this;
    }


    /**
     * 服务启动
     *
     * @param port
     * @return
     */
    public ServerBootStrap start(int port) {
        task = new Task(port,taskConfig);
        return this;
    }

    /**
     * 同步启动
     *
     * @return
     */
    public NtFuture sync() {
        return task.sync();
    }

}
