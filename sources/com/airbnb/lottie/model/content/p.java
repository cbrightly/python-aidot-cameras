package com.airbnb.lottie.model.content;

import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.d;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.layer.b;
import java.util.Arrays;
import java.util.List;

/* compiled from: ShapeGroup */
public class p implements c {
    private final String a;
    private final List<c> b;
    private final boolean c;

    public p(String name, List<c> items, boolean hidden) {
        this.a = name;
        this.b = items;
        this.c = hidden;
    }

    public String c() {
        return this.a;
    }

    public List<c> b() {
        return this.b;
    }

    public boolean d() {
        return this.c;
    }

    public c a(e0 drawable, c0 composition, b layer) {
        return new d(drawable, layer, this, composition);
    }

    public String toString() {
        return "ShapeGroup{name='" + this.a + "' Shapes: " + Arrays.toString(this.b.toArray()) + '}';
    }
}
