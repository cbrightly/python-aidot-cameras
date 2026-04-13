package com.leedarson.serviceimpl.shake.blectrl;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.ScanRecord;
import androidx.annotation.Nullable;
import com.leedarson.serviceimpl.base.d;
import com.leedarson.serviceimpl.blec075.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

/* compiled from: BleLocalCtrlMgr */
public class e implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String a = "010203040506";
    d b = new d();
    private int c = 0;

    public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord, @Nullable ScanRecord record) {
    }

    public void onScanFail(int errorCode) {
    }

    public String onFromBz() {
        return "BleLocalCtrlMgr";
    }

    public void d(int onoff) {
        Object[] objArr = {new Integer(onoff)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8736, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.b.b();
            this.b.a(c(this.a, b(), 1, onoff), new a());
        }
    }

    /* compiled from: BleLocalCtrlMgr */
    public class a extends AdvertiseCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            if (!PatchProxy.proxy(new Object[]{settingsInEffect}, this, changeQuickRedirect, false, 8739, new Class[]{AdvertiseSettings.class}, Void.TYPE).isSupported) {
                super.onStartSuccess(settingsInEffect);
            }
        }

        public void onStartFailure(int errorCode) {
            Object[] objArr = {new Integer(errorCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8740, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                super.onStartFailure(errorCode);
            }
        }
    }

    public void a(int dim) {
        Object[] objArr = {new Integer(dim)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8737, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.b.b();
            this.b.a(c(this.a, b(), 2, dim), new b());
        }
    }

    /* compiled from: BleLocalCtrlMgr */
    public class b extends AdvertiseCallback {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            if (!PatchProxy.proxy(new Object[]{settingsInEffect}, this, changeQuickRedirect, false, 8741, new Class[]{AdvertiseSettings.class}, Void.TYPE).isSupported) {
                super.onStartSuccess(settingsInEffect);
            }
        }

        public void onStartFailure(int errorCode) {
            Object[] objArr = {new Integer(errorCode)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8742, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                super.onStartFailure(errorCode);
            }
        }
    }

    private byte[] c(String mac, int seq, int cmd, int value) {
        Object[] objArr = {mac, new Integer(seq), new Integer(cmd), new Integer(value)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 8738, new Class[]{String.class, cls, cls, cls}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] params = new byte[10];
        byte[] macBytes = h.n(mac);
        System.arraycopy(macBytes, 0, params, 0, macBytes.length);
        params[6] = (byte) seq;
        params[7] = 1;
        params[8] = (byte) cmd;
        params[9] = (byte) value;
        return params;
    }

    private int b() {
        int i = this.c + 1;
        this.c = i;
        return i;
    }
}
