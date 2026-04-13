package com.leedarson.serviceimpl.blec075.beans;

import com.clj.fastble.data.BleDevice;
import java.util.concurrent.ConcurrentHashMap;

public class BleConnectConfigBean {
    public String callbackKey = "";
    public ConcurrentHashMap<String, String> connectCallbackMap = new ConcurrentHashMap<>();
    public BleDevice connectDevice;
    public ConcurrentHashMap<String, BleDevice> connectedDeviceMap = new ConcurrentHashMap<>();
    public String data = "";
    public ConcurrentHashMap<String, String> deviceMacMap = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, Boolean> unConnectedBlePool = new ConcurrentHashMap<>();
}
