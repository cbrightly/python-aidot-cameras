package com.airbnb.lottie.parser;

import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.k;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: AnimatableTextPropertiesParser */
public class b {
    private static final a.C0011a a = a.C0011a.a("a");
    private static final a.C0011a b = a.C0011a.a("fc", "sc", "sw", "t");

    public static k a(a reader, c0 composition) {
        k anim = null;
        reader.g();
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    anim = b(reader, composition);
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
        if (anim == null) {
            return new k((com.airbnb.lottie.model.animatable.a) null, (com.airbnb.lottie.model.animatable.a) null, (com.airbnb.lottie.model.animatable.b) null, (com.airbnb.lottie.model.animatable.b) null);
        }
        return anim;
    }

    private static k b(a reader, c0 composition) {
        com.airbnb.lottie.model.animatable.a color = null;
        com.airbnb.lottie.model.animatable.a stroke = null;
        com.airbnb.lottie.model.animatable.b strokeWidth = null;
        com.airbnb.lottie.model.animatable.b tracking = null;
        reader.g();
        while (reader.l()) {
            switch (reader.x(b)) {
                case 0:
                    color = d.c(reader, composition);
                    break;
                case 1:
                    stroke = d.c(reader, composition);
                    break;
                case 2:
                    strokeWidth = d.e(reader, composition);
                    break;
                case 3:
                    tracking = d.e(reader, composition);
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
        return new k(color, stroke, strokeWidth, tracking);
    }
}
