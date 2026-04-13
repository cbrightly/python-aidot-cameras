package com.leedarson.base.webservice.event;

import com.meituan.robust.ChangeQuickRedirect;

public class ServerStatusEvent {
    public static final int SERVER_ERROR = 3;
    public static final int SERVER_PORT_ERROR = 4;
    public static final int SERVER_RESTART = 5;
    public static final int SERVER_START = 1;
    public static final int SERVER_STOPPED = 2;
    public static ChangeQuickRedirect changeQuickRedirect;
    private int serverStatus;
    private String url;

    public ServerStatusEvent(String url2, int serverStatus2) {
        this.serverStatus = serverStatus2;
        this.url = url2;
    }

    public int getServerStatus() {
        return this.serverStatus;
    }

    public String getUrl() {
        return this.url;
    }
}
