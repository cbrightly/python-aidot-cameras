package com.airbnb.lottie.model.content;

import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.r;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.h;
import com.airbnb.lottie.model.layer.b;

/* compiled from: ShapePath */
public class q implements c {
    private final String a;
    private final int b;
    private final h c;
    private final boolean d;

    public q(String name, int index, h shapePath, boolean hidden) {
        this.a = name;
        this.b = index;
        this.c = shapePath;
        this.d = hidden;
    }

    public String b() {
        return this.a;
    }

    public h c() {
        return this.c;
    }

    public c a(e0 drawable, c0 composition, b layer) {
        return new r(drawable, layer, this);
    }

    public boolean d() {
        return this.d;
    }

    public String toString() {
        return "ShapePath{name=" + this.a + ", index=" + this.b + '}';
    }
}
