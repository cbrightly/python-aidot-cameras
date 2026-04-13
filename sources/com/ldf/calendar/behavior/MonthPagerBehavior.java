package com.ldf.calendar.behavior;

import android.util.Log;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.ldf.calendar.a;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.view.MonthPager;

public class MonthPagerBehavior extends CoordinatorLayout.Behavior<MonthPager> {
    private int a = 0;
    private int b = 1;
    private int c = 0;
    private int d = -1;

    /* renamed from: a */
    public boolean layoutDependsOn(CoordinatorLayout parent, MonthPager child, View dependency) {
        return dependency instanceof RecyclerView;
    }

    /* renamed from: c */
    public boolean onLayoutChild(CoordinatorLayout parent, MonthPager child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        child.offsetTopAndBottom(this.a);
        return true;
    }

    /* renamed from: b */
    public boolean onDependentViewChanged(CoordinatorLayout parent, MonthPager child, View dependency) {
        int i;
        int i2;
        CalendarViewAdapter calendarViewAdapter = (CalendarViewAdapter) child.getAdapter();
        if (this.d != -1) {
            int dy = dependency.getTop() - this.d;
            int top = child.getTop();
            int i3 = this.b;
            if (dy > i3) {
                calendarViewAdapter.n();
            } else if (dy < (-i3)) {
                calendarViewAdapter.o(child.getRowIndex());
            }
            if (dy > (-top)) {
                dy = -top;
            }
            if (dy < (-top) - child.getTopMovableDistance()) {
                dy = (-top) - child.getTopMovableDistance();
            }
            child.offsetTopAndBottom(dy);
            Log.e("ldf", "onDependentViewChanged = " + dy);
        }
        this.d = dependency.getTop();
        this.a = child.getTop();
        if (this.c > child.getCellHeight()) {
            calendarViewAdapter.n();
        }
        if (this.c < (-child.getCellHeight())) {
            calendarViewAdapter.o(child.getRowIndex());
        }
        if (this.d > child.getCellHeight() - 24 && this.d < child.getCellHeight() + 24 && this.a > (-this.b) - child.getTopMovableDistance() && this.a < this.b - child.getTopMovableDistance()) {
            a.s(true);
            calendarViewAdapter.o(child.getRowIndex());
            this.c = 0;
        }
        if (this.d > child.getViewHeight() - 24 && this.d < child.getViewHeight() + 24 && (i = this.a) < (i2 = this.b) && i > (-i2)) {
            a.s(false);
            calendarViewAdapter.n();
            this.c = 0;
        }
        return true;
    }
}
