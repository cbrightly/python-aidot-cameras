package com.leedarson.serviceimpl.system.external;

import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;

/* compiled from: lambda */
public final /* synthetic */ class c implements OnBridgeRespListener {
    public final /* synthetic */ ExternalActivity a;

    public /* synthetic */ c(ExternalActivity externalActivity) {
        this.a = externalActivity;
    }

    public final void onResult(String str, WVJBWebView wVJBWebView) {
        this.a.f0(str, wVJBWebView);
    }
}
