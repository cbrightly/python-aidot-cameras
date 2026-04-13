package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.b;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: TextKeyframeAnimation */
public class o extends g<b> {
    public o(List<com.airbnb.lottie.value.a<b>> keyframes) {
        super(keyframes);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: p */
    public b i(com.airbnb.lottie.value.a<b> keyframe, float keyframeProgress) {
        T t;
        c<A> cVar = this.e;
        if (cVar != null) {
            float f = keyframe.g;
            Float f2 = keyframe.h;
            float floatValue = f2 == null ? Float.MAX_VALUE : f2.floatValue();
            T t2 = keyframe.b;
            b bVar = (b) t2;
            T t3 = keyframe.c;
            return (b) cVar.b(f, floatValue, bVar, t3 == null ? (b) t2 : (b) t3, keyframeProgress, d(), f());
        } else if (keyframeProgress != 1.0f || (t = keyframe.c) == null) {
            return (b) keyframe.b;
        } else {
            return (b) t;
        }
    }

    public void q(c<String> valueCallback) {
        super.n(new a(new com.airbnb.lottie.value.b<>(), valueCallback, new b()));
    }

    /* compiled from: TextKeyframeAnimation */
    public class a extends c<b> {
        final /* synthetic */ com.airbnb.lottie.value.b d;
        final /* synthetic */ c e;
        final /* synthetic */ b f;

        a(com.airbnb.lottie.value.b bVar, c cVar, b bVar2) {
            this.d = bVar;
            this.e = cVar;
            this.f = bVar2;
        }

        /* JADX WARNING: type inference failed for: r19v0, types: [com.airbnb.lottie.value.b<com.airbnb.lottie.model.b>, com.airbnb.lottie.value.b] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* renamed from: d */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.airbnb.lottie.model.b a(com.airbnb.lottie.value.b<com.airbnb.lottie.model.b> r19) {
            /*
                r18 = this;
                r0 = r18
                com.airbnb.lottie.value.b r1 = r0.d
                float r2 = r19.f()
                float r3 = r19.a()
                java.lang.Object r4 = r19.g()
                com.airbnb.lottie.model.b r4 = (com.airbnb.lottie.model.b) r4
                java.lang.String r4 = r4.a
                java.lang.Object r5 = r19.b()
                com.airbnb.lottie.model.b r5 = (com.airbnb.lottie.model.b) r5
                java.lang.String r5 = r5.a
                float r6 = r19.d()
                float r7 = r19.c()
                float r8 = r19.e()
                r1.h(r2, r3, r4, r5, r6, r7, r8)
                com.airbnb.lottie.value.c r1 = r0.e
                com.airbnb.lottie.value.b r2 = r0.d
                java.lang.Object r1 = r1.a(r2)
                java.lang.String r1 = (java.lang.String) r1
                float r2 = r19.c()
                r3 = 1065353216(0x3f800000, float:1.0)
                int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
                if (r2 != 0) goto L_0x0044
                java.lang.Object r2 = r19.b()
                goto L_0x0048
            L_0x0044:
                java.lang.Object r2 = r19.g()
            L_0x0048:
                com.airbnb.lottie.model.b r2 = (com.airbnb.lottie.model.b) r2
                r15 = r2
                com.airbnb.lottie.model.b r2 = r0.f
                java.lang.String r4 = r15.b
                float r5 = r15.c
                com.airbnb.lottie.model.b$a r6 = r15.d
                int r7 = r15.e
                float r8 = r15.f
                float r9 = r15.g
                int r10 = r15.h
                int r11 = r15.i
                float r12 = r15.j
                boolean r13 = r15.k
                android.graphics.PointF r14 = r15.l
                android.graphics.PointF r3 = r15.m
                r16 = r3
                r3 = r1
                r17 = r15
                r15 = r16
                r2.a(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
                com.airbnb.lottie.model.b r2 = r0.f
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.keyframe.o.a.a(com.airbnb.lottie.value.b):com.airbnb.lottie.model.b");
        }
    }
}
