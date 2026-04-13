package com.leedarson.serviceimpl.blec075.callback;

import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: LdsBleWriteCallback */
public abstract class d implements BleC075Service.CommonBleCallback {
    public static ChangeQuickRedirect changeQuickRedirect;

    public abstract void onSimpleWriteFail(Exception exc, String str, String str2, int i);

    public abstract void onSimpleWriteSuccess(int i, int i2, byte[] bArr, String str, String str2);

    public void onWriteSuccess(int current, int total, byte[] justWrite, String callbackKey, String mac) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(current), new Integer(total), justWrite, callbackKey, mac};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6476, new Class[]{cls2, cls2, byte[].class, cls, cls}, Void.TYPE).isSupported) {
            onSimpleWriteSuccess(current, total, justWrite, callbackKey, mac);
        }
    }

    public void onWriteFailure(Exception exception, String callbackKey, String mac, int gatt) {
        Class<String> cls = String.class;
        Object[] objArr = {exception, callbackKey, mac, new Integer(gatt)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6477, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            onSimpleWriteFail(exception, callbackKey, mac, gatt);
        }
    }

    public void onReadSuccess(byte[] data, String callbackKey, String mac) {
    }

    public void onReadFailure(Exception exception, String callbackKey, String mac, int gatt) {
    }

    public void onCharacteristicChanged(byte[] data, String callbackKey, String mac) {
    }

    public void onNotifySuccess(String callbackkey, String mac) {
    }

    public void onNotifyFail(Exception exception, String callbackkey, String mac, int gattStatus) {
    }
}
