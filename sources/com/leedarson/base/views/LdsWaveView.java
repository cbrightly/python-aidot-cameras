package com.leedarson.base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.leedarson.module_base.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.LinkedList;

public class LdsWaveView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    LinkedList<Float> a1;
    LinkedList<Float> a2;
    int c;
    int d;
    int f;
    int p0;
    LinkedList<Float> p1;
    Paint p2;
    private int p3;
    int q;
    final int x;
    final float y;
    int z;

    public LdsWaveView(Context context) {
        super(context, (AttributeSet) null);
        this.c = -1;
        this.d = 20;
        this.q = 5;
        this.x = 20;
        this.y = 0.005f;
        this.p3 = 0;
    }

    public LdsWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.c = -1;
        this.d = 20;
        this.q = 5;
        this.x = 20;
        this.y = 0.005f;
        this.p3 = 0;
        this.a1 = new LinkedList<>();
        this.a2 = new LinkedList<>();
        this.p1 = new LinkedList<>();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R$styleable.LdsWaveView);
        this.c = typedArray.getColor(R$styleable.LdsWaveView_lwv_color, -1);
        this.d = (int) typedArray.getDimension(R$styleable.LdsWaveView_lwv_line_width, 20.0f);
        this.f = (int) typedArray.getDimension(R$styleable.LdsWaveView_lwv_space, 10.0f);
        this.q = typedArray.getInt(R$styleable.LdsWaveView_lwv_line_num, 0);
        typedArray.recycle();
        Paint paint = new Paint();
        this.p2 = paint;
        paint.setColor(this.c);
        this.p2.setStyle(Paint.Style.FILL);
        this.p2.setAntiAlias(true);
        this.p2.setStrokeWidth((float) this.d);
        this.p2.setStrokeCap(Paint.Cap.ROUND);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 773, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            this.z = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            int defaultSize = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            this.p0 = defaultSize;
            setMeasuredDimension(this.z, defaultSize);
            if (this.q == 0) {
                this.q = (this.z - 40) / (this.f + this.d);
            }
            this.a1.clear();
            this.a2.clear();
            for (int i = 0; i < this.q; i++) {
                this.a1.add(Float.valueOf(0.2f));
                this.a2.add(Float.valueOf(0.2f));
            }
        }
    }

    public static int getDefaultSize(int size, int measureSpec) {
        Object[] objArr = {new Integer(size), new Integer(measureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 774, new Class[]{cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int result = size;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case Integer.MIN_VALUE:
            case 1073741824:
                return specSize;
            case 0:
                return size;
            default:
                return result;
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 775, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            int half = this.a2.size() / 2;
            for (int i = 0; i < this.a2.size(); i++) {
                int dx = Math.abs(i - half) * (this.d + this.f);
                if (i < half + 1) {
                    c((this.z / 2) + dx, this.a2.get(i).floatValue(), canvas);
                } else {
                    c((this.z / 2) - dx, this.a2.get(i).floatValue(), canvas);
                }
            }
        }
    }

    private void c(int x2, float value, Canvas canvas) {
        Object[] objArr = {new Integer(x2), new Float(value), canvas};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 776, new Class[]{Integer.TYPE, Float.TYPE, Canvas.class}, Void.TYPE).isSupported) {
            Canvas canvas2 = canvas;
            int i = this.p0;
            float y2 = value * ((float) (i - 20));
            canvas2.drawLine((float) x2, ((float) (i / 2)) - (y2 / 2.0f), (float) x2, ((float) (i / 2)) + (y2 / 2.0f), this.p2);
        }
    }

    public void a(float f2) {
        Object[] objArr = {new Float(f2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 777, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            b(f2, true);
        }
    }

    public void b(float i, boolean addValue) {
        Object[] objArr = {new Float(i), new Byte(addValue ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 778, new Class[]{Float.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (addValue) {
                float i2 = i * 2.0f;
                if (i2 > 1.0f) {
                    i2 = 1.0f;
                }
                if (((double) i2) < 0.2d) {
                    i2 = 0.2f;
                }
                this.p1.clear();
                this.p1.addAll(this.a1);
                if (this.a1.size() == this.q) {
                    this.a1.removeFirst();
                }
                this.a1.addLast(Float.valueOf(i2));
            }
            d();
        }
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 779, new Class[0], Void.TYPE).isSupported) {
            int i = 0;
            while (i < this.a1.size()) {
                try {
                    float last = this.p1.get(i).floatValue();
                    float now = this.a1.get(i).floatValue();
                    if (now > last) {
                        this.a2.set(i, Float.valueOf(Math.max(last + 0.005f, now)));
                        this.p1.set(i, Float.valueOf(Math.max(0.005f + last, now)));
                    } else {
                        this.a2.set(i, Float.valueOf(last - 0.005f));
                        this.p1.set(i, Float.valueOf(last - 0.005f));
                    }
                    i++;
                } catch (Exception e) {
                    return;
                }
            }
            postInvalidate();
        }
    }
}
