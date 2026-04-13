package com.leedarson.bean;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class IpcDeviceComparableBean implements Comparable<IpcDeviceComparableBean> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int index;
    private IpcDeviceBean ipcDeviceBean;

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1047, new Class[]{Object.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : compareTo((IpcDeviceComparableBean) obj);
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index2) {
        this.index = index2;
    }

    public IpcDeviceBean getIpcDeviceBean() {
        return this.ipcDeviceBean;
    }

    public void setIpcDeviceBean(IpcDeviceBean ipcDeviceBean2) {
        this.ipcDeviceBean = ipcDeviceBean2;
    }

    public IpcDeviceComparableBean(IpcDeviceBean ipcDeviceBean2, int index2) {
        this.ipcDeviceBean = ipcDeviceBean2;
        this.index = index2;
    }

    public int compareTo(IpcDeviceComparableBean ipcDeviceComparableBean) {
        return this.index - ipcDeviceComparableBean.index;
    }
}
