package com.android.volley;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: RequestQueue */
public class j {
    private final AtomicInteger a;
    private final Set<i<?>> b;
    private final PriorityBlockingQueue<i<?>> c;
    private final PriorityBlockingQueue<i<?>> d;
    private final a e;
    private final f f;
    private final l g;
    private final g[] h;
    private b i;
    private final List<b> j;
    private final List<a> k;

    /* compiled from: RequestQueue */
    public interface a {
        void a(i<?> iVar, int i);
    }

    @Deprecated
    /* compiled from: RequestQueue */
    public interface b<T> {
        void a(i<T> iVar);
    }

    public j(a cache, f network, int threadPoolSize, l delivery) {
        this.a = new AtomicInteger();
        this.b = new HashSet();
        this.c = new PriorityBlockingQueue<>();
        this.d = new PriorityBlockingQueue<>();
        this.j = new ArrayList();
        this.k = new ArrayList();
        this.e = cache;
        this.f = network;
        this.h = new g[threadPoolSize];
        this.g = delivery;
    }

    public j(a cache, f network, int threadPoolSize) {
        this(cache, network, threadPoolSize, new d(new Handler(Looper.getMainLooper())));
    }

    public j(a cache, f network) {
        this(cache, network, 4);
    }

    public void g() {
        h();
        b bVar = new b(this.c, this.d, this.e, this.g);
        this.i = bVar;
        bVar.start();
        for (int i2 = 0; i2 < this.h.length; i2++) {
            g networkDispatcher = new g(this.d, this.f, this.e, this.g);
            this.h[i2] = networkDispatcher;
            networkDispatcher.start();
        }
    }

    public void h() {
        b bVar = this.i;
        if (bVar != null) {
            bVar.d();
        }
        for (g mDispatcher : this.h) {
            if (mDispatcher != null) {
                mDispatcher.e();
            }
        }
    }

    public int d() {
        return this.a.incrementAndGet();
    }

    public <T> i<T> a(i<T> request) {
        request.setRequestQueue(this);
        synchronized (this.b) {
            this.b.add(request);
        }
        request.setSequence(d());
        request.addMarker("add-to-queue");
        e(request, 0);
        b(request);
        return request;
    }

    /* access modifiers changed from: package-private */
    public <T> void b(i<T> request) {
        if (!request.shouldCache()) {
            f(request);
        } else {
            this.c.add(request);
        }
    }

    /* access modifiers changed from: package-private */
    public <T> void c(i<T> request) {
        synchronized (this.b) {
            this.b.remove(request);
        }
        synchronized (this.j) {
            Iterator<b> it = this.j.iterator();
            while (it.hasNext()) {
                ((b) it.next()).a(request);
            }
        }
        e(request, 5);
    }

    /* access modifiers changed from: package-private */
    public void e(i<?> request, int event) {
        synchronized (this.k) {
            for (a listener : this.k) {
                listener.a(request, event);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public <T> void f(i<T> request) {
        this.d.add(request);
    }
}
