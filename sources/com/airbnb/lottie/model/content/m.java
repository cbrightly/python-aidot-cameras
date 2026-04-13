package com.airbnb.lottie.model.content;

import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.q;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.layer.b;

/* compiled from: RoundedCorners */
public class m implements c {
    private final String a;
    private final com.airbnb.lottie.model.animatable.m<Float, Float> b;

    public m(String name, com.airbnb.lottie.model.animatable.m<Float, Float> cornerRadius) {
        this.a = name;
        this.b = cornerRadius;
    }

    public String c() {
        return this.a;
    }

    public com.airbnb.lottie.model.animatable.m<Float, Float> b() {
        return this.b;
    }

    @Nullable
    public c a(e0 drawable, c0 composition, b layer) {
        return new q(drawable, layer, this);
    }
}
