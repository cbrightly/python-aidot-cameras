package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class AddPlaySDEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String data;

    public AddPlaySDEvent(String data2) {
        this.data = data2;
    }

    public String getData() {
        return this.data;
    }
}
