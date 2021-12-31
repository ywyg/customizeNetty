package channel;

import enumm.ListenerTypeEnum;
import listener.ListenerEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author saijie.gao
 * @date 2021/12/14
 */
public abstract class NtChannel {

    protected List<ListenerEvent> list = new ArrayList<>();

    public void addObserver(ListenerEvent listenerEvent) {
        list.add(listenerEvent);
    }

    public abstract Runnable connectThreadPool();

    public abstract Runnable readThreadPool();

    protected void notifyListener(ListenerTypeEnum listenerTypeEnum) {
        list.forEach(li -> li.listener(listenerTypeEnum));
    }

}
