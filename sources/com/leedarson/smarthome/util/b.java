package com.leedarson.smarthome.util;

import com.leedarson.base.application.BaseApplication;
import com.leedarson.serviceinterface.LdsCrashEngineReportService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import timber.log.a;

/* compiled from: LdsCrashServiceWraper */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;

    static /* synthetic */ void b(String x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 10745, new Class[]{String.class}, Void.TYPE).isSupported) {
            a(x0);
        }
    }

    public static void c(BaseApplication baseApplication, String buglyVersion) {
        Class[] clsArr = {BaseApplication.class, String.class};
        if (!PatchProxy.proxy(new Object[]{baseApplication, buglyVersion}, (Object) null, changeQuickRedirect, true, 10740, clsArr, Void.TYPE).isSupported) {
            d("/bugly/crashreport/bugly", "bugly", buglyVersion);
            d("/firebase/crashreport/firebase", "firebasecrash", buglyVersion);
        }
    }

    /* compiled from: LdsCrashServiceWraper */
    public class a implements com.alibaba.android.arouter.facade.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;

        a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public void c(com.alibaba.android.arouter.facade.a postcard) {
            if (!PatchProxy.proxy(new Object[]{postcard}, this, changeQuickRedirect, false, 10746, new Class[]{com.alibaba.android.arouter.facade.a.class}, Void.TYPE).isSupported) {
                b.b(this.a + " crash init");
                ((LdsCrashEngineReportService) postcard.C()).initSdk(BaseApplication.b(), this.b);
                b.b(this.a + " crash inited");
            }
        }

        public void a(com.alibaba.android.arouter.facade.a aVar) {
            if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 10747, new Class[]{com.alibaba.android.arouter.facade.a.class}, Void.TYPE).isSupported) {
                b.b(this.a + " crash service not found");
            }
        }

        public void d(com.alibaba.android.arouter.facade.a postcard) {
        }

        public void b(com.alibaba.android.arouter.facade.a postcard) {
        }
    }

    private static void d(String path, String ref, String buglyVersion) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{path, ref, buglyVersion}, (Object) null, changeQuickRedirect, true, 10741, clsArr, Void.TYPE).isSupported) {
            com.alibaba.android.arouter.launcher.a.c().a(path).E(BaseApplication.b(), new a(ref, buglyVersion));
        }
    }

    public static void e() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 10742, new Class[0], Void.TYPE).isSupported) {
            com.alibaba.android.arouter.launcher.a.c().a("/bugly/crashreport/bugly").E(BaseApplication.b(), new C0184b());
            com.alibaba.android.arouter.launcher.a.c().a("/firebase/crashreport/firebase").E(BaseApplication.b(), new c());
        }
    }

    /* renamed from: com.leedarson.smarthome.util.b$b  reason: collision with other inner class name */
    /* compiled from: LdsCrashServiceWraper */
    public class C0184b implements com.alibaba.android.arouter.facade.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0184b() {
        }

        public void c(com.alibaba.android.arouter.facade.a postcard) {
            if (!PatchProxy.proxy(new Object[]{postcard}, this, changeQuickRedirect, false, 10748, new Class[]{com.alibaba.android.arouter.facade.a.class}, Void.TYPE).isSupported) {
                b.b("bugly crash init updateinfo");
                ((LdsCrashEngineReportService) postcard.C()).updateUserInfo();
                b.b("bugly crash inited  updateinfo");
            }
        }

        public void a(com.alibaba.android.arouter.facade.a aVar) {
            if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 10749, new Class[]{com.alibaba.android.arouter.facade.a.class}, Void.TYPE).isSupported) {
                b.b("bugly crash service not found  updateinfo");
            }
        }

        public void d(com.alibaba.android.arouter.facade.a postcard) {
        }

        public void b(com.alibaba.android.arouter.facade.a postcard) {
        }
    }

    /* compiled from: LdsCrashServiceWraper */
    public class c implements com.alibaba.android.arouter.facade.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void c(com.alibaba.android.arouter.facade.a postcard) {
            if (!PatchProxy.proxy(new Object[]{postcard}, this, changeQuickRedirect, false, 10750, new Class[]{com.alibaba.android.arouter.facade.a.class}, Void.TYPE).isSupported) {
                b.b("firebase crash init updateinfo");
                ((LdsCrashEngineReportService) postcard.C()).updateUserInfo();
                b.b("firebase crash inited updateinfo");
            }
        }

        public void a(com.alibaba.android.arouter.facade.a aVar) {
            if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 10751, new Class[]{com.alibaba.android.arouter.facade.a.class}, Void.TYPE).isSupported) {
                b.b("firebase crash service not found updateinfo");
            }
        }

        public void d(com.alibaba.android.arouter.facade.a postcard) {
        }

        public void b(com.alibaba.android.arouter.facade.a postcard) {
        }
    }

    private static void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, (Object) null, changeQuickRedirect, true, 10744, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("LdsCrashServiceWraper");
            g.h("[CrashEngineInit] " + message, new Object[0]);
        }
    }
}
