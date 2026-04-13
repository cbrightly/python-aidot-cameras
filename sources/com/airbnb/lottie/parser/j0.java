package com.airbnb.lottie.parser;

import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.c;
import com.airbnb.lottie.model.content.p;
import com.airbnb.lottie.parser.moshi.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ShapeGroupParser */
public class j0 {
    private static final a.C0011a a = a.C0011a.a("nm", "hd", "it");

    static p a(a reader, c0 composition) {
        String name = null;
        boolean hidden = false;
        List<ContentModel> items = new ArrayList<>();
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    name = reader.s();
                    break;
                case 1:
                    hidden = reader.m();
                    break;
                case 2:
                    reader.c();
                    while (reader.l()) {
                        c newItem = h.a(reader, composition);
                        if (newItem != null) {
                            items.add(newItem);
                        }
                    }
                    reader.i();
                    break;
                default:
                    reader.E();
                    break;
            }
        }
        return new p(name, items, hidden);
    }
}
