package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class NativeMqttStatusChangeEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int state;

    public NativeMqttStatusChangeEvent(int state2) {
        this.state = state2;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state2) {
        this.state = state2;
    }
}
