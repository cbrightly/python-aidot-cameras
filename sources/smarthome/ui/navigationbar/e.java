package smarthome.ui.navigationbar;

import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.serviceinterface.listener.OnBridgeRespListener;

/* compiled from: lambda */
public final /* synthetic */ class e implements OnBridgeRespListener {
    public static final /* synthetic */ e a = new e();

    private /* synthetic */ e() {
    }

    public final void onResult(String str, WVJBWebView wVJBWebView) {
        LDSNavigationBar.D(str, wVJBWebView);
    }
}
