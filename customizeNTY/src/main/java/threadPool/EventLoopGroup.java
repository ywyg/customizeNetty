package threadPool;

import java.util.concurrent.*;

/**
 * @author saijie.gao
 * @date 2021/12/3
 */
public class EventLoopGroup {

    private ScheduledThreadPoolExecutor threadPoolExecutor;

    public EventLoopGroup(int coreThread) {
        threadPoolExecutor = new ScheduledThreadPoolExecutor(coreThread);
    }

    public ScheduledThreadPoolExecutor getThreadPoolExecutor(){
        return threadPoolExecutor;
    }

}
