package org.apache.http.impl.execchain;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.http.concurrent.a;
import org.apache.http.conn.g;
import org.apache.http.conn.l;
import org.apache.http.h;

/* compiled from: ConnectionHolder */
public class c implements g, a, Closeable {
    private final org.apache.commons.logging.a c;
    private final l d;
    private final h f;
    private volatile TimeUnit p0;
    private final AtomicBoolean q = new AtomicBoolean(false);
    private volatile boolean x;
    private volatile Object y;
    private volatile long z;

    public c(org.apache.commons.logging.a log, l manager, h managedConn) {
        this.c = log;
        this.d = manager;
        this.f = managedConn;
    }

    public boolean i() {
        return this.x;
    }

    public void i0() {
        this.x = true;
    }

    public void j() {
        this.x = false;
    }

    public void y0(Object state) {
        this.y = state;
    }

    public void m(long duration, TimeUnit tunit) {
        synchronized (this.f) {
            this.z = duration;
            this.p0 = tunit;
        }
    }

    private void l(boolean reusable) {
        if (this.q.compareAndSet(false, true)) {
            synchronized (this.f) {
                if (reusable) {
                    this.d.l(this.f, this.y, this.z, this.p0);
                } else {
                    try {
                        this.f.close();
                        this.c.debug("Connection discarded");
                        this.d.l(this.f, (Object) null, 0, TimeUnit.MILLISECONDS);
                    } catch (IOException ex) {
                        try {
                            if (this.c.isDebugEnabled()) {
                                this.c.debug(ex.getMessage(), ex);
                            }
                        } finally {
                            this.d.l(this.f, (Object) null, 0, TimeUnit.MILLISECONDS);
                        }
                    }
                }
            }
        }
    }

    public void g() {
        l(this.x);
    }

    public void c() {
        if (this.q.compareAndSet(false, true)) {
            synchronized (this.f) {
                try {
                    this.f.shutdown();
                    this.c.debug("Connection discarded");
                    this.d.l(this.f, (Object) null, 0, TimeUnit.MILLISECONDS);
                } catch (IOException ex) {
                    try {
                        if (this.c.isDebugEnabled()) {
                            this.c.debug(ex.getMessage(), ex);
                        }
                        this.d.l(this.f, (Object) null, 0, TimeUnit.MILLISECONDS);
                    } catch (Throwable th) {
                        this.d.l(this.f, (Object) null, 0, TimeUnit.MILLISECONDS);
                        throw th;
                    }
                }
            }
        }
    }

    public boolean cancel() {
        boolean alreadyReleased = this.q.get();
        this.c.debug("Cancelling request execution");
        c();
        return !alreadyReleased;
    }

    public boolean a() {
        return this.q.get();
    }

    public void close() {
        l(false);
    }
}
