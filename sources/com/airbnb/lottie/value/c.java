package com.airbnb.lottie.value;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.airbnb.lottie.animation.keyframe.a;

/* compiled from: LottieValueCallback */
public class c<T> {
    private final b<T> a = new b<>();
    @Nullable
    private a<?, ?> b;
    @Nullable
    protected T c = null;

    public c() {
    }

    public c(@Nullable T staticValue) {
        this.c = staticValue;
    }

    @Nullable
    public T a(b<T> bVar) {
        return this.c;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final T b(float startFrame, float endFrame, T startValue, T endValue, float linearKeyframeProgress, float interpolatedKeyframeProgress, float overallProgress) {
        return a(this.a.h(startFrame, endFrame, startValue, endValue, linearKeyframeProgress, interpolatedKeyframeProgress, overallProgress));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final void c(@Nullable a<?, ?> animation) {
        this.b = animation;
    }
}
