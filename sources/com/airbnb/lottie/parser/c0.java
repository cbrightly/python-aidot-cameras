package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.content.j;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: PolystarShapeParser */
public class c0 {
    private static final a.C0011a a = a.C0011a.a("nm", "sy", "pt", "p", "r", "or", "os", "ir", "is", "hd", "d");

    static j a(a reader, com.airbnb.lottie.c0 composition, int d) {
        a aVar = reader;
        com.airbnb.lottie.c0 c0Var = composition;
        String name = null;
        j.a type = null;
        b points = null;
        AnimatableValue<PointF, PointF> position = null;
        b rotation = null;
        b outerRadius = null;
        b outerRoundedness = null;
        b innerRadius = null;
        b innerRoundedness = null;
        boolean hidden = false;
        boolean reversed = d == 3;
        while (reader.l()) {
            switch (aVar.x(a)) {
                case 0:
                    name = reader.s();
                    break;
                case 1:
                    type = j.a.forValue(reader.o());
                    break;
                case 2:
                    points = d.f(aVar, c0Var, false);
                    break;
                case 3:
                    position = a.b(reader, composition);
                    break;
                case 4:
                    rotation = d.f(aVar, c0Var, false);
                    break;
                case 5:
                    outerRadius = d.e(reader, composition);
                    break;
                case 6:
                    outerRoundedness = d.f(aVar, c0Var, false);
                    break;
                case 7:
                    innerRadius = d.e(reader, composition);
                    break;
                case 8:
                    innerRoundedness = d.f(aVar, c0Var, false);
                    break;
                case 9:
                    hidden = reader.m();
                    break;
                case 10:
                    reversed = reader.o() == 3;
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        return new j(name, type, points, position, rotation, innerRadius, outerRadius, innerRoundedness, outerRoundedness, hidden, reversed);
    }
}
