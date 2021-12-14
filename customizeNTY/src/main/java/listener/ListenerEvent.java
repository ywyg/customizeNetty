package listener;

import enumm.ListenerTypeEnum;

/**
 * @author saijie.gao
 * @date 2021/12/14
 */
public interface ListenerEvent {

    /**
     * 被唤醒时使用的类
     * @param listenerTypeEnum
     */
    void listener(ListenerTypeEnum listenerTypeEnum);

}
