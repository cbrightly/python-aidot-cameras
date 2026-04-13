package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.alibaba.android.arouter.facade.template.c;

public interface ThirdPartyService extends c {
    void handleData(String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);

    void onActivityResultData(int i, int i2, Intent intent);

    void onWxLoginError(String str, int i);

    void onWxLoginSuccess(String str);

    void onekeyFail(int i, String str);

    void onekeySuccess(String str);

    void qqLoginResult(int i, Object obj);

    void regWx();
}
