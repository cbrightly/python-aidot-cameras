package com.leedarson.serviceimpl.blec075.callback;

import com.leedarson.base.beans.CommonBleReadConfigBean;
import com.leedarson.serviceimpl.blec075.util.d;
import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: LdsBleNotifyCallback */
public abstract class b implements BleC075Service.CommonBleCallback {
    public static ChangeQuickRedirect changeQuickRedirect;

    public abstract void onSimpleCharacteristicChanged(byte[] bArr, String str, String str2);

    public abstract void onSimpleNotifyError(Exception exc, String str, String str2, int i);

    public abstract void onSimpleNotifySuccess(String str, String str2);

    public void onWriteSuccess(int current, int total, byte[] justWrite, String callbackKey, String mac) {
    }

    public void onWriteFailure(Exception exception, String callbackKey, String mac, int gatt) {
    }

    public void onReadSuccess(byte[] data, String callbackKey, String mac) {
    }

    public void onReadFailure(Exception exception, String callbackKey, String mac, int gatt) {
    }

    public void onCharacteristicChanged(byte[] data, String callbackKey, String mac) {
        Class<String> cls = String.class;
        Class[] clsArr = {byte[].class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{data, callbackKey, mac}, this, changeQuickRedirect, false, 6470, clsArr, Void.TYPE).isSupported) {
            onSimpleCharacteristicChanged(data, callbackKey, mac);
        }
    }

    public void onNotifySuccess(String callbackkey, String mac) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{callbackkey, mac}, this, changeQuickRedirect, false, 6471, clsArr, Void.TYPE).isSupported) {
            onSimpleNotifySuccess(callbackkey, mac);
        }
    }

    public void onNotifyFail(Exception exception, String callbackkey, String mac, int gattStatus) {
        Class<String> cls = String.class;
        Object[] objArr = {exception, callbackkey, mac, new Integer(gattStatus)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6472, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            onSimpleNotifyError(exception, callbackkey, mac, gattStatus);
        }
    }

    public byte[] decryptDataByConfig(CommonBleReadConfigBean configBean, byte[] datas) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{configBean, datas}, this, changeQuickRedirect2, false, 6473, new Class[]{CommonBleReadConfigBean.class, byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        return d.a(configBean, datas);
    }
}
