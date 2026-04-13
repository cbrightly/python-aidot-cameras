package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class PlayerTouchEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int tag;

    public PlayerTouchEvent(int tag2) {
        this.tag = tag2;
    }

    public int getTag() {
        return this.tag;
    }
}
