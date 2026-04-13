package org.slf4j.helpers;

import org.slf4j.b;
import org.slf4j.e;

/* compiled from: MarkerIgnoringBase */
public abstract class a extends f implements b {
    private static final long serialVersionUID = 9044267456635152283L;

    public abstract /* synthetic */ void debug(String str);

    public abstract /* synthetic */ void debug(String str, Object obj);

    public abstract /* synthetic */ void debug(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void debug(String str, Throwable th);

    public abstract /* synthetic */ void debug(String str, Object... objArr);

    public abstract /* synthetic */ void error(String str);

    public abstract /* synthetic */ void error(String str, Object obj);

    public abstract /* synthetic */ void error(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void error(String str, Throwable th);

    public abstract /* synthetic */ void error(String str, Object... objArr);

    public abstract /* synthetic */ void info(String str);

    public abstract /* synthetic */ void info(String str, Object obj);

    public abstract /* synthetic */ void info(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void info(String str, Throwable th);

    public abstract /* synthetic */ void info(String str, Object... objArr);

    public abstract /* synthetic */ boolean isDebugEnabled();

    public abstract /* synthetic */ boolean isErrorEnabled();

    public abstract /* synthetic */ boolean isInfoEnabled();

    public abstract /* synthetic */ boolean isTraceEnabled();

    public abstract /* synthetic */ boolean isWarnEnabled();

    public abstract /* synthetic */ void trace(String str);

    public abstract /* synthetic */ void trace(String str, Object obj);

    public abstract /* synthetic */ void trace(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void trace(String str, Throwable th);

    public abstract /* synthetic */ void trace(String str, Object... objArr);

    public abstract /* synthetic */ void warn(String str);

    public abstract /* synthetic */ void warn(String str, Object obj);

    public abstract /* synthetic */ void warn(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void warn(String str, Throwable th);

    public abstract /* synthetic */ void warn(String str, Object... objArr);

    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    public boolean isTraceEnabled(e marker) {
        return isTraceEnabled();
    }

    public void trace(e marker, String msg) {
        trace(msg);
    }

    public void trace(e marker, String format, Object arg) {
        trace(format, arg);
    }

    public void trace(e marker, String format, Object arg1, Object arg2) {
        trace(format, arg1, arg2);
    }

    public void trace(e marker, String format, Object... arguments) {
        trace(format, arguments);
    }

    public void trace(e marker, String msg, Throwable t) {
        trace(msg, t);
    }

    public boolean isDebugEnabled(e marker) {
        return isDebugEnabled();
    }

    public void debug(e marker, String msg) {
        debug(msg);
    }

    public void debug(e marker, String format, Object arg) {
        debug(format, arg);
    }

    public void debug(e marker, String format, Object arg1, Object arg2) {
        debug(format, arg1, arg2);
    }

    public void debug(e marker, String format, Object... arguments) {
        debug(format, arguments);
    }

    public void debug(e marker, String msg, Throwable t) {
        debug(msg, t);
    }

    public boolean isInfoEnabled(e marker) {
        return isInfoEnabled();
    }

    public void info(e marker, String msg) {
        info(msg);
    }

    public void info(e marker, String format, Object arg) {
        info(format, arg);
    }

    public void info(e marker, String format, Object arg1, Object arg2) {
        info(format, arg1, arg2);
    }

    public void info(e marker, String format, Object... arguments) {
        info(format, arguments);
    }

    public void info(e marker, String msg, Throwable t) {
        info(msg, t);
    }

    public boolean isWarnEnabled(e marker) {
        return isWarnEnabled();
    }

    public void warn(e marker, String msg) {
        warn(msg);
    }

    public void warn(e marker, String format, Object arg) {
        warn(format, arg);
    }

    public void warn(e marker, String format, Object arg1, Object arg2) {
        warn(format, arg1, arg2);
    }

    public void warn(e marker, String format, Object... arguments) {
        warn(format, arguments);
    }

    public void warn(e marker, String msg, Throwable t) {
        warn(msg, t);
    }

    public boolean isErrorEnabled(e marker) {
        return isErrorEnabled();
    }

    public void error(e marker, String msg) {
        error(msg);
    }

    public void error(e marker, String format, Object arg) {
        error(format, arg);
    }

    public void error(e marker, String format, Object arg1, Object arg2) {
        error(format, arg1, arg2);
    }

    public void error(e marker, String format, Object... arguments) {
        error(format, arguments);
    }

    public void error(e marker, String msg, Throwable t) {
        error(msg, t);
    }

    public String toString() {
        return getClass().getName() + "(" + getName() + ")";
    }
}
