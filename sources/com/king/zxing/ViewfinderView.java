package com.king.zxing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.zxing.ResultPoint;
import com.leedarson.serviceimpl.camera.R$color;
import com.leedarson.serviceimpl.camera.R$styleable;
import java.util.ArrayList;
import java.util.List;

public final class ViewfinderView extends View {
    private boolean A4;
    private int B4;
    private int C4;
    private int D4;
    private int E4;
    private b F4;
    private int G4;
    private int H4;
    private Rect I4;
    private int J4;
    private int K4;
    private int L4;
    private int M4;
    private int N4;
    private int O4;
    private float P4;
    private boolean Q4;
    private List<ResultPoint> R4;
    private List<ResultPoint> S4;
    private Matrix T4;
    private float a1;
    private String a2;
    private Paint c;
    private TextPaint d;
    private int f;
    private int p0;
    private c p1;
    private int p2;
    private float p3;
    public int p4;
    private Bitmap q;
    private int x;
    private int y;
    private int z;
    public int z4;

    public enum b {
        NONE(0),
        LINE(1),
        GRID(2);
        
        /* access modifiers changed from: private */
        public int mValue;

        private b(int value) {
            this.mValue = value;
        }

        /* access modifiers changed from: private */
        public static b a(int value) {
            for (b style : values()) {
                if (style.mValue == value) {
                    return style;
                }
            }
            return LINE;
        }
    }

    public enum c {
        TOP(0),
        BOTTOM(1);
        
        private int mValue;

        private c(int value) {
            this.mValue = value;
        }

        /* access modifiers changed from: private */
        public static c a(int value) {
            for (c location : values()) {
                if (location.mValue == value) {
                    return location;
                }
            }
            return TOP;
        }
    }

    public ViewfinderView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ViewfinderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewfinderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.p4 = 0;
        this.z4 = 0;
        m(context, attrs);
    }

    private void m(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R$styleable.ViewfinderView);
        this.f = array.getColor(R$styleable.ViewfinderView_maskColor, ContextCompat.getColor(context, R$color.viewfinder_mask));
        this.x = array.getColor(R$styleable.ViewfinderView_frameColor, ContextCompat.getColor(context, R$color.viewfinder_frame));
        this.z = array.getColor(R$styleable.ViewfinderView_cornerColor, ContextCompat.getColor(context, R$color.viewfinder_corner));
        this.y = array.getColor(R$styleable.ViewfinderView_laserColor, ContextCompat.getColor(context, R$color.viewfinder_laser));
        this.p0 = array.getColor(R$styleable.ViewfinderView_resultPointColor, ContextCompat.getColor(context, R$color.viewfinder_result_point_color));
        this.a2 = array.getString(R$styleable.ViewfinderView_labelText);
        this.p2 = array.getColor(R$styleable.ViewfinderView_labelTextColor, ContextCompat.getColor(context, R$color.viewfinder_text_color));
        this.p3 = array.getDimension(R$styleable.ViewfinderView_labelTextSize, TypedValue.applyDimension(2, 14.0f, getResources().getDisplayMetrics()));
        this.a1 = array.getDimension(R$styleable.ViewfinderView_labelTextPadding, TypedValue.applyDimension(1, 24.0f, getResources().getDisplayMetrics()));
        this.p1 = c.a(array.getInt(R$styleable.ViewfinderView_labelTextLocation, 0));
        this.A4 = array.getBoolean(R$styleable.ViewfinderView_showResultPoint, false);
        this.D4 = array.getDimensionPixelSize(R$styleable.ViewfinderView_frameWidth, 0);
        this.E4 = array.getDimensionPixelSize(R$styleable.ViewfinderView_frameHeight, 0);
        this.F4 = b.a(array.getInt(R$styleable.ViewfinderView_laserStyle, b.LINE.mValue));
        this.G4 = array.getInt(R$styleable.ViewfinderView_gridColumn, 20);
        this.H4 = (int) array.getDimension(R$styleable.ViewfinderView_gridHeight, TypedValue.applyDimension(1, 40.0f, getResources().getDisplayMetrics()));
        this.J4 = (int) array.getDimension(R$styleable.ViewfinderView_cornerRectWidth, TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics()));
        this.K4 = (int) array.getDimension(R$styleable.ViewfinderView_cornerRectHeight, TypedValue.applyDimension(1, 16.0f, getResources().getDisplayMetrics()));
        this.L4 = (int) array.getDimension(R$styleable.ViewfinderView_scannerLineMoveDistance, TypedValue.applyDimension(1, 2.0f, getResources().getDisplayMetrics()));
        this.M4 = (int) array.getDimension(R$styleable.ViewfinderView_scannerLineHeight, TypedValue.applyDimension(1, 5.0f, getResources().getDisplayMetrics()));
        this.N4 = (int) array.getDimension(R$styleable.ViewfinderView_frameLineWidth, TypedValue.applyDimension(1, 1.0f, getResources().getDisplayMetrics()));
        this.O4 = array.getInteger(R$styleable.ViewfinderView_scannerAnimationDelay, 15);
        this.P4 = array.getFloat(R$styleable.ViewfinderView_frameRatio, 0.625f);
        array.recycle();
        this.c = new Paint(1);
        this.d = new TextPaint(1);
        this.R4 = new ArrayList(5);
        this.S4 = null;
        this.B4 = getDisplayMetrics().widthPixels;
        this.C4 = getDisplayMetrics().heightPixels;
        com.king.zxing.util.b.h("自定义传入扫码宽width::" + this.D4 + ",frameHeight:" + this.E4);
        com.king.zxing.util.b.h("屏幕width:" + this.B4 + ",height:" + this.C4);
        StringBuilder sb = new StringBuilder();
        sb.append("扫码框占比例:");
        sb.append(this.P4);
        com.king.zxing.util.b.h(sb.toString());
        int size = (int) (((float) Math.min(this.B4, this.C4)) * this.P4);
        com.king.zxing.util.b.h("扫码框 size:" + size);
        int i = this.D4;
        if (i <= 0 || i > this.B4) {
            this.D4 = size;
        }
        com.king.zxing.util.b.h("确定扫码框width:" + this.D4);
        int i2 = this.E4;
        if (i2 <= 0 || i2 > this.C4) {
            this.E4 = size;
        }
        com.king.zxing.util.b.h("确定扫码框height:" + this.E4);
    }

    private DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    public void setLabelText(String labelText) {
        this.a2 = labelText;
    }

    public void setLabelTextColor(@ColorInt int color) {
        this.p2 = color;
    }

    public void setMaskColor(int maskColor) {
        this.f = maskColor;
    }

    public void setMaskBitmap(Bitmap bitmap) {
        this.q = bitmap;
    }

    public void setlaserColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            this.y = Color.parseColor(color);
            invalidate();
        }
    }

    public void setCornerColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            this.z = Color.parseColor(color);
            invalidate();
        }
    }

    public void setFrameColor(String color) {
        if (!TextUtils.isEmpty(color)) {
            this.x = Color.parseColor(color);
            invalidate();
        }
    }

    public void a(int height) {
        this.M4 = (int) TypedValue.applyDimension(1, (float) height, getResources().getDisplayMetrics());
        invalidate();
    }

    public void b(int width) {
        this.J4 = (int) TypedValue.applyDimension(1, (float) width, getResources().getDisplayMetrics());
        invalidate();
    }

    public void setLabelTextColorResource(@ColorRes int id) {
        this.p2 = ContextCompat.getColor(getContext(), id);
    }

    public int getFrameHeight() {
        return this.E4;
    }

    public void setLabelTextSize(float textSize) {
        this.p3 = textSize;
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"DrawAllocation"})
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int leftOffset = (((this.B4 - this.D4) / 2) + getPaddingLeft()) - getPaddingRight();
        int topOffset = (((this.C4 - this.E4) / 2) + getPaddingTop()) - getPaddingBottom();
        this.I4 = new Rect(leftOffset, topOffset, this.D4 + leftOffset, this.E4 + topOffset);
    }

    public void onDraw(Canvas canvas) {
        Rect rect = this.I4;
        if (rect != null) {
            if (this.p4 == 0 || this.z4 == 0) {
                this.p4 = rect.top;
                this.z4 = rect.bottom - this.M4;
            }
            int width = canvas.getWidth();
            int height = canvas.getHeight();
            com.king.zxing.util.b.h("canvas.getwidth:" + canvas.getWidth() + "," + canvas.getHeight());
            e(canvas, this.I4, width, height);
            h(canvas, this.I4);
            f(canvas, this.I4);
            d(canvas, this.I4);
            k(canvas, this.I4);
            j(canvas, this.I4);
            long j = (long) this.O4;
            Rect rect2 = this.I4;
            postInvalidateDelayed(j, rect2.left - 20, rect2.top - 20, rect2.right + 20, rect2.bottom + 20);
        }
    }

    public void setStopScan(boolean stopScan) {
        this.Q4 = stopScan;
    }

    private void k(Canvas canvas, Rect frame) {
        if (!TextUtils.isEmpty(this.a2)) {
            this.d.setColor(this.p2);
            this.d.setTextSize(this.p3);
            this.d.setTextAlign(Paint.Align.CENTER);
            StaticLayout staticLayout = new StaticLayout(this.a2, this.d, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            if (this.p1 == c.BOTTOM) {
                canvas.translate((float) (frame.left + (frame.width() / 2)), ((float) frame.bottom) + this.a1);
                staticLayout.draw(canvas);
                return;
            }
            canvas.translate((float) (frame.left + (frame.width() / 2)), (((float) frame.top) - this.a1) - ((float) staticLayout.getHeight()));
            staticLayout.draw(canvas);
        }
    }

    private void d(Canvas canvas, Rect frame) {
        this.c.setColor(this.z);
        int i = frame.left;
        int i2 = frame.top;
        canvas.drawRect((float) i, (float) i2, (float) (i + this.J4), (float) (i2 + this.K4), this.c);
        int i3 = frame.left;
        int i4 = frame.top;
        canvas.drawRect((float) i3, (float) i4, (float) (i3 + this.K4), (float) (i4 + this.J4), this.c);
        int i5 = frame.right;
        float f2 = (float) (i5 - this.J4);
        int i6 = frame.top;
        Canvas canvas2 = canvas;
        canvas2.drawRect(f2, (float) i6, (float) i5, (float) (i6 + this.K4), this.c);
        int i7 = frame.right;
        float f3 = (float) (i7 - this.K4);
        int i8 = frame.top;
        canvas2.drawRect(f3, (float) i8, (float) i7, (float) (i8 + this.J4), this.c);
        int i9 = frame.left;
        int i10 = frame.bottom;
        canvas.drawRect((float) i9, (float) (i10 - this.J4), (float) (i9 + this.K4), (float) i10, this.c);
        int i11 = frame.left;
        int i12 = frame.bottom;
        canvas.drawRect((float) i11, (float) (i12 - this.K4), (float) (i11 + this.J4), (float) i12, this.c);
        int i13 = frame.right;
        float f4 = (float) (i13 - this.J4);
        int i14 = frame.bottom;
        canvas.drawRect(f4, (float) (i14 - this.K4), (float) i13, (float) i14, this.c);
        int i15 = frame.right;
        float f5 = (float) (i15 - this.K4);
        int i16 = frame.bottom;
        canvas.drawRect(f5, (float) (i16 - this.J4), (float) i15, (float) i16, this.c);
    }

    private void h(Canvas canvas, Rect frame) {
        if (!this.Q4 && this.F4 != null) {
            this.c.setColor(this.y);
            switch (a.a[this.F4.ordinal()]) {
                case 1:
                    i(canvas, frame);
                    break;
                case 2:
                    g(canvas, frame);
                    break;
            }
            this.c.setShader((Shader) null);
        }
    }

    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.values().length];
            a = iArr;
            try {
                iArr[b.LINE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.GRID.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private void i(Canvas canvas, Rect frame) {
        if (!this.Q4) {
            int i = frame.left;
            int i2 = this.p4;
            this.c.setShader(new LinearGradient((float) i, (float) i2, (float) i, (float) (i2 + this.M4), n(this.y), this.y, Shader.TileMode.MIRROR));
            if (this.p4 <= this.z4) {
                int i3 = frame.left;
                int i4 = this.M4;
                int i5 = this.p4;
                canvas.drawOval(new RectF((float) (i3 + (i4 * 2)), (float) i5, (float) (frame.right - (i4 * 2)), (float) (i5 + i4)), this.c);
                this.p4 += this.L4;
                return;
            }
            this.p4 = frame.top;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x00a2 A[LOOP:1: B:16:0x009b->B:18:0x00a2, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x006d A[LOOP:0: B:7:0x0069->B:9:0x006d, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void g(android.graphics.Canvas r18, android.graphics.Rect r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            r2 = 2
            android.graphics.Paint r3 = r0.c
            float r4 = (float) r2
            r3.setStrokeWidth(r4)
            int r3 = r0.H4
            if (r3 <= 0) goto L_0x0019
            int r4 = r0.p4
            int r5 = r1.top
            int r5 = r4 - r5
            if (r5 <= r3) goto L_0x0019
            int r4 = r4 - r3
            goto L_0x001b
        L_0x0019:
            int r4 = r1.top
        L_0x001b:
            r3 = r4
            android.graphics.LinearGradient r12 = new android.graphics.LinearGradient
            int r4 = r1.left
            int r5 = r19.width()
            r6 = 2
            int r5 = r5 / r6
            int r4 = r4 + r5
            float r5 = (float) r4
            float r7 = (float) r3
            int r4 = r1.left
            int r8 = r19.width()
            int r8 = r8 / r6
            int r4 = r4 + r8
            float r8 = (float) r4
            int r4 = r0.p4
            float r9 = (float) r4
            int[] r10 = new int[r6]
            r4 = 0
            int r11 = r0.y
            int r11 = r0.n(r11)
            r10[r4] = r11
            int r4 = r0.y
            r11 = 1
            r10[r11] = r4
            float[] r11 = new float[r6]
            r11 = {0, 1065353216} // fill-array
            android.graphics.Shader$TileMode r13 = android.graphics.Shader.TileMode.CLAMP
            r4 = r12
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r13
            r4.<init>(r5, r6, r7, r8, r9, r10, r11)
            android.graphics.Paint r5 = r0.c
            r5.setShader(r4)
            int r5 = r19.width()
            float r5 = (float) r5
            r6 = 1065353216(0x3f800000, float:1.0)
            float r5 = r5 * r6
            int r6 = r0.G4
            float r6 = (float) r6
            float r5 = r5 / r6
            r6 = r5
            r7 = 1
        L_0x0069:
            int r8 = r0.G4
            if (r7 >= r8) goto L_0x0089
            int r8 = r1.left
            float r9 = (float) r8
            float r10 = (float) r7
            float r10 = r10 * r5
            float r12 = r9 + r10
            float r13 = (float) r3
            float r8 = (float) r8
            float r9 = (float) r7
            float r9 = r9 * r5
            float r14 = r8 + r9
            int r8 = r0.p4
            float r15 = (float) r8
            android.graphics.Paint r8 = r0.c
            r11 = r18
            r16 = r8
            r11.drawLine(r12, r13, r14, r15, r16)
            int r7 = r7 + 1
            goto L_0x0069
        L_0x0089:
            int r7 = r0.H4
            if (r7 <= 0) goto L_0x0095
            int r8 = r0.p4
            int r9 = r1.top
            int r8 = r8 - r9
            if (r8 <= r7) goto L_0x0095
            goto L_0x009a
        L_0x0095:
            int r7 = r0.p4
            int r8 = r1.top
            int r7 = r7 - r8
        L_0x009a:
            r8 = 0
        L_0x009b:
            float r9 = (float) r8
            float r10 = (float) r7
            float r10 = r10 / r6
            int r9 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r9 > 0) goto L_0x00be
            int r9 = r1.left
            float r11 = (float) r9
            int r9 = r0.p4
            float r10 = (float) r9
            float r12 = (float) r8
            float r12 = r12 * r6
            float r12 = r10 - r12
            int r10 = r1.right
            float r13 = (float) r10
            float r9 = (float) r9
            float r10 = (float) r8
            float r10 = r10 * r6
            float r14 = r9 - r10
            android.graphics.Paint r15 = r0.c
            r10 = r18
            r10.drawLine(r11, r12, r13, r14, r15)
            int r8 = r8 + 1
            goto L_0x009b
        L_0x00be:
            int r8 = r0.p4
            int r9 = r0.z4
            if (r8 >= r9) goto L_0x00ca
            int r9 = r0.L4
            int r8 = r8 + r9
            r0.p4 = r8
            goto L_0x00ce
        L_0x00ca:
            int r8 = r1.top
            r0.p4 = r8
        L_0x00ce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.king.zxing.ViewfinderView.g(android.graphics.Canvas, android.graphics.Rect):void");
    }

    public int n(int color) {
        String hax = Integer.toHexString(color);
        return Integer.valueOf("01" + hax.substring(2), 16).intValue();
    }

    private void f(Canvas canvas, Rect frame) {
        this.c.setColor(this.x);
        float f2 = (float) frame.left;
        int i = frame.top;
        canvas.drawRect(f2, (float) i, (float) frame.right, (float) (i + this.N4), this.c);
        int i2 = frame.left;
        canvas.drawRect((float) i2, (float) frame.top, (float) (i2 + this.N4), (float) frame.bottom, this.c);
        int i3 = frame.right;
        canvas.drawRect((float) (i3 - this.N4), (float) frame.top, (float) i3, (float) frame.bottom, this.c);
        float f3 = (float) frame.left;
        int i4 = frame.bottom;
        canvas.drawRect(f3, (float) (i4 - this.N4), (float) frame.right, (float) i4, this.c);
    }

    private void e(Canvas canvas, Rect frame, int width, int height) {
        Rect rect = frame;
        int i = width;
        int i2 = height;
        this.c.setColor(this.f);
        Bitmap bitmap = this.q;
        if (bitmap == null || bitmap.isRecycled()) {
            Canvas canvas2 = canvas;
            Canvas canvas3 = canvas;
            canvas3.drawRect(0.0f, 0.0f, (float) i, (float) rect.top, this.c);
            canvas3.drawRect(0.0f, (float) rect.top, (float) rect.left, (float) rect.bottom, this.c);
            canvas3.drawRect((float) rect.right, (float) rect.top, (float) i, (float) rect.bottom, this.c);
            canvas3.drawRect(0.0f, (float) rect.bottom, (float) i, (float) i2, this.c);
            return;
        }
        if (this.T4 == null) {
            this.T4 = new Matrix();
            float sx = (((float) i) * 1.0f) / ((float) this.q.getWidth());
            float sy = (((float) i2) * 1.0f) / ((float) this.q.getHeight());
            this.T4.reset();
            this.T4.postScale(sx, sy);
        }
        Canvas canvas4 = canvas;
        canvas.drawBitmap(this.q, this.T4, this.c);
    }

    private void j(Canvas canvas, Rect frame) {
        if (this.A4) {
            List<ResultPoint> currentPossible = this.R4;
            List<ResultPoint> currentLast = this.S4;
            if (currentPossible.isEmpty()) {
                this.S4 = null;
            } else {
                this.R4 = new ArrayList(5);
                this.S4 = currentPossible;
                this.c.setAlpha(Opcodes.IF_ICMPNE);
                this.c.setColor(this.p0);
                synchronized (currentPossible) {
                    for (ResultPoint point : currentPossible) {
                        canvas.drawCircle(point.getX(), point.getY(), 10.0f, this.c);
                    }
                }
            }
            if (currentLast != null) {
                this.c.setAlpha(80);
                this.c.setColor(this.p0);
                synchronized (currentLast) {
                    for (ResultPoint point2 : currentLast) {
                        canvas.drawCircle(point2.getX(), point2.getY(), 10.0f, this.c);
                    }
                }
            }
        }
    }

    public void l() {
        invalidate();
    }

    public void setLaserStyle(b laserStyle) {
        this.F4 = laserStyle;
    }

    public void setShowResultPoint(boolean showResultPoint) {
        this.A4 = showResultPoint;
    }

    public void c(ResultPoint point) {
        if (this.A4) {
            List<ResultPoint> points = this.R4;
            synchronized (points) {
                points.add(point);
                int size = points.size();
                if (size > 20) {
                    points.subList(0, size - 10).clear();
                }
            }
        }
    }
}
