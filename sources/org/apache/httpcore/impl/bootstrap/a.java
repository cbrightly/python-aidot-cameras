package org.apache.httpcore.impl.bootstrap;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import org.apache.httpcore.config.c;
import org.apache.httpcore.i;
import org.apache.httpcore.protocol.l;

/* compiled from: HttpServer */
public class a {
    private final int a;
    private final InetAddress b;
    private final c c;
    private final ServerSocketFactory d;
    private final l e;
    private final i<? extends org.apache.httpcore.impl.c> f;
    private final c g;
    private final org.apache.httpcore.c h;
    private final ThreadPoolExecutor i;
    private final ThreadGroup j;
    private final g k;
    private final AtomicReference<C0148a> l = new AtomicReference<>(C0148a.READY);
    private volatile ServerSocket m;
    private volatile b n;

    /* renamed from: org.apache.httpcore.impl.bootstrap.a$a  reason: collision with other inner class name */
    /* compiled from: HttpServer */
    public enum C0148a {
        READY,
        ACTIVE,
        STOPPING
    }

    a(int port, InetAddress ifAddress, c socketConfig, ServerSocketFactory serverSocketFactory, l httpService, i<? extends org.apache.httpcore.impl.c> connectionFactory, c sslSetupHandler, org.apache.httpcore.c exceptionLogger) {
        int i2 = port;
        this.a = i2;
        this.b = ifAddress;
        this.c = socketConfig;
        this.d = serverSocketFactory;
        this.e = httpService;
        this.f = connectionFactory;
        this.g = sslSetupHandler;
        this.h = exceptionLogger;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        ThreadPoolExecutor threadPoolExecutor = r9;
        ThreadPoolExecutor threadPoolExecutor2 = new ThreadPoolExecutor(1, 1, 0, timeUnit, synchronousQueue, new e("HTTP-listener-" + i2));
        this.i = threadPoolExecutor;
        ThreadGroup threadGroup = new ThreadGroup("HTTP-workers");
        this.j = threadGroup;
        this.k = new g(0, Integer.MAX_VALUE, 1, TimeUnit.SECONDS, new SynchronousQueue(), new e("HTTP-worker", threadGroup));
    }

    public void c() {
        if (this.l.compareAndSet(C0148a.READY, C0148a.ACTIVE)) {
            this.m = this.d.createServerSocket();
            this.m.setReuseAddress(this.c.j());
            this.m.bind(new InetSocketAddress(this.b, this.a), this.c.c());
            if (this.c.d() > 0) {
                this.m.setReceiveBufferSize(this.c.d());
            }
            if (this.g != null && (this.m instanceof SSLServerSocket)) {
                this.g.a((SSLServerSocket) this.m);
            }
            this.n = new b(this.c, this.m, this.e, this.f, this.h, this.k);
            this.i.execute(this.n);
        }
    }

    public void d() {
        if (this.l.compareAndSet(C0148a.ACTIVE, C0148a.STOPPING)) {
            this.i.shutdown();
            this.k.shutdown();
            b local = this.n;
            if (local != null) {
                try {
                    local.b();
                } catch (IOException ex) {
                    this.h.a(ex);
                }
            }
            this.j.interrupt();
        }
    }

    public void a(long timeout, TimeUnit timeUnit) {
        this.k.awaitTermination(timeout, timeUnit);
    }

    public void b(long gracePeriod, TimeUnit timeUnit) {
        d();
        if (gracePeriod > 0) {
            try {
                a(gracePeriod, timeUnit);
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
            }
        }
        for (f worker : this.k.a()) {
            try {
                worker.a().shutdown();
            } catch (IOException ex) {
                this.h.a(ex);
            }
        }
    }
}
