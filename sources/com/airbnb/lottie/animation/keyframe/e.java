package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.content.d;
import com.airbnb.lottie.value.a;
import java.util.List;

/* compiled from: GradientColorKeyframeAnimation */
public class e extends g<d> {
    private final d i;

    public e(List<a<d>> keyframes) {
        super(keyframes);
        int size = 0;
        d startValue = (d) keyframes.get(0).b;
        size = startValue != null ? startValue.e() : size;
        this.i = new d(new float[size], new int[size]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: p */
    public d i(a<d> keyframe, float keyframeProgress) {
        this.i.f((d) keyframe.b, (d) keyframe.c, keyframeProgress);
        return this.i;
    }
}
