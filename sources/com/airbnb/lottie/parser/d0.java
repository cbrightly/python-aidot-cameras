package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.content.k;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: RectangleShapeParser */
public class d0 {
    private static final a.C0011a a = a.C0011a.a("nm", "p", "s", "r", "hd");

    static k a(a reader, c0 composition) {
        String name = null;
        AnimatableValue<PointF, PointF> position = null;
        AnimatableValue<PointF, PointF> size = null;
        b roundedness = null;
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
                    roundedness = d.e(reader, composition);
                    break;
                case 4:
                    hidden = reader.m();
                    break;
                default:
                    reader.E();
                    break;
            }
        }
        return new k(name, position, size, roundedness, hidden);
    }
}
