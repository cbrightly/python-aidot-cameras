package com.leedarson.newui.view.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.smartcamera.kvswebrtc.beans.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class RadarArcView extends View implements b {
    public static int c = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d = 0;
    public static int f = 0;
    public int a1 = 0;
    public a a2;
    public int p0;
    public int p1 = 0;
    Paint q;
    Paint x;
    Paint y;
    private final int z = com.leedarson.view.a.a(BaseApplication.b().getApplicationContext(), 0.5f);

    public RadarArcView(Context context) {
        super(context);
        b(context);
    }

    public RadarArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        b(context);
    }

    public RadarArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        b(context);
    }

    private void b(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5411, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Paint paint = new Paint();
            this.y = paint;
            paint.setColor(Color.parseColor("#B3FF8A2C"));
            this.y.setStyle(Paint.Style.FILL);
            this.y.setAntiAlias(true);
            Paint paint2 = new Paint();
            this.q = paint2;
            paint2.setColor(Color.parseColor("#80FF8A2C"));
            this.q.setStyle(Paint.Style.FILL);
            this.q.setAntiAlias(true);
            Paint paint3 = new Paint();
            this.x = paint3;
            paint3.setColor(Color.parseColor("#FF8A2C"));
            this.x.setStyle(Paint.Style.STROKE);
            this.x.setStrokeWidth((float) this.z);
            this.x.setAntiAlias(true);
        }
    }

    public void setStartAngle(int startAngle) {
        this.a1 = startAngle;
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5412, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            a(canvas);
        }
    }

    public void a(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5414, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            Canvas canvas2 = canvas;
            int i = d;
            int i2 = f;
            RectF rectF = new RectF((float) this.p0, (float) (i - i2), (float) c, (float) (i2 + i));
            canvas2.drawArc(rectF, (float) this.a1, -30.0f, true, this.q);
            canvas2.drawArc(rectF, (float) this.a1, -30.0f, true, this.x);
        }
    }

    public a getData() {
        return this.a2;
    }

    public void setData(a data) {
        this.a2 = data;
    }
}
