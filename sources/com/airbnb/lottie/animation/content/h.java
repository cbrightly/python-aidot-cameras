package com.airbnb.lottie.animation.content;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.MaskFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.c;
import com.airbnb.lottie.animation.keyframe.q;
import com.airbnb.lottie.b0;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.content.d;
import com.airbnb.lottie.model.content.e;
import com.airbnb.lottie.model.content.g;
import com.airbnb.lottie.model.layer.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: GradientFillContent */
public class h implements e, a.b, k {
    @NonNull
    private final String a;
    private final boolean b;
    private final b c;
    private final LongSparseArray<LinearGradient> d = new LongSparseArray<>();
    private final LongSparseArray<RadialGradient> e = new LongSparseArray<>();
    private final Path f;
    private final Paint g;
    private final RectF h;
    private final List<m> i;
    private final g j;
    private final a<d, d> k;
    private final a<Integer, Integer> l;
    private final a<PointF, PointF> m;
    private final a<PointF, PointF> n;
    @Nullable
    private a<ColorFilter, ColorFilter> o;
    @Nullable
    private q p;
    private final e0 q;
    private final int r;
    @Nullable
    private a<Float, Float> s;
    float t;
    @Nullable
    private c u;

    public h(e0 lottieDrawable, c0 composition, b layer, e fill) {
        Path path = new Path();
        this.f = path;
        this.g = new com.airbnb.lottie.animation.a(1);
        this.h = new RectF();
        this.i = new ArrayList();
        this.t = 0.0f;
        this.c = layer;
        this.a = fill.f();
        this.b = fill.i();
        this.q = lottieDrawable;
        this.j = fill.e();
        path.setFillType(fill.c());
        this.r = (int) (composition.d() / 32.0f);
        a<d, d> j2 = fill.d().j();
        this.k = j2;
        j2.a(this);
        layer.g(j2);
        a<Integer, Integer> j3 = fill.g().j();
        this.l = j3;
        j3.a(this);
        layer.g(j3);
        a<PointF, PointF> j4 = fill.h().j();
        this.m = j4;
        j4.a(this);
        layer.g(j4);
        a<PointF, PointF> j5 = fill.b().j();
        this.n = j5;
        j5.a(this);
        layer.g(j5);
        if (layer.u() != null) {
            a<Float, Float> j6 = layer.u().a().j();
            this.s = j6;
            j6.a(this);
            layer.g(this.s);
        }
        if (layer.w() != null) {
            this.u = new c(this, layer, layer.w());
        }
    }

    public void a() {
        this.q.invalidateSelf();
    }

    public void b(List<c> list, List<c> contentsAfter) {
        for (int i2 = 0; i2 < contentsAfter.size(); i2++) {
            c content = contentsAfter.get(i2);
            if (content instanceof m) {
                this.i.add((m) content);
            }
        }
    }

    public void h(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        Shader shader;
        if (!this.b) {
            b0.a("GradientFillContent#draw");
            this.f.reset();
            for (int i2 = 0; i2 < this.i.size(); i2++) {
                this.f.addPath(this.i.get(i2).getPath(), parentMatrix);
            }
            this.f.computeBounds(this.h, false);
            if (this.j == g.LINEAR) {
                shader = j();
            } else {
                shader = k();
            }
            shader.setLocalMatrix(parentMatrix);
            this.g.setShader(shader);
            a<ColorFilter, ColorFilter> aVar = this.o;
            if (aVar != null) {
                this.g.setColorFilter(aVar.h());
            }
            a<Float, Float> aVar2 = this.s;
            if (aVar2 != null) {
                float blurRadius = aVar2.h().floatValue();
                if (blurRadius == 0.0f) {
                    this.g.setMaskFilter((MaskFilter) null);
                } else if (blurRadius != this.t) {
                    this.g.setMaskFilter(new BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL));
                }
                this.t = blurRadius;
            }
            c cVar = this.u;
            if (cVar != null) {
                cVar.b(this.g);
            }
            this.g.setAlpha(com.airbnb.lottie.utils.g.c((int) ((((((float) parentAlpha) / 255.0f) * ((float) this.l.h().intValue())) / 100.0f) * 255.0f), 0, 255));
            canvas.drawPath(this.f, this.g);
            b0.b("GradientFillContent#draw");
        }
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        this.f.reset();
        for (int i2 = 0; i2 < this.i.size(); i2++) {
            this.f.addPath(this.i.get(i2).getPath(), parentMatrix);
        }
        this.f.computeBounds(outBounds, false);
        outBounds.set(outBounds.left - 1.0f, outBounds.top - 1.0f, outBounds.right + 1.0f, outBounds.bottom + 1.0f);
    }

    public String getName() {
        return this.a;
    }

    private LinearGradient j() {
        int gradientHash = i();
        LinearGradient gradient = this.d.get((long) gradientHash);
        if (gradient != null) {
            return gradient;
        }
        PointF startPoint = this.m.h();
        PointF endPoint = this.n.h();
        d gradientColor = this.k.h();
        int[] colors = g(gradientColor.c());
        LinearGradient gradient2 = new LinearGradient(startPoint.x, startPoint.y, endPoint.x, endPoint.y, colors, gradientColor.d(), Shader.TileMode.CLAMP);
        this.d.put((long) gradientHash, gradient2);
        return gradient2;
    }

    private RadialGradient k() {
        float r2;
        int gradientHash = i();
        RadialGradient gradient = this.e.get((long) gradientHash);
        if (gradient != null) {
            return gradient;
        }
        PointF startPoint = this.m.h();
        PointF endPoint = this.n.h();
        d gradientColor = this.k.h();
        int[] colors = g(gradientColor.c());
        float[] positions = gradientColor.d();
        float x0 = startPoint.x;
        float y0 = startPoint.y;
        float x1 = endPoint.x;
        float y1 = endPoint.y;
        float r3 = (float) Math.hypot((double) (x1 - x0), (double) (y1 - y0));
        if (r3 <= 0.0f) {
            r2 = 0.001f;
        } else {
            r2 = r3;
        }
        float f2 = y1;
        float f3 = x1;
        float f4 = y0;
        RadialGradient gradient2 = new RadialGradient(x0, y0, r2, colors, positions, Shader.TileMode.CLAMP);
        this.e.put((long) gradientHash, gradient2);
        return gradient2;
    }

    private int i() {
        int startPointProgress = Math.round(this.m.f() * ((float) this.r));
        int endPointProgress = Math.round(this.n.f() * ((float) this.r));
        int colorProgress = Math.round(this.k.f() * ((float) this.r));
        int hash = 17;
        if (startPointProgress != 0) {
            hash = 17 * 31 * startPointProgress;
        }
        if (endPointProgress != 0) {
            hash = hash * 31 * endPointProgress;
        }
        if (colorProgress != 0) {
            return hash * 31 * colorProgress;
        }
        return hash;
    }

    private int[] g(int[] colors) {
        q qVar = this.p;
        if (qVar != null) {
            Integer[] dynamicColors = (Integer[]) qVar.h();
            if (colors.length == dynamicColors.length) {
                for (int i2 = 0; i2 < colors.length; i2++) {
                    colors[i2] = dynamicColors[i2].intValue();
                }
            } else {
                colors = new int[dynamicColors.length];
                for (int i3 = 0; i3 < dynamicColors.length; i3++) {
                    colors[i3] = dynamicColors[i3].intValue();
                }
            }
        }
        return colors;
    }

    public void e(com.airbnb.lottie.model.e keyPath, int depth, List<com.airbnb.lottie.model.e> accumulator, com.airbnb.lottie.model.e currentPartialKeyPath) {
        com.airbnb.lottie.utils.g.k(keyPath, depth, accumulator, currentPartialKeyPath, this);
    }

    public <T> void d(T property, @Nullable com.airbnb.lottie.value.c<T> callback) {
        c cVar;
        c cVar2;
        c cVar3;
        c cVar4;
        c cVar5;
        if (property == j0.d) {
            this.l.n(callback);
        } else if (property == j0.K) {
            a<ColorFilter, ColorFilter> aVar = this.o;
            if (aVar != null) {
                this.c.G(aVar);
            }
            if (callback == null) {
                this.o = null;
                return;
            }
            q qVar = new q(callback);
            this.o = qVar;
            qVar.a(this);
            this.c.g(this.o);
        } else if (property == j0.L) {
            q qVar2 = this.p;
            if (qVar2 != null) {
                this.c.G(qVar2);
            }
            if (callback == null) {
                this.p = null;
                return;
            }
            this.d.clear();
            this.e.clear();
            q qVar3 = new q(callback);
            this.p = qVar3;
            qVar3.a(this);
            this.c.g(this.p);
        } else if (property == j0.j) {
            a<Float, Float> aVar2 = this.s;
            if (aVar2 != null) {
                aVar2.n(callback);
                return;
            }
            q qVar4 = new q(callback);
            this.s = qVar4;
            qVar4.a(this);
            this.c.g(this.s);
        } else if (property == j0.e && (cVar5 = this.u) != null) {
            cVar5.c(callback);
        } else if (property == j0.G && (cVar4 = this.u) != null) {
            cVar4.f(callback);
        } else if (property == j0.H && (cVar3 = this.u) != null) {
            cVar3.d(callback);
        } else if (property == j0.I && (cVar2 = this.u) != null) {
            cVar2.e(callback);
        } else if (property == j0.J && (cVar = this.u) != null) {
            cVar.g(callback);
        }
    }
}
