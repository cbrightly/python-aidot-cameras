package org.slf4j.helpers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Queue;
import org.slf4j.b;
import org.slf4j.event.a;
import org.slf4j.event.c;
import org.slf4j.event.d;

/* compiled from: SubstituteLogger */
public class g implements b {
    private final String c;
    private volatile b d;
    private Boolean f;
    private Method q;
    private a x;
    private Queue<d> y;
    private final boolean z;

    public g(String name, Queue<d> eventQueue, boolean createdPostInitialization) {
        this.c = name;
        this.y = eventQueue;
        this.z = createdPostInitialization;
    }

    public String getName() {
        return this.c;
    }

    public boolean isTraceEnabled() {
        return a().isTraceEnabled();
    }

    public void trace(String msg) {
        a().trace(msg);
    }

    public void trace(String format, Object arg) {
        a().trace(format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        a().trace(format, arg1, arg2);
    }

    public void trace(String format, Object... arguments) {
        a().trace(format, arguments);
    }

    public void trace(String msg, Throwable t) {
        a().trace(msg, t);
    }

    public boolean isDebugEnabled() {
        return a().isDebugEnabled();
    }

    public void debug(String msg) {
        a().debug(msg);
    }

    public void debug(String format, Object arg) {
        a().debug(format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        a().debug(format, arg1, arg2);
    }

    public void debug(String format, Object... arguments) {
        a().debug(format, arguments);
    }

    public void debug(String msg, Throwable t) {
        a().debug(msg, t);
    }

    public boolean isInfoEnabled() {
        return a().isInfoEnabled();
    }

    public void info(String msg) {
        a().info(msg);
    }

    public void info(String format, Object arg) {
        a().info(format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        a().info(format, arg1, arg2);
    }

    public void info(String format, Object... arguments) {
        a().info(format, arguments);
    }

    public void info(String msg, Throwable t) {
        a().info(msg, t);
    }

    public boolean isWarnEnabled() {
        return a().isWarnEnabled();
    }

    public void warn(String msg) {
        a().warn(msg);
    }

    public void warn(String format, Object arg) {
        a().warn(format, arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
        a().warn(format, arg1, arg2);
    }

    public void warn(String format, Object... arguments) {
        a().warn(format, arguments);
    }

    public void warn(String msg, Throwable t) {
        a().warn(msg, t);
    }

    public boolean isErrorEnabled() {
        return a().isErrorEnabled();
    }

    public void error(String msg) {
        a().error(msg);
    }

    public void error(String format, Object arg) {
        a().error(format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        a().error(format, arg1, arg2);
    }

    public void error(String format, Object... arguments) {
        a().error(format, arguments);
    }

    public void error(String msg, Throwable t) {
        a().error(msg, t);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass() || !this.c.equals(((g) o).c)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    /* access modifiers changed from: package-private */
    public b a() {
        if (this.d != null) {
            return this.d;
        }
        if (this.z) {
            return c.NOP_LOGGER;
        }
        return b();
    }

    private b b() {
        if (this.x == null) {
            this.x = new a(this, this.y);
        }
        return this.x;
    }

    public void g(b delegate) {
        this.d = delegate;
    }

    public boolean c() {
        Boolean bool = this.f;
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            this.q = this.d.getClass().getMethod("log", new Class[]{c.class});
            this.f = Boolean.TRUE;
        } catch (NoSuchMethodException e) {
            this.f = Boolean.FALSE;
        }
        return this.f.booleanValue();
    }

    public void f(c event) {
        if (c()) {
            try {
                this.q.invoke(this.d, new Object[]{event});
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            }
        }
    }

    public boolean e() {
        return this.d == null;
    }

    public boolean d() {
        return this.d instanceof c;
    }
}
