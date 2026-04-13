package com.airbnb.lottie.value;

import androidx.annotation.RestrictTo;

/* compiled from: LottieFrameInfo */
public class b<T> {
    private float a;
    private float b;
    private T c;
    private T d;
    private float e;
    private float f;
    private float g;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public b<T> h(float startFrame, float endFrame, T startValue, T endValue, float linearKeyframeProgress, float interpolatedKeyframeProgress, float overallProgress) {
        this.a = startFrame;
        this.b = endFrame;
        this.c = startValue;
        this.d = endValue;
        this.e = linearKeyframeProgress;
        this.f = interpolatedKeyframeProgress;
        this.g = overallProgress;
        return this;
    }

    public float f() {
        return this.a;
    }

    public float a() {
        return this.b;
    }

    public T g() {
        return this.c;
    }

    public T b() {
        return this.d;
    }

    public float d() {
        return this.e;
    }

    public float c() {
        return this.f;
    }

    public float e() {
        return this.g;
    }
}
