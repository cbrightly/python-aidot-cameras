package com.airbnb.lottie.model.content;

import android.graphics.Paint;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.t;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.animatable.d;
import java.util.List;

/* compiled from: ShapeStroke */
public class r implements c {
    private final String a;
    @Nullable
    private final com.airbnb.lottie.model.animatable.b b;
    private final List<com.airbnb.lottie.model.animatable.b> c;
    private final com.airbnb.lottie.model.animatable.a d;
    private final d e;
    private final com.airbnb.lottie.model.animatable.b f;
    private final b g;
    private final c h;
    private final float i;
    private final boolean j;

    /* compiled from: ShapeStroke */
    public enum b {
        BUTT,
        ROUND,
        UNKNOWN;

        public Paint.Cap toPaintCap() {
            switch (a.a[ordinal()]) {
                case 1:
                    return Paint.Cap.BUTT;
                case 2:
                    return Paint.Cap.ROUND;
                default:
                    return Paint.Cap.SQUARE;
            }
        }
    }

    /* compiled from: ShapeStroke */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[c.values().length];
            b = iArr;
            try {
                iArr[c.BEVEL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                b[c.MITER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                b[c.ROUND.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[b.values().length];
            a = iArr2;
            try {
                iArr2[b.BUTT.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[b.ROUND.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[b.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    /* compiled from: ShapeStroke */
    public enum c {
        MITER,
        ROUND,
        BEVEL;

        public Paint.Join toPaintJoin() {
            switch (a.b[ordinal()]) {
                case 1:
                    return Paint.Join.BEVEL;
                case 2:
                    return Paint.Join.MITER;
                case 3:
                    return Paint.Join.ROUND;
                default:
                    return null;
            }
        }
    }

    public r(String name, @Nullable com.airbnb.lottie.model.animatable.b offset, List<com.airbnb.lottie.model.animatable.b> lineDashPattern, com.airbnb.lottie.model.animatable.a color, d opacity, com.airbnb.lottie.model.animatable.b width, b capType, c joinType, float miterLimit, boolean hidden) {
        this.a = name;
        this.b = offset;
        this.c = lineDashPattern;
        this.d = color;
        this.e = opacity;
        this.f = width;
        this.g = capType;
        this.h = joinType;
        this.i = miterLimit;
        this.j = hidden;
    }

    public com.airbnb.lottie.animation.content.c a(e0 drawable, c0 composition, com.airbnb.lottie.model.layer.b layer) {
        return new t(drawable, layer, this);
    }

    public String h() {
        return this.a;
    }

    public com.airbnb.lottie.model.animatable.a c() {
        return this.d;
    }

    public d i() {
        return this.e;
    }

    public com.airbnb.lottie.model.animatable.b j() {
        return this.f;
    }

    public List<com.airbnb.lottie.model.animatable.b> f() {
        return this.c;
    }

    public com.airbnb.lottie.model.animatable.b d() {
        return this.b;
    }

    public b b() {
        return this.g;
    }

    public c e() {
        return this.h;
    }

    public float g() {
        return this.i;
    }

    public boolean k() {
        return this.j;
    }
}
