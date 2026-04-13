package org.apache.commons.logging;

/* compiled from: Log */
public interface a {
    void debug(Object obj);

    void debug(Object obj, Throwable th);

    void error(Object obj);

    void error(Object obj, Throwable th);

    void info(Object obj);

    void info(Object obj, Throwable th);

    boolean isDebugEnabled();

    boolean isErrorEnabled();

    boolean isInfoEnabled();

    boolean isTraceEnabled();

    boolean isWarnEnabled();

    void trace(Object obj);

    void trace(Object obj, Throwable th);

    void warn(Object obj);

    void warn(Object obj, Throwable th);
}
