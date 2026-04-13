package com.leedarson.serviceimpl.ctrl;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import androidx.exifinterface.media.ExifInterface;
import com.clj.fastble.callback.e;
import com.clj.fastble.exception.d;
import com.leedarson.base.utils.f;
import com.leedarson.serviceimpl.bean.AddDeviceBean;
import com.leedarson.serviceimpl.k;
import com.leedarson.serviceimpl.listener.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import java.util.Iterator;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: LDSMatterGatt */
public class j implements a {
    private static UUID a = UUID.fromString("0000fff6-0000-1000-8000-00805f9b34fb");
    public static UUID b = UUID.fromString("18ee2ef5-263d-4559-959f-4f9c429fa00a");
    public static UUID c = UUID.fromString("18ee2ef5-263d-4559-959f-4f9c429fa00b");
    public static ChangeQuickRedirect changeQuickRedirect;
    private String d = "LDSMatterGatt";
    private Context e;
    private BluetoothGatt f;
    private BluetoothGattCharacteristic g;
    private AdvertisingDevice h;
    private String i;

    public j(Context context, BluetoothGatt gatt, AdvertisingDevice advertisingDevice) {
        this.f = gatt;
        this.e = context;
        this.h = advertisingDevice;
        this.i = advertisingDevice.c.getAddress();
    }

    public boolean h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7569, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        BluetoothGattCharacteristic aChar = f(this.f, "a00a");
        if (aChar != null) {
            this.g = aChar;
        }
        if (aChar != null) {
            return true;
        }
        return false;
    }

    public static BluetoothGattCharacteristic f(BluetoothGatt gatt, String imCompleteCharacterId) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{gatt, imCompleteCharacterId}, (Object) null, changeQuickRedirect2, true, 7570, new Class[]{BluetoothGatt.class, String.class}, BluetoothGattCharacteristic.class);
        if (proxy.isSupported) {
            return (BluetoothGattCharacteristic) proxy.result;
        }
        for (BluetoothGattService ser : gatt.getServices()) {
            Iterator<BluetoothGattCharacteristic> it = ser.getCharacteristics().iterator();
            while (true) {
                if (it.hasNext()) {
                    BluetoothGattCharacteristic chara = it.next();
                    if (chara.getUuid().toString().toUpperCase().contains(imCompleteCharacterId.toUpperCase())) {
                        return chara;
                    }
                }
            }
        }
        return null;
    }

    public void k(AddDeviceBean addDeviceBean) {
        if (!PatchProxy.proxy(new Object[]{addDeviceBean}, this, changeQuickRedirect, false, 7571, new Class[]{AddDeviceBean.class}, Void.TYPE).isSupported) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(ExifInterface.GPS_DIRECTION_TRUE, (Object) addDeviceBean.getPairingToken());
                jsonObject.put(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, (Object) addDeviceBean.getTimezone());
                c(jsonObject);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            k kVar = k.a;
            kVar.a("send LDS UTC :" + jsonObject.toString(), "LDS");
            this.g.setValue(f.d(g(addDeviceBean.getMac()), jsonObject.toString()));
            if (!this.f.writeCharacteristic(this.g)) {
                kVar.c("send LDS UTC Fail", "LDS");
            }
        }
    }

    private void c(JSONObject json) {
        if (!PatchProxy.proxy(new Object[]{json}, this, changeQuickRedirect, false, 7573, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            f.a(json);
        }
    }

    public void i() {
    }

    public boolean e(e bleNotifyCallback) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleNotifyCallback}, this, changeQuickRedirect, false, 7575, new Class[]{e.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        BluetoothGattCharacteristic characteristic = f(this.f, "a00b");
        BluetoothGatt bluetoothGatt = this.f;
        if (bluetoothGatt == null || characteristic == null) {
            if (bleNotifyCallback != null) {
                bleNotifyCallback.b(new d("gatt or characteristic equal null"));
            }
            return false;
        } else if (!bluetoothGatt.setCharacteristicNotification(characteristic, true)) {
            if (bleNotifyCallback != null) {
                bleNotifyCallback.b(new d("gatt setCharacteristicNotification fail"));
            }
            return false;
        } else {
            for (BluetoothGattDescriptor descriptor1 : characteristic.getDescriptors()) {
                k kVar = k.a;
                kVar.a("notify descriptor:" + descriptor1.getUuid().toString(), this.d);
            }
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            if (descriptor == null) {
                if (bleNotifyCallback != null) {
                    bleNotifyCallback.b(new d("descriptor equals null"));
                }
                return false;
            }
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
            boolean success2 = this.f.writeDescriptor(descriptor);
            if (!success2 && bleNotifyCallback != null) {
                bleNotifyCallback.b(new d("gatt writeDescriptor fail"));
            }
            return success2;
        }
    }

    public void a(BluetoothGattCharacteristic characteristic) {
        if (!PatchProxy.proxy(new Object[]{characteristic}, this, changeQuickRedirect, false, 7576, new Class[]{BluetoothGattCharacteristic.class}, Void.TYPE).isSupported) {
            j(characteristic);
        }
    }

    private void j(BluetoothGattCharacteristic characteristic) {
        if (!PatchProxy.proxy(new Object[]{characteristic}, this, changeQuickRedirect, false, 7577, new Class[]{BluetoothGattCharacteristic.class}, Void.TYPE).isSupported) {
            String str = new String(characteristic.getValue());
            k kVar = k.a;
            kVar.g("收到设备状态上报:" + str, "");
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7578, new Class[0], Void.TYPE).isSupported) {
            i();
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7579, new Class[0], Void.TYPE).isSupported) {
            if (this.f != null) {
                k.a.a("disconnectBle...", this.d);
                this.f.disconnect();
                this.f.close();
            }
        }
    }

    private String g(String mac) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac}, this, changeQuickRedirect, false, 7580, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "DF123EFA234569823098" + mac;
    }
}
