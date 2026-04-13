package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.n;
import com.airbnb.lottie.value.a;
import java.util.List;

/* compiled from: AnimatableSplitDimensionPathValue */
public class i implements m<PointF, PointF> {
    private final b a;
    private final b b;

    public i(b animatableXDimension, b animatableYDimension) {
        this.a = animatableXDimension;
        this.b = animatableYDimension;
    }

    public List<a<PointF>> k() {
        throw new UnsupportedOperationException("Cannot call getKeyframes on AnimatableSplitDimensionPathValue.");
    }

    public boolean i() {
        return this.a.i() && this.b.i();
    }

    public com.airbnb.lottie.animation.keyframe.a<PointF, PointF> j() {
        return new n(this.a.j(), this.b.j());
    }
}
