package com.leedarson.serviceimpl.shake.blectrl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.ParcelUuid;
import com.leedarson.base.utils.w;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.math.BigInteger;
import java.util.UUID;
import timber.log.a;

/* compiled from: BleLoaclCtrlAdvertise */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private BluetoothAdapter a;
    private BluetoothLeAdvertiser b;
    private AdvertiseCallback c;
    private AdvertiseSettings d;
    private AdvertiseData e;
    private int f = 0;
    private String g = "BleCtrlAct";

    public d() {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.a = defaultAdapter;
        this.b = defaultAdapter.getBluetoothLeAdvertiser();
    }

    public String a(byte[] data, AdvertiseCallback advertiseCallback) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{data, advertiseCallback}, this, changeQuickRedirect, false, 8732, new Class[]{byte[].class, AdvertiseCallback.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        this.a.setName("Aidot");
        this.d = new AdvertiseSettings.Builder().setAdvertiseMode(2).setTxPowerLevel(3).setTimeout(this.f).setConnectable(true).build();
        String serviceData = w.b(data);
        a.b g2 = a.g(this.g);
        g2.a("startAdvertising serviceData= " + serviceData, new Object[0]);
        UUID serviceUuid = new UUID(new BigInteger(serviceData.substring(0, serviceData.length()), 16).longValue(), new BigInteger(serviceData.substring(serviceData.length()), 16).longValue());
        a.b g3 = a.g(this.g);
        g3.c("rest - serviceUuid = " + serviceUuid.toString(), new Object[0]);
        this.e = new AdvertiseData.Builder().setIncludeDeviceName(false).setIncludeTxPowerLevel(true).addServiceUuid(new ParcelUuid(serviceUuid)).build();
        this.c = advertiseCallback;
        if (this.b != null && this.a.isEnabled()) {
            this.b.startAdvertising(this.d, this.e, this.c);
        }
        return serviceData;
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8733, new Class[0], Void.TYPE).isSupported) {
            a.g(this.g).c("rest - 停止广播", new Object[0]);
            AdvertiseCallback advertiseCallback = this.c;
            if (advertiseCallback != null) {
                this.b.stopAdvertising(advertiseCallback);
            }
        }
    }
}
