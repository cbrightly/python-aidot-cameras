package me.leolin.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import java.util.Arrays;
import java.util.List;
import me.leolin.shortcutbadger.a;

/* compiled from: HuaweiHomeBadger */
public class c implements a {
    public void b(Context context, ComponentName componentName, int badgeCount) {
        Bundle localBundle = new Bundle();
        localBundle.putString("package", context.getPackageName());
        localBundle.putString("class", componentName.getClassName());
        localBundle.putInt("badgenumber", badgeCount);
        context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", (String) null, localBundle);
    }

    public List<String> a() {
        return Arrays.asList(new String[]{"com.huawei.android.launcher"});
    }
}
