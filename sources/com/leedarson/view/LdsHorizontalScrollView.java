package com.leedarson.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class LdsHorizontalScrollView extends HorizontalScrollView {
    public static ChangeQuickRedirect changeQuickRedirect;

    public LdsHorizontalScrollView(Context context) {
        super(context);
    }

    public LdsHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LdsHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ev}, this, changeQuickRedirect, false, 11738, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Log.d("LdsHorizontalScrollView", "[hyf touch]onTouchEvent: ");
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onTouchEvent(ev);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 11739, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        Log.d("LdsHorizontalScrollView", "[hyf touch]dispatchTouchEvent: ");
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }
}
