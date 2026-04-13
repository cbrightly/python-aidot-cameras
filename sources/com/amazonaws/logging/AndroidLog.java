package com.amazonaws.logging;

import android.util.Log;
import com.amazonaws.logging.LogFactory;

public class AndroidLog implements Log {
    private LogFactory.Level level = null;
    private final String tag;

    public AndroidLog(String tag2) {
        this.tag = tag2;
    }

    public boolean isDebugEnabled() {
        return Log.isLoggable(this.tag, 3) && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.DEBUG.getValue());
    }

    public boolean isErrorEnabled() {
        return Log.isLoggable(this.tag, 6) && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.ERROR.getValue());
    }

    public boolean isInfoEnabled() {
        return Log.isLoggable(this.tag, 4) && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.INFO.getValue());
    }

    public boolean isTraceEnabled() {
        return Log.isLoggable(this.tag, 2) && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.TRACE.getValue());
    }

    public boolean isWarnEnabled() {
        return Log.isLoggable(this.tag, 5) && (getLevel() == null || getLevel().getValue() <= LogFactory.Level.WARN.getValue());
    }

    public void trace(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.TRACE.getValue()) {
            Log.v(this.tag, message.toString());
        }
    }

    public void trace(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.TRACE.getValue()) {
            Log.v(this.tag, message.toString(), t);
        }
    }

    public void debug(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.DEBUG.getValue()) {
            Log.d(this.tag, message.toString());
        }
    }

    public void debug(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.DEBUG.getValue()) {
            Log.d(this.tag, message.toString(), t);
        }
    }

    public void info(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.INFO.getValue()) {
            Log.i(this.tag, message.toString());
        }
    }

    public void info(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.INFO.getValue()) {
            Log.i(this.tag, message.toString(), t);
        }
    }

    public void warn(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.WARN.getValue()) {
            Log.w(this.tag, message.toString());
        }
    }

    public void warn(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.WARN.getValue()) {
            Log.w(this.tag, message.toString(), t);
        }
    }

    public void error(Object message) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.ERROR.getValue()) {
            Log.e(this.tag, message.toString());
        }
    }

    public void error(Object message, Throwable t) {
        if (getLevel() == null || getLevel().getValue() <= LogFactory.Level.ERROR.getValue()) {
            Log.e(this.tag, message.toString(), t);
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
