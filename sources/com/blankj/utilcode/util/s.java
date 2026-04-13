package com.blankj.utilcode.util;

import android.content.Intent;
import android.provider.Settings;
import androidx.annotation.RequiresApi;

/* compiled from: PermissionUtils */
public final class s {
    @RequiresApi(api = 23)
    public static boolean a() {
        return Settings.canDrawOverlays(f0.a());
    }

    public static void b() {
        Intent intent = h0.r(f0.a().getPackageName(), true);
        if (h0.C(intent)) {
            f0.a().startActivity(intent);
        }
    }
}
