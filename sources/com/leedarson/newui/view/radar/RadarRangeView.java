package com.leedarson.newui.view.radar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

public class RadarRangeView extends FrameLayout {
    public static int c = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d = 0;
    public static float f = 0.0f;
    private Paint q;

    public RadarRangeView(Context context) {
        super(context);
        a(context);
    }

    public RadarRangeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public RadarRangeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5448, new Class[]{Context.class}, Void.TYPE).isSupported) {
            Paint paint = new Paint();
            this.q = paint;
            paint.setColor(Color.parseColor("#80000000"));
            this.q.setAntiAlias(true);
            this.q.setStyle(Paint.Style.FILL);
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 5449, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
        }
    }
}
