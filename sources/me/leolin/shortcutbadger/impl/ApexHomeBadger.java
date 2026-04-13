package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.luck.picture.lib.config.PictureConfig;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.a;

public class ApexHomeBadger implements a {
    public void b(Context context, ComponentName componentName, int badgeCount) {
        Intent intent = new Intent("com.anddoes.launcher.COUNTER_CHANGED");
        intent.putExtra("package", componentName.getPackageName());
        intent.putExtra(PictureConfig.EXTRA_DATA_COUNT, badgeCount);
        intent.putExtra("class", componentName.getClassName());
        me.leolin.shortcutbadger.util.a.c(context, intent);
    }

    public List<String> a() {
        return Arrays.asList(new String[]{"com.anddoes.launcher"});
    }
}
