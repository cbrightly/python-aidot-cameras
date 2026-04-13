package com.didichuxing.doraemonkit.widget.tableview.format;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.TableConfig;
import com.didichuxing.doraemonkit.widget.tableview.bean.CellInfo;
import com.didichuxing.doraemonkit.widget.tableview.bean.Column;
import com.didichuxing.doraemonkit.widget.tableview.intface.IDrawFormat;
import com.didichuxing.doraemonkit.widget.tableview.utils.DrawUtils;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class TextDrawFormat<T> implements IDrawFormat<T> {
    private Map<String, SoftReference<String[]>> valueMap = new HashMap();

    public int measureWidth(Column<T> column, int position) {
        TableConfig config = TableConfig.getInstance();
        Paint paint = config.getPaint();
        config.contentStyle.fillPaint(paint);
        return DrawUtils.getMultiTextWidth(paint, getSplitString(column.format(position)));
    }

    public int measureHeight(Column<T> column, int position) {
        TableConfig config = TableConfig.getInstance();
        Paint paint = config.getPaint();
        config.contentStyle.fillPaint(paint);
        return DrawUtils.getMultiTextHeight(paint, getSplitString(column.format(position)));
    }

    public void draw(Canvas c, Rect rect, CellInfo<T> cellInfo) {
        TableConfig config = TableConfig.getInstance();
        Paint paint = config.getPaint();
        setTextPaint(config, cellInfo, paint);
        if (cellInfo.column.getTextAlign() != null) {
            paint.setTextAlign(cellInfo.column.getTextAlign());
        }
        drawText(c, cellInfo.value, rect, paint);
    }

    /* access modifiers changed from: protected */
    public void drawText(Canvas c, String value, Rect rect, Paint paint) {
        DrawUtils.drawMultiText(c, paint, rect, getSplitString(value));
    }

    public void setTextPaint(TableConfig config, CellInfo<T> cellInfo, Paint paint) {
        config.contentStyle.fillPaint(paint);
        paint.setTextSize(paint.getTextSize() * config.getZoom());
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.lang.String[]} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String[] getSplitString(java.lang.String r4) {
        /*
            r3 = this;
            r0 = 0
            java.util.Map<java.lang.String, java.lang.ref.SoftReference<java.lang.String[]>> r1 = r3.valueMap
            java.lang.Object r1 = r1.get(r4)
            if (r1 == 0) goto L_0x0018
            java.util.Map<java.lang.String, java.lang.ref.SoftReference<java.lang.String[]>> r1 = r3.valueMap
            java.lang.Object r1 = r1.get(r4)
            java.lang.ref.SoftReference r1 = (java.lang.ref.SoftReference) r1
            java.lang.Object r1 = r1.get()
            r0 = r1
            java.lang.String[] r0 = (java.lang.String[]) r0
        L_0x0018:
            if (r0 != 0) goto L_0x002a
            java.lang.String r1 = "\n"
            java.lang.String[] r0 = r4.split(r1)
            java.util.Map<java.lang.String, java.lang.ref.SoftReference<java.lang.String[]>> r1 = r3.valueMap
            java.lang.ref.SoftReference r2 = new java.lang.ref.SoftReference
            r2.<init>(r0)
            r1.put(r4, r2)
        L_0x002a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.widget.tableview.format.TextDrawFormat.getSplitString(java.lang.String):java.lang.String[]");
    }
}
