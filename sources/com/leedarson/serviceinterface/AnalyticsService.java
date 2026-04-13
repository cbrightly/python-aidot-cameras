package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import com.alibaba.android.arouter.facade.template.c;

public interface AnalyticsService extends c {
    void execute(String str, WebView webView);

    void handleData(String str, Activity activity, String str2, String str3);

    /* synthetic */ void init(Context context);

    void initUM(String str, boolean z);

    void onPause(Activity activity);

    void onResume(Activity activity);

    void setSessionContinueMillis(long j);
}
