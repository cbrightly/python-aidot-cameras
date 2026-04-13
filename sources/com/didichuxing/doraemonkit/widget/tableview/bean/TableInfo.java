package com.didichuxing.doraemonkit.widget.tableview.bean;

import android.graphics.Rect;
import java.lang.reflect.Array;

public class TableInfo {
    private int columnSize;
    private int countHeight;
    private int[] lineHeightArray;
    private int lineSize;
    private int maxLevel = 1;
    private Cell[][] rangeCells;
    private Rect tableRect;
    private int tableTitleSize;
    private int titleDirection;
    private int titleHeight;
    private int topHeight;
    private int yAxisWidth;
    private float zoom = 1.0f;

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel2) {
        this.maxLevel = maxLevel2;
    }

    public int getColumnSize() {
        return this.columnSize;
    }

    public void setColumnSize(int columnSize2) {
        this.columnSize = columnSize2;
        int i = this.lineSize;
        int[] iArr = new int[2];
        iArr[1] = columnSize2;
        iArr[0] = i;
        this.rangeCells = (Cell[][]) Array.newInstance(Cell.class, iArr);
    }

    public int getTopHeight() {
        return this.topHeight;
    }

    public int getTopHeight(float zoom2) {
        return (int) (((float) this.topHeight) * zoom2);
    }

    public void setTopHeight(int topHeight2) {
        this.topHeight = topHeight2;
    }

    public int getTitleHeight() {
        return (int) (((float) this.titleHeight) * this.zoom);
    }

    public void setTitleHeight(int titleHeight2) {
        this.titleHeight = titleHeight2;
    }

    public Rect getTableRect() {
        return this.tableRect;
    }

    public void setTableRect(Rect tableRect2) {
        this.tableRect = tableRect2;
    }

    public int getyAxisWidth() {
        return this.yAxisWidth;
    }

    public void setLineSize(int lineSize2) {
        this.lineSize = lineSize2;
        this.lineHeightArray = new int[lineSize2];
    }

    public void addLine(int count, boolean isFoot) {
        this.lineSize += count;
        int[] iArr = this.lineHeightArray;
        int size = iArr.length;
        int[] tempArray = new int[(size + count)];
        if (isFoot) {
            System.arraycopy(iArr, 0, tempArray, 0, size);
        } else {
            System.arraycopy(iArr, 0, tempArray, count, size);
        }
        this.lineHeightArray = tempArray;
        if (size == this.rangeCells.length) {
            int[] iArr2 = new int[2];
            iArr2[1] = this.columnSize;
            iArr2[0] = size + count;
            Cell[][] tempRangeCells = (Cell[][]) Array.newInstance(Cell.class, iArr2);
            for (int i = 0; i < size; i++) {
                tempRangeCells[(isFoot ? 0 : count) + i] = this.rangeCells[i];
            }
            this.rangeCells = tempRangeCells;
        }
    }

    public int getCountHeight() {
        return (int) (this.zoom * ((float) this.countHeight));
    }

    public void setCountHeight(int countHeight2) {
        this.countHeight = countHeight2;
    }

    public int[] getLineHeightArray() {
        return this.lineHeightArray;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setZoom(float zoom2) {
        this.zoom = zoom2;
    }

    public void setyAxisWidth(int yAxisWidth2) {
        this.yAxisWidth = yAxisWidth2;
    }

    public int getTableTitleSize() {
        return this.tableTitleSize;
    }

    public void setTableTitleSize(int tableTitleSize2) {
        this.tableTitleSize = tableTitleSize2;
    }

    public int getTitleDirection() {
        return this.titleDirection;
    }

    public void setTitleDirection(int titleDirection2) {
        this.titleDirection = titleDirection2;
    }

    public Cell[][] getRangeCells() {
        return this.rangeCells;
    }

    public void clear() {
        this.rangeCells = null;
        this.lineHeightArray = null;
        this.tableRect = null;
    }
}
