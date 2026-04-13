package com.leedarson.serviceimpl.bodyfat.callback;

import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: BleSimpleWriteCallback */
public abstract class c implements BleC075Service.CommonBleCallback {
    public static ChangeQuickRedirect changeQuickRedirect;

    public abstract void a(Exception exc, String str);

    public abstract void b(int i, int i2, byte[] bArr, String str);

    public void onWriteSuccess(int current, int total, byte[] justWrite, String callbackKey, String str) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(current), new Integer(total), justWrite, callbackKey, str};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6927, new Class[]{cls2, cls2, byte[].class, cls, cls}, Void.TYPE).isSupported) {
            b(current, total, justWrite, callbackKey);
        }
    }

    public void onWriteFailure(Exception exception, String callbackKey, String str, int i) {
        Class<String> cls = String.class;
        Object[] objArr = {exception, callbackKey, str, new Integer(i)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6928, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            a(exception, callbackKey);
        }
    }

    public void onReadSuccess(byte[] data, String callbackKey, String mac) {
    }

    public void onReadFailure(Exception exception, String callbackKey, String mac, int gattStatus) {
    }

    public void onCharacteristicChanged(byte[] data, String callbackKey, String mac) {
    }

    public void onNotifyFail(Exception exception, String callbackkey, String mac, int gattStatus) {
    }

    public void onNotifySuccess(String callbackkey, String mac) {
    }
}
