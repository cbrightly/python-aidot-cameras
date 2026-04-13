package com.telink.ble.mesh.util;

import android.os.Process;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;
import java.util.List;
import meshsdk.util.ProcedureCollector;
import timber.log.a;

public class MeshLogger {
    public static List<LogInfo> a = new ArrayList();
    private static boolean b = true;
    private static boolean c = false;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void d(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, (Object) null, changeQuickRedirect, true, 13399, new Class[]{String.class}, Void.TYPE).isSupported) {
            f(logMessage, "SIG-Mesh");
        }
    }

    public static void f(String logMessage, String tag) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{logMessage, tag}, (Object) null, changeQuickRedirect, true, 13400, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            g(logMessage, tag, 1);
        }
    }

    public static void e(String logMessage, int level) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(level)}, (Object) null, changeQuickRedirect, true, 13401, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            g(logMessage, "SIG-Mesh", level);
        }
    }

    public static void h(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, (Object) null, changeQuickRedirect, true, 13402, new Class[]{String.class}, Void.TYPE).isSupported) {
            e(logMessage, 0);
        }
    }

    public static void a(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, (Object) null, changeQuickRedirect, true, 13403, new Class[]{String.class}, Void.TYPE).isSupported) {
            e(logMessage, 1);
        }
    }

    public static void c(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, (Object) null, changeQuickRedirect, true, 13404, new Class[]{String.class}, Void.TYPE).isSupported) {
            e(logMessage, 2);
        }
    }

    public static void b(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, (Object) null, changeQuickRedirect, true, 13406, new Class[]{String.class}, Void.TYPE).isSupported) {
            e(logMessage, 4);
        }
    }

    public static void g(String logMessage, String tag, int level) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{logMessage, tag, new Integer(level)}, (Object) null, changeQuickRedirect, true, 13407, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            if (b) {
                switch (level) {
                    case 0:
                        a.g(tag).m(logMessage, new Object[0]);
                        break;
                    case 2:
                        a.g(tag).h(logMessage, new Object[0]);
                        break;
                    case 3:
                        a.g(tag).n(logMessage, new Object[0]);
                        break;
                    case 4:
                        a.g(tag).c(logMessage, new Object[0]);
                        break;
                    default:
                        a.g(tag).a(logMessage, new Object[0]);
                        break;
                }
            }
            if (level >= ProcedureCollector.getLevel()) {
                ProcedureCollector.addLog(logMessage, tag, (long) Process.myTid());
            }
        }
    }
}
