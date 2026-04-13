package com.downloader.internal;

import com.downloader.l;
import com.downloader.request.a;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: DownloadRequestQueue */
public class b {
    private static b a;
    private final Map<Integer, a> b = new ConcurrentHashMap();
    private final AtomicInteger c = new AtomicInteger();

    private b() {
    }

    public static void g() {
        e();
    }

    public static b e() {
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b();
                }
            }
        }
        return a;
    }

    private int f() {
        return this.c.incrementAndGet();
    }

    private void c(a request) {
        if (request != null) {
            request.f();
            this.b.remove(Integer.valueOf(request.q()));
        }
    }

    public void b() {
        for (Map.Entry<Integer, a> currentRequestMapEntry : this.b.entrySet()) {
            c((a) currentRequestMapEntry.getValue());
        }
    }

    public void a(a request) {
        this.b.put(Integer.valueOf(request.q()), request);
        request.I(l.QUEUED);
        request.H(f());
        request.E(com.downloader.core.a.b().a().a().submit(new c(request)));
    }

    public void d(a request) {
        this.b.remove(Integer.valueOf(request.q()));
    }
}
