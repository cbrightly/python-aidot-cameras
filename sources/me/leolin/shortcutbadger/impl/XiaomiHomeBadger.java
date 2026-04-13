package me.leolin.shortcutbadger.impl;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.a;

@Deprecated
public class XiaomiHomeBadger implements a {
    private ResolveInfo a;

    public void b(Context context, ComponentName componentName, int badgeCount) {
        Object obj;
        Object obj2 = "";
        try {
            Object miuiNotification = Class.forName("android.app.MiuiNotification").newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            field.setAccessible(true);
            if (badgeCount == 0) {
                obj = obj2;
            } else {
                try {
                    obj = Integer.valueOf(badgeCount);
                } catch (Exception e) {
                    field.set(miuiNotification, Integer.valueOf(badgeCount));
                }
            }
            field.set(miuiNotification, String.valueOf(obj));
        } catch (Exception e2) {
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name", componentName.getPackageName() + "/" + componentName.getClassName());
            if (badgeCount != 0) {
                obj2 = Integer.valueOf(badgeCount);
            }
            localIntent.putExtra("android.intent.extra.update_application_message_text", String.valueOf(obj2));
            try {
                me.leolin.shortcutbadger.util.a.c(context, localIntent);
            } catch (ShortcutBadgeException e3) {
            }
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            c(context, badgeCount);
        }
    }

    @TargetApi(16)
    private void c(Context context, int badgeCount) {
        if (this.a == null) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            this.a = context.getPackageManager().resolveActivity(intent, 65536);
        }
        if (this.a != null) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService("notification");
            Notification notification = new Notification.Builder(context).setContentTitle("").setContentText("").setSmallIcon(this.a.getIconResource()).build();
            try {
                Object extraNotification = notification.getClass().getDeclaredField("extraNotification").get(notification);
                extraNotification.getClass().getDeclaredMethod("setMessageCount", new Class[]{Integer.TYPE}).invoke(extraNotification, new Object[]{Integer.valueOf(badgeCount)});
                mNotificationManager.notify(0, notification);
                PushAutoTrackHelper.onNotify(mNotificationManager, 0, notification);
            } catch (Exception e) {
                throw new ShortcutBadgeException("not able to set badge", e);
            }
        }
    }

    public List<String> a() {
        return Arrays.asList(new String[]{"com.miui.miuilite", "com.miui.home", "com.miui.miuihome", "com.miui.miuihome2", "com.miui.mihome", "com.miui.mihome2", "com.i.miui.launcher"});
    }
}
