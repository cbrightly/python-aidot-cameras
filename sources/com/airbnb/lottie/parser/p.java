package com.airbnb.lottie.parser;

import android.graphics.Path;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.c;
import com.airbnb.lottie.model.animatable.d;
import com.airbnb.lottie.model.animatable.f;
import com.airbnb.lottie.model.content.e;
import com.airbnb.lottie.model.content.g;
import com.airbnb.lottie.parser.moshi.a;
import java.util.Collections;

/* compiled from: GradientFillParser */
public class p {
    private static final a.C0011a a = a.C0011a.a("nm", "g", "o", "t", "s", "e", "r", "hd");
    private static final a.C0011a b = a.C0011a.a("p", "k");

    static e a(a reader, c0 composition) {
        a aVar = reader;
        String name = null;
        c color = null;
        d opacity = null;
        g gradientType = null;
        f startPoint = null;
        f endPoint = null;
        Path.FillType fillType = Path.FillType.WINDING;
        boolean hidden = false;
        while (reader.l()) {
            switch (aVar.x(a)) {
                case 0:
                    c0 c0Var = composition;
                    name = reader.s();
                    break;
                case 1:
                    int points = -1;
                    reader.g();
                    while (reader.l()) {
                        switch (aVar.x(b)) {
                            case 0:
                                c0 c0Var2 = composition;
                                points = reader.o();
                                break;
                            case 1:
                                color = d.g(aVar, composition, points);
                                break;
                            default:
                                c0 c0Var3 = composition;
                                reader.z();
                                reader.E();
                                break;
                        }
                    }
                    c0 c0Var4 = composition;
                    reader.j();
                    break;
                case 2:
                    opacity = d.h(reader, composition);
                    break;
                case 3:
                    gradientType = reader.o() == 1 ? g.LINEAR : g.RADIAL;
                    break;
                case 4:
                    startPoint = d.i(reader, composition);
                    break;
                case 5:
                    endPoint = d.i(reader, composition);
                    break;
                case 6:
                    fillType = reader.o() == 1 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                    break;
                case 7:
                    hidden = reader.m();
                    break;
                default:
                    c0 c0Var5 = composition;
                    reader.z();
                    reader.E();
                    break;
            }
        }
        c0 c0Var6 = composition;
        return new e(name, gradientType, fillType, color, opacity == null ? new d(Collections.singletonList(new com.airbnb.lottie.value.a(100))) : opacity, startPoint, endPoint, (b) null, (b) null, hidden);
    }
}
