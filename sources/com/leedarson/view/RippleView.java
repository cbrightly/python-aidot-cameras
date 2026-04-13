package com.leedarson.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import com.leedarson.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;
import java.util.List;

public class RippleView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean a1;
    private Context c;
    private Paint d;
    private float f;
    private int p0;
    private boolean p1;
    private float q;
    private List<a> x;
    private int y;
    private int z;

    public RippleView(Context context) {
        this(context, (AttributeSet) null);
    }

    public RippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray tya = context.obtainStyledAttributes(attrs, R$styleable.mRippleView);
        this.p0 = tya.getColor(R$styleable.mRippleView_cColor, -16776961);
        this.y = tya.getInt(R$styleable.mRippleView_cSpeed, 1);
        this.z = tya.getInt(R$styleable.mRippleView_cDensity, 10);
        this.a1 = tya.getBoolean(R$styleable.mRippleView_cIsFill, false);
        this.p1 = tya.getBoolean(R$styleable.mRippleView_cIsAlpha, false);
        tya.recycle();
        b();
    }

    public void setColor(int color) {
        Object[] objArr = {new Integer(color)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11751, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.p0 = color;
            Paint paint = this.d;
            if (paint != null) {
                paint.setColor(color);
            }
        }
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11752, new Class[0], Void.TYPE).isSupported) {
            this.c = getContext();
            Paint paint = new Paint();
            this.d = paint;
            paint.setColor(this.p0);
            this.d.setStrokeWidth((float) a.a(this.c, 1.0f));
            if (this.a1) {
                this.d.setStyle(Paint.Style.FILL);
            } else {
                this.d.setStyle(Paint.Style.STROKE);
            }
            this.d.setStrokeCap(Paint.Cap.ROUND);
            this.d.setAntiAlias(true);
            this.x = new ArrayList();
            this.x.add(new a(0, 255));
            this.z = a.a(this.c, (float) this.z);
            setBackgroundColor(0);
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11753, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            a(canvas);
        }
    }

    private void a(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11754, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            canvas.save();
            for (int i = 0; i < this.x.size(); i++) {
                a c2 = this.x.get(i);
                this.d.setAlpha(c2.b);
                canvas.drawCircle(this.f / 2.0f, this.q / 2.0f, ((float) c2.a) - this.d.getStrokeWidth(), this.d);
                int i2 = c2.a;
                float f2 = this.f;
                if (((float) i2) > f2 / 2.0f) {
                    this.x.remove(i);
                } else {
                    if (this.p1) {
                        c2.b = (int) (255.0d - (((double) i2) * (255.0d / (((double) f2) / 2.0d))));
                    }
                    c2.a = i2 + this.y;
                }
            }
            if (this.x.size() > 0) {
                List<a> list = this.x;
                if (list.get(list.size() - 1).a > a.a(this.c, (float) this.z)) {
                    this.x.add(new a(0, 255));
                }
            }
            invalidate();
            canvas.restore();
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11755, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int myWidthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec);
            int myWidthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int myHeightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec);
            int myHeightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec);
            if (myWidthSpecMode == 1073741824) {
                this.f = (float) myWidthSpecSize;
            } else {
                this.f = (float) a.a(this.c, 120.0f);
            }
            if (myHeightSpecMode == 1073741824) {
                this.q = (float) myHeightSpecSize;
            } else {
                this.q = (float) a.a(this.c, 120.0f);
            }
            setMeasuredDimension((int) this.f, (int) this.q);
        }
    }

    public class a {
        int a;
        int b;

        a(int width, int alpha) {
            this.a = width;
            this.b = alpha;
        }
    }
}
