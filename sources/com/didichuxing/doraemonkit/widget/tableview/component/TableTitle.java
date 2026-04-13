package com.didichuxing.doraemonkit.widget.tableview.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.TableConfig;
import com.didichuxing.doraemonkit.widget.tableview.intface.ITableTitle;
import com.didichuxing.doraemonkit.widget.tableview.utils.DrawUtils;

public class TableTitle implements ITableTitle {
    protected int direction;
    private Rect rect = new Rect();
    private int size = 100;

    public void onDraw(Canvas canvas, Rect showRect, String tableName, TableConfig config) {
        Paint paint = config.getPaint();
        config.tableTitleStyle.fillPaint(paint);
        Rect rect2 = getRect();
        int startX = rect2.centerX();
        Path path = new Path();
        switch (this.direction) {
            case 0:
            case 2:
                int textWidth = (int) paint.measureText(tableName);
                path.moveTo((float) startX, (float) rect2.top);
                path.lineTo((float) startX, (float) rect2.bottom);
                canvas.drawTextOnPath(tableName, path, (float) (textWidth / 2), 0.0f, paint);
                return;
            case 1:
            case 3:
                DrawUtils.drawMultiText(canvas, paint, rect2, tableName.split("\n"));
                return;
            default:
                return;
        }
    }

    public void onMeasure(Rect scaleRect, Rect showRect, TableConfig config) {
        Rect rect2 = this.rect;
        rect2.left = showRect.left;
        rect2.right = showRect.right;
        rect2.top = showRect.top;
        rect2.bottom = Math.min(showRect.bottom, scaleRect.bottom);
        int h = this.size;
        int w = this.size;
        switch (this.direction) {
            case 0:
                Rect rect3 = this.rect;
                rect3.right = rect3.left + w;
                scaleRect.left += w;
                showRect.left += w;
                return;
            case 1:
                Rect rect4 = this.rect;
                rect4.bottom = rect4.top + h;
                scaleRect.top += h;
                showRect.top += h;
                return;
            case 2:
                Rect rect5 = this.rect;
                rect5.left = rect5.right - w;
                scaleRect.right -= w;
                showRect.right -= w;
                return;
            case 3:
                Rect rect6 = this.rect;
                rect6.top = rect6.bottom - h;
                scaleRect.bottom -= h;
                showRect.bottom -= h;
                return;
            default:
                return;
        }
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size2) {
        this.size = size2;
    }

    public Rect getRect() {
        return this.rect;
    }

    public void setRect(Rect rect2) {
        this.rect = rect2;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction2) {
        this.direction = direction2;
    }
}
