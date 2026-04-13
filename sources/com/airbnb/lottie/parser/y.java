package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.content.i;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: MergePathsParser */
public class y {
    private static final a.C0011a a = a.C0011a.a("nm", "mm", "hd");

    static i a(a reader) {
        String name = null;
        i.a mode = null;
        boolean hidden = false;
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    name = reader.s();
                    break;
                case 1:
                    mode = i.a.forId(reader.o());
                    break;
                case 2:
                    hidden = reader.m();
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        return new i(name, mode, hidden);
    }
}
