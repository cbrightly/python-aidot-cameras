package com.airbnb.lottie.model.content;

import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.u;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.b;

/* compiled from: ShapeTrimPath */
public class s implements c {
    private final String a;
    private final a b;
    private final b c;
    private final b d;
    private final b e;
    private final boolean f;

    /* compiled from: ShapeTrimPath */
    public enum a {
        SIMULTANEOUSLY,
        INDIVIDUALLY;

        public static a forId(int id) {
            switch (id) {
                case 1:
                    return SIMULTANEOUSLY;
                case 2:
                    return INDIVIDUALLY;
                default:
                    throw new IllegalArgumentException("Unknown trim path type " + id);
            }
        }
    }

    public s(String name, a type, b start, b end, b offset, boolean hidden) {
        this.a = name;
        this.b = type;
        this.c = start;
        this.d = end;
        this.e = offset;
        this.f = hidden;
    }

    public String c() {
        return this.a;
    }

    public a f() {
        return this.b;
    }

    public b b() {
        return this.d;
    }

    public b e() {
        return this.c;
    }

    public b d() {
        return this.e;
    }

    public boolean g() {
        return this.f;
    }

    public c a(e0 drawable, c0 composition, com.airbnb.lottie.model.layer.b layer) {
        return new u(layer, this);
    }

    public String toString() {
        return "Trim Path: {start: " + this.c + ", end: " + this.d + ", offset: " + this.e + "}";
    }
}
