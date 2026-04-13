package com.leedarson.event;

import com.meituan.robust.ChangeQuickRedirect;

public class StartPlayEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String data;

    public StartPlayEvent(String data2) {
        this.data = data2;
    }

    public String getData() {
        return this.data;
    }
}
