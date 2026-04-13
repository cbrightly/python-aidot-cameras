package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.c;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: FontParser */
public class n {
    private static final a.C0011a a = a.C0011a.a("fFamily", "fName", "fStyle", "ascent");

    static c a(a reader) {
        String family = null;
        String name = null;
        String style = null;
        float ascent = 0.0f;
        reader.g();
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    family = reader.s();
                    break;
                case 1:
                    name = reader.s();
                    break;
                case 2:
                    style = reader.s();
                    break;
                case 3:
                    ascent = (float) reader.n();
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
        return new c(family, name, style, ascent);
    }
}
