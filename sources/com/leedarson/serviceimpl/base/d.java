package com.leedarson.serviceimpl.base;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import androidx.annotation.Nullable;

/* compiled from: ScannerObserver */
public interface d {
    String onFromBz();

    void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bArr, @Nullable ScanRecord scanRecord);

    void onScanFail(int i);
}
