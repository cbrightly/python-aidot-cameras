package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.value.d;

/* compiled from: ScaleXYParser */
public class g0 implements n0<d> {
    public static final g0 a = new g0();

    private g0() {
    }

    /* renamed from: b */
    public d a(a reader, float scale) {
        boolean isArray = reader.u() == a.b.BEGIN_ARRAY;
        if (isArray) {
            reader.c();
        }
        float sx = (float) reader.n();
        float sy = (float) reader.n();
        while (reader.l()) {
            reader.E();
        }
        if (isArray) {
            reader.i();
        }
        return new d((sx / 100.0f) * scale, (sy / 100.0f) * scale);
    }
}
