package com.leedarson.base.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class RoundDotView extends View {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int c = -16776961;
    int d;
    int f;
    private Paint q;

    public RoundDotView(Context context) {
        super(context);
    }

    public RoundDotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Paint paint = new Paint();
        this.q = paint;
        paint.setAntiAlias(true);
    }

    public void setColor(int color) {
        Object[] objArr = {new Integer(color)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 799, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.c = color;
            this.q.setColor(color);
            invalidate();
        }
    }

    public void onDraw(Canvas canvas) {
        if (!PatchProxy.proxy(new Object[]{canvas}, this, changeQuickRedirect, false, 800, new Class[]{Canvas.class}, Void.TYPE).isSupported) {
            super.onDraw(canvas);
            int i = this.d;
            canvas.drawCircle((float) (i / 2), (float) (this.f / 2), (float) (i / 2), this.q);
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Object[] objArr = {new Integer(widthMeasureSpec), new Integer(heightMeasureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 801, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            this.d = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            int defaultSize = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            this.f = defaultSize;
            setMeasuredDimension(this.d, defaultSize);
        }
    }

    public static int getDefaultSize(int size, int measureSpec) {
        Object[] objArr = {new Integer(size), new Integer(measureSpec)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 802, new Class[]{cls, cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int result = size;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case Integer.MIN_VALUE:
            case 1073741824:
                return specSize;
            case 0:
                return size;
            default:
                return result;
        }
    }
}
