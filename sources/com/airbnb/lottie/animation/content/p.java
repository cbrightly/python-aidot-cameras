package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.content.l;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.model.layer.b;
import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: RepeaterContent */
public class p implements e, m, j, a.b, k {
    private final Matrix a = new Matrix();
    private final Path b = new Path();
    private final e0 c;
    private final b d;
    private final String e;
    private final boolean f;
    private final a<Float, Float> g;
    private final a<Float, Float> h;
    private final com.airbnb.lottie.animation.keyframe.p i;
    private d j;

    public p(e0 lottieDrawable, b layer, l repeater) {
        this.c = lottieDrawable;
        this.d = layer;
        this.e = repeater.c();
        this.f = repeater.f();
        a<Float, Float> j2 = repeater.b().j();
        this.g = j2;
        layer.g(j2);
        j2.a(this);
        a<Float, Float> j3 = repeater.d().j();
        this.h = j3;
        layer.g(j3);
        j3.a(this);
        com.airbnb.lottie.animation.keyframe.p b2 = repeater.e().b();
        this.i = b2;
        b2.a(layer);
        b2.b(this);
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x0005 A[LOOP:0: B:3:0x0005->B:6:0x000f, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void g(java.util.ListIterator<com.airbnb.lottie.animation.content.c> r10) {
        /*
            r9 = this;
            com.airbnb.lottie.animation.content.d r0 = r9.j
            if (r0 == 0) goto L_0x0005
            return
        L_0x0005:
            boolean r0 = r10.hasPrevious()
            if (r0 == 0) goto L_0x0012
            java.lang.Object r0 = r10.previous()
            if (r0 == r9) goto L_0x0012
            goto L_0x0005
        L_0x0012:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
        L_0x0017:
            boolean r1 = r10.hasPrevious()
            if (r1 == 0) goto L_0x002a
            java.lang.Object r1 = r10.previous()
            com.airbnb.lottie.animation.content.c r1 = (com.airbnb.lottie.animation.content.c) r1
            r0.add(r1)
            r10.remove()
            goto L_0x0017
        L_0x002a:
            java.util.Collections.reverse(r0)
            com.airbnb.lottie.animation.content.d r8 = new com.airbnb.lottie.animation.content.d
            com.airbnb.lottie.e0 r2 = r9.c
            com.airbnb.lottie.model.layer.b r3 = r9.d
            boolean r5 = r9.f
            r7 = 0
            java.lang.String r4 = "Repeater"
            r1 = r8
            r6 = r0
            r1.<init>(r2, r3, r4, r5, r6, r7)
            r9.j = r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.p.g(java.util.ListIterator):void");
    }

    public String getName() {
        return this.e;
    }

    public void b(List<c> contentsBefore, List<c> contentsAfter) {
        this.j.b(contentsBefore, contentsAfter);
    }

    public Path getPath() {
        Path contentPath = this.j.getPath();
        this.b.reset();
        float copies = this.g.h().floatValue();
        float offset = this.h.h().floatValue();
        for (int i2 = ((int) copies) - 1; i2 >= 0; i2--) {
            this.a.set(this.i.g(((float) i2) + offset));
            this.b.addPath(contentPath, this.a);
        }
        return this.b;
    }

    public void h(Canvas canvas, Matrix parentMatrix, int alpha) {
        float copies = this.g.h().floatValue();
        float offset = this.h.h().floatValue();
        float startOpacity = this.i.i().h().floatValue() / 100.0f;
        float endOpacity = this.i.e().h().floatValue() / 100.0f;
        for (int i2 = ((int) copies) - 1; i2 >= 0; i2--) {
            this.a.set(parentMatrix);
            this.a.preConcat(this.i.g(((float) i2) + offset));
            this.j.h(canvas, this.a, (int) (((float) alpha) * g.i(startOpacity, endOpacity, ((float) i2) / copies)));
        }
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        this.j.f(outBounds, parentMatrix, applyParents);
    }

    public void a() {
        this.c.invalidateSelf();
    }

    public void e(e keyPath, int depth, List<e> accumulator, e currentPartialKeyPath) {
        g.k(keyPath, depth, accumulator, currentPartialKeyPath, this);
        for (int i2 = 0; i2 < this.j.j().size(); i2++) {
            c content = this.j.j().get(i2);
            if (content instanceof k) {
                g.k(keyPath, depth, accumulator, currentPartialKeyPath, (k) content);
            }
        }
    }

    public <T> void d(T property, @Nullable c<T> callback) {
        if (!this.i.c(property, callback)) {
            if (property == j0.u) {
                this.g.n(callback);
            } else if (property == j0.v) {
                this.h.n(callback);
            }
        }
    }
}
