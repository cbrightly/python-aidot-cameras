package com.didichuxing.doraemonkit.widget.tableview.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.TableConfig;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableData;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableInfo;
import com.didichuxing.doraemonkit.widget.tableview.intface.IComponent;
import com.didichuxing.doraemonkit.widget.tableview.intface.ISequenceFormat;
import com.didichuxing.doraemonkit.widget.tableview.utils.DrawUtils;

public class YSequence<T> implements IComponent<TableData<T>> {
    private int clipWidth;
    private ISequenceFormat format;
    private Rect rect = new Rect();
    private Rect scaleRect;
    private Rect tempRect = new Rect();
    private int width;

    public void onMeasure(Rect scaleRect2, Rect showRect, TableConfig config) {
        this.scaleRect = scaleRect2;
        float f = (float) this.width;
        float f2 = 1.0f;
        if (config.getZoom() <= 1.0f) {
            f2 = config.getZoom();
        }
        int scaleWidth = (int) (f * f2);
        boolean fixed = config.isFixedYSequence();
        Rect rect2 = this.rect;
        rect2.top = scaleRect2.top;
        rect2.bottom = scaleRect2.bottom;
        int i = fixed ? showRect.left : scaleRect2.left;
        rect2.left = i;
        rect2.right = i + scaleWidth;
        if (fixed) {
            scaleRect2.left += scaleWidth;
            showRect.left += scaleWidth;
            this.clipWidth = scaleWidth;
            return;
        }
        int max = Math.max(0, scaleWidth - (showRect.left - scaleRect2.left));
        this.clipWidth = max;
        showRect.left += max;
        scaleRect2.left += scaleWidth;
    }

    public void onDraw(Canvas canvas, Rect showRect, TableData<T> tableData, TableConfig config) {
        float top;
        int totalSize;
        float top2;
        TableInfo info;
        int totalSize2;
        int showTop;
        int disY;
        Canvas canvas2 = canvas;
        Rect rect2 = showRect;
        TableConfig tableConfig = config;
        this.format = tableData.getYSequenceFormat();
        float f = 1.0f;
        if (config.getZoom() <= 1.0f) {
            f = config.getZoom();
        }
        float hZoom = f;
        int totalSize3 = tableData.getLineSize();
        TableInfo info2 = tableData.getTableInfo();
        int topHeight = info2.getTopHeight(hZoom);
        float top3 = (float) (this.rect.top + topHeight);
        int showLeft = rect2.left - this.clipWidth;
        boolean isFixTop = config.isFixedXSequence();
        int showTop2 = rect2.top;
        if (isFixTop) {
            showTop2 += topHeight;
        }
        float tempTop = top3;
        boolean isFixedTitle = config.isFixedTitle();
        boolean isFixedCount = config.isFixedCountRow();
        if (isFixedTitle) {
            if (isFixTop) {
                disY = info2.getTopHeight(hZoom);
                float f2 = hZoom;
                top = top3;
            } else {
                float f3 = hZoom;
                top = top3;
                disY = Math.max(0, topHeight - (rect2.top - this.scaleRect.top));
            }
            tempTop = (float) (rect2.top + disY);
        } else {
            top = top3;
        }
        int i = topHeight;
        this.tempRect.set(showLeft, ((int) tempTop) - topHeight, rect2.left, (int) tempTop);
        drawLeftAndTop(canvas2, rect2, this.tempRect, tableConfig);
        canvas.save();
        canvas2.clipRect(showLeft, showTop2, rect2.left, rect2.bottom);
        if (config.isShowColumnTitle()) {
            int i2 = 0;
            top2 = top;
            while (i2 < info2.getMaxLevel()) {
                float bottom = ((float) info2.getTitleHeight()) + tempTop;
                boolean isFixTop2 = isFixTop;
                if (DrawUtils.isVerticalMixRect(rect2, (int) top2, (int) bottom)) {
                    Rect rect3 = this.tempRect;
                    Rect rect4 = this.rect;
                    showTop = showTop2;
                    totalSize2 = totalSize3;
                    float f4 = tempTop;
                    rect3.set(rect4.left, (int) tempTop, rect4.right, (int) bottom);
                    drawRect(canvas2, this.tempRect, tableConfig);
                } else {
                    totalSize2 = totalSize3;
                    showTop = showTop2;
                    float f5 = tempTop;
                }
                tempTop = bottom;
                top2 += (float) info2.getTitleHeight();
                i2++;
                isFixTop = isFixTop2;
                showTop2 = showTop;
                totalSize3 = totalSize2;
            }
            totalSize = totalSize3;
            boolean z = isFixTop;
            int i3 = showTop2;
            float f6 = tempTop;
        } else {
            totalSize = totalSize3;
            boolean z2 = isFixTop;
            int i4 = showTop2;
            top2 = top;
        }
        int tempBottom = rect2.bottom;
        if (isFixedTitle || isFixedCount) {
            canvas.save();
            canvas2.clipRect((float) showLeft, tempTop, (float) rect2.left, (float) tempBottom);
        }
        int num = 0;
        int i5 = 0;
        while (true) {
            int totalSize4 = totalSize;
            if (i5 >= totalSize4) {
                TableInfo tableInfo = info2;
                float f7 = top2;
                break;
            }
            num++;
            float bottom2 = (((float) info2.getLineHeightArray()[i5]) * config.getZoom()) + top2;
            int tempBottom2 = tempBottom;
            if (rect2.bottom < this.rect.top) {
                float f8 = top2;
                break;
            }
            if (DrawUtils.isVerticalMixRect(rect2, (int) top2, (int) bottom2)) {
                Rect rect5 = this.tempRect;
                Rect rect6 = this.rect;
                info = info2;
                float f9 = top2;
                rect5.set(rect6.left, (int) top2, rect6.right, (int) bottom2);
                draw(canvas2, this.tempRect, num, tableConfig);
            } else {
                info = info2;
                float f10 = top2;
            }
            top2 = bottom2;
            i5++;
            rect2 = showRect;
            tempBottom = tempBottom2;
            info2 = info;
            totalSize = totalSize4;
        }
        if (isFixedTitle || isFixedCount) {
            canvas.restore();
        }
        canvas.restore();
    }

    private void drawLeftAndTop(Canvas canvas, Rect showRect, Rect rect2, TableConfig config) {
        canvas.save();
        canvas.clipRect(Math.max(this.rect.left, rect2.left), showRect.top, showRect.left, rect2.bottom);
        Paint paint = config.getPaint();
        if (config.getLeftAndTopBackgroundColor() != 0) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(config.getLeftAndTopBackgroundColor());
            canvas.drawRect(rect2, paint);
        }
        config.SequenceGridStyle.fillPaint(paint);
        canvas.drawRect(rect2, paint);
        canvas.restore();
    }

    private void draw(Canvas canvas, Rect rect2, int position, TableConfig config) {
        drawRect(canvas, rect2, config);
        this.format.draw(canvas, position - 1, rect2, config);
    }

    private void drawRect(Canvas canvas, Rect rect2, TableConfig config) {
        Paint paint = config.getPaint();
        config.SequenceGridStyle.fillPaint(paint);
        canvas.drawRect(rect2, paint);
        config.YSequenceStyle.fillPaint(paint);
        if (0 != 0) {
            paint.setColor(0);
        }
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width2) {
        this.width = width2;
    }

    public Rect getRect() {
        return this.rect;
    }
}
