package smarthome.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.event.ScreenStatusReceiveEvent;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import org.greenrobot.eventbus.c;

public class LDSScreenStatusReceiver extends BroadcastReceiver {
    private String a = "android.intent.action.SCREEN_ON";
    private String b = "android.intent.action.SCREEN_OFF";

    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        if (this.a.equals(intent.getAction())) {
            Constans.isScreenOn = true;
            c.c().l(new ScreenStatusReceiveEvent(true));
        } else if (this.b.equals(intent.getAction())) {
            Constans.isScreenOn = false;
            c.c().l(new ScreenStatusReceiveEvent(false));
        }
    }

    public static LDSScreenStatusReceiver a(Context context) {
        LDSScreenStatusReceiver screenStatusReceiver = new LDSScreenStatusReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.SCREEN_ON");
        filter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(screenStatusReceiver, filter);
        return screenStatusReceiver;
    }

    public static void b(Context context, LDSScreenStatusReceiver screenStatusReceiver) {
        if (screenStatusReceiver != null) {
            try {
                context.unregisterReceiver(screenStatusReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
