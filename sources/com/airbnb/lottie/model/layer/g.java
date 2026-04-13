package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.content.d;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.model.content.a;
import com.airbnb.lottie.model.content.p;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.parser.j;
import java.util.Collections;
import java.util.List;

/* compiled from: ShapeLayer */
public class g extends b {
    private final d D;
    private final c E;

    g(e0 lottieDrawable, e layerModel, c compositionLayer, c0 composition) {
        super(lottieDrawable, layerModel);
        this.E = compositionLayer;
        d dVar = new d(lottieDrawable, this, new p("__container", layerModel.n(), false), composition);
        this.D = dVar;
        dVar.b(Collections.emptyList(), Collections.emptyList());
    }

    /* access modifiers changed from: package-private */
    public void s(@NonNull Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        this.D.h(canvas, parentMatrix, parentAlpha);
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        super.f(outBounds, parentMatrix, applyParents);
        this.D.f(outBounds, this.o, applyParents);
    }

    @Nullable
    public a u() {
        a layerBlur = super.u();
        if (layerBlur != null) {
            return layerBlur;
        }
        return this.E.u();
    }

    @Nullable
    public j w() {
        j layerDropShadow = super.w();
        if (layerDropShadow != null) {
            return layerDropShadow;
        }
        return this.E.w();
    }

    /* access modifiers changed from: protected */
    public void H(e keyPath, int depth, List<e> accumulator, e currentPartialKeyPath) {
        this.D.e(keyPath, depth, accumulator, currentPartialKeyPath);
    }
}
