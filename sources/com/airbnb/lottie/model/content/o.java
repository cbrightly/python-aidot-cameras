package com.airbnb.lottie.model.content;

import android.graphics.Path;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.g;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.a;
import com.airbnb.lottie.model.animatable.d;
import com.airbnb.lottie.model.layer.b;

/* compiled from: ShapeFill */
public class o implements c {
    private final boolean a;
    private final Path.FillType b;
    private final String c;
    @Nullable
    private final a d;
    @Nullable
    private final d e;
    private final boolean f;

    public o(String name, boolean fillEnabled, Path.FillType fillType, @Nullable a color, @Nullable d opacity, boolean hidden) {
        this.c = name;
        this.a = fillEnabled;
        this.b = fillType;
        this.d = color;
        this.e = opacity;
        this.f = hidden;
    }

    public String d() {
        return this.c;
    }

    @Nullable
    public a b() {
        return this.d;
    }

    @Nullable
    public d e() {
        return this.e;
    }

    public Path.FillType c() {
        return this.b;
    }

    public boolean f() {
        return this.f;
    }

    public c a(e0 drawable, c0 composition, b layer) {
        return new g(drawable, layer, this);
    }

    public String toString() {
        return "ShapeFill{color=, fillEnabled=" + this.a + '}';
    }
}
