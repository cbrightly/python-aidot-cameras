package com.leedarson.newui.cloud_play_back.repos.beans;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class LDSBaseBean<T> {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int code = 0;
    public T data;
    public String desc = "";

    public boolean checkDataValid() {
        return this.code == 200;
    }

    public String getErrorDetalInfo() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3836, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "详细信息：" + this.code + "   desc:" + this.desc;
    }
}
