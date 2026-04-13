package com.airbnb.lottie.parser;

import androidx.annotation.Nullable;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.content.c;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.utils.d;
import io.netty.util.internal.StringUtil;

/* compiled from: ContentModelParser */
public class h {
    private static final a.C0011a a = a.C0011a.a("ty", "d");

    @Nullable
    static c a(a reader, c0 composition) {
        String type = null;
        reader.g();
        int d = 2;
        while (true) {
            if (reader.l()) {
                switch (reader.x(a)) {
                    case 0:
                        type = reader.s();
                        break;
                    case 1:
                        d = reader.o();
                        continue;
                    default:
                        reader.z();
                        reader.E();
                        continue;
                }
            }
        }
        if (type == null) {
            return null;
        }
        c model = null;
        char c = 65535;
        switch (type.hashCode()) {
            case 3239:
                if (type.equals("el")) {
                    c = 7;
                    break;
                }
                break;
            case 3270:
                if (type.equals("fl")) {
                    c = 3;
                    break;
                }
                break;
            case 3295:
                if (type.equals("gf")) {
                    c = 4;
                    break;
                }
                break;
            case 3307:
                if (type.equals("gr")) {
                    c = 0;
                    break;
                }
                break;
            case 3308:
                if (type.equals("gs")) {
                    c = 2;
                    break;
                }
                break;
            case 3488:
                if (type.equals("mm")) {
                    c = 11;
                    break;
                }
                break;
            case 3633:
                if (type.equals("rc")) {
                    c = 8;
                    break;
                }
                break;
            case 3634:
                if (type.equals("rd")) {
                    c = StringUtil.CARRIAGE_RETURN;
                    break;
                }
                break;
            case 3646:
                if (type.equals("rp")) {
                    c = 12;
                    break;
                }
                break;
            case 3669:
                if (type.equals("sh")) {
                    c = 6;
                    break;
                }
                break;
            case 3679:
                if (type.equals("sr")) {
                    c = 10;
                    break;
                }
                break;
            case 3681:
                if (type.equals("st")) {
                    c = 1;
                    break;
                }
                break;
            case 3705:
                if (type.equals("tm")) {
                    c = 9;
                    break;
                }
                break;
            case 3710:
                if (type.equals("tr")) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                model = j0.a(reader, composition);
                break;
            case 1:
                model = l0.a(reader, composition);
                break;
            case 2:
                model = q.a(reader, composition);
                break;
            case 3:
                model = i0.a(reader, composition);
                break;
            case 4:
                model = p.a(reader, composition);
                break;
            case 5:
                model = c.g(reader, composition);
                break;
            case 6:
                model = k0.a(reader, composition);
                break;
            case 7:
                model = f.a(reader, composition, d);
                break;
            case 8:
                model = d0.a(reader, composition);
                break;
            case 9:
                model = m0.a(reader, composition);
                break;
            case 10:
                model = c0.a(reader, composition, d);
                break;
            case 11:
                model = y.a(reader);
                composition.a("Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove().");
                break;
            case 12:
                model = e0.a(reader, composition);
                break;
            case 13:
                model = f0.a(reader, composition);
                break;
            default:
                d.c("Unknown shape type " + type);
                break;
        }
        while (reader.l()) {
            reader.E();
        }
        reader.j();
        return model;
    }
}
