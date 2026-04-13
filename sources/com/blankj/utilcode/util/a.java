package com.blankj.utilcode.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import java.util.List;

/* compiled from: ActivityUtils */
public final class a {
    public static String a(@NonNull String pkg) {
        if (pkg == null) {
            throw new NullPointerException("Argument 'pkg' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        } else if (h0.E(pkg)) {
            return "";
        } else {
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(pkg);
            List<ResolveInfo> info = f0.a().getPackageManager().queryIntentActivities(intent, 0);
            if (info == null || info.size() == 0) {
                return "";
            }
            return info.get(0).activityInfo.name;
        }
    }

    public static Activity b() {
        return h0.v();
    }

    public static boolean c(Activity activity) {
        return activity != null && !activity.isFinishing() && (Build.VERSION.SDK_INT < 17 || !activity.isDestroyed());
    }
}
