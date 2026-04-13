package com.airbnb.lottie.parser;

import android.graphics.Color;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: ColorParser */
public class g implements n0<Integer> {
    public static final g a = new g();

    private g() {
    }

    /* renamed from: b */
    public Integer a(a reader, float scale) {
        boolean isArray = reader.u() == a.b.BEGIN_ARRAY;
        if (isArray) {
            reader.c();
        }
        double r = reader.n();
        double g = reader.n();
        double b = reader.n();
        double a2 = 1.0d;
        if (reader.u() == a.b.NUMBER) {
            a2 = reader.n();
        }
        if (isArray) {
            reader.i();
        }
        if (r <= 1.0d && g <= 1.0d && b <= 1.0d) {
            r *= 255.0d;
            g *= 255.0d;
            b *= 255.0d;
            if (a2 <= 1.0d) {
                a2 *= 255.0d;
            }
        }
        return Integer.valueOf(Color.argb((int) a2, (int) r, (int) g, (int) b));
    }
}
