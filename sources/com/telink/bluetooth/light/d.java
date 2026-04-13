package com.telink.bluetooth.light;

import android.bluetooth.BluetoothDevice;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.util.a;

/* compiled from: DefaultAdvertiseDataFilter */
public final class d implements b<g> {
    public static ChangeQuickRedirect changeQuickRedirect;

    private d() {
    }

    public static d b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 13516, new Class[0], d.class);
        return proxy.isSupported ? (d) proxy.result : new d();
    }

    public g a(BluetoothDevice bluetoothDevice, int i, byte[] bArr) {
        byte packetSize;
        int i2 = 1;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bluetoothDevice, new Integer(i), bArr}, this, changeQuickRedirect, false, 13517, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, g.class);
        if (proxy.isSupported) {
            return (g) proxy.result;
        }
        int rssi = i;
        BluetoothDevice device = bluetoothDevice;
        byte[] scanRecord = bArr;
        com.telink.bluetooth.d.a(device.getName() + "-->" + a.a(scanRecord, ":"));
        int length = scanRecord.length;
        int rspData = 0;
        int packetPosition = 0;
        byte[] meshName = null;
        while (packetPosition < length && (packetSize = scanRecord[packetPosition]) != 0) {
            int position = packetPosition + 1;
            int i3 = scanRecord[position] & 255;
            int position2 = position + i2;
            if (i3 == 9) {
                int packetContentLength = packetSize - 1;
                if (packetContentLength > 16 || packetContentLength <= 0) {
                    return null;
                }
                byte[] meshName2 = new byte[16];
                System.arraycopy(scanRecord, position2, meshName2, 0, packetContentLength);
                meshName = meshName2;
                byte b = i3;
            } else if (i3 == 255) {
                int rspData2 = rspData + 1;
                if (rspData2 == 2) {
                    int position3 = position2 + 1;
                    int position4 = position3 + 1;
                    if (((scanRecord[position2] & 255) << 8) + (scanRecord[position3] & 255) != h.a().g()) {
                        return null;
                    }
                    int position5 = position4 + 1;
                    int meshUUID = (scanRecord[position4] & 255) + ((scanRecord[position5] & 255) << 8);
                    int position6 = position5 + 1 + 4;
                    int position7 = position6 + 1;
                    int position8 = position7 + 1;
                    int productUUID = (scanRecord[position6] & 255) + ((scanRecord[position7] & 255) << 8);
                    int position9 = position8 + 1;
                    int status = scanRecord[position8] & 255;
                    int meshAddress = (scanRecord[position9] & 255) + ((scanRecord[position9 + 1] & 255) << 8);
                    byte b2 = i3;
                    g light = new g(device, scanRecord, rssi, meshName, meshAddress);
                    light.V("com.telink.bluetooth.light.ADV_MESH_NAME", meshName);
                    light.V("com.telink.bluetooth.light.ADV_MESH_ADDRESS", Integer.valueOf(meshAddress));
                    light.V("com.telink.bluetooth.light.ADV_MESH_UUID", Integer.valueOf(meshUUID));
                    light.V("com.telink.bluetooth.light.ADV_PRODUCT_UUID", Integer.valueOf(productUUID));
                    light.V("com.telink.bluetooth.light.ADV_STATUS", Integer.valueOf(status));
                    return light;
                }
                int type = i3;
                rspData = rspData2;
            } else {
                int type2 = i3;
            }
            packetPosition += packetSize + 1;
            i2 = 1;
        }
        return null;
    }
}
