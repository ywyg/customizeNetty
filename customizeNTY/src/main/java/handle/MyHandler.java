package handle;

/**
 * @author saijie.gao
 * @date 2021/12/14
 */
public class MyHandler implements NtHandler{
    @Override
    public void read(NtContext ntContext, Object msg) {
        ntContext.writeAndFlush("Hello " + msg.toString());
    }
}
