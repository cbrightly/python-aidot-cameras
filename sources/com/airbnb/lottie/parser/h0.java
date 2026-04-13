package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.model.CubicCurveData;
import com.airbnb.lottie.model.content.n;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.utils.g;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: ShapeDataParser */
public class h0 implements n0<n> {
    public static final h0 a = new h0();
    private static final a.C0011a b = a.C0011a.a("c", "v", "i", "o");

    private h0() {
    }

    /* renamed from: b */
    public n a(a reader, float scale) {
        if (reader.u() == a.b.BEGIN_ARRAY) {
            reader.c();
        }
        boolean closed = false;
        List<PointF> pointsArray = null;
        List<PointF> inTangents = null;
        List<PointF> outTangents = null;
        reader.g();
        while (reader.l()) {
            switch (reader.x(b)) {
                case 0:
                    closed = reader.m();
                    break;
                case 1:
                    pointsArray = s.f(reader, scale);
                    break;
                case 2:
                    inTangents = s.f(reader, scale);
                    break;
                case 3:
                    outTangents = s.f(reader, scale);
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        a aVar = reader;
        reader.j();
        if (reader.u() == a.b.END_ARRAY) {
            reader.i();
        }
        if (pointsArray == null || inTangents == null || outTangents == null) {
            throw new IllegalArgumentException("Shape data was missing information.");
        } else if (pointsArray.isEmpty()) {
            return new n(new PointF(), false, Collections.emptyList());
        } else {
            int length = pointsArray.size();
            PointF initialPoint = pointsArray.get(0);
            List<CubicCurveData> curves = new ArrayList<>(length);
            for (int i = 1; i < length; i++) {
                PointF vertex = pointsArray.get(i);
                curves.add(new com.airbnb.lottie.model.a(g.a(pointsArray.get(i - 1), outTangents.get(i - 1)), g.a(vertex, inTangents.get(i)), vertex));
            }
            if (closed) {
                PointF vertex2 = pointsArray.get(0);
                curves.add(new com.airbnb.lottie.model.a(g.a(pointsArray.get(length - 1), outTangents.get(length - 1)), g.a(vertex2, inTangents.get(0)), vertex2));
            }
            return new n(initialPoint, closed, curves);
        }
    }
}
