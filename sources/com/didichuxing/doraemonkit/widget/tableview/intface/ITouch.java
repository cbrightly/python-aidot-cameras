package com.didichuxing.doraemonkit.widget.tableview.intface;

import android.view.MotionEvent;
import android.view.View;

public interface ITouch {
    boolean handlerTouchEvent(MotionEvent motionEvent);

    void onDisallowInterceptEvent(View view, MotionEvent motionEvent);
}
