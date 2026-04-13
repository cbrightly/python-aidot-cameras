package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.animation.keyframe.q;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.content.r;
import com.airbnb.lottie.model.layer.b;
import com.airbnb.lottie.value.c;

/* compiled from: StrokeContent */
public class t extends a {
    private final b r;
    private final String s;
    private final boolean t;
    private final a<Integer, Integer> u;
    @Nullable
    private a<ColorFilter, ColorFilter> v;

    public t(e0 lottieDrawable, b layer, r stroke) {
        super(lottieDrawable, layer, stroke.b().toPaintCap(), stroke.e().toPaintJoin(), stroke.g(), stroke.i(), stroke.j(), stroke.f(), stroke.d());
        this.r = layer;
        this.s = stroke.h();
        this.t = stroke.k();
        a<Integer, Integer> j = stroke.c().j();
        this.u = j;
        j.a(this);
        layer.g(j);
    }

    public void h(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        if (!this.t) {
            this.i.setColor(((com.airbnb.lottie.animation.keyframe.b) this.u).p());
            a<ColorFilter, ColorFilter> aVar = this.v;
            if (aVar != null) {
                this.i.setColorFilter(aVar.h());
            }
            super.h(canvas, parentMatrix, parentAlpha);
        }
    }

    public String getName() {
        return this.s;
    }

    public <T> void d(T property, @Nullable c<T> callback) {
        super.d(property, callback);
        if (property == j0.b) {
            this.u.n(callback);
        } else if (property == j0.K) {
            a<ColorFilter, ColorFilter> aVar = this.v;
            if (aVar != null) {
                this.r.G(aVar);
            }
            if (callback == null) {
                this.v = null;
                return;
            }
            q qVar = new q(callback);
            this.v = qVar;
            qVar.a(this);
            this.r.g(this.u);
        }
    }
}
