package com.didichuxing.doraemonkit.widget.tableview.style;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import com.didichuxing.doraemonkit.widget.tableview.intface.IStyle;
import com.didichuxing.doraemonkit.widget.tableview.utils.DensityUtils;

public class FontStyle implements IStyle {
    private static Paint.Align defaultAlign = Paint.Align.CENTER;
    private static int defaultFontColor = Color.parseColor("#636363");
    private static int defaultFontSize = 12;
    private Paint.Align align;
    private int textColor;
    private int textSize;

    public static void setDefaultTextSize(int defaultTextSize) {
        defaultFontSize = defaultTextSize;
    }

    public static void setDefaultTextAlign(Paint.Align align2) {
        defaultAlign = align2;
    }

    public static void setDefaultTextSpSize(Context context, int defaultTextSpSize) {
        defaultFontSize = DensityUtils.sp2px(context, (float) defaultTextSpSize);
    }

    public static void setDefaultTextColor(int defaultTextColor) {
        defaultFontColor = defaultTextColor;
    }

    public FontStyle() {
    }

    public FontStyle(int textSize2, int textColor2) {
        this.textSize = textSize2;
        this.textColor = textColor2;
    }

    public FontStyle(Context context, int sp, int textColor2) {
        this.textSize = DensityUtils.sp2px(context, (float) sp);
        this.textColor = textColor2;
    }

    public Paint.Align getAlign() {
        Paint.Align align2 = this.align;
        if (align2 == null) {
            return defaultAlign;
        }
        return align2;
    }

    public FontStyle setAlign(Paint.Align align2) {
        this.align = align2;
        return this;
    }

    public int getTextSize() {
        int i = this.textSize;
        if (i == 0) {
            return defaultFontSize;
        }
        return i;
    }

    public FontStyle setTextSize(int textSize2) {
        this.textSize = textSize2;
        return this;
    }

    public void setTextSpSize(Context context, int sp) {
        setTextSize(DensityUtils.sp2px(context, (float) sp));
    }

    public int getTextColor() {
        int i = this.textColor;
        if (i == 0) {
            return defaultFontColor;
        }
        return i;
    }

    public FontStyle setTextColor(int textColor2) {
        this.textColor = textColor2;
        return this;
    }

    public void fillPaint(Paint paint) {
        paint.setColor(getTextColor());
        paint.setTextAlign(getAlign());
        paint.setTextSize((float) getTextSize());
        paint.setStyle(Paint.Style.FILL);
    }
}
