package com.airbnb.lottie.model.content;

import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.i;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.c;
import com.airbnb.lottie.model.animatable.d;
import com.airbnb.lottie.model.content.r;
import java.util.List;

/* compiled from: GradientStroke */
public class f implements c {
    private final String a;
    private final g b;
    private final c c;
    private final d d;
    private final com.airbnb.lottie.model.animatable.f e;
    private final com.airbnb.lottie.model.animatable.f f;
    private final b g;
    private final r.b h;
    private final r.c i;
    private final float j;
    private final List<b> k;
    @Nullable
    private final b l;
    private final boolean m;

    public f(String name, g gradientType, c gradientColor, d opacity, com.airbnb.lottie.model.animatable.f startPoint, com.airbnb.lottie.model.animatable.f endPoint, b width, r.b capType, r.c joinType, float miterLimit, List<b> lineDashPattern, @Nullable b dashOffset, boolean hidden) {
        this.a = name;
        this.b = gradientType;
        this.c = gradientColor;
        this.d = opacity;
        this.e = startPoint;
        this.f = endPoint;
        this.g = width;
        this.h = capType;
        this.i = joinType;
        this.j = miterLimit;
        this.k = lineDashPattern;
        this.l = dashOffset;
        this.m = hidden;
    }

    public String j() {
        return this.a;
    }

    public g f() {
        return this.b;
    }

    public c e() {
        return this.c;
    }

    public d k() {
        return this.d;
    }

    public com.airbnb.lottie.model.animatable.f l() {
        return this.e;
    }

    public com.airbnb.lottie.model.animatable.f d() {
        return this.f;
    }

    public b m() {
        return this.g;
    }

    public r.b b() {
        return this.h;
    }

    public r.c g() {
        return this.i;
    }

    public List<b> h() {
        return this.k;
    }

    @Nullable
    public b c() {
        return this.l;
    }

    public float i() {
        return this.j;
    }

    public boolean n() {
        return this.m;
    }

    public com.airbnb.lottie.animation.content.c a(e0 drawable, c0 composition, com.airbnb.lottie.model.layer.b layer) {
        return new i(drawable, layer, this);
    }
}
