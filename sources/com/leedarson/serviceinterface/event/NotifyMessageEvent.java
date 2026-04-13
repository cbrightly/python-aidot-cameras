package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class NotifyMessageEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String houseId;
    private String message;

    public NotifyMessageEvent(String message2, String houseId2) {
        this.message = message2;
        this.houseId = houseId2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public String getHouseId() {
        return this.houseId;
    }

    public void setHouseId(String houseId2) {
        this.houseId = houseId2;
    }
}
