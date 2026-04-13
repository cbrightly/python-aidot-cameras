package com.blankj.utilcode.util;

import android.content.Intent;
import android.net.Uri;
import net.sqlcipher.database.SQLiteDatabase;

/* compiled from: IntentUtils */
public final class m {
    public static boolean d(Intent intent) {
        return f0.a().getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    public static Intent c(String pkgName) {
        String launcherActivity = h0.t(pkgName);
        if (h0.E(launcherActivity)) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setClassName(pkgName, launcherActivity);
        return intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public static Intent b(String pkgName, boolean isNewTask) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + pkgName));
        return a(intent, isNewTask);
    }

    private static Intent a(Intent intent, boolean isNewTask) {
        return isNewTask ? intent.addFlags(SQLiteDatabase.CREATE_IF_NECESSARY) : intent;
    }
}
