package me.leolin.shortcutbadger.impl;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import java.util.Collections;
import java.util.List;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.a;

/* compiled from: OPPOHomeBader */
public class d implements a {
    private int a = -1;

    public void b(Context context, ComponentName componentName, int badgeCount) {
        if (this.a != badgeCount) {
            this.a = badgeCount;
            if (Build.VERSION.SDK_INT >= 11) {
                d(context, badgeCount);
            } else {
                c(context, componentName, badgeCount);
            }
        }
    }

    public List<String> a() {
        return Collections.singletonList("com.oppo.launcher");
    }

    private void c(Context context, ComponentName componentName, int badgeCount) {
        if (badgeCount == 0) {
            badgeCount = -1;
        }
        Intent intent = new Intent("com.oppo.unsettledevent");
        intent.putExtra("pakeageName", componentName.getPackageName());
        intent.putExtra("number", badgeCount);
        intent.putExtra("upgradeNumber", badgeCount);
        me.leolin.shortcutbadger.util.a.c(context, intent);
    }

    @TargetApi(11)
    private void d(Context context, int badgeCount) {
        try {
            Bundle extras = new Bundle();
            extras.putInt("app_badge_count", badgeCount);
            context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", (String) null, extras);
        } catch (Throwable th) {
            throw new ShortcutBadgeException("Unable to execute Badge By Content Provider");
        }
    }
}
