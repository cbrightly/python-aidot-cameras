package com.airbnb.lottie.parser;

import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.content.s;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: ShapeTrimPathParser */
public class m0 {
    private static final a.C0011a a = a.C0011a.a("s", "e", "o", "nm", "m", "hd");

    static s a(a reader, c0 composition) {
        String name = null;
        s.a type = null;
        b start = null;
        b end = null;
        b offset = null;
        boolean hidden = false;
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    start = d.f(reader, composition, false);
                    break;
                case 1:
                    end = d.f(reader, composition, false);
                    break;
                case 2:
                    offset = d.f(reader, composition, false);
                    break;
                case 3:
                    name = reader.s();
                    break;
                case 4:
                    type = s.a.forId(reader.o());
                    break;
                case 5:
                    hidden = reader.m();
                    break;
                default:
                    reader.E();
                    break;
            }
        }
        return new s(name, type, start, end, offset, hidden);
    }
}
