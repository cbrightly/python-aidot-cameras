package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.a;

/* compiled from: FloatParser */
public class l implements n0<Float> {
    public static final l a = new l();

    private l() {
    }

    /* renamed from: b */
    public Float a(a reader, float scale) {
        return Float.valueOf(s.g(reader) * scale);
    }
}
