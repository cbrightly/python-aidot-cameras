package com.didichuxing.doraemonkit.widget.tableview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.bean.Cell;
import com.didichuxing.doraemonkit.widget.tableview.bean.CellInfo;
import com.didichuxing.doraemonkit.widget.tableview.bean.Column;
import com.didichuxing.doraemonkit.widget.tableview.bean.ColumnInfo;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableData;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableInfo;
import com.didichuxing.doraemonkit.widget.tableview.intface.ISelectFormat;
import com.didichuxing.doraemonkit.widget.tableview.listener.OnColumnClickListener;
import com.didichuxing.doraemonkit.widget.tableview.listener.TableClickObserver;
import com.didichuxing.doraemonkit.widget.tableview.utils.DrawUtils;
import java.util.List;

public class TableProvider<T> implements TableClickObserver {
    private CellInfo cellInfo = new CellInfo();
    private ColumnInfo clickColumnInfo;
    private PointF clickPoint = new PointF(-1.0f, -1.0f);
    private Rect clipRect = new Rect();
    private TableConfig config = TableConfig.getInstance();
    private boolean isClickPoint;
    private OnColumnClickListener onColumnClickListener;
    private SelectionOperation operation = new SelectionOperation();
    private Rect scaleRect;
    private Rect showRect;
    private TableData<T> tableData;
    private Rect tempRect = new Rect();
    private PointF tipPoint = new PointF();

    public void onDraw(Canvas canvas, Rect scaleRect2, Rect showRect2, TableData<T> tableData2) {
        ColumnInfo columnInfo;
        setData(scaleRect2, showRect2, tableData2);
        canvas.save();
        canvas.clipRect(this.showRect);
        drawColumnTitle(canvas);
        drawCount(canvas);
        drawContent(canvas);
        this.operation.draw(canvas, showRect2, this.config);
        canvas.restore();
        if (this.isClickPoint && (columnInfo = this.clickColumnInfo) != null) {
            this.onColumnClickListener.onClick(columnInfo);
        }
    }

    private void setData(Rect scaleRect2, Rect showRect2, TableData<T> tableData2) {
        this.isClickPoint = false;
        this.clickColumnInfo = null;
        this.operation.reset();
        this.scaleRect = scaleRect2;
        this.showRect = showRect2;
        this.tableData = tableData2;
    }

    private void drawColumnTitle(Canvas canvas) {
        if (!this.config.isShowColumnTitle()) {
            return;
        }
        if (this.config.isFixedTitle()) {
            drawTitle(canvas);
            canvas.restore();
            canvas.save();
            canvas.clipRect(this.showRect);
            return;
        }
        drawTitle(canvas);
    }

    private void drawCount(Canvas canvas) {
        int countHeight;
        List<ColumnInfo> childColumnInfos;
        float left;
        float left2;
        TableProvider tableProvider = this;
        float left3 = (float) tableProvider.scaleRect.left;
        float bottom = (float) (tableProvider.config.isFixedCountRow() ? Math.min(tableProvider.scaleRect.bottom, tableProvider.showRect.bottom) : tableProvider.scaleRect.bottom);
        int countHeight2 = tableProvider.tableData.getTableInfo().getCountHeight();
        float top = bottom - ((float) countHeight2);
        List<ColumnInfo> childColumnInfos2 = tableProvider.tableData.getChildColumnInfos();
        if (DrawUtils.isVerticalMixRect(tableProvider.showRect, (int) top, (int) bottom)) {
            List<Column> columns = tableProvider.tableData.getChildColumns();
            int columnSize = columns.size();
            boolean isPerColumnFixed = false;
            tableProvider.clipRect.set(tableProvider.showRect);
            int clipCount = 0;
            int i = 0;
            while (i < columnSize) {
                float tempLeft = left3;
                float width = ((float) columns.get(i).getComputeWidth()) * tableProvider.config.getZoom();
                if (childColumnInfos2.get(i).column.isFixed()) {
                    Rect rect = tableProvider.clipRect;
                    int i2 = rect.left;
                    childColumnInfos = childColumnInfos2;
                    if (left3 < ((float) i2)) {
                        left = (float) i2;
                        rect.left = (int) (((float) i2) + width);
                        countHeight = countHeight2;
                        isPerColumnFixed = true;
                        Canvas canvas2 = canvas;
                        float f = left;
                        tableProvider.tempRect.set((int) left, (int) top, (int) (left + width), (int) bottom);
                        left3 = tempLeft + width;
                        i++;
                        tableProvider = this;
                        childColumnInfos2 = childColumnInfos;
                        countHeight2 = countHeight;
                    } else {
                        left2 = left3;
                        countHeight = countHeight2;
                        Canvas canvas3 = canvas;
                    }
                } else {
                    childColumnInfos = childColumnInfos2;
                    if (isPerColumnFixed) {
                        canvas.save();
                        clipCount++;
                        int i3 = tableProvider.clipRect.left;
                        Rect rect2 = tableProvider.showRect;
                        int i4 = rect2.bottom;
                        countHeight = countHeight2;
                        canvas.clipRect(i3, i4 - countHeight2, rect2.right, i4);
                        left = left3;
                        float f2 = left;
                        tableProvider.tempRect.set((int) left, (int) top, (int) (left + width), (int) bottom);
                        left3 = tempLeft + width;
                        i++;
                        tableProvider = this;
                        childColumnInfos2 = childColumnInfos;
                        countHeight2 = countHeight;
                    } else {
                        left2 = left3;
                        countHeight = countHeight2;
                        Canvas canvas4 = canvas;
                    }
                }
                left = left2;
                float f22 = left;
                tableProvider.tempRect.set((int) left, (int) top, (int) (left + width), (int) bottom);
                left3 = tempLeft + width;
                i++;
                tableProvider = this;
                childColumnInfos2 = childColumnInfos;
                countHeight2 = countHeight;
            }
            float left4 = left3;
            int i5 = countHeight2;
            List<ColumnInfo> list = childColumnInfos2;
            Canvas canvas5 = canvas;
            for (int i6 = 0; i6 < clipCount; i6++) {
                canvas.restore();
            }
            float f3 = left4;
            return;
        }
        List<ColumnInfo> list2 = childColumnInfos2;
        Canvas canvas6 = canvas;
    }

    private void drawTitle(Canvas canvas) {
        TableInfo tableInfo;
        int dis;
        Canvas canvas2 = canvas;
        int dis2 = this.showRect.top - this.scaleRect.top;
        TableInfo tableInfo2 = this.tableData.getTableInfo();
        int titleHeight = tableInfo2.getTitleHeight() * tableInfo2.getMaxLevel();
        int clipHeight = this.config.isFixedTitle() ? titleHeight : Math.max(0, titleHeight - dis2);
        this.clipRect.set(this.showRect);
        List<ColumnInfo> columnInfoList = this.tableData.getColumnInfos();
        float zoom = this.config.getZoom();
        boolean isPerColumnFixed = false;
        int clipCount = 0;
        ColumnInfo parentColumnInfo = null;
        for (ColumnInfo info : columnInfoList) {
            int left = (int) ((((float) info.left) * zoom) + ((float) this.scaleRect.left));
            if (info.top != 0 || !info.column.isFixed()) {
                dis = dis2;
                if (isPerColumnFixed && info.top != 0) {
                    left = (info.left - parentColumnInfo.left) + ((int) (((float) this.clipRect.left) - (((float) info.width) * zoom)));
                    tableInfo = tableInfo2;
                } else if (isPerColumnFixed) {
                    canvas.save();
                    int i = this.clipRect.left;
                    Rect rect = this.showRect;
                    int i2 = rect.top;
                    tableInfo = tableInfo2;
                    canvas2.clipRect(i, i2, rect.right, i2 + clipHeight);
                    clipCount++;
                    isPerColumnFixed = false;
                } else {
                    tableInfo = tableInfo2;
                }
            } else {
                Rect rect2 = this.clipRect;
                if (left < rect2.left) {
                    parentColumnInfo = info;
                    fillColumnTitle(canvas2, info, rect2.left);
                    Rect rect3 = this.clipRect;
                    rect3.left = (int) (((float) rect3.left) + (((float) info.width) * zoom));
                    isPerColumnFixed = true;
                    dis2 = dis2;
                } else {
                    dis = dis2;
                    tableInfo = tableInfo2;
                }
            }
            fillColumnTitle(canvas2, info, left);
            dis2 = dis;
            tableInfo2 = tableInfo;
        }
        TableInfo tableInfo3 = tableInfo2;
        for (int i3 = 0; i3 < clipCount; i3++) {
            canvas.restore();
        }
        if (this.config.isFixedTitle()) {
            this.scaleRect.top += titleHeight;
            this.showRect.top += titleHeight;
            return;
        }
        this.showRect.top += clipHeight;
        this.scaleRect.top += titleHeight;
    }

    private void fillColumnTitle(Canvas canvas, ColumnInfo info, int left) {
        int top = ((int) (((float) info.top) * this.config.getZoom())) + (this.config.isFixedTitle() ? this.showRect : this.scaleRect).top;
        int right = (int) (((float) left) + (((float) info.width) * this.config.getZoom()));
        int bottom = (int) (((float) top) + (((float) info.height) * this.config.getZoom()));
        if (DrawUtils.isMixRect(this.showRect, left, top, right, bottom)) {
            if (!this.isClickPoint && this.onColumnClickListener != null && DrawUtils.isClick(left, top, right, bottom, this.clickPoint)) {
                this.isClickPoint = true;
                this.clickColumnInfo = info;
                this.clickPoint.set(-1.0f, -1.0f);
            }
            Paint paint = this.config.getPaint();
            this.tempRect.set(left, top, right, bottom);
            this.config.columnTitleGridStyle.fillPaint(paint);
            canvas.drawRect(this.tempRect, paint);
            this.tableData.getTitleDrawFormat().draw(canvas, info.column, this.tempRect, this.config);
        }
    }

    private void drawContent(Canvas canvas) {
        TableInfo info;
        int columnSize;
        List<ColumnInfo> childColumnInfo;
        boolean isPerFixed;
        int clipCount;
        Column column;
        Column topColumn;
        float right;
        Canvas canvas2 = canvas;
        float left = (float) this.scaleRect.left;
        List<Column> columns = this.tableData.getChildColumns();
        this.clipRect.set(this.showRect);
        TableInfo info2 = this.tableData.getTableInfo();
        int columnSize2 = columns.size();
        if (this.config.isFixedCountRow()) {
            canvas.save();
            Rect rect = this.showRect;
            canvas2.clipRect(rect.left, rect.top, rect.right, rect.bottom - info2.getCountHeight());
        }
        List<ColumnInfo> childColumnInfo2 = this.tableData.getChildColumnInfos();
        boolean isPerFixed2 = false;
        int clipCount2 = 0;
        int i = 0;
        while (true) {
            if (i >= columnSize2) {
                TableInfo tableInfo = info2;
                int i2 = columnSize2;
                List<ColumnInfo> list = childColumnInfo2;
                break;
            }
            float top = (float) this.scaleRect.top;
            Column column2 = columns.get(i);
            float width = ((float) column2.getComputeWidth()) * this.config.getZoom();
            float tempLeft = left;
            Column topColumn2 = childColumnInfo2.get(i).column;
            if (topColumn2.isFixed()) {
                isPerFixed2 = false;
                Rect rect2 = this.clipRect;
                int i3 = rect2.left;
                if (tempLeft < ((float) i3)) {
                    left = (float) i3;
                    rect2.left = (int) (((float) i3) + width);
                    isPerFixed2 = true;
                }
            } else if (isPerFixed2) {
                canvas.save();
                canvas2.clipRect(this.clipRect);
                isPerFixed2 = false;
                clipCount2++;
            }
            float right2 = left + width;
            if (left >= ((float) this.showRect.right)) {
                TableInfo tableInfo2 = info2;
                int i4 = columnSize2;
                List<ColumnInfo> list2 = childColumnInfo2;
                boolean z = isPerFixed2;
                int i5 = clipCount2;
                float f = right2;
                Column column3 = topColumn2;
                Column column4 = column2;
                break;
            }
            int size = column2.getDatas().size();
            List<Column> columns2 = columns;
            int j = 0;
            float top2 = top;
            int realPosition = 0;
            while (true) {
                if (j >= size) {
                    info = info2;
                    columnSize = columnSize2;
                    childColumnInfo = childColumnInfo2;
                    isPerFixed = isPerFixed2;
                    clipCount = clipCount2;
                    float f2 = top2;
                    int i6 = size;
                    float f3 = right2;
                    Column column5 = topColumn2;
                    Column column6 = column2;
                    break;
                }
                columnSize = columnSize2;
                String value = column2.format(j);
                childColumnInfo = childColumnInfo2;
                int totalLineHeight = 0;
                isPerFixed = isPerFixed2;
                int k = realPosition;
                while (true) {
                    clipCount = clipCount2;
                    if (k >= realPosition + 1) {
                        break;
                    }
                    totalLineHeight += info2.getLineHeightArray()[k];
                    k++;
                    clipCount2 = clipCount;
                }
                float bottom = (((float) totalLineHeight) * this.config.getZoom()) + top2;
                info = info2;
                int i7 = totalLineHeight;
                int realPosition2 = realPosition + 1;
                int size2 = size;
                this.tempRect.set((int) left, (int) top2, (int) right2, (int) bottom);
                Rect correctCellRect = correctCellRect(j, i, this.tempRect, this.config.getZoom());
                if (correctCellRect != null) {
                    int i8 = correctCellRect.top;
                    Rect rect3 = this.showRect;
                    if (i8 >= rect3.bottom) {
                        float f4 = right2;
                        Column column7 = topColumn2;
                        Column column8 = column2;
                        int i9 = realPosition2;
                        break;
                    } else if (correctCellRect.right <= rect3.left || correctCellRect.bottom <= rect3.top) {
                        right = right2;
                        topColumn = topColumn2;
                        column = column2;
                    } else {
                        Object data = column2.getDatas().get(j);
                        if (DrawUtils.isClick(correctCellRect, this.clickPoint)) {
                            this.operation.setSelectionRect(i, j, correctCellRect);
                            PointF pointF = this.tipPoint;
                            pointF.x = (left + right2) / 2.0f;
                            pointF.y = (top2 + bottom) / 2.0f;
                            clickColumn(column2, j, value, data);
                            this.isClickPoint = true;
                            this.clickPoint.set(-2.14748365E9f, -2.14748365E9f);
                        }
                        this.operation.checkSelectedPoint(i, j, correctCellRect);
                        float f5 = top2;
                        right = right2;
                        topColumn = topColumn2;
                        column = column2;
                        this.cellInfo.set(column2, data, value, i, j);
                        drawContentCell(canvas2, this.cellInfo, correctCellRect);
                    }
                } else {
                    right = right2;
                    topColumn = topColumn2;
                    column = column2;
                }
                top2 = bottom;
                j++;
                columnSize2 = columnSize;
                isPerFixed2 = isPerFixed;
                clipCount2 = clipCount;
                childColumnInfo2 = childColumnInfo;
                info2 = info;
                realPosition = realPosition2;
                size = size2;
                right2 = right;
                topColumn2 = topColumn;
                column2 = column;
            }
            left = tempLeft + width;
            i++;
            columns = columns2;
            columnSize2 = columnSize;
            isPerFixed2 = isPerFixed;
            clipCount2 = clipCount;
            childColumnInfo2 = childColumnInfo;
            info2 = info;
        }
        for (int i10 = 0; i10 < clipCount2; i10++) {
            canvas.restore();
        }
        if (this.config.isFixedCountRow()) {
            canvas.restore();
        }
    }

    /* access modifiers changed from: protected */
    public void drawContentCell(Canvas c, CellInfo<T> cellInfo2, Rect rect) {
        TableConfig tableConfig = this.config;
        tableConfig.contentGridStyle.fillPaint(tableConfig.getPaint());
        c.drawRect(rect, this.config.getPaint());
        rect.left += this.config.getTextLeftOffset();
        cellInfo2.column.getDrawFormat().draw(c, rect, cellInfo2);
    }

    private void clickColumn(Column column, int position, String value, Object data) {
        if (!this.isClickPoint && column.getOnColumnItemClickListener() != null) {
            column.getOnColumnItemClickListener().onClick(column, value, data, position);
        }
    }

    public void onClick(float x, float y) {
        PointF pointF = this.clickPoint;
        pointF.x = x;
        pointF.y = y;
    }

    public OnColumnClickListener getOnColumnClickListener() {
        return this.onColumnClickListener;
    }

    public void setOnColumnClickListener(OnColumnClickListener onColumnClickListener2) {
        this.onColumnClickListener = onColumnClickListener2;
    }

    public void setSelectFormat(ISelectFormat selectFormat) {
        this.operation.setSelectFormat(selectFormat);
    }

    public int[] getPointLocation(double row, double col) {
        int y;
        double d;
        int columnSize;
        int y2;
        List<Column> childColumns;
        double d2 = row;
        double d3 = col;
        List<Column> childColumns2 = this.tableData.getChildColumns();
        int[] lineHeights = this.tableData.getTableInfo().getLineHeightArray();
        int x = 0;
        int y3 = 0;
        int columnSize2 = childColumns2.size();
        int i = 0;
        while (true) {
            d = 1.0d;
            if (((double) i) > (((double) columnSize2) > d3 + 1.0d ? d3 + 1.0d : (double) (columnSize2 - 1))) {
                break;
            }
            int w = childColumns2.get(i).getComputeWidth();
            if (i == ((int) d3) + 1) {
                childColumns = childColumns2;
                y2 = y;
                columnSize = columnSize2;
                x = (int) (((double) x) + (((double) w) * (d3 - ((double) ((int) d3)))));
            } else {
                childColumns = childColumns2;
                y2 = y;
                columnSize = columnSize2;
                x += w;
            }
            i++;
            childColumns2 = childColumns;
            y3 = y2;
            columnSize2 = columnSize;
        }
        int i2 = y;
        int i3 = columnSize2;
        int i4 = 0;
        while (true) {
            if (((double) i4) <= (((double) lineHeights.length) > d2 + d ? d2 + d : (double) (lineHeights.length - 1))) {
                int h = lineHeights[i4];
                if (i4 == ((int) d2) + 1) {
                    y = (int) (((double) y) + (((double) h) * (d2 - ((double) ((int) d2)))));
                } else {
                    y += h;
                }
                i4++;
                double d4 = col;
                d = 1.0d;
            } else {
                Rect rect = this.scaleRect;
                return new int[]{((int) (((float) x) * this.config.getZoom())) + rect.left, ((int) (((float) y) * this.config.getZoom())) + rect.top};
            }
        }
    }

    public int[] getPointSize(int row, int col) {
        List<Column> childColumns = this.tableData.getChildColumns();
        int[] lineHeights = this.tableData.getTableInfo().getLineHeightArray();
        int col2 = col < childColumns.size() ? col : childColumns.size() - 1;
        int row2 = row < lineHeights.length ? row : lineHeights.length;
        return new int[]{(int) (((float) childColumns.get(col2 < 0 ? 0 : col2).getComputeWidth()) * this.config.getZoom()), (int) (((float) lineHeights[row2 < 0 ? 0 : row2]) * this.config.getZoom())};
    }

    private Rect correctCellRect(int row, int col, Rect rect, float zoom) {
        Cell point;
        Cell[][] rangePoints = this.tableData.getTableInfo().getRangeCells();
        if (rangePoints == null || rangePoints.length <= row || (point = rangePoints[row][col]) == null) {
            return rect;
        }
        if (point.col == -1 || point.row == -1) {
            return null;
        }
        List<Column> childColumns = this.tableData.getChildColumns();
        int[] lineHeights = this.tableData.getTableInfo().getLineHeightArray();
        int width = 0;
        int height = 0;
        for (int i = col; i < Math.min(childColumns.size(), point.col + col); i++) {
            width += childColumns.get(i).getComputeWidth();
        }
        for (int i2 = row; i2 < Math.min(lineHeights.length, point.row + row); i2++) {
            height += lineHeights[i2];
        }
        rect.right = (int) (((float) rect.left) + (((float) width) * zoom));
        rect.bottom = (int) (((float) rect.top) + (((float) height) * zoom));
        return rect;
    }

    public SelectionOperation getOperation() {
        return this.operation;
    }
}
