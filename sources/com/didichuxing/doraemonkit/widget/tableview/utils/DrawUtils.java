package com.didichuxing.doraemonkit.widget.tableview.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.style.FontStyle;

public class DrawUtils {
    public static int getTextHeight(FontStyle style, Paint paint) {
        style.fillPaint(paint);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

    public static int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

    public static float getTextCenterY(int centerY, Paint paint) {
        return ((float) centerY) - ((paint.descent() + paint.ascent()) / 2.0f);
    }

    public static float getTextCenterX(int left, int right, Paint paint) {
        Paint.Align align = paint.getTextAlign();
        if (align == Paint.Align.RIGHT) {
            return (float) right;
        }
        if (align == Paint.Align.LEFT) {
            return (float) left;
        }
        return (float) ((right + left) / 2);
    }

    public static boolean isMixRect(Rect rect, int left, int top, int right, int bottom) {
        return rect.bottom >= top && rect.right >= left && rect.top < bottom && rect.left < right;
    }

    public static boolean isClick(int left, int top, int right, int bottom, PointF clickPoint) {
        float f = clickPoint.x;
        if (f >= ((float) left) && f <= ((float) right)) {
            float f2 = clickPoint.y;
            return f2 >= ((float) top) && f2 <= ((float) bottom);
        }
    }

    public static boolean isClick(Rect rect, PointF clickPoint) {
        return rect.contains((int) clickPoint.x, (int) clickPoint.y);
    }

    public static void fillBackground(Canvas canvas, int left, int top, int right, int bottom, int bgColor, Paint paint) {
        if (bgColor != 0) {
            paint.setColor(bgColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect((float) left, (float) top, (float) right, (float) bottom, paint);
        }
    }

    public static boolean isMixHorizontalRect(Rect rect, int left, int right) {
        return rect.right >= left && rect.left <= right;
    }

    public static boolean isVerticalMixRect(Rect rect, int top, int bottom) {
        return rect.bottom >= top && rect.top <= bottom;
    }

    public static int getMultiTextHeight(Paint paint, String[] values) {
        return getTextHeight(paint) * values.length;
    }

    public static int getMultiTextWidth(Paint paint, String[] values) {
        int maxWidth = 0;
        for (String val : values) {
            int width = (int) paint.measureText(val);
            if (maxWidth < width) {
                maxWidth = width;
            }
        }
        return maxWidth;
    }

    public static void drawMultiText(Canvas canvas, Paint paint, Rect rect, String[] values) {
        for (int i = 0; i < values.length; i++) {
            canvas.drawText(values[(values.length - i) - 1], getTextCenterX(rect.left, rect.right, paint), getTextCenterY((int) (((double) ((rect.bottom + rect.top) / 2)) + ((((double) ((((float) values.length) / 2.0f) - ((float) i))) - 0.5d) * ((double) getTextHeight(paint)))), paint), paint);
        }
    }

    public static void drawSingleText(Canvas canvas, Paint paint, Rect rect, String value) {
        canvas.drawText(value, getTextCenterX(rect.left, rect.right, paint), getTextCenterY(rect.centerY(), paint), paint);
    }
}
