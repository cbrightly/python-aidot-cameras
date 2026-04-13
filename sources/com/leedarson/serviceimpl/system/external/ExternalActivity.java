package com.leedarson.serviceimpl.system.external;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.m;
import com.leedarson.base.views.ExternalWebView;
import com.leedarson.base.views.StrokeTextView;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.Constants;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceimpl.system.R$anim;
import com.leedarson.serviceimpl.system.R$id;
import com.leedarson.serviceimpl.system.R$layout;
import com.leedarson.serviceimpl.system.k;
import com.leedarson.serviceinterface.LoggerService;
import com.leedarson.serviceinterface.ZendeskService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.LinkAlexaEvent;
import com.leedarson.serviceinterface.event.WebViewSetNavFolatEvent;
import com.leedarson.serviceinterface.event.WebViewSetTitleEvent;
import com.leedarson.serviceinterface.event.WebviewCloseEvent;
import com.leedarson.serviceinterface.event.WebviewDidRenderEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.l;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class ExternalActivity extends AppCompatActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    int A4 = -225739;
    /* access modifiers changed from: private */
    public ProgressBar B4;
    private boolean C4 = true;
    private boolean D4 = false;
    /* access modifiers changed from: private */
    public boolean E4 = false;
    private Handler F4;
    private View G4;
    private View H4;
    private f I4 = new f();
    /* access modifiers changed from: private */
    public LinearLayout a1;
    /* access modifiers changed from: private */
    public String a2 = "";
    /* access modifiers changed from: private */
    public ExternalWebView c;
    private ImageView d;
    private ImageView f;
    private String p0;
    /* access modifiers changed from: private */
    public String p1 = "";
    private JSONArray p2;
    private Timer p3 = new Timer();
    private String p4;
    private ImageView q;
    /* access modifiers changed from: private */
    public StrokeTextView x;
    /* access modifiers changed from: private */
    public LDSTextView y;
    private View z;
    private String z4 = "";

    static /* synthetic */ void M(ExternalActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8911, new Class[]{ExternalActivity.class}, Void.TYPE).isSupported) {
            x0.b0();
        }
    }

    static /* synthetic */ void S(ExternalActivity x0, String x1, Context x2) {
        Class[] clsArr = {ExternalActivity.class, String.class, Context.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 8912, clsArr, Void.TYPE).isSupported) {
            x0.x0(x1, x2);
        }
    }

    static /* synthetic */ void T(ExternalActivity x0, String x1) {
        Class[] clsArr = {ExternalActivity.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 8913, clsArr, Void.TYPE).isSupported) {
            x0.v0(x1);
        }
    }

    @SuppressLint({"ResourceAsColor", "SetJavaScriptEnabled"})
    public void onCreate(Bundle savedInstanceState) {
        String str;
        String str2;
        boolean z2;
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 8883, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            org.greenrobot.eventbus.c.c().p(this);
            setContentView(R$layout.activity_external);
            com.leedarson.base.utils.c.h().a(this);
            getWindow().setFlags(16777216, 16777216);
            getWindow().clearFlags(1024);
            getWindow().getDecorView().setOnApplyWindowInsetsListener(new a());
            initView();
            String repositoryName = SharePreferenceUtils.getPrefString(this, "repositoryName", "");
            if (repositoryName.equals("C610-Innr")) {
                str2 = "";
                AssetManager mgr = getAssets();
                str = "backButton";
                Typeface tf = Typeface.createFromAsset(mgr, "NeurialGrotesk.ttf");
                AssetManager assetManager = mgr;
                this.x.setTypeface(tf);
                Typeface typeface = tf;
                this.x.getPaint().setFakeBoldText(true);
            } else {
                str = "backButton";
                str2 = "";
            }
            b0();
            this.c.setUseLoadingView(repositoryName.equals("M071-AiDot"));
            this.c.getWebView().setAliasKey(Constants.SERVICE_EXTERNAL);
            this.c.setActivity(this);
            this.c.setWebViewClient(new b());
            this.c.setWebViewChromeClient(new c());
            this.f.setOnClickListener(new d(this));
            this.d.setOnClickListener(new b(this));
            this.q.setOnClickListener(new e(this));
            this.H4.setOnClickListener(new f(this));
            String stringExtra = getIntent().getStringExtra("data");
            this.p0 = stringExtra;
            if (!TextUtils.isEmpty(stringExtra)) {
                try {
                    JSONObject jsonObject = new JSONObject(this.p0);
                    String str3 = "closeButton";
                    if (jsonObject.has("statusBarStyle")) {
                        int barStyle = jsonObject.getInt("statusBarStyle");
                        if (barStyle == 1) {
                            StatusBarUtil.setLightMode(this);
                        } else if (barStyle == 2) {
                            StatusBarUtil.setDarkMode(this);
                        }
                    } else {
                        StatusBarUtil.setLightMode(this);
                    }
                    this.z4 = jsonObject.has(CacheEntity.KEY) ? jsonObject.getString(CacheEntity.KEY) : str2;
                    String url = jsonObject.has("url") ? jsonObject.getString("url") : str2;
                    String cookie = jsonObject.has(SerializableCookie.COOKIE) ? jsonObject.get(SerializableCookie.COOKIE).toString() : str2;
                    this.p1 = jsonObject.has("redirectUri") ? jsonObject.get("redirectUri").toString() : str2;
                    this.a2 = jsonObject.has("closeByUrl") ? jsonObject.get("closeByUrl").toString() : str2;
                    this.p2 = jsonObject.has("clearDataOnClose") ? jsonObject.getJSONArray("clearDataOnClose") : new JSONArray();
                    if (jsonObject.has("navBar")) {
                        LinkedTreeMap barTree = m.b(jsonObject.optString("navBar"));
                        if (barTree != null) {
                            String backColor = barTree.containsKey("backButtonColor") ? barTree.get("backButtonColor").toString() : str2;
                            String title = barTree.containsKey("title") ? barTree.get("title").toString() : str2;
                            this.E4 = !TextUtils.isEmpty(title);
                            String titleColor = barTree.containsKey("titleColor") ? barTree.get("titleColor").toString() : str2;
                            if (barTree.containsKey("backgroundColor")) {
                                str2 = barTree.get("backgroundColor").toString();
                            }
                            String titleBarColor = str2;
                            if (!TextUtils.isEmpty(titleBarColor)) {
                                this.z.setBackgroundColor(Color.parseColor(titleBarColor));
                            }
                            if (repositoryName.equals("C610-Innr")) {
                                z2 = false;
                                this.x.setVisibility(0);
                                this.x.setText(title);
                            } else {
                                z2 = false;
                                this.y.setVisibility(0);
                                this.y.setText(title);
                            }
                            if (!TextUtils.isEmpty(titleColor)) {
                                this.x.setTextColor(Color.parseColor(titleColor));
                                this.y.setTextColor(Color.parseColor(titleColor));
                            }
                            if (!TextUtils.isEmpty(backColor)) {
                                this.d.setColorFilter(Color.parseColor(backColor));
                                this.f.setColorFilter(Color.parseColor(backColor));
                            }
                            boolean booleanValue = barTree.containsKey(H5ActionName.ACTION_HIDDEN_LIVE) ? ((Boolean) barTree.get(H5ActionName.ACTION_HIDDEN_LIVE)).booleanValue() : z2;
                            this.D4 = booleanValue;
                            if (booleanValue) {
                                this.z.setVisibility(8);
                                this.c.v(com.leedarson.serviceimpl.system.notch.b.b(this));
                            } else {
                                this.z.setVisibility(z2 ? 1 : 0);
                            }
                            if (barTree.containsKey("float") && ((Boolean) barTree.get("float")).booleanValue()) {
                                this.z.setVisibility(8);
                                this.c.v(com.leedarson.serviceimpl.system.notch.b.b(this));
                                this.G4.setVisibility(0);
                            }
                        }
                    }
                    String title2 = str3;
                    if (jsonObject.has(title2)) {
                        boolean hidden = false;
                        JSONObject closeBtnObj = jsonObject.getJSONObject(title2);
                        if (closeBtnObj != null) {
                            hidden = closeBtnObj.optBoolean(H5ActionName.ACTION_HIDDEN_LIVE);
                        }
                        this.d.setVisibility(hidden ? 8 : 0);
                    } else {
                        this.d.setVisibility(0);
                    }
                    String str4 = str;
                    if (jsonObject.has(str4)) {
                        this.C4 = jsonObject.optJSONObject(str4).optBoolean(H5ActionName.ACTION_HIDDEN_LIVE, true);
                    }
                    try {
                        JSONObject pbjson = jsonObject.getJSONObject("progressBar");
                        if (pbjson.has(TypedValues.Custom.S_COLOR)) {
                            this.A4 = Color.parseColor(pbjson.getString(TypedValues.Custom.S_COLOR));
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(url)) {
                        if (url.substring(url.length() - 4).equals(".pdf")) {
                            this.c.getWebSettings().setCacheMode(2);
                            ExternalWebView externalWebView = this.c;
                            externalWebView.G("file:///android_asset/loadPdf.html?url=" + url);
                        } else {
                            this.c.getWebSettings().setCacheMode(-1);
                            this.c.G(url);
                        }
                    }
                    if (!TextUtils.isEmpty(cookie)) {
                        z0(this, url, cookie);
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            } else {
                String stringExtra2 = getIntent().getStringExtra("advertUrl");
                this.p4 = stringExtra2;
                if (!TextUtils.isEmpty(stringExtra2)) {
                    StatusBarUtil.setLightMode(this);
                    this.z.setBackgroundColor(getResources().getColor(17170443));
                    this.d.setColorFilter(getResources().getColor(17170444));
                    this.f.setColorFilter(getResources().getColor(17170444));
                    this.x.setTextColor(getResources().getColor(17170444));
                    this.y.setTextColor(getResources().getColor(17170444));
                    this.c.getWebSettings().setCacheMode(-1);
                    this.c.getWebView().setWebChromeClient(a0());
                    this.c.G(this.p4);
                }
            }
            String callbackKey = getIntent().getStringExtra("callbackKey");
            if (!TextUtils.isEmpty(callbackKey)) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("code", 200);
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
                org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(callbackKey, obj.toString()));
            }
            y0(this.B4, -1, this.A4);
            this.p3.schedule(new d(), 50000);
            c0();
        }
    }

    public class a implements View.OnApplyWindowInsetsListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, windowInsets}, this, changeQuickRedirect2, false, 8914, new Class[]{View.class, WindowInsets.class}, WindowInsets.class);
            if (proxy.isSupported) {
                return (WindowInsets) proxy.result;
            }
            ExternalActivity.M(ExternalActivity.this);
            ExternalActivity.this.getWindow().getDecorView().setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) null);
            return view.onApplyWindowInsets(windowInsets);
        }
    }

    public class b extends WebViewClient {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 8915, new Class[]{WebView.class, String.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            if (!TextUtils.isEmpty(ExternalActivity.this.a2)) {
                Uri uri = Uri.parse(url);
                Uri rUri = Uri.parse(ExternalActivity.this.a2);
                if (uri != null && !TextUtils.isEmpty(uri.getHost()) && uri.getHost().equals(rUri.getHost())) {
                    JSONObject jsonData = new JSONObject((Map<?, ?>) k.f(uri));
                    ExternalActivity.this.finish();
                    org.greenrobot.eventbus.c.c().l(new LinkAlexaEvent(Constants.SERVICE_WEBVIEW, jsonData, "closeByUrl", ExternalActivity.this.c.getWebView()));
                    return true;
                }
            }
            if (url.startsWith(l.DEFAULT_SCHEME_NAME)) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            a.b g = timber.log.a.g("ExternalActivity");
            g.h("拦截加载了非http的url:" + url, new Object[0]);
            ExternalActivity externalActivity = ExternalActivity.this;
            ExternalActivity.S(externalActivity, url, externalActivity);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            Class[] clsArr = {WebView.class, String.class};
            if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 8916, clsArr, Void.TYPE).isSupported) {
                ExternalActivity externalActivity = ExternalActivity.this;
                ExternalActivity.T(externalActivity, "onPageFinished :" + view.getTitle() + ",progress:" + view.getProgress());
                if (view.getProgress() == 100) {
                    org.greenrobot.eventbus.c.c().l(new LinkAlexaEvent(Constants.SERVICE_WEBVIEW, new JSONObject(), ZendeskService.ACTION_OPEN, ExternalActivity.this.c.getWebView()));
                }
                if (!ExternalActivity.this.E4) {
                    ExternalActivity.this.x.setText(view.getTitle());
                    ExternalActivity.this.y.setText(view.getTitle());
                }
                ExternalActivity.this.a1.setVisibility(8);
                ExternalActivity.this.B4.setVisibility(8);
                if (!TextUtils.isEmpty(ExternalActivity.this.p1)) {
                    Uri uri = Uri.parse(url);
                    Uri rUri = Uri.parse(ExternalActivity.this.p1);
                    if (uri != null && !TextUtils.isEmpty(uri.getHost()) && uri.getHost().equals(rUri.getHost())) {
                        Intent intent = new Intent();
                        intent.setData(uri);
                        ExternalActivity.this.setResult(-1, intent);
                        ExternalActivity.this.finish();
                    }
                }
            }
        }
    }

    public class c extends WebChromeClient {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onProgressChanged(WebView view, int newProgress) {
            if (!PatchProxy.proxy(new Object[]{view, new Integer(newProgress)}, this, changeQuickRedirect, false, 8917, new Class[]{WebView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.onProgressChanged(view, newProgress);
            }
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: k0 */
    public /* synthetic */ void l0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8910, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        if (this.c.o()) {
            this.c.x();
        } else {
            Z("back");
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: m0 */
    public /* synthetic */ void n0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8909, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        Z("close");
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: q0 */
    public /* synthetic */ void s0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8908, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        Z("close");
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: t0 */
    public /* synthetic */ void u0(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8907, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        Z("close");
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public class d extends TimerTask {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8919, new Class[0], Void.TYPE).isSupported) {
                ExternalActivity.this.a1.setVisibility(8);
            }
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8918, new Class[0], Void.TYPE).isSupported) {
                ExternalActivity.this.runOnUiThread(new a(this));
            }
        }
    }

    private void b0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8884, new Class[0], Void.TYPE).isSupported) {
            int i = Build.VERSION.SDK_INT;
            if (i >= 19) {
                getWindow().getAttributes().flags |= 67108864;
            }
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                int result = getResources().getDimensionPixelSize(resourceId);
            }
            int result2 = com.leedarson.serviceimpl.system.notch.b.b(this);
            timber.log.a.g("ExternalActivity").h("状态栏高度: " + result2, new Object[0]);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.q.getLayoutParams();
            layoutParams.topMargin = result2 + 24;
            this.q.setLayoutParams(layoutParams);
            this.z.setPadding(0, result2, 0, 0);
            ((RelativeLayout.LayoutParams) this.G4.getLayoutParams()).topMargin = result2 - k.d(this, 3.0f);
            if (i >= 21) {
                Window window = getWindow();
                window.clearFlags(67108864);
                window.getDecorView().setSystemUiVisibility(AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ);
                window.addFlags(Integer.MIN_VALUE);
                window.setStatusBarColor(0);
            } else if (i >= 19) {
                getWindow().addFlags(67108864);
            }
        }
    }

    public void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8885, new Class[0], Void.TYPE).isSupported) {
            this.B4 = (ProgressBar) findViewById(R$id.hor_progressbar);
            this.c = (ExternalWebView) findViewById(R$id.external_webview);
            this.q = (ImageView) findViewById(R$id.iv_close);
            this.d = (ImageView) findViewById(R$id.btn_back);
            this.f = (ImageView) findViewById(R$id.btn_cangoback);
            this.x = (StrokeTextView) findViewById(R$id.text_title);
            this.y = (LDSTextView) findViewById(R$id.text_title2);
            this.z = findViewById(R$id.title_layout);
            this.a1 = (LinearLayout) findViewById(R$id.progress_layout);
            this.G4 = findViewById(R$id.extra_goback);
            this.H4 = findViewById(R$id.extra_goback_frame_area);
        }
    }

    private void c0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8886, new Class[0], Void.TYPE).isSupported) {
            if (this.D4 && !this.C4) {
                this.G4.setVisibility(0);
            }
        }
    }

    public class e extends WebChromeClient {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void onReceivedTitle(WebView view, String title) {
            Class[] clsArr = {WebView.class, String.class};
            if (!PatchProxy.proxy(new Object[]{view, title}, this, changeQuickRedirect, false, 8920, clsArr, Void.TYPE).isSupported) {
                super.onReceivedTitle(view, title);
            }
        }
    }

    private WebChromeClient a0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8887, new Class[0], WebChromeClient.class);
        return proxy.isSupported ? (WebChromeClient) proxy.result : new e();
    }

    private void z0(Context context, String url, String cookie) {
        Class<String> cls = String.class;
        Class[] clsArr = {Context.class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{context, url, cookie}, this, changeQuickRedirect, false, 8888, clsArr, Void.TYPE).isSupported) {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setCookie(url, cookie);
            CookieSyncManager.getInstance().sync();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Object[] objArr = {new Integer(keyCode), event};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 8889, new Class[]{Integer.TYPE, KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (!this.c.y()) {
            if (this.c.o()) {
                this.c.x();
            } else {
                Z("close");
            }
        }
        return true;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        Object[] objArr = {new Byte(hasFocus ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8890, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            super.onWindowFocusChanged(hasFocus);
            if (hasFocus) {
                v0("onWindowFocusChanged");
                StatusBarUtil.setStatusBarTextAuto(this, (WebView) null);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Object[] objArr = {new Integer(requestCode), new Integer(resultCode), data};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8891, new Class[]{cls, cls, Intent.class}, Void.TYPE).isSupported) {
            super.onActivityResult(requestCode, resultCode, data);
            v0("onActivityResult requestCode:" + requestCode);
            if (requestCode == 111) {
                this.c.u(resultCode, data);
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8892, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            String str = this.p0;
            if (str != null) {
                ExternalServiceImpl.a.remove(str);
            }
            Timer timer = this.p3;
            if (timer != null) {
                timer.cancel();
            }
            JSONArray jSONArray = this.p2;
            if (jSONArray != null && jSONArray.length() > 0 && this.c.getUrlList().size() > 0) {
                this.c.r();
            }
            ExternalWebView externalWebView = this.c;
            if (externalWebView != null) {
                externalWebView.J();
            }
            Handler handler = this.F4;
            if (handler != null) {
                handler.removeCallbacks((Runnable) null);
            }
            org.greenrobot.eventbus.c.c().r(this);
            com.leedarson.base.utils.c.h().d(this);
        }
    }

    private void v0(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 8893, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("ExternalActivity").a(log, new Object[0]);
        }
    }

    public void y0(ProgressBar progressBar, int backgroundColor, int progressColor) {
        Object[] objArr = {progressBar, new Integer(backgroundColor), new Integer(progressColor)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {ProgressBar.class, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8894, clsArr, Void.TYPE).isSupported) {
            ClipDrawable bgClipDrawable = new ClipDrawable(new ColorDrawable(backgroundColor), 3, 1);
            bgClipDrawable.setLevel(10000);
            ClipDrawable progressClip = new ClipDrawable(new ColorDrawable(progressColor), 3, 1);
            LayerDrawable progressLayerDrawable = new LayerDrawable(new Drawable[]{bgClipDrawable, progressClip, progressClip});
            progressLayerDrawable.setId(0, 16908288);
            progressLayerDrawable.setId(1, 16908303);
            progressLayerDrawable.setId(2, 16908301);
            progressBar.setProgressDrawable(progressLayerDrawable);
        }
    }

    private void Z(String action) {
        if (!PatchProxy.proxy(new Object[]{action}, this, changeQuickRedirect, false, 8895, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.F4 == null) {
                this.F4 = new Handler();
            }
            LinkAlexaEvent event = new LinkAlexaEvent(Constants.SERVICE_WEBVIEW, new JSONObject(), action, this.c.getWebView());
            event.onBridgeRespListener = new c(this);
            event.flagOnlyNotifyCurrentWebView = "back".equals(action);
            org.greenrobot.eventbus.c.c().l(event);
            this.F4.postDelayed(this.I4, 300);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: e0 */
    public /* synthetic */ void f0(String data, WVJBWebView wVJBWebView) {
        if (!PatchProxy.proxy(new Object[]{data, wVJBWebView}, this, changeQuickRedirect, false, 8906, new Class[]{String.class, WVJBWebView.class}, Void.TYPE).isSupported) {
            try {
                JSONArray dataArray = new JSONObject(data).optJSONArray("data");
                if (dataArray != null && dataArray.length() > 0) {
                    JSONObject jsonObject = dataArray.getJSONObject(0);
                    int status = jsonObject.optInt("status");
                    int code = jsonObject.optInt("code");
                    if (!(status == 200 || code == 200)) {
                        if (status != 1) {
                            String url = this.c.getWebView().getUrl();
                            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
                            if (loggerService != null) {
                                loggerService.reportELK(this, "ExternalActivity 关闭拦截， closeCheck onResult:" + data + ",url:" + url, "info", "closeWebView");
                            }
                            this.F4.removeCallbacks(this.I4);
                            return;
                        }
                    }
                    this.F4.post(this.I4);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public final class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8921, new Class[0], Void.TYPE).isSupported) {
                ExternalActivity externalActivity = ExternalActivity.this;
                ExternalActivity.T(externalActivity, "收到 action ExternalActivity ClosePageTimeoutTask ClosePageTimeoutTask:" + ExternalActivity.this.toString());
                ExternalActivity.this.finish();
                ExternalActivity.this.overridePendingTransition(R$anim.right_in_animation, R$anim.right_out_animation);
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void H5WebviewCloseEvent(WebviewCloseEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 8896, new Class[]{WebviewCloseEvent.class}, Void.TYPE).isSupported) {
            String url = this.c.getWebView().getUrl();
            v0("收到 H5WebviewCloseEvent 进行关闭,url:" + url);
            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            if (loggerService != null) {
                loggerService.reportELK(this, "收到 H5WebviewCloseEvent 进行关闭,url:" + url, "info", "closeWebView");
            }
            Handler handler = this.F4;
            if (handler != null) {
                handler.removeCallbacks(this.I4);
            }
            WVJBWebView wVJBWebView = event.webView;
            if (wVJBWebView != null && wVJBWebView.hashCode() == this.c.getWebView().hashCode()) {
                finish();
                overridePendingTransition(R$anim.right_in_animation, R$anim.right_out_animation);
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void onWebviewDidRenderEvent(WebviewDidRenderEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 8897, new Class[]{WebviewDidRenderEvent.class}, Void.TYPE).isSupported) {
            WVJBWebView wVJBWebView = event.webView;
            if (wVJBWebView != null && wVJBWebView.hashCode() == this.c.getWebView().hashCode()) {
                this.c.t(true);
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void onSetTitleChangel(WebViewSetTitleEvent event) {
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 8898, new Class[]{WebViewSetTitleEvent.class}, Void.TYPE).isSupported) {
            if (event.webView != null && this.c.getWebView().hashCode() == event.webView.hashCode() && !TextUtils.isEmpty(event.title)) {
                this.x.setText(event.title);
                this.y.setText(event.title);
            }
        }
    }

    @org.greenrobot.eventbus.l
    public void setNavbarBackFloatHidden(WebViewSetNavFolatEvent event) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 8899, new Class[]{WebViewSetNavFolatEvent.class}, Void.TYPE).isSupported) {
            if (event.webView != null && this.c.getWebView().hashCode() == event.webView.hashCode()) {
                View view = this.G4;
                if (event.floatMode) {
                    i = 8;
                }
                view.setVisibility(i);
            }
        }
    }

    private void x0(String url, Context context) {
        if (!PatchProxy.proxy(new Object[]{url, context}, this, changeQuickRedirect, false, 8900, new Class[]{String.class, Context.class}, Void.TYPE).isSupported) {
            if (url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, 1);
                    if (intent == null) {
                        return;
                    }
                    if (context.getPackageManager().resolveActivity(intent, 65536) != null) {
                        context.startActivity(intent);
                        finish();
                        return;
                    }
                    this.c.G(intent.getStringExtra("browser_fallback_url"));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8901, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            timber.log.a.i("ExternalActivity  onResume ", new Object[0]);
            w0(0);
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8902, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            timber.log.a.i("ExternalActivity  onPause", new Object[0]);
            w0(2);
        }
    }

    /* access modifiers changed from: package-private */
    public void w0(int status) {
        Object[] objArr = {new Integer(status)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8903, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("status", status);
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            LinkAlexaEvent event = new LinkAlexaEvent(Constants.SERVICE_WEBVIEW, jsonObject, "onActiveChange", this.c.getWebView());
            event.flagOnlyNotifyCurrentWebView = true;
            event.onBridgeRespListener = new g(this);
            org.greenrobot.eventbus.c.c().l(event);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: g0 */
    public /* synthetic */ void j0(String data, WVJBWebView wVJBWebView) {
        Class[] clsArr = {String.class, WVJBWebView.class};
        if (!PatchProxy.proxy(new Object[]{data, wVJBWebView}, this, changeQuickRedirect, false, 8905, clsArr, Void.TYPE).isSupported) {
            v0("收到 action ExternalActivity onBridgeRespListener data:" + data + "    ExternalActivity=" + toString());
        }
    }
}
