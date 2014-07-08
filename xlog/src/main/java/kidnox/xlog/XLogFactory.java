package kidnox.xlog;

import java.io.PrintStream;

import static kidnox.xlog.LogAdapter.Level.*;

public class XLogFactory {

    public static XLog getSimpleLogger(LogAdapter.Level level, String tag) {
        return getLogger(new SimpleConsoleLogAdapter(level), tag);
    }

    public static XLog getSimpleFinestLogger(String tag) {
        return getLogger(new SimpleConsoleLogAdapter(TRACE), tag);
    }

    public static XLog getSimpleFinestLogger(Class tag) {
        return getLogger(new SimpleConsoleLogAdapter(TRACE), tag.getSimpleName());
    }

    public static XLog getSimpleFinestLogger() {
        return getLogger(new SimpleConsoleLogAdapter(TRACE), "");
    }

    public static XLog getLogger(LogAdapter logger, String tag) {
        return new XLogImpl(logger, tag);
    }

    public static XLogFactory getSimpleLogFactory(LogAdapter.Level level) {
        return new XLogFactory(new SimpleConsoleLogAdapter(level));
    }

    public static XLogFactory getSimpleFinestLogFactory() {
        return new XLogFactory(new SimpleConsoleLogAdapter(LogAdapter.Level.TRACE));
    }

    final LogAdapter logger;

    public XLogFactory(LogAdapter logger) {
        this.logger = logger;
    }

    public XLog get() {
        return new XLogImpl(logger, "");
    }

    public XLog get(Class c) {
        return new XLogImpl(logger, c.getName());
    }

    public XLog get(String tag) {
        return new XLogImpl(logger, tag);
    }


    private static class XLogImpl implements XLog {

        private final LogAdapter logger;
        private final String tag;
        private final int level;

        XLogImpl(LogAdapter logger, String tag) {
            this.logger = logger;
            this.tag = tag;
            this.level = logger.getLogLevel().ordinal();
        }

        @Override public void trace(String message) {
            log(TRACE, message);
        }

        @Override public void trace(String message, Object... args) {
            log(TRACE, logger.format(message, args));
        }

        @Override public void debug(String message) {
            log(DEBUG, message);
        }

        @Override public void debug(String message, Object... args) {
            log(DEBUG, logger.format(message, args));
        }

        @Override public void debug(Throwable t, String message) {
            log(DEBUG, message, t);
        }

        @Override public void debug(Throwable t, String message, Object... args) {
            log(DEBUG, logger.format(message, args), t);
        }

        @Override public void info(String message) {
            log(INFO, message);
        }

        @Override public void info(String message, Object... args) {
            log(INFO, logger.format(message, args));
        }

        @Override public void info(Throwable t, String message) {
            log(INFO, message, t);
        }

        @Override public void info(Throwable t, String message, Object... args) {
            log(INFO, logger.format(message, args), t);
        }

        @Override public void warn(String message) {
            log(WARNING, message);
        }

        @Override public void warn(String message, Object... args) {
            log(WARNING, logger.format(message, args));
        }

        @Override public void warn(Throwable t, String message) {
            log(WARNING, message, t);
        }

        @Override public void warn(Throwable t, String message, Object... args) {
            log(WARNING, logger.format(message, args), t);
        }

        @Override public void warn(Throwable t) {
            log(WARNING, null, t);
        }

        @Override public void error(Throwable t, String message) {
            log(ERROR, message, t);
        }

        @Override public void error(Throwable t, String message, Object... args) {
            log(ERROR, logger.format(message, args), t);
        }

        @Override public void error(Throwable t) {
            log(ERROR, null, t);
        }

        @Override public void wtf(String message) {
            log(WTF, message);
        }

        @Override public void wtf(String message, Object... args) {
            log(WTF, logger.format(message, args));
        }

        @Override public void wtf(Throwable t, String message) {
            log(WTF, message, t);
        }

        @Override public void wtf(Throwable t, String message, Object... args) {
            log(WTF, logger.format(message, args), t);
        }

        @Override public void wtf(Throwable t) {
            log(WTF, null, t);
        }


        private void log(LogAdapter.Level priority, String message) {
            if (priority.ordinal() < level) return;
            logger.log(priority, tag, message);
        }

        private void log(LogAdapter.Level priority, String message, Throwable t) {
            if (priority.ordinal() < level) return;
            logger.log(priority, tag, message, t);
        }
    }

    static class SimpleConsoleLogAdapter implements LogAdapter {

        final int warnLevel = Level.WARNING.ordinal();

        final Level level;

        SimpleConsoleLogAdapter(Level level) {
            this.level = level;
        }

        @Override public void log(Level priority, String tag, String message) {
            getOut(priority).println(formatTag(tag) + message);
        }

        @Override public void log(Level priority, String tag, String message, Throwable t) {
            PrintStream ps = getOut(priority);
            if(message != null)
                ps.println(formatTag(tag) + message);
            t.printStackTrace(ps);
        }

        @Override public String format(String message, Object... args) {
            return String.format(message, args);
        }

        @Override public Level getLogLevel() {
            return level;
        }

        private PrintStream getOut(Level level) {
            if(level.ordinal() >= warnLevel) {
                return System.err;
            } else {
                return System.out;
            }
        }

        private String formatTag(String tag) {
            return tag == null || tag.isEmpty() ? "" : tag + " : ";
        }
    }
}
