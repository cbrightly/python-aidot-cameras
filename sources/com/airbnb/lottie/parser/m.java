package com.airbnb.lottie.parser;

import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.model.content.p;
import com.airbnb.lottie.model.d;
import com.airbnb.lottie.parser.moshi.a;
import com.leedarson.bean.Constants;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FontCharacterParser */
public class m {
    private static final a.C0011a a = a.C0011a.a("ch", "size", "w", Constants.ACTION_STYLE, "fFamily", "data");
    private static final a.C0011a b = a.C0011a.a("shapes");

    static d a(a reader, c0 composition) {
        a aVar = reader;
        char character = 0;
        double size = 0.0d;
        double width = 0.0d;
        String style = null;
        String fontFamily = null;
        List<ShapeGroup> shapes = new ArrayList<>();
        reader.g();
        while (reader.l()) {
            switch (aVar.x(a)) {
                case 0:
                    character = reader.s().charAt(0);
                    break;
                case 1:
                    size = reader.n();
                    break;
                case 2:
                    width = reader.n();
                    break;
                case 3:
                    style = reader.s();
                    break;
                case 4:
                    fontFamily = reader.s();
                    break;
                case 5:
                    reader.g();
                    while (reader.l()) {
                        switch (aVar.x(b)) {
                            case 0:
                                reader.c();
                                while (reader.l()) {
                                    shapes.add((p) h.a(reader, composition));
                                }
                                reader.i();
                                break;
                            default:
                                reader.z();
                                reader.E();
                                break;
                        }
                    }
                    reader.j();
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
        return new d(shapes, character, size, width, style, fontFamily);
    }
}
