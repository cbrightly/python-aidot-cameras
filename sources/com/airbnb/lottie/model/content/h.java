package com.airbnb.lottie.model.content;

import com.airbnb.lottie.model.animatable.d;

/* compiled from: Mask */
public class h {
    private final a a;
    private final com.airbnb.lottie.model.animatable.h b;
    private final d c;
    private final boolean d;

    /* compiled from: Mask */
    public enum a {
        MASK_MODE_ADD,
        MASK_MODE_SUBTRACT,
        MASK_MODE_INTERSECT,
        MASK_MODE_NONE
    }

    public h(a maskMode, com.airbnb.lottie.model.animatable.h maskPath, d opacity, boolean inverted) {
        this.a = maskMode;
        this.b = maskPath;
        this.c = opacity;
        this.d = inverted;
    }

    public a a() {
        return this.a;
    }

    public com.airbnb.lottie.model.animatable.h b() {
        return this.b;
    }

    public d c() {
        return this.c;
    }

    public boolean d() {
        return this.d;
    }
}
