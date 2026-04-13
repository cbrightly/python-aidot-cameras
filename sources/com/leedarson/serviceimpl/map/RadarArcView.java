package com.leedarson.serviceimpl.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.j;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class RadarArcView extends View {
    public static int c = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d = 0;
    public int a1;
    Paint f;
    public int p0;
    public int p1;
    Paint q;
    Paint x;
    private final int y = j.a(BaseApplication.b().getApplicationContext(), 1.5f);
    public int z;

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
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8278, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Paint paint = new Paint();
            this.x = paint;
            paint.setColor(Color.parseColor("#66EDF2F7"));
            this.x.setStyle(Paint.Style.FILL);
            this.x.setAntiAlias(true);
            Paint paint2 = new Paint();
            this.f = paint2;
            paint2.setColor(Color.parseColor("#66FC8E35"));
            this.f.setStyle(Paint.Style.FILL);
            this.f.setAntiAlias(true);
            Paint paint3 = new Paint();
            this.q = paint3;
            paint3.setColor(Color.parseColor("#FC8E35"));
            this.q.setStyle(Paint.Style.STROKE);
            this.q.setStrokeWidth((float) this.y);
            this.q.setAntiAlias(true);
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 8279, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            a(canvas);
        }
    }

    public void a(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 8281, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            Canvas canvas2 = canvas;
            int i = this.p0;
            int i2 = this.y;
            RectF rectF = new RectF((float) this.z, (float) (i + i2), (float) this.a1, (float) (this.p1 - i2));
            c.a("radar left:" + this.z + ",top:" + this.p0 + ",right:" + this.a1 + ",bottom:" + this.p1);
            canvas2.drawArc(rectF, -30.0f, -120.0f, true, this.f);
            canvas2.drawArc(rectF, -30.0f, -120.0f, true, this.q);
        }
    }
}
