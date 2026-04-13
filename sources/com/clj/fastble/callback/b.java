package com.clj.fastble.callback;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.a;

@TargetApi(18)
/* compiled from: BleGattCallback */
public abstract class b extends BluetoothGattCallback {
    public abstract void a(int i, String str);

    public abstract void b(BleDevice bleDevice, a aVar);

    public abstract void c(BleDevice bleDevice, BluetoothGatt bluetoothGatt, int i);

    public abstract void d(boolean z, BleDevice bleDevice, BluetoothGatt bluetoothGatt, int i);

    public abstract void e();
}
