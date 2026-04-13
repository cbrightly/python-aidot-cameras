package com.didichuxing.doraemonkit.widget.tableview.format;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.TableConfig;
import com.didichuxing.doraemonkit.widget.tableview.intface.ISequenceFormat;
import com.didichuxing.doraemonkit.widget.tableview.utils.DrawUtils;

public abstract class BaseSequenceFormat implements ISequenceFormat {
    public void draw(Canvas canvas, int sequence, Rect rect, TableConfig config) {
        Paint paint = config.getPaint();
        float textSize = paint.getTextSize();
        float f = 1.0f;
        if (config.getZoom() <= 1.0f) {
            f = config.getZoom();
        }
        paint.setTextSize(textSize * f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(format(Integer.valueOf(sequence + 1)), (float) rect.centerX(), DrawUtils.getTextCenterY(rect.centerY(), paint), paint);
    }
}
