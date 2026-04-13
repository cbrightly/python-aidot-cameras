package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.j;
import com.airbnb.lottie.animation.keyframe.k;
import com.airbnb.lottie.value.a;
import java.util.List;

/* compiled from: AnimatablePathValue */
public class e implements m<PointF, PointF> {
    private final List<a<PointF>> a;

    public e(List<a<PointF>> keyframes) {
        this.a = keyframes;
    }

    public List<a<PointF>> k() {
        return this.a;
    }

    public boolean i() {
        return this.a.size() == 1 && this.a.get(0).i();
    }

    public com.airbnb.lottie.animation.keyframe.a<PointF, PointF> j() {
        if (this.a.get(0).i()) {
            return new k(this.a);
        }
        return new j(this.a);
    }
}
