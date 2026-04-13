package com.google.maps.android.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RotationLayout extends FrameLayout {
    private int mRotation;

    public RotationLayout(Context context) {
        super(context);
    }

    public RotationLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotationLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i = this.mRotation;
        if (i == 1 || i == 3) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setViewRotation(int degrees) {
        this.mRotation = ((degrees + 360) % 360) / 90;
    }

    public void dispatchDraw(Canvas canvas) {
        int i = this.mRotation;
        if (i == 0) {
            super.dispatchDraw(canvas);
            return;
        }
        if (i == 1) {
            canvas.translate((float) getWidth(), 0.0f);
            canvas.rotate(90.0f, (float) (getWidth() / 2), 0.0f);
            canvas.translate((float) (getHeight() / 2), (float) (getWidth() / 2));
        } else if (i == 2) {
            canvas.rotate(180.0f, (float) (getWidth() / 2), (float) (getHeight() / 2));
        } else {
            canvas.translate(0.0f, (float) getHeight());
            canvas.rotate(270.0f, (float) (getWidth() / 2), 0.0f);
            canvas.translate((float) (getHeight() / 2), (float) ((-getWidth()) / 2));
        }
        super.dispatchDraw(canvas);
    }
}
