package com.leedarson.view.rangeseekbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import com.leedarson.R$color;
import com.leedarson.R$drawable;
import com.leedarson.R$styleable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.DecimalFormat;
import java.util.Locale;

/* compiled from: SeekBar */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A;
    boolean B;
    Bitmap C;
    Bitmap D;
    Bitmap E;
    ValueAnimator F;
    String G;
    boolean H = false;
    boolean I = true;
    boolean J = false;
    RangeSeekBar K;
    String L;
    Path M = new Path();
    Rect N = new Rect();
    Rect O = new Rect();
    Paint P = new Paint(1);
    DecimalFormat Q;
    int R;
    int S;
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private float i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    float s;
    int t;
    int u;
    int v;
    int w;
    float x;
    float y;
    float z = 0.0f;

    public c(RangeSeekBar rangeSeekBar, AttributeSet attrs, boolean isLeft) {
        this.K = rangeSeekBar;
        this.B = isLeft;
        A(attrs);
        B();
        C();
    }

    private void A(AttributeSet attrs) {
        if (!PatchProxy.proxy(new Object[]{attrs}, this, changeQuickRedirect, false, 11953, new Class[]{AttributeSet.class}, Void.TYPE).isSupported) {
            TypedArray t2 = d().obtainStyledAttributes(attrs, R$styleable.RangeSeekBar);
            if (t2 != null) {
                this.d = (int) t2.getDimension(R$styleable.RangeSeekBar_rsb_indicator_margin, 0.0f);
                this.e = t2.getResourceId(R$styleable.RangeSeekBar_rsb_indicator_drawable, 0);
                this.a = t2.getInt(R$styleable.RangeSeekBar_rsb_indicator_show_mode, 1);
                this.b = t2.getLayoutDimension(R$styleable.RangeSeekBar_rsb_indicator_height, -1);
                this.c = t2.getLayoutDimension(R$styleable.RangeSeekBar_rsb_indicator_width, -1);
                this.g = (int) t2.getDimension(R$styleable.RangeSeekBar_rsb_indicator_text_size, (float) e.b(d(), 14.0f));
                this.h = t2.getColor(R$styleable.RangeSeekBar_rsb_indicator_text_color, -1);
                this.j = t2.getColor(R$styleable.RangeSeekBar_rsb_indicator_background_color, ContextCompat.getColor(d(), R$color.colorAccent));
                this.k = (int) t2.getDimension(R$styleable.RangeSeekBar_rsb_indicator_padding_left, 0.0f);
                this.l = (int) t2.getDimension(R$styleable.RangeSeekBar_rsb_indicator_padding_right, 0.0f);
                this.m = (int) t2.getDimension(R$styleable.RangeSeekBar_rsb_indicator_padding_top, 0.0f);
                this.n = (int) t2.getDimension(R$styleable.RangeSeekBar_rsb_indicator_padding_bottom, 0.0f);
                this.f = (int) t2.getDimension(R$styleable.RangeSeekBar_rsb_indicator_arrow_size, 0.0f);
                this.o = t2.getResourceId(R$styleable.RangeSeekBar_rsb_thumb_drawable, R$drawable.rsb_default_thumb);
                this.p = t2.getResourceId(R$styleable.RangeSeekBar_rsb_thumb_inactivated_drawable, 0);
                this.q = (int) t2.getDimension(R$styleable.RangeSeekBar_rsb_thumb_width, (float) e.b(d(), 26.0f));
                this.r = (int) t2.getDimension(R$styleable.RangeSeekBar_rsb_thumb_height, (float) e.b(d(), 26.0f));
                this.s = t2.getFloat(R$styleable.RangeSeekBar_rsb_thumb_scale_ratio, 1.0f);
                this.i = t2.getDimension(R$styleable.RangeSeekBar_rsb_indicator_radius, 0.0f);
                t2.recycle();
            }
        }
    }

    public void C() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11954, new Class[0], Void.TYPE).isSupported) {
            this.R = this.q;
            this.S = this.r;
            if (this.b == -1) {
                this.b = e.g("8", (float) this.g).height() + this.m + this.n;
            }
            if (this.f <= 0) {
                this.f = this.q / 4;
            }
        }
    }

    public Context d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11955, new Class[0], Context.class);
        return proxy.isSupported ? (Context) proxy.result : this.K.getContext();
    }

    public Resources u() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11956, new Class[0], Resources.class);
        if (proxy.isSupported) {
            return (Resources) proxy.result;
        }
        if (d() != null) {
            return d().getResources();
        }
        return null;
    }

    private void B() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11957, new Class[0], Void.TYPE).isSupported) {
            K(this.e);
            Q(this.o, this.q, this.r);
            R(this.p, this.q, this.r);
        }
    }

    public void G(int x2, int y2) {
        Object[] objArr = {new Integer(x2), new Integer(y2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11958, clsArr, Void.TYPE).isSupported) {
            C();
            B();
            this.t = (int) (((float) x2) - (y() / 2.0f));
            this.u = (int) (((float) x2) + (y() / 2.0f));
            this.v = y2 - (v() / 2);
            this.w = (v() / 2) + y2;
        }
    }

    public void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11959, new Class[0], Void.TYPE).isSupported) {
            this.R = (int) y();
            this.S = (int) w();
            int y2 = this.K.getProgressBottom();
            int i2 = this.S;
            this.v = y2 - (i2 / 2);
            this.w = (i2 / 2) + y2;
            Q(this.o, this.R, i2);
        }
    }

    public void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11960, new Class[0], Void.TYPE).isSupported) {
            this.R = z();
            this.S = v();
            int y2 = this.K.getProgressBottom();
            int i2 = this.S;
            this.v = y2 - (i2 / 2);
            this.w = (i2 / 2) + y2;
            Q(this.o, this.R, i2);
        }
    }

    public float t() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11961, new Class[0], Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        return ((float) (g() + e() + h())) + w();
    }

    public void b(Canvas canvas, boolean isLeft) {
        int offset;
        if (!PatchProxy.proxy(new Object[]{canvas, new Byte(isLeft ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 11962, new Class[]{Canvas.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (this.I) {
                if (isLeft) {
                    float f2 = this.x;
                    if (f2 < 1.0E-4f) {
                        offset = ((int) (((float) this.K.getProgressWidth()) * this.x)) + e.b(d(), 10.0f);
                    } else if (Math.abs((1.0f - f2) - this.y) < 1.0E-4f) {
                        offset = ((int) (((float) this.K.getProgressWidth()) * this.x)) - e.b(d(), 2.0f);
                    } else {
                        offset = (int) (((float) this.K.getProgressWidth()) * this.x);
                    }
                } else {
                    float f3 = this.x;
                    if (1.0f - f3 < 1.0E-4f) {
                        offset = ((int) (((float) this.K.getProgressWidth()) * this.x)) - e.b(d(), 10.0f);
                    } else if (Math.abs(f3 - this.y) < 1.0E-4f) {
                        offset = e.b(d(), 2.0f) + ((int) (((float) this.K.getProgressWidth()) * this.x));
                    } else {
                        offset = (int) (((float) this.K.getProgressWidth()) * this.x);
                    }
                }
                canvas.save();
                canvas.translate((float) offset, 0.0f);
                if (this.A) {
                    E(canvas, this.P, c(this.G));
                }
                F(canvas);
                canvas.restore();
            }
        }
    }

    public void F(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11963, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            Bitmap bitmap = this.D;
            if (bitmap == null || this.H) {
                Bitmap bitmap2 = this.C;
                if (bitmap2 != null) {
                    canvas.drawBitmap(bitmap2, 0.0f, ((float) this.K.getProgressTop()) + (((float) (this.K.getProgressHeight() - this.S)) / 2.0f), (Paint) null);
                    return;
                }
                return;
            }
            canvas.drawBitmap(bitmap, 0.0f, ((float) this.K.getProgressTop()) + (((float) (this.K.getProgressHeight() - this.S)) / 2.0f), (Paint) null);
        }
    }

    public String c(String text2Draw) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{text2Draw}, this, changeQuickRedirect, false, 11964, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        d[] states = this.K.getRangeSeekBarState();
        if (TextUtils.isEmpty(text2Draw)) {
            if (this.B) {
                DecimalFormat decimalFormat = this.Q;
                if (decimalFormat != null) {
                    text2Draw = decimalFormat.format((double) states[0].c);
                    if (this.J) {
                        text2Draw = states[0].a;
                    }
                } else {
                    text2Draw = states[0].a;
                }
            } else {
                DecimalFormat decimalFormat2 = this.Q;
                if (decimalFormat2 != null) {
                    text2Draw = decimalFormat2.format((double) states[1].c);
                    if (this.J) {
                        text2Draw = states[1].a;
                    }
                } else {
                    text2Draw = states[1].a;
                }
            }
        }
        String str = this.L;
        if (str == null) {
            return text2Draw;
        }
        return String.format(Locale.US, str, new Object[]{text2Draw});
    }

    public void E(Canvas canvas, Paint paint, String str) {
        int tx;
        int ty;
        if (!PatchProxy.proxy(new Object[]{canvas, paint, str}, this, changeQuickRedirect, false, 11965, new Class[]{Canvas.class, Paint.class, String.class}, Void.TYPE).isSupported) {
            Paint paint2 = paint;
            Canvas canvas2 = canvas;
            String text2Draw = str;
            if (text2Draw != null) {
                paint2.setTextSize((float) this.g);
                paint2.setStyle(Paint.Style.FILL);
                paint2.setColor(this.j);
                paint2.getTextBounds(text2Draw, 0, text2Draw.length(), this.N);
                int realIndicatorWidth = this.N.width() + this.k + this.l;
                if (this.c > realIndicatorWidth) {
                    realIndicatorWidth = this.c;
                }
                int realIndicatorHeight = this.N.height() + this.m + this.n;
                if (this.b > realIndicatorHeight) {
                    realIndicatorHeight = this.b;
                }
                Rect rect = this.O;
                int i2 = this.R;
                int i3 = (int) ((((float) i2) / 2.0f) - (((float) realIndicatorWidth) / 2.0f));
                rect.left = i3;
                int i4 = ((this.w - realIndicatorHeight) - this.S) - this.d;
                rect.top = i4;
                rect.right = i3 + realIndicatorWidth;
                rect.bottom = i4 + realIndicatorHeight;
                if (this.E == null) {
                    int ax = i2 / 2;
                    int ay = rect.bottom;
                    int i5 = this.f;
                    int by = ay - i5;
                    this.M.reset();
                    this.M.moveTo((float) ax, (float) ay);
                    this.M.lineTo((float) (ax - i5), (float) by);
                    this.M.lineTo((float) (i5 + ax), (float) by);
                    this.M.close();
                    canvas2.drawPath(this.M, paint2);
                    Rect rect2 = this.O;
                    int i6 = rect2.bottom;
                    int i7 = this.f;
                    rect2.bottom = i6 - i7;
                    rect2.top -= i7;
                }
                int defaultPaddingOffset = e.b(d(), 1.0f);
                int leftOffset = (((this.O.width() / 2) - ((int) (((float) this.K.getProgressWidth()) * this.x))) - this.K.getProgressLeft()) + defaultPaddingOffset;
                int rightOffset = (((this.O.width() / 2) - ((int) (((float) this.K.getProgressWidth()) * (1.0f - this.x)))) - this.K.getProgressPaddingRight()) + defaultPaddingOffset;
                if (leftOffset > 0) {
                    Rect rect3 = this.O;
                    rect3.left += leftOffset;
                    rect3.right += leftOffset;
                } else if (rightOffset > 0) {
                    Rect rect4 = this.O;
                    rect4.left -= rightOffset;
                    rect4.right -= rightOffset;
                }
                Bitmap bitmap = this.E;
                if (bitmap != null) {
                    e.c(canvas2, paint2, bitmap, this.O);
                } else if (this.i > 0.0f) {
                    RectF rectF = new RectF(this.O);
                    float f2 = this.i;
                    canvas2.drawRoundRect(rectF, f2, f2, paint2);
                } else {
                    canvas2.drawRect(this.O, paint2);
                }
                int i8 = this.k;
                if (i8 > 0) {
                    tx = this.O.left + i8;
                } else {
                    int i9 = this.l;
                    if (i9 > 0) {
                        tx = (this.O.right - i9) - this.N.width();
                    } else {
                        tx = ((realIndicatorWidth - this.N.width()) / 2) + this.O.left;
                    }
                }
                if (this.m > 0) {
                    ty = this.O.top + this.N.height() + this.m;
                } else if (this.n > 0) {
                    ty = (this.O.bottom - this.N.height()) - this.n;
                } else {
                    ty = (this.O.bottom - ((realIndicatorHeight - this.N.height()) / 2)) + 1;
                }
                paint2.setColor(this.h);
                canvas2.drawText(text2Draw, (float) tx, (float) ty, paint2);
            }
        }
    }

    public boolean a(float x2, float y2) {
        Object[] objArr = {new Float(x2), new Float(y2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11966, new Class[]{cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int offset = (int) (((float) this.K.getProgressWidth()) * this.x);
        if (x2 <= ((float) (this.t + offset)) || x2 >= ((float) (this.u + offset)) || y2 <= ((float) this.v) || y2 >= ((float) this.w)) {
            return false;
        }
        return true;
    }

    public void U(float percent) {
        if (percent < 0.0f) {
            percent = 0.0f;
        } else if (percent > 1.0f) {
            percent = 1.0f;
        }
        this.x = percent;
    }

    public void P(boolean isEnable) {
        switch (this.a) {
            case 0:
                this.A = isEnable;
                return;
            case 1:
                this.A = false;
                return;
            case 2:
            case 3:
                this.A = true;
                return;
            default:
                return;
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11967, new Class[0], Void.TYPE).isSupported) {
            ValueAnimator valueAnimator = this.F;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.z, 0.0f});
            this.F = ofFloat;
            ofFloat.addUpdateListener(new a());
            this.F.addListener(new b());
            this.F.start();
        }
    }

    /* compiled from: SeekBar */
    public class a implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 11976, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                c.this.z = ((Float) animation.getAnimatedValue()).floatValue();
                RangeSeekBar rangeSeekBar = c.this.K;
                if (rangeSeekBar != null) {
                    rangeSeekBar.invalidate();
                }
            }
        }
    }

    /* compiled from: SeekBar */
    public class b extends AnimatorListenerAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onAnimationEnd(Animator animator) {
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 11977, new Class[]{Animator.class}, Void.TYPE).isSupported) {
                c cVar = c.this;
                cVar.z = 0.0f;
                RangeSeekBar rangeSeekBar = cVar.K;
                if (rangeSeekBar != null) {
                    rangeSeekBar.invalidate();
                }
            }
        }
    }

    public void L(String text) {
        this.G = text;
    }

    public void M(String formatPattern) {
        if (!PatchProxy.proxy(new Object[]{formatPattern}, this, changeQuickRedirect, false, 11968, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.Q = new DecimalFormat(formatPattern);
        }
    }

    public void O(String formatPattern) {
        this.L = formatPattern;
    }

    public void K(@DrawableRes int indicatorDrawableId) {
        Object[] objArr = {new Integer(indicatorDrawableId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11969, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (indicatorDrawableId != 0) {
                this.e = indicatorDrawableId;
                this.E = BitmapFactory.decodeResource(u(), indicatorDrawableId);
            }
        }
    }

    public int e() {
        return this.f;
    }

    public int j() {
        return this.k;
    }

    public int k() {
        return this.l;
    }

    public int l() {
        return this.m;
    }

    public int i() {
        return this.n;
    }

    public int h() {
        return this.d;
    }

    public int o() {
        return this.a;
    }

    public int n() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11970, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int i2 = this.b;
        if (i2 > 0) {
            if (this.E != null) {
                return i2 + this.d;
            }
            return i2 + this.f + this.d;
        } else if (this.E != null) {
            return e.g("8", (float) this.g).height() + this.m + this.n + this.d;
        } else {
            return e.g("8", (float) this.g).height() + this.m + this.n + this.d + this.f;
        }
    }

    public int g() {
        return this.b;
    }

    public int r() {
        return this.c;
    }

    public int q() {
        return this.g;
    }

    public void N(int indicatorTextSize) {
        this.g = indicatorTextSize;
    }

    public int p() {
        return this.h;
    }

    public int f() {
        return this.j;
    }

    public void R(@DrawableRes int thumbInactivatedDrawableId, int width, int height) {
        Object[] objArr = {new Integer(thumbInactivatedDrawableId), new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11971, clsArr, Void.TYPE).isSupported) {
            if (thumbInactivatedDrawableId != 0 && u() != null) {
                this.p = thumbInactivatedDrawableId;
                if (Build.VERSION.SDK_INT >= 21) {
                    this.D = e.e(width, height, u().getDrawable(thumbInactivatedDrawableId, (Resources.Theme) null));
                } else {
                    this.D = e.e(width, height, u().getDrawable(thumbInactivatedDrawableId));
                }
            }
        }
    }

    public void Q(@DrawableRes int thumbDrawableId, int width, int height) {
        Object[] objArr = {new Integer(thumbDrawableId), new Integer(width), new Integer(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11972, clsArr, Void.TYPE).isSupported) {
            if (thumbDrawableId != 0 && u() != null && width > 0 && height > 0) {
                this.o = thumbDrawableId;
                if (Build.VERSION.SDK_INT >= 21) {
                    this.C = e.e(width, height, u().getDrawable(thumbDrawableId, (Resources.Theme) null));
                } else {
                    this.C = e.e(width, height, u().getDrawable(thumbDrawableId));
                }
            }
        }
    }

    public int z() {
        return this.q;
    }

    public float w() {
        return ((float) this.r) * this.s;
    }

    public float y() {
        return ((float) this.q) * this.s;
    }

    public int v() {
        return this.r;
    }

    public float m() {
        return this.i;
    }

    public void J(boolean activate) {
        this.H = activate;
    }

    public float x() {
        return this.s;
    }

    public void T(boolean visible) {
        this.I = visible;
    }

    public void S(boolean isTimeRange) {
        this.J = isTimeRange;
    }

    public float s() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11975, new Class[0], Float.TYPE);
        if (proxy.isSupported) {
            return ((Float) proxy.result).floatValue();
        }
        return this.K.getMinProgress() + (this.x * (this.K.getMaxProgress() - this.K.getMinProgress()));
    }
}
