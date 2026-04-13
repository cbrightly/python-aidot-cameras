package com.didichuxing.doraemonkit.kit.layoutborder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;
import com.didichuxing.doraemonkit.config.LayoutBorderConfig;

public class ViewBorderDrawable extends Drawable {
    private final Context context;
    private final Paint paint;
    private final Rect rect;

    public ViewBorderDrawable(View view) {
        Paint paint2 = new Paint(1);
        this.paint = paint2;
        this.rect = new Rect(0, 0, view.getWidth(), view.getHeight());
        this.context = view.getContext();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(SupportMenu.CATEGORY_MASK);
        paint2.setStrokeWidth(4.0f);
        paint2.setPathEffect(new DashPathEffect(new float[]{4.0f, 4.0f}, 0.0f));
    }

    public void draw(@NonNull Canvas canvas) {
        if (LayoutBorderConfig.isLayoutBorderOpen()) {
            canvas.drawRect(this.rect, this.paint);
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public int getOpacity() {
        return -3;
    }
}
