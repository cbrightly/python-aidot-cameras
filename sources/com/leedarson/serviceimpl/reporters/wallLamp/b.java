package com.leedarson.serviceimpl.reporters.wallLamp;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import meshsdk.MeshLog;

/* compiled from: EnergyModeReporterTaskManager */
public class b {
    private static b a = new b();
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<String, a> b = new HashMap<>();

    private b() {
    }

    public static b b() {
        return a;
    }

    public void a(String key, String macAddress, Object value, String event) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{key, macAddress, value, event}, this, changeQuickRedirect, false, 8621, new Class[]{cls, cls, Object.class, cls}, Void.TYPE).isSupported) {
            a task = new a();
            task.c = System.currentTimeMillis();
            task.b = System.currentTimeMillis();
            task.g = key;
            task.f = macAddress;
            task.h = value;
            task.e = event;
            this.b.put(key, task);
            task.b();
            d("添加task:" + task.c + ",macAddrss:" + macAddress + ",event:" + event + ",value:" + value);
        }
    }

    public a c(String meshAddr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{meshAddr}, this, changeQuickRedirect, false, 8622, new Class[]{String.class}, a.class);
        return proxy.isSupported ? (a) proxy.result : this.b.get(meshAddr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0026, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e(com.leedarson.serviceimpl.reporters.wallLamp.a.b r9, java.lang.String r10) {
        /*
            r8 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            r3 = 1
            r1[r3] = r10
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.serviceimpl.reporters.wallLamp.a$b> r0 = com.leedarson.serviceimpl.reporters.wallLamp.a.b.class
            r6[r2] = r0
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 8623(0x21af, float:1.2083E-41)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0026
            return
        L_0x0026:
            r0 = r8
            com.leedarson.serviceimpl.reporters.wallLamp.a r1 = r0.c(r10)
            if (r1 == 0) goto L_0x0037
            r1.a = r9
            r1.a()
            java.util.HashMap<java.lang.String, com.leedarson.serviceimpl.reporters.wallLamp.a> r2 = r0.b
            r2.remove(r10)
        L_0x0037:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.reporters.wallLamp.b.e(com.leedarson.serviceimpl.reporters.wallLamp.a$b, java.lang.String):void");
    }

    public static void d(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 8624, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i("EnergyModeReporterTaskManager=>" + log);
        }
    }
}
