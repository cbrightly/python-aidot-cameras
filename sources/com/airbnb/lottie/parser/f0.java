package com.airbnb.lottie.parser;

import androidx.annotation.Nullable;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.m;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: RoundedCornersParser */
public class f0 {
    private static final a.C0011a a = a.C0011a.a("nm", "r", "hd");

    @Nullable
    static m a(a reader, c0 composition) {
        String name = null;
        AnimatableValue<Float, Float> cornerRadius = null;
        boolean hidden = false;
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    name = reader.s();
                    break;
                case 1:
                    cornerRadius = d.f(reader, composition, true);
                    break;
                case 2:
                    hidden = reader.m();
                    break;
                default:
                    reader.E();
                    break;
            }
        }
        if (hidden) {
            return null;
        }
        return new m(name, cornerRadius);
    }
}
