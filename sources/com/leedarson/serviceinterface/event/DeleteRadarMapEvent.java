package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class DeleteRadarMapEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String deviceId;

    public DeleteRadarMapEvent(String deviceId2) {
        this.deviceId = deviceId2;
    }

    public String getDeviceId() {
        return this.deviceId;
    }
}
