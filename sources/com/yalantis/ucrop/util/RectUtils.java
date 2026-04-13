package com.yalantis.ucrop.util;

import android.graphics.RectF;

public class RectUtils {
    public static float[] getCornersFromRect(RectF r) {
        float f = r.left;
        float f2 = r.top;
        float f3 = r.right;
        float f4 = r.bottom;
        return new float[]{f, f2, f3, f2, f3, f4, f, f4};
    }

    public static float[] getRectSidesFromCorners(float[] corners) {
        return new float[]{(float) Math.sqrt(Math.pow((double) (corners[0] - corners[2]), 2.0d) + Math.pow((double) (corners[1] - corners[3]), 2.0d)), (float) Math.sqrt(Math.pow((double) (corners[2] - corners[4]), 2.0d) + Math.pow((double) (corners[3] - corners[5]), 2.0d))};
    }

    public static float[] getCenterFromRect(RectF r) {
        return new float[]{r.centerX(), r.centerY()};
    }

    public static RectF trapToRect(float[] array) {
        RectF r = new RectF(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        for (int i = 1; i < array.length; i += 2) {
            float x = ((float) Math.round(array[i - 1] * 10.0f)) / 10.0f;
            float y = ((float) Math.round(array[i] * 10.0f)) / 10.0f;
            float f = r.left;
            if (x < f) {
                f = x;
            }
            r.left = f;
            float f2 = r.top;
            if (y < f2) {
                f2 = y;
            }
            r.top = f2;
            float f3 = r.right;
            if (x > f3) {
                f3 = x;
            }
            r.right = f3;
            float f4 = r.bottom;
            if (y > f4) {
                f4 = y;
            }
            r.bottom = f4;
        }
        r.sort();
        return r;
    }
}
