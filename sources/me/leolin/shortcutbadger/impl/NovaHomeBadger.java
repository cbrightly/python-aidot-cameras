package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.luck.picture.lib.config.PictureConfig;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.a;

public class NovaHomeBadger implements a {
    public void b(Context context, ComponentName componentName, int badgeCount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Progress.TAG, componentName.getPackageName() + "/" + componentName.getClassName());
        contentValues.put(PictureConfig.EXTRA_DATA_COUNT, Integer.valueOf(badgeCount));
        context.getContentResolver().insert(Uri.parse("content://com.teslacoilsw.notifier/unread_count"), contentValues);
    }

    public List<String> a() {
        return Arrays.asList(new String[]{"com.teslacoilsw.launcher"});
    }
}
