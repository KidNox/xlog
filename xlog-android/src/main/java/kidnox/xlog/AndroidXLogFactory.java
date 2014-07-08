package kidnox.xlog;

import android.util.Log;

public class AndroidXLogFactory extends XLogFactory {

    public static AndroidXLogFactory getDebugAndroidLogFactory() {
        return new AndroidXLogFactory(LogAdapter.Level.TRACE);
    }

    public static XLog getDebugAndroidLogger(String tag) {
        return getLogger(new AndroidLog(LogAdapter.Level.TRACE), tag);
    }

    public static XLog getDebugAndroidLogger() {
        return getDebugAndroidLogger("");
    }

    public AndroidXLogFactory(LogAdapter logger) {
        super(new AndroidLogWrapped(logger));
    }

    public AndroidXLogFactory(LogAdapter.Level level) {
        super(new AndroidLog(level));
    }

    public static class AndroidLog implements LogAdapter {
        final Level level;

        protected AndroidLog(Level level) {
            this.level = level;
        }

        @Override public void log(Level priority, String tag, String mes) {
            switch (priority){
                case TRACE:
                    Log.v(tag, mes);
                    break;
                case DEBUG:
                    Log.d(tag, mes);
                    break;
                case INFO:
                    Log.i(tag, mes);
                    break;
                case WARNING:
                    Log.w(tag, mes);
                    break;
                case ERROR:
                    Log.e(tag, mes);
                    break;
                case WTF:
                    Log.wtf(tag, mes);
                    break;
            }
        }

        @Override public void log(Level priority, String tag, String mes, Throwable t) {
            switch (priority){
                case TRACE:
                    Log.v(tag, mes);
                    break;
                case DEBUG:
                    Log.d(tag, mes, t);
                    break;
                case INFO:
                    Log.i(tag, mes, t);
                    break;
                case WARNING:
                    if(mes == null)    Log.w(tag, t);
                    else               Log.w(tag, mes, t);
                    break;
                case ERROR:
                    if(mes == null)    Log.e(tag, "", t);
                    else               Log.e(tag, mes, t);
                    break;
                case WTF:
                    if(mes == null)    Log.wtf(tag, t);
                    else               Log.wtf(tag, mes, t);
                    break;
            }
        }

        @Override public String format(String message, Object... args) {
            return String.format(message, args);
        }

        @Override public Level getLogLevel() {
            return level;
        }
    }

    private static class AndroidLogWrapped extends AndroidLog {
        final LogAdapter logger;

        AndroidLogWrapped(LogAdapter logger) {
            super(logger.getLogLevel());
            this.logger = logger;
        }

        @Override public void log(Level priority, String tag, String mes, Throwable t) {
            super.log(priority, tag, mes, t);
            logger.log(priority, tag, mes, t);
        }

        @Override public void log(Level priority, String tag, String mes) {
            super.log(priority, tag, mes);
            logger.log(priority, tag, mes);
        }

        @Override public String format(String message, Object... args) {
            return logger.format(message, args);
        }
    }

}
