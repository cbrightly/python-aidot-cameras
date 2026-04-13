package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.f;
import com.airbnb.lottie.model.animatable.m;

/* compiled from: CircleShape */
public class b implements c {
    private final String a;
    private final m<PointF, PointF> b;
    private final f c;
    private final boolean d;
    private final boolean e;

    public b(String name, m<PointF, PointF> position, f size, boolean isReversed, boolean hidden) {
        this.a = name;
        this.b = position;
        this.c = size;
        this.d = isReversed;
        this.e = hidden;
    }

    public c a(e0 drawable, c0 composition, com.airbnb.lottie.model.layer.b layer) {
        return new com.airbnb.lottie.animation.content.f(drawable, layer, this);
    }

    public String b() {
        return this.a;
    }

    public m<PointF, PointF> c() {
        return this.b;
    }

    public f d() {
        return this.c;
    }

    public boolean f() {
        return this.d;
    }

    public boolean e() {
        return this.e;
    }
}
