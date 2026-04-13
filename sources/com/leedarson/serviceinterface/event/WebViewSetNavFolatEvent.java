package com.leedarson.serviceinterface.event;

import com.leedarson.base.jsbridge2.WVJBWebView;

public class WebViewSetNavFolatEvent {
    public boolean floatMode = false;
    public WVJBWebView webView;

    public WebViewSetNavFolatEvent(WVJBWebView webView2, boolean floatMode2) {
        this.floatMode = floatMode2;
        this.webView = webView2;
    }
}
