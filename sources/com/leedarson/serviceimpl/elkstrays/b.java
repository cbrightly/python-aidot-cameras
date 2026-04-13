package com.leedarson.serviceimpl.elkstrays;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import meshsdk.MeshLog;

/* compiled from: ConnectMeshNetWorkReporter */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, (Object) null, changeQuickRedirect, true, 7892, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.d("auto_connect_mesh:" + message);
        }
    }

    public static void c(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, (Object) null, changeQuickRedirect, true, 7893, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.w("auto_connect_mesh:" + message);
        }
    }

    public static void b(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, (Object) null, changeQuickRedirect, true, 7894, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.e("auto_connect_mesh:" + message);
        }
    }
}
