package com.didichuxing.doraemonkit.kit.colorpick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.ViewCompat;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.util.ColorUtil;

public class ColorPickerView extends View {
    private Matrix mBitmapMatrix = new Matrix();
    private Paint mBitmapPaint;
    private Bitmap mCircleBitmap;
    private Path mClipPath = new Path();
    private Paint mFocusPaint;
    private RoundedBitmapDrawable mGridDrawable;
    private Paint mGridPaint;
    private Rect mGridRect = new Rect();
    private Paint mGridShadowPaint;
    private Paint mRingPaint;
    private String mText;
    private TextPaint mTextPaint;

    public ColorPickerView(Context context) {
        super(context);
        init();
    }

    public ColorPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ColorPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Paint paint = new Paint();
        this.mRingPaint = paint;
        paint.setAntiAlias(true);
        this.mRingPaint.setColor(-1);
        this.mRingPaint.setStyle(Paint.Style.STROKE);
        Paint paint2 = new Paint();
        this.mFocusPaint = paint2;
        paint2.setAntiAlias(true);
        this.mFocusPaint.setStyle(Paint.Style.STROKE);
        this.mFocusPaint.setStrokeWidth(3.0f);
        this.mFocusPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        Paint paint3 = new Paint();
        this.mBitmapPaint = paint3;
        paint3.setFilterBitmap(false);
        Paint paint4 = new Paint();
        this.mGridPaint = paint4;
        paint4.setStrokeWidth(1.0f);
        this.mGridPaint.setStyle(Paint.Style.STROKE);
        this.mGridPaint.setColor(-3355444);
        Paint paint5 = new Paint(this.mGridPaint);
        this.mGridShadowPaint = paint5;
        paint5.setColor(-12303292);
        TextPaint textPaint = new TextPaint();
        this.mTextPaint = textPaint;
        textPaint.setAntiAlias(true);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTextPaint.setTypeface(Typeface.MONOSPACE);
        this.mTextPaint.setTextSize((float) getResources().getDimensionPixelSize(R.dimen.dk_font_size_12));
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mClipPath.rewind();
        this.mClipPath.moveTo(0.0f, 0.0f);
        this.mClipPath.addCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) (getWidth() / 2), Path.Direction.CW);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitmap(canvas);
        drawGrid(canvas);
        drawRing(canvas);
        drawText(canvas);
        drawFocus(canvas);
    }

    private void drawText(Canvas canvas) {
        if (!TextUtils.isEmpty(this.mText)) {
            canvas.drawTextOnPath(this.mText, this.mClipPath, (float) (((double) getWidth()) * 3.141592653589793d * 0.25d), 36.0f - 5.0f, this.mTextPaint);
            canvas.setDrawFilter((DrawFilter) null);
        }
    }

    private void drawGrid(Canvas canvas) {
        if (this.mGridDrawable == null) {
            RoundedBitmapDrawable create = RoundedBitmapDrawableFactory.create(getResources(), createGridBitmap(16, canvas));
            this.mGridDrawable = create;
            create.setBounds(0, 0, getRight(), getBottom());
            this.mGridDrawable.setCircular(true);
        }
        this.mGridDrawable.draw(canvas);
    }

    private Bitmap createGridBitmap(int pixInterval, Canvas canvas) {
        int i = pixInterval;
        int width = getWidth();
        int height = getHeight();
        canvas.getClipBounds(this.mGridRect);
        Bitmap gridBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas gridCanvas = new Canvas(gridBitmap);
        if (i >= 4) {
            int alpha = Math.min(i * 36, 255);
            this.mGridPaint.setAlpha(alpha);
            this.mGridShadowPaint.setAlpha(alpha);
            gridCanvas.save();
            for (int i2 = 0; i2 <= getWidth(); i2 += i) {
                float value = (float) (i2 - 1);
                float end = (float) height;
                gridCanvas.drawLine(value, 0.0f, value, end, this.mGridPaint);
                float value2 = (float) i2;
                gridCanvas.drawLine(value2, 0.0f, value2, end, this.mGridShadowPaint);
            }
            for (int i3 = 0; i3 <= getHeight(); i3 += i) {
                float value3 = (float) (i3 - 1);
                Canvas canvas2 = gridCanvas;
                float f = (float) width;
                canvas2.drawLine(0.0f, value3, f, value3, this.mGridPaint);
                float value4 = (float) i3;
                canvas2.drawLine(0.0f, value4, f, value4, this.mGridShadowPaint);
            }
            gridCanvas.restore();
        }
        return gridBitmap;
    }

    private void drawFocus(Canvas canvas) {
        canvas.drawRect((float) ((getWidth() / 2) - 2), (float) ((getWidth() / 2) - 2), (((float) (getWidth() / 2)) + 20.0f) - 2.0f, (((float) (getWidth() / 2)) + 20.0f) - 2.0f, this.mFocusPaint);
    }

    private void drawRing(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        this.mRingPaint.setStrokeWidth(36.0f);
        canvas.drawCircle((float) (getWidth() / 2), (float) (getWidth() / 2), ((float) (getWidth() / 2)) - (36.0f / 2.0f), this.mRingPaint);
        this.mRingPaint.setColor(getResources().getColor(R.color.dk_color_333333));
        this.mRingPaint.setStrokeWidth(0.5f);
        canvas.drawCircle((float) (getWidth() / 2), (float) (getWidth() / 2), (float) (getWidth() / 2), this.mRingPaint);
        canvas.drawCircle((float) (getWidth() / 2), (float) (getWidth() / 2), ((float) (getWidth() / 2)) - 36.0f, this.mRingPaint);
    }

    private void drawBitmap(Canvas canvas) {
        Bitmap bitmap = this.mCircleBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.save();
            canvas.clipPath(this.mClipPath);
            this.mBitmapMatrix.reset();
            this.mBitmapMatrix.postScale(((float) getWidth()) / ((float) this.mCircleBitmap.getWidth()), ((float) getHeight()) / ((float) this.mCircleBitmap.getHeight()));
            canvas.drawBitmap(this.mCircleBitmap, this.mBitmapMatrix, this.mBitmapPaint);
            canvas.restore();
        }
    }

    public void setBitmap(Bitmap bitmap, int color, int x, int y) {
        this.mCircleBitmap = bitmap;
        this.mText = String.format(ColorPickConstants.TEXT_FOCUS_INFO, new Object[]{ColorUtil.parseColorInt(color), Integer.valueOf(x + 16), Integer.valueOf(y + 16)});
        this.mRingPaint.setColor(color);
        if (ColorUtil.isColdColor(color)) {
            this.mFocusPaint.setColor(-1);
            this.mTextPaint.setColor(-1);
        } else {
            this.mFocusPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
            this.mTextPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        }
        invalidate();
    }
}
