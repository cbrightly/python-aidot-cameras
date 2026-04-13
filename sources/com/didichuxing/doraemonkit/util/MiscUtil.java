package com.didichuxing.doraemonkit.util;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

public class MiscUtil {
    public static int measure(int measureSpec, int defaultSize) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        if (specMode == 1073741824) {
            return specSize;
        }
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(result, specSize);
        }
        return result;
    }

    public static int dipToPx(Context context, float dip) {
        return (int) ((dip * context.getResources().getDisplayMetrics().density) + (((float) (dip >= 0.0f ? 1 : -1)) * 0.5f));
    }

    public static String getPrecisionFormat(int precision) {
        return "%." + precision + "f";
    }

    public static <T> T[] reverse(T[] arrays) {
        if (arrays == null) {
            return null;
        }
        int length = arrays.length;
        for (int i = 0; i < length / 2; i++) {
            T t = arrays[i];
            arrays[i] = arrays[(length - i) - 1];
            arrays[(length - i) - 1] = t;
        }
        return arrays;
    }

    public static float measureTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return Math.abs(fontMetrics.ascent) - fontMetrics.descent;
    }
}
