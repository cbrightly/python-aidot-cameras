package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface WiFiService extends c {
    void getRouterInfo(String str, String str2);

    void handleData(String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);
}
