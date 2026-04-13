package io.rmiri.skeleton.utils;

import android.graphics.Color;

/* compiled from: ColorUtils */
public class b {
    public static int[] b(int colorInner, int colorOut) {
        int colorOutTransparent = a(colorOut, 0.0f);
        return new int[]{colorOutTransparent, colorInner, colorOutTransparent};
    }

    public static int[] c(int colorInner, int colorOut) {
        return new int[]{a(colorOut, 0.0f), colorInner, colorOut};
    }

    public static int a(int color, float ratio) {
        return Color.argb(Math.round(((float) Color.alpha(color)) * ratio), Color.red(color), Color.green(color), Color.blue(color));
    }
}
