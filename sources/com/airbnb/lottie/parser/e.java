package com.airbnb.lottie.parser;

import androidx.annotation.Nullable;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: BlurEffectParser */
public class e {
    private static final a.C0011a a = a.C0011a.a("ef");
    private static final a.C0011a b = a.C0011a.a("ty", "v");

    @Nullable
    static com.airbnb.lottie.model.content.a b(a reader, c0 composition) {
        com.airbnb.lottie.model.content.a blurEffect = null;
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    reader.c();
                    while (reader.l()) {
                        com.airbnb.lottie.model.content.a be = a(reader, composition);
                        if (be != null) {
                            blurEffect = be;
                        }
                    }
                    reader.i();
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        return blurEffect;
    }

    @Nullable
    private static com.airbnb.lottie.model.content.a a(a reader, c0 composition) {
        com.airbnb.lottie.model.content.a blurEffect = null;
        boolean isCorrectType = false;
        reader.g();
        while (reader.l()) {
            switch (reader.x(b)) {
                case 0:
                    isCorrectType = reader.o() == 0;
                    break;
                case 1:
                    if (!isCorrectType) {
                        reader.E();
                        break;
                    } else {
                        blurEffect = new com.airbnb.lottie.model.content.a(d.e(reader, composition));
                        break;
                    }
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
        return blurEffect;
    }
}
