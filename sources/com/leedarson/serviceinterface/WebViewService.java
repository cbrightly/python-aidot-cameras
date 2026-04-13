package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.base.jsbridge2.WVJBWebView;

public interface WebViewService extends c {
    void handleData(WVJBWebView wVJBWebView, String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);
}
