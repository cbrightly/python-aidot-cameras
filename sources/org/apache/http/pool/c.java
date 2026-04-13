package org.apache.http.pool;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import java.util.concurrent.TimeUnit;
import org.apache.http.util.a;

/* compiled from: PoolEntry */
public abstract class c<T, C> {
    private final String a;
    private final T b;
    private final C c;
    private final long d;
    private final long e;
    private long f;
    private long g;
    private volatile Object h;

    public abstract void a();

    public abstract boolean h();

    public c(String id, T route, C conn, long timeToLive, TimeUnit tunit) {
        a.i(route, "Route");
        a.i(conn, "Connection");
        a.i(tunit, "Time unit");
        this.a = id;
        this.b = route;
        this.c = conn;
        long currentTimeMillis = System.currentTimeMillis();
        this.d = currentTimeMillis;
        this.f = currentTimeMillis;
        if (timeToLive > 0) {
            this.e = currentTimeMillis + tunit.toMillis(timeToLive);
        } else {
            this.e = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
        this.g = this.e;
    }

    public String d() {
        return this.a;
    }

    public T e() {
        return this.b;
    }

    public C b() {
        return this.c;
    }

    public Object f() {
        return this.h;
    }

    public void j(Object state) {
        this.h = state;
    }

    public synchronized long g() {
        return this.f;
    }

    public synchronized long c() {
        return this.g;
    }

    public synchronized void k(long time, TimeUnit tunit) {
        long newExpiry;
        a.i(tunit, "Time unit");
        long currentTimeMillis = System.currentTimeMillis();
        this.f = currentTimeMillis;
        if (time > 0) {
            newExpiry = currentTimeMillis + tunit.toMillis(time);
        } else {
            newExpiry = DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE;
        }
        this.g = Math.min(newExpiry, this.e);
    }

    public synchronized boolean i(long now) {
        return now >= this.g;
    }

    public String toString() {
        return "[id:" + this.a + "][route:" + this.b + "][state:" + this.h + "]";
    }
}
