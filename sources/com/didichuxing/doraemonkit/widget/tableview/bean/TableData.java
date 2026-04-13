package com.didichuxing.doraemonkit.widget.tableview.bean;

import com.didichuxing.doraemonkit.widget.tableview.format.NumberSequenceFormat;
import com.didichuxing.doraemonkit.widget.tableview.format.TitleDrawFormat;
import com.didichuxing.doraemonkit.widget.tableview.intface.ISequenceFormat;
import com.didichuxing.doraemonkit.widget.tableview.intface.ITitleDrawFormat;
import com.didichuxing.doraemonkit.widget.tableview.listener.OnColumnItemClickListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableData<T> {
    private ISequenceFormat YSequenceFormat;
    private List<ColumnInfo> childColumnInfos;
    /* access modifiers changed from: private */
    public List<Column> childColumns;
    private List<ColumnInfo> columnInfos;
    private List<Column> columns;
    /* access modifiers changed from: private */
    public OnColumnClickListener<?> onColumnClickListener;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;
    /* access modifiers changed from: private */
    public OnRowClickListener<T> onRowClickListener;
    private Column sortColumn;
    /* access modifiers changed from: private */
    public List<T> t;
    private TableInfo tableInfo;
    private String tableName;
    private ITitleDrawFormat titleDrawFormat;

    public interface OnColumnClickListener<T> {
        void onClick(Column column, List<T> list, int i, int i2);
    }

    public interface OnItemClickListener<T> {
        void onClick(Column<T> column, String str, T t, int i, int i2);
    }

    public interface OnRowClickListener<T> {
        void onClick(Column column, T t, int i, int i2);
    }

    public TableData(String tableName2, List<T> t2, List<Column> columns2) {
        this(tableName2, t2, columns2, (ITitleDrawFormat) null);
    }

    public TableData(String tableName2, List<T> t2, Column... columns2) {
        this(tableName2, t2, (List<Column>) Arrays.asList(columns2));
    }

    public TableData(String tableName2, List<T> t2, List<Column> columns2, ITitleDrawFormat titleDrawFormat2) {
        TableInfo tableInfo2 = new TableInfo();
        this.tableInfo = tableInfo2;
        this.tableName = tableName2;
        this.columns = columns2;
        this.t = t2;
        tableInfo2.setLineSize(t2.size());
        this.childColumns = new ArrayList();
        this.columnInfos = new ArrayList();
        this.childColumnInfos = new ArrayList();
        this.titleDrawFormat = titleDrawFormat2 == null ? new TitleDrawFormat() : titleDrawFormat2;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName2) {
        this.tableName = tableName2;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Column> columns2) {
        this.columns = columns2;
    }

    public List<T> getT() {
        return this.t;
    }

    public void setT(List<T> t2) {
        this.t = t2;
        this.tableInfo.setLineSize(t2.size());
    }

    public List<Column> getChildColumns() {
        return this.childColumns;
    }

    public TableInfo getTableInfo() {
        return this.tableInfo;
    }

    public void setTableInfo(TableInfo tableInfo2) {
        this.tableInfo = tableInfo2;
    }

    public List<ColumnInfo> getColumnInfos() {
        return this.columnInfos;
    }

    public List<ColumnInfo> getChildColumnInfos() {
        return this.childColumnInfos;
    }

    public void setChildColumnInfos(List<ColumnInfo> childColumnInfos2) {
        this.childColumnInfos = childColumnInfos2;
    }

    public void setColumnInfos(List<ColumnInfo> columnInfos2) {
        this.columnInfos = columnInfos2;
    }

    public void setChildColumns(List<Column> childColumns2) {
        this.childColumns = childColumns2;
    }

    public Column getSortColumn() {
        return this.sortColumn;
    }

    public void setSortColumn(Column sortColumn2) {
        this.sortColumn = sortColumn2;
    }

    public ITitleDrawFormat getTitleDrawFormat() {
        return this.titleDrawFormat;
    }

    public void setTitleDrawFormat(ITitleDrawFormat titleDrawFormat2) {
        this.titleDrawFormat = titleDrawFormat2;
    }

    public ISequenceFormat getYSequenceFormat() {
        if (this.YSequenceFormat == null) {
            this.YSequenceFormat = new NumberSequenceFormat();
        }
        return this.YSequenceFormat;
    }

    public void setYSequenceFormat(ISequenceFormat YSequenceFormat2) {
        this.YSequenceFormat = YSequenceFormat2;
    }

    public Column getColumnByID(int id) {
        for (Column column : getChildColumns()) {
            if (column.getId() == id) {
                return column;
            }
        }
        return null;
    }

    public int getLineSize() {
        return this.tableInfo.getLineHeightArray().length;
    }

    public void clear() {
        List<T> list = this.t;
        if (list != null) {
            list.clear();
            this.t = null;
        }
        List<Column> list2 = this.childColumns;
        if (list2 != null) {
            list2.clear();
            this.childColumns = null;
        }
        if (this.columns != null) {
            this.columns = null;
        }
        List<ColumnInfo> list3 = this.childColumnInfos;
        if (list3 != null) {
            list3.clear();
            this.childColumnInfos = null;
        }
        TableInfo tableInfo2 = this.tableInfo;
        if (tableInfo2 != null) {
            tableInfo2.clear();
            this.tableInfo = null;
        }
        this.sortColumn = null;
        this.titleDrawFormat = null;
        this.YSequenceFormat = null;
    }

    public OnItemClickListener getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
        for (Column column : this.columns) {
            column.setOnColumnItemClickListener(new OnColumnItemClickListener() {
                public void onClick(Column column, String value, Object t, int position) {
                    if (onItemClickListener2 != null) {
                        TableData.this.onItemClickListener.onClick(column, value, t, TableData.this.childColumns.indexOf(column), position);
                    }
                }
            });
        }
    }

    public void setOnRowClickListener(OnRowClickListener<T> onRowClickListener2) {
        this.onRowClickListener = onRowClickListener2;
        if (onRowClickListener2 != null) {
            setOnItemClickListener(new OnItemClickListener() {
                public void onClick(Column column, String value, Object o, int col, int row) {
                    TableData.this.onRowClickListener.onClick(column, TableData.this.t.get(row), col, row);
                }
            });
        }
    }

    public void setOnColumnClickListener(OnColumnClickListener onColumnClickListener2) {
        this.onColumnClickListener = onColumnClickListener2;
        if (this.onRowClickListener != null) {
            setOnItemClickListener(new OnItemClickListener() {
                public void onClick(Column column, String value, Object o, int col, int row) {
                    TableData.this.onColumnClickListener.onClick(column, column.getDatas(), col, row);
                }
            });
        }
    }

    public OnRowClickListener getOnRowClickListener() {
        return this.onRowClickListener;
    }
}
