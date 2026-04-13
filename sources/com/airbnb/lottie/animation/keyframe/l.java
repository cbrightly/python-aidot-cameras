package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.value.a;
import com.airbnb.lottie.value.c;
import com.airbnb.lottie.value.d;
import java.util.List;

/* compiled from: ScaleKeyframeAnimation */
public class l extends g<d> {
    private final d i = new d();

    public l(List<a<d>> keyframes) {
        super(keyframes);
    }

    /* renamed from: p */
    public d i(a<d> keyframe, float keyframeProgress) {
        T t;
        T t2 = keyframe.b;
        if (t2 == null || (t = keyframe.c) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        d startTransform = (d) t2;
        d endTransform = (d) t;
        c<A> cVar = this.e;
        if (cVar != null) {
            d value = (d) cVar.b(keyframe.g, keyframe.h.floatValue(), startTransform, endTransform, keyframeProgress, e(), f());
            if (value != null) {
                return value;
            }
        }
        this.i.d(g.i(startTransform.b(), endTransform.b(), keyframeProgress), g.i(startTransform.c(), endTransform.c(), keyframeProgress));
        return this.i;
    }
}
