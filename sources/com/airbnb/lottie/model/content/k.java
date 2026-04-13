package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.o;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.m;

/* compiled from: RectangleShape */
public class k implements c {
    private final String a;
    private final m<PointF, PointF> b;
    private final m<PointF, PointF> c;
    private final b d;
    private final boolean e;

    public k(String name, m<PointF, PointF> position, m<PointF, PointF> size, b cornerRadius, boolean hidden) {
        this.a = name;
        this.b = position;
        this.c = size;
        this.d = cornerRadius;
        this.e = hidden;
    }

    public String c() {
        return this.a;
    }

    public b b() {
        return this.d;
    }

    public m<PointF, PointF> e() {
        return this.c;
    }

    public m<PointF, PointF> d() {
        return this.b;
    }

    public boolean f() {
        return this.e;
    }

    public c a(e0 drawable, c0 composition, com.airbnb.lottie.model.layer.b layer) {
        return new o(drawable, layer, this);
    }

    public String toString() {
        return "RectangleShape{position=" + this.b + ", size=" + this.c + '}';
    }
}
