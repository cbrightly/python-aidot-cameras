package com.leedarson.serviceinterface;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.base.beans.CommonBleReadConfigBean;
import com.leedarson.base.beans.a;
import com.leedarson.serviceinterface.listener.ScanDeviceRuleListener;
import io.reactivex.e;
import java.util.UUID;

public interface BleC075Service extends c, LDSService {

    public interface CommonBleCallback {
        void onCharacteristicChanged(byte[] bArr, String str, String str2);

        void onNotifyFail(Exception exc, String str, String str2, int i);

        void onNotifySuccess(String str, String str2);

        void onReadFailure(Exception exc, String str, String str2, int i);

        void onReadSuccess(byte[] bArr, String str, String str2);

        void onWriteFailure(Exception exc, String str, String str2, int i);

        void onWriteSuccess(int i, int i2, byte[] bArr, String str, String str2);
    }

    public interface CommonBleConnectCallback {
        void onConnectFail(int i, String str, int i2, String str2);

        void onConnectSuccess(String str, BluetoothGatt bluetoothGatt, int i);

        void onDisConnected(String str, BluetoothGatt bluetoothGatt, int i);
    }

    void clearCache();

    void commonConnect(String str, String str2, boolean z, BluetoothDevice bluetoothDevice, CommonBleConnectCallback commonBleConnectCallback, String str3, boolean z2, String str4);

    void commonDisconnect(String str);

    void commonIndicate(String str, UUID uuid, UUID uuid2, String str2, CommonBleCallback commonBleCallback);

    void commonIndicateConfig(CommonBleReadConfigBean commonBleReadConfigBean);

    void commonNotify(String str, UUID uuid, UUID uuid2, String str2, String str3, CommonBleCallback commonBleCallback);

    void commonNotifyConfig(CommonBleReadConfigBean commonBleReadConfigBean);

    void commonRead(CommonBleReadConfigBean commonBleReadConfigBean);

    void commonScan();

    void commonWrite(String str, BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, String str2, byte[] bArr, String str3, CommonBleCallback commonBleCallback, boolean z, long j);

    void commonWriteWithoutResponse(String str, BluetoothDevice bluetoothDevice, UUID uuid, UUID uuid2, byte[] bArr);

    boolean deleteBleCacheDeviceBeanByBzMac(String str);

    void disConnectTask(String str, String str2);

    Object getBleCacheDeviceBeanByBzMac(String str);

    int getConnectedDeviceRssiDetail(String str);

    String getConnectedDevicesV2(Context context);

    String getDeviceMac(String str);

    void handleData(Activity activity, String str, String str2, String str3);

    void handleNewData(Activity activity, String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initBle(Activity activity);

    boolean isConnected(String str);

    boolean isConnecting(String str);

    boolean isScaning();

    BluetoothGatt matchConnectedDeviceGatt(String str);

    void postCacheToJs();

    void scan(int i, boolean z);

    void scan(boolean z);

    e<a> seekForTargetBloothDevice(ScanDeviceRuleListener scanDeviceRuleListener, boolean z, String str, String str2);

    void setActionReset(String str);

    void setActionStop();

    void setScanForConnect(boolean z);

    void startBleMeshWakeUpAdvertising(String str, String str2);

    void stopScan();

    String toHexAdvertData(byte[] bArr);
}
