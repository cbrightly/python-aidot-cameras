package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.b;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: CircleShapeParser */
public class f {
    private static final a.C0011a a = a.C0011a.a("nm", "p", "s", "hd", "d");

    static b a(a reader, c0 composition, int d) {
        String name = null;
        AnimatableValue<PointF, PointF> position = null;
        com.airbnb.lottie.model.animatable.f size = null;
        boolean reversed = d == 3;
        boolean hidden = false;
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    name = reader.s();
                    break;
                case 1:
                    position = a.b(reader, composition);
                    break;
                case 2:
                    size = d.i(reader, composition);
                    break;
                case 3:
                    hidden = reader.m();
                    break;
                case 4:
                    reversed = reader.o() == 3;
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        a aVar = reader;
        return new b(name, position, size, reversed, hidden);
    }
}
