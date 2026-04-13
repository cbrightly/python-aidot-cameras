package com.didichuxing.doraemonkit.kit.core;

import android.view.MotionEvent;
import android.view.View;
import com.didichuxing.doraemonkit.util.UIUtils;

public class TouchProxy {
    private static final int MIN_DISTANCE_MOVE = 4;
    private static final int MIN_TAP_TIME = 1000;
    private OnTouchEventListener mEventListener;
    private int mLastX;
    private int mLastY;
    private int mStartX;
    private int mStartY;
    private TouchState mState = TouchState.STATE_STOP;

    public interface OnTouchEventListener {
        void onDown(int i, int i2);

        void onMove(int i, int i2, int i3, int i4);

        void onUp(int i, int i2);
    }

    public enum TouchState {
        STATE_MOVE,
        STATE_STOP
    }

    public TouchProxy(OnTouchEventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public void setEventListener(OnTouchEventListener eventListener) {
        this.mEventListener = eventListener;
    }

    public boolean onTouchEvent(View v, MotionEvent event) {
        int distance = UIUtils.dp2px(1.0f) * 4;
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case 0:
                this.mStartX = x;
                this.mStartY = y;
                this.mLastY = y;
                this.mLastX = x;
                OnTouchEventListener onTouchEventListener = this.mEventListener;
                if (onTouchEventListener == null) {
                    return true;
                }
                onTouchEventListener.onDown(x, y);
                return true;
            case 1:
                OnTouchEventListener onTouchEventListener2 = this.mEventListener;
                if (onTouchEventListener2 != null) {
                    onTouchEventListener2.onUp(x, y);
                }
                if (this.mState != TouchState.STATE_MOVE && event.getEventTime() - event.getDownTime() < 1000) {
                    v.performClick();
                }
                this.mState = TouchState.STATE_STOP;
                return true;
            case 2:
                if (Math.abs(x - this.mStartX) >= distance || Math.abs(y - this.mStartY) >= distance) {
                    TouchState touchState = this.mState;
                    TouchState touchState2 = TouchState.STATE_MOVE;
                    if (touchState != touchState2) {
                        this.mState = touchState2;
                    }
                } else if (this.mState == TouchState.STATE_STOP) {
                    return true;
                }
                OnTouchEventListener onTouchEventListener3 = this.mEventListener;
                if (onTouchEventListener3 != null) {
                    int i = this.mLastX;
                    int i2 = this.mLastY;
                    onTouchEventListener3.onMove(i, i2, x - i, y - i2);
                }
                this.mLastY = y;
                this.mLastX = x;
                this.mState = TouchState.STATE_MOVE;
                return true;
            default:
                return true;
        }
    }
}
