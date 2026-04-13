package com.leedarson.serviceimpl.camera.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class ClipView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Xfermode a1;
    private Paint c;
    private Paint d;
    private float f;
    private a p0;
    private int q;
    private int x;
    private int y;
    private int z;

    public enum a {
        CIRCLE,
        RECTANGLE;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public ClipView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.c = new Paint();
        this.d = new Paint();
        this.p0 = a.CIRCLE;
        this.c.setAntiAlias(true);
        this.d.setStyle(Paint.Style.STROKE);
        this.d.setColor(-1);
        this.d.setStrokeWidth((float) this.q);
        this.d.setAntiAlias(true);
        this.a1 = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 7487, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), (Paint) null, 31);
            canvas.drawColor(Color.parseColor("#40000000"));
            this.c.setXfermode(this.a1);
            a aVar = this.p0;
            if (aVar == a.CIRCLE) {
                canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) this.x, this.c);
                canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) this.x, this.d);
            } else if (aVar == a.RECTANGLE) {
                canvas.drawRect(this.f, (float) ((getHeight() / 2) - (this.z / 2)), ((float) getWidth()) - this.f, (float) ((getHeight() / 2) + (this.z / 2)), this.c);
                canvas.drawRect(this.f, (float) ((getHeight() / 2) - (this.z / 2)), ((float) getWidth()) - this.f, (float) ((getHeight() / 2) + (this.z / 2)), this.d);
            }
            canvas.restore();
        }
    }

    public Rect getClipRect() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7488, new Class[0], Rect.class);
        if (proxy.isSupported) {
            return (Rect) proxy.result;
        }
        Rect rect = new Rect();
        rect.left = (getWidth() / 2) - this.x;
        rect.right = (getWidth() / 2) + this.x;
        rect.top = (getHeight() / 2) - this.x;
        rect.bottom = (getHeight() / 2) + this.x;
        return rect;
    }

    public void setClipBorderWidth(int clipBorderWidth) {
        Object[] objArr = {new Integer(clipBorderWidth)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7489, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.q = clipBorderWidth;
            this.d.setStrokeWidth((float) clipBorderWidth);
            invalidate();
        }
    }

    public void setmHorizontalPadding(float mHorizontalPadding) {
        Object[] objArr = {new Float(mHorizontalPadding)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7490, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.f = mHorizontalPadding;
            int b = ((int) (((float) b(getContext())) - (2.0f * mHorizontalPadding))) / 2;
            this.x = b;
            int i = b * 2;
            this.y = i;
            this.z = i;
        }
    }

    public void setRatio(float ratio) {
        Object[] objArr = {new Float(ratio)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7491, new Class[]{Float.TYPE}, Void.TYPE).isSupported) {
            this.z = Math.min(a(getContext()), (int) (((float) this.y) / ratio));
        }
    }

    public static int b(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7492, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            DisplayMetrics outMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.widthPixels;
        } catch (Exception e) {
            e.printStackTrace();
            return 720;
        }
    }

    public static int a(Context context) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{context}, (Object) null, changeQuickRedirect, true, 7493, new Class[]{Context.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            DisplayMetrics outMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(outMetrics);
            return outMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
            return 720;
        }
    }

    public void setClipType(a clipType) {
        this.p0 = clipType;
    }
}
