package com.leedarson.log.mgr;

import com.leedarson.log.tracker.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;

/* compiled from: TrackerManager */
public class u {
    private static u a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<String, a> b = new HashMap<>();

    public static u d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 1310, new Class[0], u.class);
        if (proxy.isSupported) {
            return (u) proxy.result;
        }
        if (a == null) {
            synchronized (u.class) {
                if (a == null) {
                    a = new u();
                }
            }
        }
        return a;
    }

    public synchronized void b(a tracker) {
        if (!PatchProxy.proxy(new Object[]{tracker}, this, changeQuickRedirect, false, 1311, new Class[]{a.class}, Void.TYPE).isSupported) {
            if (this.b.containsKey(tracker.i())) {
                this.b.get(tracker.i()).l();
            }
            this.b.put(tracker.i(), tracker);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0066, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.lang.String r9, com.leedarson.log.tracker.BaseStepBean r10) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0067 }
            r2 = 0
            r1[r2] = r9     // Catch:{ all -> 0x0067 }
            r3 = 1
            r1[r3] = r10     // Catch:{ all -> 0x0067 }
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect     // Catch:{ all -> 0x0067 }
            r5 = 0
            r6 = 1312(0x520, float:1.839E-42)
            java.lang.Class[] r0 = new java.lang.Class[r0]     // Catch:{ all -> 0x0067 }
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            r0[r2] = r7     // Catch:{ all -> 0x0067 }
            java.lang.Class<com.leedarson.log.tracker.BaseStepBean> r2 = com.leedarson.log.tracker.BaseStepBean.class
            r0[r3] = r2     // Catch:{ all -> 0x0067 }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x0067 }
            r2 = r8
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0067 }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x0067 }
            if (r0 == 0) goto L_0x002a
            monitor-exit(r8)
            return
        L_0x002a:
            r0 = r8
            java.lang.String r1 = "TrackerManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x004e }
            r2.<init>()     // Catch:{ JSONException -> 0x004e }
            java.lang.String r3 = "Step traceId="
            r2.append(r3)     // Catch:{ JSONException -> 0x004e }
            r2.append(r9)     // Catch:{ JSONException -> 0x004e }
            java.lang.String r3 = "=="
            r2.append(r3)     // Catch:{ JSONException -> 0x004e }
            org.json.JSONObject r3 = r10.toJson()     // Catch:{ JSONException -> 0x004e }
            r2.append(r3)     // Catch:{ JSONException -> 0x004e }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x004e }
            com.leedarson.base.logger.a.a(r1, r2)     // Catch:{ JSONException -> 0x004e }
            goto L_0x0052
        L_0x004e:
            r1 = move-exception
            r1.printStackTrace()     // Catch:{ all -> 0x0067 }
        L_0x0052:
            java.util.HashMap<java.lang.String, com.leedarson.log.tracker.a> r1 = r0.b     // Catch:{ all -> 0x0067 }
            boolean r1 = r1.containsKey(r9)     // Catch:{ all -> 0x0067 }
            if (r1 == 0) goto L_0x0065
            java.util.HashMap<java.lang.String, com.leedarson.log.tracker.a> r1 = r0.b     // Catch:{ all -> 0x0067 }
            java.lang.Object r1 = r1.get(r9)     // Catch:{ all -> 0x0067 }
            com.leedarson.log.tracker.a r1 = (com.leedarson.log.tracker.a) r1     // Catch:{ all -> 0x0067 }
            r1.e(r10)     // Catch:{ all -> 0x0067 }
        L_0x0065:
            monitor-exit(r8)
            return
        L_0x0067:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.log.mgr.u.a(java.lang.String, com.leedarson.log.tracker.BaseStepBean):void");
    }

    public synchronized a c(String traceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{traceId}, this, changeQuickRedirect, false, 1313, new Class[]{String.class}, a.class);
        if (proxy.isSupported) {
            return (a) proxy.result;
        }
        if (!this.b.containsKey(traceId)) {
            return null;
        }
        return this.b.get(traceId);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x004f, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void e(java.lang.String r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x0050 }
            r2 = 0
            r1[r2] = r9     // Catch:{ all -> 0x0050 }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x0050 }
            r4 = 0
            r5 = 1314(0x522, float:1.841E-42)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x0050 }
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r2] = r0     // Catch:{ all -> 0x0050 }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x0050 }
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x0050 }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x0050 }
            if (r0 == 0) goto L_0x001f
            monitor-exit(r8)
            return
        L_0x001f:
            r0 = r8
            java.lang.String r1 = "TrackerManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0050 }
            r2.<init>()     // Catch:{ all -> 0x0050 }
            java.lang.String r3 = "report:"
            r2.append(r3)     // Catch:{ all -> 0x0050 }
            r2.append(r9)     // Catch:{ all -> 0x0050 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0050 }
            com.leedarson.base.logger.a.a(r1, r2)     // Catch:{ all -> 0x0050 }
            java.util.HashMap<java.lang.String, com.leedarson.log.tracker.a> r1 = r0.b     // Catch:{ all -> 0x0050 }
            boolean r1 = r1.containsKey(r9)     // Catch:{ all -> 0x0050 }
            if (r1 == 0) goto L_0x004e
            java.util.HashMap<java.lang.String, com.leedarson.log.tracker.a> r1 = r0.b     // Catch:{ all -> 0x0050 }
            java.lang.Object r1 = r1.get(r9)     // Catch:{ all -> 0x0050 }
            com.leedarson.log.tracker.a r1 = (com.leedarson.log.tracker.a) r1     // Catch:{ all -> 0x0050 }
            r1.l()     // Catch:{ all -> 0x0050 }
            java.util.HashMap<java.lang.String, com.leedarson.log.tracker.a> r1 = r0.b     // Catch:{ all -> 0x0050 }
            r1.remove(r9)     // Catch:{ all -> 0x0050 }
        L_0x004e:
            monitor-exit(r8)
            return
        L_0x0050:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.log.mgr.u.e(java.lang.String):void");
    }
}
