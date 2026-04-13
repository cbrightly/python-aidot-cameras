package com.leedarson.view.rangeseekbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import com.leedarson.R$styleable;
import com.leedarson.log.f;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class RangeSeekBar extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int A4;
    private int B4;
    private int C4;
    private int D4;
    private int E4;
    private float F4;
    private int G4;
    private boolean H4;
    private int I4;
    private float J4;
    private float K4;
    private float L4;
    private int M4;
    private boolean N4;
    private int O4;
    private float P4;
    private float Q4;
    private boolean R4;
    private boolean S4;
    float T4;
    float U4;
    float V4;
    boolean W4;
    Paint X4;
    RectF Y4;
    RectF Z4;
    private int a1;
    private int a2;
    Rect a5;
    RectF b5;
    private int c;
    Rect c5;
    private int d;
    c d5;
    c e5;
    private int f;
    c f5;
    Bitmap g5;
    Bitmap h5;
    List<Bitmap> i5;
    private int j5;
    private a k5;
    private b l5;
    private int p0;
    private int p1;
    private int p2;
    private CharSequence[] p3;
    private float p4;
    private int q;
    private int x;
    private int y;
    private int z;
    private int z4;

    public RangeSeekBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public RangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.R4 = false;
        this.S4 = true;
        this.W4 = false;
        this.X4 = new Paint();
        this.Y4 = new RectF();
        this.Z4 = new RectF();
        this.a5 = new Rect();
        this.b5 = new RectF();
        this.c5 = new Rect();
        this.i5 = new ArrayList();
        e(attrs);
        f();
        h(attrs);
        i();
    }

    private void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11907, new Class[0], Void.TYPE).isSupported) {
            if (this.g5 == null) {
                this.g5 = e.f(getContext(), this.E4, this.D4, this.B4);
            }
            if (this.h5 == null) {
                this.h5 = e.f(getContext(), this.E4, this.D4, this.C4);
            }
        }
    }

    private boolean t() {
        return this.M4 >= 1 && this.K4 > 0.0f && this.J4 > 0.0f;
    }

    private void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11908, new Class[0], Void.TYPE).isSupported) {
            if (t() && this.O4 != 0 && this.i5.isEmpty()) {
                Bitmap bitmap = e.f(getContext(), (int) this.J4, (int) this.K4, this.O4);
                for (int i = 0; i <= this.M4; i++) {
                    this.i5.add(bitmap);
                }
            }
        }
    }

    private void h(AttributeSet attrs) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{attrs}, this, changeQuickRedirect, false, 11909, new Class[]{AttributeSet.class}, Void.TYPE).isSupported) {
            this.d5 = new c(this, attrs, true);
            c cVar = new c(this, attrs, false);
            this.e5 = cVar;
            if (this.x == 1) {
                z2 = false;
            }
            cVar.T(z2);
        }
    }

    private void e(AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{attrs}, this, changeQuickRedirect, false, 11910, new Class[]{AttributeSet.class}, Void.TYPE).isSupported) {
            try {
                TypedArray t = getContext().obtainStyledAttributes(attrs, R$styleable.RangeSeekBar);
                this.x = t.getInt(R$styleable.RangeSeekBar_rsb_mode, 2);
                this.P4 = t.getFloat(R$styleable.RangeSeekBar_rsb_min, 0.0f);
                this.Q4 = t.getFloat(R$styleable.RangeSeekBar_rsb_max, 100.0f);
                this.F4 = t.getFloat(R$styleable.RangeSeekBar_rsb_min_interval, 0.0f);
                this.G4 = t.getInt(R$styleable.RangeSeekBar_rsb_gravity, 0);
                this.z4 = t.getColor(R$styleable.RangeSeekBar_rsb_progress_color, -11806366);
                this.p4 = (float) ((int) t.getDimension(R$styleable.RangeSeekBar_rsb_progress_radius, -1.0f));
                this.A4 = t.getColor(R$styleable.RangeSeekBar_rsb_progress_default_color, -2631721);
                this.B4 = t.getResourceId(R$styleable.RangeSeekBar_rsb_progress_drawable, 0);
                this.C4 = t.getResourceId(R$styleable.RangeSeekBar_rsb_progress_drawable_default, 0);
                this.D4 = (int) t.getDimension(R$styleable.RangeSeekBar_rsb_progress_height, (float) e.b(getContext(), 2.0f));
                this.y = t.getInt(R$styleable.RangeSeekBar_rsb_tick_mark_mode, 0);
                this.a1 = t.getInt(R$styleable.RangeSeekBar_rsb_tick_mark_gravity, 1);
                this.p1 = t.getInt(R$styleable.RangeSeekBar_rsb_tick_mark_layout_gravity, 0);
                this.p3 = t.getTextArray(R$styleable.RangeSeekBar_rsb_tick_mark_text_array);
                this.z = (int) t.getDimension(R$styleable.RangeSeekBar_rsb_tick_mark_text_margin, (float) e.b(getContext(), 7.0f));
                this.p0 = (int) t.getDimension(R$styleable.RangeSeekBar_rsb_tick_mark_text_size, (float) e.b(getContext(), 12.0f));
                this.a2 = t.getColor(R$styleable.RangeSeekBar_rsb_tick_mark_text_color, this.A4);
                this.p2 = t.getColor(R$styleable.RangeSeekBar_rsb_tick_mark_in_range_text_color, this.z4);
                this.M4 = t.getInt(R$styleable.RangeSeekBar_rsb_steps, 0);
                this.I4 = t.getColor(R$styleable.RangeSeekBar_rsb_step_color, -6447715);
                this.L4 = t.getDimension(R$styleable.RangeSeekBar_rsb_step_radius, 0.0f);
                this.J4 = t.getDimension(R$styleable.RangeSeekBar_rsb_step_width, 0.0f);
                this.K4 = t.getDimension(R$styleable.RangeSeekBar_rsb_step_height, 0.0f);
                this.O4 = t.getResourceId(R$styleable.RangeSeekBar_rsb_step_drawable, 0);
                this.N4 = t.getBoolean(R$styleable.RangeSeekBar_rsb_step_auto_bonding, true);
                t.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void n(int w, int h) {
        Object[] objArr = {new Integer(w), new Integer(h)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11911, clsArr, Void.TYPE).isSupported) {
            int viewHeight = (h - getPaddingBottom()) - getPaddingTop();
            if (h > 0) {
                int i = this.G4;
                if (i == 0) {
                    float maxIndicatorHeight = 0.0f;
                    if (!(this.d5.o() == 1 && this.e5.o() == 1)) {
                        maxIndicatorHeight = (float) Math.max(this.d5.n(), this.e5.n());
                    }
                    float thumbHeight = Math.max(this.d5.w(), this.e5.w());
                    int i2 = this.D4;
                    float thumbHeight2 = thumbHeight - (((float) i2) / 2.0f);
                    this.c = (int) (((thumbHeight2 - ((float) i2)) / 2.0f) + maxIndicatorHeight);
                    if (this.p3 != null && this.p1 == 0) {
                        this.c = (int) Math.max((float) getTickMarkRawHeight(), ((thumbHeight2 - ((float) this.D4)) / 2.0f) + maxIndicatorHeight);
                    }
                    this.d = this.c + this.D4;
                } else if (i == 1) {
                    if (this.p3 == null || this.p1 != 1) {
                        this.d = (int) ((((float) viewHeight) - (Math.max(this.d5.w(), this.e5.w()) / 2.0f)) + (((float) this.D4) / 2.0f));
                    } else {
                        this.d = viewHeight - getTickMarkRawHeight();
                    }
                    this.c = this.d - this.D4;
                } else {
                    int i3 = this.D4;
                    int i4 = (viewHeight - i3) / 2;
                    this.c = i4;
                    this.d = i4 + i3;
                }
                int maxThumbWidth = (int) Math.max(this.d5.y(), this.e5.y());
                this.f = (maxThumbWidth / 2) + getPaddingLeft();
                int paddingRight = (w - (maxThumbWidth / 2)) - getPaddingRight();
                this.q = paddingRight;
                this.E4 = paddingRight - this.f;
                this.Y4.set((float) getProgressLeft(), (float) getProgressTop(), (float) getProgressRight(), (float) getProgressBottom());
                this.j5 = w - this.q;
                if (this.p4 <= 0.0f) {
                    this.p4 = (float) ((int) (((float) (getProgressBottom() - getProgressTop())) * 0.45f));
                }
                g();
            }
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightNeeded;
        int heightNeeded2;
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11912, clsArr, Void.TYPE).isSupported) {
            int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
            int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
            if (heightMode == 1073741824) {
                heightNeeded = View.MeasureSpec.makeMeasureSpec(heightSize, 1073741824);
            } else if (heightMode == Integer.MIN_VALUE && (getParent() instanceof ViewGroup) && heightSize == -1) {
                heightNeeded = View.MeasureSpec.makeMeasureSpec(((ViewGroup) getParent()).getMeasuredHeight(), Integer.MIN_VALUE);
            } else {
                if (this.G4 != 2) {
                    heightNeeded2 = (int) getRawHeight();
                } else if (this.p3 == null || this.p1 != 1) {
                    heightNeeded2 = (int) ((getRawHeight() - (Math.max(this.d5.w(), this.e5.w()) / 2.0f)) * 2.0f);
                } else {
                    heightNeeded2 = (int) ((getRawHeight() - ((float) getTickMarkRawHeight())) * 2.0f);
                }
                heightNeeded = View.MeasureSpec.makeMeasureSpec(heightNeeded2, 1073741824);
            }
            super.onMeasure(widthMeasureSpec, heightNeeded);
        }
    }

    public int getTickMarkRawHeight() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11913, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        CharSequence[] charSequenceArr = this.p3;
        if (charSequenceArr == null || charSequenceArr.length <= 0) {
            return 0;
        }
        return this.z + e.g(String.valueOf(charSequenceArr[0]), (float) this.p0).height() + 3;
    }

    public float getRawHeight() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11914, new Class[0], Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        if (this.x == 1) {
            float rawHeight = this.d5.t();
            if (this.p1 != 1 || this.p3 == null) {
                return rawHeight;
            }
            return (rawHeight - (this.d5.w() / 2.0f)) + (((float) this.D4) / 2.0f) + Math.max((this.d5.w() - ((float) this.D4)) / 2.0f, (float) getTickMarkRawHeight());
        }
        float rawHeight2 = Math.max(this.d5.t(), this.e5.t());
        if (this.p1 != 1 || this.p3 == null) {
            return rawHeight2;
        }
        float thumbHeight = Math.max(this.d5.w(), this.e5.w());
        return (rawHeight2 - (thumbHeight / 2.0f)) + (((float) this.D4) / 2.0f) + Math.max((thumbHeight - ((float) this.D4)) / 2.0f, (float) getTickMarkRawHeight());
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        Object[] objArr = {new Integer(w), new Integer(h), new Integer(oldw), new Integer(oldh)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11915, clsArr, Void.TYPE).isSupported) {
            super.onSizeChanged(w, h, oldw, oldh);
            n(w, h);
            r(this.P4, this.Q4, this.F4);
            int lineCenterY = (getProgressBottom() + getProgressTop()) / 2;
            this.d5.G(getProgressLeft(), lineCenterY);
            if (this.x == 2) {
                this.e5.G(getProgressLeft(), lineCenterY);
            }
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11916, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            m(canvas, this.X4);
            j(canvas, this.X4);
            l(canvas, this.X4);
            k(canvas);
        }
    }

    public void m(Canvas canvas, Paint paint) {
        float num;
        float y2;
        if (!PatchProxy.proxy(new Object[]{canvas, paint}, this, changeQuickRedirect, false, 11917, new Class[]{Canvas.class, Paint.class}, Void.TYPE).isSupported) {
            Paint paint2 = paint;
            Canvas canvas2 = canvas;
            CharSequence[] charSequenceArr = this.p3;
            if (charSequenceArr != null) {
                int trickPartWidth = this.E4 / (charSequenceArr.length - 1);
                int i = 0;
                while (true) {
                    CharSequence[] charSequenceArr2 = this.p3;
                    if (i < charSequenceArr2.length) {
                        String text2Draw = charSequenceArr2[i].toString();
                        if (!TextUtils.isEmpty(text2Draw)) {
                            paint2.getTextBounds(text2Draw, 0, text2Draw.length(), this.c5);
                            paint2.setColor(this.a2);
                            if (this.y == 1) {
                                int i2 = this.a1;
                                if (i2 == 2) {
                                    num = (float) ((getProgressLeft() + (i * trickPartWidth)) - this.c5.width());
                                } else if (i2 == 1) {
                                    num = ((float) (getProgressLeft() + (i * trickPartWidth))) - (((float) this.c5.width()) / 2.0f);
                                } else {
                                    num = (float) (getProgressLeft() + (i * trickPartWidth));
                                }
                            } else {
                                float num2 = e.h(text2Draw);
                                d[] states = getRangeSeekBarState();
                                if (!(e.a(num2, states[0].c) == -1 || e.a(num2, states[1].c) == 1 || this.x != 2)) {
                                    paint2.setColor(this.p2);
                                }
                                float f2 = this.P4;
                                num = (((float) getProgressLeft()) + ((((float) this.E4) * (num2 - f2)) / (this.Q4 - f2))) - (((float) this.c5.width()) / 2.0f);
                            }
                            if (this.p1 == 0) {
                                y2 = (float) (getProgressTop() - this.z);
                            } else {
                                y2 = (float) (getProgressBottom() + this.z + this.c5.height());
                            }
                            canvas2.drawText(text2Draw, num, y2, paint2);
                        }
                        i++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public void j(Canvas canvas, Paint paint) {
        if (!PatchProxy.proxy(new Object[]{canvas, paint}, this, changeQuickRedirect, false, 11918, new Class[]{Canvas.class, Paint.class}, Void.TYPE).isSupported) {
            if (e.i(this.h5)) {
                canvas.drawBitmap(this.h5, (Rect) null, this.Y4, paint);
            } else {
                paint.setColor(this.A4);
                RectF rectF = this.Y4;
                float f2 = this.p4;
                canvas.drawRoundRect(rectF, f2, f2, paint);
            }
            if (this.x == 2) {
                this.Z4.top = (float) getProgressTop();
                RectF rectF2 = this.Z4;
                c cVar = this.d5;
                rectF2.left = ((float) cVar.t) + (cVar.y() / 2.0f) + (((float) this.E4) * this.d5.x);
                RectF rectF3 = this.Z4;
                c cVar2 = this.e5;
                rectF3.right = ((float) cVar2.t) + (cVar2.y() / 2.0f) + (((float) this.E4) * this.e5.x);
                this.Z4.bottom = (float) getProgressBottom();
            } else {
                this.Z4.top = (float) getProgressTop();
                RectF rectF4 = this.Z4;
                c cVar3 = this.d5;
                rectF4.left = ((float) cVar3.t) + (cVar3.y() / 2.0f);
                RectF rectF5 = this.Z4;
                c cVar4 = this.d5;
                rectF5.right = ((float) cVar4.t) + (cVar4.y() / 2.0f) + (((float) this.E4) * this.d5.x);
                this.Z4.bottom = (float) getProgressBottom();
            }
            if (e.i(this.g5)) {
                Rect rect = this.a5;
                rect.top = 0;
                rect.bottom = this.g5.getHeight();
                int bitmapWidth = this.g5.getWidth();
                if (this.x == 2) {
                    Rect rect2 = this.a5;
                    rect2.left = (int) (((float) bitmapWidth) * this.d5.x);
                    rect2.right = (int) (((float) bitmapWidth) * this.e5.x);
                } else {
                    Rect rect3 = this.a5;
                    rect3.left = 0;
                    rect3.right = (int) (((float) bitmapWidth) * this.d5.x);
                }
                canvas.drawBitmap(this.g5, this.a5, this.Z4, (Paint) null);
                return;
            }
            paint.setColor(this.z4);
            RectF rectF6 = this.Z4;
            float f3 = this.p4;
            canvas.drawRoundRect(rectF6, f3, f3, paint);
        }
    }

    public void l(Canvas canvas, Paint paint) {
        Class[] clsArr = {Canvas.class, Paint.class};
        if (!PatchProxy.proxy(new Object[]{canvas, paint}, this, changeQuickRedirect, false, 11919, clsArr, Void.TYPE).isSupported) {
            if (t()) {
                int stepMarks = getProgressWidth() / this.M4;
                float extHeight = (this.K4 - ((float) getProgressHeight())) / 2.0f;
                for (int k = 0; k <= this.M4; k++) {
                    float x2 = ((float) (getProgressLeft() + (k * stepMarks))) - (this.J4 / 2.0f);
                    this.b5.set(x2, ((float) getProgressTop()) - extHeight, this.J4 + x2, ((float) getProgressBottom()) + extHeight);
                    if (this.i5.isEmpty() || this.i5.size() <= k) {
                        paint.setColor(this.I4);
                        RectF rectF = this.b5;
                        float f2 = this.L4;
                        canvas.drawRoundRect(rectF, f2, f2, paint);
                    } else {
                        canvas.drawBitmap(this.i5.get(k), (Rect) null, this.b5, paint);
                    }
                }
            }
        }
    }

    public void k(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11920, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            if (this.d5.o() == 3) {
                this.d5.P(true);
            }
            this.d5.b(canvas, true);
            if (this.x == 2) {
                if (this.e5.o() == 3) {
                    this.e5.P(true);
                }
                this.e5.b(canvas, false);
            }
        }
    }

    private void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11921, new Class[0], Void.TYPE).isSupported) {
            setLayerType(1, (Paint) null);
            this.X4.setStyle(Paint.Style.FILL);
            this.X4.setColor(this.A4);
            this.X4.setTextSize((float) this.p0);
        }
    }

    private void b(boolean hasActivate) {
        c cVar;
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{new Byte(hasActivate ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11922, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (!hasActivate || (cVar = this.f5) == null) {
                this.d5.J(false);
                if (this.x == 2) {
                    this.e5.J(false);
                    return;
                }
                return;
            }
            c cVar2 = this.d5;
            boolean state = cVar == cVar2;
            cVar2.J(state);
            if (this.x == 2) {
                c cVar3 = this.e5;
                if (state) {
                    z2 = false;
                }
                cVar3.J(z2);
            }
        }
    }

    public float c(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11923, new Class[]{MotionEvent.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        return event.getX();
    }

    public float d(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11924, new Class[]{MotionEvent.class}, Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        return event.getY();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void p() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 11925(0x2e95, float:1.671E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.leedarson.view.rangeseekbar.c r1 = r0.f5
            if (r1 == 0) goto L_0x0031
            float r1 = r1.x()
            r2 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x0031
            boolean r1 = r0.W4
            if (r1 != 0) goto L_0x0031
            r1 = 1
            r0.W4 = r1
            com.leedarson.view.rangeseekbar.c r1 = r0.f5
            r1.I()
        L_0x0031:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.view.rangeseekbar.RangeSeekBar.p():void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void o() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 11926(0x2e96, float:1.6712E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            com.leedarson.view.rangeseekbar.c r2 = r1.f5
            if (r2 == 0) goto L_0x0030
            float r2 = r2.x()
            r3 = 1065353216(0x3f800000, float:1.0)
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x0030
            boolean r2 = r1.W4
            if (r2 == 0) goto L_0x0030
            r1.W4 = r0
            com.leedarson.view.rangeseekbar.c r0 = r1.f5
            r0.H()
        L_0x0030:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.view.rangeseekbar.RangeSeekBar.o():void");
    }

    public float a(float touchDownX) {
        Object[] objArr = {new Float(touchDownX)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11927, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        if (this.f5 == null) {
            return 0.0f;
        }
        float percent = ((touchDownX - ((float) getProgressLeft())) * 1.0f) / ((float) this.E4);
        if (touchDownX < ((float) getProgressLeft())) {
            percent = 0.0f;
        } else if (touchDownX > ((float) getProgressRight())) {
            percent = 1.0f;
        }
        if (this.x != 2) {
            return percent;
        }
        c cVar = this.f5;
        c cVar2 = this.d5;
        if (cVar == cVar2) {
            float f2 = this.e5.x;
            float f3 = this.V4;
            if (percent > f2 - f3) {
                return f2 - f3;
            }
            return percent;
        } else if (cVar != this.e5) {
            return percent;
        } else {
            float f4 = cVar2.x;
            float f6 = this.V4;
            if (percent < f4 + f6) {
                return f4 + f6;
            }
            return percent;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean z2 = true;
        boolean z3 = false;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11928, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!this.S4) {
            return true;
        }
        try {
            float f2 = 1.0f;
            switch (event.getAction()) {
                case 0:
                    this.T4 = c(event);
                    this.U4 = d(event);
                    if (this.x != 2) {
                        this.f5 = this.d5;
                        p();
                    } else if (this.e5.x >= 1.0f && this.d5.a(c(event), d(event))) {
                        this.f5 = this.d5;
                        p();
                    } else if (this.e5.a(c(event), d(event))) {
                        this.f5 = this.e5;
                        p();
                    } else {
                        float performClick = ((this.T4 - ((float) getProgressLeft())) * 1.0f) / ((float) this.E4);
                        if (Math.abs(this.d5.x - performClick) < Math.abs(this.e5.x - performClick)) {
                            this.f5 = this.d5;
                        } else {
                            this.f5 = this.e5;
                        }
                        this.f5.U(a(this.T4));
                    }
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    a aVar = this.k5;
                    if (aVar != null) {
                        if (this.f5 == this.d5) {
                            z3 = true;
                        }
                        aVar.c(this, z3);
                    }
                    b(true);
                    return true;
                case 1:
                    if (t() && this.N4) {
                        float percent = a(c(event));
                        float stepPercent = 1.0f / ((float) this.M4);
                        this.f5.U(((float) new BigDecimal((double) (percent / stepPercent)).setScale(0, RoundingMode.HALF_UP).intValue()) * stepPercent);
                    }
                    if (this.x == 2) {
                        this.e5.P(false);
                    }
                    this.d5.P(false);
                    this.f5.D();
                    o();
                    if (this.k5 != null) {
                        d[] states = getRangeSeekBarState();
                        this.k5.a(this, states[0].c, states[1].c, false);
                    }
                    if (this.l5 != null) {
                        d[] states2 = getRangeSeekBarState();
                        this.l5.a(this, states2[0].b, states2[0].c, states2[1].b, states2[1].c);
                    }
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    a aVar2 = this.k5;
                    if (aVar2 != null) {
                        if (this.f5 != this.d5) {
                            z2 = false;
                        }
                        aVar2.b(this, z2);
                    }
                    b(false);
                    break;
                case 2:
                    float x2 = c(event);
                    if (this.x == 2 && this.d5.x == this.e5.x) {
                        this.f5.D();
                        a aVar3 = this.k5;
                        if (aVar3 != null) {
                            aVar3.b(this, this.f5 == this.d5);
                        }
                        if (x2 - this.T4 > 0.0f) {
                            c cVar = this.f5;
                            if (cVar != this.e5) {
                                cVar.P(false);
                                o();
                                this.f5 = this.e5;
                            }
                        } else {
                            c cVar2 = this.f5;
                            if (cVar2 != this.d5) {
                                cVar2.P(false);
                                o();
                                this.f5 = this.d5;
                            }
                        }
                        a aVar4 = this.k5;
                        if (aVar4 != null) {
                            aVar4.c(this, this.f5 == this.d5);
                        }
                    }
                    p();
                    c cVar3 = this.f5;
                    if (cVar3 != null) {
                        float f3 = cVar3.z;
                        if (f3 < 1.0f) {
                            f2 = 0.1f + f3;
                        }
                        cVar3.z = f2;
                        this.T4 = x2;
                        this.f5.U(a(x2));
                        this.f5.P(true);
                        if (this.k5 != null) {
                            d[] states3 = getRangeSeekBarState();
                            this.k5.a(this, states3[0].c, states3[1].c, true);
                        }
                        invalidate();
                        if (getParent() != null) {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        b(true);
                        break;
                    }
                    break;
                case 3:
                    if (this.x == 2) {
                        this.e5.P(false);
                    }
                    c cVar4 = this.f5;
                    if (cVar4 == this.d5) {
                        o();
                    } else if (cVar4 == this.e5) {
                        o();
                    }
                    this.d5.P(false);
                    if (this.k5 != null) {
                        d[] states4 = getRangeSeekBarState();
                        this.k5.a(this, states4[0].c, states4[1].c, false);
                    }
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    b(false);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
    }

    public Parcelable onSaveInstanceState() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11929, new Class[0], Parcelable.class);
        if (proxy.isSupported) {
            return (Parcelable) proxy.result;
        }
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.c = this.P4;
        ss.d = this.Q4;
        ss.f = this.F4;
        f.b(" vary", " onSaveInstanceState");
        d[] results = getRangeSeekBarState();
        ss.x = results[0].c;
        ss.y = results[1].c;
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 11930, new Class[]{Parcelable.class}, Void.TYPE).isSupported) {
            try {
                SavedState ss = (SavedState) state;
                super.onRestoreInstanceState(ss.getSuperState());
                r(ss.c, ss.d, ss.f);
                q(ss.x, ss.y);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setIndicatorTextSize(int indicatorTextSize) {
        Object[] objArr = {new Integer(indicatorTextSize)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11931, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            c cVar = this.d5;
            if (cVar != null) {
                cVar.N(indicatorTextSize);
            }
            c cVar2 = this.e5;
            if (cVar2 != null) {
                cVar2.N(indicatorTextSize);
            }
        }
    }

    public void setOnRangeChangedListener(a listener) {
        this.k5 = listener;
    }

    public void setOnTimeRangeBarListener(b _timeRangeListener) {
        this.l5 = _timeRangeListener;
    }

    public void setProgress(float value) {
        Object[] objArr = {new Float(value)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11932, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            q(value, this.Q4);
        }
    }

    public void q(float leftValue, float rightValue) {
        Object[] objArr = {new Float(leftValue), new Float(rightValue)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11933, clsArr, Void.TYPE).isSupported) {
            float leftValue2 = Math.min(leftValue, rightValue);
            float rightValue2 = Math.max(leftValue2, rightValue);
            float f2 = this.F4;
            if (rightValue2 - leftValue2 < f2) {
                if (leftValue2 - this.P4 > this.Q4 - rightValue2) {
                    leftValue2 = rightValue2 - f2;
                } else {
                    rightValue2 = leftValue2 + f2;
                }
            }
            float f3 = this.P4;
            if (leftValue2 >= f3) {
                float f4 = this.Q4;
                if (rightValue2 <= f4) {
                    float range = f4 - f3;
                    this.d5.x = Math.abs(leftValue2 - f3) / range;
                    if (this.x == 2) {
                        this.e5.x = Math.abs(rightValue2 - this.P4) / range;
                    }
                    a aVar = this.k5;
                    if (aVar != null) {
                        aVar.a(this, leftValue2, rightValue2, false);
                    }
                    invalidate();
                    return;
                }
                throw new IllegalArgumentException("setProgress() max > (preset max - offsetValue) . #max:" + rightValue2 + " #preset max:" + rightValue2);
            }
            throw new IllegalArgumentException("setProgress() min < (preset min - offsetValue) . #min:" + leftValue2 + " #preset min:" + rightValue2);
        }
    }

    public void s(float leftBarValue, float rightBarValue) {
        Object[] objArr = {new Float(leftBarValue), new Float(rightBarValue)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11934, clsArr, Void.TYPE).isSupported) {
            this.d5.x = leftBarValue;
            if (this.x == 2) {
                this.e5.x = rightBarValue;
            }
            invalidate();
        }
    }

    public void r(float min, float max, float minInterval) {
        Object[] objArr = {new Float(min), new Float(max), new Float(minInterval)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11936, new Class[]{cls, cls, cls}, Void.TYPE).isSupported) {
            if (max <= min) {
                throw new IllegalArgumentException("setRange() max must be greater than min ! #max:" + max + " #min:" + min);
            } else if (minInterval < 0.0f) {
                throw new IllegalArgumentException("setRange() interval must be greater than zero ! #minInterval:" + minInterval);
            } else if (minInterval < max - min) {
                this.Q4 = max;
                this.P4 = min;
                this.F4 = minInterval;
                float f2 = minInterval / (max - min);
                this.V4 = f2;
                if (this.x == 2) {
                    c cVar = this.d5;
                    cVar.y = f2;
                    c cVar2 = this.e5;
                    cVar2.y = f2;
                    float f3 = cVar.x;
                    if (f3 + f2 > 1.0f || f3 + f2 <= cVar2.x) {
                        float f4 = cVar2.x;
                        if (f4 - f2 >= 0.0f && f4 - f2 < f3) {
                            cVar.x = f4 - f2;
                        }
                    } else {
                        cVar2.x = f3 + f2;
                    }
                }
                invalidate();
            } else {
                throw new IllegalArgumentException("setRange() interval must be less than (max - min) ! #minInterval:" + minInterval + " #max - min:" + (max - min));
            }
        }
    }

    public d[] getRangeSeekBarState() {
        StringBuilder sb;
        String tempMStr;
        StringBuilder sb2;
        StringBuilder sb3;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11937, new Class[0], d[].class);
        if (proxy.isSupported) {
            return (d[]) proxy.result;
        }
        d leftSeekBarState = new d();
        float s = this.d5.s();
        leftSeekBarState.c = s;
        leftSeekBarState.b = this.d5.x;
        leftSeekBarState.a = String.valueOf(s);
        this.d5.S(this.R4);
        if (this.R4) {
            float tempV = this.d5.s() * 60.0f;
            int tempH = (int) (tempV / 60.0f);
            int tempM = (int) (tempV - ((float) (tempH * 60)));
            if (tempH < 10) {
                sb2.append("0");
                sb2.append(tempH);
            } else {
                sb2 = new StringBuilder();
                sb2.append(tempH);
                sb2.append("");
            }
            String tempHStr = sb2.toString();
            if (tempM < 10) {
                sb3.append("0");
                sb3.append(tempM);
            } else {
                sb3 = new StringBuilder();
                sb3.append(tempM);
                sb3.append("");
            }
            leftSeekBarState.a = tempHStr + ":" + sb3.toString();
        }
        if (e.a(leftSeekBarState.c, this.P4) == 0) {
            leftSeekBarState.d = true;
        } else if (e.a(leftSeekBarState.c, this.Q4) == 0) {
            leftSeekBarState.e = true;
        }
        d rightSeekBarState = new d();
        if (this.x == 2) {
            float s2 = this.e5.s();
            rightSeekBarState.c = s2;
            rightSeekBarState.b = this.e5.x;
            rightSeekBarState.a = String.valueOf(s2);
            this.e5.S(this.R4);
            if (this.R4) {
                float tempV2 = this.e5.s() * 60.0f;
                if (((double) Math.abs(tempV2 - 1440.0f)) < 0.001d) {
                    tempV2 -= 1.0f;
                }
                int tempH2 = (int) (tempV2 / 60.0f);
                int tempM2 = (int) (tempV2 - ((float) (tempH2 * 60)));
                if (tempH2 < 10) {
                    sb.append("0");
                    sb.append(tempH2);
                } else {
                    sb = new StringBuilder();
                    sb.append(tempH2);
                    sb.append("");
                }
                String tempHStr2 = sb.toString();
                if (tempM2 < 10) {
                    tempMStr = "0" + tempM2;
                } else {
                    tempMStr = tempM2 + "";
                }
                rightSeekBarState.a = tempHStr2 + ":" + tempMStr;
            }
            if (e.a(this.e5.x, this.P4) == 0) {
                rightSeekBarState.d = true;
            } else if (e.a(this.e5.x, this.Q4) == 0) {
                rightSeekBarState.e = true;
            }
        }
        return new d[]{leftSeekBarState, rightSeekBarState};
    }

    public void setEnabled(boolean enabled) {
        Object[] objArr = {new Byte(enabled ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11938, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            super.setEnabled(enabled);
            this.S4 = enabled;
        }
    }

    public void setIndicatorText(String progress) {
        if (!PatchProxy.proxy(new Object[]{progress}, this, changeQuickRedirect, false, 11939, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.d5.L(progress);
            if (this.x == 2) {
                this.e5.L(progress);
            }
        }
    }

    public void setTimeRangeState(boolean timeRangeMode) {
        this.R4 = timeRangeMode;
    }

    public void setIndicatorTextDecimalFormat(String formatPattern) {
        if (!PatchProxy.proxy(new Object[]{formatPattern}, this, changeQuickRedirect, false, 11940, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.d5.M(formatPattern);
            if (this.x == 2) {
                this.e5.M(formatPattern);
            }
        }
    }

    public void setIndicatorTextStringFormat(String formatPattern) {
        if (!PatchProxy.proxy(new Object[]{formatPattern}, this, changeQuickRedirect, false, 11941, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.d5.O(formatPattern);
            if (this.x == 2) {
                this.e5.O(formatPattern);
            }
        }
    }

    public c getLeftSeekBar() {
        return this.d5;
    }

    public c getRightSeekBar() {
        return this.e5;
    }

    public int getProgressTop() {
        return this.c;
    }

    public int getProgressBottom() {
        return this.d;
    }

    public int getProgressLeft() {
        return this.f;
    }

    public int getProgressRight() {
        return this.q;
    }

    public int getProgressPaddingRight() {
        return this.j5;
    }

    public int getProgressHeight() {
        return this.D4;
    }

    public void setProgressHeight(int progressHeight) {
        this.D4 = progressHeight;
    }

    public float getMinProgress() {
        return this.P4;
    }

    public float getMaxProgress() {
        return this.Q4;
    }

    public int getTickMarkTextColor() {
        return this.a2;
    }

    public void setTickMarkTextColor(@ColorInt int tickMarkTextColor) {
        this.a2 = tickMarkTextColor;
    }

    public int getTickMarkInRangeTextColor() {
        return this.p2;
    }

    public void setTickMarkInRangeTextColor(@ColorInt int tickMarkInRangeTextColor) {
        this.p2 = tickMarkInRangeTextColor;
    }

    public int getSeekBarMode() {
        return this.x;
    }

    public void setSeekBarMode(int seekBarMode) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{new Integer(seekBarMode)}, this, changeQuickRedirect, false, 11942, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.x = seekBarMode;
            c cVar = this.e5;
            if (seekBarMode == 1) {
                z2 = false;
            }
            cVar.T(z2);
        }
    }

    public int getTickMarkMode() {
        return this.y;
    }

    public void setTickMarkMode(int tickMarkMode) {
        this.y = tickMarkMode;
    }

    public int getTickMarkTextMargin() {
        return this.z;
    }

    public void setTickMarkTextMargin(int tickMarkTextMargin) {
        this.z = tickMarkTextMargin;
    }

    public int getTickMarkTextSize() {
        return this.p0;
    }

    public void setTickMarkTextSize(int tickMarkTextSize) {
        this.p0 = tickMarkTextSize;
    }

    public int getTickMarkGravity() {
        return this.a1;
    }

    public void setTickMarkGravity(int tickMarkGravity) {
        this.a1 = tickMarkGravity;
    }

    public CharSequence[] getTickMarkTextArray() {
        return this.p3;
    }

    public void setTickMarkTextArray(CharSequence[] tickMarkTextArray) {
        this.p3 = tickMarkTextArray;
    }

    public float getMinInterval() {
        return this.F4;
    }

    public float getProgressRadius() {
        return this.p4;
    }

    public void setProgressRadius(float progressRadius) {
        this.p4 = progressRadius;
    }

    public int getProgressColor() {
        return this.z4;
    }

    public void setProgressColor(@ColorInt int progressColor) {
        this.z4 = progressColor;
    }

    public int getProgressDefaultColor() {
        return this.A4;
    }

    public void setProgressDefaultColor(@ColorInt int progressDefaultColor) {
        this.A4 = progressDefaultColor;
    }

    public int getProgressDrawableId() {
        return this.B4;
    }

    public void setProgressDrawableId(@DrawableRes int progressDrawableId) {
        Object[] objArr = {new Integer(progressDrawableId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11943, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.B4 = progressDrawableId;
            this.g5 = null;
            g();
        }
    }

    public int getProgressDefaultDrawableId() {
        return this.C4;
    }

    public void setProgressDefaultDrawableId(@DrawableRes int progressDefaultDrawableId) {
        Object[] objArr = {new Integer(progressDefaultDrawableId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11944, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.C4 = progressDefaultDrawableId;
            this.h5 = null;
            g();
        }
    }

    public int getProgressWidth() {
        return this.E4;
    }

    public void setProgressWidth(int progressWidth) {
        this.E4 = progressWidth;
    }

    public void setTypeface(Typeface typeFace) {
        if (!PatchProxy.proxy(new Object[]{typeFace}, this, changeQuickRedirect, false, 11945, new Class[]{Typeface.class}, Void.TYPE).isSupported) {
            this.X4.setTypeface(typeFace);
        }
    }

    public void setEnableThumbOverlap(boolean enableThumbOverlap) {
        this.H4 = enableThumbOverlap;
    }

    public void setSteps(int steps) {
        this.M4 = steps;
    }

    public int getSteps() {
        return this.M4;
    }

    public int getStepsColor() {
        return this.I4;
    }

    public void setStepsColor(@ColorInt int stepsColor) {
        this.I4 = stepsColor;
    }

    public float getStepsWidth() {
        return this.J4;
    }

    public void setStepsWidth(float stepsWidth) {
        this.J4 = stepsWidth;
    }

    public float getStepsHeight() {
        return this.K4;
    }

    public void setStepsHeight(float stepsHeight) {
        this.K4 = stepsHeight;
    }

    public float getStepsRadius() {
        return this.L4;
    }

    public void setStepsRadius(float stepsRadius) {
        this.L4 = stepsRadius;
    }

    public void setProgressTop(int progressTop) {
        this.c = progressTop;
    }

    public void setProgressBottom(int progressBottom) {
        this.d = progressBottom;
    }

    public void setProgressLeft(int progressLeft) {
        this.f = progressLeft;
    }

    public void setProgressRight(int progressRight) {
        this.q = progressRight;
    }

    public int getTickMarkLayoutGravity() {
        return this.p1;
    }

    public void setTickMarkLayoutGravity(int tickMarkLayoutGravity) {
        this.p1 = tickMarkLayoutGravity;
    }

    public int getGravity() {
        return this.G4;
    }

    public void setGravity(int gravity) {
        this.G4 = gravity;
    }

    public void setStepsAutoBonding(boolean stepsAutoBonding) {
        this.N4 = stepsAutoBonding;
    }

    public int getStepsDrawableId() {
        return this.O4;
    }

    public void setStepsDrawableId(@DrawableRes int stepsDrawableId) {
        Object[] objArr = {new Integer(stepsDrawableId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11946, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.i5.clear();
            this.O4 = stepsDrawableId;
            i();
        }
    }

    public List<Bitmap> getStepsBitmaps() {
        return this.i5;
    }

    public void setStepsBitmaps(List<Bitmap> stepsBitmaps) {
        if (!PatchProxy.proxy(new Object[]{stepsBitmaps}, this, changeQuickRedirect, false, 11947, new Class[]{List.class}, Void.TYPE).isSupported) {
            if (stepsBitmaps == null || stepsBitmaps.isEmpty() || stepsBitmaps.size() <= this.M4) {
                throw new IllegalArgumentException("stepsBitmaps must > steps !");
            }
            this.i5.clear();
            this.i5.addAll(stepsBitmaps);
        }
    }

    public void setStepsDrawable(List<Integer> stepsDrawableIds) {
        if (!PatchProxy.proxy(new Object[]{stepsDrawableIds}, this, changeQuickRedirect, false, 11948, new Class[]{List.class}, Void.TYPE).isSupported) {
            if (stepsDrawableIds == null || stepsDrawableIds.isEmpty() || stepsDrawableIds.size() <= this.M4) {
                throw new IllegalArgumentException("stepsDrawableIds must > steps !");
            } else if (t()) {
                List<Bitmap> stepsBitmaps = new ArrayList<>();
                for (int i = 0; i < stepsDrawableIds.size(); i++) {
                    stepsBitmaps.add(e.f(getContext(), (int) this.J4, (int) this.K4, stepsDrawableIds.get(i).intValue()));
                }
                setStepsBitmaps(stepsBitmaps);
            } else {
                throw new IllegalArgumentException("stepsWidth must > 0, stepsHeight must > 0,steps must > 0 First!!");
            }
        }
    }
}
