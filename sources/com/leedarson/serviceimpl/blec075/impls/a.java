package com.leedarson.serviceimpl.blec075.impls;

import android.text.TextUtils;
import com.clj.fastble.callback.c;
import com.leedarson.base.beans.CommonBleReadConfigBean;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: LdsBleIndicateCallbackImpl */
public class a extends c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private CommonBleReadConfigBean a;
    private com.leedarson.serviceimpl.blec075.callback.a b;

    public a(CommonBleReadConfigBean configBean, com.leedarson.serviceimpl.blec075.callback.a _trackElkHandler) {
        this.a = configBean;
        this.b = _trackElkHandler;
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6478, new Class[0], Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("bleIndicate");
            g.a("onIndicateSuccess mac:" + this.a.mac + ",characterUUID:" + this.a.characterUUID.toString(), new Object[0]);
            com.leedarson.serviceimpl.blec075.callback.a aVar = this.b;
            if (aVar != null) {
                aVar.b("onIndicateSuccess:" + this.a.mac, "indicateBle", "info", "commonIndicate");
            }
            CommonBleReadConfigBean commonBleReadConfigBean = this.a;
            BleC075Service.CommonBleCallback commonBleCallback = commonBleReadConfigBean.commonBleCallback;
            if (commonBleCallback != null) {
                commonBleCallback.onNotifySuccess((String) null, commonBleReadConfigBean.mac);
            }
        }
    }

    public void b(com.clj.fastble.exception.a exception) {
        if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 6479, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("bleIndicate");
            g.a("onIndicateFailure mac:" + this.a.mac + ",characterUUID:" + this.a.characterUUID.toString(), new Object[0]);
            com.leedarson.serviceimpl.blec075.callback.a aVar = this.b;
            if (aVar != null) {
                aVar.b("onIndicateFailure:" + this.a.mac + ",ex:" + exception.toString(), "indicateBle", "info", "commonIndicate");
            }
            BleC075Service.CommonBleCallback commonBleCallback = this.a.commonBleCallback;
            if (commonBleCallback != null) {
                commonBleCallback.onNotifyFail(new Exception(exception.getDescription()), (String) null, this.a.mac, -1);
            }
        }
    }

    public void a(byte[] data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6480, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g("bleIndicate");
            g.a("onCharacteristicChanged:" + this.a.characterUUID.toString() + ",data:" + h.b(data), new Object[0]);
            CommonBleReadConfigBean commonBleReadConfigBean = this.a;
            BleC075Service.CommonBleCallback commonBleCallback = commonBleReadConfigBean.commonBleCallback;
            if (commonBleCallback != null) {
                commonBleCallback.onCharacteristicChanged(data, (String) null, commonBleReadConfigBean.mac);
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
                jsonObject3.put("mac", (Object) this.a.mac);
            } catch (Exception e) {
                e.printStackTrace();
            }
            org.greenrobot.eventbus.c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, "onMessage", jsonObject3.toString()));
        }
    }
}
