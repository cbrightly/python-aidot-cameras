package smarthome.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.w;
import com.leedarson.base.utils.x;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.event.NetWorkStatusEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import org.greenrobot.eventbus.c;
import smarthome.reporter.HttpServiceReporter;
import timber.log.a;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private String a = "";
    private String b = "netWorkChange";
    private String c = "";
    private int d = -1;
    String e;

    public static NetworkChangeReceiver c(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("android.net.conn.LDS_CONNECTIVITY_CHANGE");
        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
        context.registerReceiver(networkChangeReceiver, intentFilter);
        return networkChangeReceiver;
    }

    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        String action = intent.getAction();
        a.b g = a.g(this.b);
        g.m(" onNetWorkChange  action=" + action, new Object[0]);
        if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            "android.net.conn.LDS_CONNECTIVITY_CHANGE".equals(action);
        }
        a.b g2 = a.g(this.b);
        g2.m(" onNetWorkChange  postNetworkStatus=" + action, new Object[0]);
        b(context);
    }

    public void b(Context context) {
        if (Constans.isScreenOn) {
            if (TextUtils.isEmpty(this.e)) {
                this.e = SharePreferenceUtils.getPrefString(context, "repositoryName", "");
            }
            if ((!"leedarson-Leedarson".equals(this.e) && !"leedarson-NewLeedarson".equals(this.e)) || (!BaseApplication.d && BaseApplication.b().p3)) {
                boolean hasChange = false;
                int netState = w.w(context);
                int i = this.d;
                if (i == -1) {
                    this.d = netState;
                    hasChange = true;
                } else if (i != netState) {
                    hasChange = true;
                    this.d = netState;
                    this.a = this.c;
                }
                String networkName = a(netState, context);
                if (!this.c.equals(networkName)) {
                    this.c = networkName;
                    hasChange = true;
                }
                if (hasChange) {
                    NetWorkStatusEvent event = new NetWorkStatusEvent(netState, this.a, this.c);
                    if (event.checkNetWorkEnable() && !com.leedarson.base.utils.networkutil.a.a(BaseApplication.b())) {
                        HttpServiceReporter.i().h.onNext(false);
                    }
                    a.g(this.b).m(" onNetWorkChange  postNetWorkEvent", new Object[0]);
                    c.c().l(event);
                }
            }
        }
    }

    private String a(int state, Context context) {
        a.b g = a.g(this.b);
        g.m(" getNetworkName  state=" + state, new Object[0]);
        if (state == 0) {
            return "no network";
        }
        if (state == 1) {
            return "mobile network";
        }
        if (state == 2) {
            return x.d(context);
        }
        return "unknown network";
    }
}
