package com.leedarson.serviceimpl.business.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.netty.util.internal.StringUtil;

public class TRVCommandBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String categoryId = "";
    public String commandId = "";
    public String destinationId = "00";
    public String mac = "";
    public String payload = "";
    public String sourceId = "00";

    public TRVCommandBean(String mac2) {
        this.mac = mac2;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7111, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "{" + "\"mac\":\"" + this.mac + StringUtil.DOUBLE_QUOTE + ",\"destinationId\":\"" + this.destinationId + StringUtil.DOUBLE_QUOTE + ",\"sourceId\":\"" + this.sourceId + StringUtil.DOUBLE_QUOTE + ",\"categoryId\":\"" + this.categoryId + StringUtil.DOUBLE_QUOTE + ",\"commandId\":\"" + this.commandId + StringUtil.DOUBLE_QUOTE + ",\"payload\":\"" + this.payload + StringUtil.DOUBLE_QUOTE + '}';
    }
}
