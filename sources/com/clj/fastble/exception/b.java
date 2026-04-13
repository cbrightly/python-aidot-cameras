package com.clj.fastble.exception;

import android.bluetooth.BluetoothGatt;

/* compiled from: ConnectException */
public class b extends a {
    private BluetoothGatt bluetoothGatt;
    private int gattStatus;

    public b(BluetoothGatt bluetoothGatt2, int gattStatus2) {
        super(101, "ConnectException Gatt Exception Occurred! gattStatus=" + gattStatus2);
        this.bluetoothGatt = bluetoothGatt2;
        this.gattStatus = gattStatus2;
    }

    public int getGattStatus() {
        return this.gattStatus;
    }

    public b setGattStatus(int gattStatus2) {
        this.gattStatus = gattStatus2;
        return this;
    }

    public BluetoothGatt getBluetoothGatt() {
        return this.bluetoothGatt;
    }

    public b setBluetoothGatt(BluetoothGatt bluetoothGatt2) {
        this.bluetoothGatt = bluetoothGatt2;
        return this;
    }

    public String toString() {
        return "ConnectException{gattStatus=" + this.gattStatus + ", bluetoothGatt=" + this.bluetoothGatt + "} " + super.toString();
    }
}
