package com.leedarson.serviceimpl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.base.c;
import com.leedarson.serviceinterface.BleBusinessService;
import com.leedarson.serviceinterface.event.BluetoothStatusEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import org.json.JSONObject;

public class BluetoothChangeReceiver extends BroadcastReceiver {
    public static ChangeQuickRedirect changeQuickRedirect;
    final String a = "android.bluetooth.adapter.action.STATE_CHANGED";

    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 5883, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
            Log.e("BluetoothChangeReceiver", "BluetoothChangeReceiver: " + intent.getAction());
            if (!TextUtils.isEmpty(intent.getAction()) && intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                int blueState = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0);
                try {
                    JSONObject jsonObject = new JSONObject();
                    switch (blueState) {
                        case 10:
                            c.k().C(1);
                            org.greenrobot.eventbus.c.c().l(new BluetoothStatusEvent(10));
                            Log.e("BluetoothChangeReceiver", "蓝牙已关闭");
                            jsonObject.put("status", -1);
                            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, "onBluetoothStatusChange", jsonObject.toString()));
                            c.k().C(1);
                            BleBusinessService bleBusinessService = (BleBusinessService) a.c().g(BleBusinessService.class);
                            if (bleBusinessService != null) {
                                bleBusinessService.disConnectAllDevices();
                                return;
                            }
                            return;
                        case 11:
                            org.greenrobot.eventbus.c.c().l(new BluetoothStatusEvent(11));
                            Log.e("BluetoothChangeReceiver", "正在打开蓝牙中");
                            return;
                        case 12:
                            org.greenrobot.eventbus.c.c().l(new BluetoothStatusEvent(12));
                            Log.e("BluetoothChangeReceiver", "蓝牙已打开666");
                            jsonObject.put("status", 1);
                            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, "onBluetoothStatusChange", jsonObject.toString()));
                            return;
                        case 13:
                            org.greenrobot.eventbus.c.c().l(new BluetoothStatusEvent(13));
                            Log.e("BluetoothChangeReceiver", "正在关闭蓝牙中");
                            return;
                        default:
                            return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }
}
