package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: PointFParser */
public class b0 implements n0<PointF> {
    public static final b0 a = new b0();

    private b0() {
    }

    /* renamed from: b */
    public PointF a(a reader, float scale) {
        a.b token = reader.u();
        if (token == a.b.BEGIN_ARRAY) {
            return s.e(reader, scale);
        }
        if (token == a.b.BEGIN_OBJECT) {
            return s.e(reader, scale);
        }
        if (token == a.b.NUMBER) {
            PointF point = new PointF(((float) reader.n()) * scale, ((float) reader.n()) * scale);
            while (reader.l()) {
                reader.E();
            }
            return point;
        }
        throw new IllegalArgumentException("Cannot convert json to point. Next token is " + token);
    }
}
