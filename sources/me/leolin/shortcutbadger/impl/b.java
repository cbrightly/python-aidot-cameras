package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.luck.picture.lib.config.PictureConfig;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.a;

/* compiled from: EverythingMeHomeBadger */
public class b implements a {
    public void b(Context context, ComponentName componentName, int badgeCount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("package_name", componentName.getPackageName());
        contentValues.put("activity_name", componentName.getClassName());
        contentValues.put(PictureConfig.EXTRA_DATA_COUNT, Integer.valueOf(badgeCount));
        context.getContentResolver().insert(Uri.parse("content://me.everything.badger/apps"), contentValues);
    }

    public List<String> a() {
        return Arrays.asList(new String[]{"me.everything.launcher"});
    }
}
