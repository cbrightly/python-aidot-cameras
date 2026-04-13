package com.leedarson.newui.cloud_play_back.repos;

import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.newui.cloud_play_back.repos.z;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;
import io.reactivex.f;

/* compiled from: lambda */
public final /* synthetic */ class e implements OnBridgeRespListener {
    public final /* synthetic */ z.a a;
    public final /* synthetic */ f b;

    public /* synthetic */ e(z.a aVar, f fVar) {
        this.a = aVar;
        this.b = fVar;
    }

    public final void onResult(String str, WVJBWebView wVJBWebView) {
        this.a.b(this.b, str, wVJBWebView);
    }
}
