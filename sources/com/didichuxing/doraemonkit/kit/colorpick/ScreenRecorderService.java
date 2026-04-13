package com.didichuxing.doraemonkit.kit.colorpick;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;

public class ScreenRecorderService extends Service {
    @RequiresApi(api = 21)
    public int onStartCommand(Intent intent, int flags, int startId) {
        PushAutoTrackHelper.onServiceStartCommand(this, intent, flags, startId);
        try {
            createNotificationChannel();
            ColorPickManager.getInstance().setMediaProjection(((MediaProjectionManager) getSystemService("media_projection")).getMediaProjection(-1, (Intent) intent.getParcelableExtra("data")));
            if (ColorPickManager.getInstance().getColorPickerDokitView() != null) {
                ColorPickManager.getInstance().getColorPickerDokitView().onScreenServiceReady();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        Intent nfIntent = new Intent(this, UniversalActivity.class);
        PushAutoTrackHelper.hookIntentGetActivity(this, 0, nfIntent, 0);
        PendingIntent activity = PendingIntent.getActivity(this, 0, nfIntent, 0);
        PushAutoTrackHelper.hookPendingIntentGetActivity(activity, this, 0, nfIntent, 0);
        Notification.Builder contentIntent = builder.setContentIntent(activity);
        Resources resources = getResources();
        int i = R.mipmap.dk_doraemon;
        contentIntent.setLargeIcon(BitmapFactory.decodeResource(resources, i)).setSmallIcon(i).setContentText("Dokit屏幕取色器前台服务").setWhen(System.currentTimeMillis());
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 26) {
            builder.setChannelId("notification_id");
        }
        if (i2 >= 26) {
            ((NotificationManager) getSystemService("notification")).createNotificationChannel(new NotificationChannel("notification_id", "notification_name", 2));
        }
        Notification notification = builder.build();
        notification.defaults = 1;
        startForeground(110, notification);
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }
}
