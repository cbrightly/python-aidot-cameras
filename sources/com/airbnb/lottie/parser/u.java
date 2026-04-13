package com.airbnb.lottie.parser;

import com.airbnb.lottie.animation.keyframe.i;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.value.Keyframe;
import java.util.ArrayList;
import java.util.List;

/* compiled from: KeyframesParser */
public class u {
    static a.C0011a a = a.C0011a.a("k");

    static <T> List<com.airbnb.lottie.value.a<T>> a(a reader, c0 composition, float scale, n0<T> valueParser, boolean multiDimensional) {
        List<Keyframe<T>> keyframes = new ArrayList<>();
        if (reader.u() == a.b.STRING) {
            composition.a("Lottie doesn't support expressions.");
            return keyframes;
        }
        reader.g();
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    if (reader.u() != a.b.BEGIN_ARRAY) {
                        keyframes.add(t.c(reader, composition, scale, valueParser, false, multiDimensional));
                        break;
                    } else {
                        reader.c();
                        if (reader.u() == a.b.NUMBER) {
                            keyframes.add(t.c(reader, composition, scale, valueParser, false, multiDimensional));
                        } else {
                            while (reader.l()) {
                                keyframes.add(t.c(reader, composition, scale, valueParser, true, multiDimensional));
                            }
                        }
                        reader.i();
                        break;
                    }
                default:
                    reader.E();
                    break;
            }
        }
        reader.j();
        b(keyframes);
        return keyframes;
    }

    public static <T> void b(List<? extends com.airbnb.lottie.value.a<T>> keyframes) {
        T t;
        int size = keyframes.size();
        for (int i = 0; i < size - 1; i++) {
            com.airbnb.lottie.value.a aVar = (com.airbnb.lottie.value.a) keyframes.get(i);
            Keyframe<T> nextKeyframe = (com.airbnb.lottie.value.a) keyframes.get(i + 1);
            aVar.h = Float.valueOf(nextKeyframe.g);
            if (aVar.c == null && (t = nextKeyframe.b) != null) {
                aVar.c = t;
                if (aVar instanceof i) {
                    ((i) aVar).j();
                }
            }
        }
        Keyframe<?> lastKeyframe = (com.airbnb.lottie.value.a) keyframes.get(size - 1);
        if ((lastKeyframe.b == null || lastKeyframe.c == null) && keyframes.size() > 1) {
            keyframes.remove(lastKeyframe);
        }
    }
}
