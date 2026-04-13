package com.airbnb.lottie.parser;

import com.airbnb.lottie.parser.moshi.a;

/* compiled from: IntegerParser */
public class r implements n0<Integer> {
    public static final r a = new r();

    private r() {
    }

    /* renamed from: b */
    public Integer a(a reader, float scale) {
        return Integer.valueOf(Math.round(s.g(reader) * scale));
    }
}
