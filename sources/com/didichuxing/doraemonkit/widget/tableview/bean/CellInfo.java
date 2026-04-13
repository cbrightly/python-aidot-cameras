package com.didichuxing.doraemonkit.widget.tableview.bean;

public class CellInfo<T> {
    public int col;
    public Column<T> column;
    public T data;
    public int row;
    public String value;

    public void set(Column<T> column2, T t, String value2, int col2, int row2) {
        this.column = column2;
        this.value = value2;
        this.data = t;
        this.row = row2;
        this.col = col2;
    }
}
