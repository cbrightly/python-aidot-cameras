package com.leedarson.serviceimpl.system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationManagerCompat;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import net.sqlcipher.database.SQLiteDatabase;

/* compiled from: NotificationUtils */
public class j {
    public static ChangeQuickRedirect changeQuickRedirect;

    @SuppressLint({"NewApi"})
    public static boolean b(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 8818, new Class[]{Context.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    public static void a(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, (Object) null, changeQuickRedirect, true, 8819, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            Intent intent = new Intent();
            intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            int i = Build.VERSION.SDK_INT;
            if (i >= 26) {
                intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                intent.putExtra("android.provider.extra.APP_PACKAGE", activity.getPackageName());
                intent.putExtra("android.provider.extra.CHANNEL_ID", activity.getApplicationInfo().uid);
            } else if (i >= 23) {
                intent.putExtra("app_package", activity.getPackageName());
                intent.putExtra("app_uid", activity.getApplicationInfo().uid);
            }
            activity.startActivity(intent);
        }
    }
}
