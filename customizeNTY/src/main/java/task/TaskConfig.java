package task;

import channel.NtChannel;
import handle.NtHandler;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author saijie.gao
 * @date 2021/12/14
 */
public class TaskConfig {

    /**
     * 负责处理连接的线程池
     */
    private ScheduledThreadPoolExecutor connect;

    /**
     * 负责处理读写的线程池
     */
    private ScheduledThreadPoolExecutor read;


    /**
     * 选择通讯类型
     */
    private NtChannel ntChannel;


    /**
     * 方法处理类
     */
    private NtHandler ntHandler;

    public void setConnect(ScheduledThreadPoolExecutor connect) {
        this.connect = connect;
    }

    public void setRead(ScheduledThreadPoolExecutor read) {
        this.read = read;
    }

    public void setChannel(NtChannel channel) {
        this.ntChannel = channel;
    }

    public void setNtHandler(NtHandler ntHandler) {
        this.ntHandler = ntHandler;
    }

    public ScheduledThreadPoolExecutor getConnect() {
        return connect;
    }

    public ScheduledThreadPoolExecutor getRead() {
        return read;
    }

    public NtChannel getNtChannel() {
        return ntChannel;
    }

    public NtHandler getNtHandler() {
        return ntHandler;
    }
}
