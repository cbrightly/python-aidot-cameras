package org.apache.http.impl.client;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.http.conn.l;

/* compiled from: IdleConnectionEvictor */
public final class a0 {
    private final l a;
    private final ThreadFactory b;
    private final Thread c;
    /* access modifiers changed from: private */
    public final long d;
    /* access modifiers changed from: private */
    public final long e;
    /* access modifiers changed from: private */
    public volatile Exception f;

    public a0(l connectionManager, ThreadFactory threadFactory, long sleepTime, TimeUnit sleepTimeUnit, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        this.a = (l) org.apache.http.util.a.i(connectionManager, "Connection manager");
        ThreadFactory bVar = threadFactory != null ? threadFactory : new b();
        this.b = bVar;
        this.d = sleepTimeUnit != null ? sleepTimeUnit.toMillis(sleepTime) : sleepTime;
        this.e = maxIdleTimeUnit != null ? maxIdleTimeUnit.toMillis(maxIdleTime) : maxIdleTime;
        this.c = bVar.newThread(new a(connectionManager));
    }

    /* compiled from: IdleConnectionEvictor */
    public class a implements Runnable {
        final /* synthetic */ l c;

        a(l lVar) {
            this.c = lVar;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(a0.this.d);
                    this.c.j();
                    if (a0.this.e > 0) {
                        this.c.a(a0.this.e, TimeUnit.MILLISECONDS);
                    }
                } catch (Exception ex) {
                    Exception unused = a0.this.f = ex;
                    return;
                }
            }
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public a0(l connectionManager, long maxIdleTime, TimeUnit maxIdleTimeUnit) {
        this(connectionManager, (ThreadFactory) null, maxIdleTime > 0 ? maxIdleTime : 5, maxIdleTimeUnit != null ? maxIdleTimeUnit : TimeUnit.SECONDS, maxIdleTime, maxIdleTimeUnit);
    }

    public void e() {
        this.c.start();
    }

    public void d() {
        this.c.interrupt();
    }

    /* compiled from: IdleConnectionEvictor */
    public static class b implements ThreadFactory {
        b() {
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "Connection evictor");
            t.setDaemon(true);
            return t;
        }
    }
}
