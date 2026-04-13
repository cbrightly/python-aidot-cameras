package com.leedarson.serviceimpl.zendesk;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class ContentActivity extends AppCompatActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String c;
    private WVJBWebView d;
    private ImageView f;
    /* access modifiers changed from: private */
    public ProgressBar q;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 9164, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            setContentView(R$layout.activity_content);
            Intent intent = getIntent();
            if (intent.hasExtra("url")) {
                this.c = intent.getStringExtra("url");
            } else {
                finish();
            }
            this.d = (WVJBWebView) findViewById(R$id.webview);
            this.q = (ProgressBar) findViewById(R$id.hor_progressbar);
            WebSettings settings = this.d.getSettings();
            settings.setDomStorageEnabled(true);
            if (Build.VERSION.SDK_INT >= 21) {
                CookieManager.getInstance().setAcceptThirdPartyCookies(this.d, true);
                settings.setMixedContentMode(0);
            }
            settings.setAppCachePath(getFilesDir().getAbsolutePath() + "/webcache");
            settings.setAppCacheEnabled(true);
            settings.setAllowFileAccess(false);
            settings.setSupportZoom(true);
            settings.setJavaScriptEnabled(true);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(true);
            settings.setTextZoom(100);
            this.d.setWebViewClient(new a());
            this.d.setWebChromeClient(new b(this));
            WVJBWebView wVJBWebView = this.d;
            String str = this.c;
            wVJBWebView.loadUrl(str);
            SensorsDataAutoTrackHelper.loadUrl2(wVJBWebView, str);
            ImageView imageView = (ImageView) findViewById(R$id.iv_back);
            this.f = imageView;
            imageView.setOnClickListener(new c());
        }
    }

    public class a extends WebViewClient {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 9166, new Class[]{WebView.class, String.class}, Boolean.TYPE);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : super.shouldOverrideUrlLoading(view, url);
        }

        public void onPageFinished(WebView view, String url) {
            Class[] clsArr = {WebView.class, String.class};
            if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 9167, clsArr, Void.TYPE).isSupported) {
                super.onPageFinished(view, url);
            }
        }
    }

    public class b extends a {
        public static ChangeQuickRedirect changeQuickRedirect;

        b(Activity activity) {
            super(activity);
        }

        public void onProgressChanged(WebView view, int newProgress) {
            Object[] objArr = {view, new Integer(newProgress)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9168, new Class[]{WebView.class, Integer.TYPE}, Void.TYPE).isSupported) {
                super.onProgressChanged(view, newProgress);
                ContentActivity.this.q.setProgress(newProgress);
                if (!(ContentActivity.this.q.getVisibility() == 0 || newProgress == 100)) {
                    ContentActivity.this.q.setVisibility(0);
                }
                if (newProgress > 95) {
                    ContentActivity.this.q.setVisibility(8);
                }
            }
        }

        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            return true;
        }

        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
            Class[] clsArr = {View.class, WebChromeClient.CustomViewCallback.class};
            if (!PatchProxy.proxy(new Object[]{view, callback}, this, changeQuickRedirect, false, 9169, clsArr, Void.TYPE).isSupported) {
                super.onShowCustomView(view, callback);
            }
        }

        public void onHideCustomView() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9170, new Class[0], Void.TYPE).isSupported) {
                super.onHideCustomView();
            }
        }

        @Nullable
        public View getVideoLoadingProgressView() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9171, new Class[0], View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            FrameLayout frameLayout = new FrameLayout(ContentActivity.this);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            return frameLayout;
        }
    }

    public class c implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 9172, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            ContentActivity.this.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Object[] objArr = {new Integer(keyCode), event};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 9165, new Class[]{Integer.TYPE, KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (keyCode != 4 || !this.d.canGoBack()) {
            return super.onKeyDown(keyCode, event);
        }
        finish();
        return true;
    }
}
