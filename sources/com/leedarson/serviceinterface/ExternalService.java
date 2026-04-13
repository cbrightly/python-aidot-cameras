package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.base.jsbridge2.WVJBWebView;

public interface ExternalService extends c {
    void handleData(WVJBWebView wVJBWebView, Activity activity, String str, String str2, String str3);

    /* synthetic */ void init(Context context);

    void openExternalWebview(Activity activity, String str);
}
