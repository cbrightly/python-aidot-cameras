package com.blankj.utilcode.util;

import android.content.res.Resources;

/* compiled from: SizeUtils */
public final class z {
    public static int a(float dpValue) {
        return (int) ((dpValue * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static int b(float pxValue) {
        return (int) ((pxValue / Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }
}
