package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.value.a;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: FloatKeyframeAnimation */
public class d extends g<Float> {
    public d(List<a<Float>> keyframes) {
        super(keyframes);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: r */
    public Float i(a<Float> keyframe, float keyframeProgress) {
        return Float.valueOf(q(keyframe, keyframeProgress));
    }

    /* access modifiers changed from: package-private */
    public float q(a<Float> keyframe, float keyframeProgress) {
        if (keyframe.b == null || keyframe.c == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        c<A> cVar = this.e;
        if (cVar != null) {
            Float value = (Float) cVar.b(keyframe.g, keyframe.h.floatValue(), (Float) keyframe.b, (Float) keyframe.c, keyframeProgress, e(), f());
            if (value != null) {
                return value.floatValue();
            }
        }
        return g.i(keyframe.g(), keyframe.d(), keyframeProgress);
    }

    public float p() {
        return q(b(), d());
    }
}
