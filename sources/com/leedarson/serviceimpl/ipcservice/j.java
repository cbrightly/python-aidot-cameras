package com.leedarson.serviceimpl.ipcservice;

import android.content.Context;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.m;
import com.leedarson.bean.PartialUpdateEventBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.json.JSONObject;

/* compiled from: PartialUpdateOnDeviceOTAStrategy */
public class j {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(JSONObject payloadObj, Context context) {
        if (!PatchProxy.proxy(new Object[]{payloadObj, context}, (Object) null, changeQuickRedirect, true, 8085, new Class[]{JSONObject.class, Context.class}, Void.TYPE).isSupported) {
            try {
                PartialUpdateEventBean partialUpdateEventBean = (PartialUpdateEventBean) m.a(payloadObj.toString(), PartialUpdateEventBean.class);
                PartialUpdateEventBean.ExtensionsBean extensionsBean = partialUpdateEventBean.extensions;
                if (extensionsBean != null) {
                    String method = extensionsBean.method;
                    if ("updateOtaStautsNotif".equals(method) || "updateOtaStatusNotify".equals(method)) {
                        int stage = extensionsBean.stage.intValue();
                        String version = extensionsBean.version;
                        long cacheTimestamp = System.currentTimeMillis();
                        String deviceId = partialUpdateEventBean.id;
                        if (stage != 0) {
                            BaseApplication.b().v(deviceId + "_deviceOTA", stage + "_" + version + "_" + cacheTimestamp);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
