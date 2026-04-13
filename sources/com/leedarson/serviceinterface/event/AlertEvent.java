package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class AlertEvent {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String callback;
    public String data;

    public AlertEvent(String data2, String callbackKey) {
        this.data = data2;
        this.callback = callbackKey;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data2) {
        this.data = data2;
    }

    public String getCallback() {
        return this.callback;
    }

    public void setCallback(String callback2) {
        this.callback = callback2;
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9209, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "AlertEvent{data='" + this.data + '\'' + ", callback='" + this.callback + '\'' + '}';
    }
}
