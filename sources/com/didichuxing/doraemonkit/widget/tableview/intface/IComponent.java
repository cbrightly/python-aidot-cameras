package com.didichuxing.doraemonkit.widget.tableview.intface;

import android.graphics.Canvas;
import android.graphics.Rect;
import com.didichuxing.doraemonkit.widget.tableview.TableConfig;

public interface IComponent<T> {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 2;
    public static final int TOP = 1;

    void onDraw(Canvas canvas, Rect rect, T t, TableConfig tableConfig);

    void onMeasure(Rect rect, Rect rect2, TableConfig tableConfig);
}
