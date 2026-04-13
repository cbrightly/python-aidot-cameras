package me.leolin.shortcutbadger.impl;

import android.content.AsyncQueryHandler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import java.util.Arrays;
import java.util.List;

public class SonyHomeBadger implements me.leolin.shortcutbadger.a {
    private final Uri a = Uri.parse("content://com.sonymobile.home.resourceprovider/badge");
    private AsyncQueryHandler b;

    public void b(Context context, ComponentName componentName, int badgeCount) {
        if (h(context)) {
            e(context, componentName, badgeCount);
        } else {
            d(context, componentName, badgeCount);
        }
    }

    public List<String> a() {
        return Arrays.asList(new String[]{"com.sonyericsson.home", "com.sonymobile.home"});
    }

    private static void d(Context context, ComponentName componentName, int badgeCount) {
        Intent intent = new Intent("com.sonyericsson.home.action.UPDATE_BADGE");
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", componentName.getPackageName());
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", componentName.getClassName());
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(badgeCount));
        intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", badgeCount > 0);
        context.sendBroadcast(intent);
    }

    private void e(Context context, ComponentName componentName, int badgeCount) {
        if (badgeCount >= 0) {
            ContentValues contentValues = c(badgeCount, componentName);
            if (Looper.myLooper() == Looper.getMainLooper()) {
                if (this.b == null) {
                    this.b = new a(context.getApplicationContext().getContentResolver());
                }
                f(contentValues);
                return;
            }
            g(context, contentValues);
        }
    }

    public class a extends AsyncQueryHandler {
        a(ContentResolver x0) {
            super(x0);
        }
    }

    private void f(ContentValues contentValues) {
        this.b.startInsert(0, (Object) null, this.a, contentValues);
    }

    private void g(Context context, ContentValues contentValues) {
        context.getApplicationContext().getContentResolver().insert(this.a, contentValues);
    }

    private ContentValues c(int badgeCount, ComponentName componentName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("badge_count", Integer.valueOf(badgeCount));
        contentValues.put("package_name", componentName.getPackageName());
        contentValues.put("activity_name", componentName.getClassName());
        return contentValues;
    }

    private static boolean h(Context context) {
        if (context.getPackageManager().resolveContentProvider("com.sonymobile.home.resourceprovider", 0) != null) {
            return true;
        }
        return false;
    }
}
