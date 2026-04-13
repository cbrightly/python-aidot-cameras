package com.didichuxing.doraemonkit.kit.alignruler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;

public class AlignLineView extends View {
    private Paint mLinePaint;
    private int mPosX = -1;
    private int mPosY = -1;
    private Paint mTextPaint;

    public AlignLineView(Context context) {
        super(context);
        init();
    }

    public AlignLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AlignLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mLinePaint = paint;
        paint.setAntiAlias(true);
        this.mLinePaint.setColor(getResources().getColor(R.color.dk_color_CC3A4B));
        Paint paint2 = new Paint();
        this.mTextPaint = paint2;
        paint2.setAntiAlias(true);
        this.mTextPaint.setTextSize((float) getResources().getDimensionPixelSize(R.dimen.dk_font_size_14));
        this.mTextPaint.setColor(getResources().getColor(R.color.dk_color_333333));
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        if (this.mPosY != -1 || this.mPosX != -1) {
            int left = this.mPosX;
            int right = getWidth() - this.mPosX;
            int top = this.mPosY;
            int bottom = getHeight() - this.mPosY;
            canvas.drawText(String.valueOf(left), (float) (left / 2), (float) this.mPosY, this.mTextPaint);
            canvas.drawText(String.valueOf(right), (float) ((this.mPosX + getWidth()) / 2), (float) this.mPosY, this.mTextPaint);
            canvas.drawText(String.valueOf(top), (float) this.mPosX, (float) (top / 2), this.mTextPaint);
            canvas.drawText(String.valueOf(bottom), (float) this.mPosX, (float) ((this.mPosY + getHeight()) / 2), this.mTextPaint);
        }
    }

    private void drawLine(Canvas canvas) {
        int i = this.mPosY;
        if (i != -1 || this.mPosX != -1) {
            canvas.drawLine(0.0f, (float) i, (float) getWidth(), (float) this.mPosY, this.mLinePaint);
            int i2 = this.mPosX;
            canvas.drawLine((float) i2, 0.0f, (float) i2, (float) getHeight(), this.mLinePaint);
        }
    }

    public void showInfo(int x, int y) {
        this.mPosX = x;
        this.mPosY = y;
        invalidate();
    }
}
