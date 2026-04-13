package org.slf4j.event;

import java.util.Queue;
import org.slf4j.b;
import org.slf4j.e;
import org.slf4j.helpers.g;

/* compiled from: EventRecodingLogger */
public class a implements b {
    String c;
    g d;
    Queue<d> f;

    public a(g logger, Queue<d> eventQueue) {
        this.d = logger;
        this.c = logger.getName();
        this.f = eventQueue;
    }

    public String getName() {
        return this.c;
    }

    public boolean isTraceEnabled() {
        return true;
    }

    public void trace(String msg) {
        d(b.TRACE, (e) null, msg, (Throwable) null);
    }

    public void trace(String format, Object arg) {
        e(b.TRACE, (e) null, format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        b(b.TRACE, (e) null, format, arg1, arg2);
    }

    public void trace(String format, Object... arguments) {
        c(b.TRACE, (e) null, format, arguments);
    }

    public void trace(String msg, Throwable t) {
        d(b.TRACE, (e) null, msg, t);
    }

    public boolean isDebugEnabled() {
        return true;
    }

    public void debug(String msg) {
        d(b.DEBUG, (e) null, msg, (Throwable) null);
    }

    public void debug(String format, Object arg) {
        e(b.DEBUG, (e) null, format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        b(b.DEBUG, (e) null, format, arg1, arg2);
    }

    public void debug(String format, Object... arguments) {
        c(b.DEBUG, (e) null, format, arguments);
    }

    public void debug(String msg, Throwable t) {
        d(b.DEBUG, (e) null, msg, t);
    }

    public boolean isInfoEnabled() {
        return true;
    }

    public void info(String msg) {
        d(b.INFO, (e) null, msg, (Throwable) null);
    }

    public void info(String format, Object arg) {
        e(b.INFO, (e) null, format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        b(b.INFO, (e) null, format, arg1, arg2);
    }

    public void info(String format, Object... arguments) {
        c(b.INFO, (e) null, format, arguments);
    }

    public void info(String msg, Throwable t) {
        d(b.INFO, (e) null, msg, t);
    }

    public boolean isWarnEnabled() {
        return true;
    }

    public void warn(String msg) {
        d(b.WARN, (e) null, msg, (Throwable) null);
    }

    public void warn(String format, Object arg) {
        e(b.WARN, (e) null, format, arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
        b(b.WARN, (e) null, format, arg1, arg2);
    }

    public void warn(String format, Object... arguments) {
        c(b.WARN, (e) null, format, arguments);
    }

    public void warn(String msg, Throwable t) {
        d(b.WARN, (e) null, msg, t);
    }

    public boolean isErrorEnabled() {
        return true;
    }

    public void error(String msg) {
        d(b.ERROR, (e) null, msg, (Throwable) null);
    }

    public void error(String format, Object arg) {
        e(b.ERROR, (e) null, format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        b(b.ERROR, (e) null, format, arg1, arg2);
    }

    public void error(String format, Object... arguments) {
        c(b.ERROR, (e) null, format, arguments);
    }

    public void error(String msg, Throwable t) {
        d(b.ERROR, (e) null, msg, t);
    }

    private void d(b level, e marker, String msg, Throwable t) {
        a(level, marker, msg, (Object[]) null, t);
    }

    private void e(b level, e marker, String msg, Object arg1) {
        a(level, marker, msg, new Object[]{arg1}, (Throwable) null);
    }

    private void b(b level, e marker, String msg, Object arg1, Object arg2) {
        if (arg2 instanceof Throwable) {
            b bVar = level;
            e eVar = marker;
            String str = msg;
            a(bVar, eVar, str, new Object[]{arg1}, (Throwable) arg2);
            return;
        }
        a(level, marker, msg, new Object[]{arg1, arg2}, (Throwable) null);
    }

    private void c(b level, e marker, String msg, Object[] args) {
        Throwable throwableCandidate = org.slf4j.helpers.b.a(args);
        if (throwableCandidate != null) {
            a(level, marker, msg, org.slf4j.helpers.b.b(args), throwableCandidate);
        } else {
            a(level, marker, msg, args, (Throwable) null);
        }
    }

    private void a(b level, e marker, String msg, Object[] args, Throwable throwable) {
        d loggingEvent = new d();
        loggingEvent.j(System.currentTimeMillis());
        loggingEvent.c(level);
        loggingEvent.d(this.d);
        loggingEvent.e(this.c);
        loggingEvent.f(marker);
        loggingEvent.g(msg);
        loggingEvent.h(Thread.currentThread().getName());
        loggingEvent.b(args);
        loggingEvent.i(throwable);
        this.f.add(loggingEvent);
    }
}
