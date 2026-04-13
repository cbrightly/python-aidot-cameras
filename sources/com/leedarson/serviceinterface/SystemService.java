package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.base.jsbridge2.WVJBWebView;
import org.json.JSONObject;

public interface SystemService extends c {
    int getStatusBarHeight(Context context);

    void goH5Page(String str, JSONObject jSONObject, long j);

    void handleData(WVJBWebView wVJBWebView, String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);

    void logOutApp(String str);

    void startBackUpLog(Activity activity);
}
