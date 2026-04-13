package com.didichuxing.doraemonkit.widget.tableview.intface;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.bean.CellInfo;
import com.didichuxing.doraemonkit.widget.tableview.bean.Column;

public interface IDrawFormat<T> {
    void draw(Canvas canvas, Rect rect, CellInfo<T> cellInfo);

    int measureHeight(Column<T> column, int i);

    int measureWidth(Column<T> column, int i);
}
