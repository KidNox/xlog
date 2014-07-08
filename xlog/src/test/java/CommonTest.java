import kidnox.xlog.XLog;
import kidnox.xlog.XLogFactory;
import org.junit.Test;

public class CommonTest {

    @Test public void commonTest() {
        XLog xLog = XLogFactory.getSimpleFinestLogger(getClass());

        xLog.debug("debug");
        xLog.debug("debug, args = %s, %s, %s", "string", new Object(), null);
        xLog.debug(throwAndCatch(), "debug");

        xLog.warn("warning");
        xLog.error(throwAndCatch(), "warning args = %s, %s, %s", "string", new Object(), null);

        xLog.wtf(throwAndCatch());

        xLog.trace("trace");
        xLog.debug(new Object());
        xLog.info(new Object(), null, "debug");

    }

    Throwable throwAndCatch() {
        try {
            throw new RuntimeException();
        } catch (Throwable t){
            return t;
        }
    }


}
