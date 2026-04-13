package com.leedarson;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;
import com.leedarson.serviceimpl.m;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;

public class RecordService extends Service {
    public static ChangeQuickRedirect changeQuickRedirect;
    String c = "RecordService";

    @Nullable
    public IBinder onBind(Intent intent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 1, new Class[]{Intent.class}, IBinder.class);
        if (proxy.isSupported) {
            return (IBinder) proxy.result;
        }
        Log.d(this.c, "onBind()");
        b();
        return new a();
    }

    public boolean onUnbind(Intent intent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 2, new Class[]{Intent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Log.d(this.c, "onUnbind()");
        return super.onUnbind(intent);
    }

    public void onCreate() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3, new Class[0], Void.TYPE).isSupported) {
            super.onCreate();
            Log.d(this.c, "onCreate");
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        PushAutoTrackHelper.onServiceStartCommand(this, intent, flags, startId);
        Object[] objArr = {intent, new Integer(flags), new Integer(startId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4, new Class[]{Intent.class, cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        Log.d(this.c, "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    private void b() {
        PendingIntent pendingIntent;
        Notification notification;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5, new Class[0], Void.TYPE).isSupported) {
            Log.d(this.c, "startForeground...");
            stopForeground(true);
            Intent paddint = new Intent("CZB");
            int i = Build.VERSION.SDK_INT;
            if (i >= 31) {
                PushAutoTrackHelper.hookIntentGetBroadcast(this, 8, paddint, 67108864);
                PendingIntent broadcast = PendingIntent.getBroadcast(this, 8, paddint, 67108864);
                PushAutoTrackHelper.hookPendingIntentGetBroadcast(broadcast, this, 8, paddint, 67108864);
                pendingIntent = broadcast;
            } else {
                PushAutoTrackHelper.hookIntentGetBroadcast(this, 8, paddint, 1073741824);
                PendingIntent broadcast2 = PendingIntent.getBroadcast(this, 8, paddint, 1073741824);
                PushAutoTrackHelper.hookPendingIntentGetBroadcast(broadcast2, this, 8, paddint, 1073741824);
                pendingIntent = broadcast2;
            }
            if (i >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ONE_ID", "CHANNEL_ONE_ID", 4);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
                notificationChannel.setShowBadge(true);
                notificationChannel.setLockscreenVisibility(1);
                NotificationManager manager = (NotificationManager) getSystemService("notification");
                if (manager != null) {
                    manager.createNotificationChannel(notificationChannel);
                }
                notification = new Notification.Builder(this, "CHANNEL_ONE_ID").setChannelId("CHANNEL_ONE_ID").setTicker("Nature").setContentTitle("这是一个测试标题").setContentIntent(pendingIntent).setContentText("正在采集麦克风").build();
                notification.flags = 32 | notification.flags;
            } else {
                notification = new Notification.Builder(this).setContentIntent(pendingIntent).setContentTitle("8.0以下").setContentText("8.0-text").build();
                notification.flags = 32;
            }
            startForeground(1, notification);
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6, new Class[0], Void.TYPE).isSupported) {
            stopForeground(true);
            super.onDestroy();
            Log.d(this.c, "onDestroy");
        }
    }

    public class a extends Binder {
        public static ChangeQuickRedirect changeQuickRedirect;

        public a() {
        }

        public RecordService a() {
            return RecordService.this;
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7, new Class[0], Void.TYPE).isSupported) {
            m.o().A();
        }
    }

    public void c(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 8, new Class[]{String.class}, Void.TYPE).isSupported) {
            m.o().B(ref);
        }
    }
}
