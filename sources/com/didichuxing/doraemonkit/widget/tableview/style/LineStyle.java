package com.didichuxing.doraemonkit.widget.tableview.style;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathEffect;
import com.didichuxing.doraemonkit.widget.tableview.intface.IStyle;
import com.didichuxing.doraemonkit.widget.tableview.utils.DensityUtils;

public class LineStyle implements IStyle {
    private static int defaultLineColor = Color.parseColor("#e6e6e6");
    private static float defaultLineSize = 2.0f;
    private int color = -1;
    private PathEffect effect = new PathEffect();
    private boolean isFill;
    private float width = -1.0f;

    public LineStyle() {
    }

    public LineStyle(float width2, int color2) {
        this.width = width2;
        this.color = color2;
    }

    public LineStyle(Context context, float dp, int color2) {
        this.width = (float) DensityUtils.dp2px(context, dp);
        this.color = color2;
    }

    public static void setDefaultLineSize(float width2) {
        defaultLineSize = width2;
    }

    public static void setDefaultLineSize(Context context, float dp) {
        defaultLineSize = (float) DensityUtils.dp2px(context, dp);
    }

    public static void setDefaultLineColor(int color2) {
        defaultLineColor = color2;
    }

    public float getWidth() {
        float f = this.width;
        if (f == -1.0f) {
            return defaultLineSize;
        }
        return f;
    }

    public LineStyle setWidth(float width2) {
        this.width = width2;
        return this;
    }

    public LineStyle setWidth(Context context, int dp) {
        this.width = (float) DensityUtils.dp2px(context, (float) dp);
        return this;
    }

    public int getColor() {
        int i = this.color;
        if (i == -1) {
            return defaultLineColor;
        }
        return i;
    }

    public boolean isFill() {
        return this.isFill;
    }

    public LineStyle setFill(boolean fill) {
        this.isFill = fill;
        return this;
    }

    public LineStyle setColor(int color2) {
        this.color = color2;
        return this;
    }

    public LineStyle setEffect(PathEffect effect2) {
        this.effect = effect2;
        return this;
    }

    public void fillPaint(Paint paint) {
        paint.setColor(getColor());
        paint.setStyle(this.isFill ? Paint.Style.FILL : Paint.Style.STROKE);
        paint.setStrokeWidth(getWidth());
        paint.setPathEffect(this.effect);
    }
}
