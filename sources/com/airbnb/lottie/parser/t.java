package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.value.Keyframe;
import java.lang.ref.WeakReference;

/* compiled from: KeyframeParser */
public class t {
    private static final Interpolator a = new LinearInterpolator();
    private static SparseArrayCompat<WeakReference<Interpolator>> b;
    static a.C0011a c = a.C0011a.a("t", "s", "e", "o", "i", "h", TypedValues.TransitionType.S_TO, "ti");
    static a.C0011a d = a.C0011a.a("x", "y");

    t() {
    }

    private static SparseArrayCompat<WeakReference<Interpolator>> g() {
        if (b == null) {
            b = new SparseArrayCompat<>();
        }
        return b;
    }

    @Nullable
    private static WeakReference<Interpolator> a(int hash) {
        WeakReference<Interpolator> weakReference;
        synchronized (t.class) {
            weakReference = g().get(hash);
        }
        return weakReference;
    }

    private static void h(int hash, WeakReference<Interpolator> interpolator) {
        synchronized (t.class) {
            b.put(hash, interpolator);
        }
    }

    static <T> com.airbnb.lottie.value.a<T> c(a reader, c0 composition, float scale, n0<T> valueParser, boolean animated, boolean multiDimensional) {
        if (animated && multiDimensional) {
            return e(composition, reader, scale, valueParser);
        }
        if (animated) {
            return d(composition, reader, scale, valueParser);
        }
        return f(reader, scale, valueParser);
    }

    private static <T> com.airbnb.lottie.value.a<T> d(c0 composition, a reader, float scale, n0<T> valueParser) {
        Interpolator interpolator;
        a aVar = reader;
        float f = scale;
        n0<T> n0Var = valueParser;
        PointF cp1 = null;
        PointF cp2 = null;
        float startFrame = 0.0f;
        T startValue = null;
        T endValue = null;
        boolean hold = false;
        reader.g();
        PointF pathCp1 = null;
        PointF pathCp2 = null;
        while (reader.l()) {
            switch (aVar.x(c)) {
                case 0:
                    startFrame = (float) reader.n();
                    break;
                case 1:
                    startValue = n0Var.a(aVar, f);
                    break;
                case 2:
                    endValue = n0Var.a(aVar, f);
                    break;
                case 3:
                    cp1 = s.e(aVar, 1.0f);
                    break;
                case 4:
                    cp2 = s.e(aVar, 1.0f);
                    break;
                case 5:
                    boolean z = true;
                    if (reader.o() != 1) {
                        z = false;
                    }
                    hold = z;
                    break;
                case 6:
                    pathCp1 = s.e(reader, scale);
                    break;
                case 7:
                    pathCp2 = s.e(reader, scale);
                    break;
                default:
                    reader.E();
                    break;
            }
        }
        reader.j();
        if (hold) {
            endValue = startValue;
            interpolator = a;
        } else if (cp1 == null || cp2 == null) {
            interpolator = a;
        } else {
            interpolator = b(cp1, cp2);
        }
        Keyframe<T> keyframe = new com.airbnb.lottie.value.a<>(composition, startValue, endValue, interpolator, startFrame, (Float) null);
        keyframe.o = pathCp1;
        keyframe.p = pathCp2;
        return keyframe;
    }

    private static <T> com.airbnb.lottie.value.a<T> e(c0 composition, a reader, float scale, n0<T> valueParser) {
        Interpolator yInterpolator;
        Interpolator xInterpolator;
        Interpolator interpolator;
        T endValue;
        Keyframe<T> keyframe;
        float yCp1x;
        float yCp1y;
        float yCp2x;
        float yCp2y;
        a aVar = reader;
        float f = scale;
        n0<T> n0Var = valueParser;
        PointF cp1 = null;
        PointF cp2 = null;
        PointF xCp2 = null;
        PointF xCp22 = null;
        PointF yCp1 = null;
        PointF yCp2 = null;
        float startFrame = 0.0f;
        T startValue = null;
        boolean hold = false;
        Interpolator xInterpolator2 = null;
        Interpolator yInterpolator2 = null;
        reader.g();
        T endValue2 = null;
        T endValue3 = null;
        PointF xCp1 = null;
        while (reader.l()) {
            Interpolator xInterpolator3 = xInterpolator2;
            switch (aVar.x(c)) {
                case 0:
                    PointF pointF = yCp2;
                    float f2 = startFrame;
                    T t = endValue3;
                    PointF pathCp2 = xCp1;
                    Interpolator interpolator2 = yInterpolator2;
                    startFrame = (float) reader.n();
                    xCp22 = xCp22;
                    xCp2 = xCp2;
                    xInterpolator2 = xInterpolator3;
                    xCp1 = pathCp2;
                    yCp1 = yCp1;
                    break;
                case 1:
                    PointF pointF2 = yCp2;
                    float f3 = startFrame;
                    T t2 = endValue3;
                    PointF pathCp22 = xCp1;
                    Interpolator interpolator3 = yInterpolator2;
                    PointF xCp12 = xCp2;
                    PointF xCp13 = xCp22;
                    startValue = n0Var.a(aVar, f);
                    xCp2 = xCp12;
                    xInterpolator2 = xInterpolator3;
                    xCp1 = pathCp22;
                    break;
                case 2:
                    PointF pointF3 = yCp2;
                    float f4 = startFrame;
                    T t3 = endValue3;
                    PointF pathCp23 = xCp1;
                    Interpolator interpolator4 = yInterpolator2;
                    PointF xCp14 = xCp2;
                    PointF xCp15 = xCp22;
                    endValue2 = n0Var.a(aVar, f);
                    xCp2 = xCp14;
                    xInterpolator2 = xInterpolator3;
                    xCp1 = pathCp23;
                    break;
                case 3:
                    PointF yCp12 = yCp1;
                    PointF yCp22 = yCp2;
                    float startFrame2 = startFrame;
                    T pathCp1 = endValue3;
                    PointF pathCp24 = xCp1;
                    Interpolator yInterpolator3 = yInterpolator2;
                    if (reader.u() != a.b.BEGIN_OBJECT) {
                        PointF xCp16 = xCp2;
                        PointF xCp17 = xCp22;
                        cp1 = s.e(reader, scale);
                        xCp2 = xCp16;
                        xInterpolator2 = xInterpolator3;
                        yInterpolator2 = yInterpolator3;
                        xCp1 = pathCp24;
                        endValue3 = pathCp1;
                        startFrame = startFrame2;
                        yCp2 = yCp22;
                        yCp1 = yCp12;
                        break;
                    } else {
                        reader.g();
                        float xCp1x = 0.0f;
                        float xCp1y = 0.0f;
                        float yCp1x2 = 0.0f;
                        float yCp1y2 = 0.0f;
                        while (reader.l()) {
                            switch (aVar.x(d)) {
                                case 0:
                                    PointF xCp18 = xCp2;
                                    PointF xCp23 = xCp22;
                                    a.b u = reader.u();
                                    a.b bVar = a.b.NUMBER;
                                    if (u != bVar) {
                                        reader.c();
                                        PointF xCp24 = xCp23;
                                        xCp1x = (float) reader.n();
                                        if (reader.u() == bVar) {
                                            yCp1x = (float) reader.n();
                                        } else {
                                            yCp1x = xCp1x;
                                        }
                                        yCp1x2 = yCp1x;
                                        reader.i();
                                        xCp22 = xCp24;
                                        xCp2 = xCp18;
                                        break;
                                    } else {
                                        xCp1x = (float) reader.n();
                                        yCp1x2 = xCp1x;
                                        xCp2 = xCp18;
                                        xCp22 = xCp23;
                                        break;
                                    }
                                case 1:
                                    a.b u2 = reader.u();
                                    a.b bVar2 = a.b.NUMBER;
                                    if (u2 != bVar2) {
                                        reader.c();
                                        PointF xCp19 = xCp2;
                                        PointF xCp25 = xCp22;
                                        xCp1y = (float) reader.n();
                                        if (reader.u() == bVar2) {
                                            yCp1y = (float) reader.n();
                                        } else {
                                            yCp1y = xCp1y;
                                        }
                                        yCp1y2 = yCp1y;
                                        reader.i();
                                        xCp2 = xCp19;
                                        xCp22 = xCp25;
                                        break;
                                    } else {
                                        xCp1y = (float) reader.n();
                                        yCp1y2 = xCp1y;
                                        break;
                                    }
                                default:
                                    PointF xCp110 = xCp2;
                                    PointF xCp111 = xCp22;
                                    reader.E();
                                    xCp2 = xCp110;
                                    break;
                            }
                        }
                        PointF xCp112 = xCp2;
                        PointF xCp113 = xCp22;
                        PointF xCp114 = new PointF(xCp1x, xCp1y);
                        PointF yCp13 = new PointF(yCp1x2, yCp1y2);
                        reader.j();
                        yCp1 = yCp13;
                        xInterpolator2 = xInterpolator3;
                        yInterpolator2 = yInterpolator3;
                        xCp1 = pathCp24;
                        endValue3 = pathCp1;
                        startFrame = startFrame2;
                        yCp2 = yCp22;
                        PointF pointF4 = xCp114;
                        xCp22 = xCp113;
                        xCp2 = pointF4;
                        break;
                    }
                case 4:
                    Interpolator yInterpolator4 = yInterpolator2;
                    if (reader.u() != a.b.BEGIN_OBJECT) {
                        PointF pointF5 = yCp2;
                        float f5 = startFrame;
                        T t4 = endValue3;
                        PointF pointF6 = xCp1;
                        cp2 = s.e(reader, scale);
                        xInterpolator2 = xInterpolator3;
                        yInterpolator2 = yInterpolator4;
                        break;
                    } else {
                        reader.g();
                        float xCp2x = 0.0f;
                        float xCp2y = 0.0f;
                        T pathCp12 = endValue3;
                        PointF pathCp25 = xCp1;
                        float yCp2x2 = 0.0f;
                        float yCp2y2 = 0.0f;
                        while (reader.l()) {
                            float startFrame3 = startFrame;
                            switch (aVar.x(d)) {
                                case 0:
                                    PointF xCp26 = xCp22;
                                    PointF yCp14 = yCp1;
                                    PointF yCp23 = yCp2;
                                    a.b u3 = reader.u();
                                    a.b bVar3 = a.b.NUMBER;
                                    if (u3 != bVar3) {
                                        reader.c();
                                        xCp22 = xCp26;
                                        xCp2x = (float) reader.n();
                                        if (reader.u() == bVar3) {
                                            yCp2x = (float) reader.n();
                                        } else {
                                            yCp2x = xCp2x;
                                        }
                                        yCp2x2 = yCp2x;
                                        reader.i();
                                        startFrame = startFrame3;
                                        yCp2 = yCp23;
                                        yCp1 = yCp14;
                                        break;
                                    } else {
                                        xCp2x = (float) reader.n();
                                        yCp2x2 = xCp2x;
                                        xCp22 = xCp26;
                                        startFrame = startFrame3;
                                        yCp2 = yCp23;
                                        yCp1 = yCp14;
                                        break;
                                    }
                                case 1:
                                    a.b u4 = reader.u();
                                    PointF yCp24 = yCp2;
                                    a.b bVar4 = a.b.NUMBER;
                                    if (u4 != bVar4) {
                                        reader.c();
                                        PointF xCp27 = xCp22;
                                        PointF yCp15 = yCp1;
                                        xCp2y = (float) reader.n();
                                        if (reader.u() == bVar4) {
                                            yCp2y = (float) reader.n();
                                        } else {
                                            yCp2y = xCp2y;
                                        }
                                        yCp2y2 = yCp2y;
                                        reader.i();
                                        xCp22 = xCp27;
                                        startFrame = startFrame3;
                                        yCp2 = yCp24;
                                        yCp1 = yCp15;
                                        break;
                                    } else {
                                        xCp2y = (float) reader.n();
                                        yCp2y2 = xCp2y;
                                        startFrame = startFrame3;
                                        yCp2 = yCp24;
                                        break;
                                    }
                                default:
                                    PointF pointF7 = yCp1;
                                    PointF pointF8 = yCp2;
                                    reader.E();
                                    startFrame = startFrame3;
                                    break;
                            }
                        }
                        PointF pointF9 = yCp2;
                        float f6 = startFrame;
                        xCp22 = new PointF(xCp2x, xCp2y);
                        yCp2 = new PointF(yCp2x2, yCp2y2);
                        reader.j();
                        xInterpolator2 = xInterpolator3;
                        yInterpolator2 = yInterpolator4;
                        xCp1 = pathCp25;
                        endValue3 = pathCp12;
                        yCp1 = yCp1;
                        break;
                    }
                case 5:
                    Interpolator yInterpolator5 = yInterpolator2;
                    boolean z = true;
                    if (reader.o() != 1) {
                        z = false;
                    }
                    hold = z;
                    xInterpolator2 = xInterpolator3;
                    yInterpolator2 = yInterpolator5;
                    break;
                case 6:
                    endValue3 = s.e(reader, scale);
                    xInterpolator2 = xInterpolator3;
                    break;
                case 7:
                    xCp1 = s.e(reader, scale);
                    xInterpolator2 = xInterpolator3;
                    break;
                default:
                    PointF pointF10 = yCp1;
                    PointF pointF11 = yCp2;
                    float f7 = startFrame;
                    T t5 = endValue3;
                    PointF pathCp26 = xCp1;
                    Interpolator interpolator5 = yInterpolator2;
                    PointF xCp115 = xCp2;
                    PointF xCp116 = xCp22;
                    reader.E();
                    xCp2 = xCp115;
                    xInterpolator2 = xInterpolator3;
                    xCp1 = pathCp26;
                    break;
            }
        }
        PointF yCp16 = yCp1;
        PointF yCp25 = yCp2;
        float startFrame4 = startFrame;
        T pathCp13 = endValue3;
        PointF pathCp27 = xCp1;
        Interpolator xInterpolator4 = xInterpolator2;
        Interpolator yInterpolator6 = yInterpolator2;
        PointF xCp117 = xCp2;
        PointF xCp118 = xCp22;
        reader.j();
        if (hold) {
            endValue = startValue;
            xInterpolator = xInterpolator4;
            yInterpolator = yInterpolator6;
            PointF pointF12 = yCp25;
            PointF pointF13 = yCp16;
            PointF pointF14 = xCp118;
            PointF xCp28 = xCp117;
            interpolator = a;
        } else if (cp1 != null && cp2 != null) {
            endValue = endValue2;
            xInterpolator = xInterpolator4;
            yInterpolator = yInterpolator6;
            PointF pointF15 = yCp25;
            PointF pointF16 = yCp16;
            PointF pointF17 = xCp118;
            PointF xCp29 = xCp117;
            interpolator = b(cp1, cp2);
        } else if (xCp117 == null || yCp16 == null || xCp118 == null || yCp25 == null) {
            PointF xCp210 = xCp118;
            PointF xCp211 = xCp117;
            PointF pointF18 = yCp25;
            PointF pointF19 = yCp16;
            interpolator = a;
            endValue = endValue2;
            xInterpolator = xInterpolator4;
            yInterpolator = yInterpolator6;
        } else {
            xInterpolator = b(xCp117, xCp118);
            yInterpolator = b(yCp16, yCp25);
            endValue = endValue2;
            interpolator = null;
        }
        if (xInterpolator == null || yInterpolator == null) {
            keyframe = new com.airbnb.lottie.value.a<>(composition, startValue, endValue, interpolator, startFrame4, (Float) null);
        } else {
            keyframe = new com.airbnb.lottie.value.a<>(composition, startValue, endValue, xInterpolator, yInterpolator, startFrame4, (Float) null);
        }
        keyframe.o = pathCp13;
        keyframe.p = pathCp27;
        return keyframe;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: android.view.animation.Interpolator} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.view.animation.Interpolator b(android.graphics.PointF r8, android.graphics.PointF r9) {
        /*
            r0 = 0
            float r1 = r8.x
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            r3 = 1065353216(0x3f800000, float:1.0)
            float r1 = com.airbnb.lottie.utils.g.b(r1, r2, r3)
            r8.x = r1
            float r1 = r8.y
            r4 = -1027080192(0xffffffffc2c80000, float:-100.0)
            r5 = 1120403456(0x42c80000, float:100.0)
            float r1 = com.airbnb.lottie.utils.g.b(r1, r4, r5)
            r8.y = r1
            float r1 = r9.x
            float r1 = com.airbnb.lottie.utils.g.b(r1, r2, r3)
            r9.x = r1
            float r1 = r9.y
            float r1 = com.airbnb.lottie.utils.g.b(r1, r4, r5)
            r9.y = r1
            float r2 = r8.x
            float r4 = r8.y
            float r5 = r9.x
            int r1 = com.airbnb.lottie.utils.h.i(r2, r4, r5, r1)
            boolean r2 = com.airbnb.lottie.b0.c()
            if (r2 == 0) goto L_0x003b
            r2 = 0
            goto L_0x003f
        L_0x003b:
            java.lang.ref.WeakReference r2 = a(r1)
        L_0x003f:
            if (r2 == 0) goto L_0x0048
            java.lang.Object r4 = r2.get()
            r0 = r4
            android.view.animation.Interpolator r0 = (android.view.animation.Interpolator) r0
        L_0x0048:
            if (r2 == 0) goto L_0x004c
            if (r0 != 0) goto L_0x0093
        L_0x004c:
            float r4 = r8.x     // Catch:{ IllegalArgumentException -> 0x005a }
            float r5 = r8.y     // Catch:{ IllegalArgumentException -> 0x005a }
            float r6 = r9.x     // Catch:{ IllegalArgumentException -> 0x005a }
            float r7 = r9.y     // Catch:{ IllegalArgumentException -> 0x005a }
            android.view.animation.Interpolator r3 = androidx.core.view.animation.PathInterpolatorCompat.create(r4, r5, r6, r7)     // Catch:{ IllegalArgumentException -> 0x005a }
            r0 = r3
            goto L_0x0083
        L_0x005a:
            r4 = move-exception
            java.lang.String r5 = r4.getMessage()
            java.lang.String r6 = "The Path cannot loop back on itself."
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L_0x007d
            float r5 = r8.x
            float r3 = java.lang.Math.min(r5, r3)
            float r5 = r8.y
            float r6 = r9.x
            r7 = 0
            float r6 = java.lang.Math.max(r6, r7)
            float r7 = r9.y
            android.view.animation.Interpolator r0 = androidx.core.view.animation.PathInterpolatorCompat.create(r3, r5, r6, r7)
            goto L_0x0083
        L_0x007d:
            android.view.animation.LinearInterpolator r3 = new android.view.animation.LinearInterpolator
            r3.<init>()
            r0 = r3
        L_0x0083:
            boolean r3 = com.airbnb.lottie.b0.c()
            if (r3 != 0) goto L_0x0093
            java.lang.ref.WeakReference r3 = new java.lang.ref.WeakReference     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0092 }
            r3.<init>(r0)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0092 }
            h(r1, r3)     // Catch:{ ArrayIndexOutOfBoundsException -> 0x0092 }
            goto L_0x0093
        L_0x0092:
            r3 = move-exception
        L_0x0093:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.parser.t.b(android.graphics.PointF, android.graphics.PointF):android.view.animation.Interpolator");
    }

    private static <T> com.airbnb.lottie.value.a<T> f(a reader, float scale, n0<T> valueParser) {
        return new com.airbnb.lottie.value.a<>(valueParser.a(reader, scale));
    }
}
