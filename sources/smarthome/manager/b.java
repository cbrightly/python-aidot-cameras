package smarthome.manager;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.RequiresApi;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.leedarson.base.R$layout;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.ExternalWebView;
import com.leedarson.bean.Constants;
import com.leedarson.serviceinterface.JsbridgeService;
import com.leedarson.serviceinterface.event.TabResendProgressEvent;
import com.leedarson.serviceinterface.event.WebviewDidRenderEvent;
import com.leedarson.serviceinterface.event.WebviewReloadEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import okhttp3.d0;
import okhttp3.e;
import okhttp3.f;
import org.greenrobot.eventbus.l;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import smarthome.reporter.q;
import smarthome.ui.navigationbar.LDSNavigationBar;
import timber.log.a;

/* compiled from: MultiWebViewManager */
public class b {
    private static b a;
    private final String b = "MultiWebViewManager";
    private final String c = "http://xx/";
    private HashMap<String, ExternalWebView> d = new HashMap<>();
    private ViewGroup e;
    /* access modifiers changed from: private */
    public WeakReference<Activity> f;
    private Handler g = new Handler(Looper.getMainLooper());
    private String h = "Home";
    private d i = new d();
    private c j = new c();
    /* access modifiers changed from: private */
    public WVJBWebView k;
    /* access modifiers changed from: private */
    public q l;

    public b() {
        org.greenrobot.eventbus.c.c().p(this);
    }

    public static b g() {
        if (a == null) {
            synchronized (b.class) {
                if (a == null) {
                    a = new b();
                }
            }
        }
        return a;
    }

    public void i(ViewGroup viewGroup, Activity activity) {
        this.e = viewGroup;
        this.f = new WeakReference<>(activity);
        j(activity);
    }

    public void u() {
        Handler handler = this.g;
        if (handler != null) {
            handler.removeCallbacks((Runnable) null);
        }
        org.greenrobot.eventbus.c.c().r(this);
    }

    public void j(Activity activity) {
        String defaultUrl = "http://xx/";
        if (w.w(activity) > 0) {
            defaultUrl = "";
        }
        JSONArray array = new JSONArray();
        try {
            JSONObject obj2 = new JSONObject();
            obj2.put(CacheEntity.KEY, (Object) "Community");
            obj2.put("url", (Object) defaultUrl);
            array.put((Object) obj2);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        g().o(array);
    }

    public void o(JSONArray array) {
        if (array != null && array.length() > 0) {
            for (int i2 = 0; i2 < array.length(); i2++) {
                try {
                    JSONObject item = array.getJSONObject(i2);
                    n(item.optString(CacheEntity.KEY), item.optString("url"));
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void n(String key, String url) {
        if (this.d.containsKey(key)) {
            ExternalWebView externalWebView = this.d.get(key);
            Uri uri = null;
            if (!TextUtils.isEmpty(url)) {
                uri = Uri.parse(url);
            }
            externalWebView.setWebViewClient(new C0493b(key, uri, externalWebView));
            q("preloadWebView", "11111reload key:" + key + ",url:" + url);
            externalWebView.H(url, this.g);
        } else if (this.e != null && this.f.get() != null) {
            q("preloadWebView", "2222preload key:" + key + ",url:" + url);
            ExternalWebView webView = new ExternalWebView(this.e.getContext());
            webView.setActivity((Activity) this.f.get());
            webView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            webView.setVisibility(8);
            webView.setAliasKey(key);
            Uri uri2 = null;
            if (!TextUtils.isEmpty(url)) {
                uri2 = Uri.parse(url);
            }
            webView.setWebViewClient(new C0493b(key, uri2, webView));
            if ("Community".equals(key)) {
                webView.setSkeletonLayoutId(R$layout.layout_skeleton_community);
                webView.setOnErrorClickProxyListener(new a(this));
            }
            if ("Shop".equals(key)) {
                webView.setUseLoadingView(true);
                webView.v(com.leedarson.serviceimpl.system.notch.b.b((Context) this.f.get()));
                if (TextUtils.isEmpty(url)) {
                    webView.O();
                }
            }
            if (!TextUtils.isEmpty(url)) {
                webView.G(url);
            }
            this.d.put(key, webView);
            this.e.addView(webView);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: k */
    public /* synthetic */ void l(ExternalWebView wvjbWebView) {
        try {
            new JSONObject().put("activeKey", (Object) "Community");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        q("WebviewLoadError", "try again 刷新论坛...");
        wvjbWebView.w();
    }

    /* renamed from: smarthome.manager.b$b  reason: collision with other inner class name */
    /* compiled from: MultiWebViewManager */
    public class C0493b extends WebViewClient {
        private String a;
        private Uri b;
        private ExternalWebView c;

        public C0493b(String key, Uri uri, ExternalWebView webView) {
            this.a = key;
            this.b = uri;
            this.c = webView;
        }

        @RequiresApi(api = 23)
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            b bVar = b.this;
            bVar.q("WebviewLoadError", "网页加载错误 onReceivedError:" + error.getDescription() + ",alias:" + this.a + ",url:" + request.getUrl().toString() + ",code:" + error.getErrorCode());
            Uri reqUri = request.getUrl();
            Uri uri = this.b;
            if (((uri == null || uri.getHost() == null || !this.b.getHost().equals(reqUri.getHost())) ? false : true) || "http://xx/".equals(request.getUrl().toString())) {
                this.c.M();
            }
        }

        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);
            a.b g = timber.log.a.g("MultiWebViewManager");
            g.c("网页http错误 onReceivedHttpError:,alias:" + this.a, new Object[0]);
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            a.b g = timber.log.a.g("MultiWebViewManager");
            g.a("onPageFinished:" + this.c.getAliasKey(), new Object[0]);
        }
    }

    public void r(String key) {
        int childCount = this.e.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View view = this.e.getChildAt(i2);
            if (view instanceof WVJBWebView) {
                WVJBWebView webView = (WVJBWebView) view;
                if (webView.getAliasKey().equals(key)) {
                    webView.setVisibility(0);
                    v(webView);
                } else {
                    if ("Home".equals(webView.getAliasKey())) {
                        this.g.removeCallbacks(this.j);
                    }
                    webView.setVisibility(8);
                }
            }
            if (view instanceof ExternalWebView) {
                ExternalWebView externalWebView = (ExternalWebView) view;
                if (externalWebView.getAliasKey().equals(key)) {
                    externalWebView.setVisibility(0);
                    v(externalWebView.getWebView());
                    this.h = externalWebView.getAliasKey();
                } else {
                    externalWebView.setVisibility(8);
                }
            }
        }
    }

    public void s() {
        t();
    }

    public void h() {
        ExternalWebView comminityWebView = this.d.get("Community");
        if (comminityWebView != null) {
            timber.log.a.g("AlarmWindowHelper").m("communitiyweb 不显示", new Object[0]);
            comminityWebView.setVisibility(8);
        }
        ExternalWebView shopWebView = this.d.get("Shop");
        if (shopWebView != null) {
            shopWebView.setVisibility(8);
        }
    }

    public void t() {
        int childCount = this.e.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View view = this.e.getChildAt(i2);
            if (view instanceof WVJBWebView) {
                WVJBWebView webView = (WVJBWebView) view;
                if (webView.getAliasKey().equals("Home")) {
                    webView.setVisibility(0);
                    v(webView);
                    this.h = "Home";
                } else {
                    webView.setVisibility(8);
                }
            } else {
                view.setVisibility(8);
            }
        }
    }

    public boolean e() {
        ExternalWebView externalWebView;
        if (this.h.equals("Home") || (externalWebView = this.d.get(this.h)) == null || externalWebView.getVisibility() != 0 || !externalWebView.o()) {
            return false;
        }
        externalWebView.x();
        return true;
    }

    public ExternalWebView f(String key) {
        return this.d.get(key);
    }

    public void v(WVJBWebView webView) {
        this.k = webView;
        this.g.removeCallbacks(this.i);
        this.g.postDelayed(this.i, 600);
    }

    /* compiled from: MultiWebViewManager */
    public final class d implements Runnable {
        d() {
        }

        public void run() {
            if (b.this.k != null) {
                StatusBarUtil.setStatusBarTextAuto((Activity) b.this.f.get(), b.this.k);
            }
        }
    }

    /* compiled from: MultiWebViewManager */
    public final class c implements Runnable {
        c() {
        }

        public void run() {
            b.this.t();
        }
    }

    public void d(WVJBWebView webView) {
        Handler handler;
        WVJBWebView wVJBWebView = this.k;
        if (wVJBWebView != null && webView != null && wVJBWebView.hashCode() == webView.hashCode() && (handler = this.g) != null) {
            handler.removeCallbacks(this.i);
        }
    }

    @l
    public void onWebviewDidRenderEvent(WebviewDidRenderEvent didRenderEvent) {
        WVJBWebView srcView = didRenderEvent.webView;
        a.b g2 = timber.log.a.g("MultiWebViewManager");
        g2.a("MultiWebViewManager 收到didRender src:" + srcView.getAliasKey() + ",hashcode=" + srcView.hashCode(), new Object[0]);
        int childCount = this.e.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View view = this.e.getChildAt(i2);
            if (view instanceof ExternalWebView) {
                ExternalWebView externalWebView = (ExternalWebView) view;
                if (externalWebView.getWebView().hashCode() == srcView.hashCode()) {
                    a.b g3 = timber.log.a.g("MultiWebViewManager");
                    g3.c("MultiWebViewManager didRender 匹配到子webview ，key:" + srcView.getAliasKey(), new Object[0]);
                    externalWebView.t(true);
                }
            }
        }
    }

    public void m(LDSNavigationBar ldsNavigationBar) {
        if (this.d.containsKey("Shop") && "Shop".equals(ldsNavigationBar.getCurrentActiveKey())) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("status", 0);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            JsbridgeService jsbridgeService = (JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(JsbridgeService.class);
            if (jsbridgeService != null) {
                jsbridgeService.nativeCallJs(this.d.get("Shop").getWebView(), Constants.SERVICE_WEBVIEW, "onActiveChange", jsonObject.toString());
            }
        }
    }

    @l
    public void onWebviewReloadEvent(WebviewReloadEvent reloadEvent) {
        WVJBWebView srcWebView = reloadEvent.webView;
        if (srcWebView.getAliasKey().equals("Home")) {
            p(srcWebView, (Context) this.f.get());
        }
    }

    private void p(WVJBWebView srcWebView, Context context) {
        boolean needResendCurrentActiveKey = !TextUtils.isEmpty(SharePreferenceUtils.getPrefString(context, "resendCurrentActiveKey", ""));
        q b2 = q.b();
        this.l = b2;
        b2.f(context, System.currentTimeMillis() + "");
        String loadUrl = com.leedarson.base.webservice.utils.b.b().e(SharePreferenceUtils.getPrefString(context, "local_url", ""));
        timber.log.a.g("LdsCore").h("before reload Main webview Verify httpserver:" + loadUrl, new Object[0]);
        com.leedarson.base.webservice.utils.b.b().m(context, loadUrl, new a(new smarthome.reporter.beans.a("verifyHttpServer", 200), System.currentTimeMillis(), context, needResendCurrentActiveKey));
    }

    /* compiled from: MultiWebViewManager */
    public class a implements f {
        final /* synthetic */ smarthome.reporter.beans.a c;
        final /* synthetic */ long d;
        final /* synthetic */ Context f;
        final /* synthetic */ boolean q;

        a(smarthome.reporter.beans.a aVar, long j, Context context, boolean z) {
            this.c = aVar;
            this.d = j;
            this.f = context;
            this.q = z;
        }

        public void onFailure(e call, IOException e) {
            a.b g = timber.log.a.g("LdsCore");
            g.c("reload Main webview verify:onFailure: " + e.toString(), new Object[0]);
            smarthome.reporter.beans.a aVar = this.c;
            aVar.setResponse("verifyHttpServer fail,ex:" + e.toString());
            this.c.setDuration(System.currentTimeMillis() - this.d);
            b.this.l.a(this.c);
            b.this.l.d(TabResendProgressEvent.STEP_RESTART_HTTP_SERVER);
            SharePreferenceUtils.setPrefBoolean(this.f, "reload_main_webview", true);
            com.leedarson.base.webservice.utils.b b = com.leedarson.base.webservice.utils.b.b();
            Context context = this.f;
            b.i(context, "MultiWebViewManager.reloadMainVerify.onFailure exception=" + e.toString());
            if (this.q) {
                org.greenrobot.eventbus.c.c().l(new TabResendProgressEvent(TabResendProgressEvent.STEP_DIAGNOSIS_WEBVIEW, this.c.getDuration(), this.c.getCode()));
            }
        }

        public void onResponse(e call, d0 response) {
            a.b g = timber.log.a.g("LdsCore");
            g.c("reload Main webview verify:onResponse: " + response.j(), new Object[0]);
            if (response.j() != 200) {
                smarthome.reporter.beans.a aVar = this.c;
                aVar.setResponse("verifyHttpServer fail,verify code :" + response.j());
                this.c.setDuration(System.currentTimeMillis() - this.d);
                b.this.l.a(this.c);
                b.this.l.d(TabResendProgressEvent.STEP_RESTART_HTTP_SERVER);
                SharePreferenceUtils.setPrefBoolean(this.f, "reload_main_webview", true);
                com.leedarson.base.webservice.utils.b.b().i(this.f, "MultiWebViewManager.reloadMainVerify.onResponse ");
                if (this.q) {
                    org.greenrobot.eventbus.c.c().l(new TabResendProgressEvent(TabResendProgressEvent.STEP_DIAGNOSIS_WEBVIEW, this.c.getDuration(), this.c.getCode()));
                    return;
                }
                return;
            }
            this.c.setResponse("verifyHttpServer success");
            this.c.setDuration(System.currentTimeMillis() - this.d);
            b.this.l.a(this.c);
            b.this.l.e();
            if (this.q) {
                org.greenrobot.eventbus.c.c().l(new TabResendProgressEvent(TabResendProgressEvent.STEP_DIAGNOSIS_WEBVIEW, this.c.getDuration(), this.c.getCode()));
            }
        }
    }

    public void q(String event, String msg) {
        a.b g2 = timber.log.a.g("MultiWebViewManager");
        g2.a("preLoad View ---> " + msg, new Object[0]);
        com.leedarson.log.elk.a.y(this).t("LdsWebView").e(event).p(msg).a().b();
    }
}
