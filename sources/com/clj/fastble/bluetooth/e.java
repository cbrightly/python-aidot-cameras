package com.clj.fastble.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.clj.fastble.callback.d;
import com.clj.fastble.callback.f;
import com.clj.fastble.callback.g;
import com.clj.fastble.callback.h;
import com.clj.fastble.exception.c;
import java.util.UUID;
import meshsdk.cache.CacheHandler;
import timber.log.a;

@TargetApi(18)
/* compiled from: BleConnector */
public class e {
    private static long a = CacheHandler.delayTime;
    private BluetoothGatt b;
    private BluetoothGattService c;
    private BluetoothGattCharacteristic d;
    private d e;
    private Handler f = new a(Looper.getMainLooper());

    e(d bleBluetooth) {
        this.e = bleBluetooth;
        this.b = bleBluetooth.M();
    }

    /* compiled from: BleConnector */
    public class a extends Handler {
        a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 17:
                    com.clj.fastble.callback.e notifyCallback = (com.clj.fastble.callback.e) msg.obj;
                    if (notifyCallback != null) {
                        notifyCallback.b(new com.clj.fastble.exception.e("BleConnector.MSG_CHA_NOTIFY_START"));
                        return;
                    }
                    return;
                case 18:
                    e.this.n();
                    com.clj.fastble.callback.e notifyCallback2 = (com.clj.fastble.callback.e) msg.obj;
                    int status = msg.getData().getInt("notify_status");
                    if (notifyCallback2 == null) {
                        return;
                    }
                    if (status == 0) {
                        notifyCallback2.c();
                        return;
                    } else {
                        notifyCallback2.b(new c(status));
                        return;
                    }
                case 19:
                    com.clj.fastble.callback.e notifyCallback3 = (com.clj.fastble.callback.e) msg.obj;
                    byte[] value = msg.getData().getByteArray("notify_value");
                    if (notifyCallback3 != null) {
                        notifyCallback3.a(value);
                        return;
                    }
                    return;
                case 33:
                    com.clj.fastble.callback.c indicateCallback = (com.clj.fastble.callback.c) msg.obj;
                    if (indicateCallback != null) {
                        indicateCallback.b(new com.clj.fastble.exception.e("BleConnector.MSG_CHA_INDICATE_START"));
                        return;
                    }
                    return;
                case 34:
                    e.this.l();
                    com.clj.fastble.callback.c indicateCallback2 = (com.clj.fastble.callback.c) msg.obj;
                    int status2 = msg.getData().getInt("indicate_status");
                    if (indicateCallback2 == null) {
                        return;
                    }
                    if (status2 == 0) {
                        indicateCallback2.c();
                        return;
                    } else {
                        indicateCallback2.b(new c(status2));
                        return;
                    }
                case 35:
                    com.clj.fastble.callback.c indicateCallback3 = (com.clj.fastble.callback.c) msg.obj;
                    byte[] value2 = msg.getData().getByteArray("indicate_value");
                    if (indicateCallback3 != null) {
                        indicateCallback3.a(value2);
                        return;
                    }
                    return;
                case 49:
                    h writeCallback = (h) msg.obj;
                    if (writeCallback != null) {
                        writeCallback.a(new com.clj.fastble.exception.e("BleConnector.MSG_CHA_WRITE_START"));
                        return;
                    }
                    return;
                case 50:
                    e.this.x();
                    h writeCallback2 = (h) msg.obj;
                    Bundle bundle = msg.getData();
                    int status3 = bundle.getInt("write_status");
                    byte[] value3 = bundle.getByteArray("write_value");
                    if (writeCallback2 == null) {
                        return;
                    }
                    if (status3 == 0) {
                        writeCallback2.b(1, 1, value3);
                        return;
                    } else {
                        writeCallback2.a(new c(status3));
                        return;
                    }
                case 65:
                    f readCallback = (f) msg.obj;
                    if (readCallback != null) {
                        readCallback.a(new com.clj.fastble.exception.e("BleConnector.MSG_CHA_READ_START"));
                        return;
                    }
                    return;
                case 66:
                    e.this.p();
                    f readCallback2 = (f) msg.obj;
                    Bundle bundle2 = msg.getData();
                    int status4 = bundle2.getInt("read_status");
                    byte[] value4 = bundle2.getByteArray("read_value");
                    if (readCallback2 == null) {
                        return;
                    }
                    if (status4 == 0) {
                        readCallback2.b(value4);
                        return;
                    } else {
                        readCallback2.a(new c(status4));
                        return;
                    }
                case 81:
                    g rssiCallback = (g) msg.obj;
                    if (rssiCallback != null) {
                        rssiCallback.a(new com.clj.fastble.exception.e("BleConnector.MSG_READ_RSSI_START"));
                        return;
                    }
                    return;
                case 82:
                    e.this.q();
                    g rssiCallback2 = (g) msg.obj;
                    Bundle bundle3 = msg.getData();
                    int status5 = bundle3.getInt("rssi_status");
                    int value5 = bundle3.getInt("rssi_value");
                    if (rssiCallback2 == null) {
                        return;
                    }
                    if (status5 == 0) {
                        rssiCallback2.b(value5);
                        return;
                    } else {
                        rssiCallback2.a(new c(status5));
                        return;
                    }
                case 97:
                    d mtuChangedCallback = (d) msg.obj;
                    if (mtuChangedCallback != null) {
                        mtuChangedCallback.onSetMTUFailure(new com.clj.fastble.exception.e("BleConnector.MSG_SET_MTU_START"));
                        return;
                    }
                    return;
                case 98:
                    e.this.m();
                    d mtuChangedCallback2 = (d) msg.obj;
                    Bundle bundle4 = msg.getData();
                    int status6 = bundle4.getInt("mtu_status");
                    int value6 = bundle4.getInt("mtu_value");
                    if (mtuChangedCallback2 == null) {
                        return;
                    }
                    if (status6 == 0) {
                        mtuChangedCallback2.onMtuChanged(value6);
                        return;
                    } else {
                        mtuChangedCallback2.onSetMTUFailure(new c(status6));
                        return;
                    }
                default:
                    return;
            }
        }
    }

    public e v(String serviceUUID, String characteristicUUID) {
        return u(f(serviceUUID), f(characteristicUUID));
    }

    private UUID f(String uuid) {
        if (uuid == null) {
            return null;
        }
        return UUID.fromString(uuid);
    }

    private e u(UUID serviceUUID, UUID characteristicUUID) {
        BluetoothGatt bluetoothGatt;
        if (!(serviceUUID == null || (bluetoothGatt = this.b) == null)) {
            this.c = bluetoothGatt.getService(serviceUUID);
        }
        BluetoothGattService bluetoothGattService = this.c;
        if (!(bluetoothGattService == null || characteristicUUID == null)) {
            this.d = bluetoothGattService.getCharacteristic(characteristicUUID);
        }
        return this;
    }

    public void e(com.clj.fastble.callback.e bleNotifyCallback, String uuid_notify, boolean userCharacteristicDescriptor) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.d;
        if (bluetoothGattCharacteristic != null && (bluetoothGattCharacteristic.getProperties() | 16) > 0) {
            h(bleNotifyCallback, uuid_notify);
            s(this.b, this.d, userCharacteristicDescriptor, true, bleNotifyCallback);
        } else if (bleNotifyCallback != null) {
            bleNotifyCallback.b(new com.clj.fastble.exception.d("this characteristic not support notify!"));
        }
    }

    public boolean c(boolean useCharacteristicDescriptor) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.d;
        if (bluetoothGattCharacteristic == null || (bluetoothGattCharacteristic.getProperties() | 16) <= 0) {
            return false;
        }
        return s(this.b, this.d, useCharacteristicDescriptor, false, (com.clj.fastble.callback.e) null);
    }

    private void a(String message) {
        a.b g = timber.log.a.g("BleConnector");
        g.m("LdsConnectDevice.BleConnector " + message, new Object[0]);
    }

    private boolean s(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, boolean useCharacteristicDescriptor, boolean enable, com.clj.fastble.callback.e bleNotifyCallback) {
        BluetoothGattDescriptor descriptor;
        if (gatt == null || characteristic == null) {
            n();
            if (bleNotifyCallback != null) {
                bleNotifyCallback.b(new com.clj.fastble.exception.d("gatt or characteristic equal null"));
            }
            return false;
        }
        a("设置CharacterNotification enable=" + enable + "   useCharacteristicDescriptor=" + useCharacteristicDescriptor + "  characterUuid=" + characteristic.getUuid());
        boolean success1 = gatt.setCharacteristicNotification(characteristic, enable);
        StringBuilder sb = new StringBuilder();
        sb.append("设置CharacterNotification result=");
        sb.append(enable);
        a(sb.toString());
        if (!success1) {
            n();
            if (bleNotifyCallback != null) {
                bleNotifyCallback.b(new com.clj.fastble.exception.d("gatt setCharacteristicNotification fail"));
            }
            return false;
        }
        if (useCharacteristicDescriptor) {
            descriptor = characteristic.getDescriptor(characteristic.getUuid());
            a("设置CharacterNotification   1111");
        } else {
            descriptor = characteristic.getDescriptor(f("00002902-0000-1000-8000-00805f9b34fb"));
            a("设置CharacterNotification   2222");
        }
        if (descriptor == null) {
            n();
            a("设置CharacterNotification   3333  blenotifyCallback=" + bleNotifyCallback);
            if (bleNotifyCallback != null) {
                bleNotifyCallback.b(new com.clj.fastble.exception.d("descriptor equals null"));
            }
            return false;
        }
        descriptor.setValue(enable ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        boolean success2 = gatt.writeDescriptor(descriptor);
        a("设置CharacterNotification   4444 success=" + success2 + "  gatt=" + gatt.toString());
        if (!success2) {
            n();
            a("设置CharacterNotification   5555 bleNotifyCallback=" + bleNotifyCallback);
            if (bleNotifyCallback != null) {
                bleNotifyCallback.b(new com.clj.fastble.exception.d("gatt writeDescriptor fail   gatt:" + gatt.toString()));
            }
        }
        return success2;
    }

    public void d(com.clj.fastble.callback.c bleIndicateCallback, String uuid_indicate, boolean useCharacteristicDescriptor) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.d;
        if (bluetoothGattCharacteristic != null && (bluetoothGattCharacteristic.getProperties() | 16) > 0) {
            g(bleIndicateCallback, uuid_indicate);
            r(this.b, this.d, useCharacteristicDescriptor, true, bleIndicateCallback);
        } else if (bleIndicateCallback != null) {
            bleIndicateCallback.b(new com.clj.fastble.exception.d("this characteristic not support indicate!"));
        }
    }

    public boolean b(boolean userCharacteristicDescriptor) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.d;
        if (bluetoothGattCharacteristic == null || (bluetoothGattCharacteristic.getProperties() | 16) <= 0) {
            return false;
        }
        return r(this.b, this.d, userCharacteristicDescriptor, false, (com.clj.fastble.callback.c) null);
    }

    private boolean r(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, boolean useCharacteristicDescriptor, boolean enable, com.clj.fastble.callback.c bleIndicateCallback) {
        BluetoothGattDescriptor descriptor;
        byte[] bArr;
        if (gatt == null || characteristic == null) {
            l();
            if (bleIndicateCallback != null) {
                bleIndicateCallback.b(new com.clj.fastble.exception.d("gatt or characteristic equal null"));
            }
            return false;
        } else if (!gatt.setCharacteristicNotification(characteristic, enable)) {
            l();
            if (bleIndicateCallback != null) {
                bleIndicateCallback.b(new com.clj.fastble.exception.d("gatt setCharacteristicNotification fail"));
            }
            return false;
        } else {
            if (useCharacteristicDescriptor) {
                descriptor = characteristic.getDescriptor(characteristic.getUuid());
            } else {
                descriptor = characteristic.getDescriptor(f("00002902-0000-1000-8000-00805f9b34fb"));
            }
            if (descriptor == null) {
                l();
                if (bleIndicateCallback != null) {
                    bleIndicateCallback.b(new com.clj.fastble.exception.d("descriptor equals null"));
                }
                return false;
            }
            if (enable) {
                bArr = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
            } else {
                bArr = BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
            }
            descriptor.setValue(bArr);
            boolean success2 = gatt.writeDescriptor(descriptor);
            if (!success2) {
                l();
                if (bleIndicateCallback != null) {
                    bleIndicateCallback.b(new com.clj.fastble.exception.d("gatt writeDescriptor fail"));
                }
            }
            return success2;
        }
    }

    public void w(byte[] data, h bleWriteCallback, String uuid_write) {
        if (data != null && data.length > 0) {
            BluetoothGattCharacteristic bluetoothGattCharacteristic = this.d;
            if (bluetoothGattCharacteristic == null || (bluetoothGattCharacteristic.getProperties() & 12) == 0) {
                if (bleWriteCallback != null) {
                    bleWriteCallback.a(new com.clj.fastble.exception.d("this characteristic not support write!"));
                }
            } else if (this.d.setValue(data)) {
                StringBuilder tip = new StringBuilder();
                long currentTimeMillis = System.currentTimeMillis();
                boolean flagWriteValue = this.b.writeCharacteristic(this.d);
                j(bleWriteCallback, uuid_write);
                int retryWriteIndex = 1;
                if (!flagWriteValue) {
                    while (!flagWriteValue && retryWriteIndex <= 3) {
                        BluetoothGattCharacteristic _writeCharacterId = this.b.getService(this.d.getService().getUuid()).getCharacteristic(this.d.getUuid());
                        _writeCharacterId.setValue(data);
                        try {
                            Thread.sleep((long) (retryWriteIndex * 200));
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        flagWriteValue = this.b.writeCharacteristic(_writeCharacterId);
                        retryWriteIndex++;
                        tip.append("正在尝试蓝牙写入数据对象重新获取: retryWriteIndex=" + retryWriteIndex + "   flagWriteValue=" + flagWriteValue + "\n");
                    }
                }
                if (!flagWriteValue) {
                    x();
                    if (bleWriteCallback != null) {
                        bleWriteCallback.a(new com.clj.fastble.exception.d("gatt writeCharacteristic fail  gattHash=" + this.b + " " + tip));
                    }
                }
            } else if (bleWriteCallback != null) {
                bleWriteCallback.a(new com.clj.fastble.exception.d("Updates the locally stored value of this characteristic fail"));
            }
        } else if (bleWriteCallback != null) {
            bleWriteCallback.a(new com.clj.fastble.exception.d("the data to be written is empty"));
        }
    }

    public void o(f bleReadCallback, String uuid_read) {
        BluetoothGattCharacteristic bluetoothGattCharacteristic = this.d;
        if (bluetoothGattCharacteristic != null && (bluetoothGattCharacteristic.getProperties() & 2) > 0) {
            i(bleReadCallback, uuid_read);
            if (!this.b.readCharacteristic(this.d)) {
                p();
                if (bleReadCallback != null) {
                    bleReadCallback.a(new com.clj.fastble.exception.d("gatt readCharacteristic fail"));
                }
            }
        } else if (bleReadCallback != null) {
            bleReadCallback.a(new com.clj.fastble.exception.d("this characteristic not support read!"));
        }
    }

    public void t(int requiredMtu, d bleMtuChangedCallback) {
        if (Build.VERSION.SDK_INT >= 21) {
            k(bleMtuChangedCallback);
            if (!this.b.requestMtu(requiredMtu)) {
                m();
                if (bleMtuChangedCallback != null) {
                    bleMtuChangedCallback.onSetMTUFailure(new com.clj.fastble.exception.d("gatt requestMtu fail"));
                }
            }
        } else if (bleMtuChangedCallback != null) {
            bleMtuChangedCallback.onSetMTUFailure(new com.clj.fastble.exception.d("API level lower than 21"));
        }
    }

    private void h(com.clj.fastble.callback.e bleNotifyCallback, String uuid_notify) {
        if (bleNotifyCallback != null) {
            n();
            bleNotifyCallback.setKey(uuid_notify);
            bleNotifyCallback.setHandler(this.f);
            this.e.D(uuid_notify, bleNotifyCallback);
            Handler handler = this.f;
            handler.sendMessageDelayed(handler.obtainMessage(17, bleNotifyCallback), (long) com.clj.fastble.a.o().r());
        }
    }

    private void g(com.clj.fastble.callback.c bleIndicateCallback, String uuid_indicate) {
        if (bleIndicateCallback != null) {
            l();
            bleIndicateCallback.setKey(uuid_indicate);
            bleIndicateCallback.setHandler(this.f);
            this.e.B(uuid_indicate, bleIndicateCallback);
            Handler handler = this.f;
            handler.sendMessageDelayed(handler.obtainMessage(33, bleIndicateCallback), (long) com.clj.fastble.a.o().r());
        }
    }

    private void j(h bleWriteCallback, String uuid_write) {
        if (bleWriteCallback != null) {
            x();
            bleWriteCallback.setKey(uuid_write);
            bleWriteCallback.setHandler(this.f);
            this.e.F(uuid_write, bleWriteCallback);
            Handler handler = this.f;
            handler.sendMessageDelayed(handler.obtainMessage(49, bleWriteCallback), (long) com.clj.fastble.a.o().r());
        }
    }

    private void i(f bleReadCallback, String uuid_read) {
        if (bleReadCallback != null) {
            p();
            bleReadCallback.setKey(uuid_read);
            bleReadCallback.setHandler(this.f);
            this.e.E(uuid_read, bleReadCallback);
            Handler handler = this.f;
            handler.sendMessageDelayed(handler.obtainMessage(65, bleReadCallback), (long) com.clj.fastble.a.o().r());
        }
    }

    private void k(d bleMtuChangedCallback) {
        if (bleMtuChangedCallback != null) {
            m();
            bleMtuChangedCallback.setHandler(this.f);
            this.e.C(bleMtuChangedCallback);
            Handler handler = this.f;
            handler.sendMessageDelayed(handler.obtainMessage(97, bleMtuChangedCallback), (long) com.clj.fastble.a.o().r());
        }
    }

    public void n() {
        this.f.removeMessages(17);
    }

    public void l() {
        this.f.removeMessages(33);
    }

    public void x() {
        this.f.removeMessages(49);
    }

    public void p() {
        this.f.removeMessages(65);
    }

    public void q() {
        this.f.removeMessages(81);
    }

    public void m() {
        this.f.removeMessages(97);
    }
}
