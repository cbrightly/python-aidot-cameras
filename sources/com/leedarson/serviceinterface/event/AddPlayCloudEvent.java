package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class AddPlayCloudEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String data;

    public AddPlayCloudEvent(String data2) {
        this.data = data2;
    }

    public String getData() {
        return this.data;
    }
}
