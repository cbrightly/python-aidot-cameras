package com.blankj.utilcode.util;

import android.os.Vibrator;
import androidx.annotation.RequiresPermission;

/* compiled from: VibrateUtils */
public final class i0 {
    private static Vibrator a;

    @RequiresPermission("android.permission.VIBRATE")
    public static void b(long milliseconds) {
        Vibrator vibrator = a();
        if (vibrator != null) {
            vibrator.vibrate(milliseconds);
        }
    }

    private static Vibrator a() {
        if (a == null) {
            a = (Vibrator) f0.a().getSystemService("vibrator");
        }
        return a;
    }
}
