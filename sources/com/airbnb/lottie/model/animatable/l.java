package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.p;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.content.c;
import com.airbnb.lottie.model.layer.b;

/* compiled from: AnimatableTransform */
public class l implements c {
    @Nullable
    private final e a;
    @Nullable
    private final m<PointF, PointF> b;
    @Nullable
    private final g c;
    @Nullable
    private final b d;
    @Nullable
    private final d e;
    @Nullable
    private final b f;
    @Nullable
    private final b g;
    @Nullable
    private final b h;
    @Nullable
    private final b i;

    public l() {
        this((e) null, (m<PointF, PointF>) null, (g) null, (b) null, (d) null, (b) null, (b) null, (b) null, (b) null);
    }

    public l(@Nullable e anchorPoint, @Nullable m<PointF, PointF> position, @Nullable g scale, @Nullable b rotation, @Nullable d opacity, @Nullable b startOpacity, @Nullable b endOpacity, @Nullable b skew, @Nullable b skewAngle) {
        this.a = anchorPoint;
        this.b = position;
        this.c = scale;
        this.d = rotation;
        this.e = opacity;
        this.h = startOpacity;
        this.i = endOpacity;
        this.f = skew;
        this.g = skewAngle;
    }

    @Nullable
    public e c() {
        return this.a;
    }

    @Nullable
    public m<PointF, PointF> f() {
        return this.b;
    }

    @Nullable
    public g h() {
        return this.c;
    }

    @Nullable
    public b g() {
        return this.d;
    }

    @Nullable
    public d e() {
        return this.e;
    }

    @Nullable
    public b k() {
        return this.h;
    }

    @Nullable
    public b d() {
        return this.i;
    }

    @Nullable
    public b i() {
        return this.f;
    }

    @Nullable
    public b j() {
        return this.g;
    }

    public p b() {
        return new p(this);
    }

    @Nullable
    public com.airbnb.lottie.animation.content.c a(e0 drawable, c0 composition, b layer) {
        return null;
    }
}
