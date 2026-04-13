package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.value.a;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: PointKeyframeAnimation */
public class k extends g<PointF> {
    private final PointF i = new PointF();

    public k(List<a<PointF>> keyframes) {
        super(keyframes);
    }

    /* renamed from: p */
    public PointF i(a<PointF> keyframe, float keyframeProgress) {
        return j(keyframe, keyframeProgress, keyframeProgress, keyframeProgress);
    }

    /* access modifiers changed from: protected */
    /* renamed from: q */
    public PointF j(a<PointF> keyframe, float linearKeyframeProgress, float xKeyframeProgress, float yKeyframeProgress) {
        T t;
        T t2 = keyframe.b;
        if (t2 == null || (t = keyframe.c) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        PointF startPoint = (PointF) t2;
        PointF endPoint = (PointF) t;
        c<A> cVar = this.e;
        if (cVar != null) {
            PointF value = (PointF) cVar.b(keyframe.g, keyframe.h.floatValue(), startPoint, endPoint, linearKeyframeProgress, e(), f());
            if (value != null) {
                return value;
            }
        }
        PointF value2 = this.i;
        float f = startPoint.x;
        float f2 = f + ((endPoint.x - f) * xKeyframeProgress);
        float f3 = startPoint.y;
        value2.set(f2, f3 + ((endPoint.y - f3) * yKeyframeProgress));
        return this.i;
    }
}
