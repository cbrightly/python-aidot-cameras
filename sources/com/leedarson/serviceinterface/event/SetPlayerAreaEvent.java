package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class SetPlayerAreaEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int maxY;
    private int minY;

    public SetPlayerAreaEvent(int minY2, int maxY2) {
        this.minY = minY2;
        this.maxY = maxY2;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMaxY() {
        return this.maxY;
    }
}
