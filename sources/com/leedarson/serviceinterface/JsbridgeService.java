package com.leedarson.serviceinterface;

import android.app.Activity;
import android.content.Context;
import com.alibaba.android.arouter.facade.template.c;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;
import java.util.List;

public interface JsbridgeService extends c {
    void callbackToJs(String str, String str2);

    void clearEventSet();

    List<WVJBWebView> getWebViewByEvents(String str);

    boolean hasCallbackKey(String str);

    /* synthetic */ void init(Context context);

    void nativeCallJs(WVJBWebView wVJBWebView, String str, String str2, String str3);

    void nativeCallJs(WVJBWebView wVJBWebView, String str, String str2, String str3, OnBridgeRespListener onBridgeRespListener);

    void nativeCallJsArray(WVJBWebView wVJBWebView, String str, String str2, String str3);

    void nativeTestHandlerData(Activity activity, String str, String str2, String str3, String str4);

    void registerJsCallNative(Activity activity, WVJBWebView wVJBWebView, String str);
}
