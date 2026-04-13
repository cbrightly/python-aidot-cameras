package com.leedarson.base.webservice.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.Nullable;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.webservice.utils.b;
import com.leedarson.secret.JNIUtil;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import com.yanzhenjie.andserver.d;
import com.yanzhenjie.andserver.e;
import java.net.InetAddress;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManager;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class CoreService extends Service {
    private static String c = "127.0.0.1";
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d = -1;
    public static int f = -1;
    public static int q = 0;
    public static int x = 1;
    public static int y = -2;
    private String a1;
    /* access modifiers changed from: private */
    public String a2 = "CoreService";
    private int p0;
    private boolean p1 = false;
    private String p2 = "";
    com.leedarson.base.webservice.listerners.a p3 = null;
    private e z;

    public void onCreate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 957, new Class[0], Void.TYPE).isSupported) {
            d("CoreService.onCreate");
        }
    }

    private void d(String desc) {
        if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 958, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.base.webservice.listerners.a aVar = this.p3;
            if (aVar != null) {
                aVar.b(false);
            }
            com.leedarson.base.webservice.listerners.a aVar2 = new com.leedarson.base.webservice.listerners.a();
            this.p3 = aVar2;
            aVar2.b(true);
            int prefInt = SharePreferenceUtils.getPrefInt(BaseApplication.b(), "serverport", 9999);
            this.p0 = prefInt;
            this.p3.d(prefInt);
            this.p3.f(desc);
            this.p2 = SharePreferenceUtils.getPrefString(BaseApplication.b(), "notification_house_id", "");
            boolean reload_main_webview = SharePreferenceUtils.getPrefBoolean(BaseApplication.b(), "reload_main_webview", false);
            String _urlParamLang = "lang=" + SharePreferenceUtils.getPrefString(BaseApplication.b(), IjkMediaMeta.IJKM_KEY_LANGUAGE, "");
            this.a1 = String.format(Locale.US, "https://%s:%d/", new Object[]{c, Integer.valueOf(this.p0)});
            if (reload_main_webview) {
                this.a1 = b.b().d(this.a1) + "&" + _urlParamLang;
            } else if (!TextUtils.isEmpty(this.p2)) {
                this.a1 += "?houseId=" + this.p2 + "&" + _urlParamLang;
            } else {
                this.a1 += "?" + _urlParamLang;
            }
            this.p3.c(this.a1);
            timber.log.a.g("LdsCore").h("lang---->CoreService onCreate loadUrl: " + this.a1 + "isRestart=" + this.p1, new Object[0]);
            try {
                InetAddress addr = InetAddress.getByName(c);
                timber.log.a.g("LdsCore").h("AndServer ip: " + addr, new Object[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            com.leedarson.base.manager.a.g().i("startHttpServer", "");
            if (BaseApplication.b().f != null && BaseApplication.b().f.isRunning()) {
                BaseApplication.b().f.shutdown();
            }
            d = q;
            this.z = com.yanzhenjie.andserver.a.a(this).e(this.p0).a(10, TimeUnit.SECONDS).d(this.p3).c(new a()).b(c()).build();
            BaseApplication.b().f = this.z;
            timber.log.a.g("CoreService").h("CoreService onCreate mServer: " + this.z.isRunning(), new Object[0]);
        }
    }

    public class a implements d {
        public static ChangeQuickRedirect changeQuickRedirect;

        public a() {
        }

        public void a(SSLServerSocket sSLServerSocket) {
            if (!PatchProxy.proxy(new Object[]{sSLServerSocket}, this, changeQuickRedirect, false, 966, new Class[]{SSLServerSocket.class}, Void.TYPE).isSupported) {
                timber.log.a.g(CoreService.this.a2).c("====sslSocketInitializer onCreated: ", new Object[0]);
            }
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        PushAutoTrackHelper.onServiceStartCommand(this, intent, i, i2);
        Object[] objArr = {intent, new Integer(i), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 959, new Class[]{Intent.class, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (intent != null) {
            this.p1 = intent.getBooleanExtra("isRestart", false);
        } else {
            this.p1 = SharePreferenceUtils.getPrefBoolean(BaseApplication.b(), "isRestart", false);
            SharePreferenceUtils.setPrefInt(this, "restartCount", 0);
        }
        if (this.p1) {
            com.leedarson.base.webservice.listerners.a aVar = this.p3;
            if (aVar != null) {
                aVar.b(false);
            }
            e eVar = this.z;
            if (eVar != null) {
                eVar.shutdown();
            }
            d("Core.onStartCommand  isRestart=" + this.p1);
        }
        this.p3.e(this.p1);
        e();
        return 1;
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 960, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("LdsCore").h("CoreService onDestroy", new Object[0]);
            super.onDestroy();
            f();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void e() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 961(0x3c1, float:1.347E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.yanzhenjie.andserver.e r1 = r0.z
            if (r1 == 0) goto L_0x0036
            boolean r1 = r1.isRunning()
            if (r1 == 0) goto L_0x0031
            org.greenrobot.eventbus.c r1 = org.greenrobot.eventbus.c.c()
            com.leedarson.base.webservice.event.ServerStatusEvent r2 = new com.leedarson.base.webservice.event.ServerStatusEvent
            java.lang.String r3 = r0.a1
            r4 = 1
            r2.<init>(r3, r4)
            r1.l(r2)
            goto L_0x0036
        L_0x0031:
            com.yanzhenjie.andserver.e r1 = r0.z
            r1.a()
        L_0x0036:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.webservice.server.CoreService.e():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 962(0x3c2, float:1.348E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.yanzhenjie.andserver.e r1 = r0.z
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isRunning()
            if (r1 == 0) goto L_0x0026
            com.yanzhenjie.andserver.e r1 = r0.z
            r1.shutdown()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.webservice.server.CoreService.f():void");
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    private SSLContext c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 963, new Class[0], SSLContext.class);
        if (proxy.isSupported) {
            return (SSLContext) proxy.result;
        }
        SSLContext sslcontext = null;
        String data = JNIUtil.getInstance().getStr3();
        try {
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(getApplicationContext().getAssets().open("189bdf8247d084d4dacaacb028702616"), data.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, data.toCharArray());
            sslcontext = SSLContext.getInstance("TLSv1.2");
            sslcontext.init(kmf.getKeyManagers(), (TrustManager[]) null, (SecureRandom) null);
            return sslcontext;
        } catch (Exception e) {
            e.printStackTrace();
            return sslcontext;
        }
    }

    public static void a(String bzRef) {
        if (!PatchProxy.proxy(new Object[]{bzRef}, (Object) null, changeQuickRedirect, true, 964, new Class[]{String.class}, Void.TYPE).isSupported) {
            int newAndServerPort = b.b().c();
            int oldPort = SharePreferenceUtils.getPrefInt(BaseApplication.b(), "serverport", 9999);
            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            if (loggerService != null) {
                BaseApplication b = BaseApplication.b();
                loggerService.reportELK(b, "httpServer:更新端口,bzReason=" + bzRef + " 原端口：" + oldPort + ",newPort=" + newAndServerPort, "info", "startHttpServer");
            }
            Log.e("updateServerPort", "更新andServerPort=" + newAndServerPort + ",bzRef=" + bzRef);
            SharePreferenceUtils.setPrefInt(BaseApplication.b(), "serverport", newAndServerPort);
        }
    }
}
