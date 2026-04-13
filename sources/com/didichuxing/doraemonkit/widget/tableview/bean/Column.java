package com.didichuxing.doraemonkit.widget.tableview.bean;

import android.graphics.Paint;
import com.didichuxing.doraemonkit.widget.tableview.format.FastTextDrawFormat;
import com.didichuxing.doraemonkit.widget.tableview.intface.IDrawFormat;
import com.didichuxing.doraemonkit.widget.tableview.intface.IFormat;
import com.didichuxing.doraemonkit.widget.tableview.listener.OnColumnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class Column<T> implements Comparable<Column> {
    public static final String INVAL_VALUE = "";
    private String columnName;
    private int computeWidth;
    private List<T> datas;
    private IDrawFormat<T> drawFormat = new FastTextDrawFormat();
    private IFormat<T> format;
    private int id;
    private boolean isFixed;
    private int minHeight;
    private int minWidth;
    private OnColumnItemClickListener<T> onColumnItemClickListener;
    private Paint.Align textAlign;
    private Paint.Align titleAlign;
    private int width;

    public Column(String columnName2, IFormat<T> format2, IDrawFormat<T> drawFormat2) {
        this.columnName = columnName2;
        this.format = format2;
        if (drawFormat2 != null) {
            this.drawFormat = drawFormat2;
        }
        this.datas = new ArrayList();
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName2) {
        this.columnName = columnName2;
    }

    public IFormat<T> getFormat() {
        return this.format;
    }

    public void setFormat(IFormat<T> format2) {
        this.format = format2;
    }

    public IDrawFormat<T> getDrawFormat() {
        return this.drawFormat;
    }

    public void setDrawFormat(IDrawFormat<T> drawFormat2) {
        this.drawFormat = drawFormat2;
    }

    public List<T> getDatas() {
        return this.datas;
    }

    public void setDatas(List<T> datas2) {
        this.datas = datas2;
    }

    public String format(int position) {
        if (position < 0 || position >= this.datas.size()) {
            return "";
        }
        return format(this.datas.get(position));
    }

    public String format(T t) {
        IFormat<T> iFormat = this.format;
        if (iFormat != null) {
            return iFormat.format(t);
        }
        return t == null ? "" : t.toString();
    }

    public int getComputeWidth() {
        return this.computeWidth;
    }

    public void setComputeWidth(int computeWidth2) {
        this.computeWidth = computeWidth2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int compareTo(Column o) {
        return this.id - o.getId();
    }

    public OnColumnItemClickListener<T> getOnColumnItemClickListener() {
        return this.onColumnItemClickListener;
    }

    public void setOnColumnItemClickListener(OnColumnItemClickListener<T> onColumnItemClickListener2) {
        this.onColumnItemClickListener = onColumnItemClickListener2;
    }

    public boolean isFixed() {
        return this.isFixed;
    }

    public void setFixed(boolean fixed) {
        this.isFixed = fixed;
    }

    public Paint.Align getTextAlign() {
        return this.textAlign;
    }

    public void setTextAlign(Paint.Align textAlign2) {
        this.textAlign = textAlign2;
    }

    public int getMinWidth() {
        return this.minWidth;
    }

    public void setMinWidth(int minWidth2) {
        this.minWidth = minWidth2;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public void setMinHeight(int minHeight2) {
        this.minHeight = minHeight2;
    }

    public Paint.Align getTitleAlign() {
        return this.titleAlign;
    }

    public void setTitleAlign(Paint.Align titleAlign2) {
        this.titleAlign = titleAlign2;
    }

    public void setWidth(int width2) {
        if (width2 > 0) {
            this.width = width2;
            setDrawFormat(new FastTextDrawFormat());
        }
    }

    public int getWidth() {
        int i = this.width;
        if (i == 0) {
            return this.computeWidth;
        }
        return i;
    }
}
