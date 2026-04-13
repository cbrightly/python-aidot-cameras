package com.scwang.smart.drawable;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/* compiled from: PaintDrawable */
public abstract class a extends Drawable {
    protected Paint c;

    protected a() {
        Paint paint = new Paint();
        this.c = paint;
        paint.setStyle(Paint.Style.FILL);
        this.c.setAntiAlias(true);
        this.c.setColor(-5592406);
    }

    public void a(int color) {
        this.c.setColor(color);
    }

    public void setAlpha(int alpha) {
        this.c.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.c.setColorFilter(cf);
    }

    public int getOpacity() {
        return -3;
    }
}
