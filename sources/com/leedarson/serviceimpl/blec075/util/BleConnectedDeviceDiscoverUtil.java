package com.leedarson.serviceimpl.blec075.util;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import com.leedarson.bean.IRhyDevice;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import timber.log.a;

public class BleConnectedDeviceDiscoverUtil {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static boolean b(String macAddress) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{macAddress}, (Object) null, changeQuickRedirect, true, 6598, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!BluetoothAdapter.checkBluetoothAddress(macAddress)) {
            return false;
        }
        BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macAddress);
        try {
            Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
            isConnectedMethod.setAccessible(true);
            return ((Boolean) isConnectedMethod.invoke(device, (Object[]) null)).booleanValue();
        } catch (NoSuchMethodException e) {
            return false;
        } catch (IllegalAccessException e2) {
            return false;
        } catch (InvocationTargetException e3) {
            return false;
        }
    }

    @SuppressLint({"MissingPermission"})
    public static BlutoothConnectedDeviceListBean a(Context context) {
        String Type;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 6600, new Class[]{Context.class}, BlutoothConnectedDeviceListBean.class);
        if (proxy.isSupported) {
            return (BlutoothConnectedDeviceListBean) proxy.result;
        }
        Set<BluetoothDevice> result = new HashSet<>();
        Set<BluetoothDevice> deviceSet = new HashSet<>();
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService("bluetooth");
        List GattDevices = bluetoothManager.getConnectedDevices(7);
        if (GattDevices != null && GattDevices.size() > 0) {
            deviceSet.addAll(GattDevices);
        }
        Set ClassicDevices = bluetoothManager.getAdapter().getBondedDevices();
        if (ClassicDevices != null && ClassicDevices.size() > 0) {
            deviceSet.addAll(ClassicDevices);
        }
        for (BluetoothDevice dev : deviceSet) {
            switch (dev.getType()) {
                case 1:
                    Type = "经典";
                    break;
                case 2:
                    Type = IRhyDevice.TYPE_BLE;
                    break;
                case 3:
                    Type = "双模";
                    break;
                default:
                    Type = "未知";
                    break;
            }
            String connect = "设备未连接";
            if (b(dev.getAddress())) {
                result.add(dev);
                connect = "设备已连接";
            }
            a.i("手机蓝牙状态 : " + connect + ", address = " + dev.getAddress() + "(" + Type + "), name --> " + dev.getName(), new Object[0]);
        }
        BlutoothConnectedDeviceListBean _tempCallBackBean = new BlutoothConnectedDeviceListBean();
        _tempCallBackBean.result = result;
        return _tempCallBackBean;
    }

    public static class BlutoothConnectedDeviceListBean {
        public static ChangeQuickRedirect changeQuickRedirect;
        public int connectedDeviceSize = 0;
        public String dataResult = "";
        Set<BluetoothDevice> result = new HashSet();

        public ArrayList<BluetoothDevice> getConnectedDevices() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6601, new Class[0], ArrayList.class);
            if (proxy.isSupported) {
                return (ArrayList) proxy.result;
            }
            ArrayList<BluetoothDevice> _temps = new ArrayList<>();
            for (BluetoothDevice item : this.result) {
                _temps.add(item);
            }
            this.connectedDeviceSize = _temps.size();
            return _temps;
        }

        @SuppressLint({"MissingPermission"})
        public String printResult() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6602, new Class[0], String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            ArrayList<BluetoothDevice> _devices = getConnectedDevices();
            StringBuilder _buildOfResult = new StringBuilder();
            _buildOfResult.append("手机蓝牙状态: 共计" + _devices.size() + "已连接 \n  详情信息如下：");
            for (int i = 0; i < _devices.size(); i++) {
                _buildOfResult.append(" address=" + _devices.get(i).getAddress() + "  name:" + _devices.get(i).getName() + "\n");
            }
            this.dataResult = _buildOfResult.toString();
            return _buildOfResult.toString();
        }

        public BluetoothDevice checkDeviceIsConnected(String devIdOrMac) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{devIdOrMac}, this, changeQuickRedirect, false, 6603, new Class[]{String.class}, BluetoothDevice.class);
            if (proxy.isSupported) {
                return (BluetoothDevice) proxy.result;
            }
            ArrayList<BluetoothDevice> _devices = getConnectedDevices();
            for (int i = 0; i < _devices.size(); i++) {
                if (_devices.get(i).getAddress().contains(devIdOrMac.replace(":", ""))) {
                    return _devices.get(i);
                }
            }
            return null;
        }
    }
}
