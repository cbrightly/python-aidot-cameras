package com.leedarson.log;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: LdsDebug */
public class f {
    private static boolean a = false;
    public static ChangeQuickRedirect changeQuickRedirect;

    /* compiled from: LdsDebug */
    public enum b {
        VERBOSE,
        INFO,
        DEBUG,
        WARING,
        ERROR,
        ASSERT;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    private static void f(b l, String header, String info) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{l, header, info}, (Object) null, changeQuickRedirect, true, 1148, new Class[]{b.class, cls, cls}, Void.TYPE).isSupported) {
            StackTraceElement[] es = Thread.currentThread().getStackTrace();
            String log = "<" + header + ">" + info + ("(" + es[4].getClassName() + "." + es[4].getMethodName() + "--" + es[4].getFileName() + "@" + es[4].getLineNumber() + ")");
            switch (a.a[l.ordinal()]) {
                case 1:
                    timber.log.a.g("WEBRTC").m(log, new Object[0]);
                    return;
                case 2:
                    timber.log.a.g("WEBRTC").h(log, new Object[0]);
                    return;
                case 3:
                    timber.log.a.g("WEBRTC").a(log, new Object[0]);
                    return;
                case 4:
                    timber.log.a.g("WEBRTC").n(log, new Object[0]);
                    return;
                case 5:
                    timber.log.a.g("WEBRTC").c(log, new Object[0]);
                    return;
                default:
                    timber.log.a.g("WEBRTC").c(log, new Object[0]);
                    return;
            }
        }
    }

    /* compiled from: LdsDebug */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.VERBOSE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.INFO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b.DEBUG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[b.WARING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[b.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public static void e(String info) {
        if (!PatchProxy.proxy(new Object[]{info}, (Object) null, changeQuickRedirect, true, 1150, new Class[]{String.class}, Void.TYPE).isSupported && a) {
            f(b.INFO, "", info);
        }
    }

    public static void a(String info) {
        if (!PatchProxy.proxy(new Object[]{info}, (Object) null, changeQuickRedirect, true, 1151, new Class[]{String.class}, Void.TYPE).isSupported && a) {
            f(b.DEBUG, "", info);
        }
    }

    public static void c(String info) {
        if (!PatchProxy.proxy(new Object[]{info}, (Object) null, changeQuickRedirect, true, 1153, new Class[]{String.class}, Void.TYPE).isSupported && a) {
            f(b.ERROR, "", info);
        }
    }

    public static void b(String header, String info) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{header, info}, (Object) null, changeQuickRedirect, true, 1156, clsArr, Void.TYPE).isSupported && a) {
            f(b.DEBUG, header, info);
        }
    }

    public static void d(String header, String info) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{header, info}, (Object) null, changeQuickRedirect, true, 1157, clsArr, Void.TYPE).isSupported && a) {
            f(b.ERROR, header, info);
        }
    }
}
