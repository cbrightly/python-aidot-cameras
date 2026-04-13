package io.rmiri.skeleton;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.rmiri.skeleton.master.SkeletonMaster;
import java.util.ArrayList;

public class SkeletonViewGroup extends SkeletonMaster {
    private float A4;
    private float B4;
    private float C4;
    private ValueAnimator D4;
    /* access modifiers changed from: private */
    public float E4 = 0.0f;
    /* access modifiers changed from: private */
    public float F4 = 0.0f;
    private boolean G4 = false;
    /* access modifiers changed from: private */
    public boolean H4 = false;
    private boolean I4 = false;
    /* access modifiers changed from: private */
    public boolean J4 = false;
    private Canvas a1;
    private Bitmap a2;
    ArrayList<io.rmiri.skeleton.master.b> f = new ArrayList<>();
    private Paint p0;
    private Canvas p1;
    private Bitmap p2;
    private Path p3 = new Path();
    /* access modifiers changed from: private */
    public int[] p4;
    private ArrayList<io.rmiri.skeleton.master.b> q;
    private c x;
    int y = -1;
    private Paint z;
    private float z4;

    public interface c {
        void a();

        void b();
    }

    public SkeletonViewGroup(@NonNull Context context) {
        super(context);
    }

    public SkeletonViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SkeletonViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void b(Context context, @Nullable AttributeSet attrs) {
        super.b(context, attrs);
        this.p4 = io.rmiri.skeleton.utils.b.b(this.c.h(), this.c.g());
    }

    /* JADX WARNING: type inference failed for: r9v14, types: [android.view.ViewParent] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onLayout(boolean r20, int r21, int r22, int r23, int r24) {
        /*
            r19 = this;
            r0 = r19
            super.onLayout(r20, r21, r22, r23, r24)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "SkeletonGroup onLayout "
            r1.append(r2)
            int r2 = r0.d
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            io.rmiri.skeleton.utils.a.a(r1)
            java.util.ArrayList<io.rmiri.skeleton.master.b> r1 = r0.q
            java.lang.String r2 = "SkeletonGroup onLayout and skeletonModelsChildren == null ... "
            if (r1 != 0) goto L_0x01e5
            java.util.ArrayList<io.rmiri.skeleton.master.b> r1 = r0.f
            if (r1 == 0) goto L_0x01e5
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x01e5
            io.rmiri.skeleton.master.b r1 = r0.c
            boolean r1 = r1.I()
            if (r1 == 0) goto L_0x01e5
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            int r2 = r0.d
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            io.rmiri.skeleton.utils.a.a(r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r0.q = r1
            android.view.ViewParent r1 = r19.getParent()
            boolean r1 = r1 instanceof android.view.ViewGroup
            java.lang.String r2 = "SkeletonView"
            if (r1 == 0) goto L_0x0085
            android.view.ViewParent r1 = r19.getParent()
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            int r1 = r1.getId()
            r0.y = r1
            r4 = -1
            if (r1 != r4) goto L_0x0085
            java.lang.String r1 = "Parent need to ID"
            android.util.Log.e(r2, r1)
            android.view.ViewParent r1 = r19.getParent()
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            int r4 = android.view.View.generateViewId()
            r1.setId(r4)
            android.view.ViewParent r1 = r19.getParent()
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
            int r1 = r1.getId()
            r0.y = r1
        L_0x0085:
            java.util.ArrayList<io.rmiri.skeleton.master.b> r1 = r0.f
            java.util.Iterator r1 = r1.iterator()
        L_0x008b:
            boolean r4 = r1.hasNext()
            if (r4 == 0) goto L_0x01d8
            java.lang.Object r4 = r1.next()
            io.rmiri.skeleton.master.b r4 = (io.rmiri.skeleton.master.b) r4
            android.view.View r5 = r4.w()
            if (r5 == 0) goto L_0x00aa
            java.lang.String r5 = "use start and end views"
            android.util.Log.i(r2, r5)
            android.view.View r5 = r4.w()
            r4.O(r5)
        L_0x00aa:
            android.view.View r5 = r4.e()
            int r5 = r5.getLeft()
            float r5 = (float) r5
            android.view.View r6 = r4.e()
            int r6 = r6.getTop()
            float r6 = (float) r6
            r7 = 0
            r8 = 0
            android.view.View r9 = r4.e()
            android.util.Pair r9 = r0.n(r9)
            java.lang.Object r10 = r9.first
            java.lang.Float r10 = (java.lang.Float) r10
            float r10 = r10.floatValue()
            float r5 = r5 + r10
            java.lang.Object r10 = r9.second
            java.lang.Float r10 = (java.lang.Float) r10
            float r10 = r10.floatValue()
            float r6 = r6 + r10
            float r10 = r4.q()
            r11 = 1
            int r10 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r10 == 0) goto L_0x00e9
            float r10 = r4.q()
            r12 = r10
            r13 = r10
            r14 = r10
            goto L_0x0123
        L_0x00e9:
            float r10 = r4.u()
            int r10 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r10 == 0) goto L_0x00f6
            float r10 = r4.u()
            goto L_0x00f7
        L_0x00f6:
            r10 = 0
        L_0x00f7:
            float r12 = r4.s()
            int r12 = (r12 > r11 ? 1 : (r12 == r11 ? 0 : -1))
            if (r12 == 0) goto L_0x0104
            float r12 = r4.s()
            goto L_0x0105
        L_0x0104:
            r12 = 0
        L_0x0105:
            r14 = r12
            float r12 = r4.t()
            int r12 = (r12 > r11 ? 1 : (r12 == r11 ? 0 : -1))
            if (r12 == 0) goto L_0x0113
            float r12 = r4.t()
            goto L_0x0114
        L_0x0113:
            r12 = 0
        L_0x0114:
            r13 = r12
            float r12 = r4.r()
            int r12 = (r12 > r11 ? 1 : (r12 == r11 ? 0 : -1))
            if (r12 == 0) goto L_0x0122
            float r12 = r4.r()
            goto L_0x0123
        L_0x0122:
            r12 = 0
        L_0x0123:
            boolean r15 = r4.H()
            if (r15 == 0) goto L_0x013d
            int r11 = r19.getMeasuredWidth()
            float r11 = (float) r11
            float r11 = r11 + r5
            float r11 = r11 - r13
            int r7 = r19.getMeasuredHeight()
            float r7 = (float) r7
            float r7 = r7 + r6
            float r7 = r7 - r12
            r16 = r1
            r18 = r2
            goto L_0x01bf
        L_0x013d:
            android.view.View r15 = r4.p()
            if (r15 == 0) goto L_0x018c
            android.view.View r11 = r4.p()
            int r11 = r11.getLeft()
            float r11 = (float) r11
            android.view.View r15 = r4.p()
            int r15 = r15.getTop()
            float r15 = (float) r15
            android.view.View r3 = r4.p()
            android.util.Pair r3 = r0.n(r3)
            r16 = r1
            java.lang.Object r1 = r3.first
            java.lang.Float r1 = (java.lang.Float) r1
            float r1 = r1.floatValue()
            float r1 = r1 + r11
            android.view.View r17 = r4.p()
            r18 = r2
            int r2 = r17.getMeasuredWidth()
            float r2 = (float) r2
            float r1 = r1 + r2
            float r1 = r1 - r13
            java.lang.Object r2 = r3.second
            java.lang.Float r2 = (java.lang.Float) r2
            float r2 = r2.floatValue()
            float r2 = r2 + r15
            android.view.View r7 = r4.p()
            int r7 = r7.getMeasuredHeight()
            float r7 = (float) r7
            float r2 = r2 + r7
            float r7 = r2 - r12
            r11 = r1
            goto L_0x01bf
        L_0x018c:
            r16 = r1
            r18 = r2
            float r1 = r4.o()
            int r1 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r1 == 0) goto L_0x01a7
            float r1 = r4.o()
            float r1 = r1 + r5
            float r11 = r1 - r13
            float r1 = r4.n()
            float r1 = r1 + r6
            float r7 = r1 - r12
            goto L_0x01bf
        L_0x01a7:
            android.view.View r1 = r4.e()
            int r1 = r1.getMeasuredWidth()
            float r1 = (float) r1
            float r1 = r1 + r5
            float r11 = r1 - r13
            android.view.View r1 = r4.e()
            int r1 = r1.getMeasuredHeight()
            float r1 = (float) r1
            float r1 = r1 + r6
            float r7 = r1 - r12
        L_0x01bf:
            float r5 = r5 + r14
            float r6 = r6 + r10
            r4.j0(r5)
            r4.l0(r6)
            r4.k0(r11)
            r4.m0(r7)
            java.util.ArrayList<io.rmiri.skeleton.master.b> r1 = r0.q
            r1.add(r4)
            r1 = r16
            r2 = r18
            goto L_0x008b
        L_0x01d8:
            io.rmiri.skeleton.master.b r1 = r0.c
            boolean r1 = r1.F()
            if (r1 == 0) goto L_0x02d7
            r19.u()
            goto L_0x02d7
        L_0x01e5:
            java.util.ArrayList<io.rmiri.skeleton.master.b> r1 = r0.q
            if (r1 != 0) goto L_0x02d7
            io.rmiri.skeleton.master.b r1 = r0.c
            boolean r1 = r1.I()
            if (r1 == 0) goto L_0x02d7
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r2)
            int r2 = r0.d
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            io.rmiri.skeleton.utils.a.a(r1)
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r0.q = r1
            r1 = 0
            android.view.View r1 = r0.getChildAt(r1)
            java.util.ArrayList r1 = r0.a(r1)
            java.util.Iterator r1 = r1.iterator()
        L_0x0219:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x02cc
            java.lang.Object r2 = r1.next()
            android.view.View r2 = (android.view.View) r2
            if (r2 == 0) goto L_0x02ca
            boolean r3 = r2 instanceof io.rmiri.skeleton.SkeletonView
            if (r3 == 0) goto L_0x02ca
            r3 = r2
            io.rmiri.skeleton.SkeletonView r3 = (io.rmiri.skeleton.SkeletonView) r3
            io.rmiri.skeleton.master.b r3 = r3.getSkeletonModel()
            int r4 = r2.getLeft()
            float r4 = (float) r4
            int r5 = r2.getTop()
            float r5 = (float) r5
            r6 = 0
            r7 = 0
            android.view.ViewParent r8 = r2.getParent()
            android.view.View r8 = (android.view.View) r8
        L_0x0244:
            if (r8 == 0) goto L_0x025e
            boolean r9 = r8 instanceof io.rmiri.skeleton.SkeletonViewGroup
            if (r9 != 0) goto L_0x025e
            int r9 = r8.getLeft()
            float r9 = (float) r9
            float r4 = r4 + r9
            int r9 = r8.getTop()
            float r9 = (float) r9
            float r5 = r5 + r9
            android.view.ViewParent r9 = r8.getParent()
            r8 = r9
            android.view.View r8 = (android.view.View) r8
            goto L_0x0244
        L_0x025e:
            float r9 = r3.q()
            r10 = -822083584(0xffffffffcf000000, float:-2.14748365E9)
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 == 0) goto L_0x0270
            float r9 = r3.q()
            r10 = r9
            r11 = r9
            r12 = r9
            goto L_0x02a9
        L_0x0270:
            float r9 = r3.u()
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 == 0) goto L_0x027d
            float r9 = r3.u()
            goto L_0x027e
        L_0x027d:
            r9 = 0
        L_0x027e:
            float r11 = r3.s()
            int r11 = (r11 > r10 ? 1 : (r11 == r10 ? 0 : -1))
            if (r11 == 0) goto L_0x028b
            float r11 = r3.s()
            goto L_0x028c
        L_0x028b:
            r11 = 0
        L_0x028c:
            r12 = r11
            float r11 = r3.t()
            int r11 = (r11 > r10 ? 1 : (r11 == r10 ? 0 : -1))
            if (r11 == 0) goto L_0x029a
            float r11 = r3.t()
            goto L_0x029b
        L_0x029a:
            r11 = 0
        L_0x029b:
            float r13 = r3.r()
            int r10 = (r13 > r10 ? 1 : (r13 == r10 ? 0 : -1))
            if (r10 == 0) goto L_0x02a8
            float r10 = r3.r()
            goto L_0x02a9
        L_0x02a8:
            r10 = 0
        L_0x02a9:
            int r13 = r2.getMeasuredWidth()
            float r13 = (float) r13
            float r13 = r13 + r4
            float r13 = r13 - r11
            int r6 = r2.getMeasuredHeight()
            float r6 = (float) r6
            float r6 = r6 + r5
            float r6 = r6 - r10
            float r4 = r4 + r12
            float r5 = r5 + r9
            r3.j0(r4)
            r3.l0(r5)
            r3.k0(r13)
            r3.m0(r6)
            java.util.ArrayList<io.rmiri.skeleton.master.b> r7 = r0.q
            r7.add(r3)
        L_0x02ca:
            goto L_0x0219
        L_0x02cc:
            io.rmiri.skeleton.master.b r1 = r0.c
            boolean r1 = r1.F()
            if (r1 == 0) goto L_0x02d7
            r19.u()
        L_0x02d7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.rmiri.skeleton.SkeletonViewGroup.onLayout(boolean, int, int, int, int):void");
    }

    /* JADX WARNING: type inference failed for: r3v7, types: [android.view.ViewParent] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.util.Pair<java.lang.Float, java.lang.Float> n(android.view.View r8) {
        /*
            r7 = this;
            if (r8 != 0) goto L_0x0019
            java.lang.String r0 = "SkeletonView"
            java.lang.String r1 = "view is null"
            android.util.Log.i(r0, r1)
            android.util.Pair r0 = new android.util.Pair
            r1 = 0
            java.lang.Float r2 = java.lang.Float.valueOf(r1)
            java.lang.Float r1 = java.lang.Float.valueOf(r1)
            r0.<init>(r2, r1)
            return r0
        L_0x0019:
            r0 = 0
            r1 = 0
            android.view.ViewParent r2 = r8.getParent()
            android.view.View r2 = (android.view.View) r2
        L_0x0021:
            if (r2 == 0) goto L_0x003f
            int r3 = r2.getId()
            int r4 = r7.y
            if (r3 == r4) goto L_0x003f
            int r3 = r2.getLeft()
            float r3 = (float) r3
            float r0 = r0 + r3
            int r3 = r2.getTop()
            float r3 = (float) r3
            float r1 = r1 + r3
            android.view.ViewParent r3 = r2.getParent()
            r2 = r3
            android.view.View r2 = (android.view.View) r2
            goto L_0x0021
        L_0x003f:
            android.view.ViewParent r3 = r7.getParent()
            android.view.View r3 = (android.view.View) r3
            int r4 = r3.getPaddingLeft()
            float r4 = (float) r4
            float r0 = r0 - r4
            int r4 = r3.getPaddingTop()
            float r4 = (float) r4
            float r1 = r1 - r4
            android.util.Pair r4 = new android.util.Pair
            java.lang.Float r5 = java.lang.Float.valueOf(r0)
            java.lang.Float r6 = java.lang.Float.valueOf(r1)
            r4.<init>(r5, r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: io.rmiri.skeleton.SkeletonViewGroup.n(android.view.View):android.util.Pair");
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        float newY1;
        Canvas canvas2 = canvas;
        super.dispatchDraw(canvas);
        if (this.G4) {
            int i = 3;
            if (this.z == null) {
                int i2 = 1;
                Paint paint = new Paint(1);
                this.p0 = paint;
                paint.setStyle(Paint.Style.FILL);
                this.p0.setColor(this.c.g());
                Paint paint2 = new Paint(1);
                this.z = paint2;
                paint2.setStyle(Paint.Style.FILL);
                Bitmap.Config bitmapConfig = Bitmap.Config.ALPHA_8;
                this.p2 = Bitmap.createBitmap(getWidth(), getHeight(), bitmapConfig);
                this.p1 = new Canvas(this.p2);
                this.a2 = Bitmap.createBitmap(getWidth(), getHeight(), bitmapConfig);
                this.a1 = new Canvas(this.a2);
                int i3 = 0;
                while (i3 < this.q.size()) {
                    io.rmiri.skeleton.master.b skeletonAttributeChild = this.q.get(i3);
                    if (skeletonAttributeChild.v() == i2) {
                        RectF rectangleRect = new RectF(skeletonAttributeChild.B(), skeletonAttributeChild.D(), skeletonAttributeChild.C(), skeletonAttributeChild.E());
                        this.p3.addRoundRect(rectangleRect, q(rectangleRect, skeletonAttributeChild), Path.Direction.CW);
                    } else {
                        int i4 = 2;
                        if (skeletonAttributeChild.v() == 2) {
                            this.p3.addOval(new RectF(skeletonAttributeChild.B(), skeletonAttributeChild.D(), skeletonAttributeChild.C(), skeletonAttributeChild.E()), Path.Direction.CW);
                        } else if (skeletonAttributeChild.v() == i) {
                            float lineHeight = (float) skeletonAttributeChild.x();
                            float lineSpaceVertical = (float) skeletonAttributeChild.A();
                            int lineNumber = skeletonAttributeChild.z();
                            if (lineNumber == 0) {
                                lineNumber = (int) ((skeletonAttributeChild.E() - skeletonAttributeChild.D()) / (lineHeight + lineSpaceVertical));
                            }
                            io.rmiri.skeleton.utils.a.a("line number  " + lineNumber);
                            int j = 0;
                            while (j < lineNumber) {
                                float newX1 = skeletonAttributeChild.B();
                                float newX2 = skeletonAttributeChild.C();
                                if (j == 0) {
                                    newY1 = skeletonAttributeChild.D();
                                } else {
                                    newY1 = skeletonAttributeChild.D() + (((float) j) * lineHeight) + (((float) j) * lineSpaceVertical);
                                }
                                float newY2 = skeletonAttributeChild.D() + (((float) (j + 1)) * lineHeight) + (((float) j) * lineSpaceVertical);
                                if (j == lineNumber - 1) {
                                    float quarterWidth = (skeletonAttributeChild.C() - skeletonAttributeChild.B()) / 4.0f;
                                    switch (skeletonAttributeChild.y()) {
                                        case 2:
                                            if (this.c.a() != i4) {
                                                newX2 = skeletonAttributeChild.C() - quarterWidth;
                                                break;
                                            } else {
                                                newX1 = skeletonAttributeChild.B() + quarterWidth;
                                                break;
                                            }
                                        case 3:
                                            if (this.c.a() != i4) {
                                                newX2 = skeletonAttributeChild.C() - (2.0f * quarterWidth);
                                                break;
                                            } else {
                                                newX1 = skeletonAttributeChild.B() + (2.0f * quarterWidth);
                                                break;
                                            }
                                        case 4:
                                            if (this.c.a() != i4) {
                                                newX2 = skeletonAttributeChild.C() - (3.0f * quarterWidth);
                                                break;
                                            } else {
                                                newX1 = skeletonAttributeChild.B() + (3.0f * quarterWidth);
                                                break;
                                            }
                                    }
                                }
                                RectF rectangleRect2 = new RectF(newX1, newY1, newX2, newY2);
                                this.p3.addRoundRect(rectangleRect2, q(rectangleRect2, skeletonAttributeChild), Path.Direction.CW);
                                j++;
                                bitmapConfig = bitmapConfig;
                                i4 = 2;
                            }
                        }
                    }
                    this.p1.drawPath(this.p3, this.p0);
                    this.a1.drawPath(this.p3, this.p0);
                    i3++;
                    bitmapConfig = bitmapConfig;
                    i = 3;
                    i2 = 1;
                }
            }
            if (this.H4) {
                switch (this.c.c()) {
                    case 1:
                        o(canvas2, io.rmiri.skeleton.utils.b.a(this.c.f(), 1.0f - this.F4));
                        this.p0.setAlpha((int) ((1.0f - this.F4) * 255.0f));
                        canvas2.drawBitmap(this.p2, 0.0f, 0.0f, this.p0);
                        return;
                    case 2:
                        float f2 = this.E4;
                        if (f2 == 1.0f || f2 == -1.0f) {
                            o(canvas2, this.c.f());
                            canvas2.drawBitmap(this.p2, 0.0f, 0.0f, this.p0);
                        }
                        r();
                        this.z.setShader(new LinearGradient(this.z4, this.A4, this.B4, this.C4, this.p4, new float[]{0.0f, 0.4f, 0.8f}, Shader.TileMode.CLAMP));
                        canvas2.drawBitmap(this.a2, 0.0f, 0.0f, this.z);
                        return;
                    default:
                        return;
                }
            } else {
                o(canvas2, this.c.f());
                canvas2.drawBitmap(this.p2, 0.0f, 0.0f, this.p0);
                switch (this.c.d()) {
                    case 1:
                        io.rmiri.skeleton.utils.a.a(0.0f + "");
                        this.z.setColor(io.rmiri.skeleton.utils.b.a(this.c.h(), Math.abs(this.E4)));
                        canvas2.drawBitmap(this.a2, 0.0f, 0.0f, this.z);
                        return;
                    case 2:
                        r();
                        this.z.setShader(new LinearGradient(this.z4, this.A4, this.B4, this.C4, this.p4, new float[]{0.0f, 0.4f, 0.8f}, Shader.TileMode.CLAMP));
                        canvas2.drawBitmap(this.a2, 0.0f, 0.0f, this.z);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private float[] q(RectF rectangleRect, io.rmiri.skeleton.master.b skeletonAttributeChild) {
        float cornerRadiusTopRight;
        float cornerRadiusBottomLRight;
        float cornerRadiusBottomLeft;
        float cornerRadiusTopLeft;
        if (skeletonAttributeChild.i() != Integer.MIN_VALUE) {
            cornerRadiusTopLeft = s(rectangleRect, (float) skeletonAttributeChild.i());
            cornerRadiusBottomLeft = cornerRadiusTopLeft;
            cornerRadiusBottomLRight = cornerRadiusTopLeft;
            cornerRadiusTopRight = cornerRadiusTopLeft;
        } else {
            float f2 = 0.0f;
            cornerRadiusTopLeft = skeletonAttributeChild.l() != Integer.MIN_VALUE ? s(rectangleRect, (float) skeletonAttributeChild.l()) : 0.0f;
            cornerRadiusTopRight = skeletonAttributeChild.m() != Integer.MIN_VALUE ? s(rectangleRect, (float) skeletonAttributeChild.m()) : 0.0f;
            float cornerRadiusBottomLRight2 = skeletonAttributeChild.j() != Integer.MIN_VALUE ? s(rectangleRect, (float) skeletonAttributeChild.j()) : 0.0f;
            if (skeletonAttributeChild.k() != Integer.MIN_VALUE) {
                f2 = s(rectangleRect, (float) skeletonAttributeChild.k());
            }
            cornerRadiusBottomLeft = f2;
            cornerRadiusBottomLRight = cornerRadiusBottomLRight2;
        }
        return new float[]{cornerRadiusTopLeft, cornerRadiusTopLeft, cornerRadiusTopRight, cornerRadiusTopRight, cornerRadiusBottomLRight, cornerRadiusBottomLRight, cornerRadiusBottomLeft, cornerRadiusBottomLeft};
    }

    private float s(RectF rectangleRect, float initCornerRadius) {
        return Math.min(Math.min(rectangleRect.width(), rectangleRect.height()) / 2.0f, initCornerRadius);
    }

    private void o(Canvas canvas, int color) {
        if (color != 17170445) {
            canvas.drawColor(color);
        }
    }

    private void r() {
        switch (this.c.a()) {
            case 1:
                float offset = ((float) getWidth()) * this.E4;
                this.z4 = offset;
                this.A4 = 0.0f;
                this.B4 = ((float) getWidth()) + offset;
                this.C4 = 0.0f;
                return;
            case 2:
                float offset2 = ((float) getWidth()) * this.E4;
                this.z4 = offset2;
                this.A4 = 0.0f;
                this.B4 = ((float) getWidth()) + offset2;
                this.C4 = 0.0f;
                return;
            case 3:
                float offset3 = ((float) getHeight()) * this.E4;
                this.z4 = 0.0f;
                this.A4 = offset3;
                this.B4 = 0.0f;
                this.C4 = ((float) getHeight()) + offset3;
                return;
            case 4:
                float offset4 = ((float) getHeight()) * this.E4;
                this.z4 = 0.0f;
                this.A4 = offset4;
                this.B4 = 0.0f;
                this.C4 = ((float) getHeight()) + offset4;
                return;
            default:
                return;
        }
    }

    private void u() {
        ArrayList<io.rmiri.skeleton.master.b> arrayList = this.q;
        if (arrayList != null && arrayList.size() > 0) {
            this.G4 = true;
            this.I4 = true;
            setHoldTouchEventsFromChildren(true);
            float valueAnimationMoveFractionStart = 0.0f;
            float valueAnimationMoveFractionEnd = 0.0f;
            switch (this.c.a()) {
                case 1:
                    valueAnimationMoveFractionStart = -1.0f;
                    valueAnimationMoveFractionEnd = 1.0f;
                    break;
                case 2:
                    valueAnimationMoveFractionStart = 1.0f;
                    valueAnimationMoveFractionEnd = -1.0f;
                    break;
                case 3:
                    valueAnimationMoveFractionStart = -1.0f;
                    valueAnimationMoveFractionEnd = 1.0f;
                    break;
                case 4:
                    valueAnimationMoveFractionStart = 1.0f;
                    valueAnimationMoveFractionEnd = -1.0f;
                    break;
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{valueAnimationMoveFractionStart, valueAnimationMoveFractionEnd});
            this.D4 = ofFloat;
            ofFloat.setDuration(this.c.b());
            this.D4.setRepeatCount(-1);
            this.D4.setRepeatMode(1);
            this.D4.addListener(new a());
            this.D4.addUpdateListener(new b());
            this.D4.start();
            c cVar = this.x;
            if (cVar != null) {
                cVar.b();
            }
        }
    }

    public class a implements Animator.AnimatorListener {
        a() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
            io.rmiri.skeleton.utils.a.a("SkeletonGroup onAnimationRepeat " + SkeletonViewGroup.this.d);
            if (SkeletonViewGroup.this.H4) {
                io.rmiri.skeleton.utils.a.a("SkeletonGroup isCanDrawFinishState " + SkeletonViewGroup.this.d);
                SkeletonViewGroup.this.t();
            }
            if (SkeletonViewGroup.this.J4) {
                io.rmiri.skeleton.utils.a.a("SkeletonGroup isLastLoopAnimation " + SkeletonViewGroup.this.d);
                if (SkeletonViewGroup.this.c.c() == 0) {
                    SkeletonViewGroup.this.t();
                    return;
                }
                boolean unused = SkeletonViewGroup.this.J4 = false;
                boolean unused2 = SkeletonViewGroup.this.H4 = true;
                SkeletonViewGroup skeletonViewGroup = SkeletonViewGroup.this;
                int[] unused3 = skeletonViewGroup.p4 = io.rmiri.skeleton.utils.b.c(skeletonViewGroup.c.h(), SkeletonViewGroup.this.c.g());
            }
        }
    }

    public class b implements ValueAnimator.AnimatorUpdateListener {
        b() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            float unused = SkeletonViewGroup.this.E4 = ((Float) animation.getAnimatedValue()).floatValue();
            float unused2 = SkeletonViewGroup.this.F4 = animation.getAnimatedFraction();
            SkeletonViewGroup.this.invalidate();
        }
    }

    /* access modifiers changed from: private */
    public void t() {
        this.G4 = false;
        this.J4 = false;
        this.H4 = false;
        ValueAnimator valueAnimator = this.D4;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.D4.end();
            this.D4.cancel();
        }
        setHoldTouchEventsFromChildren(false);
        c cVar = this.x;
        if (cVar != null) {
            cVar.a();
        }
    }

    public void v() {
        if (!this.I4) {
            u();
        }
    }

    public void p() {
        if (this.I4) {
            this.J4 = true;
        }
    }

    public void setSkeletonModels(ArrayList<io.rmiri.skeleton.master.b> skeletonModels) {
        this.f = skeletonModels;
    }

    public void setAutoPlay(boolean isAnimationAutoStart) {
        this.c.N(isAnimationAutoStart);
    }

    public void setShowSkeleton(boolean isShowSkeleton) {
        this.c.e0(isShowSkeleton);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        p();
        Log.e("+++++++++", "onDetachedFromWindow" + this.d);
    }

    public void setSkeletonListener(c skeletonListener) {
        this.x = skeletonListener;
    }
}
