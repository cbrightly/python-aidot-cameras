package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class ClearFragmentEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String deviceId;
    private int sign;

    public ClearFragmentEvent(int sign2) {
        this.sign = sign2;
    }

    public ClearFragmentEvent(String deviceId2) {
        this.deviceId = deviceId2;
    }

    public int getSign() {
        return this.sign;
    }

    public String getDeviceId() {
        return this.deviceId;
    }
}
