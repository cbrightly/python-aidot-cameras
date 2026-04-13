package com.didichuxing.doraemonkit.kit.performance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.didichuxing.doraemonkit.R;

public class PolyLineItemView extends View {
    private static Paint mGradientPaint;
    private static float pointBottomY;
    private static float pointTopY = 50.0f;
    private final float BIG_RADIUS = 20.0f;
    private final float CIRCLE_STROKE_WIDTH = 2.0f;
    private final int GRAPH_STROKE_WIDTH = 2;
    private final float SMALL_RADIUS = 10.0f;
    private float currentValue;
    private boolean drawDiver;
    private boolean drawLeftLine = true;
    private boolean drawRightLine = true;
    private String label;
    private float lastValue;
    private Paint mPaint = new Paint();
    private float maxValue;
    private float minValue;
    private float nextValue;
    private boolean onTouch = false;
    private float pointSize = 10.0f;
    private float pointX;
    private float pointY;
    private boolean showLabel;
    private boolean touchable = true;
    private float viewHeight;
    private float viewWidth;

    public PolyLineItemView(Context context) {
        super(context);
    }

    public PolyLineItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PolyLineItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setCurrentValue(float currentValue2) {
        float f = this.maxValue;
        if (currentValue2 > f) {
            currentValue2 = (float) ((int) f);
        }
        float f2 = this.minValue;
        if (currentValue2 < f2) {
            currentValue2 = (float) ((int) f2);
        }
        this.currentValue = currentValue2;
        invalidate();
    }

    public void setMaxValue(int maxValue2) {
        this.maxValue = (float) maxValue2;
    }

    public void setMinValue(int minValue2) {
        this.minValue = (float) minValue2;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.viewHeight = (float) getMeasuredHeight();
        float measuredWidth = (float) getMeasuredWidth();
        this.viewWidth = measuredWidth;
        this.pointX = measuredWidth / 2.0f;
        if (pointBottomY == 0.0f) {
            pointBottomY = this.viewHeight - this.pointSize;
        }
        float f = 1.0f - (this.currentValue / (this.maxValue - this.minValue));
        float f2 = pointBottomY;
        float f3 = pointTopY;
        this.pointY = (f * (f2 - f3)) + f3;
        if (mGradientPaint == null) {
            Paint paint = new Paint();
            mGradientPaint = paint;
            paint.setShader(new LinearGradient(0.0f, 0.0f, this.viewWidth, this.viewHeight, getResources().getColor(R.color.dk_color_3300BFFF), getResources().getColor(R.color.dk_color_33434352), Shader.TileMode.CLAMP));
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawGraph(canvas);
        drawPoint(canvas);
        drawValue(canvas);
        drawLine(canvas);
    }

    private void drawValue(Canvas canvas) {
        if (this.onTouch || this.showLabel) {
            this.mPaint.setTextSize(20.0f);
            this.mPaint.setColor(-1);
            this.mPaint.setStrokeWidth(0.0f);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(this.label, this.viewWidth / 2.0f, this.pointY - (this.mPaint.getFontMetrics().bottom * 4.0f), this.mPaint);
        }
    }

    public void setlastValue(float lastValue2) {
        float f = this.maxValue;
        if (lastValue2 > f) {
            lastValue2 = (float) ((int) f);
        }
        float f2 = this.minValue;
        if (lastValue2 < f2) {
            lastValue2 = (float) ((int) f2);
        }
        this.lastValue = lastValue2;
    }

    public void setNextValue(float nextValue2) {
        float f = this.maxValue;
        if (nextValue2 > f) {
            nextValue2 = (float) ((int) f);
        }
        float f2 = this.minValue;
        if (nextValue2 < f2) {
            nextValue2 = (float) ((int) f2);
        }
        this.nextValue = nextValue2;
    }

    private void drawGraph(Canvas canvas) {
        this.mPaint.setPathEffect((PathEffect) null);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(getResources().getColor(R.color.dk_color_4c00C9F4));
        this.mPaint.setStrokeWidth(2.0f);
        this.mPaint.setAntiAlias(true);
        if (this.drawLeftLine) {
            float f = this.currentValue;
            float middleValue = f - ((f - this.lastValue) / 2.0f);
            float f2 = pointBottomY;
            float f3 = pointTopY;
            float f4 = this.maxValue;
            float f5 = this.minValue;
            float middleY = ((((f2 - f3) * 1.0f) / (f4 - f5)) * ((f4 - middleValue) + f5)) + f3;
            canvas.drawLine(0.0f, middleY, this.pointX, this.pointY, this.mPaint);
            drawGradient(canvas, middleY, false);
        }
        if (this.drawRightLine) {
            float f6 = this.currentValue;
            float middleValue2 = f6 - ((f6 - this.nextValue) / 2.0f);
            float f7 = pointBottomY;
            float f8 = pointTopY;
            float f9 = this.maxValue;
            float f10 = this.minValue;
            float middleY2 = ((((f7 - f8) * 1.0f) / (f9 - f10)) * ((f9 - middleValue2) + f10)) + f8;
            canvas.drawLine(this.pointX, this.pointY, this.viewWidth, middleY2, this.mPaint);
            drawGradient(canvas, middleY2, true);
        }
    }

    private void drawGradient(Canvas canvas, float middleY, boolean isRight) {
        Path path = new Path();
        if (!isRight) {
            path.moveTo(0.0f, middleY);
            path.lineTo(this.pointX, this.pointY);
            path.lineTo(this.pointX, pointBottomY);
            path.lineTo(0.0f, pointBottomY);
        } else {
            path.moveTo(this.pointX, this.pointY);
            path.lineTo(this.pointX, pointBottomY);
            path.lineTo(this.pointX + (this.viewWidth / 2.0f), pointBottomY);
            path.lineTo(this.pointX + (this.viewWidth / 2.0f), middleY);
        }
        canvas.drawPath(path, mGradientPaint);
    }

    private void drawPoint(Canvas canvas) {
        if (this.onTouch) {
            this.mPaint.setColor(getResources().getColor(R.color.dk_color_4c00C9F4));
            this.mPaint.setPathEffect((PathEffect) null);
            this.mPaint.setStrokeWidth(2.0f);
            this.mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(this.pointX, this.pointY, 20.0f, this.mPaint);
        }
        this.mPaint.setColor(getResources().getColor(R.color.dk_color_ff00C9F4));
        this.mPaint.setStrokeWidth(2.0f);
        canvas.drawCircle(this.pointX, this.pointY, this.pointSize, this.mPaint);
    }

    private void drawLine(Canvas canvas) {
        if (this.drawDiver) {
            this.mPaint.setColor(getResources().getColor(R.color.dk_color_999999));
            this.mPaint.setPathEffect((PathEffect) null);
            this.mPaint.setStrokeWidth(2.0f);
            this.mPaint.setStyle(Paint.Style.FILL);
            if (this.drawLeftLine) {
                float f = pointBottomY;
                canvas.drawLine(0.0f, f, this.viewWidth / 2.0f, f, this.mPaint);
            }
            if (this.drawRightLine) {
                float f2 = this.viewWidth;
                float f3 = pointBottomY;
                canvas.drawLine(f2 / 2.0f, f3, f2, f3, this.mPaint);
            }
        }
    }

    public void setDrawLeftLine(boolean drawLeftLine2) {
        this.drawLeftLine = drawLeftLine2;
    }

    public void setDrawRightLine(boolean drawRightLine2) {
        this.drawRightLine = drawRightLine2;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.touchable) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case 0:
                this.onTouch = true;
                setBackgroundResource(R.drawable.dk_line_chart_selected_background);
                break;
            case 1:
            case 3:
                this.onTouch = false;
                setBackgroundResource(0);
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setDrawDiver(boolean drawDiver2) {
        this.drawDiver = drawDiver2;
    }

    public void setPointSize(float pointSize2) {
        if (pointSize2 != 0.0f) {
            this.pointSize = pointSize2;
        }
    }

    public void setTouchable(boolean touchable2) {
        this.touchable = touchable2;
    }

    public void showLabel(boolean showLatestLabel) {
        this.showLabel = showLatestLabel;
    }

    public void setLabel(String label2) {
        this.label = label2;
    }
}
