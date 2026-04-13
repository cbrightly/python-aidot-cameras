package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;

public interface SecurityService extends c {
    public static final String ACTION_HIDE_ALARM = "hideAlarm";
    public static final String ACTION_SHOW_ALARM = "showAlarm";

    void handleData(String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);
}
