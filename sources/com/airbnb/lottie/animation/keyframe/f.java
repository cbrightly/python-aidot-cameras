package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.value.a;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: IntegerKeyframeAnimation */
public class f extends g<Integer> {
    public f(List<a<Integer>> keyframes) {
        super(keyframes);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: r */
    public Integer i(a<Integer> keyframe, float keyframeProgress) {
        return Integer.valueOf(q(keyframe, keyframeProgress));
    }

    /* access modifiers changed from: package-private */
    public int q(a<Integer> keyframe, float keyframeProgress) {
        if (keyframe.b == null || keyframe.c == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        c<A> cVar = this.e;
        if (cVar != null) {
            Integer value = (Integer) cVar.b(keyframe.g, keyframe.h.floatValue(), (Integer) keyframe.b, (Integer) keyframe.c, keyframeProgress, e(), f());
            if (value != null) {
                return value.intValue();
            }
        }
        return g.j(keyframe.h(), keyframe.e(), keyframeProgress);
    }

    public int p() {
        return q(b(), d());
    }
}
