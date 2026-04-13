package com.airbnb.lottie.utils;

/* compiled from: MeanCalculator */
public class f {
    private float a;
    private int b;

    public void a(float number) {
        float f = this.a + number;
        this.a = f;
        int i = this.b + 1;
        this.b = i;
        if (i == Integer.MAX_VALUE) {
            this.a = f / 2.0f;
            this.b = i / 2;
        }
    }
}
