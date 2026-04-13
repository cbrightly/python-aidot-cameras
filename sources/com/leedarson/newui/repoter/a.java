package com.leedarson.newui.repoter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: AbsIPCReporter */
public abstract class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a = null;
    protected String b = "";
    protected String c = "";
    public String d = "";
    public String e = "";
    public String f = "";
    public String g = "";

    public void d(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4480, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.b = deviceId;
            IpcDeviceBean ipcDeviceBean = a();
            if (ipcDeviceBean != null) {
                this.d = ipcDeviceBean.hardwareVersion;
                this.e = ipcDeviceBean.firmwareVersion;
            }
        }
    }

    private IpcDeviceBean a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4481, new Class[0], IpcDeviceBean.class);
        if (proxy.isSupported) {
            return (IpcDeviceBean) proxy.result;
        }
        for (int i = 0; i < IpcServiceImpl.a.size(); i++) {
            String str = this.b;
            if (str != null && str.equals(IpcServiceImpl.a.get(i).id)) {
                return IpcServiceImpl.a.get(i);
            }
        }
        return null;
    }

    @SuppressLint({"WrongConstant"})
    public com.leedarson.log.elk.a b(String message) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4482, new Class[]{String.class}, com.leedarson.log.elk.a.class);
        if (proxy.isSupported) {
            return (com.leedarson.log.elk.a) proxy.result;
        }
        com.leedarson.log.elk.a u = com.leedarson.log.elk.a.y(this).t("LdsPlayer").b(8).p(message).o("info").u("hardwareVersion", this.d).u("firmwareVersion", this.e).u("IPCSDKVersion", this.f);
        return u.u("deviceId", this.b + "").u("internetSpeed", this.g);
    }

    public com.leedarson.log.elk.a c(String message) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4483, new Class[]{String.class}, com.leedarson.log.elk.a.class);
        if (proxy.isSupported) {
            return (com.leedarson.log.elk.a) proxy.result;
        }
        com.leedarson.log.elk.a u = com.leedarson.log.elk.a.y(this).t("LiveKVS").b(8).p(message).o("info").u("hardwareVersion", this.d).u("firmwareVersion", this.e).u("IPCSDKVersion", this.f);
        return u.u("deviceId", this.b + "").u("internetSpeed", this.g);
    }
}
