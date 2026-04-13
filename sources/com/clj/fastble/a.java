package com.clj.fastble;

import android.annotation.TargetApi;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.clj.fastble.bluetooth.f;
import com.clj.fastble.bluetooth.g;
import com.clj.fastble.callback.c;
import com.clj.fastble.callback.e;
import com.clj.fastble.callback.h;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.d;
import com.clj.fastble.scan.b;
import java.util.List;

@TargetApi(18)
/* compiled from: BleManager */
public class a {
    private Application a;
    private b b;
    private BluetoothAdapter c;
    private f d;
    private BluetoothManager e;
    private int f = 14;
    private int g = 7000;
    private int h = 0;
    private long i = KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS;
    private int j = 20;
    private long k = 10000;

    /* renamed from: com.clj.fastble.a$a  reason: collision with other inner class name */
    /* compiled from: BleManager */
    public static class C0063a {
        /* access modifiers changed from: private */
        public static final a a = new a();
    }

    public static a o() {
        return C0063a.a;
    }

    public void y(Application app) {
        if (this.a == null && app != null) {
            this.a = app;
            if (C()) {
                this.e = (BluetoothManager) this.a.getSystemService("bluetooth");
            }
            this.c = BluetoothAdapter.getDefaultAdapter();
            this.d = new f();
            this.b = new b();
        }
    }

    public Context n() {
        return this.a;
    }

    public f q() {
        return this.d;
    }

    public int p() {
        return this.f;
    }

    public int r() {
        return this.g;
    }

    public a I(int count) {
        this.g = count;
        return this;
    }

    public int s() {
        return this.h;
    }

    public long t() {
        return this.i;
    }

    public int v() {
        return this.j;
    }

    public long l() {
        return this.k;
    }

    public a G(long time) {
        if (time <= 0) {
            time = 100;
        }
        this.k = time;
        return this;
    }

    public a f(boolean enable) {
        com.clj.fastble.utils.a.a = enable;
        return this;
    }

    public BluetoothGatt b(BleDevice bleDevice, com.clj.fastble.callback.b bleGattCallback) {
        if (bleGattCallback == null) {
            throw new IllegalArgumentException("BleGattCallback can not be Null!");
        } else if (!z()) {
            com.clj.fastble.utils.a.a("Bluetooth not enable!");
            bleGattCallback.b(bleDevice, new d("Bluetooth not enable!"));
            return null;
        } else {
            if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()) {
                com.clj.fastble.utils.a.c("Be careful: currentThread is not MainThread!");
            }
            if (bleDevice == null || bleDevice.a() == null) {
                bleGattCallback.b(bleDevice, new d("Not Found Device Exception Occurred!"));
                return null;
            }
            com.clj.fastble.bluetooth.d bleBluetooth = this.d.e(bleDevice);
            if (bleBluetooth == null) {
                bleBluetooth = this.d.b(bleDevice);
            }
            return bleBluetooth.I(bleDevice, this.b.a(), bleGattCallback);
        }
    }

    public BluetoothGatt c(BleDevice bleDevice, com.clj.fastble.callback.b bleGattCallback) {
        return b(bleDevice, bleGattCallback);
    }

    public void D(BleDevice bleDevice, String uuid_service, String uuid_notify, e callback) {
        E(bleDevice, uuid_service, uuid_notify, false, callback);
    }

    public void E(BleDevice bleDevice, String uuid_service, String uuid_notify, boolean useCharacteristicDescriptor, e callback) {
        a("开始准备 Notify");
        if (callback != null) {
            a("检测传参数：  bleBluetooth");
            com.clj.fastble.bluetooth.d bleBluetooth = this.d.e(bleDevice);
            if (bleBluetooth == null) {
                a("检测传参数：  bleBluetooth==null（设备没有连接上）");
                callback.b(new d("This device not connect!"));
                return;
            }
            a("检测传参数：  bleBluetooth（已经连接上） serviceUuid=" + uuid_service + "  characterUuid=" + uuid_notify + "  useCharacteristicDescriptor=" + useCharacteristicDescriptor);
            bleBluetooth.P().v(uuid_service, uuid_notify).e(callback, uuid_notify, useCharacteristicDescriptor);
            return;
        }
        a("开始准备 Notify(Callback为空，不往下执行)");
        throw new IllegalArgumentException("BleNotifyCallback can not be Null!");
    }

    private void a(String msg) {
        timber.log.a.i("LdsConnectDevice.BleManager " + msg, new Object[0]);
    }

    public void w(BleDevice bleDevice, String uuid_service, String uuid_indicate, c callback) {
        x(bleDevice, uuid_service, uuid_indicate, false, callback);
    }

    public void x(BleDevice bleDevice, String uuid_service, String uuid_indicate, boolean useCharacteristicDescriptor, c callback) {
        if (callback != null) {
            com.clj.fastble.bluetooth.d bleBluetooth = this.d.e(bleDevice);
            if (bleBluetooth == null) {
                callback.b(new d("This device not connect!"));
            } else {
                bleBluetooth.P().v(uuid_service, uuid_indicate).d(callback, uuid_indicate, useCharacteristicDescriptor);
            }
        } else {
            throw new IllegalArgumentException("BleIndicateCallback can not be Null!");
        }
    }

    public boolean L(BleDevice bleDevice, String uuid_service, String uuid_notify) {
        return M(bleDevice, uuid_service, uuid_notify, false);
    }

    public boolean M(BleDevice bleDevice, String uuid_service, String uuid_notify, boolean useCharacteristicDescriptor) {
        com.clj.fastble.bluetooth.d bleBluetooth = this.d.e(bleDevice);
        if (bleBluetooth == null) {
            return false;
        }
        boolean success = bleBluetooth.P().v(uuid_service, uuid_notify).c(useCharacteristicDescriptor);
        if (success) {
            bleBluetooth.T(uuid_notify);
        }
        return success;
    }

    public boolean J(BleDevice bleDevice, String uuid_service, String uuid_indicate) {
        return K(bleDevice, uuid_service, uuid_indicate, false);
    }

    public boolean K(BleDevice bleDevice, String uuid_service, String uuid_indicate, boolean useCharacteristicDescriptor) {
        com.clj.fastble.bluetooth.d bleBluetooth = this.d.e(bleDevice);
        if (bleBluetooth == null) {
            return false;
        }
        boolean success = bleBluetooth.P().v(uuid_service, uuid_indicate).b(useCharacteristicDescriptor);
        if (success) {
            bleBluetooth.R(uuid_indicate);
        }
        return success;
    }

    public void N(BleDevice bleDevice, String uuid_service, String uuid_write, byte[] data, boolean split, h callback) {
        O(bleDevice, uuid_service, uuid_write, data, split, true, 0, callback);
    }

    public void O(BleDevice bleDevice, String uuid_service, String uuid_write, byte[] data, boolean split, boolean sendNextWhenLastSuccess, long intervalBetweenTwoPackage, h callback) {
        String str = uuid_write;
        byte[] bArr = data;
        h hVar = callback;
        if (hVar == null) {
            BleDevice bleDevice2 = bleDevice;
            String str2 = uuid_service;
            throw new IllegalArgumentException("BleWriteCallback can not be Null!");
        } else if (bArr == null) {
            com.clj.fastble.utils.a.a("data is Null!");
            hVar.a(new d("data is Null!"));
        } else {
            if (bArr.length > 20 && !split) {
                com.clj.fastble.utils.a.c("Be careful: data's length beyond 20! Ensure MTU higher than 23, or use spilt write!");
            }
            com.clj.fastble.bluetooth.d bleBluetooth = this.d.e(bleDevice);
            if (bleBluetooth == null) {
                hVar.a(new d("This device not connect!"));
                String str3 = uuid_service;
            } else if (!split || bArr.length <= v()) {
                bleBluetooth.P().v(uuid_service, str).w(bArr, hVar, str);
            } else {
                new g().k(bleBluetooth, uuid_service, uuid_write, data, sendNextWhenLastSuccess, intervalBetweenTwoPackage, callback);
                String str4 = uuid_service;
            }
        }
    }

    public void F(BleDevice bleDevice, String uuid_service, String uuid_read, com.clj.fastble.callback.f callback) {
        if (callback != null) {
            com.clj.fastble.bluetooth.d bleBluetooth = this.d.e(bleDevice);
            if (bleBluetooth == null) {
                callback.a(new d("This device is not connected!"));
            } else {
                bleBluetooth.P().v(uuid_service, uuid_read).o(callback, uuid_read);
            }
        } else {
            throw new IllegalArgumentException("BleReadCallback can not be Null!");
        }
    }

    public void H(BleDevice bleDevice, int mtu, com.clj.fastble.callback.d callback) {
        if (callback == null) {
            throw new IllegalArgumentException("BleMtuChangedCallback can not be Null!");
        } else if (mtu > 512) {
            com.clj.fastble.utils.a.a("requiredMtu should lower than 512 !");
            callback.onSetMTUFailure(new d("requiredMtu should lower than 512 !"));
        } else if (mtu < 23) {
            com.clj.fastble.utils.a.a("requiredMtu should higher than 23 !");
            callback.onSetMTUFailure(new d("requiredMtu should higher than 23 !"));
        } else {
            com.clj.fastble.bluetooth.d bleBluetooth = this.d.e(bleDevice);
            if (bleBluetooth == null) {
                callback.onSetMTUFailure(new d("This device is not connected!"));
            } else {
                bleBluetooth.P().t(mtu, callback);
            }
        }
    }

    public boolean C() {
        return Build.VERSION.SDK_INT >= 18 && this.a.getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public boolean z() {
        BluetoothAdapter bluetoothAdapter = this.c;
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public com.clj.fastble.bluetooth.d h(BleDevice bleDevice) {
        f fVar = this.d;
        if (fVar != null) {
            return fVar.e(bleDevice);
        }
        return null;
    }

    public BluetoothGatt i(BleDevice bleDevice) {
        com.clj.fastble.bluetooth.d bleBluetooth = h(bleDevice);
        if (bleBluetooth != null) {
            return bleBluetooth.M();
        }
        return null;
    }

    public List<BluetoothGattService> k(BleDevice bleDevice) {
        BluetoothGatt gatt = i(bleDevice);
        if (gatt != null) {
            return gatt.getServices();
        }
        return null;
    }

    public List<BluetoothGattCharacteristic> j(BluetoothGattService service) {
        return service.getCharacteristics();
    }

    public com.clj.fastble.data.b u() {
        return com.clj.fastble.scan.c.a().b();
    }

    public List<BleDevice> g() {
        f fVar = this.d;
        if (fVar == null) {
            return null;
        }
        return fVar.g();
    }

    public int m(BleDevice bleDevice) {
        if (bleDevice != null) {
            return this.e.getConnectionState(bleDevice.a(), 7);
        }
        return 0;
    }

    public boolean A(BleDevice bleDevice) {
        return m(bleDevice) == 2;
    }

    public boolean B(String mac) {
        for (BleDevice bleDevice : g()) {
            if (bleDevice != null && bleDevice.c().equals(mac)) {
                return true;
            }
        }
        return false;
    }

    public void d(BleDevice bleDevice) {
        f fVar = this.d;
        if (fVar != null) {
            fVar.c(bleDevice);
        }
    }

    public void e() {
        f fVar = this.d;
        if (fVar != null) {
            fVar.d();
        }
    }
}
