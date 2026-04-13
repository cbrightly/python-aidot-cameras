package com.airbnb.lottie.model.content;

import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.p;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.b;

/* compiled from: Repeater */
public class l implements c {
    private final String a;
    private final b b;
    private final b c;
    private final com.airbnb.lottie.model.animatable.l d;
    private final boolean e;

    public l(String name, b copies, b offset, com.airbnb.lottie.model.animatable.l transform, boolean hidden) {
        this.a = name;
        this.b = copies;
        this.c = offset;
        this.d = transform;
        this.e = hidden;
    }

    public String c() {
        return this.a;
    }

    public b b() {
        return this.b;
    }

    public b d() {
        return this.c;
    }

    public com.airbnb.lottie.model.animatable.l e() {
        return this.d;
    }

    public boolean f() {
        return this.e;
    }

    @Nullable
    public c a(e0 drawable, c0 composition, com.airbnb.lottie.model.layer.b layer) {
        return new p(drawable, layer, this);
    }
}
