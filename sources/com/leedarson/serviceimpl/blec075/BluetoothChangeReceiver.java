package com.leedarson.serviceimpl.blec075;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import org.greenrobot.eventbus.c;
import org.json.JSONObject;

public class BluetoothChangeReceiver extends BroadcastReceiver {
    public static ChangeQuickRedirect changeQuickRedirect;
    final String a = "android.bluetooth.adapter.action.STATE_CHANGED";

    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 6391, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
            Log.e("BluetoothChangeReceiver", "BluetoothChangeReceiver: " + intent.getAction());
            if (!TextUtils.isEmpty(intent.getAction()) && intent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0)) {
                    case 10:
                        Log.e("BluetoothChangeReceiver", "蓝牙已关闭");
                        JSONObject jsonObject1 = new JSONObject();
                        try {
                            jsonObject1.put("code", 0);
                            jsonObject1.put("desc", (Object) "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent intent2 = new Intent("com.leedarson.BLE_ENABLE_STATE_CHANGE");
                        intent2.putExtra("com.leedarson.EXTRAS_BLE_ENABLE", 0);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
                        return;
                    case 11:
                        Log.e("BluetoothChangeReceiver", "正在打开蓝牙中");
                        return;
                    case 12:
                        Log.e("BluetoothChangeReceiver", "蓝牙已打开");
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("code", 1);
                            jsonObject.put("desc", (Object) "");
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        c.c().l(new JsCallH5ByNativeEvent("MeshBLEService", "BluetoothStateUpdated", jsonObject.toString()));
                        return;
                    case 13:
                        Log.e("BluetoothChangeReceiver", "正在关闭蓝牙中");
                        return;
                    default:
                        return;
                }
            }
        }
    }
}
