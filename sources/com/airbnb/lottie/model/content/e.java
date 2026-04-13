package com.airbnb.lottie.model.content;

import android.graphics.Path;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.h;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.c;
import com.airbnb.lottie.model.animatable.d;
import com.airbnb.lottie.model.animatable.f;

/* compiled from: GradientFill */
public class e implements c {
    private final g a;
    private final Path.FillType b;
    private final c c;
    private final d d;
    private final f e;
    private final f f;
    private final String g;
    @Nullable
    private final b h;
    @Nullable
    private final b i;
    private final boolean j;

    public e(String name, g gradientType, Path.FillType fillType, c gradientColor, d opacity, f startPoint, f endPoint, b highlightLength, b highlightAngle, boolean hidden) {
        this.a = gradientType;
        this.b = fillType;
        this.c = gradientColor;
        this.d = opacity;
        this.e = startPoint;
        this.f = endPoint;
        this.g = name;
        this.h = highlightLength;
        this.i = highlightAngle;
        this.j = hidden;
    }

    public String f() {
        return this.g;
    }

    public g e() {
        return this.a;
    }

    public Path.FillType c() {
        return this.b;
    }

    public c d() {
        return this.c;
    }

    public d g() {
        return this.d;
    }

    public f h() {
        return this.e;
    }

    public f b() {
        return this.f;
    }

    public boolean i() {
        return this.j;
    }

    public com.airbnb.lottie.animation.content.c a(e0 drawable, c0 composition, com.airbnb.lottie.model.layer.b layer) {
        return new h(drawable, composition, layer, this);
    }
}
