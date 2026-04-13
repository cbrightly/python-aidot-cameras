package com.didichuxing.doraemonkit.widget.tableview;

import android.graphics.Paint;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.bean.Cell;
import com.didichuxing.doraemonkit.widget.tableview.bean.Column;
import com.didichuxing.doraemonkit.widget.tableview.bean.ColumnInfo;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableData;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableInfo;
import com.didichuxing.doraemonkit.widget.tableview.intface.ITableTitle;
import java.util.List;

public class TableMeasurer<T> {
    private boolean isReMeasure;

    public TableInfo measure(TableData<T> tableData, int width, int height) {
        this.isReMeasure = true;
        TableInfo tableInfo = tableData.getTableInfo();
        tableInfo.setTableRect(new Rect(0, 0, Math.max(getTableWidth(tableData), width), Math.max(getTableHeight(tableData), height)));
        measureColumnSize(tableData);
        return tableInfo;
    }

    public void measureTableTitle(TableData<T> tableData, ITableTitle tableTitle, Rect showRect) {
        TableInfo tableInfo = tableData.getTableInfo();
        Rect tableRect = tableInfo.getTableRect();
        if (this.isReMeasure) {
            this.isReMeasure = false;
            int size = tableTitle.getSize();
            tableInfo.setTitleDirection(tableTitle.getDirection());
            tableInfo.setTableTitleSize(size);
            if (tableTitle.getDirection() == 1 || tableTitle.getDirection() == 3) {
                tableRect.bottom += size;
                reSetShowRect(showRect, tableRect);
                return;
            }
            tableRect.right += size;
            reSetShowRect(showRect, tableRect);
            return;
        }
        reSetShowRect(showRect, tableRect);
    }

    public void reSetShowRect(Rect showRect, Rect tableRect) {
        int i = showRect.bottom;
        int i2 = tableRect.bottom;
        if (i > i2) {
            showRect.bottom = i2;
        }
        int i3 = showRect.right;
        int i4 = tableRect.right;
        if (i3 > i4) {
            showRect.right = i4;
        }
    }

    public void addTableHeight(TableData<T> tableData) {
        tableData.getTableInfo().setTableRect(new Rect(0, 0, getTableWidth(tableData), getTableHeight(tableData)));
    }

    private int getTableHeight(TableData<T> tableData) {
        TableConfig config = TableConfig.getInstance();
        int titleHeight = config.isShowColumnTitle() ? tableData.getTitleDrawFormat().measureHeight(config) + (config.getColumnTitleVerticalPadding() * 2) : 0;
        TableInfo tableInfo = tableData.getTableInfo();
        tableInfo.setTitleHeight(titleHeight);
        tableInfo.setTopHeight(0);
        int totalContentHeight = 0;
        for (int height : tableInfo.getLineHeightArray()) {
            totalContentHeight += height;
        }
        return 0 + (tableInfo.getMaxLevel() * titleHeight) + totalContentHeight;
    }

    private int getTableWidth(TableData<T> tableData) {
        int[] lineHeightArray;
        int totalSize;
        int i;
        int totalWidth = 0;
        TableConfig config = TableConfig.getInstance();
        Paint paint = config.getPaint();
        config.YSequenceStyle.fillPaint(paint);
        int totalSize2 = tableData.getLineSize();
        if (config.isShowYSequence()) {
            int yAxisWidth = (int) paint.measureText(tableData.getYSequenceFormat().format(Integer.valueOf(totalSize2)) + (config.getSequenceHorizontalPadding() * 2));
            tableData.getTableInfo().setyAxisWidth(yAxisWidth);
            totalWidth = 0 + yAxisWidth;
        }
        int columnPos = 0;
        int contentWidth = 0;
        int[] lineHeightArray2 = tableData.getTableInfo().getLineHeightArray();
        for (Column column : tableData.getChildColumns()) {
            float columnNameWidth = (float) (tableData.getTitleDrawFormat().measureWidth(column, config) + (config.getColumnTitleHorizontalPadding() * 2));
            int columnWidth = 0;
            int size = column.getDatas().size();
            int currentPosition = 0;
            Cell[][] rangeCells = tableData.getTableInfo().getRangeCells();
            int position = 0;
            while (position < size) {
                int width = column.getDrawFormat().measureWidth(column, position);
                Paint paint2 = paint;
                measureRowHeight(lineHeightArray2, column, currentPosition, position);
                currentPosition++;
                if (rangeCells != null) {
                    Cell cell = rangeCells[position][columnPos];
                    if (cell != null) {
                        totalSize = totalSize2;
                        lineHeightArray = lineHeightArray2;
                        if (cell.row == -1 || (i = cell.col) == -1) {
                            Cell cell2 = cell.realCell;
                            if (cell2 != null) {
                                width = cell2.width / cell2.col;
                            }
                        } else {
                            cell.width = width;
                            width /= i;
                        }
                    } else {
                        totalSize = totalSize2;
                        lineHeightArray = lineHeightArray2;
                    }
                } else {
                    totalSize = totalSize2;
                    lineHeightArray = lineHeightArray2;
                }
                if (columnWidth < width) {
                    columnWidth = width;
                }
                position++;
                paint = paint2;
                totalSize2 = totalSize;
                lineHeightArray2 = lineHeightArray;
            }
            Paint paint3 = paint;
            int totalSize3 = totalSize2;
            int[] iArr = lineHeightArray2;
            int width2 = Math.max(column.getMinWidth(), (int) Math.max(columnNameWidth, (float) ((config.getHorizontalPadding() * 2) + columnWidth)));
            column.setComputeWidth(width2);
            contentWidth += width2;
            columnPos++;
            paint = paint3;
            totalSize2 = totalSize3;
        }
        int i2 = totalSize2;
        int[] iArr2 = lineHeightArray2;
        int minWidth = config.getMinTableWidth();
        if (minWidth == -1 || minWidth - totalWidth < contentWidth) {
            return totalWidth + contentWidth;
        }
        int minWidth2 = minWidth - totalWidth;
        float widthScale = ((float) minWidth2) / ((float) contentWidth);
        for (Column column2 : tableData.getChildColumns()) {
            column2.setComputeWidth((int) (((float) column2.getComputeWidth()) * widthScale));
        }
        return totalWidth + minWidth2;
    }

    private void measureRowHeight(int[] lineHeightArray, Column column, int currentPosition, int position) {
        TableConfig config = TableConfig.getInstance();
        int height = 0;
        if (0 == 0) {
            height = column.getDrawFormat().measureHeight(column, position) + (config.getVerticalPadding() * 2);
        }
        int height2 = Math.max(column.getMinHeight(), height);
        if (height2 > lineHeightArray[currentPosition]) {
            lineHeightArray[currentPosition] = height2;
        }
    }

    private void measureColumnSize(TableData<T> tableData) {
        List<Column> columnList = tableData.getColumns();
        int left = 0;
        int maxLevel = tableData.getTableInfo().getMaxLevel();
        tableData.getColumnInfos().clear();
        tableData.getChildColumnInfos().clear();
        for (int i = 0; i < columnList.size(); i++) {
            left += getColumnInfo(tableData, columnList.get(i), (ColumnInfo) null, left, 0, maxLevel).width;
        }
    }

    public ColumnInfo getColumnInfo(TableData<T> tableData, Column column, ColumnInfo parent, int left, int top, int overLevel) {
        TableInfo tableInfo = tableData.getTableInfo();
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.value = column.getColumnName();
        columnInfo.column = column;
        columnInfo.width = column.getComputeWidth();
        columnInfo.top = top;
        columnInfo.height = tableInfo.getTitleHeight() * overLevel;
        columnInfo.left = left;
        tableData.getChildColumnInfos().add(columnInfo);
        tableData.getColumnInfos().add(columnInfo);
        return columnInfo;
    }
}
