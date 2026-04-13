package com.leedarson.serviceimpl.tcp.manager;

import android.content.Context;
import com.meituan.robust.ChangeQuickRedirect;

public class LdsTCPManager$ConnectParams {
    public static ChangeQuickRedirect changeQuickRedirect;
    String aesKey;
    Context context;
    public int heartBeatTickOfSecond = 0;
    String inetHost;
    int inetPort;
    String sessionid;
    int tls;

    LdsTCPManager$ConnectParams() {
    }

    public String getInetHost() {
        return this.inetHost;
    }

    public void setInetHost(String inetHost2) {
        this.inetHost = inetHost2;
    }

    public int getInetPort() {
        return this.inetPort;
    }

    public void setInetPort(int inetPort2) {
        this.inetPort = inetPort2;
    }

    public String getSessionid() {
        return this.sessionid;
    }

    public void setSessionid(String sessionid2) {
        this.sessionid = sessionid2;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context2) {
        this.context = context2;
    }

    public int getTls() {
        return this.tls;
    }

    public void setTls(int tls2) {
        this.tls = tls2;
    }

    public String getAesKey() {
        return this.aesKey;
    }

    public void setAesKey(String aesKey2) {
        this.aesKey = aesKey2;
    }
}
