package com.didichuxing.doraemonkit.widget.verticalviewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;
import com.didichuxing.doraemonkit.widget.verticalviewpager.transforms.DefaultTransformer;

public class VerticalViewPager extends ViewPager {
    public VerticalViewPager(Context context) {
        this(context, (AttributeSet) null);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, new DefaultTransformer());
    }

    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = (float) getWidth();
        float height = (float) getHeight();
        event.setLocation((event.getY() / height) * width, (event.getX() / width) * height);
        return event;
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = super.onInterceptTouchEvent(swapTouchEvent(event));
        swapTouchEvent(event);
        return intercept;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapTouchEvent(ev));
    }
}
