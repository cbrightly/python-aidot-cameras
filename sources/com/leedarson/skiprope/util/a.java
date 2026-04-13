package com.leedarson.skiprope.util;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: SkipRopeLog */
public class a {
    public static boolean a = true;
    private static boolean b = false;
    public static String c = "SkipRope";
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(String msg) {
        if (PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 9628, new Class[]{String.class}, Void.TYPE).isSupported || !a || msg == null) {
            return;
        }
        if (b) {
            Log.d(c, msg);
        } else {
            d(msg, 1, c);
        }
    }

    public static void c(String msg) {
        if (PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 9629, new Class[]{String.class}, Void.TYPE).isSupported || !a || msg == null) {
            return;
        }
        if (b) {
            Log.i(c, msg);
        } else {
            d(msg, 2, c);
        }
    }

    public static void f(String msg) {
        if (PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 9630, new Class[]{String.class}, Void.TYPE).isSupported || !a || msg == null) {
            return;
        }
        if (b) {
            Log.w(c, msg);
        } else {
            d(msg, 3, c);
        }
    }

    public static void b(String msg) {
        if (PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 9631, new Class[]{String.class}, Void.TYPE).isSupported || !a || msg == null) {
            return;
        }
        if (b) {
            Log.e(c, msg);
        } else {
            d(msg, 4, c);
        }
    }

    public static void e(String msg) {
        if (PatchProxy.proxy(new Object[]{msg}, (Object) null, changeQuickRedirect, true, 9632, new Class[]{String.class}, Void.TYPE).isSupported || !a || msg == null) {
            return;
        }
        if (b) {
            Log.v(c, msg);
        } else {
            d(msg, 0, c);
        }
    }

    public static void d(String msg, int level, String tag) {
        Class<String> cls = String.class;
        Object[] objArr = {msg, new Integer(level), tag};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9633, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            switch (level) {
                case 0:
                    timber.log.a.g(tag).m(msg, new Object[0]);
                    return;
                case 1:
                    timber.log.a.g(tag).a(msg, new Object[0]);
                    return;
                case 2:
                    timber.log.a.g(tag).h(msg, new Object[0]);
                    return;
                case 3:
                    timber.log.a.g(tag).n(msg, new Object[0]);
                    return;
                case 4:
                    timber.log.a.g(tag).c(msg, new Object[0]);
                    return;
                default:
                    return;
            }
        }
    }
}
