package org.slf4j.event;

import org.slf4j.e;
import org.slf4j.helpers.g;

/* compiled from: SubstituteLoggingEvent */
public class d implements c {
    b a;
    e b;
    String c;
    g d;
    String e;
    String f;
    Object[] g;
    long h;
    Throwable i;

    public void c(b level) {
        this.a = level;
    }

    public void f(e marker) {
        this.b = marker;
    }

    public void e(String loggerName) {
        this.c = loggerName;
    }

    public g a() {
        return this.d;
    }

    public void d(g logger) {
        this.d = logger;
    }

    public void g(String message) {
        this.f = message;
    }

    public void b(Object[] argArray) {
        this.g = argArray;
    }

    public void j(long timeStamp) {
        this.h = timeStamp;
    }

    public void h(String threadName) {
        this.e = threadName;
    }

    public void i(Throwable throwable) {
        this.i = throwable;
    }
}
