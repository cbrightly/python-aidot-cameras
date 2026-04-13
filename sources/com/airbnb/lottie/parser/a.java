package com.airbnb.lottie.parser;

import android.graphics.PointF;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.e;
import com.airbnb.lottie.model.animatable.i;
import com.airbnb.lottie.model.animatable.m;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.utils.h;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.List;

/* compiled from: AnimatablePathValueParser */
public class a {
    private static final a.C0011a a = a.C0011a.a("k", "x", "y");

    public static e a(com.airbnb.lottie.parser.moshi.a reader, c0 composition) {
        List<Keyframe<PointF>> keyframes = new ArrayList<>();
        if (reader.u() == a.b.BEGIN_ARRAY) {
            reader.c();
            while (reader.l()) {
                keyframes.add(z.a(reader, composition));
            }
            reader.i();
            u.b(keyframes);
        } else {
            keyframes.add(new com.airbnb.lottie.value.a(s.e(reader, h.e())));
        }
        return new e(keyframes);
    }

    static m<PointF, PointF> b(com.airbnb.lottie.parser.moshi.a reader, c0 composition) {
        e pathAnimation = null;
        b xAnimation = null;
        b yAnimation = null;
        boolean hasExpressions = false;
        reader.g();
        while (reader.u() != a.b.END_OBJECT) {
            switch (reader.x(a)) {
                case 0:
                    pathAnimation = a(reader, composition);
                    break;
                case 1:
                    if (reader.u() != a.b.STRING) {
                        xAnimation = d.e(reader, composition);
                        break;
                    } else {
                        hasExpressions = true;
                        reader.E();
                        break;
                    }
                case 2:
                    if (reader.u() != a.b.STRING) {
                        yAnimation = d.e(reader, composition);
                        break;
                    } else {
                        hasExpressions = true;
                        reader.E();
                        break;
                    }
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
        if (hasExpressions) {
            composition.a("Lottie doesn't support expressions.");
        }
        if (pathAnimation != null) {
            return pathAnimation;
        }
        return new i(xAnimation, yAnimation);
    }
}
