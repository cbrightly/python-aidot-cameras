package com.leedarson.base.webservice.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.c;
import com.leedarson.base.webservice.server.CoreService;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.b0;
import okhttp3.f;
import okhttp3.z;

/* compiled from: WebServiceUtil */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Intent a;
    private Context b;
    private boolean c;
    public boolean d;
    private int e;
    private int f;

    /* renamed from: com.leedarson.base.webservice.utils.b$b  reason: collision with other inner class name */
    /* compiled from: WebServiceUtil */
    public static class C0085b {
        /* access modifiers changed from: private */
        public static final b a = new b((a) null);
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    /* synthetic */ b(a x0) {
        this();
    }

    private b() {
        this.c = false;
        this.d = true;
        this.e = 10000;
        this.f = 11100;
    }

    public static synchronized b b() {
        synchronized (b.class) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 988, new Class[0], b.class);
            if (proxy.isSupported) {
                b bVar = (b) proxy.result;
                return bVar;
            }
            b a2 = C0085b.a;
            return a2;
        }
    }

    public Context a() {
        return this.b;
    }

    public void h(String message, String eventName) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{message, eventName}, this, changeQuickRedirect, false, 989, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            if (loggerService != null) {
                loggerService.reportELK(BaseApplication.b(), message, "info", TextUtils.isEmpty(eventName) ? "startHttpServer" : eventName);
            }
        }
    }

    public void j(Context context, boolean isRestart, String bzRef) {
        Object[] objArr = {context, new Byte(isRestart ? (byte) 1 : 0), bzRef};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 990, new Class[]{Context.class, Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            try {
                if (this.a == null) {
                    this.a = new Intent(context, CoreService.class);
                }
                this.b = context;
                this.a.putExtra("isRestart", isRestart);
                SharePreferenceUtils.setPrefBoolean(context, "isRestart", isRestart);
                if (!BaseApplication.d) {
                    context.startService(this.a);
                    if (loggerService != null) {
                        BaseApplication b2 = BaseApplication.b();
                        loggerService.reportELK(b2, "httpServer:触发启动(serverStart),bzReason=" + bzRef + ",isRestart=" + isRestart, "info", "startHttpServer");
                        return;
                    }
                    return;
                }
                timber.log.a.g("LdsCore").m("应用在后台不允许启动服务", new Object[0]);
                if (loggerService != null) {
                    BaseApplication b3 = BaseApplication.b();
                    loggerService.reportELK(b3, "httpServer:触发启动(失败，应用在后台不允许启动服务),bzReason=" + bzRef + ",isRestart=" + isRestart, "info", "startHttpServer");
                }
            } catch (Exception e2) {
                if (e2.toString().contains("BackgroundServiceStartNotAllowedException")) {
                    if (loggerService != null) {
                        loggerService.reportELK(BaseApplication.b(), "httpServer:触发启动(失败，应用在后台不允许启动服务) 尝试将应用销毁 - 待用户进来后可以快速恢复(防止卡在启动页)", "info", "startHttpServer");
                    }
                    c.h().f();
                }
                if (loggerService != null) {
                    BaseApplication b4 = BaseApplication.b();
                    loggerService.reportELK(b4, "httpServer:触发启动(异常),bzReason=" + bzRef + ",exception=" + e2.toString(), "info", "startHttpServer");
                }
                e2.printStackTrace();
            }
        }
    }

    public void k(Context context, String bzRef) {
        Class[] clsArr = {Context.class, String.class};
        if (!PatchProxy.proxy(new Object[]{context, bzRef}, this, changeQuickRedirect, false, 991, clsArr, Void.TYPE).isSupported) {
            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            try {
                Intent intent = this.a;
                if (intent != null) {
                    context.stopService(intent);
                    if (loggerService != null) {
                        BaseApplication b2 = BaseApplication.b();
                        loggerService.reportELK(b2, "httpServer:触发结束(正常),bzReason=" + bzRef, "info", "startHttpServer");
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (loggerService != null) {
                    if (BaseApplication.b().f != null && BaseApplication.b().f.isRunning()) {
                        BaseApplication.b().f.shutdown();
                    }
                    BaseApplication b3 = BaseApplication.b();
                    loggerService.reportELK(b3, "httpServer:触发结束(异常),bzReason=" + bzRef + ",exception=" + e2.toString(), "info", "startHttpServer");
                }
            }
        }
    }

    public boolean g(Context context, String ServiceName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context, ServiceName}, this, changeQuickRedirect, false, 992, new Class[]{Context.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (TextUtils.isEmpty(ServiceName)) {
                return false;
            }
            ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList) ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
            for (int i = 0; i < runningService.size(); i++) {
                if (runningService.get(i).service.getClassName().toString().equals(ServiceName)) {
                    return true;
                }
            }
            return false;
        } catch (SecurityException e2) {
            e2.printStackTrace();
        }
    }

    public int c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 993, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return new Random().nextInt((this.f - this.e) + 1) + this.e;
    }

    public void m(Context context, String url, f callback) {
        if (!PatchProxy.proxy(new Object[]{context, url, callback}, this, changeQuickRedirect, false, 994, new Class[]{Context.class, String.class, f.class}, Void.TYPE).isSupported) {
            z.a cbuilder = new z.a();
            try {
                CertificateFactory cAf = CertificateFactory.getInstance("X.509");
                InputStream inputStream = a().getAssets().open("840bb0b9a960c6003e1975fc3b164e4c");
                KeyStore caks = KeyStore.getInstance(KeyStore.getDefaultType());
                caks.load((InputStream) null, (char[]) null);
                caks.setCertificateEntry("ca-certificate", (X509Certificate) cAf.generateCertificate(inputStream));
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(caks);
                TrustManager[] trustManagers = tmf.getTrustManagers();
                if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                    throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
                }
                inputStream.close();
                SSLContext sslcontext = SSLContext.getInstance("TLSv1.2");
                sslcontext.init((KeyManager[]) null, tmf.getTrustManagers(), (SecureRandom) null);
                cbuilder.U(sslcontext.getSocketFactory(), (X509TrustManager) trustManagers[0]);
                cbuilder.M(new a());
                cbuilder.c().a(new b0.a().e().p(url).b()).o0(callback);
            } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
            } catch (KeyManagementException e3) {
                e3.printStackTrace();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
    }

    /* compiled from: WebServiceUtil */
    public class a implements HostnameVerifier {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    public boolean f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 995, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Context context = this.b;
        if (context != null && this.c && g(context, "com.leedarson.base.webservice.server.CoreService")) {
            return true;
        }
        return false;
    }

    public void l(boolean httpServerRunning) {
        this.c = httpServerRunning;
    }

    public void i(Context context, String bzRef) {
        if (!PatchProxy.proxy(new Object[]{context, bzRef}, this, changeQuickRedirect, false, 996, new Class[]{Context.class, String.class}, Void.TYPE).isSupported) {
            b().k(context, bzRef);
            b b2 = b();
            b2.j(context, false, "WebServiceUtil.restartServer bzRef=" + bzRef);
        }
    }

    public String d(String loadUrl) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{loadUrl}, this, changeQuickRedirect, false, 997, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Uri uri = Uri.parse(loadUrl);
        return NetworkManager.MOCK_SCHEME_HTTPS + uri.getHost() + ":" + uri.getPort() + "/?t=" + System.currentTimeMillis();
    }

    public String e(String loadUrl) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{loadUrl}, this, changeQuickRedirect, false, 998, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Uri uri = Uri.parse(loadUrl);
        return NetworkManager.MOCK_SCHEME_HTTPS + uri.getHost() + ":" + uri.getPort();
    }
}
