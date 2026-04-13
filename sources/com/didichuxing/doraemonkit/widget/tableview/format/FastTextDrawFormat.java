package com.didichuxing.doraemonkit.widget.tableview.format;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.TableConfig;
import com.didichuxing.doraemonkit.widget.tableview.bean.Column;
import com.didichuxing.doraemonkit.widget.tableview.utils.DrawUtils;
import java.util.HashMap;

public class FastTextDrawFormat<T> extends TextDrawFormat<T> {
    private String HEIGHT_KEY = "dk_height";
    private HashMap<String, Integer> cacheMap = new HashMap<>();

    public int measureWidth(Column<T> column, int position) {
        String value = column.format(position);
        Integer maxLengthValue = this.cacheMap.get(column.getColumnName());
        if (maxLengthValue == null) {
            return comperLength(column, value);
        }
        if (value.length() > maxLengthValue.intValue()) {
            return comperLength(column, value);
        }
        return 0;
    }

    private int comperLength(Column<T> column, String value) {
        TableConfig config = TableConfig.getInstance();
        Paint paint = config.getPaint();
        config.contentStyle.fillPaint(paint);
        this.cacheMap.put(column.getColumnName(), Integer.valueOf(value.length()));
        return (int) paint.measureText(value);
    }

    public int measureHeight(Column<T> column, int position) {
        if (this.cacheMap.get(this.HEIGHT_KEY) == null) {
            TableConfig config = TableConfig.getInstance();
            Paint paint = config.getPaint();
            config.contentStyle.fillPaint(paint);
            this.cacheMap.put(this.HEIGHT_KEY, Integer.valueOf(DrawUtils.getTextHeight(paint)));
        }
        return this.cacheMap.get(this.HEIGHT_KEY).intValue();
    }

    /* access modifiers changed from: protected */
    public void drawText(Canvas c, String value, Rect rect, Paint paint) {
        DrawUtils.drawSingleText(c, paint, rect, value);
    }
}
