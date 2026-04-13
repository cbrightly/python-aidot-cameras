package smarthome.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.leedarson.base.utils.w;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import org.greenrobot.eventbus.c;
import smarthome.event.a;

public class GpsStatusReceiver extends BroadcastReceiver {
    private boolean a = false;

    public static GpsStatusReceiver a(Context context) {
        IntentFilter filter = new IntentFilter();
        GpsStatusReceiver gpsStatusReceiver = new GpsStatusReceiver();
        filter.addAction("android.location.PROVIDERS_CHANGED");
        context.registerReceiver(gpsStatusReceiver, filter);
        return gpsStatusReceiver;
    }

    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action) && action.equals("android.location.PROVIDERS_CHANGED")) {
            boolean P = w.P(context);
            this.a = P;
            if (P) {
                c.c().l(new a(1));
            } else {
                c.c().l(new a(0));
            }
        }
    }
}
