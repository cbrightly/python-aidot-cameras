package com.airbnb.lottie.value;

/* compiled from: ScaleXY */
public class d {
    private float a;
    private float b;

    public d(float sx, float sy) {
        this.a = sx;
        this.b = sy;
    }

    public d() {
        this(1.0f, 1.0f);
    }

    public float b() {
        return this.a;
    }

    public float c() {
        return this.b;
    }

    public void d(float scaleX, float scaleY) {
        this.a = scaleX;
        this.b = scaleY;
    }

    public boolean a(float scaleX, float scaleY) {
        return this.a == scaleX && this.b == scaleY;
    }

    public String toString() {
        return b() + "x" + c();
    }
}
