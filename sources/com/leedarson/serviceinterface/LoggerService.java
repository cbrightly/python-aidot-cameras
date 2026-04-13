package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import org.json.JSONObject;

public interface LoggerService extends c {
    JSONObject appendCommonProperties(JSONObject jSONObject);

    void debugMode();

    void handleData(String str, Activity activity, String str2, String str3, String str4);

    void handleDebugEvent(Activity activity, String str);

    /* synthetic */ void init(Context context);

    void loginSensorsData(String str);

    void release();

    void reportELK(Object obj, String str, String str2, String str3);

    void reportELK(Object obj, String str, String str2, String str3, String str4);

    void reportPermissionSensorsData(String str, boolean z);

    void reportSensorsData(String str, JSONObject jSONObject);

    void uploadLogger1();
}
