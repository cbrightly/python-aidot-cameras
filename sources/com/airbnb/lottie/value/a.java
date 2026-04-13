package com.airbnb.lottie.value;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import com.airbnb.lottie.c0;

/* compiled from: Keyframe */
public class a<T> {
    @Nullable
    private final c0 a;
    @Nullable
    public final T b;
    @Nullable
    public T c;
    @Nullable
    public final Interpolator d;
    @Nullable
    public final Interpolator e;
    @Nullable
    public final Interpolator f;
    public final float g;
    @Nullable
    public Float h;
    private float i;
    private float j;
    private int k;
    private int l;
    private float m;
    private float n;
    public PointF o;
    public PointF p;

    public a(c0 composition, @Nullable T startValue, @Nullable T endValue, @Nullable Interpolator interpolator, float startFrame, @Nullable Float endFrame) {
        this.i = -3987645.8f;
        this.j = -3987645.8f;
        this.k = 784923401;
        this.l = 784923401;
        this.m = Float.MIN_VALUE;
        this.n = Float.MIN_VALUE;
        this.o = null;
        this.p = null;
        this.a = composition;
        this.b = startValue;
        this.c = endValue;
        this.d = interpolator;
        this.e = null;
        this.f = null;
        this.g = startFrame;
        this.h = endFrame;
    }

    public a(c0 composition, @Nullable T startValue, @Nullable T endValue, @Nullable Interpolator xInterpolator, @Nullable Interpolator yInterpolator, float startFrame, @Nullable Float endFrame) {
        this.i = -3987645.8f;
        this.j = -3987645.8f;
        this.k = 784923401;
        this.l = 784923401;
        this.m = Float.MIN_VALUE;
        this.n = Float.MIN_VALUE;
        this.o = null;
        this.p = null;
        this.a = composition;
        this.b = startValue;
        this.c = endValue;
        this.d = null;
        this.e = xInterpolator;
        this.f = yInterpolator;
        this.g = startFrame;
        this.h = endFrame;
    }

    protected a(c0 composition, @Nullable T startValue, @Nullable T endValue, @Nullable Interpolator interpolator, @Nullable Interpolator xInterpolator, @Nullable Interpolator yInterpolator, float startFrame, @Nullable Float endFrame) {
        this.i = -3987645.8f;
        this.j = -3987645.8f;
        this.k = 784923401;
        this.l = 784923401;
        this.m = Float.MIN_VALUE;
        this.n = Float.MIN_VALUE;
        this.o = null;
        this.p = null;
        this.a = composition;
        this.b = startValue;
        this.c = endValue;
        this.d = interpolator;
        this.e = xInterpolator;
        this.f = yInterpolator;
        this.g = startFrame;
        this.h = endFrame;
    }

    public a(T value) {
        this.i = -3987645.8f;
        this.j = -3987645.8f;
        this.k = 784923401;
        this.l = 784923401;
        this.m = Float.MIN_VALUE;
        this.n = Float.MIN_VALUE;
        this.o = null;
        this.p = null;
        this.a = null;
        this.b = value;
        this.c = value;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = Float.MIN_VALUE;
        this.h = Float.valueOf(Float.MAX_VALUE);
    }

    private a(T startValue, T endValue) {
        this.i = -3987645.8f;
        this.j = -3987645.8f;
        this.k = 784923401;
        this.l = 784923401;
        this.m = Float.MIN_VALUE;
        this.n = Float.MIN_VALUE;
        this.o = null;
        this.p = null;
        this.a = null;
        this.b = startValue;
        this.c = endValue;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = Float.MIN_VALUE;
        this.h = Float.valueOf(Float.MAX_VALUE);
    }

    public a<T> b(T startValue, T endValue) {
        return new a<>(startValue, endValue);
    }

    public float f() {
        c0 c0Var = this.a;
        if (c0Var == null) {
            return 0.0f;
        }
        if (this.m == Float.MIN_VALUE) {
            this.m = (this.g - c0Var.p()) / this.a.e();
        }
        return this.m;
    }

    public float c() {
        if (this.a == null) {
            return 1.0f;
        }
        if (this.n == Float.MIN_VALUE) {
            if (this.h == null) {
                this.n = 1.0f;
            } else {
                this.n = f() + ((this.h.floatValue() - this.g) / this.a.e());
            }
        }
        return this.n;
    }

    public boolean i() {
        return this.d == null && this.e == null && this.f == null;
    }

    public boolean a(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        return progress >= f() && progress < c();
    }

    public float g() {
        if (this.i == -3987645.8f) {
            this.i = ((Float) this.b).floatValue();
        }
        return this.i;
    }

    public float d() {
        if (this.j == -3987645.8f) {
            this.j = ((Float) this.c).floatValue();
        }
        return this.j;
    }

    public int h() {
        if (this.k == 784923401) {
            this.k = ((Integer) this.b).intValue();
        }
        return this.k;
    }

    public int e() {
        if (this.l == 784923401) {
            this.l = ((Integer) this.c).intValue();
        }
        return this.l;
    }

    public String toString() {
        return "Keyframe{startValue=" + this.b + ", endValue=" + this.c + ", startFrame=" + this.g + ", endFrame=" + this.h + ", interpolator=" + this.d + '}';
    }
}
