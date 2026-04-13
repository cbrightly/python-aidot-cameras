package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class PartialUpdateEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String data;

    public PartialUpdateEvent(String data2) {
        this.data = data2;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data2) {
        this.data = data2;
    }
}
