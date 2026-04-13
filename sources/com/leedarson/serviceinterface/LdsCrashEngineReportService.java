package com.leedarson.serviceinterface;

import android.content.Context;
import android.webkit.WebView;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.base.application.BaseApplication;

public interface LdsCrashEngineReportService extends c {
    /* synthetic */ void init(Context context);

    void initSdk(BaseApplication baseApplication, String str);

    void injectWebView(WebView webView);

    void updateUserInfo();
}
