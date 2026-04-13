package com.yalantis.ucrop.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import com.luck.picture.lib.R;

public class HorizontalProgressWheelView extends View {
    private final Rect mCanvasClipBounds;
    private float mLastTouchedPosition;
    private int mMiddleLineColor;
    private int mProgressLineHeight;
    private int mProgressLineMargin;
    private Paint mProgressLinePaint;
    private int mProgressLineWidth;
    private Paint mProgressMiddleLinePaint;
    private boolean mScrollStarted;
    private ScrollingListener mScrollingListener;
    private float mTotalScrollDistance;

    public interface ScrollingListener {
        void onScroll(float f, float f2);

        void onScrollEnd();

        void onScrollStart();
    }

    public HorizontalProgressWheelView(Context context) {
        this(context, (AttributeSet) null);
    }

    public HorizontalProgressWheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressWheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCanvasClipBounds = new Rect();
        init();
    }

    @TargetApi(21)
    public HorizontalProgressWheelView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mCanvasClipBounds = new Rect();
    }

    public void setScrollingListener(ScrollingListener scrollingListener) {
        this.mScrollingListener = scrollingListener;
    }

    public void setMiddleLineColor(@ColorInt int middleLineColor) {
        this.mMiddleLineColor = middleLineColor;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                this.mLastTouchedPosition = event.getX();
                break;
            case 1:
                ScrollingListener scrollingListener = this.mScrollingListener;
                if (scrollingListener != null) {
                    this.mScrollStarted = false;
                    scrollingListener.onScrollEnd();
                    break;
                }
                break;
            case 2:
                float distance = event.getX() - this.mLastTouchedPosition;
                if (distance != 0.0f) {
                    if (!this.mScrollStarted) {
                        this.mScrollStarted = true;
                        ScrollingListener scrollingListener2 = this.mScrollingListener;
                        if (scrollingListener2 != null) {
                            scrollingListener2.onScrollStart();
                        }
                    }
                    onScrollEvent(event, distance);
                    break;
                }
                break;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.getClipBounds(this.mCanvasClipBounds);
        int width = this.mCanvasClipBounds.width();
        int i = this.mProgressLineWidth;
        int i2 = this.mProgressLineMargin;
        int linesCount = width / (i + i2);
        float deltaX = this.mTotalScrollDistance % ((float) (i2 + i));
        for (int i3 = 0; i3 < linesCount; i3++) {
            if (i3 < linesCount / 4) {
                this.mProgressLinePaint.setAlpha((int) ((((float) i3) / ((float) (linesCount / 4))) * 255.0f));
            } else if (i3 > (linesCount * 3) / 4) {
                this.mProgressLinePaint.setAlpha((int) ((((float) (linesCount - i3)) / ((float) (linesCount / 4))) * 255.0f));
            } else {
                this.mProgressLinePaint.setAlpha(255);
            }
            Rect rect = this.mCanvasClipBounds;
            float f = (-deltaX) + ((float) rect.left) + ((float) ((this.mProgressLineWidth + this.mProgressLineMargin) * i3));
            float centerY = ((float) rect.centerY()) - (((float) this.mProgressLineHeight) / 4.0f);
            Rect rect2 = this.mCanvasClipBounds;
            canvas.drawLine(f, centerY, (-deltaX) + ((float) rect2.left) + ((float) ((this.mProgressLineWidth + this.mProgressLineMargin) * i3)), ((float) rect2.centerY()) + (((float) this.mProgressLineHeight) / 4.0f), this.mProgressLinePaint);
        }
        canvas.drawLine((float) this.mCanvasClipBounds.centerX(), ((float) this.mCanvasClipBounds.centerY()) - (((float) this.mProgressLineHeight) / 2.0f), (float) this.mCanvasClipBounds.centerX(), ((float) this.mCanvasClipBounds.centerY()) + (((float) this.mProgressLineHeight) / 2.0f), this.mProgressMiddleLinePaint);
    }

    private void onScrollEvent(MotionEvent event, float distance) {
        this.mTotalScrollDistance -= distance;
        postInvalidate();
        this.mLastTouchedPosition = event.getX();
        ScrollingListener scrollingListener = this.mScrollingListener;
        if (scrollingListener != null) {
            scrollingListener.onScroll(-distance, this.mTotalScrollDistance);
        }
    }

    private void init() {
        this.mMiddleLineColor = ContextCompat.getColor(getContext(), R.color.ucrop_color_widget_rotate_mid_line);
        this.mProgressLineWidth = getContext().getResources().getDimensionPixelSize(R.dimen.ucrop_width_horizontal_wheel_progress_line);
        this.mProgressLineHeight = getContext().getResources().getDimensionPixelSize(R.dimen.ucrop_height_horizontal_wheel_progress_line);
        this.mProgressLineMargin = getContext().getResources().getDimensionPixelSize(R.dimen.ucrop_margin_horizontal_wheel_progress_line);
        Paint paint = new Paint(1);
        this.mProgressLinePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mProgressLinePaint.setStrokeWidth((float) this.mProgressLineWidth);
        this.mProgressLinePaint.setColor(getResources().getColor(R.color.ucrop_color_progress_wheel_line));
        Paint paint2 = new Paint(this.mProgressLinePaint);
        this.mProgressMiddleLinePaint = paint2;
        paint2.setColor(this.mMiddleLineColor);
        this.mProgressMiddleLinePaint.setStrokeCap(Paint.Cap.ROUND);
        this.mProgressMiddleLinePaint.setStrokeWidth((float) getContext().getResources().getDimensionPixelSize(R.dimen.ucrop_width_middle_wheel_progress_line));
    }
}
