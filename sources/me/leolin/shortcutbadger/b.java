package me.leolin.shortcutbadger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import me.leolin.shortcutbadger.impl.AdwHomeBadger;
import me.leolin.shortcutbadger.impl.ApexHomeBadger;
import me.leolin.shortcutbadger.impl.DefaultBadger;
import me.leolin.shortcutbadger.impl.NewHtcHomeBadger;
import me.leolin.shortcutbadger.impl.NovaHomeBadger;
import me.leolin.shortcutbadger.impl.SonyHomeBadger;
import me.leolin.shortcutbadger.impl.a;
import me.leolin.shortcutbadger.impl.c;
import me.leolin.shortcutbadger.impl.d;
import me.leolin.shortcutbadger.impl.e;
import me.leolin.shortcutbadger.impl.f;
import me.leolin.shortcutbadger.impl.g;
import me.leolin.shortcutbadger.impl.h;

/* compiled from: ShortcutBadger */
public final class b {
    private static final List<Class<? extends a>> a;
    private static final Object b = new Object();
    private static a c;
    private static ComponentName d;

    static {
        LinkedList linkedList = new LinkedList();
        a = linkedList;
        linkedList.add(AdwHomeBadger.class);
        linkedList.add(ApexHomeBadger.class);
        linkedList.add(DefaultBadger.class);
        linkedList.add(NewHtcHomeBadger.class);
        linkedList.add(NovaHomeBadger.class);
        linkedList.add(SonyHomeBadger.class);
        linkedList.add(a.class);
        linkedList.add(c.class);
        linkedList.add(d.class);
        linkedList.add(e.class);
        linkedList.add(h.class);
        linkedList.add(f.class);
        linkedList.add(g.class);
        linkedList.add(me.leolin.shortcutbadger.impl.b.class);
    }

    public static boolean a(Context context, int badgeCount) {
        try {
            b(context, badgeCount);
            return true;
        } catch (ShortcutBadgeException e) {
            if (!Log.isLoggable("ShortcutBadger", 3)) {
                return false;
            }
            Log.d("ShortcutBadger", "Unable to execute badge", e);
            return false;
        }
    }

    public static void b(Context context, int badgeCount) {
        if (c != null || c(context)) {
            try {
                c.b(context, d, badgeCount);
            } catch (Exception e) {
                throw new ShortcutBadgeException("Unable to execute badge", e);
            }
        } else {
            throw new ShortcutBadgeException("No default launcher available");
        }
    }

    private static boolean c(Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntent == null) {
            Log.e("ShortcutBadger", "Unable to find launch intent for package " + context.getPackageName());
            return false;
        }
        d = launchIntent.getComponent();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 65536)) {
            String currentHomePackage = resolveInfo.activityInfo.packageName;
            Iterator<Class<? extends a>> it = a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                a shortcutBadger = null;
                try {
                    shortcutBadger = (a) it.next().newInstance();
                } catch (Exception e) {
                }
                if (shortcutBadger != null && shortcutBadger.a().contains(currentHomePackage)) {
                    c = shortcutBadger;
                    break;
                }
            }
            if (c != null) {
                break;
            }
        }
        if (c != null) {
            return true;
        }
        String str = Build.MANUFACTURER;
        if (str.equalsIgnoreCase("ZUK")) {
            c = new h();
            return true;
        } else if (str.equalsIgnoreCase("OPPO")) {
            c = new d();
            return true;
        } else if (str.equalsIgnoreCase("VIVO")) {
            c = new f();
            return true;
        } else if (str.equalsIgnoreCase("ZTE")) {
            c = new g();
            return true;
        } else {
            c = new DefaultBadger();
            return true;
        }
    }
}
