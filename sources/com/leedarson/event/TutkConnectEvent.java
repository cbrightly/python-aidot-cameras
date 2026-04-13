package com.leedarson.event;

import com.meituan.robust.ChangeQuickRedirect;

public class TutkConnectEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String callback;
    private String data;

    public TutkConnectEvent(String callback2, String data2) {
        this.callback = callback2;
        this.data = data2;
    }

    public String getCallback() {
        return this.callback;
    }

    public String getData() {
        return this.data;
    }
}
