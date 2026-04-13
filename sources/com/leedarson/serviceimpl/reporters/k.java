package com.leedarson.serviceimpl.reporters;

import com.leedarson.log.elk.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.MeshLog;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.util.MeshConstants;

/* compiled from: MeshJsonReporter */
public class k {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, (Object) null, changeQuickRedirect, true, 8556, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i("MeshJsonReporter:" + message);
            a.y(k.class).e(MeshConstants.TRACE_ID_MESHJSON).c(MeshDataManager.class.getSimpleName()).t("BleMesh").p(message).a().b();
        }
    }

    public static void b(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, (Object) null, changeQuickRedirect, true, 8557, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i("MeshJsonReporter:" + message);
        }
    }
}
