package com.didichuxing.doraemonkit.kit.performance.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Shader;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.util.UIUtils;

public class LineRender {
    private final float CIRCLE_STROKE_WIDTH = 2.0f;
    private final int GRAPH_STROKE_WIDTH = 2;
    private final float SMALL_RADIUS = 10.0f;
    private float baseLine = 20.0f;
    private boolean drawRightLine = true;
    private String label;
    private float labelAlpha;
    private Context mContext;
    private Paint mGradientPaint = new Paint();
    private Path mGradientPath = new Path();
    private Paint mLabelPaint = new Paint(1);
    private Paint mLinePaint = new Paint(1);
    private int mPaddingBottom;
    private int mPaddingTop = 50;
    private Paint mPointPaint = new Paint(1);
    private float maxValue;
    private float minValue;
    private float nextValue;
    private float pointSize = 10.0f;
    private float pointX;
    private float pointY;
    private boolean showLabel;
    private float startPosition;
    private float viewHeight;
    private float viewWidth;

    public LineRender(Context context) {
        this.mContext = context;
        this.mPaddingBottom = UIUtils.dp2px(2.0f);
    }

    public void setMaxValue(int maxValue2) {
        this.maxValue = (float) maxValue2;
    }

    public void setMinValue(int minValue2) {
        this.minValue = (float) minValue2;
    }

    /* access modifiers changed from: protected */
    public void measure(float width, float height) {
        this.viewHeight = (height - ((float) this.mPaddingBottom)) - ((float) this.mPaddingTop);
        this.viewWidth = width;
        initPaint();
    }

    private void initPaint() {
        this.mGradientPaint.setShader(new LinearGradient(0.0f, 0.0f, this.viewWidth, this.viewHeight, this.mContext.getResources().getColor(R.color.dk_color_3300BFFF), this.mContext.getResources().getColor(R.color.dk_color_33434352), Shader.TileMode.CLAMP));
        this.mLabelPaint.setTextSize((float) this.mContext.getResources().getDimensionPixelSize(R.dimen.dk_font_size_10));
        this.mLabelPaint.setColor(-1);
        this.mLabelPaint.setTextAlign(Paint.Align.CENTER);
        this.mLinePaint.setPathEffect((PathEffect) null);
        this.mLinePaint.setStyle(Paint.Style.FILL);
        this.mLinePaint.setColor(this.mContext.getResources().getColor(R.color.dk_color_4c00C9F4));
        this.mLinePaint.setStrokeWidth(2.0f);
        this.mLinePaint.setAntiAlias(true);
        this.mPointPaint.setColor(this.mContext.getResources().getColor(R.color.dk_color_ff00C9F4));
        this.mPointPaint.setStrokeWidth(2.0f);
    }

    public void draw(Canvas canvas) {
        drawGraph(canvas);
        drawGradient(canvas);
        drawPoint(canvas);
        drawLabel(canvas);
    }

    private void drawLabel(Canvas canvas) {
        if (this.showLabel && !TextUtils.isEmpty(this.label)) {
            this.mLabelPaint.setAlpha((int) (this.labelAlpha * 255.0f));
            canvas.drawText(this.label, this.startPosition, this.pointY - this.baseLine, this.mLabelPaint);
        }
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
        this.nextValue = ((1.0f - (nextValue2 / (f - f2))) * this.viewHeight) + ((float) this.mPaddingTop);
    }

    public void setCurrentValue(int index, float currentValue) {
        float f = this.maxValue;
        if (currentValue > f) {
            currentValue = (float) ((int) f);
        }
        float f2 = this.minValue;
        if (currentValue < f2) {
            currentValue = (float) ((int) f2);
        }
        float f3 = ((float) index) * this.viewWidth;
        this.startPosition = f3;
        this.pointX = f3;
        this.pointY = ((1.0f - (currentValue / (f - f2))) * this.viewHeight) + ((float) this.mPaddingTop);
    }

    private void drawGraph(Canvas canvas) {
        if (this.drawRightLine) {
            float middleY = this.nextValue;
            float f = this.startPosition;
            canvas.drawLine(f, this.pointY, this.viewWidth + f, middleY, this.mLinePaint);
        }
    }

    private void drawGradient(Canvas canvas) {
        if (this.drawRightLine) {
            this.mGradientPath.rewind();
            this.mGradientPath.moveTo(this.pointX, this.pointY);
            this.mGradientPath.lineTo(this.pointX, this.viewHeight + ((float) this.mPaddingTop));
            this.mGradientPath.lineTo(this.pointX + this.viewWidth, this.viewHeight + ((float) this.mPaddingTop));
            this.mGradientPath.lineTo(this.pointX + this.viewWidth, this.nextValue);
            canvas.drawPath(this.mGradientPath, this.mGradientPaint);
        }
    }

    private void drawPoint(Canvas canvas) {
        canvas.drawCircle(this.pointX, this.pointY, this.pointSize, this.mPointPaint);
    }

    public void setDrawRightLine(boolean drawRightLine2) {
        this.drawRightLine = drawRightLine2;
    }

    public void setPointSize(float pointSize2) {
        if (pointSize2 != 0.0f) {
            this.pointSize = pointSize2;
        }
    }

    public void setLabel(String label2) {
        this.label = label2;
    }

    public void setShowLabel(boolean show) {
        this.showLabel = show;
    }

    public void setLabelAlpha(float alpha) {
        this.labelAlpha = alpha;
    }
}
