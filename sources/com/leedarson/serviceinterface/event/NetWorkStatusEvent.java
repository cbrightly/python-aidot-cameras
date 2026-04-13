package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.util.internal.StringUtil;

public class NetWorkStatusEvent {
    public static final int HAS_NETWORK = 1;
    public static final int NO_NETWORK = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    private String curNetworkName = "unknow network";
    private int netWorkStatus;
    private String preNetworkName = "unknow network";

    public NetWorkStatusEvent(int netWorkStatus2) {
        setNetWorkStatus(netWorkStatus2);
    }

    public NetWorkStatusEvent(int netWorkStatus2, String preNetworkName2, String curNetworkName2) {
        this.netWorkStatus = netWorkStatus2;
        this.preNetworkName = preNetworkName2;
        this.curNetworkName = curNetworkName2;
    }

    public int getNetWorkStatus() {
        return this.netWorkStatus;
    }

    public void setNetWorkStatus(int netWorkStatus2) {
        this.netWorkStatus = netWorkStatus2;
    }

    public boolean checkNetWorkEnable() {
        return this.netWorkStatus > 0;
    }

    public String getPreNetworkName() {
        return this.preNetworkName;
    }

    public String getCurNetworkName() {
        return this.curNetworkName;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9210, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "{" + "\"netWorkStatus\":" + this.netWorkStatus + ",\"preNetworkName\":\"" + this.preNetworkName + StringUtil.DOUBLE_QUOTE + ",\"curNetworkName\":\"" + this.curNetworkName + StringUtil.DOUBLE_QUOTE + '}';
    }
}
