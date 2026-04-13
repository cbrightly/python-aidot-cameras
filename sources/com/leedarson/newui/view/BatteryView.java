package com.leedarson.newui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.leedarson.R$color;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class BatteryView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    boolean a1 = false;
    private int c = getResources().getColor(R$color.danger_color);
    private int d = getResources().getColor(R$color.danger_color_64);
    private int f = getResources().getColor(R$color.bg_card_color);
    Paint p0 = new Paint();
    private int q = getResources().getColor(R$color.white64);
    private float x = 0.3f;
    Paint y = new Paint();
    Paint z = new Paint();

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet set) {
        super(context, set);
        this.y.setAntiAlias(true);
        this.y.setStyle(Paint.Style.FILL);
        this.z.setAntiAlias(true);
        this.z.setStyle(Paint.Style.STROKE);
        this.z.setStrokeWidth((float) a(1.5f));
        this.p0.setAntiAlias(true);
        this.p0.setStyle(Paint.Style.FILL);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        this.p0.setTextSize((float) Math.round(20.0f * Math.min(((float) dm.widthPixels) / 720.0f, ((float) dm.heightPixels) / 1080.0f)));
    }

    private int a(float dpValue) {
        Object[] objArr = {new Float(dpValue)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4940, new Class[]{Float.TYPE}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return (int) ((dpValue * getResources().getDisplayMetrics().density) + 0.5f);
    }

    @SuppressLint({"DrawAllocation"})
    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 4941, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            Canvas canvas2 = canvas;
            super.onDraw(canvas2);
            if (this.x >= 0.2f || this.a1) {
                this.y.setColor(this.q);
                this.z.setColor(this.f);
                this.p0.setColor(this.f);
            } else {
                this.y.setColor(this.d);
                this.z.setColor(this.c);
                this.p0.setColor(this.c);
            }
            int a = getWidth() - a(2.0f);
            int b = getHeight() - a(1.5f);
            float d2 = ((float) a) * this.x;
            float right = (float) a(2.5f);
            float bottom = (float) a(1.5f);
            RectF re1 = new RectF((float) a(0.5f), (float) a(0.5f), d2 - right, ((float) b) + bottom);
            RectF re2 = new RectF(0.0f, 0.0f, ((float) a) - right, ((float) b) + bottom);
            int i = a;
            RectF re3 = new RectF(((float) a) - right, (float) (b / 3), (float) a, (((float) b) + bottom) - ((float) (b / 3)));
            canvas2.drawRect(re1, this.y);
            canvas2.drawRoundRect(re2, 3.0f, 3.0f, this.z);
            canvas2.drawRoundRect(re3, 3.0f, 3.0f, this.p0);
        }
    }

    public synchronized void setProgress(int percent) {
        Object[] objArr = {new Integer(percent)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4942, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.x = (float) (((double) percent) / 100.0d);
            postInvalidate();
        }
    }

    public void setCharging(boolean isCharging) {
        Object[] objArr = {new Byte(isCharging ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4943, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.a1 = isCharging;
            postInvalidate();
        }
    }
}
