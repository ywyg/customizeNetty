package customize_handler;

import handle.NtContext;
import handle.NtHandler;

/**
 * @author saijie.gao
 * @date 2021/12/31
 */
public class Bhandler implements NtHandler {
    @Override
    public void read(NtContext ntContext, Object msg) {
        ntContext.writeAndFlush(String.format("I'm BHandler , receive msg is: %s", msg.toString()));
    }
}
