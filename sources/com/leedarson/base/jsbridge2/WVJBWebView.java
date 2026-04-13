package com.leedarson.base.jsbridge2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.Keep;
import androidx.appcompat.app.AlertDialog;
import com.github.druk.dnssd.NSType;
import com.leedarson.base.utils.t;
import com.leedarson.base.utils.v;
import com.leedarson.base.utils.w;
import com.leedarson.base.webservice.event.ServerStatusEvent;
import com.leedarson.serviceinterface.AnalyticsService;
import com.leedarson.serviceinterface.JsbridgeService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.luck.picture.lib.camera.CustomCameraView;
import com.luck.picture.lib.compress.Checker;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.z;
import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class WVJBWebView extends WebView {
    public static z c;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public int A4 = 0;
    /* access modifiers changed from: private */
    public ArrayList<m> B4 = null;
    private Map<String, n> C4 = null;
    /* access modifiers changed from: private */
    public Map<String, l> D4 = null;
    private long E4 = 0;
    /* access modifiers changed from: private */
    public boolean F4 = true;
    WebChromeClient G4;
    WebViewClient H4;
    private WebChromeClient I4 = new f();
    private WebViewClient J4 = new g();
    /* access modifiers changed from: private */
    public com.leedarson.base.views.g a1;
    private boolean a2 = false;
    private String d;
    j f = null;
    /* access modifiers changed from: private */
    public String p0 = null;
    /* access modifiers changed from: private */
    public boolean p1 = false;
    private int p2;
    private int p3;
    /* access modifiers changed from: private */
    public String p4 = "Home";
    /* access modifiers changed from: private */
    public i q = null;
    /* access modifiers changed from: private */
    public boolean x = false;
    /* access modifiers changed from: private */
    public String y = "";
    private String z = "";
    private boolean z4 = true;

    public interface i {
        boolean onClose();
    }

    public interface l<T, R> {
        void a(T t, n<R> nVar);
    }

    public interface n<T> {
        void onResult(T t);
    }

    static /* synthetic */ void c(WVJBWebView x0, String x1) {
        Class[] clsArr = {WVJBWebView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, NSType.TKEY, clsArr, Void.TYPE).isSupported) {
            x0.b(x1);
        }
    }

    static /* synthetic */ void d(WVJBWebView x0, String x1) {
        Class[] clsArr = {WVJBWebView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 250, clsArr, Void.TYPE).isSupported) {
            x0.H(x1);
        }
    }

    static /* synthetic */ void p(WVJBWebView x0, SslErrorHandler x1, String x2) {
        Class[] clsArr = {WVJBWebView.class, SslErrorHandler.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 255, clsArr, Void.TYPE).isSupported) {
            x0.P(x1, x2);
        }
    }

    static /* synthetic */ void q(WVJBWebView x0, String x1, Map x2) {
        Class[] clsArr = {WVJBWebView.class, String.class, Map.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, NSType.IXFR, clsArr, Void.TYPE).isSupported) {
            x0.I(x1, x2);
        }
    }

    static /* synthetic */ void r(WVJBWebView x0, String x1) {
        Class[] clsArr = {WVJBWebView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, NSType.AXFR, clsArr, Void.TYPE).isSupported) {
            x0.F(x1);
        }
    }

    static /* synthetic */ void s(WVJBWebView x0, m x1) {
        Class[] clsArr = {WVJBWebView.class, m.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, NSType.MAILB, clsArr, Void.TYPE).isSupported) {
            x0.C(x1);
        }
    }

    static /* synthetic */ void v(WVJBWebView x0, String x1, ValueCallback x2) {
        Class[] clsArr = {WVJBWebView.class, String.class, ValueCallback.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 254, clsArr, Void.TYPE).isSupported) {
            super.evaluateJavascript(x1, x2);
        }
    }

    public void setWebCacheStrategy(int strategyFlag) {
        this.A4 |= strategyFlag;
    }

    public boolean G() {
        return this.z4;
    }

    public void setVisibleUserHint(boolean visibleUserHint) {
        this.z4 = visibleUserHint;
    }

    public String getAliasKey() {
        return this.p4;
    }

    public void setAliasKey(String aliasKey) {
        this.p4 = aliasKey;
    }

    public WVJBWebView(Context context) {
        super(context);
        init();
    }

    public WVJBWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WVJBWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setVisible(boolean visible) {
        this.p1 = visible;
    }

    public static class j extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;
        WeakReference<WVJBWebView> a;

        j(WVJBWebView wvjbWebView) {
            super(Looper.getMainLooper());
            this.a = new WeakReference<>(wvjbWebView);
        }

        public void handleMessage(Message msg) {
            WVJBWebView wvjbWebView;
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 310, new Class[]{Message.class}, Void.TYPE).isSupported && (wvjbWebView = (WVJBWebView) this.a.get()) != null) {
                switch (msg.what) {
                    case 1:
                        WVJBWebView.c(wvjbWebView, (String) msg.obj);
                        return;
                    case 2:
                        WVJBWebView.d(wvjbWebView, (String) msg.obj);
                        return;
                    case 3:
                        k info = (k) msg.obj;
                        WVJBWebView.q(wvjbWebView, info.a, info.b);
                        return;
                    case 4:
                        WVJBWebView.r(wvjbWebView, (String) msg.obj);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public static class k {
        String a;
        Map<String, String> b;

        k(String url, Map<String, String> additionalHttpHeaders) {
            this.a = url;
            this.b = additionalHttpHeaders;
        }
    }

    public static class m {
        Object a;
        String b;
        String c;
        String d;
        Object e;

        private m() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
        }

        /* synthetic */ m(c x0) {
            this();
        }
    }

    public void A(boolean disable) {
        this.F4 = !disable;
    }

    public void x(String handlerName, Object data) {
        Class[] clsArr = {String.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{handlerName, data}, this, changeQuickRedirect, false, 227, clsArr, Void.TYPE).isSupported) {
            y(handlerName, data, (n) null);
        }
    }

    public <T> void y(String handlerName, Object data, n<T> responseCallback) {
        Class[] clsArr = {String.class, Object.class, n.class};
        if (!PatchProxy.proxy(new Object[]{handlerName, data, responseCallback}, this, changeQuickRedirect, false, 228, clsArr, Void.TYPE).isSupported) {
            N(data, responseCallback, handlerName);
        }
    }

    public void setJavascriptCloseWindowListener(i listener) {
        this.q = listener;
    }

    public <T, R> void L(String handlerName, l<T, R> handler) {
        Class[] clsArr = {String.class, l.class};
        if (!PatchProxy.proxy(new Object[]{handlerName, handler}, this, changeQuickRedirect, false, 230, clsArr, Void.TYPE).isSupported) {
            if (handlerName != null && handlerName.length() != 0 && handler != null) {
                this.D4.put(handlerName, handler);
            }
        }
    }

    private void N(Object data, n responseCallback, String handlerName) {
        Class[] clsArr = {Object.class, n.class, String.class};
        if (!PatchProxy.proxy(new Object[]{data, responseCallback, handlerName}, this, changeQuickRedirect, false, 231, clsArr, Void.TYPE).isSupported) {
            if (data != null || (handlerName != null && handlerName.length() != 0)) {
                m message = new m((c) null);
                if (data != null) {
                    message.a = data;
                }
                if (responseCallback != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("java_cb_");
                    long j2 = this.E4 + 1;
                    this.E4 = j2;
                    sb.append(j2);
                    String callbackId = sb.toString();
                    this.C4.put(callbackId, responseCallback);
                    message.b = callbackId;
                }
                if (handlerName != null) {
                    message.c = handlerName;
                }
                K(message);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void K(com.leedarson.base.jsbridge2.WVJBWebView.m r9) {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x002d }
            r2 = 0
            r1[r2] = r9     // Catch:{ all -> 0x002d }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x002d }
            r4 = 0
            r5 = 232(0xe8, float:3.25E-43)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x002d }
            java.lang.Class<com.leedarson.base.jsbridge2.WVJBWebView$m> r0 = com.leedarson.base.jsbridge2.WVJBWebView.m.class
            r6[r2] = r0     // Catch:{ all -> 0x002d }
            java.lang.Class r7 = java.lang.Void.TYPE     // Catch:{ all -> 0x002d }
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x002d }
            boolean r0 = r0.isSupported     // Catch:{ all -> 0x002d }
            if (r0 == 0) goto L_0x001f
            monitor-exit(r8)
            return
        L_0x001f:
            r0 = r8
            java.util.ArrayList<com.leedarson.base.jsbridge2.WVJBWebView$m> r1 = r0.B4     // Catch:{ all -> 0x002d }
            if (r1 == 0) goto L_0x0028
            r1.add(r9)     // Catch:{ all -> 0x002d }
            goto L_0x002b
        L_0x0028:
            r0.C(r9)     // Catch:{ all -> 0x002d }
        L_0x002b:
            monitor-exit(r8)
            return
        L_0x002d:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.jsbridge2.WVJBWebView.K(com.leedarson.base.jsbridge2.WVJBWebView$m):void");
    }

    private void C(m message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 233, new Class[]{m.class}, Void.TYPE).isSupported) {
            String messageJSON = J(message).toString();
            E(String.format(Locale.US, "WebViewJavascriptBridge._handleMessageFromJava(%s)", new Object[]{messageJSON}));
        }
    }

    private void F(String info) {
        if (!PatchProxy.proxy(new Object[]{info}, this, changeQuickRedirect, false, 234, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                m message = a(new JSONObject(info));
                String str = message.d;
                if (str != null) {
                    n responseCallback = this.C4.remove(str);
                    if (responseCallback != null) {
                        responseCallback.onResult(message.e);
                    }
                    return;
                }
                n responseCallback2 = null;
                String callbackId = message.b;
                if (callbackId != null) {
                    responseCallback2 = new b(callbackId);
                }
                l handler = this.D4.get(message.c);
                if (handler != null) {
                    handler.a(message.a, responseCallback2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public class b implements n {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;

        b(String str) {
            this.a = str;
        }

        public void onResult(Object data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, CustomCameraView.BUTTON_STATE_BOTH, new Class[]{Object.class}, Void.TYPE).isSupported) {
                m msg = new m((c) null);
                msg.d = this.a;
                msg.e = data;
                WVJBWebView.s(WVJBWebView.this, msg);
            }
        }
    }

    private JSONObject J(m message) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 235, new Class[]{m.class}, JSONObject.class);
        if (proxy.isSupported) {
            return (JSONObject) proxy.result;
        }
        JSONObject jo = new JSONObject();
        try {
            String str = message.b;
            if (str != null) {
                jo.put("callbackId", (Object) str);
            }
            Object obj = message.a;
            if (obj != null) {
                jo.put("data", obj);
            }
            String str2 = message.c;
            if (str2 != null) {
                jo.put("handlerName", (Object) str2);
            }
            String str3 = message.d;
            if (str3 != null) {
                jo.put("responseId", (Object) str3);
            }
            Object obj2 = message.e;
            if (obj2 != null) {
                jo.put("responseData", obj2);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jo;
    }

    private m a(JSONObject jo) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jo}, this, changeQuickRedirect, false, 236, new Class[]{JSONObject.class}, m.class);
        if (proxy.isSupported) {
            return (m) proxy.result;
        }
        m message = new m((c) null);
        try {
            if (jo.has("callbackId")) {
                message.b = jo.getString("callbackId");
            }
            if (jo.has("data")) {
                message.a = jo.get("data");
            }
            if (jo.has("handlerName")) {
                message.c = jo.getString("handlerName");
            }
            if (jo.has("responseId")) {
                message.d = jo.getString("responseId");
            }
            if (jo.has("responseData")) {
                message.e = jo.get("responseData");
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return message;
    }

    /* access modifiers changed from: package-private */
    @Keep
    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 237, new Class[0], Void.TYPE).isSupported) {
            setLayerType(2, (Paint) null);
            setBackgroundColor(0);
            this.f = new j(this);
            this.d = getContext().getFilesDir().getAbsolutePath() + "/webcache";
            this.C4 = new HashMap();
            this.D4 = new HashMap();
            this.B4 = new ArrayList<>();
            this.a1 = new com.leedarson.base.views.g(getContext());
            WebSettings settings = getSettings();
            settings.setDomStorageEnabled(true);
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(this, true);
                settings.setMixedContentMode(0);
            }
            settings.setAllowFileAccess(true);
            settings.setJavaScriptEnabled(true);
            settings.setSavePassword(false);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setDatabaseEnabled(true);
            super.setWebChromeClient(this.I4);
            super.setWebViewClient(this.J4);
            if (i2 > 10 && i2 < 17) {
                removeJavascriptInterface("searchBoxJavaBridge_");
                removeJavascriptInterface("accessibilityTraversal");
                removeJavascriptInterface("accessibility");
            }
            L("_hasNativeMethod", new c());
            L("_closePage", new d());
            L("_disableJavascriptAlertBoxSafetyTimeout", new e());
            super.addJavascriptInterface(new Object() {
                public static ChangeQuickRedirect changeQuickRedirect;

                @JavascriptInterface
                @Keep
                public void notice(String info) {
                    if (!PatchProxy.proxy(new Object[]{info}, this, changeQuickRedirect, false, 263, new Class[]{String.class}, Void.TYPE).isSupported) {
                        WVJBWebView.this.f.sendMessage(WVJBWebView.this.f.obtainMessage(4, info));
                    }
                }
            }, "WVJBInterface");
        }
    }

    public class c implements l {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void a(Object data, n callback) {
            boolean z = false;
            if (!PatchProxy.proxy(new Object[]{data, callback}, this, changeQuickRedirect, false, 260, new Class[]{Object.class, n.class}, Void.TYPE).isSupported) {
                if (WVJBWebView.this.D4.get(data) != null) {
                    z = true;
                }
                callback.onResult(Boolean.valueOf(z));
            }
        }
    }

    public class d implements l {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void a(Object obj, n nVar) {
            Class[] clsArr = {Object.class, n.class};
            if (!PatchProxy.proxy(new Object[]{obj, nVar}, this, changeQuickRedirect, false, 261, clsArr, Void.TYPE).isSupported) {
                if (WVJBWebView.this.q == null || WVJBWebView.this.q.onClose()) {
                    ((Activity) WVJBWebView.this.getContext()).onBackPressed();
                }
            }
        }
    }

    public class e implements l {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void a(Object data, n nVar) {
            Class[] clsArr = {Object.class, n.class};
            if (!PatchProxy.proxy(new Object[]{data, nVar}, this, changeQuickRedirect, false, 262, clsArr, Void.TYPE).isSupported) {
                WVJBWebView.this.A(((Boolean) data).booleanValue());
            }
        }
    }

    private void b(String script) {
        if (!PatchProxy.proxy(new Object[]{script}, this, changeQuickRedirect, false, 240, new Class[]{String.class}, Void.TYPE).isSupported) {
            v(this, script, (ValueCallback) null);
        }
    }

    public void E(String script) {
        if (!PatchProxy.proxy(new Object[]{script}, this, changeQuickRedirect, false, 241, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                b(script);
                return;
            }
            this.f.sendMessage(this.f.obtainMessage(1, script));
        }
    }

    public void loadUrl(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 242, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.f.sendMessage(this.f.obtainMessage(2, url));
        }
    }

    private void H(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 243, new Class[]{String.class}, Void.TYPE).isSupported) {
            super.loadUrl(url);
            SensorsDataAutoTrackHelper.loadUrl2(this, url);
        }
    }

    private void I(String url, Map<String, String> additionalHttpHeaders) {
        Class[] clsArr = {String.class, Map.class};
        if (!PatchProxy.proxy(new Object[]{url, additionalHttpHeaders}, this, changeQuickRedirect, false, IjkMediaMeta.FF_PROFILE_H264_HIGH_444_PREDICTIVE, clsArr, Void.TYPE).isSupported) {
            super.loadUrl(url, additionalHttpHeaders);
            SensorsDataAutoTrackHelper.loadUrl2(this, url, additionalHttpHeaders);
        }
    }

    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        Class[] clsArr = {String.class, Map.class};
        if (!PatchProxy.proxy(new Object[]{url, additionalHttpHeaders}, this, changeQuickRedirect, false, 245, clsArr, Void.TYPE).isSupported) {
            this.f.sendMessage(this.f.obtainMessage(3, new k(url, additionalHttpHeaders)));
        }
    }

    public void setWebChromeClient(WebChromeClient client) {
        this.G4 = client;
    }

    public void setWebViewClient(WebViewClient client) {
        this.H4 = client;
    }

    public class f extends WebChromeClient {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            Object[] objArr = {view, new Integer(newProgress)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 264, new Class[]{WebView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                timber.log.a.c(WVJBWebView.this.p4 + ",onProgressChanged isCanLoadJSBridge : " + WVJBWebView.this.x + "  newProgress = " + newProgress, new Object[0]);
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onProgressChanged(view, newProgress);
                } else {
                    super.onProgressChanged(view, newProgress);
                }
            }
        }

        public void onReceivedTitle(WebView view, String title) {
            Class[] clsArr = {WebView.class, String.class};
            if (!PatchProxy.proxy(new Object[]{view, title}, this, changeQuickRedirect, false, 265, clsArr, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onReceivedTitle(view, title);
                } else {
                    super.onReceivedTitle(view, title);
                }
            }
        }

        public void onReceivedIcon(WebView view, Bitmap icon) {
            Class[] clsArr = {WebView.class, Bitmap.class};
            if (!PatchProxy.proxy(new Object[]{view, icon}, this, changeQuickRedirect, false, 266, clsArr, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onReceivedIcon(view, icon);
                } else {
                    super.onReceivedIcon(view, icon);
                }
            }
        }

        public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
            Object[] objArr = {view, url, new Byte(precomposed ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 267, new Class[]{WebView.class, String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onReceivedTouchIconUrl(view, url, precomposed);
                } else {
                    super.onReceivedTouchIconUrl(view, url, precomposed);
                }
            }
        }

        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            Class[] clsArr = {View.class, WebChromeClient.CustomViewCallback.class};
            if (!PatchProxy.proxy(new Object[]{view, callback}, this, changeQuickRedirect, false, 268, clsArr, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onShowCustomView(view, callback);
                } else {
                    super.onShowCustomView(view, callback);
                }
            }
        }

        @TargetApi(14)
        public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {
            Object[] objArr = {view, new Integer(requestedOrientation), callback};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 269, new Class[]{View.class, Integer.TYPE, WebChromeClient.CustomViewCallback.class}, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onShowCustomView(view, requestedOrientation, callback);
                } else {
                    super.onShowCustomView(view, requestedOrientation, callback);
                }
            }
        }

        public void onHideCustomView() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, SubsamplingScaleImageView.ORIENTATION_270, new Class[0], Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onHideCustomView();
                } else {
                    super.onHideCustomView();
                }
            }
        }

        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            Object[] objArr = {view, new Byte(isDialog ? (byte) 1 : 0), new Byte(isUserGesture ? (byte) 1 : 0), resultMsg};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 271, new Class[]{WebView.class, cls, cls, Message.class}, cls);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null) {
                return webChromeClient.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        public void onRequestFocus(WebView view) {
            if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, Base.kNumLenSymbols, new Class[]{WebView.class}, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onRequestFocus(view);
                } else {
                    super.onRequestFocus(view);
                }
            }
        }

        public void onCloseWindow(WebView window) {
            if (!PatchProxy.proxy(new Object[]{window}, this, changeQuickRedirect, false, Base.kMatchMaxLen, new Class[]{WebView.class}, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onCloseWindow(window);
                } else {
                    super.onCloseWindow(window);
                }
            }
        }

        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Class<String> cls = String.class;
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, url, message, result}, this, changeQuickRedirect2, false, 274, new Class[]{WebView.class, cls, cls, JsResult.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (!WVJBWebView.this.F4) {
                result.confirm();
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null && webChromeClient.onJsAlert(view, url, message, result)) {
                return true;
            }
            new AlertDialog.Builder(WVJBWebView.this.getContext()).setMessage((CharSequence) message).setCancelable(false).setPositiveButton(17039370, (DialogInterface.OnClickListener) new a(result)).create().show();
            return true;
        }

        public class a implements DialogInterface.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ JsResult c;

            a(JsResult jsResult) {
                this.c = jsResult;
            }

            @SensorsDataInstrumented
            public void onClick(DialogInterface dialog, int i) {
                if (PatchProxy.proxy(new Object[]{dialog, new Integer(i)}, this, changeQuickRedirect, false, 290, new Class[]{DialogInterface.class, Integer.TYPE}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackDialog(dialog, i);
                    return;
                }
                int i2 = i;
                dialog.dismiss();
                if (WVJBWebView.this.F4) {
                    this.c.confirm();
                }
                SensorsDataAutoTrackHelper.trackDialog(dialog, i);
            }
        }

        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            Class<String> cls = String.class;
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, url, message, result}, this, changeQuickRedirect2, false, 275, new Class[]{WebView.class, cls, cls, JsResult.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (!WVJBWebView.this.F4) {
                result.confirm();
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null && webChromeClient.onJsConfirm(view, url, message, result)) {
                return true;
            }
            DialogInterface.OnClickListener listener = new b(result);
            new AlertDialog.Builder(WVJBWebView.this.getContext()).setMessage((CharSequence) message).setCancelable(false).setPositiveButton(17039370, listener).setNegativeButton(17039360, listener).show();
            return true;
        }

        public class b implements DialogInterface.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ JsResult c;

            b(JsResult jsResult) {
                this.c = jsResult;
            }

            @SensorsDataInstrumented
            public void onClick(DialogInterface dialogInterface, int i) {
                if (PatchProxy.proxy(new Object[]{dialogInterface, new Integer(i)}, this, changeQuickRedirect, false, 291, new Class[]{DialogInterface.class, Integer.TYPE}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
                    return;
                }
                int which = i;
                DialogInterface dialogInterface2 = dialogInterface;
                if (WVJBWebView.this.F4) {
                    if (which == -1) {
                        this.c.confirm();
                    } else {
                        this.c.cancel();
                    }
                }
                SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
            }
        }

        public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
            Class<String> cls = String.class;
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{webView, str, str2, str3, jsPromptResult}, this, changeQuickRedirect2, false, 276, new Class[]{WebView.class, cls, cls, cls, JsPromptResult.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            String url = str;
            String defaultValue = str3;
            WebView view = webView;
            String message = str2;
            JsPromptResult result = jsPromptResult;
            if (!WVJBWebView.this.F4) {
                result.confirm();
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null && webChromeClient.onJsPrompt(view, url, message, defaultValue, result)) {
                return true;
            }
            EditText editText = new EditText(WVJBWebView.this.getContext());
            editText.setText(defaultValue);
            if (defaultValue != null) {
                editText.setSelection(defaultValue.length());
            }
            float dpi = WVJBWebView.this.getContext().getResources().getDisplayMetrics().density;
            DialogInterface.OnClickListener listener = new c(result, editText);
            new AlertDialog.Builder(WVJBWebView.this.getContext()).setTitle((CharSequence) message).setView((View) editText).setCancelable(false).setPositiveButton(17039370, listener).setNegativeButton(17039360, listener).show();
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
            int t = (int) (16.0f * dpi);
            layoutParams.setMargins(t, 0, t, 0);
            layoutParams.gravity = 1;
            editText.setLayoutParams(layoutParams);
            int padding = (int) (15.0f * dpi);
            editText.setPadding(padding - ((int) (5.0f * dpi)), padding, padding, padding);
            return true;
        }

        public class c implements DialogInterface.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ JsPromptResult c;
            final /* synthetic */ EditText d;

            c(JsPromptResult jsPromptResult, EditText editText) {
                this.c = jsPromptResult;
                this.d = editText;
            }

            @SensorsDataInstrumented
            public void onClick(DialogInterface dialogInterface, int i) {
                if (PatchProxy.proxy(new Object[]{dialogInterface, new Integer(i)}, this, changeQuickRedirect, false, 292, new Class[]{DialogInterface.class, Integer.TYPE}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
                    return;
                }
                int which = i;
                DialogInterface dialogInterface2 = dialogInterface;
                if (WVJBWebView.this.F4) {
                    if (which == -1) {
                        this.c.confirm(this.d.getText().toString());
                    } else {
                        this.c.cancel();
                    }
                }
                SensorsDataAutoTrackHelper.trackDialog(dialogInterface, i);
            }
        }

        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            Class<String> cls = String.class;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, url, message, result}, this, changeQuickRedirect, false, 277, new Class[]{WebView.class, cls, cls, JsResult.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null) {
                return webChromeClient.onJsBeforeUnload(view, url, message, result);
            }
            return super.onJsBeforeUnload(view, url, message, result);
        }

        public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, WebStorage.QuotaUpdater quotaUpdater) {
            Class<String> cls = String.class;
            Object[] objArr = {str, str2, new Long(j), new Long(j2), new Long(j3), quotaUpdater};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Long.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 278, new Class[]{cls, cls, cls2, cls2, cls2, WebStorage.QuotaUpdater.class}, Void.TYPE).isSupported) {
                String databaseIdentifier = str2;
                WebStorage.QuotaUpdater quotaUpdater2 = quotaUpdater;
                String url = str;
                long quota = j;
                long totalQuota = j3;
                long estimatedDatabaseSize = j2;
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater2);
                } else {
                    super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota, quotaUpdater2);
                }
            }
        }

        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            Class[] clsArr = {String.class, GeolocationPermissions.Callback.class};
            if (!PatchProxy.proxy(new Object[]{origin, callback}, this, changeQuickRedirect, false, 279, clsArr, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onGeolocationPermissionsShowPrompt(origin, callback);
                } else {
                    super.onGeolocationPermissionsShowPrompt(origin, callback);
                }
            }
        }

        public void onGeolocationPermissionsHidePrompt() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 280, new Class[0], Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onGeolocationPermissionsHidePrompt();
                } else {
                    super.onGeolocationPermissionsHidePrompt();
                }
            }
        }

        @TargetApi(21)
        public void onPermissionRequest(PermissionRequest request) {
            if (!PatchProxy.proxy(new Object[]{request}, this, changeQuickRedirect, false, 281, new Class[]{PermissionRequest.class}, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onPermissionRequest(request);
                } else {
                    super.onPermissionRequest(request);
                }
            }
        }

        @TargetApi(21)
        public void onPermissionRequestCanceled(PermissionRequest request) {
            if (!PatchProxy.proxy(new Object[]{request}, this, changeQuickRedirect, false, 282, new Class[]{PermissionRequest.class}, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onPermissionRequestCanceled(request);
                } else {
                    super.onPermissionRequestCanceled(request);
                }
            }
        }

        public boolean onJsTimeout() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 283, new Class[0], Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null) {
                return webChromeClient.onJsTimeout();
            }
            return super.onJsTimeout();
        }

        public void onConsoleMessage(String message, int lineNumber, String sourceID) {
            Class<String> cls = String.class;
            Object[] objArr = {message, new Integer(lineNumber), sourceID};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 284, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.onConsoleMessage(message, lineNumber, sourceID);
                } else {
                    super.onConsoleMessage(message, lineNumber, sourceID);
                }
            }
        }

        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{consoleMessage}, this, changeQuickRedirect, false, 285, new Class[]{ConsoleMessage.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null) {
                return webChromeClient.onConsoleMessage(consoleMessage);
            }
            if (consoleMessage.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                a.b g = timber.log.a.g(WVJBWebView.class.getSimpleName());
                g.c("web error:" + consoleMessage.message(), new Object[0]);
                com.leedarson.base.manager.a.g().h(consoleMessage.message());
            }
            return super.onConsoleMessage(consoleMessage);
        }

        public Bitmap getDefaultVideoPoster() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 286, new Class[0], Bitmap.class);
            if (proxy.isSupported) {
                return (Bitmap) proxy.result;
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null) {
                return webChromeClient.getDefaultVideoPoster();
            }
            return super.getDefaultVideoPoster();
        }

        public View getVideoLoadingProgressView() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 287, new Class[0], View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null) {
                return webChromeClient.getVideoLoadingProgressView();
            }
            return super.getVideoLoadingProgressView();
        }

        public void getVisitedHistory(ValueCallback<String[]> callback) {
            if (!PatchProxy.proxy(new Object[]{callback}, this, changeQuickRedirect, false, 288, new Class[]{ValueCallback.class}, Void.TYPE).isSupported) {
                WebChromeClient webChromeClient = WVJBWebView.this.G4;
                if (webChromeClient != null) {
                    webChromeClient.getVisitedHistory(callback);
                } else {
                    super.getVisitedHistory(callback);
                }
            }
        }

        @TargetApi(21)
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{webView, filePathCallback, fileChooserParams}, this, changeQuickRedirect2, false, 289, new Class[]{WebView.class, ValueCallback.class, WebChromeClient.FileChooserParams.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            WebChromeClient webChromeClient = WVJBWebView.this.G4;
            if (webChromeClient != null) {
                return webChromeClient.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    }

    public class g extends WebViewClient {
        public static ChangeQuickRedirect changeQuickRedirect;

        g() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect2, false, 293, new Class[]{WebView.class, String.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            AnalyticsService analyticsService = (AnalyticsService) com.alibaba.android.arouter.launcher.a.c().g(AnalyticsService.class);
            if (analyticsService != null) {
                analyticsService.execute(url, view);
            }
            WebViewClient webViewClient = WVJBWebView.this.H4;
            if (webViewClient != null) {
                return webViewClient.shouldOverrideUrlLoading(view, url);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Class[] clsArr = {WebView.class, String.class, Bitmap.class};
            if (!PatchProxy.proxy(new Object[]{view, url, favicon}, this, changeQuickRedirect, false, 294, clsArr, Void.TYPE).isSupported) {
                v.d("WEBVIEW#OnPageStarted", "webview onStarted");
                com.leedarson.base.logger.a.c("JBWebView", "  ======> onPageStarted webview.current.url=" + url);
                boolean unused = WVJBWebView.this.x = false;
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onPageStarted(view, url, favicon);
                } else {
                    super.onPageStarted(view, url, favicon);
                }
                try {
                    int netState = w.w(WVJBWebView.this.getContext());
                    if (WVJBWebView.this.a1 != null && WVJBWebView.this.p1 && netState > 0) {
                        WVJBWebView.this.a1.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onPageFinished(WebView view, String url) {
            if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 295, new Class[]{WebView.class, String.class}, Void.TYPE).isSupported) {
                com.leedarson.base.logger.a.c("JBWebView", "  ======> onPageFinished webview.current.url=" + url);
                Log.i("GhuntStart", w.r() + "----JSBridge server end");
                JsbridgeService jsbridgeService = (JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(JsbridgeService.class);
                if (jsbridgeService != null) {
                    jsbridgeService.clearEventSet();
                }
                t.INSTANCE.timeArray[5] = w.r();
                timber.log.a.c("onPageFinished: " + url, new Object[0]);
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onPageFinished(view, url);
                } else {
                    super.onPageFinished(view, url);
                }
                if (WVJBWebView.this.a1 != null) {
                    WVJBWebView.this.a1.dismiss();
                }
            }
        }

        public void onLoadResource(WebView view, String url) {
            if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 296, new Class[]{WebView.class, String.class}, Void.TYPE).isSupported) {
                if ("https://__bridge_loaded__/".equals(url)) {
                    Log.i("GhuntStart", w.r() + "----JSBridge server start");
                    t.INSTANCE.timeArray[6] = w.r();
                    boolean unused = WVJBWebView.this.x = true;
                    try {
                        InputStream is = view.getContext().getAssets().open("jsbridge2/WebViewJavascriptBridge.js");
                        byte[] buffer = new byte[is.available()];
                        is.read(buffer);
                        is.close();
                        WVJBWebView.this.E(new String(buffer));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    synchronized (WVJBWebView.this) {
                        if (WVJBWebView.this.B4 != null) {
                            for (int i = 0; i < WVJBWebView.this.B4.size(); i++) {
                                WVJBWebView wVJBWebView = WVJBWebView.this;
                                WVJBWebView.s(wVJBWebView, (m) wVJBWebView.B4.get(i));
                            }
                            ArrayList unused2 = WVJBWebView.this.B4 = null;
                        }
                    }
                    org.greenrobot.eventbus.c.c().l(new com.leedarson.base.webview.a());
                }
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onLoadResource(view, url);
                } else {
                    super.onLoadResource(view, url);
                }
            }
        }

        @TargetApi(23)
        public void onPageCommitVisible(WebView view, String url) {
            Class[] clsArr = {WebView.class, String.class};
            if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 297, clsArr, Void.TYPE).isSupported) {
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onPageCommitVisible(view, url);
                } else {
                    super.onPageCommitVisible(view, url);
                }
            }
        }

        @Deprecated
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            WebViewClient webViewClient = WVJBWebView.this.H4;
            if (webViewClient != null) {
                return webViewClient.shouldInterceptRequest(view, url);
            }
            return super.shouldInterceptRequest(view, url);
        }

        @TargetApi(21)
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            WebResourceResponse response;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, request}, this, changeQuickRedirect, false, 298, new Class[]{WebView.class, WebResourceRequest.class}, WebResourceResponse.class);
            if (proxy.isSupported) {
                return (WebResourceResponse) proxy.result;
            }
            if (WVJBWebView.this.p0 == null || WVJBWebView.this.p0.equals(request.getUrl().toString())) {
                SharePreferenceUtils.setPrefString(WVJBWebView.this.getContext(), "httpBaseUrl", "");
                String unused = WVJBWebView.this.p0 = request.getUrl().toString();
                if ("Home".equals(WVJBWebView.this.p4)) {
                    SharePreferenceUtils.setPrefString(WVJBWebView.this.getContext(), "mainPage", WVJBWebView.this.p0);
                }
                io.reactivex.l.g0(10, TimeUnit.SECONDS).X(new a(this));
            }
            String url = request.getUrl().toString();
            if (url.contains("android/image")) {
                String[] urlSplit = url.split("android/image");
                if (urlSplit.length > 1) {
                    try {
                        return new WebResourceResponse(Checker.MIME_TYPE_JPG, "UTF-8", new FileInputStream(new File(urlSplit[1].trim())));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } else if (url.contains("android/internal_image")) {
                String[] urlSplit2 = url.split("android/internal_image");
                if (urlSplit2.length > 1) {
                    String imgPath = urlSplit2[1];
                    if (imgPath.contains("?ver=")) {
                        imgPath = imgPath.split("\\?ver=")[0];
                    }
                    try {
                        return new WebResourceResponse(Checker.MIME_TYPE_JPG, "UTF-8", new FileInputStream(new File(WVJBWebView.this.getContext().getFilesDir().getPath() + "/web/" + imgPath.trim())));
                    } catch (FileNotFoundException e2) {
                        e2.printStackTrace();
                    }
                }
            } else if ((WVJBWebView.this.A4 & 1) == 1) {
                d webCacheStrategy = new b(WVJBWebView.this.getContext());
                if (webCacheStrategy.b(view, request) && (response = webCacheStrategy.a(view, request)) != null) {
                    return response;
                }
            }
            WebViewClient webViewClient = WVJBWebView.this.H4;
            if (webViewClient != null) {
                return webViewClient.shouldInterceptRequest(view, request);
            }
            return super.shouldInterceptRequest(view, request);
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b(Long l) {
            if (!PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 309, new Class[]{Long.class}, Void.TYPE).isSupported) {
                if (SharePreferenceUtils.getPrefString(WVJBWebView.this.getContext(), "httpBaseUrl", "").equals("")) {
                    org.greenrobot.eventbus.c.c().l(new com.leedarson.base.webview.b(WVJBWebView.this.y));
                }
            }
        }

        @Deprecated
        public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
            WebViewClient webViewClient = WVJBWebView.this.H4;
            if (webViewClient != null) {
                webViewClient.onTooManyRedirects(view, cancelMsg, continueMsg);
            } else {
                super.onTooManyRedirects(view, cancelMsg, continueMsg);
            }
        }

        @Deprecated
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (failingUrl.equals(WVJBWebView.this.p0)) {
                org.greenrobot.eventbus.c.c().l(new com.leedarson.base.webview.b(WVJBWebView.this.y));
            }
            WebViewClient webViewClient = WVJBWebView.this.H4;
            if (webViewClient != null) {
                webViewClient.onReceivedError(view, errorCode, description, failingUrl);
            } else {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
            if (Build.VERSION.SDK_INT < 23) {
                timber.log.a.g("Ghunt").h("Server_error002--", new Object[0]);
                if (errorCode != -2) {
                    org.greenrobot.eventbus.c.c().l(new ServerStatusEvent("", 3));
                }
            }
        }

        @TargetApi(23)
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Class[] clsArr = {WebView.class, WebResourceRequest.class, WebResourceError.class};
            if (!PatchProxy.proxy(new Object[]{view, request, error}, this, changeQuickRedirect, false, 299, clsArr, Void.TYPE).isSupported) {
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onReceivedError(view, request, error);
                } else {
                    super.onReceivedError(view, request, error);
                }
                if (request.isForMainFrame()) {
                    a.b g = timber.log.a.g("Ghunt");
                    g.h("Server_error00--" + error.getDescription() + "                " + request.getUrl() + "--errorCode =" + error.getErrorCode(), new Object[0]);
                    if (error.getErrorCode() != -2) {
                        org.greenrobot.eventbus.c.c().l(new ServerStatusEvent("", 3));
                    }
                }
            }
        }

        @TargetApi(23)
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Class[] clsArr = {WebView.class, WebResourceRequest.class, WebResourceResponse.class};
            if (!PatchProxy.proxy(new Object[]{view, request, errorResponse}, this, changeQuickRedirect, false, IjkMediaCodecInfo.RANK_SECURE, clsArr, Void.TYPE).isSupported) {
                if (request.getUrl().toString().equals(WVJBWebView.this.p0) || request.getUrl().toString().contains("127.0.0.1:9999")) {
                    org.greenrobot.eventbus.c.c().l(new com.leedarson.base.webview.b(WVJBWebView.this.y));
                }
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onReceivedHttpError(view, request, errorResponse);
                } else {
                    super.onReceivedHttpError(view, request, errorResponse);
                }
            }
        }

        public void onFormResubmission(WebView view, Message dontResend, Message resend) {
            Class[] clsArr = {WebView.class, Message.class, Message.class};
            if (!PatchProxy.proxy(new Object[]{view, dontResend, resend}, this, changeQuickRedirect, false, 301, clsArr, Void.TYPE).isSupported) {
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onFormResubmission(view, dontResend, resend);
                } else {
                    super.onFormResubmission(view, dontResend, resend);
                }
            }
        }

        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            Object[] objArr = {view, url, new Byte(isReload ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 302, new Class[]{WebView.class, String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.doUpdateVisitedHistory(view, url, isReload);
                } else {
                    super.doUpdateVisitedHistory(view, url, isReload);
                }
            }
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Class[] clsArr = {WebView.class, SslErrorHandler.class, SslError.class};
            if (!PatchProxy.proxy(new Object[]{view, handler, error}, this, changeQuickRedirect, false, 303, clsArr, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("LdsCore");
                g.c("onReceivedSslError: " + error.toString() + "url:" + view.getUrl(), new Object[0]);
                if (error.getPrimaryError() <= 3) {
                    WVJBWebView.p(WVJBWebView.this, handler, view.getUrl());
                } else {
                    super.onReceivedSslError(view, handler, error);
                }
            }
        }

        @TargetApi(21)
        public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
            Class[] clsArr = {WebView.class, ClientCertRequest.class};
            if (!PatchProxy.proxy(new Object[]{view, request}, this, changeQuickRedirect, false, 304, clsArr, Void.TYPE).isSupported) {
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onReceivedClientCertRequest(view, request);
                } else {
                    super.onReceivedClientCertRequest(view, request);
                }
            }
        }

        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            Class<String> cls = String.class;
            if (!PatchProxy.proxy(new Object[]{view, handler, host, realm}, this, changeQuickRedirect, false, 305, new Class[]{WebView.class, HttpAuthHandler.class, cls, cls}, Void.TYPE).isSupported) {
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onReceivedHttpAuthRequest(view, handler, host, realm);
                } else {
                    super.onReceivedHttpAuthRequest(view, handler, host, realm);
                }
            }
        }

        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect2, false, 306, new Class[]{WebView.class, KeyEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            WebViewClient webViewClient = WVJBWebView.this.H4;
            if (webViewClient != null) {
                return webViewClient.shouldOverrideKeyEvent(view, event);
            }
            return super.shouldOverrideKeyEvent(view, event);
        }

        @Deprecated
        public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
            WebViewClient webViewClient = WVJBWebView.this.H4;
            if (webViewClient != null) {
                webViewClient.onUnhandledKeyEvent(view, event);
            } else {
                super.onUnhandledKeyEvent(view, event);
            }
        }

        public void onScaleChanged(WebView view, float oldScale, float newScale) {
            Object[] objArr = {view, new Float(oldScale), new Float(newScale)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Float.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 307, new Class[]{WebView.class, cls, cls}, Void.TYPE).isSupported) {
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onScaleChanged(view, oldScale, newScale);
                } else {
                    super.onScaleChanged(view, oldScale, newScale);
                }
            }
        }

        @TargetApi(12)
        public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
            Class<String> cls = String.class;
            if (!PatchProxy.proxy(new Object[]{view, realm, account, args}, this, changeQuickRedirect, false, 308, new Class[]{WebView.class, cls, cls, cls}, Void.TYPE).isSupported) {
                WebViewClient webViewClient = WVJBWebView.this.H4;
                if (webViewClient != null) {
                    webViewClient.onReceivedLoginRequest(view, realm, account, args);
                } else {
                    super.onReceivedLoginRequest(view, realm, account, args);
                }
            }
        }
    }

    private void P(SslErrorHandler handler, String url) {
        if (!PatchProxy.proxy(new Object[]{handler, url}, this, changeQuickRedirect, false, 246, new Class[]{SslErrorHandler.class, String.class}, Void.TYPE).isSupported) {
            timber.log.a.i("verify:用于做站点的网络探测     url=" + url, new Object[0]);
            if (c == null) {
                z.a cbuilder = new z.a();
                try {
                    CertificateFactory cAf = CertificateFactory.getInstance("X.509");
                    InputStream inputStream = getContext().getAssets().open("840bb0b9a960c6003e1975fc3b164e4c");
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
                    cbuilder.M(new h());
                    c = cbuilder.c();
                } catch (NoSuchAlgorithmException e2) {
                    e2.printStackTrace();
                } catch (KeyManagementException e3) {
                    e3.printStackTrace();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            c.a(new b0.a().e().p(url).b()).o0(new a(handler));
        }
    }

    public class h implements HostnameVerifier {
        public static ChangeQuickRedirect changeQuickRedirect;

        h() {
        }

        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }

    public class a implements okhttp3.f {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ SslErrorHandler c;

        a(SslErrorHandler sslErrorHandler) {
            this.c = sslErrorHandler;
        }

        public void onFailure(okhttp3.e eVar, IOException iOException) {
            Class[] clsArr = {okhttp3.e.class, IOException.class};
            if (!PatchProxy.proxy(new Object[]{eVar, iOException}, this, changeQuickRedirect, false, 257, clsArr, Void.TYPE).isSupported) {
                this.c.cancel();
            }
        }

        public void onResponse(okhttp3.e eVar, d0 d0Var) {
            Class[] clsArr = {okhttp3.e.class, d0.class};
            if (!PatchProxy.proxy(new Object[]{eVar, d0Var}, this, changeQuickRedirect, false, CustomCameraView.BUTTON_STATE_ONLY_RECORDER, clsArr, Void.TYPE).isSupported) {
                this.c.proceed();
            }
        }
    }

    public void M() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 247, new Class[0], Void.TYPE).isSupported) {
            String str = this.p0;
            loadUrl(str);
            SensorsDataAutoTrackHelper.loadUrl2(this, str);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 248, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!this.a2) {
            return super.onTouchEvent(event);
        }
        if (event.getY() < ((float) this.p2) || event.getY() > ((float) this.p3)) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    public void O(int minY, int maxY) {
        this.a2 = true;
        this.p2 = minY;
        this.p3 = maxY;
    }

    public void z() {
        this.a2 = false;
    }

    public void D() {
        this.a2 = true;
    }

    public void B() {
        this.a2 = false;
    }
}
