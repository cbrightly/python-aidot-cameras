package com.didichuxing.doraemonkit.widget.tableview.bean;

import com.didichuxing.doraemonkit.widget.tableview.TableConfig;
import com.didichuxing.doraemonkit.widget.tableview.intface.IDrawFormat;
import com.didichuxing.doraemonkit.widget.tableview.intface.IFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayTableData<T> extends TableData<T> {
    private List<Column<T>> arrayColumns;
    private T[][] data;

    public static <T> ArrayTableData<T> create(String tableName, String[] titleNames, T[][] data2, IDrawFormat<T> drawFormat) {
        List<Column<T>> columns = new ArrayList<>();
        for (int i = 0; i < data2.length; i++) {
            T[] dataArray = data2[i];
            Column<T> column = new Column<>(titleNames == null ? "" : titleNames[i], (IFormat) null, drawFormat);
            column.setDatas(Arrays.asList(dataArray));
            columns.add(column);
        }
        ArrayTableData<T> tableData = new ArrayTableData<>(tableName, new ArrayList<>(Arrays.asList(data2[0])), columns);
        tableData.setData(data2);
        return tableData;
    }

    public static <T> ArrayTableData<T> create(String tableName, T[][] data2, IDrawFormat<T> drawFormat) {
        TableConfig.getInstance().setShowColumnTitle(false);
        return create(tableName, (String[]) null, data2, drawFormat);
    }

    public void setFormat(IFormat<T> format) {
        for (Column<T> column : this.arrayColumns) {
            column.setFormat(format);
        }
    }

    public void setDrawFormat(IDrawFormat<T> format) {
        for (Column<T> column : this.arrayColumns) {
            column.setDrawFormat(format);
        }
    }

    public void setMinWidth(int minWidth) {
        for (Column<T> column : this.arrayColumns) {
            column.setMinWidth(minWidth);
        }
    }

    public void setMinHeight(int minHeight) {
        for (Column<T> column : this.arrayColumns) {
            column.setMinHeight(minHeight);
        }
    }

    protected ArrayTableData(String tableName, List<T> t, List<Column<T>> columns) {
        super(tableName, t, (List<Column>) new ArrayList(columns));
        this.arrayColumns = columns;
    }

    public List<Column<T>> getArrayColumns() {
        return this.arrayColumns;
    }

    public T[][] getData() {
        return this.data;
    }

    public void setData(T[][] data2) {
        this.data = data2;
    }
}
