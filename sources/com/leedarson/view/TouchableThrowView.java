package com.leedarson.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class TouchableThrowView extends RelativeLayout {
    public static ChangeQuickRedirect changeQuickRedirect;

    public TouchableThrowView(Context context) {
        super(context);
    }

    public TouchableThrowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchableThrowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{motionEvent}, this, changeQuickRedirect, false, 11794, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        MotionEvent event = motionEvent;
        if (getContext() == null || !(getContext() instanceof Activity)) {
            return false;
        }
        MotionEvent obtain = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), event.getAction(), event.getRawX(), event.getRawY(), event.getPressure(), event.getSize(), event.getMetaState(), event.getXPrecision(), event.getYPrecision(), event.getDeviceId(), event.getEdgeFlags());
        return ((Activity) getContext()).dispatchTouchEvent(event);
    }
}
