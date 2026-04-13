package com.amazonaws.logging;

import com.amazonaws.logging.LogFactory;
import org.apache.commons.logging.a;
import org.apache.commons.logging.h;

public class ApacheCommonsLogging implements Log {
    private LogFactory.Level level = null;
    private a log;
    private Class logClass;
    private String logString;

    public ApacheCommonsLogging(Class logClass2) {
        this.logClass = logClass2;
        this.log = h.n(logClass2);
    }

    public ApacheCommonsLogging(String logString2) {
        this.logString = logString2;
        this.log = h.o(logString2);
    }

    public boolean isDebugEnabled() {
        return this.log.isDebugEnabled() && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.DEBUG.getValue());
    }

    public boolean isErrorEnabled() {
        return this.log.isErrorEnabled() && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.ERROR.getValue());
    }

    public boolean isInfoEnabled() {
        return this.log.isInfoEnabled() && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.INFO.getValue());
    }

    public boolean isTraceEnabled() {
        return this.log.isTraceEnabled() && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.TRACE.getValue());
    }

    public boolean isWarnEnabled() {
        return this.log.isWarnEnabled() && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.WARN.getValue());
    }

    public void trace(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.TRACE.getValue()) {
            this.log.trace(message);
        }
    }

    public void trace(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.TRACE.getValue()) {
            this.log.trace(message, t);
        }
    }

    public void debug(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.DEBUG.getValue()) {
            this.log.debug(message);
        }
    }

    public void debug(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.DEBUG.getValue()) {
            this.log.debug(message, t);
        }
    }

    public void info(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.INFO.getValue()) {
            this.log.info(message);
        }
    }

    public void info(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.INFO.getValue()) {
            this.log.info(message, t);
        }
    }

    public void warn(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.WARN.getValue()) {
            this.log.warn(message);
        }
    }

    public void warn(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.WARN.getValue()) {
            this.log.warn(message, t);
        }
    }

    public void error(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.ERROR.getValue()) {
            this.log.error(message);
        }
    }

    public void error(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.ERROR.getValue()) {
            this.log.error(message, t);
        }
    }

    public void setLevel(LogFactory.Level level2) {
        this.level = level2;
    }

    private LogFactory.Level getLevel() {
        LogFactory.Level level2 = this.level;
        if (level2 != null) {
            return level2;
        }
        return LogFactory.getLevel();
    }
}
