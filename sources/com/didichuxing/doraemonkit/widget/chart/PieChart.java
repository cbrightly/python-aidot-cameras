package com.didichuxing.doraemonkit.widget.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.util.UIUtils;
import java.util.List;

public class PieChart extends View {
    private static final float DEFAULT_RING_WIDTH = 19.5f;
    private static final float DEFAULT_SLICE_SPACE = 2.0f;
    private double DEG2RAD = 0.017453292519943295d;
    private float FDEG2RAD = 0.017453292f;
    private int mHeight;
    private Path mPath = new Path();
    private List<PieData> mPieData;
    private Paint mRenderPaint;
    private float mRingWidth;
    private float mSliceSpace;
    protected Paint mTransparentCirclePaint;
    private int mWidth;

    public PieChart(Context context) {
        super(context);
        setRingWidth(DEFAULT_RING_WIDTH);
        setSliceSpace(2.0f);
        initPaint();
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setRingWidth(DEFAULT_RING_WIDTH);
        setSliceSpace(2.0f);
        initPaint();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPieData != null) {
            drawPie(canvas);
            drawHolo(canvas);
        }
    }

    private void initPaint() {
        Paint paint = new Paint(1);
        this.mRenderPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(1);
        this.mTransparentCirclePaint = paint2;
        paint2.setColor(-1);
        this.mTransparentCirclePaint.setStyle(Paint.Style.FILL);
    }

    private void drawPie(Canvas canvas) {
        float sweepAngleOuter;
        int i;
        RectF rectF = new RectF(0.0f, 0.0f, (float) this.mWidth, (float) this.mHeight);
        Center center = new Center();
        int i2 = this.mWidth;
        center.x = ((float) i2) / 2.0f;
        center.y = ((float) this.mHeight) / 2.0f;
        float radius = ((float) i2) / 2.0f;
        float sliceSpaceAngleOuter = this.mPieData.size() == 1 ? 0.0f : this.mSliceSpace / (this.FDEG2RAD * radius);
        float angle = 0.0f;
        int i3 = 0;
        while (i3 < this.mPieData.size()) {
            PieData data = this.mPieData.get(i3);
            this.mRenderPaint.setColor(data.color);
            this.mPath.reset();
            float sliceAngle = data.angel;
            float startAngleOuter = ((sliceSpaceAngleOuter / 2.0f) + angle) - 90.0f;
            float sweepAngleOuter2 = sliceAngle - sliceSpaceAngleOuter;
            if (sweepAngleOuter2 < 0.0f) {
                sweepAngleOuter = 0.0f;
            } else {
                sweepAngleOuter = sweepAngleOuter2;
            }
            float angle2 = angle + sliceAngle;
            if (sweepAngleOuter >= 360.0f) {
                this.mPath.addCircle(center.x, center.y, radius, Path.Direction.CW);
                float f = sweepAngleOuter;
                float f2 = startAngleOuter;
                PieData pieData = data;
                i = i3;
            } else {
                this.mPath.arcTo(rectF, startAngleOuter, sweepAngleOuter);
                float angleMiddle = startAngleOuter + (sweepAngleOuter / 2.0f);
                PieData pieData2 = data;
                i = i3;
                float sliceSpaceOffset = calculateMinimumRadiusForSpacedSlice(center, radius, sliceAngle, center.x + (((float) Math.cos((double) (this.FDEG2RAD * startAngleOuter))) * radius), center.y + (((float) Math.sin((double) (this.FDEG2RAD * startAngleOuter))) * radius), startAngleOuter, sweepAngleOuter);
                this.mPath.lineTo(center.x + (((float) Math.cos((double) (this.FDEG2RAD * angleMiddle))) * sliceSpaceOffset), center.y + (((float) Math.sin((double) (this.FDEG2RAD * angleMiddle))) * sliceSpaceOffset));
            }
            canvas.drawPath(this.mPath, this.mRenderPaint);
            i3 = i + 1;
            angle = angle2;
        }
    }

    private void drawHolo(Canvas canvas) {
        int i = this.mWidth;
        canvas.drawCircle(((float) i) / 2.0f, ((float) this.mHeight) / 2.0f, (((float) i) / 2.0f) - this.mRingWidth, this.mTransparentCirclePaint);
    }

    /* access modifiers changed from: protected */
    public float calculateMinimumRadiusForSpacedSlice(Center center, float radius, float angle, float arcStartPointX, float arcStartPointY, float startAngle, float sweepAngle) {
        Center center2 = center;
        float angleMiddle = startAngle + (sweepAngle / 2.0f);
        float arcEndPointX = center2.x + (((float) Math.cos((double) ((startAngle + sweepAngle) * this.FDEG2RAD))) * radius);
        float arcEndPointY = center2.y + (((float) Math.sin((double) ((startAngle + sweepAngle) * this.FDEG2RAD))) * radius);
        float arcMidPointX = center2.x + (((float) Math.cos((double) (this.FDEG2RAD * angleMiddle))) * radius);
        float arcMidPointY = center2.y + (((float) Math.sin((double) (this.FDEG2RAD * angleMiddle))) * radius);
        float f = angleMiddle;
        float containedTriangleHeight = (float) ((Math.sqrt(Math.pow((double) (arcEndPointX - arcStartPointX), 2.0d) + Math.pow((double) (arcEndPointY - arcStartPointY), 2.0d)) / 2.0d) * Math.tan(((180.0d - ((double) angle)) / 2.0d) * this.DEG2RAD));
        float spacedRadius = radius - containedTriangleHeight;
        float f2 = containedTriangleHeight;
        float f3 = spacedRadius;
        return (float) (((double) spacedRadius) - Math.sqrt(Math.pow((double) (arcMidPointX - ((arcEndPointX + arcStartPointX) / 2.0f)), 2.0d) + Math.pow((double) (arcMidPointY - ((arcEndPointY + arcStartPointY) / 2.0f)), 2.0d)));
    }

    public void setSliceSpace(float space) {
        this.mSliceSpace = (float) UIUtils.dp2px(space);
    }

    public void setRingWidth(float ringWidth) {
        this.mRingWidth = (float) UIUtils.dp2px(ringWidth);
    }

    public void setData(List<PieData> pieData) {
        this.mPieData = pieData;
        int weightSum = 0;
        for (PieData data : pieData) {
            weightSum = (int) (((long) weightSum) + data.weight);
        }
        for (PieData data2 : pieData) {
            if (weightSum == 0) {
                float unused = data2.angel = (float) (360 / pieData.size());
            } else {
                float unused2 = data2.angel = (((float) data2.weight) * 360.0f) / ((float) weightSum);
            }
        }
    }

    public static class PieData {
        /* access modifiers changed from: private */
        public float angel;
        public final int color;
        public final long weight;

        public PieData(int color2, long weight2) {
            this.color = color2;
            this.weight = weight2;
        }
    }

    public class Center {
        public float x;
        public float y;

        private Center() {
        }
    }
}
