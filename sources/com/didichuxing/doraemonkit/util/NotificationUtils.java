package com.didichuxing.doraemonkit.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import com.didichuxing.doraemonkit.R;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.util.ArrayList;
import java.util.List;

public class NotificationUtils {
    private static final String ID_HIGH_CHANNEL = "channel_1_oncar";
    private static final String ID_LOW_CHANNEL = "channel_low_onecar";
    public static final int ID_SHOW_BLOCK_NOTIFICATION = 1001;
    private static final String NAME_HIGH_CHANNEL = "channel_1_name_oncar";
    private static final String NAME_LOW_CHANNEL = "channel_name_low_onecar";
    private static NotificationManager sNotificationManager;

    public static void setMessageNotification(Context context, int notifyId, int smallIconId, CharSequence title, CharSequence summary) {
        setMessageNotification(context, notifyId, smallIconId, title, summary, (CharSequence) null);
    }

    public static void setMessageNotification(Context context, int notifyId, int smallIconId, CharSequence title, CharSequence summary, CharSequence ticker) {
        setMessageNotification(context, notifyId, smallIconId, title, summary, ticker, (PendingIntent) null);
    }

    public static void setMessageNotification(Context context, int notifyId, int smallIconId, CharSequence title, CharSequence summary, CharSequence ticker, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new NotificationCompat.Builder(context, ID_HIGH_CHANNEL);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder.setSmallIcon(smallIconId).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.dk_doraemon)).setContentTitle(title).setContentText(summary).setAutoCancel(true).setProgress(0, 0, false);
        if (!TextUtils.isEmpty(ticker)) {
            builder.setTicker(ticker);
        }
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        } else {
            Intent intent = new Intent();
            PushAutoTrackHelper.hookIntentGetBroadcast(context, 0, intent, 134217728);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 134217728);
            PushAutoTrackHelper.hookPendingIntentGetBroadcast(broadcast, context, 0, intent, 134217728);
            builder.setContentIntent(broadcast);
        }
        NotificationManager manager = createNotificationManager(context);
        Notification build = builder.build();
        manager.notify(notifyId, build);
        PushAutoTrackHelper.onNotify(manager, notifyId, build);
    }

    public static void setInfoNotification(Context context, int notifyId, CharSequence title, CharSequence summary, CharSequence ticker, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new NotificationCompat.Builder(context, ID_HIGH_CHANNEL);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        builder.setSmallIcon(R.mipmap.dk_doraemon).setContentTitle(title).setContentText(summary).setAutoCancel(true).setProgress(0, 0, false);
        if (!TextUtils.isEmpty(ticker)) {
            builder.setTicker(ticker);
        }
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        } else {
            Intent intent = new Intent();
            PushAutoTrackHelper.hookIntentGetBroadcast(context, 0, intent, 134217728);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 134217728);
            PushAutoTrackHelper.hookPendingIntentGetBroadcast(broadcast, context, 0, intent, 134217728);
            builder.setContentIntent(broadcast);
        }
        NotificationManager manager = createNotificationManager(context);
        Notification build = builder.build();
        manager.notify(notifyId, build);
        PushAutoTrackHelper.onNotify(manager, notifyId, build);
    }

    public static void setProgressNotification(Context context, int notifyId, CharSequence title, int progress) {
        setProgressNotification(context, notifyId, title, (CharSequence) null, progress);
    }

    public static void setProgressNotification(Context context, int notifyId, CharSequence title, CharSequence ticker, int progress) {
        setProgressNotification(context, notifyId, title, ticker, progress, (PendingIntent) null);
    }

    public static void setProgressNotification(Context context, int notifyId, CharSequence title, CharSequence ticker, int progress, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new NotificationCompat.Builder(context, ID_HIGH_CHANNEL);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        boolean z = true;
        NotificationCompat.Builder ongoing = builder.setSmallIcon(17301633).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.dk_doraemon)).setContentTitle(title).setProgress(100, progress, progress == 0).setOngoing(progress < 100);
        if (progress != 100) {
            z = false;
        }
        ongoing.setAutoCancel(z);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        } else {
            Intent intent = new Intent();
            PushAutoTrackHelper.hookIntentGetBroadcast(context, 0, intent, 134217728);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, 134217728);
            PushAutoTrackHelper.hookPendingIntentGetBroadcast(broadcast, context, 0, intent, 134217728);
            builder.setContentIntent(broadcast);
        }
        if (!TextUtils.isEmpty(ticker)) {
            builder.setTicker(ticker);
        }
        NotificationManager manager = createNotificationManager(context);
        Notification build = builder.build();
        manager.notify(notifyId, build);
        PushAutoTrackHelper.onNotify(manager, notifyId, build);
    }

    public static void cancelNotification(Context context, int notifyId) {
        createNotificationManager(context).cancel(notifyId);
    }

    public static void cancelNotification(Context context) {
        createNotificationManager(context).cancelAll();
    }

    private static NotificationManager createNotificationManager(Context context) {
        NotificationManager notificationManager = sNotificationManager;
        if (notificationManager != null) {
            return notificationManager;
        }
        sNotificationManager = (NotificationManager) context.getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationHightChannel = new NotificationChannel(ID_HIGH_CHANNEL, NAME_HIGH_CHANNEL, 4);
            NotificationChannel notificationLowChannel = new NotificationChannel(ID_LOW_CHANNEL, NAME_LOW_CHANNEL, 2);
            List<NotificationChannel> channelList = new ArrayList<>();
            channelList.add(notificationLowChannel);
            channelList.add(notificationHightChannel);
            sNotificationManager.createNotificationChannels(channelList);
        }
        return sNotificationManager;
    }
}
