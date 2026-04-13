package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.value.a;

/* compiled from: PathKeyframe */
public class i extends a<PointF> {
    @Nullable
    private Path q;
    private final a<PointF> r;

    public i(c0 composition, a<PointF> keyframe) {
        super(composition, (PointF) keyframe.b, (PointF) keyframe.c, keyframe.d, keyframe.e, keyframe.f, keyframe.g, keyframe.h);
        this.r = keyframe;
        j();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r1 = r5.b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void j() {
        /*
            r5 = this;
            T r0 = r5.c
            if (r0 == 0) goto L_0x001b
            T r1 = r5.b
            if (r1 == 0) goto L_0x001b
            android.graphics.PointF r1 = (android.graphics.PointF) r1
            r2 = r0
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            float r2 = r2.x
            android.graphics.PointF r0 = (android.graphics.PointF) r0
            float r0 = r0.y
            boolean r0 = r1.equals(r2, r0)
            if (r0 == 0) goto L_0x001b
            r0 = 1
            goto L_0x001c
        L_0x001b:
            r0 = 0
        L_0x001c:
            T r1 = r5.b
            if (r1 == 0) goto L_0x0036
            T r2 = r5.c
            if (r2 == 0) goto L_0x0036
            if (r0 != 0) goto L_0x0036
            android.graphics.PointF r1 = (android.graphics.PointF) r1
            android.graphics.PointF r2 = (android.graphics.PointF) r2
            com.airbnb.lottie.value.a<android.graphics.PointF> r3 = r5.r
            android.graphics.PointF r4 = r3.o
            android.graphics.PointF r3 = r3.p
            android.graphics.Path r1 = com.airbnb.lottie.utils.h.d(r1, r2, r4, r3)
            r5.q = r1
        L_0x0036:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.keyframe.i.j():void");
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Path k() {
        return this.q;
    }
}
