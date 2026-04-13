package io.netty.util.internal.logging;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JdkLogger extends AbstractInternalLogger {
    static final String SELF = JdkLogger.class.getName();
    static final String SUPER = AbstractInternalLogger.class.getName();
    private static final long serialVersionUID = -1767272577989225979L;
    final transient Logger logger;

    JdkLogger(Logger logger2) {
        super(logger2.getName());
        this.logger = logger2;
    }

    public boolean isTraceEnabled() {
        return this.logger.isLoggable(Level.FINEST);
    }

    public void trace(String msg) {
        Logger logger2 = this.logger;
        Level level = Level.FINEST;
        if (logger2.isLoggable(level)) {
            log(SELF, level, msg, (Throwable) null);
        }
    }

    public void trace(String format, Object arg) {
        Logger logger2 = this.logger;
        Level level = Level.FINEST;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void trace(String format, Object argA, Object argB) {
        Logger logger2 = this.logger;
        Level level = Level.FINEST;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void trace(String format, Object... argArray) {
        Logger logger2 = this.logger;
        Level level = Level.FINEST;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void trace(String msg, Throwable t) {
        Logger logger2 = this.logger;
        Level level = Level.FINEST;
        if (logger2.isLoggable(level)) {
            log(SELF, level, msg, t);
        }
    }

    public boolean isDebugEnabled() {
        return this.logger.isLoggable(Level.FINE);
    }

    public void debug(String msg) {
        Logger logger2 = this.logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            log(SELF, level, msg, (Throwable) null);
        }
    }

    public void debug(String format, Object arg) {
        Logger logger2 = this.logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void debug(String format, Object argA, Object argB) {
        Logger logger2 = this.logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void debug(String format, Object... argArray) {
        Logger logger2 = this.logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void debug(String msg, Throwable t) {
        Logger logger2 = this.logger;
        Level level = Level.FINE;
        if (logger2.isLoggable(level)) {
            log(SELF, level, msg, t);
        }
    }

    public boolean isInfoEnabled() {
        return this.logger.isLoggable(Level.INFO);
    }

    public void info(String msg) {
        if (this.logger.isLoggable(Level.INFO)) {
            log(SELF, Level.INFO, msg, (Throwable) null);
        }
    }

    public void info(String format, Object arg) {
        if (this.logger.isLoggable(Level.INFO)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    public void info(String format, Object argA, Object argB) {
        if (this.logger.isLoggable(Level.INFO)) {
            FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    public void info(String format, Object... argArray) {
        if (this.logger.isLoggable(Level.INFO)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            log(SELF, Level.INFO, ft.getMessage(), ft.getThrowable());
        }
    }

    public void info(String msg, Throwable t) {
        if (this.logger.isLoggable(Level.INFO)) {
            log(SELF, Level.INFO, msg, t);
        }
    }

    public boolean isWarnEnabled() {
        return this.logger.isLoggable(Level.WARNING);
    }

    public void warn(String msg) {
        Logger logger2 = this.logger;
        Level level = Level.WARNING;
        if (logger2.isLoggable(level)) {
            log(SELF, level, msg, (Throwable) null);
        }
    }

    public void warn(String format, Object arg) {
        Logger logger2 = this.logger;
        Level level = Level.WARNING;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void warn(String format, Object argA, Object argB) {
        Logger logger2 = this.logger;
        Level level = Level.WARNING;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void warn(String format, Object... argArray) {
        Logger logger2 = this.logger;
        Level level = Level.WARNING;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, argArray);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void warn(String msg, Throwable t) {
        Logger logger2 = this.logger;
        Level level = Level.WARNING;
        if (logger2.isLoggable(level)) {
            log(SELF, level, msg, t);
        }
    }

    public boolean isErrorEnabled() {
        return this.logger.isLoggable(Level.SEVERE);
    }

    public void error(String msg) {
        Logger logger2 = this.logger;
        Level level = Level.SEVERE;
        if (logger2.isLoggable(level)) {
            log(SELF, level, msg, (Throwable) null);
        }
    }

    public void error(String format, Object arg) {
        Logger logger2 = this.logger;
        Level level = Level.SEVERE;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.format(format, arg);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void error(String format, Object argA, Object argB) {
        Logger logger2 = this.logger;
        Level level = Level.SEVERE;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.format(format, argA, argB);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void error(String format, Object... arguments) {
        Logger logger2 = this.logger;
        Level level = Level.SEVERE;
        if (logger2.isLoggable(level)) {
            FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
            log(SELF, level, ft.getMessage(), ft.getThrowable());
        }
    }

    public void error(String msg, Throwable t) {
        Logger logger2 = this.logger;
        Level level = Level.SEVERE;
        if (logger2.isLoggable(level)) {
            log(SELF, level, msg, t);
        }
    }

    private void log(String callerFQCN, Level level, String msg, Throwable t) {
        LogRecord record = new LogRecord(level, msg);
        record.setLoggerName(name());
        record.setThrown(t);
        fillCallerData(callerFQCN, record);
        this.logger.log(record);
    }

    private static void fillCallerData(String callerFQCN, LogRecord record) {
        StackTraceElement[] steArray = new Throwable().getStackTrace();
        int selfIndex = -1;
        int i = 0;
        while (true) {
            if (i >= steArray.length) {
                break;
            }
            String className = steArray[i].getClassName();
            if (className.equals(callerFQCN) || className.equals(SUPER)) {
                selfIndex = i;
            } else {
                i++;
            }
        }
        int found = -1;
        int i2 = selfIndex + 1;
        while (true) {
            if (i2 >= steArray.length) {
                break;
            }
            String className2 = steArray[i2].getClassName();
            if (!className2.equals(callerFQCN) && !className2.equals(SUPER)) {
                found = i2;
                break;
            }
            i2++;
        }
        if (found != -1) {
            StackTraceElement ste = steArray[found];
            record.setSourceClassName(ste.getClassName());
            record.setSourceMethodName(ste.getMethodName());
        }
    }
}
