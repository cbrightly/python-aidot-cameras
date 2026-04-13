package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class SocketStatusChangeEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String ipKey;
    private String sessionId;
    private int statusCode;

    public SocketStatusChangeEvent(String ipKey2, int statusCode2, String sessionId2) {
        this.ipKey = ipKey2;
        this.statusCode = statusCode2;
        this.sessionId = sessionId2;
    }

    public String getIpKey() {
        return this.ipKey;
    }

    public void setIpKey(String ipKey2) {
        this.ipKey = ipKey2;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode2) {
        this.statusCode = statusCode2;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId2) {
        this.sessionId = sessionId2;
    }
}
