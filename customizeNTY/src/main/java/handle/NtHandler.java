package handle;

/**
 * @author saijie.gao
 * @date 2021/12/13
 */
public interface NtHandler {

    /**
     *  接受数据并且处理
     */
    void read(NtContext ntContext,Object msg);

}
