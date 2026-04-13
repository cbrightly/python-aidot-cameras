package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class PlayControlEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int code;

    public PlayControlEvent(int code2) {
        this.code = code2;
    }

    public int getCode() {
        return this.code;
    }
}
