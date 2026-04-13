package com.leedarson.newui.repos.beans;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class AbnormalVersionBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public boolean appAbnormalVersion;
    public boolean deviceAbnormalVersion;

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4468, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "AbnormalVersionBean{appVersion=" + this.appAbnormalVersion + ", deviceVersion=" + this.deviceAbnormalVersion + '}';
    }
}
