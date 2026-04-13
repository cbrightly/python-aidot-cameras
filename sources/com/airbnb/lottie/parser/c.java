package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.d;
import com.airbnb.lottie.model.animatable.e;
import com.airbnb.lottie.model.animatable.g;
import com.airbnb.lottie.model.animatable.i;
import com.airbnb.lottie.model.animatable.l;
import com.airbnb.lottie.model.animatable.m;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: AnimatableTransformParser */
public class c {
    private static final a.C0011a a = a.C0011a.a("a", "p", "s", "rz", "r", "o", "so", "eo", "sk", "sa");
    private static final a.C0011a b = a.C0011a.a("k");

    public static l g(a reader, c0 composition) {
        b skew;
        b skewAngle;
        boolean z;
        a aVar = reader;
        c0 c0Var = composition;
        b rotation = null;
        boolean z2 = false;
        boolean isObject = reader.u() == a.b.BEGIN_OBJECT;
        if (isObject) {
            reader.g();
        }
        g scale = null;
        d opacity = null;
        b startOpacity = null;
        b endOpacity = null;
        b skew2 = null;
        b skewAngle2 = null;
        e anchorPoint = null;
        AnimatableValue<PointF, PointF> position = null;
        while (reader.l()) {
            switch (aVar.x(a)) {
                case 0:
                    boolean z3 = z2;
                    reader.g();
                    while (reader.l()) {
                        switch (aVar.x(b)) {
                            case 0:
                                anchorPoint = a.a(reader, composition);
                                break;
                            default:
                                reader.z();
                                reader.E();
                                break;
                        }
                    }
                    reader.j();
                    c0Var = composition;
                    z2 = z3;
                    continue;
                case 1:
                    boolean z4 = z2;
                    position = a.b(reader, composition);
                    c0Var = composition;
                    continue;
                case 2:
                    boolean z5 = z2;
                    scale = d.j(reader, composition);
                    c0Var = composition;
                    continue;
                case 3:
                    c0Var.a("Lottie doesn't support 3D layers.");
                    break;
                case 4:
                    break;
                case 5:
                    opacity = d.h(reader, composition);
                    continue;
                case 6:
                    startOpacity = d.f(aVar, c0Var, z2);
                    continue;
                case 7:
                    endOpacity = d.f(aVar, c0Var, z2);
                    continue;
                case 8:
                    skew2 = d.f(aVar, c0Var, z2);
                    continue;
                case 9:
                    skewAngle2 = d.f(aVar, c0Var, z2);
                    continue;
                default:
                    boolean z6 = z2;
                    reader.z();
                    reader.E();
                    c0Var = composition;
                    continue;
            }
            b rotation2 = d.f(aVar, c0Var, z2);
            if (rotation2.k().isEmpty()) {
                com.airbnb.lottie.value.a aVar2 = r1;
                com.airbnb.lottie.value.a aVar3 = new com.airbnb.lottie.value.a(composition, Float.valueOf(0.0f), Float.valueOf(0.0f), (Interpolator) null, 0.0f, Float.valueOf(composition.f()));
                rotation2.k().add(aVar2);
                z = false;
            } else if (((com.airbnb.lottie.value.a) rotation2.k().get(0)).b == null) {
                z = false;
                rotation2.k().set(0, new com.airbnb.lottie.value.a(composition, Float.valueOf(0.0f), Float.valueOf(0.0f), (Interpolator) null, 0.0f, Float.valueOf(composition.f())));
            } else {
                z = false;
            }
            c0Var = composition;
            z2 = z;
            rotation = rotation2;
        }
        if (isObject) {
            reader.j();
        }
        if (a(anchorPoint)) {
            anchorPoint = null;
        }
        if (b(position)) {
            position = null;
        }
        if (c(rotation)) {
            rotation = null;
        }
        if (d(scale)) {
            scale = null;
        }
        if (f(skew2)) {
            skew = null;
        } else {
            skew = skew2;
        }
        if (e(skewAngle2)) {
            skewAngle = null;
        } else {
            skewAngle = skewAngle2;
        }
        return new l(anchorPoint, position, scale, rotation, opacity, startOpacity, endOpacity, skew, skewAngle);
    }

    private static boolean a(e anchorPoint) {
        return anchorPoint == null || (anchorPoint.i() && ((PointF) anchorPoint.k().get(0).b).equals(0.0f, 0.0f));
    }

    private static boolean b(m<PointF, PointF> position) {
        if (position == null || (!(position instanceof i) && position.i() && ((PointF) position.k().get(0).b).equals(0.0f, 0.0f))) {
            return true;
        }
        return false;
    }

    private static boolean c(b rotation) {
        return rotation == null || (rotation.i() && ((Float) ((com.airbnb.lottie.value.a) rotation.k().get(0)).b).floatValue() == 0.0f);
    }

    private static boolean d(g scale) {
        return scale == null || (scale.i() && ((com.airbnb.lottie.value.d) ((com.airbnb.lottie.value.a) scale.k().get(0)).b).a(1.0f, 1.0f));
    }

    private static boolean f(b skew) {
        return skew == null || (skew.i() && ((Float) ((com.airbnb.lottie.value.a) skew.k().get(0)).b).floatValue() == 0.0f);
    }

    private static boolean e(b skewAngle) {
        return skewAngle == null || (skewAngle.i() && ((Float) ((com.airbnb.lottie.value.a) skewAngle.k().get(0)).b).floatValue() == 0.0f);
    }
}
