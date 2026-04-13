package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.q;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.content.d;
import com.airbnb.lottie.model.content.f;
import com.airbnb.lottie.model.content.g;
import com.airbnb.lottie.model.layer.b;
import com.airbnb.lottie.value.c;

/* compiled from: GradientStrokeContent */
public class i extends a {
    private final a<PointF, PointF> A;
    @Nullable
    private q B;
    private final String r;
    private final boolean s;
    private final LongSparseArray<LinearGradient> t = new LongSparseArray<>();
    private final LongSparseArray<RadialGradient> u = new LongSparseArray<>();
    private final RectF v = new RectF();
    private final g w;
    private final int x;
    private final a<d, d> y;
    private final a<PointF, PointF> z;

    public i(e0 lottieDrawable, b layer, f stroke) {
        super(lottieDrawable, layer, stroke.b().toPaintCap(), stroke.g().toPaintJoin(), stroke.i(), stroke.k(), stroke.m(), stroke.h(), stroke.c());
        this.r = stroke.j();
        this.w = stroke.f();
        this.s = stroke.n();
        this.x = (int) (lottieDrawable.t().d() / 32.0f);
        a<d, d> j = stroke.e().j();
        this.y = j;
        j.a(this);
        layer.g(j);
        a<PointF, PointF> j2 = stroke.l().j();
        this.z = j2;
        j2.a(this);
        layer.g(j2);
        a<PointF, PointF> j3 = stroke.d().j();
        this.A = j3;
        j3.a(this);
        layer.g(j3);
    }

    public void h(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        Shader shader;
        if (!this.s) {
            f(this.v, parentMatrix, false);
            if (this.w == g.LINEAR) {
                shader = l();
            } else {
                shader = m();
            }
            shader.setLocalMatrix(parentMatrix);
            this.i.setShader(shader);
            super.h(canvas, parentMatrix, parentAlpha);
        }
    }

    public String getName() {
        return this.r;
    }

    private LinearGradient l() {
        int gradientHash = k();
        LinearGradient gradient = this.t.get((long) gradientHash);
        if (gradient != null) {
            return gradient;
        }
        PointF startPoint = this.z.h();
        PointF endPoint = this.A.h();
        d gradientColor = this.y.h();
        int[] colors = j(gradientColor.c());
        float[] positions = gradientColor.d();
        float x0 = startPoint.x;
        float y0 = startPoint.y;
        float x1 = endPoint.x;
        float y1 = endPoint.y;
        float f = y1;
        float f2 = x1;
        float f3 = y0;
        float f4 = x0;
        LinearGradient gradient2 = new LinearGradient(x0, y0, x1, y1, colors, positions, Shader.TileMode.CLAMP);
        this.t.put((long) gradientHash, gradient2);
        return gradient2;
    }

    private RadialGradient m() {
        int gradientHash = k();
        RadialGradient gradient = this.u.get((long) gradientHash);
        if (gradient != null) {
            return gradient;
        }
        PointF startPoint = this.z.h();
        PointF endPoint = this.A.h();
        d gradientColor = this.y.h();
        int[] colors = j(gradientColor.c());
        float[] positions = gradientColor.d();
        float x0 = startPoint.x;
        float y0 = startPoint.y;
        float x1 = endPoint.x;
        float y1 = endPoint.y;
        float r2 = (float) Math.hypot((double) (x1 - x0), (double) (y1 - y0));
        float f = r2;
        float f2 = y1;
        float f3 = x1;
        float f4 = y0;
        RadialGradient gradient2 = new RadialGradient(x0, y0, r2, colors, positions, Shader.TileMode.CLAMP);
        this.u.put((long) gradientHash, gradient2);
        return gradient2;
    }

    private int k() {
        int startPointProgress = Math.round(this.z.f() * ((float) this.x));
        int endPointProgress = Math.round(this.A.f() * ((float) this.x));
        int colorProgress = Math.round(this.y.f() * ((float) this.x));
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

    private int[] j(int[] colors) {
        q qVar = this.B;
        if (qVar != null) {
            Integer[] dynamicColors = (Integer[]) qVar.h();
            if (colors.length == dynamicColors.length) {
                for (int i = 0; i < colors.length; i++) {
                    colors[i] = dynamicColors[i].intValue();
                }
            } else {
                colors = new int[dynamicColors.length];
                for (int i2 = 0; i2 < dynamicColors.length; i2++) {
                    colors[i2] = dynamicColors[i2].intValue();
                }
            }
        }
        return colors;
    }

    public <T> void d(T property, @Nullable c<T> callback) {
        super.d(property, callback);
        if (property == j0.L) {
            q qVar = this.B;
            if (qVar != null) {
                this.f.G(qVar);
            }
            if (callback == null) {
                this.B = null;
                return;
            }
            q qVar2 = new q(callback);
            this.B = qVar2;
            qVar2.a(this);
            this.f.g(this.B);
        }
    }
}
