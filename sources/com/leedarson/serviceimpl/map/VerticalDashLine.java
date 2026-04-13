package com.leedarson.serviceimpl.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.j;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class VerticalDashLine extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c = j.a(BaseApplication.b(), 4.0f);
    private int d = j.a(BaseApplication.b(), 4.0f);
    private Paint f;
    private Paint q;

    public VerticalDashLine(Context context) {
        super(context);
        a(context);
    }

    public VerticalDashLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public VerticalDashLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 8414, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Paint paint = new Paint();
            this.f = paint;
            paint.setColor(Color.parseColor("#ffffff"));
            this.f.setAntiAlias(true);
            this.f.setStyle(Paint.Style.FILL);
            this.f.setStrokeWidth((float) this.c);
            Paint paint2 = new Paint();
            this.q = paint2;
            paint2.setColor(0);
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 8415, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            int numlines = getHeight() / (this.c + this.d);
            for (int i = 0; i < numlines; i++) {
                int i2 = this.c;
                int i3 = this.d;
                int stopY = ((i3 + i2) * i) + i2;
                canvas.drawLine(0.0f, (float) (i * (i2 + i3)), 0.0f, (float) stopY, this.f);
                canvas.drawLine(0.0f, (float) stopY, 0.0f, (float) (this.d + stopY), this.q);
            }
        }
    }
}
