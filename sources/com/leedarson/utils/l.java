package com.leedarson.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.os.Handler;
import android.os.Looper;
import com.leedarson.serviceimpl.base.c;
import com.leedarson.serviceimpl.base.d;
import com.leedarson.serviceinterface.event.BleDeviceFoundEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.AdvertisingDevice;

/* compiled from: RhyBleScanner */
public class l implements d {
    private static l a;
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean b = false;
    private Handler c = new Handler(Looper.getMainLooper());
    private b d = new b();

    public static l a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 11523, new Class[0], l.class);
        if (proxy.isSupported) {
            return (l) proxy.result;
        }
        if (a == null) {
            synchronized (l.class) {
                if (a == null) {
                    a = new l();
                }
            }
        }
        return a;
    }

    public void c(long j) {
        Object[] objArr = {new Long(j)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11524, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            if (!this.b) {
                this.b = true;
                c.k().x(this);
                this.c.removeCallbacks(this.d);
                this.c.postDelayed(this.d, 60000);
            }
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11525, new Class[0], Void.TYPE).isSupported) {
            this.b = false;
            c.k().B(this);
        }
    }

    public void b(BluetoothDevice dev, int rssi, byte[] scanRecord) {
        Object[] objArr = {dev, new Integer(rssi), scanRecord};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11526, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            new AdvertisingDevice(dev, rssi, scanRecord);
            BleDeviceFoundEvent event = new BleDeviceFoundEvent(1);
            event.bluetoothDevice = dev;
            event.rssi = rssi;
            event.scanRecord = scanRecord;
            org.greenrobot.eventbus.c.c().l(event);
        }
    }

    public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord, ScanRecord scanRecord2) {
        Object[] objArr = {bluetoothDevice, new Integer(rssi), scanRecord, scanRecord2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11527, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class, ScanRecord.class}, Void.TYPE).isSupported) {
            b(bluetoothDevice, rssi, scanRecord);
        }
    }

    public void onScanFail(int errorCode) {
    }

    public String onFromBz() {
        return "RhyBle Scan";
    }

    /* compiled from: RhyBleScanner */
    public final class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private b() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11528, new Class[0], Void.TYPE).isSupported) {
                l.this.d();
            }
        }
    }
}
