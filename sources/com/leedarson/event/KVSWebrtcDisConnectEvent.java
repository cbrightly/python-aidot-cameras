package com.leedarson.event;

import com.meituan.robust.ChangeQuickRedirect;

public class KVSWebrtcDisConnectEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String callback;
    private String deviceId;

    public KVSWebrtcDisConnectEvent(String callback2, String deviceId2) {
        this.callback = callback2;
        this.deviceId = deviceId2;
    }

    public String getCallback() {
        return this.callback;
    }

    public String getDeviceId() {
        return this.deviceId;
    }
}
