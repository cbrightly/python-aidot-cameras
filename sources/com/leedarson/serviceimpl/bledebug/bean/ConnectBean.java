package com.leedarson.serviceimpl.bledebug.bean;

import android.bluetooth.BluetoothDevice;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import org.spongycastle.pqc.math.linearalgebra.ByteUtils;

public class ConnectBean {
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_CONNECT_FAIL = 3;
    public static final int STATE_DISCONNECTING = 4;
    public static final int STATE_FOUND = 0;
    public static final int STATE_IDLE = -1;
    public static ChangeQuickRedirect changeQuickRedirect;
    public AdvertisingDevice advertisingDevice;
    public BluetoothDevice bluetoothDevice;
    public String mac;
    public int state = -1;

    public ConnectBean(String mac2) {
        this.mac = mac2;
    }

    public boolean isMatch(AdvertisingDevice device) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{device}, this, changeQuickRedirect, false, 6655, new Class[]{AdvertisingDevice.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String scanRecord = ByteUtils.e(device.f);
        boolean b = device.c.getAddress().replace(":", "").toUpperCase().contains(this.mac.toUpperCase());
        boolean b1 = scanRecord != null && scanRecord.contains(this.mac);
        boolean b2 = scanRecord != null && scanRecord.toUpperCase().contains(this.mac.toUpperCase());
        if (b || b1 || b2) {
            return true;
        }
        return false;
    }
}
