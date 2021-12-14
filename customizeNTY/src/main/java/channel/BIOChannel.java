package channel;

/**
 * @author saijie.gao
 * @date 2021/12/14
 */
public class BIOChannel extends NtChannel{


    @Override
    public Runnable connectThreadPool() {
        return null;
    }

    @Override
    public Runnable readThreadPool() {
        return null;
    }
}
