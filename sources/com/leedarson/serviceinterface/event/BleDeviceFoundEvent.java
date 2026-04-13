package com.leedarson.serviceinterface.event;

import android.bluetooth.BluetoothDevice;

public class BleDeviceFoundEvent {
    public static final int DIR_BLE_TO_MESH = 0;
    public static final int DIR_MESH_TO_BLE = 1;
    public BluetoothDevice bluetoothDevice;
    public int direction;
    public int rssi;
    public byte[] scanRecord;

    public BleDeviceFoundEvent(int direction2) {
        this.direction = direction2;
    }
}
