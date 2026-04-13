package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class WxResponseEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String url;

    public WxResponseEvent(String url2) {
        this.url = url2;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url2) {
        this.url = url2;
    }
}
