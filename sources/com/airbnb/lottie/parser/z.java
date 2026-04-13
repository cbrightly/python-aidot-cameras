package com.airbnb.lottie.parser;

import com.airbnb.lottie.animation.keyframe.i;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.utils.h;

/* compiled from: PathKeyframeParser */
public class z {
    static i a(a reader, c0 composition) {
        return new i(composition, t.c(reader, composition, h.e(), a0.a, reader.u() == a.b.BEGIN_OBJECT, false));
    }
}
