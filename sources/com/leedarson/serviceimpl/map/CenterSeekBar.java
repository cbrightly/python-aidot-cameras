package com.leedarson.serviceimpl.map;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class CenterSeekBar extends View {
    private static final String c = CenterSeekBar.class.getSimpleName();
    public static ChangeQuickRedirect changeQuickRedirect;
    int A4;
    protected int B4;
    protected int C4;
    protected int D4;
    protected int E4;
    private int F4;
    private int G4;
    private int H4;
    private int a1;
    private a a2;
    private Drawable d;
    private Drawable f;
    private int p0;
    private int p1;
    private int p2;
    int p3;
    int p4;
    private Drawable q;
    private int x;
    private int y;
    private int z;
    int z4;

    public interface a {
        void a(CenterSeekBar centerSeekBar);

        void b(CenterSeekBar centerSeekBar, int i, boolean z);

        void c(CenterSeekBar centerSeekBar);
    }

    public CenterSeekBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public CenterSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a1 = 0;
        this.p1 = 0;
        this.z4 = 12;
        this.B4 = 0;
        this.C4 = 0;
        this.D4 = 0;
        this.E4 = 0;
        this.G4 = 0;
        TypedArray a3 = context.obtainStyledAttributes(attrs, R$styleable.CenterSeekBar, defStyleAttr, defStyleAttr);
        Drawable drawable = a3.getDrawable(R$styleable.CenterSeekBar_thumb);
        this.d = drawable;
        if (drawable == null) {
            this.d = getResources().getDrawable(R$drawable.seekbar_thumb_selector);
        }
        Drawable drawable2 = a3.getDrawable(R$styleable.CenterSeekBar_progressDrawable);
        this.q = drawable2;
        if (drawable2 == null) {
            this.q = getResources().getDrawable(R$drawable.seekbar_progress);
        }
        Drawable drawable3 = a3.getDrawable(R$styleable.CenterSeekBar_backgroundDrawable);
        this.f = drawable3;
        if (drawable3 == null) {
            this.f = getResources().getDrawable(R$drawable.seekbar_background);
        }
        this.H4 = a3.getInt(R$styleable.CenterSeekBar_progress, 0);
        this.G4 = a3.getInt(R$styleable.CenterSeekBar_min, 0);
        this.F4 = a3.getInt(R$styleable.CenterSeekBar_max, 0);
        this.y = this.f.getIntrinsicHeight();
        this.x = this.f.getIntrinsicWidth();
        this.p0 = this.d.getIntrinsicHeight();
        this.z = this.d.getIntrinsicWidth();
        a3.recycle();
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8133, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            Drawable d2 = this.q;
            Drawable drawable = this.d;
            int thumbHeight = drawable == null ? 0 : drawable.getIntrinsicHeight();
            int dw = 0;
            int dh = 0;
            if (d2 != null) {
                dw = Math.max(this.p3, Math.min(this.p4, d2.getIntrinsicWidth()));
                dh = Math.max(thumbHeight, Math.max(this.z4, Math.min(this.A4, d2.getIntrinsicHeight())));
            }
            int dw2 = dw + this.B4 + this.C4;
            int dh2 = dh + this.D4 + this.E4;
            this.x = View.resolveSizeAndState(dw2, widthMeasureSpec, 0);
            int resolveSizeAndState = View.resolveSizeAndState(dh2, heightMeasureSpec, 0);
            this.y = resolveSizeAndState;
            int i = this.x;
            int i2 = i / 2;
            this.a1 = i2;
            int i3 = this.z;
            this.p1 = i2 - (i3 / 2);
            setMeasuredDimension(i + i3, resolveSizeAndState);
        }
    }

    public synchronized void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 8134, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            Drawable drawable = this.f;
            int i = this.z;
            drawable.setBounds(i / 2, 0, this.x - (i / 2), this.y);
            this.f.draw(canvas);
            int i2 = this.a1;
            int i3 = this.x;
            if (i2 > i3 / 2) {
                if (i2 - (i3 / 2) >= this.z) {
                    this.q.setBounds(i3 / 2, 0, i2, this.y);
                    this.q.draw(canvas);
                }
                int i4 = this.a1;
                int i5 = this.z;
                int left = i4 - i5;
                int i6 = this.p1;
                if (left <= i6) {
                    left = i6;
                }
                this.d.setBounds(left, 0, i5 + left, this.p0);
            } else if (i2 < i3 / 2) {
                if ((i3 / 2) - i2 >= this.z) {
                    this.q.setBounds(i2, 0, i3 / 2, this.y);
                    this.q.draw(canvas);
                }
                int left2 = this.a1;
                int i7 = this.p1;
                if (left2 >= i7) {
                    left2 = i7;
                }
                this.d.setBounds(left2, 0, this.z + left2, this.p0);
            } else {
                int i8 = this.z;
                if ((i3 / 2) - ((i8 / 2) + i2) >= i8) {
                    this.q.setBounds(i2 + (i8 / 2), 0, i3 / 2, this.y);
                    this.q.draw(canvas);
                }
                Drawable drawable2 = this.d;
                int i9 = this.a1;
                int i10 = this.z;
                drawable2.setBounds(i9 - (i10 / 2), 0, i9 + (i10 / 2), this.p0);
            }
            this.d.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        float scale;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 8135, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        switch (event.getAction()) {
            case 0:
                this.p2 = 1;
                setPressed(true);
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                a aVar = this.a2;
                if (aVar != null) {
                    aVar.a(this);
                    break;
                }
                break;
            case 1:
                setPressed(false);
                invalidate();
                a aVar2 = this.a2;
                if (aVar2 != null) {
                    aVar2.c(this);
                    break;
                }
                break;
            case 2:
                if (this.p2 == 1) {
                    if (event.getX() <= ((float) (this.z / 2))) {
                        scale = 0.0f;
                    } else if (event.getX() >= ((float) (this.x - (this.z / 2)))) {
                        scale = 1.0f;
                    } else {
                        scale = Math.abs(event.getX() - ((float) (this.z / 2))) / ((float) (this.x - this.z));
                    }
                    int i = this.F4;
                    int i2 = this.G4;
                    int progressPosition = (int) (((float) (i - i2)) * scale);
                    int i3 = this.x;
                    int i4 = this.z;
                    this.a1 = (((i3 - i4) * progressPosition) / (i - i2)) + (i4 / 2);
                    int i5 = progressPosition + i2;
                    this.H4 = i5;
                    if (progressPosition > i) {
                        this.H4 = i;
                    } else if (i5 < i2) {
                        this.H4 = i2;
                    }
                    a aVar3 = this.a2;
                    if (aVar3 != null) {
                        aVar3.b(this, this.H4, true);
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    public void drawableStateChanged() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8136, new Class[0], Void.TYPE).isSupported) {
            super.drawableStateChanged();
            Drawable thumb = this.d;
            if (thumb != null && thumb.isStateful() && thumb.setState(getDrawableState())) {
                invalidateDrawable(thumb);
            }
        }
    }

    public int getMaxProgress() {
        return this.F4;
    }

    public void setMaxProgress(int maxProgress) {
        this.F4 = maxProgress;
    }

    public int getMinProgress() {
        return this.G4;
    }

    public void setMinProgress(int minProgress) {
        this.G4 = minProgress;
    }

    public int getProgress() {
        return this.H4;
    }

    public void setProgress(int progress) {
        Object[] objArr = {new Integer(progress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8137, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            int i = this.F4;
            if (progress >= i || progress <= this.G4) {
                this.H4 = this.G4;
            } else {
                this.H4 = progress;
            }
            int i2 = this.G4;
            this.a1 = (this.x * (progress - i2)) / (i - i2);
            invalidate();
        }
    }

    public void setOnSeekBarChangeListener(a listener) {
        this.a2 = listener;
    }
}
