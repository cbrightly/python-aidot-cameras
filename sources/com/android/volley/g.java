package com.android.volley;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import androidx.annotation.VisibleForTesting;
import java.util.concurrent.BlockingQueue;

/* compiled from: NetworkDispatcher */
public class g extends Thread {
    private final BlockingQueue<i<?>> c;
    private final f d;
    private final a f;
    private final l q;
    private volatile boolean x = false;

    public g(BlockingQueue<i<?>> queue, f network, a cache, l delivery) {
        this.c = queue;
        this.d = network;
        this.f = cache;
        this.q = delivery;
    }

    public void e() {
        this.x = true;
        interrupt();
    }

    @TargetApi(14)
    private void a(i<?> request) {
        if (Build.VERSION.SDK_INT >= 14) {
            TrafficStats.setThreadStatsTag(request.getTrafficStatsTag());
        }
    }

    public void run() {
        Process.setThreadPriority(10);
        while (true) {
            try {
                c();
            } catch (InterruptedException e) {
                if (this.x) {
                    Thread.currentThread().interrupt();
                    return;
                }
                n.c("Ignoring spurious interrupt of NetworkDispatcher thread; use quit() to terminate it", new Object[0]);
            }
        }
    }

    private void c() {
        d((i) this.c.take());
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void d(i<?> request) {
        long startTimeMs = SystemClock.elapsedRealtime();
        request.sendEvent(3);
        try {
            request.addMarker("network-queue-take");
            if (request.isCanceled()) {
                request.finish("network-discard-cancelled");
                request.notifyListenerResponseNotUsable();
                request.sendEvent(4);
                return;
            }
            a(request);
            h networkResponse = this.d.a(request);
            request.addMarker("network-http-complete");
            if (!networkResponse.e || !request.hasHadResponseDelivered()) {
                Response<?> response = request.parseNetworkResponse(networkResponse);
                request.addMarker("network-parse-complete");
                if (request.shouldCache() && response.b != null) {
                    this.f.b(request.getCacheKey(), response.b);
                    request.addMarker("network-cache-written");
                }
                request.markDelivered();
                this.q.a(request, response);
                request.notifyListenerResponseReceived(response);
                request.sendEvent(4);
                return;
            }
            request.finish("not-modified");
            request.notifyListenerResponseNotUsable();
            request.sendEvent(4);
        } catch (VolleyError volleyError) {
            volleyError.setNetworkTimeMs(SystemClock.elapsedRealtime() - startTimeMs);
            b(request, volleyError);
            request.notifyListenerResponseNotUsable();
        } catch (Exception e) {
            n.d(e, "Unhandled exception %s", e.toString());
            VolleyError volleyError2 = new VolleyError((Throwable) e);
            volleyError2.setNetworkTimeMs(SystemClock.elapsedRealtime() - startTimeMs);
            this.q.c(request, volleyError2);
            request.notifyListenerResponseNotUsable();
        } catch (Throwable th) {
            request.sendEvent(4);
            throw th;
        }
    }

    private void b(i<?> request, VolleyError error) {
        this.q.c(request, request.parseNetworkError(error));
    }
}
