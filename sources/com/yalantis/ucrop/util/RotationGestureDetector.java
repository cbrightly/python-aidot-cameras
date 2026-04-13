package com.yalantis.ucrop.util;

import android.view.MotionEvent;
import androidx.annotation.NonNull;

public class RotationGestureDetector {
    private static final int INVALID_POINTER_INDEX = -1;
    private float fX;
    private float fY;
    private float mAngle;
    private boolean mIsFirstTouch;
    private OnRotationGestureListener mListener;
    private int mPointerIndex1 = -1;
    private int mPointerIndex2 = -1;
    private float sX;
    private float sY;

    public interface OnRotationGestureListener {
        boolean onRotation(RotationGestureDetector rotationGestureDetector);
    }

    public RotationGestureDetector(OnRotationGestureListener listener) {
        this.mListener = listener;
    }

    public float getAngle() {
        return this.mAngle;
    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        MotionEvent motionEvent = event;
        switch (event.getActionMasked()) {
            case 0:
                this.sX = event.getX();
                this.sY = event.getY();
                this.mPointerIndex1 = motionEvent.findPointerIndex(motionEvent.getPointerId(0));
                this.mAngle = 0.0f;
                this.mIsFirstTouch = true;
                break;
            case 1:
                this.mPointerIndex1 = -1;
                break;
            case 2:
                if (!(this.mPointerIndex1 == -1 || this.mPointerIndex2 == -1 || event.getPointerCount() <= this.mPointerIndex2)) {
                    float nsX = motionEvent.getX(this.mPointerIndex1);
                    float nsY = motionEvent.getY(this.mPointerIndex1);
                    float nfX = motionEvent.getX(this.mPointerIndex2);
                    float nfY = motionEvent.getY(this.mPointerIndex2);
                    if (this.mIsFirstTouch) {
                        this.mAngle = 0.0f;
                        this.mIsFirstTouch = false;
                    } else {
                        calculateAngleBetweenLines(this.fX, this.fY, this.sX, this.sY, nfX, nfY, nsX, nsY);
                    }
                    OnRotationGestureListener onRotationGestureListener = this.mListener;
                    if (onRotationGestureListener != null) {
                        onRotationGestureListener.onRotation(this);
                    }
                    this.fX = nfX;
                    this.fY = nfY;
                    this.sX = nsX;
                    this.sY = nsY;
                    break;
                }
            case 5:
                this.fX = event.getX();
                this.fY = event.getY();
                this.mPointerIndex2 = motionEvent.findPointerIndex(motionEvent.getPointerId(event.getActionIndex()));
                this.mAngle = 0.0f;
                this.mIsFirstTouch = true;
                break;
            case 6:
                this.mPointerIndex2 = -1;
                break;
        }
        return true;
    }

    private float calculateAngleBetweenLines(float fx1, float fy1, float fx2, float fy2, float sx1, float sy1, float sx2, float sy2) {
        return calculateAngleDelta((float) Math.toDegrees((double) ((float) Math.atan2((double) (fy1 - fy2), (double) (fx1 - fx2)))), (float) Math.toDegrees((double) ((float) Math.atan2((double) (sy1 - sy2), (double) (sx1 - sx2)))));
    }

    private float calculateAngleDelta(float angleFrom, float angleTo) {
        float f = (angleTo % 360.0f) - (angleFrom % 360.0f);
        this.mAngle = f;
        if (f < -180.0f) {
            this.mAngle = f + 360.0f;
        } else if (f > 180.0f) {
            this.mAngle = f - 360.0f;
        }
        return this.mAngle;
    }

    public static class SimpleOnRotationGestureListener implements OnRotationGestureListener {
        public boolean onRotation(RotationGestureDetector rotationDetector) {
            return false;
        }
    }
}
