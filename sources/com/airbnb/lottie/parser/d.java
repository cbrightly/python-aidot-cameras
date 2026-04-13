package com.airbnb.lottie.parser;

import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.c;
import com.airbnb.lottie.model.animatable.f;
import com.airbnb.lottie.model.animatable.g;
import com.airbnb.lottie.model.animatable.j;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.utils.h;
import java.util.List;

/* compiled from: AnimatableValueParser */
public class d {
    public static b e(a reader, c0 composition) {
        return f(reader, composition, true);
    }

    public static b f(a reader, c0 composition, boolean isDp) {
        return new b(a(reader, isDp ? h.e() : 1.0f, composition, l.a));
    }

    static com.airbnb.lottie.model.animatable.d h(a reader, c0 composition) {
        return new com.airbnb.lottie.model.animatable.d(b(reader, composition, r.a));
    }

    static f i(a reader, c0 composition) {
        return new f(u.a(reader, composition, h.e(), b0.a, true));
    }

    static g j(a reader, c0 composition) {
        return new g(b(reader, composition, g0.a));
    }

    static com.airbnb.lottie.model.animatable.h k(a reader, c0 composition) {
        return new com.airbnb.lottie.model.animatable.h(a(reader, h.e(), composition, h0.a));
    }

    static j d(a reader, c0 composition) {
        return new j(a(reader, h.e(), composition, i.a));
    }

    static com.airbnb.lottie.model.animatable.a c(a reader, c0 composition) {
        return new com.airbnb.lottie.model.animatable.a(b(reader, composition, g.a));
    }

    static c g(a reader, c0 composition, int points) {
        return new c(b(reader, composition, new o(points)));
    }

    private static <T> List<com.airbnb.lottie.value.a<T>> b(a reader, c0 composition, n0<T> valueParser) {
        return u.a(reader, composition, 1.0f, valueParser, false);
    }

    private static <T> List<com.airbnb.lottie.value.a<T>> a(a reader, float scale, c0 composition, n0<T> valueParser) {
        return u.a(reader, composition, scale, valueParser, false);
    }
}
