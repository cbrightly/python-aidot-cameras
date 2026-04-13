package com.leedarson.newui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.leedarson.R$color;
import com.leedarson.R$drawable;
import com.leedarson.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class PTZCtrlView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int A4;
    private int B4;
    private int C4;
    private a D4;
    private long E4;
    private int a1;
    private int a2;
    private int c;
    private int d;
    private int f;
    private Bitmap p0;
    private Matrix p1;
    private int p2;
    private Paint p3;
    private boolean p4;
    private int q;
    private int x;
    private Point y;
    private RectF z;
    private int z4;

    public interface a {
        void a();

        void b(int i);

        void c(int i);
    }

    public PTZCtrlView(@NonNull Context context) {
        this(context, (AttributeSet) null);
    }

    public PTZCtrlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.c = 15;
        this.d = 5;
        this.f = 20;
        this.a2 = 0;
        this.p2 = 200;
        this.p4 = false;
        this.z4 = -1;
        this.E4 = 0;
        f(context, attrs);
    }

    private void f(Context context, AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{context, attrs}, this, changeQuickRedirect, false, 5218, new Class[]{Context.class, AttributeSet.class}, Void.TYPE).isSupported) {
            Paint paint = new Paint();
            this.p3 = paint;
            paint.setAntiAlias(true);
            this.p3.setStyle(Paint.Style.FILL);
            this.y = new Point();
            this.z = new RectF();
            this.p1 = new Matrix();
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R$styleable.PTZCtrlView);
            this.a1 = typedArray.getDimensionPixelSize(R$styleable.PTZCtrlView_ptz_bmp_size, 48);
            this.f = typedArray.getDimensionPixelSize(R$styleable.PTZCtrlView_ptz_bmp_margin, 20);
            this.d = typedArray.getDimensionPixelSize(R$styleable.PTZCtrlView_ptz_stroke_width, 5);
            typedArray.recycle();
            this.A4 = R$color.text_assist_color60;
            this.B4 = R$color.text_second_color;
            this.C4 = R$color.white100;
            this.p0 = e(context, R$drawable.ic_live_ptz_arrow);
        }
    }

    public Bitmap e(Context context, @DrawableRes int drawableId) {
        Object[] objArr = {context, new Integer(drawableId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5219, new Class[]{Context.class, Integer.TYPE}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        drawable.setTint(this.B4);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if ((drawable instanceof VectorDrawable) || (drawable instanceof VectorDrawableCompat)) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            int i = this.a1;
            drawable.setBounds(0, 0, i, i);
            drawable.draw(canvas);
            return bitmap;
        }
        throw new IllegalArgumentException("unsupported drawable type");
    }

    public boolean onTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5220, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (isEnabled()) {
            switch (event.getAction()) {
                case 0:
                    float downX = event.getX();
                    float downY = event.getY();
                    Point point = this.y;
                    float dx = downX - ((float) point.x);
                    float dy = downY - ((float) point.y);
                    if (a(dy) <= a(dx)) {
                        if ((this.c & 3) == 3) {
                            if (dx > 0.0f) {
                                h(1);
                            } else {
                                h(0);
                            }
                        }
                    } else if ((this.c & 12) == 12) {
                        if (dy > 0.0f) {
                            h(3);
                        } else {
                            h(2);
                        }
                    }
                    return true;
                case 1:
                case 3:
                    h(-1);
                    break;
                case 2:
                    b();
                    break;
            }
        }
        return false;
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5221, new Class[0], Void.TYPE).isSupported) {
            if (this.p4 && this.D4 != null && System.currentTimeMillis() - this.E4 >= ((long) this.p2)) {
                this.E4 = System.currentTimeMillis();
                this.D4.b(this.z4);
            }
        }
    }

    private float a(float f2) {
        Object[] objArr = {new Float(f2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5222, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        return Math.abs(f2);
    }

    public void setOnDirectTapListener(a listener) {
        this.D4 = listener;
    }

    public void g(int bmpSize, int bmpMargin, int strokeWidth, int strokeColor, int iconColor, int bgColor) {
        Object[] objArr = {new Integer(bmpSize), new Integer(bmpMargin), new Integer(strokeWidth), new Integer(strokeColor), new Integer(iconColor), new Integer(bgColor)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5223, clsArr, Void.TYPE).isSupported) {
            this.a1 = bmpSize;
            this.f = bmpMargin;
            this.d = strokeWidth;
            this.A4 = strokeColor;
            this.B4 = iconColor;
            this.C4 = bgColor;
            this.p0 = e(getContext(), R$drawable.ic_live_ptz_arrow);
            postInvalidate();
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5224, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            c(canvas);
            d(canvas);
        }
    }

    public void setDirection(int func) {
        Object[] objArr = {new Integer(func)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5225, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.c = func;
            postInvalidate();
        }
    }

    private void d(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5226, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            this.p3.setColor(-1);
            int i = this.c;
            int i2 = i & 12;
            if ((i & 12) == 12) {
                this.p1.reset();
                canvas.drawBitmap(this.p0, (float) (this.y.x - (this.a1 / 2)), (float) this.f, this.p3);
                this.p1.reset();
                this.p1.postRotate(180.0f);
                Bitmap bitmap = this.p0;
                int i3 = this.a1;
                Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, i3, i3, this.p1, true);
                int i4 = this.y.x;
                int i5 = this.a1;
                canvas.drawBitmap(dstbmp, (float) (i4 - (i5 / 2)), (float) ((this.x - i5) - this.f), this.p3);
            }
            if ((this.c & 3) == 3) {
                this.p1.reset();
                this.p1.postRotate(90.0f);
                Bitmap bitmap2 = this.p0;
                int i6 = this.a1;
                Bitmap dstbmp2 = Bitmap.createBitmap(bitmap2, 0, 0, i6, i6, this.p1, true);
                int i7 = this.q;
                int i8 = this.a1;
                canvas.drawBitmap(dstbmp2, (float) ((i7 - i8) - this.f), (float) ((this.x / 2) - (i8 / 2)), this.p3);
                this.p1.reset();
                this.p1.postRotate(270.0f);
                Bitmap bitmap3 = this.p0;
                int i9 = this.a1;
                canvas.drawBitmap(Bitmap.createBitmap(bitmap3, 0, 0, i9, i9, this.p1, true), (float) this.f, (float) ((this.x / 2) - (this.a1 / 2)), this.p3);
            }
        }
    }

    private void c(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5228, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            this.p3.setStyle(Paint.Style.STROKE);
            this.p3.setStrokeWidth((float) this.d);
            this.p3.setColor(this.A4);
            Point point = this.y;
            int i = point.x;
            canvas.drawCircle((float) i, (float) point.y, (float) (i - (this.d / 2)), this.p3);
        }
    }

    private void h(int direct) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{new Integer(direct)}, this, changeQuickRedirect, false, 5229, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            switch (direct) {
                case -1:
                    this.a2 = 0;
                    break;
                case 0:
                    this.a2 = 135;
                    break;
                case 1:
                    this.a2 = -45;
                    break;
                case 2:
                    this.a2 = 225;
                    break;
                case 3:
                    this.a2 = 45;
                    break;
            }
            if (direct == -1) {
                z2 = false;
            }
            this.p4 = z2;
            if (z2) {
                this.z4 = direct;
                this.E4 = System.currentTimeMillis();
            } else {
                this.z4 = -1;
            }
            postInvalidate();
            a aVar = this.D4;
            if (aVar == null) {
                return;
            }
            if (this.p4) {
                aVar.c(direct);
                ((Vibrator) getContext().getSystemService("vibrator")).vibrate(50);
                return;
            }
            aVar.a();
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5230, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            this.q = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            int defaultSize = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            this.x = defaultSize;
            setMeasuredDimension(this.q, defaultSize);
            Point point = this.y;
            int i = this.q;
            point.x = i / 2;
            int i2 = this.x;
            point.y = i2 / 2;
            RectF rectF = this.z;
            int i3 = this.d;
            rectF.left = (float) i3;
            rectF.top = (float) i3;
            rectF.right = (float) (i - i3);
            rectF.bottom = (float) (i2 - i3);
        }
    }

    public static int getDefaultSize(int size, int measureSpec) {
        Object[] objArr = {new Integer(size), new Integer(measureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 5231, new Class[]{cls, cls}, cls);
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

    public void setIntervalMills(int spaceMills) {
        this.p2 = spaceMills;
    }
}
