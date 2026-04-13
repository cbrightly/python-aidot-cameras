package smarthome.ui.navigationbar;

import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;

/* compiled from: lambda */
public final /* synthetic */ class h implements OnBridgeRespListener {
    public static final /* synthetic */ h a = new h();

    private /* synthetic */ h() {
    }

    public final void onResult(String str, WVJBWebView wVJBWebView) {
        LDSNavigationBar.E(str, wVJBWebView);
    }
}
