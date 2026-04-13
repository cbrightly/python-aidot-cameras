package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.value.a;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: ColorKeyframeAnimation */
public class b extends g<Integer> {
    public b(List<a<Integer>> keyframes) {
        super(keyframes);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: r */
    public Integer i(a<Integer> keyframe, float keyframeProgress) {
        return Integer.valueOf(q(keyframe, keyframeProgress));
    }

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
        return com.airbnb.lottie.utils.b.c(g.b(keyframeProgress, 0.0f, 1.0f), ((Integer) keyframe.b).intValue(), ((Integer) keyframe.c).intValue());
    }

    public int p() {
        return q(b(), d());
    }
}
