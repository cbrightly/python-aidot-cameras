package com.blankj.utilcode.util;

import android.content.res.Resources;
import android.graphics.Point;
import android.view.WindowManager;

/* compiled from: ScreenUtils */
public final class x {
    public static int b() {
        WindowManager wm = (WindowManager) f0.a().getSystemService("window");
        if (wm == null) {
            return -1;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static int a() {
        WindowManager wm = (WindowManager) f0.a().getSystemService("window");
        if (wm == null) {
            return -1;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static float c() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static int d() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    public static boolean e() {
        return f0.a().getResources().getConfiguration().orientation == 1;
    }
}
