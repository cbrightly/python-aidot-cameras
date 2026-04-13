package com.leedarson.serviceimpl.blec075.impls;

import android.text.TextUtils;
import com.leedarson.base.beans.CommonBleReadConfigBean;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.blec075.BleC075ServiceImpl;
import com.leedarson.serviceimpl.blec075.callback.a;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: LdsBleNotifyCallbackImpl */
public class b extends com.leedarson.serviceimpl.blec075.callback.b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private CommonBleReadConfigBean a;
    private a b;

    public b(CommonBleReadConfigBean configBean, a _trackElkHandler) {
        this.a = configBean;
    }

    public void onSimpleNotifyError(Exception exception, String callbackKey, String mac, int gatt) {
        Class<String> cls = String.class;
        Object[] objArr = {exception, callbackKey, mac, new Integer(gatt)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6481, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("bleNotify");
            g.n("onNotifyFailure mac:" + mac + ",characterUUID:" + this.a.serviceUUID.toString() + ",msg:" + exception.getMessage(), new Object[0]);
            com.leedarson.serviceimpl.blec075.callback.a aVar = this.b;
            if (aVar != null) {
                aVar.a("onNotifyFailure:" + mac + ",ex:" + exception, "notifyBle", "info", "commonNotify", callbackKey, mac);
            }
            try {
                JSONObject j2 = new JSONObject();
                j2.put("code", BleC075ServiceImpl.e);
                j2.put("desc", (Object) exception.getMessage());
                j2.put("gatt", gatt);
                j2.put("mac", (Object) mac);
                c.c().l(new JsBridgeCallbackEvent(callbackKey, j2.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                timber.log.a.c("start notify Exception: " + e.toString(), new Object[0]);
            }
            BleC075Service.CommonBleCallback commonBleCallback = this.a.commonBleCallback;
            if (commonBleCallback != null) {
                commonBleCallback.onNotifyFail(exception, callbackKey, mac, gatt);
            }
        }
    }

    public void onSimpleNotifySuccess(String callbackKey, String mac) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackKey, mac}, this, changeQuickRedirect, false, 6482, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("bleNotify");
            g.a("onNotifySuccess mac:" + mac + ",characterUUID:" + this.a.characterUUID.toString(), new Object[0]);
            com.leedarson.serviceimpl.blec075.callback.a aVar = this.b;
            if (aVar != null) {
                aVar.a("onNotifySuccess:" + mac, "notifyBle", "info", "commonNotify", callbackKey, mac);
            }
            try {
                JSONObject j2 = new JSONObject();
                j2.put("code", 200);
                j2.put("desc", (Object) "");
                j2.put("mac", (Object) mac);
                c.c().l(new JsBridgeCallbackEvent(callbackKey, j2.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                timber.log.a.c("start notify Exception: " + e.toString(), new Object[0]);
            }
            BleC075Service.CommonBleCallback commonBleCallback = this.a.commonBleCallback;
            if (commonBleCallback != null) {
                commonBleCallback.onNotifySuccess(callbackKey, mac);
            }
        }
    }

    public void onSimpleCharacteristicChanged(byte[] data, String str, String mac) {
        Class<String> cls = String.class;
        Class[] clsArr = {byte[].class, cls, cls};
        if (!PatchProxy.proxy(new Object[]{data, str, mac}, this, changeQuickRedirect, false, 6483, clsArr, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("bleNotify");
            g.a("onCharacteristicChanged charUUID:" + this.a.characterUUID.toString() + ",data:" + h.b(data), new Object[0]);
            CommonBleReadConfigBean commonBleReadConfigBean = this.a;
            BleC075Service.CommonBleCallback commonBleCallback = commonBleReadConfigBean.commonBleCallback;
            if (commonBleCallback != null) {
                commonBleCallback.onCharacteristicChanged(data, (String) null, mac);
                return;
            }
            byte[] bArr = data;
            byte[] recdeC = null;
            if (!TextUtils.isEmpty(commonBleReadConfigBean.encryptKey)) {
                recdeC = decryptDataByConfig(this.a, data);
            }
            JSONObject jsonObject3 = new JSONObject();
            try {
                jsonObject3.put("code", 200);
                jsonObject3.put("desc", (Object) "");
                if (recdeC != null) {
                    jsonObject3.put("data", (Object) new String(recdeC, "utf-8"));
                } else {
                    jsonObject3.put("data", (Object) h.b(data));
                }
                jsonObject3.put("characterUUID", (Object) this.a.characterUUID.toString());
                jsonObject3.put("serviceUUID", (Object) this.a.serviceUUID.toString());
                jsonObject3.put("mac", (Object) mac);
            } catch (Exception e) {
                e.printStackTrace();
            }
            c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, "onMessage", jsonObject3.toString()));
        }
    }
}
