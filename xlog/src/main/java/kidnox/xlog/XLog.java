package kidnox.xlog;


public interface XLog {

    public void trace(Object message);
    public void trace(Object... message);
    public void trace(String message, Object... args);

    public void debug(Object message);
    public void debug(Object... message);
    public void debug(String message, Object... args);
    public void debug(Throwable t, String message);
    public void debug(Throwable t, String message, Object... args);

    public void info(Object message);
    public void info(Object... message);
    public void info(String message, Object... args);
    public void info(Throwable t, String message);
    public void info(Throwable t, String message, Object... args);

    public void warn(String message);
    public void warn(String message, Object... args);
    public void warn(Throwable t);
    public void warn(Throwable t, String message);
    public void warn(Throwable t, String message, Object... args);

    public void error(Throwable t);
    public void error(Throwable t, String message);
    public void error(Throwable t, String message, Object... args);

    public void wtf(String message);
    public void wtf(String message, Object... args);
    public void wtf(Throwable t);
    public void wtf(Throwable t, String message);
    public void wtf(Throwable t, String message, Object... args);

}
