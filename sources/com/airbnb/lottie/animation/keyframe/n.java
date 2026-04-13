package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.a;
import com.airbnb.lottie.value.c;
import java.util.Collections;

/* compiled from: SplitDimensionPathKeyframeAnimation */
public class n extends a<PointF, PointF> {
    private final PointF i = new PointF();
    private final PointF j = new PointF();
    private final a<Float, Float> k;
    private final a<Float, Float> l;
    @Nullable
    protected c<Float> m;
    @Nullable
    protected c<Float> n;

    public n(a<Float, Float> xAnimation, a<Float, Float> yAnimation) {
        super(Collections.emptyList());
        this.k = xAnimation;
        this.l = yAnimation;
        m(f());
    }

    public void r(@Nullable c<Float> xValueCallback) {
        c<Float> cVar = this.m;
        if (cVar != null) {
            cVar.c((a<?, ?>) null);
        }
        this.m = xValueCallback;
        if (xValueCallback != null) {
            xValueCallback.c(this);
        }
    }

    public void s(@Nullable c<Float> yValueCallback) {
        c<Float> cVar = this.n;
        if (cVar != null) {
            cVar.c((a<?, ?>) null);
        }
        this.n = yValueCallback;
        if (yValueCallback != null) {
            yValueCallback.c(this);
        }
    }

    public void m(float progress) {
        this.k.m(progress);
        this.l.m(progress);
        this.i.set(this.k.h().floatValue(), this.l.h().floatValue());
        for (int i2 = 0; i2 < this.a.size(); i2++) {
            this.a.get(i2).a();
        }
    }

    /* renamed from: p */
    public PointF h() {
        return i((a<PointF>) null, 0.0f);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: q */
    public PointF i(a<PointF> aVar, float keyframeProgress) {
        Keyframe<Float> yKeyframe;
        Keyframe<Float> xKeyframe;
        Float xCallbackValue = null;
        Float yCallbackValue = null;
        if (!(this.m == null || (xKeyframe = this.k.b()) == null)) {
            float progress = this.k.d();
            Float endFrame = xKeyframe.h;
            c<Float> cVar = this.m;
            float f = xKeyframe.g;
            xCallbackValue = cVar.b(f, endFrame == null ? f : endFrame.floatValue(), (Float) xKeyframe.b, (Float) xKeyframe.c, keyframeProgress, keyframeProgress, progress);
        }
        if (!(this.n == null || (yKeyframe = this.l.b()) == null)) {
            float progress2 = this.l.d();
            Float endFrame2 = yKeyframe.h;
            c<Float> cVar2 = this.n;
            float f2 = yKeyframe.g;
            yCallbackValue = cVar2.b(f2, endFrame2 == null ? f2 : endFrame2.floatValue(), (Float) yKeyframe.b, (Float) yKeyframe.c, keyframeProgress, keyframeProgress, progress2);
        }
        if (xCallbackValue == null) {
            this.j.set(this.i.x, 0.0f);
        } else {
            this.j.set(xCallbackValue.floatValue(), 0.0f);
        }
        if (yCallbackValue == null) {
            PointF pointF = this.j;
            pointF.set(pointF.x, this.i.y);
        } else {
            PointF pointF2 = this.j;
            pointF2.set(pointF2.x, yCallbackValue.floatValue());
        }
        return this.j;
    }
}
