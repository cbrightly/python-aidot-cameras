package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.c;
import com.airbnb.lottie.animation.keyframe.f;
import com.airbnb.lottie.animation.keyframe.q;
import com.airbnb.lottie.b0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.animatable.d;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.utils.h;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BaseStrokeContent */
public abstract class a implements a.b, k, e {
    private final PathMeasure a = new PathMeasure();
    private final Path b = new Path();
    private final Path c = new Path();
    private final RectF d = new RectF();
    private final e0 e;
    protected final com.airbnb.lottie.model.layer.b f;
    private final List<b> g = new ArrayList();
    private final float[] h;
    final Paint i;
    private final com.airbnb.lottie.animation.keyframe.a<?, Float> j;
    private final com.airbnb.lottie.animation.keyframe.a<?, Integer> k;
    private final List<com.airbnb.lottie.animation.keyframe.a<?, Float>> l;
    @Nullable
    private final com.airbnb.lottie.animation.keyframe.a<?, Float> m;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<ColorFilter, ColorFilter> n;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Float, Float> o;
    float p;
    @Nullable
    private c q;

    a(e0 lottieDrawable, com.airbnb.lottie.model.layer.b layer, Paint.Cap cap, Paint.Join join, float miterLimit, d opacity, com.airbnb.lottie.model.animatable.b width, List<com.airbnb.lottie.model.animatable.b> dashPattern, com.airbnb.lottie.model.animatable.b offset) {
        com.airbnb.lottie.animation.a aVar = new com.airbnb.lottie.animation.a(1);
        this.i = aVar;
        this.p = 0.0f;
        this.e = lottieDrawable;
        this.f = layer;
        aVar.setStyle(Paint.Style.STROKE);
        aVar.setStrokeCap(cap);
        aVar.setStrokeJoin(join);
        aVar.setStrokeMiter(miterLimit);
        this.k = opacity.j();
        this.j = width.j();
        if (offset == null) {
            this.m = null;
        } else {
            this.m = offset.j();
        }
        this.l = new ArrayList(dashPattern.size());
        this.h = new float[dashPattern.size()];
        for (int i2 = 0; i2 < dashPattern.size(); i2++) {
            this.l.add(dashPattern.get(i2).j());
        }
        layer.g(this.k);
        layer.g(this.j);
        for (int i3 = 0; i3 < this.l.size(); i3++) {
            layer.g(this.l.get(i3));
        }
        com.airbnb.lottie.animation.keyframe.a<?, Float> aVar2 = this.m;
        if (aVar2 != null) {
            layer.g(aVar2);
        }
        this.k.a(this);
        this.j.a(this);
        for (int i4 = 0; i4 < dashPattern.size(); i4++) {
            this.l.get(i4).a(this);
        }
        com.airbnb.lottie.animation.keyframe.a<?, Float> aVar3 = this.m;
        if (aVar3 != null) {
            aVar3.a(this);
        }
        if (layer.u() != null) {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> j2 = layer.u().a().j();
            this.o = j2;
            j2.a(this);
            layer.g(this.o);
        }
        if (layer.w() != null) {
            this.q = new c(this, layer, layer.w());
        }
    }

    public void a() {
        this.e.invalidateSelf();
    }

    /* JADX WARNING: type inference failed for: r2v6, types: [com.airbnb.lottie.animation.content.c] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(java.util.List<com.airbnb.lottie.animation.content.c> r8, java.util.List<com.airbnb.lottie.animation.content.c> r9) {
        /*
            r7 = this;
            r0 = 0
            int r1 = r8.size()
            int r1 = r1 + -1
        L_0x0007:
            if (r1 < 0) goto L_0x0024
            java.lang.Object r2 = r8.get(r1)
            com.airbnb.lottie.animation.content.c r2 = (com.airbnb.lottie.animation.content.c) r2
            boolean r3 = r2 instanceof com.airbnb.lottie.animation.content.u
            if (r3 == 0) goto L_0x0021
            r3 = r2
            com.airbnb.lottie.animation.content.u r3 = (com.airbnb.lottie.animation.content.u) r3
            com.airbnb.lottie.model.content.s$a r3 = r3.j()
            com.airbnb.lottie.model.content.s$a r4 = com.airbnb.lottie.model.content.s.a.INDIVIDUALLY
            if (r3 != r4) goto L_0x0021
            r0 = r2
            com.airbnb.lottie.animation.content.u r0 = (com.airbnb.lottie.animation.content.u) r0
        L_0x0021:
            int r1 = r1 + -1
            goto L_0x0007
        L_0x0024:
            if (r0 == 0) goto L_0x0029
            r0.d(r7)
        L_0x0029:
            r1 = 0
            int r2 = r9.size()
            int r2 = r2 + -1
        L_0x0030:
            if (r2 < 0) goto L_0x0078
            java.lang.Object r3 = r9.get(r2)
            com.airbnb.lottie.animation.content.c r3 = (com.airbnb.lottie.animation.content.c) r3
            boolean r4 = r3 instanceof com.airbnb.lottie.animation.content.u
            r5 = 0
            if (r4 == 0) goto L_0x005f
            r4 = r3
            com.airbnb.lottie.animation.content.u r4 = (com.airbnb.lottie.animation.content.u) r4
            com.airbnb.lottie.model.content.s$a r4 = r4.j()
            com.airbnb.lottie.model.content.s$a r6 = com.airbnb.lottie.model.content.s.a.INDIVIDUALLY
            if (r4 != r6) goto L_0x005f
            if (r1 == 0) goto L_0x004f
            java.util.List<com.airbnb.lottie.animation.content.a$b> r4 = r7.g
            r4.add(r1)
        L_0x004f:
            com.airbnb.lottie.animation.content.a$b r4 = new com.airbnb.lottie.animation.content.a$b
            r6 = r3
            com.airbnb.lottie.animation.content.u r6 = (com.airbnb.lottie.animation.content.u) r6
            r4.<init>(r6)
            r1 = r4
            r4 = r3
            com.airbnb.lottie.animation.content.u r4 = (com.airbnb.lottie.animation.content.u) r4
            r4.d(r7)
            goto L_0x0075
        L_0x005f:
            boolean r4 = r3 instanceof com.airbnb.lottie.animation.content.m
            if (r4 == 0) goto L_0x0075
            if (r1 != 0) goto L_0x006b
            com.airbnb.lottie.animation.content.a$b r4 = new com.airbnb.lottie.animation.content.a$b
            r4.<init>(r0)
            r1 = r4
        L_0x006b:
            java.util.List r4 = r1.a
            r5 = r3
            com.airbnb.lottie.animation.content.m r5 = (com.airbnb.lottie.animation.content.m) r5
            r4.add(r5)
        L_0x0075:
            int r2 = r2 + -1
            goto L_0x0030
        L_0x0078:
            if (r1 == 0) goto L_0x007f
            java.util.List<com.airbnb.lottie.animation.content.a$b> r2 = r7.g
            r2.add(r1)
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.a.b(java.util.List, java.util.List):void");
    }

    public void h(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        b0.a("StrokeContent#draw");
        if (h.h(parentMatrix)) {
            b0.b("StrokeContent#draw");
            return;
        }
        this.i.setAlpha(g.c((int) ((((((float) parentAlpha) / 255.0f) * ((float) ((f) this.k).p())) / 100.0f) * 255.0f), 0, 255));
        this.i.setStrokeWidth(((com.airbnb.lottie.animation.keyframe.d) this.j).p() * h.g(parentMatrix));
        if (this.i.getStrokeWidth() <= 0.0f) {
            b0.b("StrokeContent#draw");
            return;
        }
        g(parentMatrix);
        com.airbnb.lottie.animation.keyframe.a<ColorFilter, ColorFilter> aVar = this.n;
        if (aVar != null) {
            this.i.setColorFilter(aVar.h());
        }
        com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar2 = this.o;
        if (aVar2 != null) {
            float blurRadius = aVar2.h().floatValue();
            if (blurRadius == 0.0f) {
                this.i.setMaskFilter((MaskFilter) null);
            } else if (blurRadius != this.p) {
                this.i.setMaskFilter(this.f.v(blurRadius));
            }
            this.p = blurRadius;
        }
        c cVar = this.q;
        if (cVar != null) {
            cVar.b(this.i);
        }
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            b pathGroup = this.g.get(i2);
            if (pathGroup.b != null) {
                i(canvas, pathGroup, parentMatrix);
            } else {
                b0.a("StrokeContent#buildPath");
                this.b.reset();
                for (int j2 = pathGroup.a.size() - 1; j2 >= 0; j2--) {
                    this.b.addPath(((m) pathGroup.a.get(j2)).getPath(), parentMatrix);
                }
                b0.b("StrokeContent#buildPath");
                b0.a("StrokeContent#drawPath");
                canvas.drawPath(this.b, this.i);
                b0.b("StrokeContent#drawPath");
            }
        }
        b0.b("StrokeContent#draw");
    }

    private void i(Canvas canvas, b pathGroup, Matrix parentMatrix) {
        float animStartValue;
        float startValue;
        float endValue;
        float startValue2;
        Canvas canvas2 = canvas;
        Matrix matrix = parentMatrix;
        b0.a("StrokeContent#applyTrimPath");
        if (pathGroup.b == null) {
            b0.b("StrokeContent#applyTrimPath");
            return;
        }
        this.b.reset();
        for (int j2 = pathGroup.a.size() - 1; j2 >= 0; j2--) {
            this.b.addPath(((m) pathGroup.a.get(j2)).getPath(), matrix);
        }
        float animStartValue2 = pathGroup.b.i().h().floatValue() / 100.0f;
        float animEndValue = pathGroup.b.e().h().floatValue() / 100.0f;
        float animOffsetValue = pathGroup.b.g().h().floatValue() / 360.0f;
        if (animStartValue2 >= 0.01f || animEndValue <= 0.99f) {
            boolean z = false;
            this.a.setPath(this.b, false);
            float totalLength = this.a.getLength();
            while (this.a.nextContour()) {
                totalLength += this.a.getLength();
            }
            float offsetLength = totalLength * animOffsetValue;
            float startLength = (totalLength * animStartValue2) + offsetLength;
            float endLength = Math.min((totalLength * animEndValue) + offsetLength, (startLength + totalLength) - 1.0f);
            float currentLength = 0.0f;
            int j3 = pathGroup.a.size() - 1;
            while (j3 >= 0) {
                this.c.set(((m) pathGroup.a.get(j3)).getPath());
                this.c.transform(matrix);
                this.a.setPath(this.c, z);
                float length = this.a.getLength();
                if (endLength <= totalLength || endLength - totalLength >= currentLength + length || currentLength >= endLength - totalLength) {
                    animStartValue = animStartValue2;
                    if (currentLength + length >= startLength && currentLength <= endLength) {
                        if (currentLength + length > endLength || startLength >= currentLength) {
                            if (startLength < currentLength) {
                                startValue = 0.0f;
                            } else {
                                startValue = (startLength - currentLength) / length;
                            }
                            if (endLength > currentLength + length) {
                                endValue = 1.0f;
                            } else {
                                endValue = (endLength - currentLength) / length;
                            }
                            h.a(this.c, startValue, endValue, 0.0f);
                            canvas2.drawPath(this.c, this.i);
                        } else {
                            canvas2.drawPath(this.c, this.i);
                        }
                    }
                } else {
                    if (startLength > totalLength) {
                        startValue2 = (startLength - totalLength) / length;
                    } else {
                        startValue2 = 0.0f;
                    }
                    animStartValue = animStartValue2;
                    h.a(this.c, startValue2, Math.min((endLength - totalLength) / length, 1.0f), 0.0f);
                    canvas2.drawPath(this.c, this.i);
                }
                currentLength += length;
                j3--;
                matrix = parentMatrix;
                animStartValue2 = animStartValue;
                z = false;
            }
            b0.b("StrokeContent#applyTrimPath");
            return;
        }
        canvas2.drawPath(this.b, this.i);
        b0.b("StrokeContent#applyTrimPath");
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        b0.a("StrokeContent#getBounds");
        this.b.reset();
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            b pathGroup = this.g.get(i2);
            for (int j2 = 0; j2 < pathGroup.a.size(); j2++) {
                this.b.addPath(((m) pathGroup.a.get(j2)).getPath(), parentMatrix);
            }
        }
        this.b.computeBounds(this.d, false);
        float width = ((com.airbnb.lottie.animation.keyframe.d) this.j).p();
        RectF rectF = this.d;
        rectF.set(rectF.left - (width / 2.0f), rectF.top - (width / 2.0f), rectF.right + (width / 2.0f), rectF.bottom + (width / 2.0f));
        outBounds.set(this.d);
        outBounds.set(outBounds.left - 1.0f, outBounds.top - 1.0f, outBounds.right + 1.0f, outBounds.bottom + 1.0f);
        b0.b("StrokeContent#getBounds");
    }

    private void g(Matrix parentMatrix) {
        b0.a("StrokeContent#applyDashPattern");
        if (this.l.isEmpty()) {
            b0.b("StrokeContent#applyDashPattern");
            return;
        }
        float scale = h.g(parentMatrix);
        for (int i2 = 0; i2 < this.l.size(); i2++) {
            this.h[i2] = ((Float) this.l.get(i2).h()).floatValue();
            if (i2 % 2 == 0) {
                float[] fArr = this.h;
                if (fArr[i2] < 1.0f) {
                    fArr[i2] = 1.0f;
                }
            } else {
                float[] fArr2 = this.h;
                if (fArr2[i2] < 0.1f) {
                    fArr2[i2] = 0.1f;
                }
            }
            float[] fArr3 = this.h;
            fArr3[i2] = fArr3[i2] * scale;
        }
        com.airbnb.lottie.animation.keyframe.a<?, Float> aVar = this.m;
        this.i.setPathEffect(new DashPathEffect(this.h, aVar == null ? 0.0f : aVar.h().floatValue() * scale));
        b0.b("StrokeContent#applyDashPattern");
    }

    public void e(e keyPath, int depth, List<e> accumulator, e currentPartialKeyPath) {
        g.k(keyPath, depth, accumulator, currentPartialKeyPath, this);
    }

    @CallSuper
    public <T> void d(T property, @Nullable com.airbnb.lottie.value.c<T> callback) {
        c cVar;
        c cVar2;
        c cVar3;
        c cVar4;
        c cVar5;
        if (property == j0.d) {
            this.k.n(callback);
        } else if (property == j0.s) {
            this.j.n(callback);
        } else if (property == j0.K) {
            com.airbnb.lottie.animation.keyframe.a<ColorFilter, ColorFilter> aVar = this.n;
            if (aVar != null) {
                this.f.G(aVar);
            }
            if (callback == null) {
                this.n = null;
                return;
            }
            q qVar = new q(callback);
            this.n = qVar;
            qVar.a(this);
            this.f.g(this.n);
        } else if (property == j0.j) {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar2 = this.o;
            if (aVar2 != null) {
                aVar2.n(callback);
                return;
            }
            q qVar2 = new q(callback);
            this.o = qVar2;
            qVar2.a(this);
            this.f.g(this.o);
        } else if (property == j0.e && (cVar5 = this.q) != null) {
            cVar5.c(callback);
        } else if (property == j0.G && (cVar4 = this.q) != null) {
            cVar4.f(callback);
        } else if (property == j0.H && (cVar3 = this.q) != null) {
            cVar3.d(callback);
        } else if (property == j0.I && (cVar2 = this.q) != null) {
            cVar2.e(callback);
        } else if (property == j0.J && (cVar = this.q) != null) {
            cVar.g(callback);
        }
    }

    /* compiled from: BaseStrokeContent */
    public static final class b {
        /* access modifiers changed from: private */
        public final List<m> a;
        /* access modifiers changed from: private */
        @Nullable
        public final u b;

        private b(@Nullable u trimPath) {
            this.a = new ArrayList();
            this.b = trimPath;
        }
    }
}
