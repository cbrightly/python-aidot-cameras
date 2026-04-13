package com.leedarson.serviceinterface.event;

import com.leedarson.base.jsbridge2.WVJBWebView;

public class WebviewReloadEvent {
    public WVJBWebView webView;

    public WebviewReloadEvent(WVJBWebView webView2) {
        this.webView = webView2;
    }
}
