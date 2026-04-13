package com.leedarson.serviceimpl.system.external;

import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;

/* compiled from: lambda */
public final /* synthetic */ class g implements OnBridgeRespListener {
    public final /* synthetic */ ExternalActivity a;

    public /* synthetic */ g(ExternalActivity externalActivity) {
        this.a = externalActivity;
    }

    public final void onResult(String str, WVJBWebView wVJBWebView) {
        this.a.j0(str, wVJBWebView);
    }
}
