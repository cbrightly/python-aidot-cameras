package com.leedarson.serviceimpl.ble.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.ble.service.TelinkLightService;
import com.leedarson.serviceinterface.BleBusinessService;
import com.leedarson.serviceinterface.event.BluetoothStatusEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import org.greenrobot.eventbus.c;
import org.json.JSONException;
import org.json.JSONObject;

public class BluetoothChangeReceiver extends BroadcastReceiver {
    public static ChangeQuickRedirect changeQuickRedirect;
    final String a = "android.bluetooth.adapter.action.STATE_CHANGED";

    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 6267, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
            if (intent != null && !TextUtils.isEmpty(intent.getAction()) && intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0)) {
                    case 10:
                        c.c().l(new BluetoothStatusEvent(10));
                        Log.e("BluetoothChangeReceiver", "蓝牙已关闭");
                        JSONObject joResponse1 = new JSONObject();
                        try {
                            joResponse1.put("status", -1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, "onBluetoothStatusChange", joResponse1.toString()));
                        BleBusinessService bleBusinessService = (BleBusinessService) a.c().g(BleBusinessService.class);
                        if (bleBusinessService != null) {
                            bleBusinessService.disConnectAllDevices();
                            return;
                        }
                        return;
                    case 11:
                        c.c().l(new BluetoothStatusEvent(11));
                        Log.e("BluetoothChangeReceiver", "正在打开蓝牙中");
                        return;
                    case 12:
                        if (TelinkLightService.h() != null) {
                            TelinkLightService.h().g(true);
                        }
                        c.c().l(new BluetoothStatusEvent(12));
                        Log.e("BluetoothChangeReceiver", "蓝牙已打开");
                        JSONObject joResponse = new JSONObject();
                        try {
                            joResponse.put("status", 1);
                        } catch (JSONException e2) {
                            e2.printStackTrace();
                        }
                        c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, "onBluetoothStatusChange", joResponse.toString()));
                        return;
                    case 13:
                        c.c().l(new BluetoothStatusEvent(13));
                        Log.e("BluetoothChangeReceiver", "正在关闭蓝牙中");
                        return;
                    default:
                        return;
                }
            }
        }
    }
}
