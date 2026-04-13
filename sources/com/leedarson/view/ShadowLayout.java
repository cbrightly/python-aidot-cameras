package com.leedarson.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.leedarson.R$color;
import com.leedarson.R$dimen;
import com.leedarson.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class ShadowLayout extends FrameLayout {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int A4;
    private RectF B4;
    private boolean a1;
    private Paint a2;
    private int c;
    private int d;
    private float f;
    private boolean p0;
    private boolean p1;
    private Paint p2;
    private int p3;
    private int p4;
    private float q;
    private float x;
    private float y;
    private boolean z;
    private int z4;

    public ShadowLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.B4 = new RectF();
        d(context, attrs);
    }

    public void setMDx(float mDx) {
        Object[] objArr = {new Float(mDx)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11760, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            float abs = Math.abs(mDx);
            float f2 = this.f;
            if (abs <= f2) {
                this.x = mDx;
            } else if (mDx > 0.0f) {
                this.x = f2;
            } else {
                this.x = -f2;
            }
            g();
        }
    }

    public void setMDy(float mDy) {
        Object[] objArr = {new Float(mDy)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11761, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            float abs = Math.abs(mDy);
            float f2 = this.f;
            if (abs <= f2) {
                this.y = mDy;
            } else if (mDy > 0.0f) {
                this.y = f2;
            } else {
                this.y = -f2;
            }
            g();
        }
    }

    public float getmCornerRadius() {
        return this.q;
    }

    public void setmCornerRadius(int mCornerRadius) {
        Object[] objArr = {new Integer(mCornerRadius)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11762, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.q = (float) mCornerRadius;
            f(getWidth(), getHeight());
        }
    }

    public float getmShadowLimit() {
        return this.f;
    }

    public void setmShadowLimit(int mShadowLimit) {
        Object[] objArr = {new Integer(mShadowLimit)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11763, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.f = (float) mShadowLimit;
            g();
        }
    }

    public void setmShadowColor(int mShadowColor) {
        Object[] objArr = {new Integer(mShadowColor)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11764, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.d = mShadowColor;
            f(getWidth(), getHeight());
        }
    }

    public void setLeftShow(boolean leftShow) {
        Object[] objArr = {new Byte(leftShow ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11765, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.z = leftShow;
            g();
        }
    }

    public void setRightShow(boolean rightShow) {
        Object[] objArr = {new Byte(rightShow ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11766, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.p0 = rightShow;
            g();
        }
    }

    public void setTopShow(boolean topShow) {
        Object[] objArr = {new Byte(topShow ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11767, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.a1 = topShow;
            g();
        }
    }

    public void setBottomShow(boolean bottomShow) {
        Object[] objArr = {new Byte(bottomShow ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11768, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.p1 = bottomShow;
            g();
        }
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        Object[] objArr = {new Integer(w), new Integer(h), new Integer(oldw), new Integer(oldh)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11769, new Class[]{cls, cls, cls, cls}, Void.TYPE).isSupported) {
            super.onSizeChanged(w, h, oldw, oldh);
            if (w > 0 && h > 0) {
                f(w, h);
            }
        }
    }

    private void d(Context context, AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{context, attrs}, this, changeQuickRedirect, false, 11770, new Class[]{Context.class, AttributeSet.class}, Void.TYPE).isSupported) {
            c(attrs);
            Paint paint = new Paint();
            this.a2 = paint;
            paint.setAntiAlias(true);
            this.a2.setStyle(Paint.Style.FILL);
            Paint paint2 = new Paint(1);
            this.p2 = paint2;
            paint2.setStyle(Paint.Style.FILL);
            this.p2.setColor(this.c);
            g();
        }
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11771, new Class[0], Void.TYPE).isSupported) {
            int xPadding = (int) (this.f + Math.abs(this.x));
            int yPadding = (int) (this.f + Math.abs(this.y));
            if (this.z) {
                this.p3 = xPadding;
            } else {
                this.p3 = 0;
            }
            if (this.a1) {
                this.p4 = yPadding;
            } else {
                this.p4 = 0;
            }
            if (this.p0) {
                this.z4 = xPadding;
            } else {
                this.z4 = 0;
            }
            if (this.p1) {
                this.A4 = yPadding;
            } else {
                this.A4 = 0;
            }
            setPadding(this.p3, this.p4, this.z4, this.A4);
        }
    }

    private void f(int w, int h) {
        Object[] objArr = {new Integer(w), new Integer(h)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11772, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            BitmapDrawable drawable = new BitmapDrawable(b(w, h, this.q, this.f, this.x, this.y, this.d, 0));
            if (Build.VERSION.SDK_INT <= 16) {
                setBackgroundDrawable(drawable);
            } else {
                setBackground(drawable);
            }
        }
    }

    private void c(AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{attrs}, this, changeQuickRedirect, false, 11773, new Class[]{AttributeSet.class}, Void.TYPE).isSupported) {
            TypedArray attr = getContext().obtainStyledAttributes(attrs, R$styleable.ShadowLayout);
            if (attr != null) {
                try {
                    this.z = attr.getBoolean(R$styleable.ShadowLayout_hl_leftShow, true);
                    this.p0 = attr.getBoolean(R$styleable.ShadowLayout_hl_rightShow, true);
                    this.p1 = attr.getBoolean(R$styleable.ShadowLayout_hl_bottomShow, true);
                    this.a1 = attr.getBoolean(R$styleable.ShadowLayout_hl_topShow, true);
                    this.q = attr.getDimension(R$styleable.ShadowLayout_hl_cornerRadius, getResources().getDimension(R$dimen.dp_0));
                    this.f = attr.getDimension(R$styleable.ShadowLayout_hl_shadowLimit, getResources().getDimension(R$dimen.dp_5));
                    this.x = attr.getDimension(R$styleable.ShadowLayout_hl_dx, 0.0f);
                    this.y = attr.getDimension(R$styleable.ShadowLayout_hl_dy, 0.0f);
                    int color = attr.getColor(R$styleable.ShadowLayout_hl_shadowColor, getResources().getColor(R$color.default_shadow_color));
                    this.d = color;
                    e(color);
                    this.c = attr.getColor(R$styleable.ShadowLayout_hl_shadowBackColor, getResources().getColor(R$color.default_shadowback_color));
                } finally {
                    attr.recycle();
                }
            }
        }
    }

    private Bitmap b(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius, float dx, float dy, int i, int i2) {
        Object[] objArr = {new Integer(shadowWidth), new Integer(shadowHeight), new Float(cornerRadius), new Float(shadowRadius), new Float(dx), new Float(dy), new Integer(i), new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class cls2 = Float.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11774, new Class[]{cls, cls, cls2, cls2, cls2, cls2, cls, cls}, Bitmap.class);
        if (proxy.isSupported) {
            return (Bitmap) proxy.result;
        }
        int fillColor = i2;
        int shadowColor = i;
        float dx2 = dx / 4.0f;
        float dy2 = dy / 4.0f;
        int shadowWidth2 = shadowWidth / 4;
        int shadowHeight2 = shadowHeight / 4;
        float cornerRadius2 = cornerRadius / 4.0f;
        float shadowRadius2 = shadowRadius / 4.0f;
        Bitmap output = Bitmap.createBitmap(shadowWidth2, shadowHeight2, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);
        RectF shadowRect = new RectF(shadowRadius2, shadowRadius2, ((float) shadowWidth2) - shadowRadius2, ((float) shadowHeight2) - shadowRadius2);
        if (dy2 > 0.0f) {
            shadowRect.top += dy2;
            shadowRect.bottom -= dy2;
        } else if (dy2 < 0.0f) {
            shadowRect.top += Math.abs(dy2);
            shadowRect.bottom -= Math.abs(dy2);
        }
        if (dx2 > 0.0f) {
            shadowRect.left += dx2;
            shadowRect.right -= dx2;
        } else if (dx2 < 0.0f) {
            shadowRect.left += Math.abs(dx2);
            shadowRect.right -= Math.abs(dx2);
        }
        this.a2.setColor(fillColor);
        if (!isInEditMode()) {
            this.a2.setShadowLayer(shadowRadius2, dx2, dy2, shadowColor);
        }
        canvas.drawRoundRect(shadowRect, cornerRadius2, cornerRadius2, this.a2);
        return output;
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11775, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            RectF rectF = this.B4;
            rectF.left = (float) this.p3;
            rectF.top = (float) this.p4;
            rectF.right = (float) (getWidth() - this.z4);
            this.B4.bottom = (float) (getHeight() - this.A4);
            RectF rectF2 = this.B4;
            int trueHeight = (int) (rectF2.bottom - rectF2.top);
            float f2 = this.q;
            if (f2 > ((float) (trueHeight / 2))) {
                canvas.drawRoundRect(rectF2, (float) (trueHeight / 2), (float) (trueHeight / 2), this.p2);
            } else {
                canvas.drawRoundRect(rectF2, f2, f2, this.p2);
            }
        }
    }

    public void e(int color) {
        if (!PatchProxy.proxy(new Object[]{new Integer(color)}, this, changeQuickRedirect, false, 11776, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (Color.alpha(color) == 255) {
                String red = Integer.toHexString(Color.red(color));
                String green = Integer.toHexString(Color.green(color));
                String blue = Integer.toHexString(Color.blue(color));
                if (red.length() == 1) {
                    red = "0" + red;
                }
                if (green.length() == 1) {
                    green = "0" + green;
                }
                if (blue.length() == 1) {
                    blue = "0" + blue;
                }
                this.d = a("#2a" + red + green + blue);
            }
        }
    }

    public static int a(String argb) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{argb}, (Object) null, changeQuickRedirect, true, 11777, new Class[]{String.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (!argb.startsWith("#")) {
            argb = "#" + argb;
        }
        return Color.parseColor(argb);
    }
}
