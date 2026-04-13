package com.leedarson.smartcamera.kvswebrtc;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* compiled from: ExecutorServiceWrap */
public class d0 {
    public static ChangeQuickRedirect changeQuickRedirect;
    ExecutorService a;
    private List<Future> b = new ArrayList();

    public d0(ExecutorService singleExecutor) {
        this.a = singleExecutor;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0034, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.concurrent.Future<?> b(java.lang.Runnable r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0035 }
            r2 = 0
            r1[r2] = r9     // Catch:{ all -> 0x0035 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x0035 }
            r4 = 0
            r5 = 9699(0x25e3, float:1.3591E-41)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x0035 }
            java.lang.Class<java.lang.Runnable> r0 = java.lang.Runnable.class
            r6[r2] = r0     // Catch:{ all -> 0x0035 }
            java.lang.Class<java.util.concurrent.Future> r7 = java.util.concurrent.Future.class
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0035 }
            boolean r1 = r0.isSupported     // Catch:{ all -> 0x0035 }
            if (r1 == 0) goto L_0x0023
            java.lang.Object r9 = r0.result     // Catch:{ all -> 0x0035 }
            java.util.concurrent.Future r9 = (java.util.concurrent.Future) r9     // Catch:{ all -> 0x0035 }
            monitor-exit(r8)
            return r9
        L_0x0023:
            r0 = r8
            r1 = 0
            java.util.concurrent.ExecutorService r2 = r0.a     // Catch:{ all -> 0x0035 }
            if (r2 == 0) goto L_0x0033
            java.util.concurrent.Future r2 = r2.submit(r9)     // Catch:{ all -> 0x0035 }
            r1 = r2
            java.util.List<java.util.concurrent.Future> r2 = r0.b     // Catch:{ all -> 0x0035 }
            r2.add(r1)     // Catch:{ all -> 0x0035 }
        L_0x0033:
            monitor-exit(r8)
            return r1
        L_0x0035:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.smartcamera.kvswebrtc.d0.b(java.lang.Runnable):java.util.concurrent.Future");
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9700, new Class[0], Void.TYPE).isSupported) {
            Log.d("ExecutorServiceWrap", "cancelAllTask: ");
            List<Future> list = this.b;
            if (list != null) {
                for (Future future : list) {
                    if (future != null && !future.isCancelled()) {
                        future.cancel(true);
                    }
                }
                this.b.clear();
            }
        }
    }
}
