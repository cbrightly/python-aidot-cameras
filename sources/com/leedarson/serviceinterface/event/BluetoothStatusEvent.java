package com.leedarson.serviceinterface.event;

import com.meituan.robust.ChangeQuickRedirect;

public class BluetoothStatusEvent {
    public static final int STATE_ON = 0;
    public static final int STATE_TURNING_ON = 1;
    public static ChangeQuickRedirect changeQuickRedirect;
    private int bluetoothStatus;

    public BluetoothStatusEvent(int bluetoothStatus2) {
        setBluetoothStatus(bluetoothStatus2);
    }

    public int getBluetoothStatus() {
        return this.bluetoothStatus;
    }

    public void setBluetoothStatus(int bluetoothStatus2) {
        this.bluetoothStatus = bluetoothStatus2;
    }
}
