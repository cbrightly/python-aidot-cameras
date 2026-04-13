package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import com.airbnb.lottie.value.a;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: PathKeyframeAnimation */
public class j extends g<PointF> {
    private final PointF i = new PointF();
    private final float[] j = new float[2];
    private final PathMeasure k = new PathMeasure();
    private i l;

    public j(List<? extends a<PointF>> keyframes) {
        super(keyframes);
    }

    /* renamed from: p */
    public PointF i(a<PointF> keyframe, float keyframeProgress) {
        i pathKeyframe = (i) keyframe;
        Path path = pathKeyframe.k();
        if (path == null) {
            return (PointF) keyframe.b;
        }
        c<A> cVar = this.e;
        if (cVar != null) {
            PointF value = (PointF) cVar.b(pathKeyframe.g, pathKeyframe.h.floatValue(), (PointF) pathKeyframe.b, (PointF) pathKeyframe.c, e(), keyframeProgress, f());
            if (value != null) {
                return value;
            }
        }
        if (this.l != pathKeyframe) {
            this.k.setPath(path, false);
            this.l = pathKeyframe;
        }
        PathMeasure pathMeasure = this.k;
        pathMeasure.getPosTan(pathMeasure.getLength() * keyframeProgress, this.j, (float[]) null);
        PointF pointF = this.i;
        float[] fArr = this.j;
        pointF.set(fArr[0], fArr[1]);
        return this.i;
    }
}
