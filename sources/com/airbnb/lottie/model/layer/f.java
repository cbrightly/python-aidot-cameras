package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import com.airbnb.lottie.e0;

/* compiled from: NullLayer */
public class f extends b {
    f(e0 lottieDrawable, e layerModel) {
        super(lottieDrawable, layerModel);
    }

    /* access modifiers changed from: package-private */
    public void s(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        super.f(outBounds, parentMatrix, applyParents);
        outBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
    }
}
