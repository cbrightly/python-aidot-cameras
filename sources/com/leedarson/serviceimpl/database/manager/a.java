package com.leedarson.serviceimpl.database.manager;

import android.content.Context;
import com.leedarson.secret.JNIUtil;
import com.leedarson.serviceimpl.database.manager.b;
import com.leedarson.serviceinterface.LoggerService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: DBManager */
public class a {
    public static String a = "appdb";
    private static a b;
    private static c c;
    public static ChangeQuickRedirect changeQuickRedirect;
    static LoggerService d;

    private a() {
    }

    public static synchronized a b(Context context) {
        synchronized (a.class) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7851, new Class[]{Context.class}, a.class);
            if (proxy.isSupported) {
                a aVar = (a) proxy.result;
                return aVar;
            }
            if (b == null) {
                d = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
                c(context);
                b = new a();
            }
            a aVar2 = b;
            return aVar2;
        }
    }

    private static void c(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7852, new Class[]{Context.class}, Void.TYPE).isSupported) {
            String pwd = "";
            try {
                b.a helper = new b.a(context, a + "-encrypted");
                pwd = JNIUtil.getInstance().getStr6();
                c = new b(helper.getEncryptedWritableDb(pwd)).a();
            } catch (Exception e) {
                LoggerService loggerService = d;
                if (loggerService != null) {
                    loggerService.reportELK(context, "db pwd:" + pwd + ",db init fail:" + e.getMessage(), "error", "initGreenDao");
                }
            }
        }
    }

    public c a() {
        return c;
    }
}
