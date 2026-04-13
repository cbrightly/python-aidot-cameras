package com.android.volley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.a;
import com.android.volley.i;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/* compiled from: WaitingRequestManager */
public class o implements i.b {
    private final Map<String, List<i<?>>> a = new HashMap();
    private final l b;
    @Nullable
    private final j c = null;
    @Nullable
    private final b d;
    @Nullable
    private final BlockingQueue<i<?>> e;

    o(@NonNull b cacheDispatcher, @NonNull BlockingQueue<i<?>> networkQueue, l responseDelivery) {
        this.b = responseDelivery;
        this.d = cacheDispatcher;
        this.e = networkQueue;
    }

    public void a(i<?> request, k<?> response) {
        List<Request<?>> waitingRequests;
        a.C0017a aVar = response.b;
        if (aVar == null || aVar.a()) {
            b(request);
            return;
        }
        String cacheKey = request.getCacheKey();
        synchronized (this) {
            waitingRequests = this.a.remove(cacheKey);
        }
        if (waitingRequests != null) {
            if (n.b) {
                n.e("Releasing %d waiting requests for cacheKey=%s.", Integer.valueOf(waitingRequests.size()), cacheKey);
            }
            for (Request<?> waiting : waitingRequests) {
                this.b.a(waiting, response);
            }
        }
    }

    public synchronized void b(i<?> request) {
        BlockingQueue<i<?>> blockingQueue;
        String cacheKey = request.getCacheKey();
        List<Request<?>> waitingRequests = this.a.remove(cacheKey);
        if (waitingRequests != null && !waitingRequests.isEmpty()) {
            if (n.b) {
                n.e("%d waiting requests for cacheKey=%s; resend to network", Integer.valueOf(waitingRequests.size()), cacheKey);
            }
            Request<?> nextInLine = (i) waitingRequests.remove(0);
            this.a.put(cacheKey, waitingRequests);
            nextInLine.setNetworkRequestCompleteListener(this);
            j jVar = this.c;
            if (jVar != null) {
                jVar.f(nextInLine);
            } else if (!(this.d == null || (blockingQueue = this.e) == null)) {
                try {
                    blockingQueue.put(nextInLine);
                } catch (InterruptedException iex) {
                    n.c("Couldn't add request to queue. %s", iex.toString());
                    Thread.currentThread().interrupt();
                    this.d.d();
                }
            }
        }
        return;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003b, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0053, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean c(com.android.volley.i<?> r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            java.lang.String r0 = r7.getCacheKey()     // Catch:{ all -> 0x0054 }
            java.util.Map<java.lang.String, java.util.List<com.android.volley.i<?>>> r1 = r6.a     // Catch:{ all -> 0x0054 }
            boolean r1 = r1.containsKey(r0)     // Catch:{ all -> 0x0054 }
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x003c
            java.util.Map<java.lang.String, java.util.List<com.android.volley.i<?>>> r1 = r6.a     // Catch:{ all -> 0x0054 }
            java.lang.Object r1 = r1.get(r0)     // Catch:{ all -> 0x0054 }
            java.util.List r1 = (java.util.List) r1     // Catch:{ all -> 0x0054 }
            if (r1 != 0) goto L_0x001f
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ all -> 0x0054 }
            r4.<init>()     // Catch:{ all -> 0x0054 }
            r1 = r4
        L_0x001f:
            java.lang.String r4 = "waiting-for-response"
            r7.addMarker(r4)     // Catch:{ all -> 0x0054 }
            r1.add(r7)     // Catch:{ all -> 0x0054 }
            java.util.Map<java.lang.String, java.util.List<com.android.volley.i<?>>> r4 = r6.a     // Catch:{ all -> 0x0054 }
            r4.put(r0, r1)     // Catch:{ all -> 0x0054 }
            boolean r4 = com.android.volley.n.b     // Catch:{ all -> 0x0054 }
            if (r4 == 0) goto L_0x003a
            java.lang.String r4 = "Request for cacheKey=%s is in flight, putting on hold."
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ all -> 0x0054 }
            r5[r3] = r0     // Catch:{ all -> 0x0054 }
            com.android.volley.n.b(r4, r5)     // Catch:{ all -> 0x0054 }
        L_0x003a:
            monitor-exit(r6)
            return r2
        L_0x003c:
            java.util.Map<java.lang.String, java.util.List<com.android.volley.i<?>>> r1 = r6.a     // Catch:{ all -> 0x0054 }
            r4 = 0
            r1.put(r0, r4)     // Catch:{ all -> 0x0054 }
            r7.setNetworkRequestCompleteListener(r6)     // Catch:{ all -> 0x0054 }
            boolean r1 = com.android.volley.n.b     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x0052
            java.lang.String r1 = "new request, sending to network %s"
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0054 }
            r2[r3] = r0     // Catch:{ all -> 0x0054 }
            com.android.volley.n.b(r1, r2)     // Catch:{ all -> 0x0054 }
        L_0x0052:
            monitor-exit(r6)
            return r3
        L_0x0054:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.volley.o.c(com.android.volley.i):boolean");
    }
}
