package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.airbnb.lottie.animation.content.c;
import com.airbnb.lottie.animation.content.n;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.animatable.m;

/* compiled from: PolystarShape */
public class j implements c {
    private final String a;
    private final a b;
    private final b c;
    private final m<PointF, PointF> d;
    private final b e;
    private final b f;
    private final b g;
    private final b h;
    private final b i;
    private final boolean j;
    private final boolean k;

    /* compiled from: PolystarShape */
    public enum a {
        STAR(1),
        POLYGON(2);
        
        private final int value;

        private a(int value2) {
            this.value = value2;
        }

        public static a forValue(int value2) {
            for (a type : values()) {
                if (type.value == value2) {
                    return type;
                }
            }
            return null;
        }
    }

    public j(String name, a type, b points, m<PointF, PointF> position, b rotation, b innerRadius, b outerRadius, b innerRoundedness, b outerRoundedness, boolean hidden, boolean isReversed) {
        this.a = name;
        this.b = type;
        this.c = points;
        this.d = position;
        this.e = rotation;
        this.f = innerRadius;
        this.g = outerRadius;
        this.h = innerRoundedness;
        this.i = outerRoundedness;
        this.j = hidden;
        this.k = isReversed;
    }

    public String d() {
        return this.a;
    }

    public a j() {
        return this.b;
    }

    public b g() {
        return this.c;
    }

    public m<PointF, PointF> h() {
        return this.d;
    }

    public b i() {
        return this.e;
    }

    public b b() {
        return this.f;
    }

    public b e() {
        return this.g;
    }

    public b c() {
        return this.h;
    }

    public b f() {
        return this.i;
    }

    public boolean k() {
        return this.j;
    }

    public boolean l() {
        return this.k;
    }

    public c a(e0 drawable, c0 composition, com.airbnb.lottie.model.layer.b layer) {
        return new n(drawable, layer, this);
    }
}
