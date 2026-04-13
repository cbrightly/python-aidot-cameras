package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class ScreenConfigurationChangeEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean isPortrait;

    public ScreenConfigurationChangeEvent(boolean isPortrait2) {
        this.isPortrait = isPortrait2;
    }

    public boolean isPortrait() {
        return this.isPortrait;
    }
}
