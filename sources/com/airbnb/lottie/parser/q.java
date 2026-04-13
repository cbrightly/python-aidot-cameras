package com.airbnb.lottie.parser;

import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.c;
import com.airbnb.lottie.model.animatable.d;
import com.airbnb.lottie.model.content.f;
import com.airbnb.lottie.model.content.g;
import com.airbnb.lottie.model.content.r;
import com.airbnb.lottie.parser.moshi.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: GradientStrokeParser */
public class q {
    private static final a.C0011a a = a.C0011a.a("nm", "g", "o", "t", "s", "e", "w", "lc", "lj", "ml", "hd", "d");
    private static final a.C0011a b = a.C0011a.a("p", "k");
    private static final a.C0011a c = a.C0011a.a("n", "v");

    static f a(a reader, c0 composition) {
        a aVar = reader;
        c0 c0Var = composition;
        String name = null;
        c color = null;
        d opacity = null;
        g gradientType = null;
        com.airbnb.lottie.model.animatable.f startPoint = null;
        com.airbnb.lottie.model.animatable.f endPoint = null;
        b width = null;
        r.b capType = null;
        r.c joinType = null;
        b offset = null;
        float miterLimit = 0.0f;
        boolean hidden = false;
        List<AnimatableFloatValue> lineDashPattern = new ArrayList<>();
        while (reader.l()) {
            boolean hidden2 = hidden;
            switch (aVar.x(a)) {
                case 0:
                    name = reader.s();
                    hidden = hidden2;
                    break;
                case 1:
                    List<AnimatableFloatValue> lineDashPattern2 = lineDashPattern;
                    int points = -1;
                    reader.g();
                    while (reader.l()) {
                        switch (aVar.x(b)) {
                            case 0:
                                points = reader.o();
                                break;
                            case 1:
                                color = d.g(aVar, c0Var, points);
                                break;
                            default:
                                reader.z();
                                reader.E();
                                break;
                        }
                    }
                    reader.j();
                    hidden = hidden2;
                    lineDashPattern = lineDashPattern2;
                    break;
                case 2:
                    opacity = d.h(reader, composition);
                    hidden = hidden2;
                    break;
                case 3:
                    List<AnimatableFloatValue> lineDashPattern3 = lineDashPattern;
                    gradientType = reader.o() == 1 ? g.LINEAR : g.RADIAL;
                    hidden = hidden2;
                    lineDashPattern = lineDashPattern3;
                    break;
                case 4:
                    startPoint = d.i(reader, composition);
                    hidden = hidden2;
                    break;
                case 5:
                    endPoint = d.i(reader, composition);
                    hidden = hidden2;
                    break;
                case 6:
                    width = d.e(reader, composition);
                    hidden = hidden2;
                    break;
                case 7:
                    capType = r.b.values()[reader.o() - 1];
                    hidden = hidden2;
                    lineDashPattern = lineDashPattern;
                    break;
                case 8:
                    joinType = r.c.values()[reader.o() - 1];
                    hidden = hidden2;
                    lineDashPattern = lineDashPattern;
                    break;
                case 9:
                    miterLimit = (float) reader.n();
                    hidden = hidden2;
                    lineDashPattern = lineDashPattern;
                    break;
                case 10:
                    hidden = reader.m();
                    break;
                case 11:
                    reader.c();
                    while (reader.l()) {
                        String n = null;
                        b val = null;
                        reader.g();
                        while (reader.l()) {
                            switch (aVar.x(c)) {
                                case 0:
                                    n = reader.s();
                                    break;
                                case 1:
                                    val = d.e(reader, composition);
                                    break;
                                default:
                                    reader.z();
                                    reader.E();
                                    break;
                            }
                        }
                        reader.j();
                        if (n.equals("o")) {
                            offset = val;
                        } else if (n.equals("d") || n.equals("g")) {
                            c0Var.u(true);
                            lineDashPattern.add(val);
                        }
                    }
                    reader.i();
                    if (lineDashPattern.size() == 1) {
                        lineDashPattern.add((b) lineDashPattern.get(0));
                    }
                    hidden = hidden2;
                    break;
                default:
                    List<AnimatableFloatValue> list = lineDashPattern;
                    reader.z();
                    reader.E();
                    hidden = hidden2;
                    break;
            }
        }
        return new f(name, gradientType, color, opacity == null ? new d(Collections.singletonList(new com.airbnb.lottie.value.a(100))) : opacity, startPoint, endPoint, width, capType, joinType, miterLimit, lineDashPattern, offset, hidden);
    }
}
