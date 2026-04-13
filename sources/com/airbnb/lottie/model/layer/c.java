package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.animation.keyframe.q;
import com.airbnb.lottie.b0;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.model.layer.e;
import com.airbnb.lottie.utils.h;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CompositionLayer */
public class c extends b {
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Float, Float> D;
    private final List<b> E = new ArrayList();
    private final RectF F = new RectF();
    private final RectF G = new RectF();
    private final Paint H = new Paint();
    private boolean I = true;

    public c(e0 lottieDrawable, e layerModel, List<e> layerModels, c0 composition) {
        super(lottieDrawable, layerModel);
        b parentLayer;
        b timeRemapping = layerModel.u();
        if (timeRemapping != null) {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> j = timeRemapping.j();
            this.D = j;
            g(j);
            this.D.a(this);
        } else {
            this.D = null;
        }
        LongSparseArray<BaseLayer> layerMap = new LongSparseArray<>(composition.k().size());
        b mattedLayer = null;
        for (int i = layerModels.size() - 1; i >= 0; i--) {
            e lm = layerModels.get(i);
            b layer = b.t(this, lm, lottieDrawable, composition);
            if (layer != null) {
                layerMap.put(layer.x().d(), layer);
                if (mattedLayer == null) {
                    this.E.add(0, layer);
                    switch (a.a[lm.h().ordinal()]) {
                        case 1:
                        case 2:
                            mattedLayer = layer;
                            break;
                    }
                } else {
                    mattedLayer.I(layer);
                    mattedLayer = null;
                }
            }
        }
        for (int i2 = 0; i2 < layerMap.size(); i2++) {
            b layerView = (b) layerMap.get(layerMap.keyAt(i2));
            if (!(layerView == null || (parentLayer = (b) layerMap.get(layerView.x().j())) == null)) {
                layerView.K(parentLayer);
            }
        }
    }

    /* compiled from: CompositionLayer */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[e.b.values().length];
            a = iArr;
            try {
                iArr[e.b.ADD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[e.b.INVERT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public void O(boolean clipToCompositionBounds) {
        this.I = clipToCompositionBounds;
    }

    public void J(boolean outline) {
        super.J(outline);
        for (b layer : this.E) {
            layer.J(outline);
        }
    }

    /* access modifiers changed from: package-private */
    public void s(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        b0.a("CompositionLayer#draw");
        this.G.set(0.0f, 0.0f, this.q.l(), this.q.k());
        parentMatrix.mapRect(this.G);
        int childAlpha = 255;
        boolean isDrawingWithOffScreen = this.p.O() && this.E.size() > 1 && parentAlpha != 255;
        if (isDrawingWithOffScreen) {
            this.H.setAlpha(parentAlpha);
            h.m(canvas, this.G, this.H);
        } else {
            canvas.save();
        }
        if (!isDrawingWithOffScreen) {
            childAlpha = parentAlpha;
        }
        for (int i = this.E.size() - 1; i >= 0; i--) {
            boolean nonEmptyClip = true;
            if (!(!this.I && "__container".equals(this.q.i())) && !this.G.isEmpty()) {
                nonEmptyClip = canvas.clipRect(this.G);
            }
            if (nonEmptyClip) {
                this.E.get(i).h(canvas, parentMatrix, childAlpha);
            }
        }
        canvas.restore();
        b0.b("CompositionLayer#draw");
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        super.f(outBounds, parentMatrix, applyParents);
        for (int i = this.E.size() - 1; i >= 0; i--) {
            this.F.set(0.0f, 0.0f, 0.0f, 0.0f);
            this.E.get(i).f(this.F, this.o, true);
            outBounds.union(this.F);
        }
    }

    public void L(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        super.L(progress);
        if (this.D != null) {
            progress = ((this.D.h().floatValue() * this.q.b().i()) - this.q.b().p()) / (this.p.t().e() + 0.01f);
        }
        if (this.D == null) {
            progress -= this.q.r();
        }
        if (this.q.v() != 0.0f && !"__container".equals(this.q.i())) {
            progress /= this.q.v();
        }
        for (int i = this.E.size() - 1; i >= 0; i--) {
            this.E.get(i).L(progress);
        }
    }

    /* access modifiers changed from: protected */
    public void H(com.airbnb.lottie.model.e keyPath, int depth, List<com.airbnb.lottie.model.e> accumulator, com.airbnb.lottie.model.e currentPartialKeyPath) {
        for (int i = 0; i < this.E.size(); i++) {
            this.E.get(i).e(keyPath, depth, accumulator, currentPartialKeyPath);
        }
    }

    public <T> void d(T property, @Nullable com.airbnb.lottie.value.c<T> callback) {
        super.d(property, callback);
        if (property != j0.E) {
            return;
        }
        if (callback == null) {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar = this.D;
            if (aVar != null) {
                aVar.n((com.airbnb.lottie.value.c<Float>) null);
                return;
            }
            return;
        }
        q qVar = new q(callback);
        this.D = qVar;
        qVar.a(this);
        g(this.D);
    }
}
