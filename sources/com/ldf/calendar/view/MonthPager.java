package com.ldf.calendar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;
import com.ldf.calendar.behavior.MonthPagerBehavior;
import com.ldf.calendar.component.CalendarViewAdapter;

@CoordinatorLayout.DefaultBehavior(MonthPagerBehavior.class)
public class MonthPager extends ViewPager {
    public static int c = 1000;
    private boolean a1;
    /* access modifiers changed from: private */
    public int d;
    private int f;
    private boolean p0;
    /* access modifiers changed from: private */
    public int p1;
    private int q;
    private int x;
    /* access modifiers changed from: private */
    public a y;
    /* access modifiers changed from: private */
    public boolean z;

    public interface a {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    public MonthPager(Context context) {
        this(context, (AttributeSet) null);
    }

    public MonthPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.d = c;
        this.x = 6;
        this.z = false;
        this.p0 = false;
        this.a1 = true;
        this.p1 = 0;
        f();
    }

    private void f() {
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (MonthPager.this.y != null) {
                    MonthPager.this.y.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            public void onPageSelected(int position) {
                int unused = MonthPager.this.d = position;
                if (MonthPager.this.z) {
                    if (MonthPager.this.y != null) {
                        MonthPager.this.y.onPageSelected(position);
                    }
                    boolean unused2 = MonthPager.this.z = false;
                }
            }

            public void onPageScrollStateChanged(int state) {
                int unused = MonthPager.this.p1 = state;
                if (MonthPager.this.y != null) {
                    MonthPager.this.y.onPageScrollStateChanged(state);
                }
                boolean unused2 = MonthPager.this.z = true;
            }
        });
        this.p0 = true;
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        if (this.p0) {
            Log.e("ldf", "MonthPager Just Can Use Own OnPageChangeListener");
        } else {
            super.addOnPageChangeListener(listener);
        }
    }

    public void addOnPageChangeListener(a listener) {
        this.y = listener;
        Log.e("ldf", "MonthPager Just Can Use Own OnPageChangeListener");
    }

    public void setScrollable(boolean scrollable) {
        this.a1 = scrollable;
    }

    public boolean onTouchEvent(MotionEvent me2) {
        if (!this.a1) {
            return false;
        }
        return super.onTouchEvent(me2);
    }

    public boolean onInterceptTouchEvent(MotionEvent me2) {
        if (!this.a1) {
            return false;
        }
        return super.onInterceptTouchEvent(me2);
    }

    public void g(int offset) {
        setCurrentItem(this.d + offset);
        ((CalendarViewAdapter) getAdapter()).h(CalendarViewAdapter.f());
    }

    public int getPageScrollState() {
        return this.p1;
    }

    public int getTopMovableDistance() {
        CalendarViewAdapter calendarViewAdapter = (CalendarViewAdapter) getAdapter();
        if (calendarViewAdapter == null) {
            return this.f;
        }
        int selectedRowIndex = calendarViewAdapter.c().get(this.d % 3).getSelectedRowIndex();
        this.x = selectedRowIndex;
        return this.f * selectedRowIndex;
    }

    public int getCellHeight() {
        return this.f;
    }

    public void setViewHeight(int viewHeight) {
        this.f = viewHeight / 6;
        this.q = viewHeight;
    }

    public int getViewHeight() {
        return this.q;
    }

    public int getCurrentPosition() {
        return this.d;
    }

    public void setCurrentPosition(int currentPosition) {
        this.d = currentPosition;
    }

    public int getRowIndex() {
        this.x = ((CalendarViewAdapter) getAdapter()).c().get(this.d % 3).getSelectedRowIndex();
        Log.e("ldf", "getRowIndex = " + this.x);
        return this.x;
    }

    public void setRowIndex(int rowIndex) {
        this.x = rowIndex;
    }
}
