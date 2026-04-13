package com.leedarson.serviceimpl.reporters.ota;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import meshsdk.MeshLog;

/* compiled from: OTAReporterTaskManager */
public class b {
    private static b a = new b();
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<String, a> b = new HashMap<>();

    private b() {
    }

    public static b b() {
        return a;
    }

    public void a(String str, String str2, String currentVersion, String targetVersion, Object obj, String str3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, currentVersion, targetVersion, obj, str3}, this, changeQuickRedirect, false, 8601, new Class[]{cls, cls, cls, cls, Object.class, cls}, Void.TYPE).isSupported) {
            String macAddress = str2;
            String event = str3;
            String key = str;
            Object value = obj;
            a task = new a();
            task.b = System.currentTimeMillis();
            task.c = System.currentTimeMillis();
            task.d = key;
            task.g = macAddress;
            task.j = currentVersion;
            task.k = targetVersion;
            task.e = value;
            task.f = event;
            this.b.put(key, task);
            task.b();
            d("添加task:" + task.b + ",macAddrss:" + macAddress + ",event:" + event + ",value:" + value);
        }
    }

    public a c(String meshAddr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{meshAddr}, this, changeQuickRedirect, false, 8602, new Class[]{String.class}, a.class);
        return proxy.isSupported ? (a) proxy.result : this.b.get(meshAddr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x002b, code lost:
        r0 = r9;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void e(com.leedarson.serviceimpl.reporters.ota.a.b r10, java.lang.String r11, java.lang.String r12) {
        /*
            r9 = this;
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r1 = 3
            java.lang.Object[] r2 = new java.lang.Object[r1]
            r3 = 0
            r2[r3] = r10
            r4 = 1
            r2[r4] = r11
            r5 = 2
            r2[r5] = r12
            com.meituan.robust.ChangeQuickRedirect r6 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class<com.leedarson.serviceimpl.reporters.ota.a$b> r1 = com.leedarson.serviceimpl.reporters.ota.a.b.class
            r7[r3] = r1
            r7[r4] = r0
            r7[r5] = r0
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r0 = 8603(0x219b, float:1.2055E-41)
            r3 = r9
            r4 = r6
            r6 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x002b
            return
        L_0x002b:
            r0 = r9
            com.leedarson.serviceimpl.reporters.ota.a r1 = r0.c(r12)
            if (r1 == 0) goto L_0x003e
            r1.h = r10
            r1.i = r11
            r1.a()
            java.util.HashMap<java.lang.String, com.leedarson.serviceimpl.reporters.ota.a> r2 = r0.b
            r2.remove(r12)
        L_0x003e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.reporters.ota.b.e(com.leedarson.serviceimpl.reporters.ota.a$b, java.lang.String, java.lang.String):void");
    }

    public static void d(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 8604, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i("OTAReporterTaskManager=>" + log);
        }
    }
}
