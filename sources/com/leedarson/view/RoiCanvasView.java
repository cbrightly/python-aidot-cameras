package com.leedarson.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;
import com.leedarson.bean.RoiPoint;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.List;

public class RoiCanvasView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Paint c;
    private List<RoiPoint> d;

    public RoiCanvasView(Context context) {
        super(context);
        b();
    }

    public RoiCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        b();
    }

    public RoiCanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        b();
    }

    private void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11756, new Class[0], Void.TYPE).isSupported) {
            this.c = new Paint();
        }
    }

    public void dispatchDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11757, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.dispatchDraw(canvas);
            float mW = (float) getMeasuredWidth();
            float mH = (float) getMeasuredHeight();
            if (this.d != null && mW != 0.0f && mH != 0.0f) {
                for (int i = 0; i < this.d.size(); i++) {
                    int color = SupportMenu.CATEGORY_MASK;
                    switch (i) {
                        case 0:
                            color = Color.parseColor("#00DFFF");
                            break;
                        case 1:
                            color = Color.parseColor("#FF0000");
                            break;
                        case 2:
                            color = Color.parseColor("#008000");
                            break;
                        case 3:
                            color = Color.parseColor("#FFFF00");
                            break;
                        case 4:
                            color = Color.parseColor("#800080");
                            break;
                    }
                    a(canvas, this.d.get(i), color, mW, mH);
                }
            }
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 11758, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
        }
    }

    public void a(Canvas canvas, RoiPoint point, int color, float width, float height) {
        Object[] objArr = {canvas, point, new Integer(color), new Float(width), new Float(height)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        Class[] clsArr = {Canvas.class, RoiPoint.class, Integer.TYPE, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11759, clsArr, Void.TYPE).isSupported) {
            if (canvas != null && point.getPoint1() != null && point.getPoint2() != null && point.getPoint3() != null && point.getPoint4() != null) {
                this.c.reset();
                this.c.setColor(color);
                this.c.setAlpha(128);
                this.c.setAntiAlias(true);
                this.c.setStyle(Paint.Style.FILL_AND_STROKE);
                Path path1 = new Path();
                path1.moveTo(point.getPoint1().getX() * width, point.getPoint1().getY() * height);
                path1.lineTo(point.getPoint2().getX() * width, point.getPoint2().getY() * height);
                path1.lineTo(point.getPoint3().getX() * width, point.getPoint3().getY() * height);
                path1.lineTo(point.getPoint4().getX() * width, point.getPoint4().getY() * height);
                path1.close();
                canvas.drawCircle(point.getPoint1().getX() * width, point.getPoint1().getY() * height, 5.0f, this.c);
                canvas.drawCircle(point.getPoint2().getX() * width, point.getPoint2().getY() * height, 5.0f, this.c);
                canvas.drawCircle(point.getPoint3().getX() * width, point.getPoint3().getY() * height, 5.0f, this.c);
                canvas.drawCircle(point.getPoint4().getX() * width, point.getPoint4().getY() * height, 5.0f, this.c);
                canvas.drawPath(path1, this.c);
            }
        }
    }
}
