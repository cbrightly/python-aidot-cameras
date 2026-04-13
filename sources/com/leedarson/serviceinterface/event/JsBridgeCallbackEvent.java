package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class JsBridgeCallbackEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String callbackKey;
    private String message;

    public JsBridgeCallbackEvent(String callbackKey2, String message2) {
        this.callbackKey = callbackKey2;
        this.message = message2;
    }

    public String getCallbackKey() {
        return this.callbackKey;
    }

    public void setCallbackKey(String callbackKey2) {
        this.callbackKey = callbackKey2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }
}
