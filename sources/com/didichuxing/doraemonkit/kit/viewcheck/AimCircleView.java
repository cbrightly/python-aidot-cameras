package com.didichuxing.doraemonkit.kit.viewcheck;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;

public class AimCircleView extends View {
    private Paint mPaint;

    public AimCircleView(Context context) {
        super(context);
        init();
    }

    public AimCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AimCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        float cx = (float) (getWidth() / 2);
        float cy = (float) (getWidth() / 2);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(getResources().getColor(R.color.dk_color_FFFFFF));
        canvas.drawCircle(cx, cy, (float) (getWidth() / 2), this.mPaint);
        float radius = (float) (getResources().getDimensionPixelSize(R.dimen.dk_dp_40) / 2);
        this.mPaint.setColor(getResources().getColor(R.color.dk_color_30CC3A4B));
        canvas.drawCircle(cx, cy, radius, this.mPaint);
        float radius2 = (float) (getResources().getDimensionPixelSize(R.dimen.dk_dp_20) / 2);
        this.mPaint.setColor(getResources().getColor(R.color.dk_color_CC3A4B));
        canvas.drawCircle(cx, cy, radius2, this.mPaint);
        float radius3 = (float) (getWidth() / 2);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(4.0f);
        this.mPaint.setColor(getResources().getColor(R.color.dk_color_337CC4));
        canvas.drawCircle(cx, cy, radius3 - 2.0f, this.mPaint);
    }
}
