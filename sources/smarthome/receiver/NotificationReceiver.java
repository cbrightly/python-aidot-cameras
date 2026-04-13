package smarthome.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.leedarson.serviceinterface.Constans;
import com.leedarson.serviceinterface.event.NotifyMessageEvent;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import org.greenrobot.eventbus.c;

public class NotificationReceiver extends BroadcastReceiver {
    public static NotificationReceiver a(Context context) {
        IntentFilter intentFilterno = new IntentFilter(Constans.NOTIFI_ACTION_NAME);
        NotificationReceiver notificationReceiver = new NotificationReceiver();
        context.registerReceiver(notificationReceiver, intentFilterno);
        return notificationReceiver;
    }

    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        String action = intent.getAction();
        String data = intent.getStringExtra("data");
        if (!TextUtils.isEmpty(action) && action.equals(Constans.NOTIFI_ACTION_NAME)) {
            c.c().l(new NotifyMessageEvent(data, ""));
        }
    }
}
