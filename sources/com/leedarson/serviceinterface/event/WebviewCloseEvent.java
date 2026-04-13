package com.leedarson.serviceinterface.event;

import com.leedarson.base.jsbridge2.WVJBWebView;

public class WebviewCloseEvent {
    public WVJBWebView webView;

    public WebviewCloseEvent(WVJBWebView webView2) {
        this.webView = webView2;
    }
}
