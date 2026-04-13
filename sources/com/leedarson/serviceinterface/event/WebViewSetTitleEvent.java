package com.leedarson.serviceinterface.event;

import com.leedarson.base.jsbridge2.WVJBWebView;

public class WebViewSetTitleEvent {
    public String title = "";
    public WVJBWebView webView;

    public WebViewSetTitleEvent(WVJBWebView webView2, String title2) {
        this.title = title2;
        this.webView = webView2;
    }
}
