package com.airbnb.lottie.animation.keyframe;

import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.model.layer.b;
import com.airbnb.lottie.parser.j;

/* compiled from: DropShadowKeyframeAnimation */
public class c implements a.b {
    private final a.b a;
    private final a<Integer, Integer> b;
    private final a<Float, Float> c;
    private final a<Float, Float> d;
    private final a<Float, Float> e;
    private final a<Float, Float> f;
    private boolean g = true;

    public c(a.b listener, b layer, j dropShadowEffect) {
        this.a = listener;
        a<Integer, Integer> j = dropShadowEffect.a().j();
        this.b = j;
        j.a(this);
        layer.g(j);
        a<Float, Float> j2 = dropShadowEffect.d().j();
        this.c = j2;
        j2.a(this);
        layer.g(j2);
        a<Float, Float> j3 = dropShadowEffect.b().j();
        this.d = j3;
        j3.a(this);
        layer.g(j3);
        a<Float, Float> j4 = dropShadowEffect.c().j();
        this.e = j4;
        j4.a(this);
        layer.g(j4);
        a<Float, Float> j5 = dropShadowEffect.e().j();
        this.f = j5;
        j5.a(this);
        layer.g(j5);
    }

    public void a() {
        this.g = true;
        this.a.a();
    }

    public void b(Paint paint) {
        if (this.g) {
            this.g = false;
            double directionRad = ((double) this.d.h().floatValue()) * 0.017453292519943295d;
            float distance = this.e.h().floatValue();
            int baseColor = this.b.h().intValue();
            int color = Color.argb(Math.round(this.c.h().floatValue()), Color.red(baseColor), Color.green(baseColor), Color.blue(baseColor));
            paint.setShadowLayer(this.f.h().floatValue(), ((float) Math.sin(directionRad)) * distance, ((float) Math.cos(3.141592653589793d + directionRad)) * distance, color);
        }
    }

    public void c(@Nullable com.airbnb.lottie.value.c<Integer> callback) {
        this.b.n(callback);
    }

    public void f(@Nullable com.airbnb.lottie.value.c<Float> callback) {
        if (callback == null) {
            this.c.n((com.airbnb.lottie.value.c) null);
        } else {
            this.c.n(new a(callback));
        }
    }

    /* compiled from: DropShadowKeyframeAnimation */
    public class a extends com.airbnb.lottie.value.c<Float> {
        final /* synthetic */ com.airbnb.lottie.value.c d;

        a(com.airbnb.lottie.value.c cVar) {
            this.d = cVar;
        }

        /* JADX WARNING: type inference failed for: r4v0, types: [com.airbnb.lottie.value.b<java.lang.Float>, com.airbnb.lottie.value.b] */
        /* JADX WARNING: Unknown variable types count: 1 */
        @androidx.annotation.Nullable
        /* renamed from: d */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Float a(com.airbnb.lottie.value.b<java.lang.Float> r4) {
            /*
                r3 = this;
                com.airbnb.lottie.value.c r0 = r3.d
                java.lang.Object r0 = r0.a(r4)
                java.lang.Float r0 = (java.lang.Float) r0
                if (r0 != 0) goto L_0x000c
                r1 = 0
                return r1
            L_0x000c:
                float r1 = r0.floatValue()
                r2 = 1076048691(0x40233333, float:2.55)
                float r1 = r1 * r2
                java.lang.Float r1 = java.lang.Float.valueOf(r1)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.keyframe.c.a.a(com.airbnb.lottie.value.b):java.lang.Float");
        }
    }

    public void d(@Nullable com.airbnb.lottie.value.c<Float> callback) {
        this.d.n(callback);
    }

    public void e(@Nullable com.airbnb.lottie.value.c<Float> callback) {
        this.e.n(callback);
    }

    public void g(@Nullable com.airbnb.lottie.value.c<Float> callback) {
        this.f.n(callback);
    }
}
