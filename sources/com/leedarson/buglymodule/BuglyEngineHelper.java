package com.leedarson.buglymodule;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.log.d;
import com.leedarson.serviceinterface.LdsCrashEngineReportService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.tencent.bugly.crashreport.CrashReport;

public class BuglyEngineHelper implements LdsCrashEngineReportService {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static void a(BaseApplication application, CrashReport.CrashHandleCallback handleCallback, String buglyVersion) {
        String appId;
        Class[] clsArr = {BaseApplication.class, CrashReport.CrashHandleCallback.class, String.class};
        if (!PatchProxy.proxy(new Object[]{application, handleCallback, buglyVersion}, (Object) null, changeQuickRedirect, true, 1126, clsArr, Void.TYPE).isSupported) {
            CrashReport.putUserData(application, "username", SharePreferenceUtils.getPrefString(application, "username", ""));
            CrashReport.putUserData(application, "WEB_VERSION", SharePreferenceUtils.getPrefString(application, "WEB_VERSION", ""));
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(application);
            strategy.setAppVersion(buglyVersion);
            if (handleCallback != null) {
                strategy.setCrashHandleCallback(handleCallback);
            }
            if (!BaseApplication.b().o()) {
                appId = "68c43afc8c";
                strategy.setEnableANRCrashMonitor(true);
            } else {
                appId = "b0877312cc";
            }
            CrashReport.setIsDevelopmentDevice(application, !BaseApplication.b().o());
            d.b().e(application, !BaseApplication.b().o());
            d.b().g(a.a);
            CrashReport.setUserId(SharePreferenceUtils.getPrefString(application, "username", ""));
            CrashReport.initCrashReport(application, appId, !BaseApplication.b().o(), strategy);
        }
    }

    static /* synthetic */ void h(Thread thread, Throwable ex) {
        Class[] clsArr = {Thread.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{thread, ex}, (Object) null, changeQuickRedirect, true, 1130, clsArr, Void.TYPE).isSupported) {
            CrashReport.postCatchedException(ex, thread);
        }
    }

    public void initSdk(BaseApplication application, String extroData) {
        Class[] clsArr = {BaseApplication.class, String.class};
        if (!PatchProxy.proxy(new Object[]{application, extroData}, this, changeQuickRedirect, false, 1127, clsArr, Void.TYPE).isSupported) {
            a(application, new b(application), extroData);
        }
    }

    public void updateUserInfo() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1128, new Class[0], Void.TYPE).isSupported) {
            CrashReport.putUserData(BaseApplication.b(), "username", SharePreferenceUtils.getPrefString(BaseApplication.b(), "username", ""));
            CrashReport.putUserData(BaseApplication.b(), "WEB_VERSION", SharePreferenceUtils.getPrefString(BaseApplication.b(), "WEB_VERSION", ""));
            CrashReport.setUserId(SharePreferenceUtils.getPrefString(BaseApplication.b(), "username", ""));
        }
    }

    public void injectWebView(WebView webView) {
        if (!PatchProxy.proxy(new Object[]{webView}, this, changeQuickRedirect, false, 1129, new Class[]{WebView.class}, Void.TYPE).isSupported) {
            try {
                CrashReport.setJavascriptMonitor(webView, true);
            } catch (Exception e) {
                Log.e("BuglyEngineHelper", "注入webView发生异常-Bugly" + e.toString());
            }
        }
    }

    public void init(Context context) {
    }
}
