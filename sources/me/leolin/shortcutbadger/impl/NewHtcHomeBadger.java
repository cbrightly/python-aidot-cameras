package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.luck.picture.lib.config.PictureConfig;
import java.util.Collections;
import java.util.List;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.a;

public class NewHtcHomeBadger implements a {
    public void b(Context context, ComponentName componentName, int badgeCount) {
        boolean intent1Success;
        boolean intentSuccess;
        Intent intent1 = new Intent("com.htc.launcher.action.SET_NOTIFICATION");
        intent1.putExtra("com.htc.launcher.extra.COMPONENT", componentName.flattenToShortString());
        intent1.putExtra("com.htc.launcher.extra.COUNT", badgeCount);
        Intent intent = new Intent("com.htc.launcher.action.UPDATE_SHORTCUT");
        intent.putExtra("packagename", componentName.getPackageName());
        intent.putExtra(PictureConfig.EXTRA_DATA_COUNT, badgeCount);
        try {
            me.leolin.shortcutbadger.util.a.c(context, intent1);
            intent1Success = true;
        } catch (ShortcutBadgeException e) {
            intent1Success = false;
        }
        try {
            me.leolin.shortcutbadger.util.a.c(context, intent);
            intentSuccess = true;
        } catch (ShortcutBadgeException e2) {
            intentSuccess = false;
        }
        if (!intent1Success && !intentSuccess) {
            throw new ShortcutBadgeException("unable to resolve intent: " + intent.toString());
        }
    }

    public List<String> a() {
        return Collections.singletonList("com.htc.launcher");
    }
}
