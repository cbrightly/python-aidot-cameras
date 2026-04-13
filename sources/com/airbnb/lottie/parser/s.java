package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.PointF;
import androidx.annotation.ColorInt;
import com.airbnb.lottie.parser.moshi.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: JsonUtils */
public class s {
    private static final a.C0011a a = a.C0011a.a("x", "y");

    @ColorInt
    static int d(com.airbnb.lottie.parser.moshi.a reader) {
        reader.c();
        int r = (int) (reader.n() * 255.0d);
        int g = (int) (reader.n() * 255.0d);
        int b = (int) (reader.n() * 255.0d);
        while (reader.l()) {
            reader.E();
        }
        reader.i();
        return Color.argb(255, r, g, b);
    }

    static List<PointF> f(com.airbnb.lottie.parser.moshi.a reader, float scale) {
        List<PointF> points = new ArrayList<>();
        reader.c();
        while (reader.u() == a.b.BEGIN_ARRAY) {
            reader.c();
            points.add(e(reader, scale));
            reader.i();
        }
        reader.i();
        return points;
    }

    /* compiled from: JsonUtils */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[a.b.values().length];
            a = iArr;
            try {
                iArr[a.b.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.b.BEGIN_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.b.BEGIN_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static PointF e(com.airbnb.lottie.parser.moshi.a reader, float scale) {
        switch (a.a[reader.u().ordinal()]) {
            case 1:
                return b(reader, scale);
            case 2:
                return a(reader, scale);
            case 3:
                return c(reader, scale);
            default:
                throw new IllegalArgumentException("Unknown point starts with " + reader.u());
        }
    }

    private static PointF b(com.airbnb.lottie.parser.moshi.a reader, float scale) {
        float x = (float) reader.n();
        float y = (float) reader.n();
        while (reader.l()) {
            reader.E();
        }
        return new PointF(x * scale, y * scale);
    }

    private static PointF a(com.airbnb.lottie.parser.moshi.a reader, float scale) {
        reader.c();
        float x = (float) reader.n();
        float y = (float) reader.n();
        while (reader.u() != a.b.END_ARRAY) {
            reader.E();
        }
        reader.i();
        return new PointF(x * scale, y * scale);
    }

    private static PointF c(com.airbnb.lottie.parser.moshi.a reader, float scale) {
        float x = 0.0f;
        float y = 0.0f;
        reader.g();
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    x = g(reader);
                    break;
                case 1:
                    y = g(reader);
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
        return new PointF(x * scale, y * scale);
    }

    static float g(com.airbnb.lottie.parser.moshi.a reader) {
        a.b token = reader.u();
        switch (a.a[token.ordinal()]) {
            case 1:
                return (float) reader.n();
            case 2:
                reader.c();
                float val = (float) reader.n();
                while (reader.l()) {
                    reader.E();
                }
                reader.i();
                return val;
            default:
                throw new IllegalArgumentException("Unknown value for token of type " + token);
        }
    }
}
