package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.c;
import com.airbnb.lottie.animation.keyframe.q;
import com.airbnb.lottie.b0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.content.o;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.model.layer.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FillContent */
public class g implements e, a.b, k {
    private final Path a;
    private final Paint b = new com.airbnb.lottie.animation.a(1);
    private final b c;
    private final String d;
    private final boolean e;
    private final List<m> f = new ArrayList();
    private final a<Integer, Integer> g;
    private final a<Integer, Integer> h;
    @Nullable
    private a<ColorFilter, ColorFilter> i;
    private final e0 j;
    @Nullable
    private a<Float, Float> k;
    float l;
    @Nullable
    private c m;

    public g(e0 lottieDrawable, b layer, o fill) {
        Path path = new Path();
        this.a = path;
        this.c = layer;
        this.d = fill.d();
        this.e = fill.f();
        this.j = lottieDrawable;
        if (layer.u() != null) {
            a<Float, Float> j2 = layer.u().a().j();
            this.k = j2;
            j2.a(this);
            layer.g(this.k);
        }
        if (layer.w() != null) {
            this.m = new c(this, layer, layer.w());
        }
        if (fill.b() == null || fill.e() == null) {
            this.g = null;
            this.h = null;
            return;
        }
        path.setFillType(fill.c());
        a<Integer, Integer> j3 = fill.b().j();
        this.g = j3;
        j3.a(this);
        layer.g(j3);
        a<Integer, Integer> j4 = fill.e().j();
        this.h = j4;
        j4.a(this);
        layer.g(j4);
    }

    public void a() {
        this.j.invalidateSelf();
    }

    public void b(List<c> list, List<c> contentsAfter) {
        for (int i2 = 0; i2 < contentsAfter.size(); i2++) {
            c content = contentsAfter.get(i2);
            if (content instanceof m) {
                this.f.add((m) content);
            }
        }
    }

    public String getName() {
        return this.d;
    }

    public void h(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        if (!this.e) {
            b0.a("FillContent#draw");
            this.b.setColor((com.airbnb.lottie.utils.g.c((int) ((((((float) parentAlpha) / 255.0f) * ((float) this.h.h().intValue())) / 100.0f) * 255.0f), 0, 255) << 24) | (16777215 & ((com.airbnb.lottie.animation.keyframe.b) this.g).p()));
            a<ColorFilter, ColorFilter> aVar = this.i;
            if (aVar != null) {
                this.b.setColorFilter(aVar.h());
            }
            a<Float, Float> aVar2 = this.k;
            if (aVar2 != null) {
                float blurRadius = aVar2.h().floatValue();
                if (blurRadius == 0.0f) {
                    this.b.setMaskFilter((MaskFilter) null);
                } else if (blurRadius != this.l) {
                    this.b.setMaskFilter(this.c.v(blurRadius));
                }
                this.l = blurRadius;
            }
            c cVar = this.m;
            if (cVar != null) {
                cVar.b(this.b);
            }
            this.a.reset();
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                this.a.addPath(this.f.get(i2).getPath(), parentMatrix);
            }
            canvas.drawPath(this.a, this.b);
            b0.b("FillContent#draw");
        }
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        this.a.reset();
        for (int i2 = 0; i2 < this.f.size(); i2++) {
            this.a.addPath(this.f.get(i2).getPath(), parentMatrix);
        }
        this.a.computeBounds(outBounds, false);
        outBounds.set(outBounds.left - 1.0f, outBounds.top - 1.0f, outBounds.right + 1.0f, outBounds.bottom + 1.0f);
    }

    public void e(e keyPath, int depth, List<e> accumulator, e currentPartialKeyPath) {
        com.airbnb.lottie.utils.g.k(keyPath, depth, accumulator, currentPartialKeyPath, this);
    }

    public <T> void d(T property, @Nullable com.airbnb.lottie.value.c<T> callback) {
        c cVar;
        c cVar2;
        c cVar3;
        c cVar4;
        c cVar5;
        if (property == j0.a) {
            this.g.n(callback);
        } else if (property == j0.d) {
            this.h.n(callback);
        } else if (property == j0.K) {
            a<ColorFilter, ColorFilter> aVar = this.i;
            if (aVar != null) {
                this.c.G(aVar);
            }
            if (callback == null) {
                this.i = null;
                return;
            }
            q qVar = new q(callback);
            this.i = qVar;
            qVar.a(this);
            this.c.g(this.i);
        } else if (property == j0.j) {
            a<Float, Float> aVar2 = this.k;
            if (aVar2 != null) {
                aVar2.n(callback);
                return;
            }
            q qVar2 = new q(callback);
            this.k = qVar2;
            qVar2.a(this);
            this.c.g(this.k);
        } else if (property == j0.e && (cVar5 = this.m) != null) {
            cVar5.c(callback);
        } else if (property == j0.G && (cVar4 = this.m) != null) {
            cVar4.f(callback);
        } else if (property == j0.H && (cVar3 = this.m) != null) {
            cVar3.d(callback);
        } else if (property == j0.I && (cVar2 = this.m) != null) {
            cVar2.e(callback);
        } else if (property == j0.J && (cVar = this.m) != null) {
            cVar.g(callback);
        }
    }
}
