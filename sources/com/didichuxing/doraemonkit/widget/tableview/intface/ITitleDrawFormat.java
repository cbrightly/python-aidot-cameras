package com.didichuxing.doraemonkit.widget.tableview.intface;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.TableConfig;
import com.didichuxing.doraemonkit.widget.tableview.bean.Column;

public interface ITitleDrawFormat {
    void draw(Canvas canvas, Column column, Rect rect, TableConfig tableConfig);

    int measureHeight(TableConfig tableConfig);

    int measureWidth(Column column, TableConfig tableConfig);
}
