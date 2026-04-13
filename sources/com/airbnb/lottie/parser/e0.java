package com.airbnb.lottie.parser;

import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.content.l;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: RepeaterParser */
public class e0 {
    private static final a.C0011a a = a.C0011a.a("nm", "c", "o", "tr", "hd");

    static l a(a reader, c0 composition) {
        String name = null;
        b copies = null;
        b offset = null;
        com.airbnb.lottie.model.animatable.l transform = null;
        boolean hidden = false;
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    name = reader.s();
                    break;
                case 1:
                    copies = d.f(reader, composition, false);
                    break;
                case 2:
                    offset = d.f(reader, composition, false);
                    break;
                case 3:
                    transform = c.g(reader, composition);
                    break;
                case 4:
                    hidden = reader.m();
                    break;
                default:
                    reader.E();
                    break;
            }
        }
        return new l(name, copies, offset, transform, hidden);
    }
}
