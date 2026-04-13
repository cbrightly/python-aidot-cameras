package com.didichuxing.doraemonkit.widget.tableview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import com.didichuxing.doraemonkit.widget.tableview.intface.ISelectFormat;
import com.didichuxing.doraemonkit.widget.tableview.utils.MatrixHelper;

public class SelectionOperation implements MatrixHelper.OnInterceptListener {
    private static final int INVALID = -1;
    private boolean isShow;
    private int selectColumn = -1;
    private ISelectFormat selectFormat;
    private int selectRow = -1;
    private Rect selectionRect = new Rect();

    public void reset() {
        this.isShow = false;
    }

    public void setSelectionRect(int selectColumn2, int selectRow2, Rect rect) {
        this.selectRow = selectRow2;
        this.selectColumn = selectColumn2;
        this.selectionRect.set(rect);
        this.isShow = true;
    }

    public boolean isSelectedPoint(int selectColumn2, int selectRow2) {
        return selectRow2 == this.selectRow && selectColumn2 == this.selectColumn;
    }

    public void checkSelectedPoint(int selectColumn2, int selectRow2, Rect rect) {
        if (isSelectedPoint(selectColumn2, selectRow2)) {
            this.selectionRect.set(rect);
            this.isShow = true;
        }
    }

    public void draw(Canvas canvas, Rect showRect, TableConfig config) {
        ISelectFormat iSelectFormat = this.selectFormat;
        if (iSelectFormat != null && this.isShow) {
            iSelectFormat.draw(canvas, this.selectionRect, showRect, config);
        }
    }

    public ISelectFormat getSelectFormat() {
        return this.selectFormat;
    }

    public void setSelectFormat(ISelectFormat selectFormat2) {
        this.selectFormat = selectFormat2;
    }

    public boolean isIntercept(MotionEvent e1, float distanceX, float distanceY) {
        return false;
    }
}
