package com.leedarson.base.utils;

import android.app.Activity;
import com.alibaba.android.arouter.launcher.a;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.beans.LDSDomainBean;
import com.leedarson.serviceinterface.BusinessService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: CheckNetStatueInfoUtils */
public class g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a() {
        BusinessService bleBusinessService;
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 465, new Class[0], Void.TYPE).isSupported && (bleBusinessService = (BusinessService) a.c().g(BusinessService.class)) != null) {
            JSONObject data = new JSONObject();
            try {
                Gson gson = new Gson();
                data.put(CacheEntity.KEY, (Object) "Business.startNetworkDiagnosis");
                JSONArray domains = new JSONArray();
                domains.put((Object) new JSONObject(gson.toJson((Object) new LDSDomainBean("www.google.com"))));
                domains.put((Object) new JSONObject(gson.toJson((Object) new LDSDomainBean("www.amazon.com"))));
                domains.put((Object) new JSONObject(gson.toJson((Object) new LDSDomainBean(SharePreferenceUtils.getPrefString(BaseApplication.b(), "httpServer", "")))));
                data.put("domains", (Object) domains);
                bleBusinessService.handleData("", (Activity) null, "startNetworkDiagnosis", data.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
