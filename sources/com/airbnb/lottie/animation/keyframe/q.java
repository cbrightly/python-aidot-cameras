package com.airbnb.lottie.animation.keyframe;

import androidx.annotation.Nullable;
import com.airbnb.lottie.value.a;
import com.airbnb.lottie.value.c;
import java.util.Collections;

/* compiled from: ValueCallbackKeyframeAnimation */
public class q<K, A> extends a<K, A> {
    private final A i;

    public q(c<A> valueCallback) {
        this(valueCallback, (Object) null);
    }

    public q(c<A> valueCallback, @Nullable A valueCallbackValue) {
        super(Collections.emptyList());
        n(valueCallback);
        this.i = valueCallbackValue;
    }

    public void m(float progress) {
        this.d = progress;
    }

    /* access modifiers changed from: package-private */
    public float c() {
        return 1.0f;
    }

    public void k() {
        if (this.e != null) {
            super.k();
        }
    }

    public A h() {
        c<A> cVar = this.e;
        A a = this.i;
        return cVar.b(0.0f, 0.0f, a, a, f(), f(), f());
    }

    /* access modifiers changed from: package-private */
    public A i(a<K> aVar, float keyframeProgress) {
        return h();
    }
}
