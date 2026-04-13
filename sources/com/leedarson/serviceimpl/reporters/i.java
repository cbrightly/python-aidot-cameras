package com.leedarson.serviceimpl.reporters;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.gson.Gson;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.greenrobot.eventbus.c;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: MatterConfNetReporter */
public class i extends h {
    public static ChangeQuickRedirect changeQuickRedirect;

    public i(String traceId, String deviceInfo) {
        super(traceId, deviceInfo);
    }

    public void c(MatterConfigStepBeans step) {
        if (!PatchProxy.proxy(new Object[]{step}, this, changeQuickRedirect, false, 8553, new Class[]{MatterConfigStepBeans.class}, Void.TYPE).isSupported) {
            a.i("matterConfig.step=" + step.getStep() + "\n" + new Gson().toJson((Object) step), new Object[0]);
            String module = Constants.SERVICE_Matter;
            String action = "onMessage";
            try {
                JSONObject responseObj = new JSONObject();
                responseObj.put(CacheEntity.KEY, (Object) module + "." + action);
                responseObj.put("traceId", (Object) this.b);
                responseObj.put("protocol", (Object) "matter");
                JSONObject jsonObjectData = new JSONObject();
                jsonObjectData.put("step", (Object) step.getStep());
                jsonObjectData.put("time", step.getDuration());
                jsonObjectData.put("code", step.getCode());
                jsonObjectData.put("desc", (Object) step.getDesc());
                responseObj.put("data", (Object) jsonObjectData);
                c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_Matter, "onMessage", responseObj.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.a.add(step);
        }
    }

    public com.leedarson.log.elk.a a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8554, new Class[0], com.leedarson.log.elk.a.class);
        return proxy.isSupported ? (com.leedarson.log.elk.a) proxy.result : com.leedarson.log.elk.a.y(this).t("LdsMatter").e("ConfigNetWork").b(8).o("info");
    }
}
