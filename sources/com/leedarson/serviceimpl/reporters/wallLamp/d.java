package com.leedarson.serviceimpl.reporters.wallLamp;

import com.leedarson.serviceimpl.reporters.wallLamp.c;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import meshsdk.MeshLog;

/* compiled from: SecurityAlarmReporterTaskManager */
public class d {
    private static d a = new d();
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<String, c> b = new HashMap<>();

    private d() {
    }

    public static d b() {
        return a;
    }

    public void a(String key, String macAddress, Object value, String event) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{key, macAddress, value, event}, this, changeQuickRedirect, false, 8631, new Class[]{cls, cls, Object.class, cls}, Void.TYPE).isSupported) {
            c task = new c();
            task.c = System.currentTimeMillis();
            task.b = System.currentTimeMillis();
            task.g = key;
            task.f = macAddress;
            task.h = value;
            task.e = event;
            this.b.put(key, task);
            task.b();
            d("添加task:" + task.c + ",key:" + key + ",macAddrss:" + macAddress + ",event:" + event + ",value:" + value);
        }
    }

    public c c(String meshAddr) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{meshAddr}, this, changeQuickRedirect, false, 8632, new Class[]{String.class}, c.class);
        return proxy.isSupported ? (c) proxy.result : this.b.get(meshAddr);
    }

    public void e(c.b code, String meshAddr) {
        Class[] clsArr = {c.b.class, String.class};
        if (!PatchProxy.proxy(new Object[]{code, meshAddr}, this, changeQuickRedirect, false, 8633, clsArr, Void.TYPE).isSupported) {
            c task = c(meshAddr);
            if (task != null) {
                task.a = code;
                task.a();
                this.b.remove(meshAddr);
                return;
            }
            d("task empty,meshAddr:" + meshAddr);
        }
    }

    public static void d(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, (Object) null, changeQuickRedirect, true, 8634, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i("SecurityAlarmReporterTaskManager=>" + log);
        }
    }
}
