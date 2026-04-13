package com.airbnb.lottie.parser;

import android.graphics.Path;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.d;
import com.airbnb.lottie.model.content.o;
import com.airbnb.lottie.parser.moshi.a;
import java.util.Collections;

/* compiled from: ShapeFillParser */
public class i0 {
    private static final a.C0011a a = a.C0011a.a("nm", "c", "o", "fillEnabled", "r", "hd");

    static o a(a reader, c0 composition) {
        com.airbnb.lottie.model.animatable.a color = null;
        boolean fillEnabled = false;
        d opacity = null;
        String name = null;
        int fillTypeInt = 1;
        boolean hidden = false;
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    name = reader.s();
                    break;
                case 1:
                    color = d.c(reader, composition);
                    break;
                case 2:
                    opacity = d.h(reader, composition);
                    break;
                case 3:
                    fillEnabled = reader.m();
                    break;
                case 4:
                    fillTypeInt = reader.o();
                    break;
                case 5:
                    hidden = reader.m();
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        return new o(name, fillEnabled, fillTypeInt == 1 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD, color, opacity == null ? new d(Collections.singletonList(new com.airbnb.lottie.value.a(100))) : opacity, hidden);
    }
}
