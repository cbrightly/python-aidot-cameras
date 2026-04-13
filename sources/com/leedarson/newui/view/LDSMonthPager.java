package com.leedarson.newui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.ldf.calendar.view.MonthPager;
import com.leedarson.bean.SwipeDirection;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class LDSMonthPager extends MonthPager {
    public static ChangeQuickRedirect changeQuickRedirect;
    private float a2;
    private SwipeDirection p2;

    public LDSMonthPager(Context context) {
        super(context);
    }

    public LDSMonthPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5041, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (h(event)) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5042, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (h(event)) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    private boolean h(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5043, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        SwipeDirection swipeDirection = this.p2;
        if (swipeDirection == SwipeDirection.all) {
            return true;
        }
        if (swipeDirection == SwipeDirection.none) {
            return false;
        }
        if (event.getAction() == 0) {
            this.a2 = event.getX();
            return true;
        }
        if (event.getAction() == 2) {
            try {
                float diffX = event.getX() - this.a2;
                if (diffX <= 0.0f || this.p2 != SwipeDirection.right) {
                    return diffX >= 0.0f || this.p2 != SwipeDirection.left;
                }
                return false;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        this.p2 = direction;
    }
}
