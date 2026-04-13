package com.leedarson.base.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.FileProvider;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.w;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.leedarson.serviceinterface.JsbridgeService;
import com.leedarson.serviceinterface.LoggerService;
import com.luck.picture.lib.config.PictureMimeType;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.Constants;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class ExternalWebView extends FrameLayout {
    protected static final FrameLayout.LayoutParams c = new FrameLayout.LayoutParams(-1, -1);
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int A4;
    private i B4;
    private long C4;
    String D4;
    String E4;
    int F4;
    /* access modifiers changed from: private */
    public List<String> G4;
    LDSSkeleton H4;
    private int I4;
    private boolean J4;
    /* access modifiers changed from: private */
    public h K4;
    /* access modifiers changed from: private */
    public com.tbruyelle.rxpermissions2.b a1;
    private View a2;
    /* access modifiers changed from: private */
    public String d;
    /* access modifiers changed from: private */
    public WVJBWebView f;
    /* access modifiers changed from: private */
    public ValueCallback<Uri[]> p0;
    /* access modifiers changed from: private */
    public int p1;
    private FrameLayout p2;
    private WebChromeClient.CustomViewCallback p3;
    private LoadingView p4;
    private WebErrorView q;
    private View x;
    private Activity y;
    private WebSettings z;
    private Handler z4;

    public interface h {
        void a(ExternalWebView externalWebView);
    }

    static /* synthetic */ void a(ExternalWebView x0, String x1) {
        Class[] clsArr = {ExternalWebView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 689, clsArr, Void.TYPE).isSupported) {
            x0.I(x1);
        }
    }

    static /* synthetic */ int h(ExternalWebView x0) {
        int i2 = x0.p1;
        x0.p1 = i2 + 1;
        return i2;
    }

    static /* synthetic */ void i(ExternalWebView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 690, new Class[]{ExternalWebView.class}, Void.TYPE).isSupported) {
            x0.N();
        }
    }

    static /* synthetic */ void j(ExternalWebView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 691, new Class[]{ExternalWebView.class}, Void.TYPE).isSupported) {
            x0.q();
        }
    }

    static /* synthetic */ void m(ExternalWebView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 692, new Class[]{ExternalWebView.class}, Void.TYPE).isSupported) {
            x0.p();
        }
    }

    static /* synthetic */ void n(ExternalWebView x0, View x1, WebChromeClient.CustomViewCallback x2) {
        Class[] clsArr = {ExternalWebView.class, View.class, WebChromeClient.CustomViewCallback.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 693, clsArr, Void.TYPE).isSupported) {
            x0.L(x1, x2);
        }
    }

    public ExternalWebView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public ExternalWebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.d = "";
        this.p1 = 0;
        this.A4 = 5000;
        this.B4 = new i(this, (a) null);
        this.F4 = 111;
        this.I4 = 0;
        this.J4 = false;
        C();
    }

    public void setActivity(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 655, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            this.y = activity;
            this.a1 = new com.tbruyelle.rxpermissions2.b(activity);
            K(activity);
        }
    }

    private void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 656, new Class[0], Void.TYPE).isSupported) {
            this.G4 = new ArrayList();
            LayoutInflater.from(getContext()).inflate(R$layout.view_external_webview, this, true);
            this.f = (WVJBWebView) findViewById(R$id.js_bridge_web_view);
            this.p4 = (LoadingView) findViewById(R$id.lv_loading);
            this.x = findViewById(R$id.layout_loading);
            D();
            F();
        }
    }

    private void F() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 657, new Class[0], Void.TYPE).isSupported) {
            WebSettings settings = this.f.getSettings();
            this.z = settings;
            settings.setDomStorageEnabled(true);
            if (Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(this.f, true);
                this.z.setMixedContentMode(0);
            }
            this.z.setAllowFileAccess(false);
            this.z.setSupportZoom(true);
            this.z.setJavaScriptEnabled(true);
            this.z.setLoadWithOverviewMode(true);
            this.z.setUseWideViewPort(true);
            this.z.setTextZoom(100);
            String ua = this.f.getSettings().getUserAgentString();
            this.f.getSettings().setUserAgentString(ua + " Leedarson GoogleHome");
            setWebViewClient((WebViewClient) null);
            setWebViewChromeClient(new f((WebChromeClient) null));
        }
    }

    private void K(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 658, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            JsbridgeService jsbridgeService = (JsbridgeService) com.alibaba.android.arouter.launcher.a.c().g(JsbridgeService.class);
            if (jsbridgeService != null) {
                jsbridgeService.registerJsCallNative(activity, this.f, "JsCallNative");
            }
        }
    }

    private void N() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 659, new Class[0], Void.TYPE).isSupported) {
            I("onShowFileChooser");
            Intent intent1 = new Intent("android.intent.action.GET_CONTENT");
            intent1.addCategory("android.intent.category.OPENABLE");
            intent1.setType(com.yanzhenjie.andserver.util.h.ALL_VALUE);
            Intent videoIntent = new Intent("android.media.action.VIDEO_CAPTURE");
            StringBuilder sb = new StringBuilder();
            sb.append(getContext().getCacheDir());
            String str = File.separator;
            sb.append(str);
            sb.append("externalVideo_");
            sb.append(System.currentTimeMillis());
            sb.append(".mp4");
            this.E4 = sb.toString();
            int i2 = Build.VERSION.SDK_INT;
            if (i2 >= 24) {
                Context context = getContext();
                videoIntent.putExtra("output", FileProvider.getUriForFile(context, getContext().getPackageName() + ".fileProvider", new File(this.E4)));
                videoIntent.setFlags(2);
            } else {
                videoIntent.putExtra("output", Uri.fromFile(new File(this.E4)));
            }
            videoIntent.putExtra("android.intent.extra.videoQuality", 0.9d);
            Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
            this.D4 = getContext().getCacheDir() + str + "externalPhoto_" + System.currentTimeMillis() + PictureMimeType.PNG;
            if (i2 >= 24) {
                Context context2 = getContext();
                intent2.putExtra("output", FileProvider.getUriForFile(context2, getContext().getPackageName() + ".fileProvider", new File(this.D4)));
                intent2.setFlags(2);
            } else {
                intent2.putExtra("output", Uri.fromFile(new File(this.D4)));
            }
            Intent chooser = new Intent("android.intent.action.CHOOSER");
            chooser.putExtra("android.intent.extra.TITLE", "File Chooser");
            chooser.putExtra("android.intent.extra.INTENT", intent1);
            chooser.putExtra("android.intent.extra.INITIAL_INTENTS", new Intent[]{intent2, videoIntent});
            this.y.startActivityForResult(chooser, this.F4);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0052 A[Catch:{ Exception -> 0x00c6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0082 A[Catch:{ Exception -> 0x00c6 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0089 A[Catch:{ Exception -> 0x00c6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void u(int r11, @androidx.annotation.Nullable android.content.Intent r12) {
        /*
            r10 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r11)
            r8 = 0
            r1[r8] = r2
            r9 = 1
            r1[r9] = r12
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r8] = r0
            java.lang.Class<android.content.Intent> r0 = android.content.Intent.class
            r6[r9] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 660(0x294, float:9.25E-43)
            r2 = r10
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0029
            return
        L_0x0029:
            r0 = r10
            java.lang.String r1 = "fileChooseCallback"
            r0.I(r1)     // Catch:{ Exception -> 0x00c6 }
            if (r12 == 0) goto L_0x003a
            r1 = -1
            if (r11 == r1) goto L_0x0035
            goto L_0x003a
        L_0x0035:
            android.net.Uri r1 = r12.getData()     // Catch:{ Exception -> 0x00c6 }
            goto L_0x003b
        L_0x003a:
            r1 = 0
        L_0x003b:
            if (r1 != 0) goto L_0x0065
            java.lang.String r2 = r0.D4     // Catch:{ Exception -> 0x00c6 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x00c6 }
            if (r2 != 0) goto L_0x0065
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r3 = r0.D4     // Catch:{ Exception -> 0x00c6 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00c6 }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00c6 }
            if (r3 == 0) goto L_0x0065
            android.net.Uri r3 = android.net.Uri.fromFile(r2)     // Catch:{ Exception -> 0x00c6 }
            r1 = r3
            android.content.Context r3 = r0.getContext()     // Catch:{ Exception -> 0x00c6 }
            android.content.Intent r4 = new android.content.Intent     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r5 = "android.intent.action.MEDIA_SCANNER_SCAN_FILE"
            r4.<init>(r5, r1)     // Catch:{ Exception -> 0x00c6 }
            r3.sendBroadcast(r4)     // Catch:{ Exception -> 0x00c6 }
        L_0x0065:
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ Exception -> 0x00c6 }
            r3 = 31
            if (r2 < r3) goto L_0x0087
            if (r1 != 0) goto L_0x0087
            java.lang.String r2 = r0.E4     // Catch:{ Exception -> 0x00c6 }
            boolean r2 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Exception -> 0x00c6 }
            if (r2 != 0) goto L_0x0087
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r3 = r0.E4     // Catch:{ Exception -> 0x00c6 }
            r2.<init>(r3)     // Catch:{ Exception -> 0x00c6 }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x00c6 }
            if (r3 == 0) goto L_0x0087
            android.net.Uri r3 = android.net.Uri.fromFile(r2)     // Catch:{ Exception -> 0x00c6 }
            r1 = r3
        L_0x0087:
            if (r1 == 0) goto L_0x00c5
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c6 }
            r2.<init>()     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r3 = "onReceiveValue:"
            r2.append(r3)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r3 = r1.getPath()     // Catch:{ Exception -> 0x00c6 }
            r2.append(r3)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00c6 }
            r0.I(r2)     // Catch:{ Exception -> 0x00c6 }
            android.webkit.ValueCallback<android.net.Uri[]> r2 = r0.p0     // Catch:{ Exception -> 0x00ab }
            android.net.Uri[] r3 = new android.net.Uri[r9]     // Catch:{ Exception -> 0x00ab }
            r3[r8] = r1     // Catch:{ Exception -> 0x00ab }
            r2.onReceiveValue(r3)     // Catch:{ Exception -> 0x00ab }
            goto L_0x00c4
        L_0x00ab:
            r2 = move-exception
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c6 }
            r3.<init>()     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r4 = "onReceiveValue--->ResultCallback"
            r3.append(r4)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r4 = r2.toString()     // Catch:{ Exception -> 0x00c6 }
            r3.append(r4)     // Catch:{ Exception -> 0x00c6 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x00c6 }
            r0.I(r3)     // Catch:{ Exception -> 0x00c6 }
        L_0x00c4:
            return
        L_0x00c5:
            goto L_0x00d0
        L_0x00c6:
            r1 = move-exception
            java.lang.String r2 = "ExternalWebView"
            timber.log.a$b r2 = timber.log.a.g(r2)
            r2.d(r1)
        L_0x00d0:
            r0.q()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.views.ExternalWebView.u(int, android.content.Intent):void");
    }

    private void L(View view, WebChromeClient.CustomViewCallback callback) {
        if (!PatchProxy.proxy(new Object[]{view, callback}, this, changeQuickRedirect, false, 661, new Class[]{View.class, WebChromeClient.CustomViewCallback.class}, Void.TYPE).isSupported) {
            if (this.a2 != null) {
                callback.onCustomViewHidden();
                return;
            }
            FullscreenHold fullscreenHold = new FullscreenHold(getContext());
            this.p2 = fullscreenHold;
            FrameLayout.LayoutParams layoutParams = c;
            fullscreenHold.addView(view, layoutParams);
            ((FrameLayout) this.y.getWindow().getDecorView()).addView(this.p2, layoutParams);
            timber.log.a.g("ExternalWebView").m("webview showCustomView 显示黑色", new Object[0]);
            ((FrameLayout.LayoutParams) this.p2.getLayoutParams()).bottomMargin = w.v(getContext());
            this.a2 = view;
            this.p3 = callback;
        }
    }

    public boolean y() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 662, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.a2 == null) {
            return false;
        }
        ((FrameLayout) this.y.getWindow().getDecorView()).removeView(this.p2);
        this.p2 = null;
        this.a2 = null;
        this.p3.onCustomViewHidden();
        this.f.setVisibility(0);
        return true;
    }

    public static class FullscreenHold extends FrameLayout {
        public static ChangeQuickRedirect changeQuickRedirect;

        public FullscreenHold(@NotNull Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(17170444));
        }

        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    private void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 663, new Class[0], Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 33) {
                if (!this.a1.f("android.permission.READ_MEDIA_IMAGES") || !this.a1.f("android.permission.READ_MEDIA_VIDEO") || !this.a1.f("android.permission.CAMERA")) {
                    this.a1.l("android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO", "android.permission.CAMERA").Y(new a(), new b());
                } else {
                    N();
                }
            } else if (!this.a1.f("android.permission.READ_EXTERNAL_STORAGE") || !this.a1.f("android.permission.WRITE_EXTERNAL_STORAGE") || !this.a1.f("android.permission.CAMERA")) {
                this.a1.l("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA").Y(new c(), new d());
            } else {
                N();
            }
        }
    }

    public class a implements io.reactivex.functions.e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 695, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 694, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                ExternalWebView externalWebView = ExternalWebView.this;
                ExternalWebView.a(externalWebView, "permission " + permission.a + ",isGranted:" + permission.b);
                if (!permission.b) {
                    ExternalWebView.j(ExternalWebView.this);
                } else if (ExternalWebView.this.a1.f("android.permission.READ_MEDIA_IMAGES") && ExternalWebView.this.a1.f("android.permission.READ_MEDIA_VIDEO") && ExternalWebView.this.a1.f("android.permission.CAMERA") && ExternalWebView.this.p1 == 0) {
                    ExternalWebView.i(ExternalWebView.this);
                    ExternalWebView.h(ExternalWebView.this);
                }
            }
        }
    }

    public class b implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 697, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 696, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                timber.log.a.g("ExternalWebView").d(throwable);
                ExternalWebView.j(ExternalWebView.this);
            }
        }
    }

    public class c implements io.reactivex.functions.e<com.tbruyelle.rxpermissions2.a> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 699, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((com.tbruyelle.rxpermissions2.a) obj);
            }
        }

        public void a(com.tbruyelle.rxpermissions2.a permission) {
            if (!PatchProxy.proxy(new Object[]{permission}, this, changeQuickRedirect, false, 698, new Class[]{com.tbruyelle.rxpermissions2.a.class}, Void.TYPE).isSupported) {
                ExternalWebView externalWebView = ExternalWebView.this;
                ExternalWebView.a(externalWebView, "permission " + permission.a + ",isGranted:" + permission.b);
                if (!permission.b) {
                    ExternalWebView.j(ExternalWebView.this);
                } else if (ExternalWebView.this.a1.f("android.permission.READ_EXTERNAL_STORAGE") && ExternalWebView.this.a1.f("android.permission.WRITE_EXTERNAL_STORAGE") && ExternalWebView.this.a1.f("android.permission.CAMERA") && ExternalWebView.this.p1 == 0) {
                    ExternalWebView.i(ExternalWebView.this);
                    ExternalWebView.h(ExternalWebView.this);
                }
            }
        }
    }

    public class d implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 701, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 700, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                timber.log.a.g("ExternalWebView").d(throwable);
                ExternalWebView.j(ExternalWebView.this);
            }
        }
    }

    private void q() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 664, new Class[0], Void.TYPE).isSupported) {
            I("clearUploadMessage");
            this.D4 = null;
            this.E4 = null;
            if (this.p0 != null) {
                I("clearUploadMessage#onReceiveValue");
                try {
                    this.p0.onReceiveValue((Object) null);
                    this.p0 = null;
                } catch (Exception ex) {
                    I("clearUploadMessage#onReceiveValue .exception=" + ex.toString());
                }
            }
        }
    }

    private void I(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 665, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("ExternalWebView").a(log, new Object[0]);
        }
    }

    public void setWebViewClient(WebViewClient webViewClientUI) {
        if (!PatchProxy.proxy(new Object[]{webViewClientUI}, this, changeQuickRedirect, false, 666, new Class[]{WebViewClient.class}, Void.TYPE).isSupported) {
            this.f.setWebViewClient(new g(webViewClientUI));
        }
    }

    public void setWebViewChromeClient(WebChromeClient chromeClientUI) {
        if (!PatchProxy.proxy(new Object[]{chromeClientUI}, this, changeQuickRedirect, false, 667, new Class[]{WebChromeClient.class}, Void.TYPE).isSupported) {
            this.f.setWebChromeClient(new f(chromeClientUI));
        }
    }

    public void G(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 668, new Class[]{String.class}, Void.TYPE).isSupported) {
            H(url, (Handler) null);
        }
    }

    public void H(String url, Handler handler) {
        Class[] clsArr = {String.class, Handler.class};
        if (!PatchProxy.proxy(new Object[]{url, handler}, this, changeQuickRedirect, false, 669, clsArr, Void.TYPE).isSupported) {
            this.z4 = handler;
            this.C4 = System.currentTimeMillis();
            if (handler != null && (("Community".equals(this.f.getAliasKey()) || "Shop".equals(this.f.getAliasKey())) && !"http://xx/".equals(url))) {
                handler.removeCallbacks(this.B4);
                handler.postDelayed(this.B4, KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS);
                I("启动 webview 加载超时检测 key:" + this.f.getAliasKey() + ",url:" + url);
            }
            this.d = url;
            I("update rootUrl=" + this.d + ",key:" + this.f.getAliasKey());
            E();
            O();
            WVJBWebView wVJBWebView = this.f;
            wVJBWebView.loadUrl(url);
            SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView, url);
        }
    }

    public void w() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 670, new Class[0], Void.TYPE).isSupported) {
            H(this.d, this.z4);
        }
    }

    public boolean o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 671, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            String currentUrl = this.f.getUrl().replace("/?", "?");
            String rUrl = this.d.replace("/?", "?");
            I("canGoBack  webView.getUrl:" + currentUrl + ",rootUrl:" + rUrl);
            if (currentUrl.equals(rUrl)) {
                return false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return this.f.canGoBack();
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 672, new Class[0], Void.TYPE).isSupported) {
            this.f.goBack();
        }
    }

    public void J() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 673, new Class[0], Void.TYPE).isSupported) {
            I("webview onDestroy");
            WVJBWebView wVJBWebView = this.f;
            if (wVJBWebView != null) {
                wVJBWebView.loadUrl("about:blank");
                SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView, "about:blank");
                ViewParent parent = this.f.getParent();
                if (parent != null && (parent instanceof ViewGroup)) {
                    ((ViewGroup) parent).removeView(this.f);
                }
                this.f.stopLoading();
                this.f.getSettings().setJavaScriptEnabled(false);
                this.f.clearHistory();
                this.f.clearCache(true);
                this.f.removeAllViewsInLayout();
                this.f.removeAllViews();
                this.f.setWebViewClient((WebViewClient) null);
                this.f.setWebChromeClient((WebChromeClient) null);
                this.f.destroy();
                this.f = null;
            }
        }
    }

    public void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 674, new Class[0], Void.TYPE).isSupported) {
            for (String domain : this.G4) {
                s(domain);
            }
        }
    }

    public WVJBWebView getWebView() {
        return this.f;
    }

    public WebSettings getWebSettings() {
        return this.z;
    }

    private static void s(String domain) {
        CookieManager cookieManager;
        if (!PatchProxy.proxy(new Object[]{domain}, (Object) null, changeQuickRedirect, true, 675, new Class[]{String.class}, Void.TYPE).isSupported && (cookieManager = CookieManager.getInstance()) != null) {
            if (Build.VERSION.SDK_INT < 11 && domain.startsWith(".")) {
                domain = domain.substring(1);
            }
            String cookieGlob = cookieManager.getCookie(domain);
            if (cookieGlob != null) {
                for (String cookieTuple : cookieGlob.split(Constants.PACKNAME_END)) {
                    String[] cookieParts = cookieTuple.split("=");
                    cookieManager.setCookie(domain, cookieParts[0] + "=; Expires=Wed, 31 Dec 2015 23:59:59 GMT");
                }
                CookieSyncManager.getInstance().sync();
            }
        }
    }

    public List<String> getUrlList() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 676, new Class[0], List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        if (this.G4 == null) {
            this.G4 = new ArrayList();
        }
        return this.G4;
    }

    public class g extends WebViewClient {
        public static ChangeQuickRedirect changeQuickRedirect;
        private WebViewClient a;

        public g(WebViewClient webViewClient) {
            this.a = webViewClient;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect2, false, 708, new Class[]{WebView.class, String.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            ExternalWebView.this.G4.add(url);
            WebViewClient webViewClient = this.a;
            if (webViewClient != null) {
                return webViewClient.shouldOverrideUrlLoading(view, url);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageFinished(WebView view, String url) {
            Class[] clsArr = {WebView.class, String.class};
            if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 709, clsArr, Void.TYPE).isSupported) {
                WebViewClient webViewClient = this.a;
                if (webViewClient != null) {
                    webViewClient.onPageFinished(view, url);
                } else {
                    super.onPageFinished(view, url);
                }
            }
        }

        @RequiresApi(api = 23)
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Class[] clsArr = {WebView.class, WebResourceRequest.class, WebResourceError.class};
            if (!PatchProxy.proxy(new Object[]{view, request, error}, this, changeQuickRedirect, false, 710, clsArr, Void.TYPE).isSupported) {
                WebViewClient webViewClient = this.a;
                if (webViewClient != null) {
                    webViewClient.onReceivedError(view, request, error);
                } else {
                    super.onReceivedError(view, request, error);
                }
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Class<String> cls = String.class;
            Object[] objArr = {view, new Integer(errorCode), description, failingUrl};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 711, new Class[]{WebView.class, Integer.TYPE, cls, cls}, Void.TYPE).isSupported) {
                WebViewClient webViewClient = this.a;
                if (webViewClient != null) {
                    webViewClient.onReceivedError(view, errorCode, description, failingUrl);
                } else {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            }
        }

        @RequiresApi(api = 23)
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Class[] clsArr = {WebView.class, WebResourceRequest.class, WebResourceResponse.class};
            if (!PatchProxy.proxy(new Object[]{view, request, errorResponse}, this, changeQuickRedirect, false, 712, clsArr, Void.TYPE).isSupported) {
                WebViewClient webViewClient = this.a;
                if (webViewClient != null) {
                    webViewClient.onReceivedHttpError(view, request, errorResponse);
                } else {
                    super.onReceivedHttpError(view, request, errorResponse);
                }
            }
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Class[] clsArr = {WebView.class, SslErrorHandler.class, SslError.class};
            if (!PatchProxy.proxy(new Object[]{view, handler, error}, this, changeQuickRedirect, false, 713, clsArr, Void.TYPE).isSupported) {
                WebViewClient webViewClient = this.a;
                if (webViewClient != null) {
                    webViewClient.onReceivedSslError(view, handler, error);
                } else {
                    super.onReceivedSslError(view, handler, error);
                }
            }
        }
    }

    public class f extends WebChromeClient {
        public static ChangeQuickRedirect changeQuickRedirect;
        private WebChromeClient a;

        public f(WebChromeClient chromeClient) {
            this.a = chromeClient;
        }

        public void onProgressChanged(WebView view, int newProgress) {
            Object[] objArr = {view, new Integer(newProgress)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH, new Class[]{WebView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                if (newProgress > 95) {
                    ExternalWebView.this.t(false);
                }
                WebChromeClient webChromeClient = this.a;
                if (webChromeClient != null) {
                    webChromeClient.onProgressChanged(view, newProgress);
                } else {
                    super.onProgressChanged(view, newProgress);
                }
            }
        }

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{webView, filePathCallback, fileChooserParams}, this, changeQuickRedirect, false, TypedValues.TransitionType.TYPE_AUTO_TRANSITION, new Class[]{WebView.class, ValueCallback.class, WebChromeClient.FileChooserParams.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            ExternalWebView.a(ExternalWebView.this, "webview#onShowFileChooser");
            ValueCallback unused = ExternalWebView.this.p0 = filePathCallback;
            int unused2 = ExternalWebView.this.p1 = 0;
            ExternalWebView.m(ExternalWebView.this);
            return true;
        }

        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            Class[] clsArr = {View.class, WebChromeClient.CustomViewCallback.class};
            if (!PatchProxy.proxy(new Object[]{view, callback}, this, changeQuickRedirect, false, TypedValues.TransitionType.TYPE_INTERPOLATOR, clsArr, Void.TYPE).isSupported) {
                super.onShowCustomView(view, callback);
                ExternalWebView.a(ExternalWebView.this, "onShowCustomView");
                ExternalWebView.n(ExternalWebView.this, view, callback);
            }
        }

        public void onHideCustomView() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, TypedValues.TransitionType.TYPE_STAGGERED, new Class[0], Void.TYPE).isSupported) {
                super.onHideCustomView();
                ExternalWebView.a(ExternalWebView.this, "onHideCustomView");
                ExternalWebView.this.y();
            }
        }

        @Nullable
        public View getVideoLoadingProgressView() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, TypedValues.TransitionType.TYPE_TRANSITION_FLAGS, new Class[0], View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            ExternalWebView.a(ExternalWebView.this, "getVideoLoadingProgressView");
            FrameLayout frameLayout = new FrameLayout(ExternalWebView.this.getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            return frameLayout;
        }
    }

    public void setAliasKey(String key) {
        if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 677, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.f.setAliasKey(key);
        }
    }

    public String getAliasKey() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 678, new Class[0], String.class);
        return proxy.isSupported ? (String) proxy.result : this.f.getAliasKey();
    }

    public void E() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 679, new Class[0], Void.TYPE).isSupported) {
            if (getSkeletonLayoutId() != 0) {
                LDSSkeleton lDSSkeleton = new LDSSkeleton(this.y);
                this.H4 = lDSSkeleton;
                lDSSkeleton.setLayout(getSkeletonLayoutId());
                ((FrameLayout) findViewById(R$id.container_view)).addView(this.H4, new FrameLayout.LayoutParams(-1, -1));
            }
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 680, new Class[0], Void.TYPE).isSupported) {
            WebErrorView webErrorView = (WebErrorView) findViewById(R$id.errorLayout);
            this.q = webErrorView;
            if (webErrorView != null) {
                webErrorView.setRetryClickListener(new e());
            }
        }
    }

    public class e implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 702, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            ExternalWebView.this.E();
            ExternalWebView.this.z();
            if (ExternalWebView.this.K4 != null) {
                ExternalWebView.this.K4.a(ExternalWebView.this);
            } else {
                ExternalWebView.this.f.reload();
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void setUseLoadingView(boolean useLoadingView) {
        this.J4 = useLoadingView;
    }

    public int getSkeletonLayoutId() {
        return this.I4;
    }

    public void setSkeletonLayoutId(int skeletonLayoutId) {
        this.I4 = skeletonLayoutId;
    }

    public void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 681, new Class[0], Void.TYPE).isSupported) {
            LDSSkeleton lDSSkeleton = this.H4;
            if (lDSSkeleton != null) {
                lDSSkeleton.a();
            }
            FrameLayout container_view = (FrameLayout) findViewById(R$id.container_view);
            int childCount = container_view.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = container_view.getChildAt(i2);
                if (childAt == this.H4) {
                    container_view.removeViewAt(i2);
                } else if (childAt instanceof LDSSkeleton) {
                    ((LDSSkeleton) childAt).a();
                    container_view.removeViewAt(i2);
                }
            }
            this.f.setVisibility(0);
        }
    }

    public void O() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 682, new Class[0], Void.TYPE).isSupported) {
            if (this.J4 && this.p4 != null) {
                this.x.setVisibility(0);
                this.p4.setVisibility(0);
                this.p4.c();
            }
            z();
        }
    }

    public void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 683, new Class[0], Void.TYPE).isSupported) {
            if (this.J4 && this.p4 != null) {
                this.x.setVisibility(8);
                this.p4.b();
                this.p4.setVisibility(8);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void M() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 684(0x2ac, float:9.58E-43)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            com.leedarson.base.views.WebErrorView r2 = r1.q
            if (r2 == 0) goto L_0x0025
            r2.setVisibility(r0)
            com.leedarson.base.jsbridge2.WVJBWebView r0 = r1.f
            r2 = 8
            r0.setVisibility(r2)
        L_0x0025:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.base.views.ExternalWebView.M():void");
    }

    public void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 685, new Class[0], Void.TYPE).isSupported) {
            if (this.q != null) {
                this.f.setVisibility(0);
                this.q.setVisibility(8);
            }
        }
    }

    public void t(boolean isLog) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isLog ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 686, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            long duration = System.currentTimeMillis() - this.C4;
            I(this.f.getAliasKey() + ",didRender,duration:" + duration);
            this.f.setVisibility(0);
            B();
            A();
            Handler handler = this.z4;
            if (handler != null) {
                handler.removeCallbacks(this.B4);
            }
            LoggerService loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class);
            if (loggerService != null && isLog) {
                loggerService.reportELK(this, ("load" + this.f.getAliasKey()) + " duration is " + duration + ",url:" + this.d, "info", "loadWebView");
            }
        }
    }

    public void v(int offset) {
        Object[] objArr = {new Integer(offset)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 687, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            int titleBarHeight = com.leedarson.base.utils.d.b(this.y, 44.0f);
            View view = this.x;
            if (view != null) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
                params.topMargin = params.topMargin + titleBarHeight + offset;
                this.x.setLayoutParams(params);
            }
        }
    }

    public final class i implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private i() {
        }

        /* synthetic */ i(ExternalWebView x0, a x1) {
            this();
        }

        public void run() {
            LoggerService loggerService;
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 714, new Class[0], Void.TYPE).isSupported) {
                String alias = ExternalWebView.this.f.getAliasKey();
                String event = "load" + alias;
                ExternalWebView.a(ExternalWebView.this, event + " for more than 5 seconds,url:" + ExternalWebView.this.d);
                if (("Shop".equals(alias) || "Community".equals(alias)) && (loggerService = (LoggerService) com.alibaba.android.arouter.launcher.a.c().g(LoggerService.class)) != null) {
                    loggerService.reportELK(ExternalWebView.this, event + " for more than 5 seconds,url:" + ExternalWebView.this.d, "info", "loadWebView");
                }
            }
        }
    }

    public void setVisibility(int visibility) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{new Integer(visibility)}, this, changeQuickRedirect, false, 688, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            WVJBWebView wVJBWebView = this.f;
            if (visibility != 0) {
                z2 = false;
            }
            wVJBWebView.setVisibleUserHint(z2);
            super.setVisibility(visibility);
        }
    }

    public void setOnErrorClickProxyListener(h onErrorClickProxyListener) {
        this.K4 = onErrorClickProxyListener;
    }
}
