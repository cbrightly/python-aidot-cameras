package com.leedarson.serviceimpl.blec075.ldsblecaches;

import android.bluetooth.BluetoothGatt;
import com.clj.fastble.data.BleDevice;
import com.leedarson.serviceinterface.BleC075Service;

public class LDSBleCacheItemBean {
    public static final int CONNECT_STATUE_CONNECTED = 2;
    public static final int CONNECT_STATUE_CONNECTING = 1;
    public static final int CONNECT_STATUE_DISCONNECTED = 4;
    public static final int CONNECT_STATUE_DISCONNECTING = 3;
    public static final int CONNECT_STATUE_IDLE = 0;
    public int CONNECT_STATUE = 0;
    public BluetoothGatt _bleGattConnection;
    public BleC075Service.CommonBleConnectCallback _callbackProxy;
    public h _taskItemDesc;
    public BleDevice bleDevice;
    public String bzMac = "";
    public long createTimeSpan = 0;
    public long endTimeSpan = 0;
    public String realDeviceMac = "";
    public int retryTimes = 0;
}
