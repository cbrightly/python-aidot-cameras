package com.android.volley;

import android.os.Process;
import androidx.annotation.VisibleForTesting;
import com.android.volley.a;
import java.util.concurrent.BlockingQueue;

/* compiled from: CacheDispatcher */
public class b extends Thread {
    private static final boolean c = n.b;
    private final BlockingQueue<i<?>> d;
    /* access modifiers changed from: private */
    public final BlockingQueue<i<?>> f;
    private final a q;
    private final l x;
    private volatile boolean y = false;
    private final o z;

    public b(BlockingQueue<i<?>> cacheQueue, BlockingQueue<i<?>> networkQueue, a cache, l delivery) {
        this.d = cacheQueue;
        this.f = networkQueue;
        this.q = cache;
        this.x = delivery;
        this.z = new o(this, networkQueue, delivery);
    }

    public void d() {
        this.y = true;
        interrupt();
    }

    public void run() {
        if (c) {
            n.e("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.q.initialize();
        while (true) {
            try {
                b();
            } catch (InterruptedException e) {
                if (this.y) {
                    Thread.currentThread().interrupt();
                    return;
                }
                n.c("Ignoring spurious interrupt of CacheDispatcher thread; use quit() to terminate it", new Object[0]);
            }
        }
    }

    private void b() {
        c((i) this.d.take());
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void c(i<?> request) {
        request.addMarker("cache-queue-take");
        request.sendEvent(1);
        try {
            if (request.isCanceled()) {
                request.finish("cache-discard-canceled");
                return;
            }
            a.C0017a entry = this.q.get(request.getCacheKey());
            if (entry == null) {
                request.addMarker("cache-miss");
                if (!this.z.c(request)) {
                    this.f.put(request);
                }
                request.sendEvent(2);
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (entry.b(currentTimeMillis)) {
                request.addMarker("cache-hit-expired");
                request.setCacheEntry(entry);
                if (!this.z.c(request)) {
                    this.f.put(request);
                }
                request.sendEvent(2);
                return;
            }
            request.addMarker("cache-hit");
            Response<?> response = request.parseNetworkResponse(new h(entry.a, entry.g));
            request.addMarker("cache-hit-parsed");
            if (!response.b()) {
                request.addMarker("cache-parsing-failed");
                this.q.a(request.getCacheKey(), true);
                request.setCacheEntry((a.C0017a) null);
                if (!this.z.c(request)) {
                    this.f.put(request);
                }
                request.sendEvent(2);
                return;
            }
            if (!entry.c(currentTimeMillis)) {
                this.x.a(request, response);
            } else {
                request.addMarker("cache-hit-refresh-needed");
                request.setCacheEntry(entry);
                response.d = true;
                if (!this.z.c(request)) {
                    this.x.b(request, response, new a(request));
                } else {
                    this.x.a(request, response);
                }
            }
            request.sendEvent(2);
        } finally {
            request.sendEvent(2);
        }
    }

    /* compiled from: CacheDispatcher */
    public class a implements Runnable {
        final /* synthetic */ i c;

        a(i iVar) {
            this.c = iVar;
        }

        public void run() {
            try {
                b.this.f.put(this.c);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
