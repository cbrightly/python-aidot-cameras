package com.leedarson.serviceimpl.http;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import timber.log.a;

public class PayActivity extends AppCompatActivity {
    public static ChangeQuickRedirect changeQuickRedirect;
    private WebView c;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 7955, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            setContentView(R$layout.activity_pay);
            String url = getIntent().getStringExtra("url");
            WebView webView = (WebView) findViewById(R$id.webview);
            this.c = webView;
            webView.getSettings().setJavaScriptEnabled(true);
            this.c.getSettings().setCacheMode(1);
            this.c.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            this.c.getSettings().setUseWideViewPort(true);
            this.c.getSettings().setTextZoom(100);
            this.c.setWebViewClient(new a());
            WebView webView2 = this.c;
            webView2.loadUrl(url);
            SensorsDataAutoTrackHelper.loadUrl2(webView2, url);
        }
    }

    public class a extends WebViewClient {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 7957, new Class[]{WebView.class, String.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            a.b g = timber.log.a.g("PayActivity");
            g.h("shouldOverrideUrlLoading: " + url, new Object[0]);
            if (url.contains("ipc/selectplan")) {
                Intent mIntent = new Intent();
                mIntent.putExtra("payResult", 2);
                PayActivity.this.setResult(0, mIntent);
                PayActivity.this.finish();
            } else if (url.contains("ipcpaysuccess")) {
                Intent mIntent2 = new Intent();
                mIntent2.putExtra("payResult", 0);
                PayActivity.this.setResult(0, mIntent2);
                PayActivity.this.finish();
            } else if (url.contains("ipc/ipcpayfail")) {
                Intent mIntent3 = new Intent();
                mIntent3.putExtra("payResult", 1);
                PayActivity.this.setResult(0, mIntent3);
                PayActivity.this.finish();
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(keyCode), event}, this, changeQuickRedirect, false, 7956, new Class[]{Integer.TYPE, KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.c.canGoBack()) {
            this.c.goBack();
        } else {
            Intent mIntent = new Intent();
            mIntent.putExtra("payResult", 2);
            setResult(0, mIntent);
            finish();
        }
        return true;
    }
}
