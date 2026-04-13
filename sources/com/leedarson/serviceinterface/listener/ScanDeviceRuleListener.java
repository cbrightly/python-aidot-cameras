package com.leedarson.serviceinterface.listener;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import androidx.annotation.Nullable;

public interface ScanDeviceRuleListener {
    boolean checkIsTarget(BluetoothDevice bluetoothDevice, int i, byte[] bArr, @Nullable ScanRecord scanRecord);
}
