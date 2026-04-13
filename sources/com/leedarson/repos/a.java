package com.leedarson.repos;

import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;
import io.reactivex.m;

/* compiled from: lambda */
public final /* synthetic */ class a implements OnBridgeRespListener {
    public final /* synthetic */ m a;

    public /* synthetic */ a(m mVar) {
        this.a = mVar;
    }

    public final void onResult(String str, WVJBWebView wVJBWebView) {
        c.c(this.a, str, wVJBWebView);
    }
}
