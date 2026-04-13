package com.airbnb.lottie.parser;

import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.h;
import com.airbnb.lottie.model.content.q;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: ShapePathParser */
public class k0 {
    static a.C0011a a = a.C0011a.a("nm", "ind", "ks", "hd");

    static q a(a reader, c0 composition) {
        String name = null;
        int ind = 0;
        h shape = null;
        boolean hidden = false;
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    name = reader.s();
                    break;
                case 1:
                    ind = reader.o();
                    break;
                case 2:
                    shape = d.k(reader, composition);
                    break;
                case 3:
                    hidden = reader.m();
                    break;
                default:
                    reader.E();
                    break;
            }
        }
        return new q(name, ind, shape, hidden);
    }
}
