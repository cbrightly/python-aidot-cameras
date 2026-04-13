package com.leedarson.serviceimpl.tcp.beans;

import com.meituan.robust.ChangeQuickRedirect;

public class TCPChannelMapBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public TCP_CONNECT_STATUS connectStatus = TCP_CONNECT_STATUS.UNCONNECTED;
    public String deviceId = "";
    public TCP_LOGIN_STATUS loginStatus = TCP_LOGIN_STATUS.NOTLOGIN;
    public String sessionId = "";

    public enum TCP_CONNECT_STATUS {
        CONNECTED,
        UNCONNECTED;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public enum TCP_LOGIN_STATUS {
        LOGIN,
        NOTLOGIN;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public boolean isConnectAndLogin() {
        return this.connectStatus == TCP_CONNECT_STATUS.CONNECTED && this.loginStatus == TCP_LOGIN_STATUS.LOGIN;
    }
}
