package com.leedarson.newui.view.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.leedarson.smartcamera.kvswebrtc.beans.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class RadarPointView extends View implements b {
    public static int c = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d = 0;
    private Paint f;
    private Paint q;
    private a x;

    public RadarPointView(Context context) {
        super(context);
        a(context);
    }

    public RadarPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public RadarPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5443, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Paint paint = new Paint();
            this.f = paint;
            paint.setColor(Color.parseColor("#FFFFFF"));
            this.f.setAntiAlias(true);
            this.f.setStyle(Paint.Style.STROKE);
            Paint paint2 = new Paint();
            this.q = paint2;
            paint2.setColor(Color.parseColor("#FEDDC3"));
            this.q.setAntiAlias(true);
            this.q.setStyle(Paint.Style.FILL);
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5444, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            canvas.translate((float) (c / 2), (float) d);
            if (getData() != null) {
                this.q.setColor(getData().p);
                this.f.setColor(this.x.r);
                Paint paint = this.f;
                int i = this.x.s;
                if (i == 0) {
                    i = c.n;
                }
                paint.setStrokeWidth((float) i);
                canvas.drawCircle(getData().l, getData().m, getData().n, this.f);
                canvas.drawCircle(getData().l, getData().m, getData().n, this.q);
            }
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5445, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setData(a data) {
        this.x = data;
    }

    public a getData() {
        return this.x;
    }

    public static void b(int width, int height) {
        c = width;
        d = height;
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5447, new Class[0], Void.TYPE).isSupported) {
            invalidate();
        }
    }
}
