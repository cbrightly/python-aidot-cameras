package com.yalantis.ucrop.util;

public final class CubicEasing {
    public static float easeOut(float time, float start, float end, float duration) {
        float f = (time / duration) - 1.0f;
        float time2 = f;
        return (((f * time2 * time2) + 1.0f) * end) + start;
    }

    public static float easeIn(float time, float start, float end, float duration) {
        float f = time / duration;
        float time2 = f;
        return (f * end * time2 * time2) + start;
    }

    public static float easeInOut(float time, float start, float end, float duration) {
        float f = time / (duration / 2.0f);
        float time2 = f;
        if (f < 1.0f) {
            return ((end / 2.0f) * time2 * time2 * time2) + start;
        }
        float f2 = time2 - 2.0f;
        float time3 = f2;
        return ((end / 2.0f) * ((f2 * time3 * time3) + 2.0f)) + start;
    }
}
