package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class SocketMessageResponseEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String ipKey;
    private String message;
    private String sessionId;

    public SocketMessageResponseEvent(String ipKey2, String message2, String sessionId2) {
        this.ipKey = ipKey2;
        this.message = message2;
        this.sessionId = sessionId2;
    }

    public String getIpKey() {
        return this.ipKey;
    }

    public void setIpKey(String ipKey2) {
        this.ipKey = ipKey2;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId2) {
        this.sessionId = sessionId2;
    }
}
