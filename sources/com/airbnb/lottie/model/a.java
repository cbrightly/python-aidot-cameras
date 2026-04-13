package com.airbnb.lottie.model;

import android.annotation.SuppressLint;
import android.graphics.PointF;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: CubicCurveData */
public class a {
    private final PointF a;
    private final PointF b;
    private final PointF c;

    public a() {
        this.a = new PointF();
        this.b = new PointF();
        this.c = new PointF();
    }

    public a(PointF controlPoint1, PointF controlPoint2, PointF vertex) {
        this.a = controlPoint1;
        this.b = controlPoint2;
        this.c = vertex;
    }

    public void d(float x, float y) {
        this.a.set(x, y);
    }

    public PointF a() {
        return this.a;
    }

    public void e(float x, float y) {
        this.b.set(x, y);
    }

    public PointF b() {
        return this.b;
    }

    public void f(float x, float y) {
        this.c.set(x, y);
    }

    public PointF c() {
        return this.c;
    }

    @SuppressLint({"DefaultLocale"})
    @NonNull
    public String toString() {
        return String.format("v=%.2f,%.2f cp1=%.2f,%.2f cp2=%.2f,%.2f", new Object[]{Float.valueOf(this.c.x), Float.valueOf(this.c.y), Float.valueOf(this.a.x), Float.valueOf(this.a.y), Float.valueOf(this.b.x), Float.valueOf(this.b.y)});
    }
}
