package me.leolin.shortcutbadger.impl;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import java.util.Collections;
import java.util.List;
import me.leolin.shortcutbadger.a;

/* compiled from: ZukHomeBadger */
public class h implements a {
    private final Uri a = Uri.parse("content://com.android.badge/badge");

    @TargetApi(11)
    public void b(Context context, ComponentName componentName, int badgeCount) {
        Bundle extra = new Bundle();
        extra.putInt("app_badge_count", badgeCount);
        context.getContentResolver().call(this.a, "setAppBadgeCount", (String) null, extra);
    }

    public List<String> a() {
        return Collections.singletonList("com.zui.launcher");
    }
}
