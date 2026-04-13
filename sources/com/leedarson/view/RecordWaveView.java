package com.leedarson.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import com.leedarson.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;

public class RecordWaveView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int a1;
    private int a2;
    private ArrayList<Short> c;
    private short d;
    private float f;
    private int p0;
    private float p1;
    private long p2;
    private boolean p3;
    private float q;
    private float x;
    private Paint y;
    private Paint z;

    public RecordWaveView(Context context) {
        this(context, (AttributeSet) null);
    }

    public RecordWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.c = new ArrayList<>();
        this.d = 300;
        this.x = 1.0f;
        this.p0 = ViewCompat.MEASURED_STATE_MASK;
        this.a1 = ViewCompat.MEASURED_STATE_MASK;
        this.p1 = 4.0f;
        this.a2 = 100;
        this.p3 = false;
        e(attrs, defStyleAttr);
    }

    private void e(AttributeSet attrs, int defStyle) {
        Object[] objArr = {attrs, new Integer(defStyle)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11740, new Class[]{AttributeSet.class, Integer.TYPE}, Void.TYPE).isSupported) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R$styleable.WaveView, defStyle, 0);
            this.p0 = a.getColor(R$styleable.WaveView_waveColor, this.p0);
            this.a1 = a.getColor(R$styleable.WaveView_baselineColor, this.a1);
            this.p1 = a.getDimension(R$styleable.WaveView_waveStokeWidth, this.p1);
            this.d = (short) a.getInt(R$styleable.WaveView_maxValue, this.d);
            this.a2 = a.getInt(R$styleable.WaveView_invalidateTime, this.a2);
            this.x = a.getDimension(R$styleable.WaveView_space, this.x);
            a.recycle();
            f();
        }
    }

    private void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11741, new Class[0], Void.TYPE).isSupported) {
            Paint paint = new Paint();
            this.y = paint;
            paint.setColor(this.p0);
            this.y.setStrokeWidth(this.p1);
            this.y.setAntiAlias(true);
            this.y.setFilterBitmap(true);
            this.y.setStrokeCap(Paint.Cap.ROUND);
            this.y.setStyle(Paint.Style.FILL);
            Paint paint2 = new Paint();
            this.z = paint2;
            paint2.setColor(this.a1);
            this.z.setStrokeWidth(1.0f);
            this.z.setAntiAlias(true);
            this.z.setFilterBitmap(true);
            this.z.setStyle(Paint.Style.FILL);
        }
    }

    public short getMax() {
        return this.d;
    }

    public void setMax(short max) {
        this.d = max;
    }

    public float getSpace() {
        return this.x;
    }

    public void setSpace(float space) {
        this.x = space;
    }

    public int getmWaveColor() {
        return this.p0;
    }

    public void setmWaveColor(int mWaveColor) {
        Object[] objArr = {new Integer(mWaveColor)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11742, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.p0 = mWaveColor;
            g();
        }
    }

    public int getmBaseLineColor() {
        return this.a1;
    }

    public void setmBaseLineColor(int mBaseLineColor) {
        Object[] objArr = {new Integer(mBaseLineColor)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11743, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.a1 = mBaseLineColor;
            g();
        }
    }

    public float getWaveStrokeWidth() {
        return this.p1;
    }

    public void setWaveStrokeWidth(float waveStrokeWidth) {
        Object[] objArr = {new Float(waveStrokeWidth)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11744, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.p1 = waveStrokeWidth;
            g();
        }
    }

    public int getInvalidateTime() {
        return this.a2;
    }

    public void setInvalidateTime(int invalidateTime) {
        this.a2 = invalidateTime;
    }

    public void setMaxConstant(boolean maxConstant) {
        this.p3 = maxConstant;
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11745, new Class[0], Void.TYPE).isSupported) {
            f();
            invalidate();
        }
    }

    public void a(short data) {
        if (!PatchProxy.proxy(new Object[]{new Short(data)}, this, changeQuickRedirect, false, 11746, new Class[]{Short.TYPE}, Void.TYPE).isSupported) {
            if (data < 0) {
                data = (short) (-data);
            }
            if (data > this.d && !this.p3) {
                this.d = data;
            }
            if (((float) this.c.size()) > this.f / this.x) {
                synchronized (this) {
                    this.c.remove(0);
                    this.c.add(Short.valueOf(data));
                }
            } else {
                this.c.add(Short.valueOf(data));
            }
            if (System.currentTimeMillis() - this.p2 > ((long) this.a2)) {
                invalidate();
                this.p2 = System.currentTimeMillis();
            }
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11747, new Class[0], Void.TYPE).isSupported) {
            this.c.clear();
            g();
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11748, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            canvas.translate(0.0f, this.q / 2.0f);
            c(canvas);
            d(canvas);
        }
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.f = (float) w;
        this.q = (float) h;
    }

    private void d(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11749, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            Canvas mCanvas = canvas;
            for (int i = 0; i < this.c.size(); i++) {
                float x2 = ((float) i) * this.x;
                float y2 = ((((float) this.c.get(i).shortValue()) / ((float) this.d)) * this.q) / 2.0f;
                mCanvas.drawLine(x2, -y2, x2, y2, this.y);
            }
        }
    }

    private void c(Canvas mCanvas) {
        if (!PatchProxy.proxy(new Object[]{mCanvas}, this, changeQuickRedirect, false, 11750, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            mCanvas.drawLine(0.0f, 0.0f, this.f, 0.0f, this.z);
        }
    }
}
