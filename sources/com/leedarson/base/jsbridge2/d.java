package com.leedarson.base.jsbridge2;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.meituan.robust.ChangeQuickRedirect;

/* compiled from: WebCacheStrategy */
public abstract class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    String a = "webview_cache";

    public abstract WebResourceResponse a(WebView webView, WebResourceRequest webResourceRequest);

    public abstract boolean b(WebView webView, WebResourceRequest webResourceRequest);
}
