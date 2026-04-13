package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.animation.keyframe.a;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.content.j;
import com.airbnb.lottie.model.content.s;
import com.airbnb.lottie.model.e;
import com.airbnb.lottie.model.layer.b;
import com.airbnb.lottie.utils.g;
import com.airbnb.lottie.value.c;
import java.util.List;

/* compiled from: PolystarContent */
public class n implements m, a.b, k {
    private final Path a = new Path();
    private final String b;
    private final e0 c;
    private final j.a d;
    private final boolean e;
    private final boolean f;
    private final com.airbnb.lottie.animation.keyframe.a<?, Float> g;
    private final com.airbnb.lottie.animation.keyframe.a<?, PointF> h;
    private final com.airbnb.lottie.animation.keyframe.a<?, Float> i;
    @Nullable
    private final com.airbnb.lottie.animation.keyframe.a<?, Float> j;
    private final com.airbnb.lottie.animation.keyframe.a<?, Float> k;
    @Nullable
    private final com.airbnb.lottie.animation.keyframe.a<?, Float> l;
    private final com.airbnb.lottie.animation.keyframe.a<?, Float> m;
    private final b n = new b();
    private boolean o;

    public n(e0 lottieDrawable, b layer, j polystarShape) {
        this.c = lottieDrawable;
        this.b = polystarShape.d();
        j.a j2 = polystarShape.j();
        this.d = j2;
        this.e = polystarShape.k();
        this.f = polystarShape.l();
        com.airbnb.lottie.animation.keyframe.a<Float, Float> j3 = polystarShape.g().j();
        this.g = j3;
        com.airbnb.lottie.animation.keyframe.a<PointF, PointF> j4 = polystarShape.h().j();
        this.h = j4;
        com.airbnb.lottie.animation.keyframe.a<Float, Float> j5 = polystarShape.i().j();
        this.i = j5;
        com.airbnb.lottie.animation.keyframe.a<Float, Float> j6 = polystarShape.e().j();
        this.k = j6;
        com.airbnb.lottie.animation.keyframe.a<Float, Float> j7 = polystarShape.f().j();
        this.m = j7;
        j.a aVar = j.a.STAR;
        if (j2 == aVar) {
            this.j = polystarShape.b().j();
            this.l = polystarShape.c().j();
        } else {
            this.j = null;
            this.l = null;
        }
        layer.g(j3);
        layer.g(j4);
        layer.g(j5);
        layer.g(j6);
        layer.g(j7);
        if (j2 == aVar) {
            layer.g(this.j);
            layer.g(this.l);
        }
        j3.a(this);
        j4.a(this);
        j5.a(this);
        j6.a(this);
        j7.a(this);
        if (j2 == aVar) {
            this.j.a(this);
            this.l.a(this);
        }
    }

    public void a() {
        j();
    }

    private void j() {
        this.o = false;
        this.c.invalidateSelf();
    }

    public void b(List<c> contentsBefore, List<c> list) {
        for (int i2 = 0; i2 < contentsBefore.size(); i2++) {
            c content = contentsBefore.get(i2);
            if ((content instanceof u) && ((u) content).j() == s.a.SIMULTANEOUSLY) {
                u trimPath = (u) content;
                this.n.a(trimPath);
                trimPath.d(this);
            }
        }
    }

    public Path getPath() {
        if (this.o) {
            return this.a;
        }
        this.a.reset();
        if (this.e) {
            this.o = true;
            return this.a;
        }
        switch (a.a[this.d.ordinal()]) {
            case 1:
                i();
                break;
            case 2:
                g();
                break;
        }
        this.a.close();
        this.n.b(this.a);
        this.o = true;
        return this.a;
    }

    /* compiled from: PolystarContent */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[j.a.values().length];
            a = iArr;
            try {
                iArr[j.a.STAR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[j.a.POLYGON.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public String getName() {
        return this.b;
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x012c  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0150  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0167  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x023f  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0241  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void i() {
        /*
            r47 = this;
            r0 = r47
            com.airbnb.lottie.animation.keyframe.a<?, java.lang.Float> r1 = r0.g
            java.lang.Object r1 = r1.h()
            java.lang.Float r1 = (java.lang.Float) r1
            float r1 = r1.floatValue()
            com.airbnb.lottie.animation.keyframe.a<?, java.lang.Float> r2 = r0.i
            if (r2 != 0) goto L_0x0015
            r2 = 0
            goto L_0x0020
        L_0x0015:
            java.lang.Object r2 = r2.h()
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            double r2 = (double) r2
        L_0x0020:
            r4 = 4636033603912859648(0x4056800000000000, double:90.0)
            double r2 = r2 - r4
            double r2 = java.lang.Math.toRadians(r2)
            r4 = 4618760256179416344(0x401921fb54442d18, double:6.283185307179586)
            double r6 = (double) r1
            double r4 = r4 / r6
            float r4 = (float) r4
            boolean r5 = r0.f
            if (r5 == 0) goto L_0x0039
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r4 = r4 * r5
        L_0x0039:
            r5 = 1073741824(0x40000000, float:2.0)
            float r6 = r4 / r5
            int r7 = (int) r1
            float r7 = (float) r7
            float r7 = r1 - r7
            r8 = 0
            int r9 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r9 == 0) goto L_0x004c
            r9 = 1065353216(0x3f800000, float:1.0)
            float r9 = r9 - r7
            float r9 = r9 * r6
            double r9 = (double) r9
            double r2 = r2 + r9
        L_0x004c:
            com.airbnb.lottie.animation.keyframe.a<?, java.lang.Float> r9 = r0.k
            java.lang.Object r9 = r9.h()
            java.lang.Float r9 = (java.lang.Float) r9
            float r9 = r9.floatValue()
            com.airbnb.lottie.animation.keyframe.a<?, java.lang.Float> r10 = r0.j
            java.lang.Object r10 = r10.h()
            java.lang.Float r10 = (java.lang.Float) r10
            float r10 = r10.floatValue()
            r11 = 0
            com.airbnb.lottie.animation.keyframe.a<?, java.lang.Float> r12 = r0.l
            r13 = 1120403456(0x42c80000, float:100.0)
            if (r12 == 0) goto L_0x0077
            java.lang.Object r12 = r12.h()
            java.lang.Float r12 = (java.lang.Float) r12
            float r12 = r12.floatValue()
            float r11 = r12 / r13
        L_0x0077:
            r12 = 0
            com.airbnb.lottie.animation.keyframe.a<?, java.lang.Float> r14 = r0.m
            if (r14 == 0) goto L_0x0088
            java.lang.Object r14 = r14.h()
            java.lang.Float r14 = (java.lang.Float) r14
            float r14 = r14.floatValue()
            float r12 = r14 / r13
        L_0x0088:
            r13 = 0
            int r14 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r14 == 0) goto L_0x00b5
            float r14 = r9 - r10
            float r14 = r14 * r7
            float r13 = r10 + r14
            double r14 = (double) r13
            double r16 = java.lang.Math.cos(r2)
            double r14 = r14 * r16
            float r14 = (float) r14
            r16 = r9
            double r8 = (double) r13
            double r17 = java.lang.Math.sin(r2)
            double r8 = r8 * r17
            float r8 = (float) r8
            android.graphics.Path r9 = r0.a
            r9.moveTo(r14, r8)
            float r9 = r4 * r7
            float r9 = r9 / r5
            r18 = r6
            double r5 = (double) r9
            double r2 = r2 + r5
            r5 = r16
            r6 = r18
            goto L_0x00d8
        L_0x00b5:
            r18 = r6
            r16 = r9
            r5 = r16
            double r8 = (double) r5
            double r19 = java.lang.Math.cos(r2)
            double r8 = r8 * r19
            float r14 = (float) r8
            double r8 = (double) r5
            double r19 = java.lang.Math.sin(r2)
            double r8 = r8 * r19
            float r8 = (float) r8
            android.graphics.Path r6 = r0.a
            r6.moveTo(r14, r8)
            r16 = r8
            r6 = r18
            double r8 = (double) r6
            double r2 = r2 + r8
            r8 = r16
        L_0x00d8:
            r9 = 0
            r18 = r2
            double r2 = (double) r1
            double r2 = java.lang.Math.ceil(r2)
            r20 = 4611686018427387904(0x4000000000000000, double:2.0)
            double r2 = r2 * r20
            r16 = 0
            r15 = r16
        L_0x00e8:
            r22 = r1
            double r0 = (double) r15
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 >= 0) goto L_0x0256
            if (r9 == 0) goto L_0x00f3
            r0 = r5
            goto L_0x00f4
        L_0x00f3:
            r0 = r10
        L_0x00f4:
            r1 = r6
            r16 = 0
            int r23 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            if (r23 == 0) goto L_0x010f
            r23 = r0
            r24 = r1
            double r0 = (double) r15
            double r25 = r2 - r20
            int r0 = (r0 > r25 ? 1 : (r0 == r25 ? 0 : -1))
            if (r0 != 0) goto L_0x010c
            float r0 = r4 * r7
            r1 = 1073741824(0x40000000, float:2.0)
            float r0 = r0 / r1
            goto L_0x0117
        L_0x010c:
            r1 = 1073741824(0x40000000, float:2.0)
            goto L_0x0115
        L_0x010f:
            r23 = r0
            r24 = r1
            r1 = 1073741824(0x40000000, float:2.0)
        L_0x0115:
            r0 = r24
        L_0x0117:
            r16 = 0
            int r17 = (r13 > r16 ? 1 : (r13 == r16 ? 0 : -1))
            r24 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            if (r17 == 0) goto L_0x012c
            r17 = r4
            r26 = r5
            double r4 = (double) r15
            double r27 = r2 - r24
            int r4 = (r4 > r27 ? 1 : (r4 == r27 ? 0 : -1))
            if (r4 != 0) goto L_0x0130
            r4 = r13
            goto L_0x0132
        L_0x012c:
            r17 = r4
            r26 = r5
        L_0x0130:
            r4 = r23
        L_0x0132:
            r5 = r14
            r23 = r8
            r27 = r2
            double r1 = (double) r4
            double r29 = java.lang.Math.cos(r18)
            double r1 = r1 * r29
            float r14 = (float) r1
            double r1 = (double) r4
            double r29 = java.lang.Math.sin(r18)
            double r1 = r1 * r29
            float r8 = (float) r1
            r1 = 0
            int r2 = (r11 > r1 ? 1 : (r11 == r1 ? 0 : -1))
            if (r2 != 0) goto L_0x0167
            int r2 = (r12 > r1 ? 1 : (r12 == r1 ? 0 : -1))
            if (r2 != 0) goto L_0x0167
            r2 = r47
            android.graphics.Path r1 = r2.a
            r1.lineTo(r14, r8)
            r39 = r0
            r36 = r10
            r37 = r11
            r38 = r12
            r40 = r23
            r16 = 0
            r23 = r4
            goto L_0x0238
        L_0x0167:
            r2 = r47
            r1 = r23
            r23 = r4
            double r3 = (double) r1
            r36 = r10
            r37 = r11
            double r10 = (double) r5
            double r3 = java.lang.Math.atan2(r3, r10)
            r10 = 4609753056924675352(0x3ff921fb54442d18, double:1.5707963267948966)
            double r3 = r3 - r10
            float r3 = (float) r3
            double r10 = (double) r3
            double r10 = java.lang.Math.cos(r10)
            float r4 = (float) r10
            double r10 = (double) r3
            double r10 = java.lang.Math.sin(r10)
            float r10 = (float) r10
            r38 = r12
            double r11 = (double) r8
            r39 = r0
            r40 = r1
            double r0 = (double) r14
            double r0 = java.lang.Math.atan2(r11, r0)
            r11 = 4609753056924675352(0x3ff921fb54442d18, double:1.5707963267948966)
            double r0 = r0 - r11
            float r0 = (float) r0
            double r11 = (double) r0
            double r11 = java.lang.Math.cos(r11)
            float r1 = (float) r11
            double r11 = (double) r0
            double r11 = java.lang.Math.sin(r11)
            float r11 = (float) r11
            if (r9 == 0) goto L_0x01ae
            r12 = r37
            goto L_0x01b0
        L_0x01ae:
            r12 = r38
        L_0x01b0:
            if (r9 == 0) goto L_0x01b5
            r29 = r38
            goto L_0x01b7
        L_0x01b5:
            r29 = r37
        L_0x01b7:
            r41 = r29
            if (r9 == 0) goto L_0x01be
            r29 = r36
            goto L_0x01c0
        L_0x01be:
            r29 = r26
        L_0x01c0:
            r42 = r29
            if (r9 == 0) goto L_0x01c7
            r29 = r26
            goto L_0x01c9
        L_0x01c7:
            r29 = r36
        L_0x01c9:
            r43 = r29
            float r29 = r42 * r12
            r30 = 1056236141(0x3ef4e26d, float:0.47829)
            float r29 = r29 * r30
            float r29 = r29 * r4
            float r31 = r42 * r12
            float r31 = r31 * r30
            float r31 = r31 * r10
            float r32 = r43 * r41
            float r32 = r32 * r30
            float r32 = r32 * r1
            float r33 = r43 * r41
            float r33 = r33 * r30
            float r33 = r33 * r11
            r16 = 0
            int r30 = (r7 > r16 ? 1 : (r7 == r16 ? 0 : -1))
            if (r30 == 0) goto L_0x0217
            if (r15 != 0) goto L_0x01ff
            float r29 = r29 * r7
            float r31 = r31 * r7
            r44 = r0
            r45 = r1
            r0 = r29
            r1 = r31
            r24 = r32
            r25 = r33
            goto L_0x0223
        L_0x01ff:
            r44 = r0
            r45 = r1
            double r0 = (double) r15
            double r24 = r27 - r24
            int r0 = (r0 > r24 ? 1 : (r0 == r24 ? 0 : -1))
            if (r0 != 0) goto L_0x021b
            float r32 = r32 * r7
            float r33 = r33 * r7
            r0 = r29
            r1 = r31
            r24 = r32
            r25 = r33
            goto L_0x0223
        L_0x0217:
            r44 = r0
            r45 = r1
        L_0x021b:
            r0 = r29
            r1 = r31
            r24 = r32
            r25 = r33
        L_0x0223:
            r46 = r3
            android.graphics.Path r3 = r2.a
            float r30 = r5 - r0
            float r31 = r40 - r1
            float r32 = r14 + r24
            float r33 = r8 + r25
            r29 = r3
            r34 = r14
            r35 = r8
            r29.cubicTo(r30, r31, r32, r33, r34, r35)
        L_0x0238:
            r0 = r39
            double r3 = (double) r0
            double r18 = r18 + r3
            if (r9 != 0) goto L_0x0241
            r1 = 1
            goto L_0x0242
        L_0x0241:
            r1 = 0
        L_0x0242:
            r9 = r1
            int r15 = r15 + 1
            r0 = r2
            r4 = r17
            r1 = r22
            r5 = r26
            r2 = r27
            r10 = r36
            r11 = r37
            r12 = r38
            goto L_0x00e8
        L_0x0256:
            r27 = r2
            r17 = r4
            r2 = r47
            com.airbnb.lottie.animation.keyframe.a<?, android.graphics.PointF> r0 = r2.h
            java.lang.Object r0 = r0.h()
            android.graphics.PointF r0 = (android.graphics.PointF) r0
            android.graphics.Path r1 = r2.a
            float r3 = r0.x
            float r4 = r0.y
            r1.offset(r3, r4)
            android.graphics.Path r1 = r2.a
            r1.close()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.n.i():void");
    }

    private void g() {
        float anglePerPoint;
        double currentAngle;
        double numPoints;
        int points;
        int points2 = (int) Math.floor((double) this.g.h().floatValue());
        com.airbnb.lottie.animation.keyframe.a<?, Float> aVar = this.i;
        double currentAngle2 = Math.toRadians((aVar == null ? 0.0d : (double) aVar.h().floatValue()) - 90.0d);
        float anglePerPoint2 = (float) (6.283185307179586d / ((double) points2));
        float roundedness = this.m.h().floatValue() / 100.0f;
        float radius = this.k.h().floatValue();
        float x = (float) (((double) radius) * Math.cos(currentAngle2));
        float y = (float) (((double) radius) * Math.sin(currentAngle2));
        this.a.moveTo(x, y);
        double currentAngle3 = currentAngle2 + ((double) anglePerPoint2);
        double numPoints2 = Math.ceil((double) points2);
        int i2 = 0;
        while (((double) i2) < numPoints2) {
            float previousX = x;
            float previousY = y;
            x = (float) (((double) radius) * Math.cos(currentAngle3));
            y = (float) (((double) radius) * Math.sin(currentAngle3));
            if (roundedness != 0.0f) {
                numPoints = numPoints2;
                float cp1Theta = (float) (Math.atan2((double) previousY, (double) previousX) - 1.5707963267948966d);
                float cp1Dx = (float) Math.cos((double) cp1Theta);
                points = points2;
                currentAngle = currentAngle3;
                anglePerPoint = anglePerPoint2;
                float cp2Theta = (float) (Math.atan2((double) y, (double) x) - 1.5707963267948966d);
                float f2 = cp2Theta;
                this.a.cubicTo(previousX - (((radius * roundedness) * 0.25f) * cp1Dx), previousY - (((radius * roundedness) * 0.25f) * ((float) Math.sin((double) cp1Theta))), x + (radius * roundedness * 0.25f * ((float) Math.cos((double) cp2Theta))), y + (radius * roundedness * 0.25f * ((float) Math.sin((double) cp2Theta))), x, y);
            } else {
                points = points2;
                currentAngle = currentAngle3;
                anglePerPoint = anglePerPoint2;
                numPoints = numPoints2;
                this.a.lineTo(x, y);
            }
            float anglePerPoint3 = anglePerPoint;
            currentAngle3 = currentAngle + ((double) anglePerPoint3);
            i2++;
            anglePerPoint2 = anglePerPoint3;
            points2 = points;
            numPoints2 = numPoints;
        }
        double d2 = currentAngle3;
        float f3 = anglePerPoint2;
        double d3 = numPoints2;
        PointF position = this.h.h();
        this.a.offset(position.x, position.y);
        this.a.close();
    }

    public void e(e keyPath, int depth, List<e> accumulator, e currentPartialKeyPath) {
        g.k(keyPath, depth, accumulator, currentPartialKeyPath, this);
    }

    public <T> void d(T property, @Nullable c<T> callback) {
        com.airbnb.lottie.animation.keyframe.a<?, Float> aVar;
        com.airbnb.lottie.animation.keyframe.a<?, Float> aVar2;
        if (property == j0.w) {
            this.g.n(callback);
        } else if (property == j0.x) {
            this.i.n(callback);
        } else if (property == j0.n) {
            this.h.n(callback);
        } else if (property == j0.y && (aVar2 = this.j) != null) {
            aVar2.n(callback);
        } else if (property == j0.z) {
            this.k.n(callback);
        } else if (property == j0.A && (aVar = this.l) != null) {
            aVar.n(callback);
        } else if (property == j0.B) {
            this.m.n(callback);
        }
    }
}
