package kidnox.xlog;

public interface LogAdapter {

    public enum Level {
        TRACE, DEBUG, INFO, WARNING, ERROR, WTF
    }

    void log(Level priority, String tag, String message);

    void log(Level priority, String tag, String message, Throwable t);

    String format(String message, Object... args);

    Level getLogLevel();

}
