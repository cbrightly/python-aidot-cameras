package com.leedarson.serviceimpl.bledebug;

import android.util.Log;
import com.leedarson.bean.Constants;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: DebugLog */
public class a {
    public static boolean a = true;
    private static boolean b = false;
    public static String c = Constants.SERVICE_SIG_MESH;
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(String msg) {
        if (PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 6624, new Class[]{String.class}, Void.TYPE).isSupported || !a || msg == null) {
            return;
        }
        if (b) {
            Log.d(c, msg);
        } else {
            d(msg, 1, c);
        }
    }

    public static void c(String msg) {
        if (PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 6625, new Class[]{String.class}, Void.TYPE).isSupported || !a || msg == null) {
            return;
        }
        if (b) {
            Log.i(c, msg);
        } else {
            d(msg, 2, c);
        }
    }

    public static void b(String msg) {
        if (PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 6627, new Class[]{String.class}, Void.TYPE).isSupported || !a || msg == null) {
            return;
        }
        if (b) {
            Log.e(c, msg);
        } else {
            d(msg, 4, c);
        }
    }

    public static void d(String msg, int level, String tag) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{msg, new Integer(level), tag}, (Object) null, changeQuickRedirect, true, 6629, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            String msg2 = "GattTest-" + msg;
            switch (level) {
                case 0:
                    timber.log.a.g(tag).m(msg2, new Object[0]);
                    return;
                case 1:
                    timber.log.a.g(tag).a(msg2, new Object[0]);
                    return;
                case 2:
                    timber.log.a.g(tag).h(msg2, new Object[0]);
                    return;
                case 3:
                    timber.log.a.g(tag).n(msg2, new Object[0]);
                    return;
                case 4:
                    timber.log.a.g(tag).c(msg2, new Object[0]);
                    return;
                default:
                    return;
            }
        }
    }
}
