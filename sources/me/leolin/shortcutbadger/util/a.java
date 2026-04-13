package me.leolin.shortcutbadger.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import java.util.Collections;
import java.util.List;
import me.leolin.shortcutbadger.ShortcutBadgeException;

/* compiled from: BroadcastHelper */
public class a {
    public static List<ResolveInfo> a(Context context, Intent intent) {
        List<ResolveInfo> receivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        return receivers != null ? receivers : Collections.emptyList();
    }

    public static void c(Context context, Intent intent) {
        List<ResolveInfo> resolveInfos = a(context, intent);
        if (resolveInfos.size() != 0) {
            for (ResolveInfo info : resolveInfos) {
                Intent actualIntent = new Intent(intent);
                if (info != null) {
                    actualIntent.setPackage(info.resolvePackageName);
                    context.sendBroadcast(actualIntent);
                }
            }
            return;
        }
        throw new ShortcutBadgeException("unable to resolve intent: " + intent.toString());
    }

    public static void b(Context context, Intent intent) {
        boolean oreoIntentSuccess = false;
        if (Build.VERSION.SDK_INT >= 26) {
            Intent oreoIntent = new Intent(intent);
            oreoIntent.setAction("me.leolin.shortcutbadger.BADGE_COUNT_UPDATE");
            try {
                c(context, oreoIntent);
                oreoIntentSuccess = true;
            } catch (ShortcutBadgeException e) {
                oreoIntentSuccess = false;
            }
        }
        if (!oreoIntentSuccess) {
            c(context, intent);
        }
    }
}
