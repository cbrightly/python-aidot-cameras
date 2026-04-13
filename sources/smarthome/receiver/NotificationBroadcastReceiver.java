package smarthome.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.leedarson.base.utils.c;
import com.leedarson.serviceinterface.event.NotifyMessageEvent;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import timber.log.a;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
        if ("Notification_action".equals(intent.getAction())) {
            Activity currentActivity = c.h().c();
            a.e("NotificationBroadcastReceiver onReceive: " + currentActivity, new Object[0]);
            if (currentActivity == null) {
                Bundle bundle = new Bundle();
                bundle.putString("push_data", intent.getStringExtra("push_data"));
                bundle.putString("notification_houseId", intent.getStringExtra("notification_houseId"));
                a.e("NotificationBroadcastReceiver   pushData=" + intent.getStringExtra("push_data") + "  notification_houseId=" + intent.getStringExtra("notification_houseId"), new Object[0]);
                com.alibaba.android.arouter.launcher.a.c().a("/app/main/").L(bundle).C();
                return;
            }
            a.e("NotificationBroadcastReceiver   pushData=" + intent.getStringExtra("push_data"), new Object[0]);
            if (!c.h().c().toString().contains("com.leedarson.newui.IpcMainActivity") || !c.h().c().toString().contains("com.leedarson.smarthome.ui.MainActivity")) {
                com.alibaba.android.arouter.launcher.a.c().a("/app/main/").C();
            }
            org.greenrobot.eventbus.c.c().l(new NotifyMessageEvent(intent.getStringExtra("push_data"), intent.getStringExtra("notification_houseId")));
        }
    }
}
