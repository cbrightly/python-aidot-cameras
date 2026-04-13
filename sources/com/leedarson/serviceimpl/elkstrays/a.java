package com.leedarson.serviceimpl.elkstrays;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.HashMap;

/* compiled from: BleMeshConfigStraysReport */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(HashMap<String, Object> datas) {
        if (!PatchProxy.proxy(new Object[]{datas}, (Object) null, changeQuickRedirect, true, 7890, new Class[]{HashMap.class}, Void.TYPE).isSupported) {
            b(datas, (String) null);
        }
    }

    public static void b(HashMap<String, Object> datas, String traceId) {
        if (!PatchProxy.proxy(new Object[]{datas, traceId}, (Object) null, changeQuickRedirect, true, 7891, new Class[]{HashMap.class, String.class}, Void.TYPE).isSupported) {
            com.leedarson.log.elk.a.y(a.class).t("LdsBleMesh").x(traceId != null ? traceId : "BleMesh.Config.Changed").o("info").q(datas).a().b();
        }
    }
}
