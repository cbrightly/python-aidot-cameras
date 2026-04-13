package com.leedarson.serviceimpl.blec075;

import android.bluetooth.BluetoothDevice;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceinterface.SkipRopeService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.Map;
import timber.log.a;

/* compiled from: LDSDeviceDelegate */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "LDSDeviceDelegate";

    public void a(byte[] customDataUnit, BluetoothDevice bluetoothDevice) {
        if (!PatchProxy.proxy(new Object[]{customDataUnit, bluetoothDevice}, this, changeQuickRedirect, false, 6399, new Class[]{byte[].class, BluetoothDevice.class}, Void.TYPE).isSupported) {
            String hex = h.b(customDataUnit);
            String devName = bluetoothDevice != null ? bluetoothDevice.getName() : "";
            a.b g = a.g("LDSDeviceDelegate");
            g.a("LDSDeviceDelegate customDataUnit:" + hex, new Object[0]);
            if ("SkipJoy".equals(devName) || hex.startsWith("ac60") || hex.startsWith("AC60")) {
                c();
            }
        }
    }

    public void b(byte[] scanRecord, BluetoothDevice bluetoothDevice) {
        Map<Byte, h.a> k;
        Class[] clsArr = {byte[].class, BluetoothDevice.class};
        if (!PatchProxy.proxy(new Object[]{scanRecord, bluetoothDevice}, this, changeQuickRedirect, false, 6400, clsArr, Void.TYPE).isSupported) {
            if (scanRecord != null && (k = h.k(scanRecord)) != null && k.containsKey((byte) -1)) {
                a(k.get((byte) -1).c, bluetoothDevice);
            }
        }
    }

    private void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6401, new Class[0], Void.TYPE).isSupported) {
            SkipRopeService skipRopeService = (SkipRopeService) com.alibaba.android.arouter.launcher.a.c().g(SkipRopeService.class);
            if (skipRopeService != null) {
                skipRopeService.checkSource();
            }
        }
    }
}
