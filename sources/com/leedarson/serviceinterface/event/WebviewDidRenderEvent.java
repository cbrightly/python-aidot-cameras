package com.leedarson.serviceinterface.event;

import com.leedarson.base.jsbridge2.WVJBWebView;

public class WebviewDidRenderEvent {
    public WVJBWebView webView;

    public WebviewDidRenderEvent(WVJBWebView webView2) {
        this.webView = webView2;
    }
}
