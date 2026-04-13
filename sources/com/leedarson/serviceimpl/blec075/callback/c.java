package com.leedarson.serviceimpl.blec075.callback;

import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: LdsBleReadCallback */
public abstract class c implements BleC075Service.CommonBleCallback {
    public static ChangeQuickRedirect changeQuickRedirect;

    public abstract void a(Exception exc, String str, String str2, int i);

    public abstract void b(byte[] bArr, String str, String str2);

    public void onWriteSuccess(int current, int total, byte[] justWrite, String callbackKey, String mac) {
    }

    public void onWriteFailure(Exception exception, String callbackKey, String mac, int gatt) {
    }

    public void onReadSuccess(byte[] data, String callbackKey, String mac) {
        Class<String> cls = String.class;
        Class[] clsArr = {byte[].class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{data, callbackKey, mac}, this, changeQuickRedirect, false, 6474, clsArr, Void.TYPE).isSupported) {
            b(data, callbackKey, mac);
        }
    }

    public void onReadFailure(Exception exception, String callbackKey, String mac, int gatt) {
        Class<String> cls = String.class;
        Object[] objArr = {exception, callbackKey, mac, new Integer(gatt)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6475, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            a(exception, callbackKey, mac, gatt);
        }
    }

    public void onCharacteristicChanged(byte[] data, String callbackKey, String mac) {
    }

    public void onNotifySuccess(String callbackkey, String mac) {
    }

    public void onNotifyFail(Exception exception, String callbackkey, String mac, int gattStatus) {
    }
}
