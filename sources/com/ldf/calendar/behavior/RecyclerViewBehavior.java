package com.ldf.calendar.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.ldf.calendar.a;
import com.ldf.calendar.view.MonthPager;

public class RecyclerViewBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    private int a = -1;
    private int b = -1;
    private Context c;
    private boolean d = false;
    boolean e = false;
    boolean f = false;

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context;
    }

    /* renamed from: c */
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        b(parent, child, a(parent));
        return true;
    }

    private void b(CoordinatorLayout parent, RecyclerView child, MonthPager monthPager) {
        if (monthPager.getBottom() > 0 && this.a == -1) {
            int viewHeight = monthPager.getViewHeight();
            this.a = viewHeight;
            i(viewHeight);
        }
        if (!this.d) {
            int viewHeight2 = monthPager.getViewHeight();
            this.a = viewHeight2;
            i(viewHeight2);
            this.d = true;
        }
        child.offsetTopAndBottom(a.n());
        this.b = a(parent).getCellHeight();
    }

    /* renamed from: g */
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.e("ldf", "onStartNestedScroll");
        ((MonthPager) coordinatorLayout.getChildAt(0)).setScrollable(false);
        if ((nestedScrollAxes & 2) != 0) {
            return true;
        }
        return false;
    }

    /* renamed from: f */
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, int dx, int dy, int[] consumed) {
        Log.e("ldf", "onNestedPreScroll");
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        child.setVerticalScrollBarEnabled(true);
        boolean z = false;
        if (((MonthPager) coordinatorLayout.getChildAt(0)).getPageScrollState() != 0) {
            consumed[1] = dy;
            Log.w("ldf", "onNestedPreScroll: MonthPager dragging");
            Toast.makeText(this.c, "loading month data", 0).show();
            return;
        }
        this.e = dy > 0 && child.getTop() <= this.a && child.getTop() > a(coordinatorLayout).getCellHeight();
        if (dy < 0 && !ViewCompat.canScrollVertically(target, -1)) {
            z = true;
        }
        this.f = z;
        if (this.e || z) {
            consumed[1] = a.p(child, dy, a(coordinatorLayout).getCellHeight(), a(coordinatorLayout).getViewHeight());
            i(child.getTop());
        }
    }

    /* renamed from: h */
    public void onStopNestedScroll(CoordinatorLayout parent, RecyclerView child, View target) {
        Log.e("ldf", "onStopNestedScroll");
        super.onStopNestedScroll(parent, child, target);
        ((MonthPager) parent.getChildAt(0)).setScrollable(true);
        if (!a.l()) {
            if (this.a - a.n() <= a.j(this.c) || !this.e) {
                a.q(parent, child, a(parent).getViewHeight(), 150);
            } else {
                a.q(parent, child, a(parent).getCellHeight(), 500);
            }
        } else if (a.n() - this.b <= a.j(this.c) || !this.f) {
            a.q(parent, child, a(parent).getCellHeight(), 150);
        } else {
            a.q(parent, child, a(parent).getViewHeight(), 500);
        }
    }

    /* renamed from: d */
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, float velocityX, float velocityY, boolean consumed) {
        Log.d("ldf", "onNestedFling: velocityY: " + velocityY);
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    /* renamed from: e */
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, RecyclerView child, View target, float velocityX, float velocityY) {
        if (this.e || this.f) {
            return true;
        }
        return false;
    }

    private MonthPager a(CoordinatorLayout coordinatorLayout) {
        return (MonthPager) coordinatorLayout.getChildAt(0);
    }

    private void i(int top) {
        a.o(top);
        if (a.n() == this.a) {
            a.s(false);
        } else if (a.n() == this.b) {
            a.s(true);
        }
    }
}
