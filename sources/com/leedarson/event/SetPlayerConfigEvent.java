package com.leedarson.event;

import com.meituan.robust.ChangeQuickRedirect;

public class SetPlayerConfigEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String data;

    public SetPlayerConfigEvent(String data2) {
        this.data = data2;
    }

    public String getData() {
        return this.data;
    }
}
