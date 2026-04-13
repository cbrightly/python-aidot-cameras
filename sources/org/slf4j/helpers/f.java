package org.slf4j.helpers;

import java.io.Serializable;
import org.slf4j.b;
import org.slf4j.c;
import org.slf4j.e;

/* compiled from: NamedLoggerBase */
public abstract class f implements b, Serializable {
    private static final long serialVersionUID = 7535258609338176893L;
    protected String name;

    public abstract /* synthetic */ void debug(String str);

    public abstract /* synthetic */ void debug(String str, Object obj);

    public abstract /* synthetic */ void debug(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void debug(String str, Throwable th);

    public abstract /* synthetic */ void debug(String str, Object... objArr);

    public abstract /* synthetic */ void debug(e eVar, String str);

    public abstract /* synthetic */ void debug(e eVar, String str, Object obj);

    public abstract /* synthetic */ void debug(e eVar, String str, Object obj, Object obj2);

    public abstract /* synthetic */ void debug(e eVar, String str, Throwable th);

    public abstract /* synthetic */ void debug(e eVar, String str, Object... objArr);

    public abstract /* synthetic */ void error(String str);

    public abstract /* synthetic */ void error(String str, Object obj);

    public abstract /* synthetic */ void error(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void error(String str, Throwable th);

    public abstract /* synthetic */ void error(String str, Object... objArr);

    public abstract /* synthetic */ void error(e eVar, String str);

    public abstract /* synthetic */ void error(e eVar, String str, Object obj);

    public abstract /* synthetic */ void error(e eVar, String str, Object obj, Object obj2);

    public abstract /* synthetic */ void error(e eVar, String str, Throwable th);

    public abstract /* synthetic */ void error(e eVar, String str, Object... objArr);

    public abstract /* synthetic */ void info(String str);

    public abstract /* synthetic */ void info(String str, Object obj);

    public abstract /* synthetic */ void info(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void info(String str, Throwable th);

    public abstract /* synthetic */ void info(String str, Object... objArr);

    public abstract /* synthetic */ void info(e eVar, String str);

    public abstract /* synthetic */ void info(e eVar, String str, Object obj);

    public abstract /* synthetic */ void info(e eVar, String str, Object obj, Object obj2);

    public abstract /* synthetic */ void info(e eVar, String str, Throwable th);

    public abstract /* synthetic */ void info(e eVar, String str, Object... objArr);

    public abstract /* synthetic */ boolean isDebugEnabled();

    public abstract /* synthetic */ boolean isDebugEnabled(e eVar);

    public abstract /* synthetic */ boolean isErrorEnabled();

    public abstract /* synthetic */ boolean isErrorEnabled(e eVar);

    public abstract /* synthetic */ boolean isInfoEnabled();

    public abstract /* synthetic */ boolean isInfoEnabled(e eVar);

    public abstract /* synthetic */ boolean isTraceEnabled();

    public abstract /* synthetic */ boolean isTraceEnabled(e eVar);

    public abstract /* synthetic */ boolean isWarnEnabled();

    public abstract /* synthetic */ boolean isWarnEnabled(e eVar);

    public abstract /* synthetic */ void trace(String str);

    public abstract /* synthetic */ void trace(String str, Object obj);

    public abstract /* synthetic */ void trace(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void trace(String str, Throwable th);

    public abstract /* synthetic */ void trace(String str, Object... objArr);

    public abstract /* synthetic */ void trace(e eVar, String str);

    public abstract /* synthetic */ void trace(e eVar, String str, Object obj);

    public abstract /* synthetic */ void trace(e eVar, String str, Object obj, Object obj2);

    public abstract /* synthetic */ void trace(e eVar, String str, Throwable th);

    public abstract /* synthetic */ void trace(e eVar, String str, Object... objArr);

    public abstract /* synthetic */ void warn(String str);

    public abstract /* synthetic */ void warn(String str, Object obj);

    public abstract /* synthetic */ void warn(String str, Object obj, Object obj2);

    public abstract /* synthetic */ void warn(String str, Throwable th);

    public abstract /* synthetic */ void warn(String str, Object... objArr);

    public abstract /* synthetic */ void warn(e eVar, String str);

    public abstract /* synthetic */ void warn(e eVar, String str, Object obj);

    public abstract /* synthetic */ void warn(e eVar, String str, Object obj, Object obj2);

    public abstract /* synthetic */ void warn(e eVar, String str, Throwable th);

    public abstract /* synthetic */ void warn(e eVar, String str, Object... objArr);

    f() {
    }

    public String getName() {
        return this.name;
    }

    /* access modifiers changed from: protected */
    public Object readResolve() {
        return c.j(getName());
    }
}
