package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.d;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.content.k;
import com.airbnb.lottie.model.content.s;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.model.layer.b;
import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: RectangleContent */
public class o implements a.b, k, m {
    private final Path a = new Path();
    private final RectF b = new RectF();
    private final String c;
    private final boolean d;
    private final e0 e;
    private final a<?, PointF> f;
    private final a<?, PointF> g;
    private final a<?, Float> h;
    private final b i = new b();
    @Nullable
    private a<Float, Float> j = null;
    private boolean k;

    public o(e0 lottieDrawable, b layer, k rectShape) {
        this.c = rectShape.c();
        this.d = rectShape.f();
        this.e = lottieDrawable;
        a<PointF, PointF> j2 = rectShape.d().j();
        this.f = j2;
        a<PointF, PointF> j3 = rectShape.e().j();
        this.g = j3;
        a<Float, Float> j4 = rectShape.b().j();
        this.h = j4;
        layer.g(j2);
        layer.g(j3);
        layer.g(j4);
        j2.a(this);
        j3.a(this);
        j4.a(this);
    }

    public String getName() {
        return this.c;
    }

    public void a() {
        g();
    }

    private void g() {
        this.k = false;
        this.e.invalidateSelf();
    }

    public void b(List<c> contentsBefore, List<c> list) {
        for (int i2 = 0; i2 < contentsBefore.size(); i2++) {
            c content = contentsBefore.get(i2);
            if ((content instanceof u) && ((u) content).j() == s.a.SIMULTANEOUSLY) {
                u trimPath = (u) content;
                this.i.a(trimPath);
                trimPath.d(this);
            } else if (content instanceof q) {
                this.j = ((q) content).g();
            }
        }
    }

    public Path getPath() {
        a<Float, Float> aVar;
        if (this.k) {
            return this.a;
        }
        this.a.reset();
        if (this.d) {
            this.k = true;
            return this.a;
        }
        PointF size = this.g.h();
        float halfWidth = size.x / 2.0f;
        float halfHeight = size.y / 2.0f;
        a<?, Float> aVar2 = this.h;
        float radius = aVar2 == null ? 0.0f : ((d) aVar2).p();
        if (radius == 0.0f && (aVar = this.j) != null) {
            radius = Math.min(aVar.h().floatValue(), Math.min(halfWidth, halfHeight));
        }
        float maxRadius = Math.min(halfWidth, halfHeight);
        if (radius > maxRadius) {
            radius = maxRadius;
        }
        PointF position = this.f.h();
        this.a.moveTo(position.x + halfWidth, (position.y - halfHeight) + radius);
        this.a.lineTo(position.x + halfWidth, (position.y + halfHeight) - radius);
        if (radius > 0.0f) {
            RectF rectF = this.b;
            float f2 = position.x;
            float f3 = position.y;
            rectF.set((f2 + halfWidth) - (radius * 2.0f), (f3 + halfHeight) - (radius * 2.0f), f2 + halfWidth, f3 + halfHeight);
            this.a.arcTo(this.b, 0.0f, 90.0f, false);
        }
        this.a.lineTo((position.x - halfWidth) + radius, position.y + halfHeight);
        if (radius > 0.0f) {
            RectF rectF2 = this.b;
            float f4 = position.x;
            float f5 = position.y;
            rectF2.set(f4 - halfWidth, (f5 + halfHeight) - (radius * 2.0f), (f4 - halfWidth) + (radius * 2.0f), f5 + halfHeight);
            this.a.arcTo(this.b, 90.0f, 90.0f, false);
        }
        this.a.lineTo(position.x - halfWidth, (position.y - halfHeight) + radius);
        if (radius > 0.0f) {
            RectF rectF3 = this.b;
            float f6 = position.x;
            float f7 = position.y;
            rectF3.set(f6 - halfWidth, f7 - halfHeight, (f6 - halfWidth) + (radius * 2.0f), (f7 - halfHeight) + (radius * 2.0f));
            this.a.arcTo(this.b, 180.0f, 90.0f, false);
        }
        this.a.lineTo((position.x + halfWidth) - radius, position.y - halfHeight);
        if (radius > 0.0f) {
            RectF rectF4 = this.b;
            float f8 = position.x;
            float f9 = position.y;
            rectF4.set((f8 + halfWidth) - (radius * 2.0f), f9 - halfHeight, f8 + halfWidth, (f9 - halfHeight) + (2.0f * radius));
            this.a.arcTo(this.b, 270.0f, 90.0f, false);
        }
        this.a.close();
        this.i.b(this.a);
        this.k = true;
        return this.a;
    }

    public void e(e keyPath, int depth, List<e> accumulator, e currentPartialKeyPath) {
        g.k(keyPath, depth, accumulator, currentPartialKeyPath, this);
    }

    public <T> void d(T property, @Nullable c<T> callback) {
        if (property == j0.l) {
            this.g.n(callback);
        } else if (property == j0.n) {
            this.f.n(callback);
        } else if (property == j0.m) {
            this.h.n(callback);
        }
    }
}
