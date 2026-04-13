package com.leedarson.skiprope.callback;

import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: BleSimpleNotifyCallback */
public abstract class a implements BleC075Service.CommonBleCallback {
    public static ChangeQuickRedirect changeQuickRedirect;

    public abstract void a(byte[] bArr);

    public void onWriteSuccess(int current, int total, byte[] justWrite, String callbackKey, String mac) {
    }

    public void onWriteFailure(Exception exception, String callbackKey, String mac, int gatt) {
    }

    public void onReadSuccess(byte[] data, String callbackKey, String mac) {
    }

    public void onReadFailure(Exception exception, String callbackKey, String mac, int gatt) {
    }

    public void onCharacteristicChanged(byte[] data, String str, String str2) {
        Class<String> cls = String.class;
        Class[] clsArr = {byte[].class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{data, str, str2}, this, changeQuickRedirect, false, 9508, clsArr, Void.TYPE).isSupported) {
            a(data);
        }
    }
}
