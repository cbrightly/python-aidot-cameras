package com.yalantis.ucrop.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.luck.picture.lib.R;
import com.yalantis.ucrop.callback.OverlayViewChangeListener;
import com.yalantis.ucrop.util.RectUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class OverlayView extends View {
    public static final boolean DEFAULT_CIRCLE_DIMMED_LAYER = false;
    public static final int DEFAULT_CROP_GRID_COLUMN_COUNT = 2;
    public static final int DEFAULT_CROP_GRID_ROW_COUNT = 2;
    public static final boolean DEFAULT_DRAG_FRAME = true;
    public static final int DEFAULT_FREESTYLE_CROP_MODE = 0;
    public static final boolean DEFAULT_SHOW_CROP_FRAME = true;
    public static final boolean DEFAULT_SHOW_CROP_GRID = true;
    public static final int FREESTYLE_CROP_MODE_DISABLE = 0;
    public static final int FREESTYLE_CROP_MODE_ENABLE = 1;
    public static final int FREESTYLE_CROP_MODE_ENABLE_WITH_PASS_THROUGH = 2;
    /* access modifiers changed from: private */
    public OverlayViewChangeListener mCallback;
    private boolean mCircleDimmedLayer;
    private Path mCircularPath;
    private Paint mCropFrameCornersPaint;
    private Paint mCropFramePaint;
    protected float[] mCropGridCenter;
    private int mCropGridColumnCount;
    protected float[] mCropGridCorners;
    private Paint mCropGridPaint;
    private int mCropGridRowCount;
    private int mCropRectCornerTouchAreaLineLength;
    private int mCropRectMinSize;
    /* access modifiers changed from: private */
    public final RectF mCropViewRect;
    private int mCurrentTouchCornerIndex;
    private int mDimmedBorderColor;
    private int mDimmedColor;
    private Paint mDimmedStrokePaint;
    private int mFreestyleCropMode;
    private float[] mGridPoints;
    private boolean mIsDragFrame;
    private float mPreviousTouchX;
    private float mPreviousTouchY;
    private boolean mShouldSetupCropBounds;
    private boolean mShowCropFrame;
    private boolean mShowCropGrid;
    private int mStrokeWidth;
    private float mTargetAspectRatio;
    private final RectF mTempRect;
    protected int mThisHeight;
    protected int mThisWidth;
    private int mTouchPointThreshold;
    private ValueAnimator smoothAnimator;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FreestyleMode {
    }

    public OverlayView(Context context) {
        this(context, (AttributeSet) null);
    }

    public OverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverlayView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCropViewRect = new RectF();
        this.mTempRect = new RectF();
        this.mGridPoints = null;
        this.mCircularPath = new Path();
        this.mDimmedStrokePaint = new Paint(1);
        this.mCropGridPaint = new Paint(1);
        this.mCropFramePaint = new Paint(1);
        this.mCropFrameCornersPaint = new Paint(1);
        this.mFreestyleCropMode = 0;
        this.mPreviousTouchX = -1.0f;
        this.mPreviousTouchY = -1.0f;
        this.mCurrentTouchCornerIndex = -1;
        this.mStrokeWidth = 1;
        this.mIsDragFrame = true;
        this.mTouchPointThreshold = getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_rect_corner_touch_threshold);
        this.mCropRectMinSize = getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_rect_min_size);
        this.mCropRectCornerTouchAreaLineLength = getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_rect_corner_touch_area_line_length);
        init();
    }

    public OverlayViewChangeListener getOverlayViewChangeListener() {
        return this.mCallback;
    }

    public void setOverlayViewChangeListener(OverlayViewChangeListener callback) {
        this.mCallback = callback;
    }

    @NonNull
    public RectF getCropViewRect() {
        return this.mCropViewRect;
    }

    @Deprecated
    public boolean isFreestyleCropEnabled() {
        return this.mFreestyleCropMode == 1;
    }

    @Deprecated
    public void setFreestyleCropEnabled(boolean freestyleCropEnabled) {
        this.mFreestyleCropMode = freestyleCropEnabled;
    }

    public boolean isDragFrame() {
        return this.mIsDragFrame;
    }

    public void setDragFrame(boolean mIsDragFrame2) {
        this.mIsDragFrame = mIsDragFrame2;
    }

    public void setDimmedStrokeWidth(int strokeWidth) {
        this.mStrokeWidth = strokeWidth;
        Paint paint = this.mDimmedStrokePaint;
        if (paint != null) {
            paint.setStrokeWidth((float) strokeWidth);
        }
    }

    public int getFreestyleCropMode() {
        return this.mFreestyleCropMode;
    }

    public void setFreestyleCropMode(int mFreestyleCropMode2) {
        this.mFreestyleCropMode = mFreestyleCropMode2;
        postInvalidate();
    }

    public void setCircleDimmedLayer(boolean circleDimmedLayer) {
        this.mCircleDimmedLayer = circleDimmedLayer;
    }

    public void setCropGridRowCount(@IntRange(from = 0) int cropGridRowCount) {
        this.mCropGridRowCount = cropGridRowCount;
        this.mGridPoints = null;
    }

    public void setCropGridColumnCount(@IntRange(from = 0) int cropGridColumnCount) {
        this.mCropGridColumnCount = cropGridColumnCount;
        this.mGridPoints = null;
    }

    public void setShowCropFrame(boolean showCropFrame) {
        this.mShowCropFrame = showCropFrame;
    }

    public void setShowCropGrid(boolean showCropGrid) {
        this.mShowCropGrid = showCropGrid;
    }

    public void setDimmedColor(@ColorInt int dimmedColor) {
        this.mDimmedColor = dimmedColor;
    }

    public void setCropFrameStrokeWidth(@IntRange(from = 0) int width) {
        this.mCropFramePaint.setStrokeWidth((float) width);
    }

    public void setCropGridStrokeWidth(@IntRange(from = 0) int width) {
        this.mCropGridPaint.setStrokeWidth((float) width);
    }

    public void setDimmedBorderColor(@ColorInt int dimmedBorderColor) {
        this.mDimmedBorderColor = dimmedBorderColor;
        Paint paint = this.mDimmedStrokePaint;
        if (paint != null) {
            paint.setColor(dimmedBorderColor);
        }
    }

    public void setCropFrameColor(@ColorInt int color) {
        this.mCropFramePaint.setColor(color);
    }

    public void setCropGridColor(@ColorInt int color) {
        this.mCropGridPaint.setColor(color);
    }

    public void setTargetAspectRatio(float targetAspectRatio) {
        this.mTargetAspectRatio = targetAspectRatio;
        if (this.mThisWidth > 0) {
            setupCropBounds();
            postInvalidate();
            return;
        }
        this.mShouldSetupCropBounds = true;
    }

    public void setupCropBounds() {
        int i = this.mThisWidth;
        float f = this.mTargetAspectRatio;
        int height = (int) (((float) i) / f);
        int i2 = this.mThisHeight;
        if (height > i2) {
            int width = (int) (((float) i2) * f);
            int halfDiff = (i - width) / 2;
            this.mCropViewRect.set((float) (getPaddingLeft() + halfDiff), (float) getPaddingTop(), (float) (getPaddingLeft() + width + halfDiff), (float) (getPaddingTop() + this.mThisHeight));
        } else {
            int halfDiff2 = (i2 - height) / 2;
            this.mCropViewRect.set((float) getPaddingLeft(), (float) (getPaddingTop() + halfDiff2), (float) (getPaddingLeft() + this.mThisWidth), (float) (getPaddingTop() + height + halfDiff2));
        }
        OverlayViewChangeListener overlayViewChangeListener = this.mCallback;
        if (overlayViewChangeListener != null) {
            overlayViewChangeListener.onCropRectUpdated(this.mCropViewRect);
        }
        updateGridPoints();
    }

    /* access modifiers changed from: private */
    public void updateGridPoints() {
        this.mCropGridCorners = RectUtils.getCornersFromRect(this.mCropViewRect);
        this.mCropGridCenter = RectUtils.getCenterFromRect(this.mCropViewRect);
        this.mGridPoints = null;
        this.mCircularPath.reset();
        this.mCircularPath.addCircle(this.mCropViewRect.centerX(), this.mCropViewRect.centerY(), Math.min(this.mCropViewRect.width(), this.mCropViewRect.height()) / 2.0f, Path.Direction.CW);
    }

    /* access modifiers changed from: protected */
    public void init() {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(1, (Paint) null);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            int left2 = getPaddingLeft();
            int top2 = getPaddingTop();
            int right2 = getWidth() - getPaddingRight();
            int bottom2 = getHeight() - getPaddingBottom();
            this.mThisWidth = right2 - left2;
            this.mThisHeight = bottom2 - top2;
            if (this.mShouldSetupCropBounds) {
                this.mShouldSetupCropBounds = false;
                setTargetAspectRatio(this.mTargetAspectRatio);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDimmedLayer(canvas);
        drawCropGrid(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean shouldHandle = false;
        if (this.mCropViewRect.isEmpty() || this.mFreestyleCropMode == 0) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        if ((event.getAction() & 255) == 0) {
            ValueAnimator valueAnimator = this.smoothAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            int currentTouchIndex = getCurrentTouchIndex(x, y);
            this.mCurrentTouchCornerIndex = currentTouchIndex;
            if (!(currentTouchIndex == -1 || currentTouchIndex == 4)) {
                shouldHandle = true;
            }
            if (!shouldHandle) {
                this.mPreviousTouchX = -1.0f;
                this.mPreviousTouchY = -1.0f;
            } else if (this.mPreviousTouchX < 0.0f) {
                this.mPreviousTouchX = x;
                this.mPreviousTouchY = y;
            }
            return shouldHandle;
        } else if ((event.getAction() & 255) == 2 && event.getPointerCount() == 1 && this.mCurrentTouchCornerIndex != -1) {
            float x2 = Math.min(Math.max(x, (float) getPaddingLeft()), (float) (getWidth() - getPaddingRight()));
            float y2 = Math.min(Math.max(y, (float) getPaddingTop()), (float) (getHeight() - getPaddingBottom()));
            updateCropViewRect(x2, y2);
            this.mPreviousTouchX = x2;
            this.mPreviousTouchY = y2;
            return true;
        } else {
            if ((event.getAction() & 255) == 1) {
                this.mPreviousTouchX = -1.0f;
                this.mPreviousTouchY = -1.0f;
                this.mCurrentTouchCornerIndex = -1;
                OverlayViewChangeListener overlayViewChangeListener = this.mCallback;
                if (overlayViewChangeListener != null) {
                    overlayViewChangeListener.onCropRectUpdated(this.mCropViewRect);
                }
                smoothToCenter();
            }
            return false;
        }
    }

    private void updateCropViewRect(float touchX, float touchY) {
        this.mTempRect.set(this.mCropViewRect);
        switch (this.mCurrentTouchCornerIndex) {
            case 0:
                if (isDragFrame()) {
                    RectF rectF = this.mTempRect;
                    RectF rectF2 = this.mCropViewRect;
                    rectF.set(touchX, touchY, rectF2.right, rectF2.bottom);
                    break;
                }
                break;
            case 1:
                if (isDragFrame()) {
                    RectF rectF3 = this.mTempRect;
                    RectF rectF4 = this.mCropViewRect;
                    rectF3.set(rectF4.left, touchY, touchX, rectF4.bottom);
                    break;
                }
                break;
            case 2:
                if (isDragFrame()) {
                    RectF rectF5 = this.mTempRect;
                    RectF rectF6 = this.mCropViewRect;
                    rectF5.set(rectF6.left, rectF6.top, touchX, touchY);
                    break;
                }
                break;
            case 3:
                if (isDragFrame()) {
                    RectF rectF7 = this.mTempRect;
                    RectF rectF8 = this.mCropViewRect;
                    rectF7.set(touchX, rectF8.top, rectF8.right, touchY);
                    break;
                }
                break;
            case 4:
                this.mTempRect.offset(touchX - this.mPreviousTouchX, touchY - this.mPreviousTouchY);
                if (this.mTempRect.left > ((float) getLeft()) && this.mTempRect.top > ((float) getTop()) && this.mTempRect.right < ((float) getRight()) && this.mTempRect.bottom < ((float) getBottom())) {
                    this.mCropViewRect.set(this.mTempRect);
                    updateGridPoints();
                    postInvalidate();
                    return;
                }
                return;
        }
        boolean changeWidth = true;
        boolean changeHeight = this.mTempRect.height() >= ((float) this.mCropRectMinSize);
        if (this.mTempRect.width() < ((float) this.mCropRectMinSize)) {
            changeWidth = false;
        }
        RectF rectF9 = this.mCropViewRect;
        rectF9.set(changeWidth ? this.mTempRect.left : rectF9.left, changeHeight ? this.mTempRect.top : rectF9.top, changeWidth ? this.mTempRect.right : rectF9.right, changeHeight ? this.mTempRect.bottom : rectF9.bottom);
        if (changeHeight || changeWidth) {
            updateGridPoints();
            postInvalidate();
        }
    }

    private int getCurrentTouchIndex(float touchX, float touchY) {
        int closestPointIndex = -1;
        double closestPointDistance = (double) this.mTouchPointThreshold;
        for (int i = 0; i < 8; i += 2) {
            double distanceToCorner = Math.sqrt(Math.pow((double) (touchX - this.mCropGridCorners[i]), 2.0d) + Math.pow((double) (touchY - this.mCropGridCorners[i + 1]), 2.0d));
            if (distanceToCorner < closestPointDistance) {
                closestPointDistance = distanceToCorner;
                closestPointIndex = i / 2;
            }
        }
        if (this.mFreestyleCropMode != 1 || closestPointIndex >= 0 || !this.mCropViewRect.contains(touchX, touchY)) {
            return closestPointIndex;
        }
        return 4;
    }

    /* access modifiers changed from: protected */
    public void drawDimmedLayer(@NonNull Canvas canvas) {
        canvas.save();
        if (this.mCircleDimmedLayer) {
            canvas.clipPath(this.mCircularPath, Region.Op.DIFFERENCE);
        } else {
            canvas.clipRect(this.mCropViewRect, Region.Op.DIFFERENCE);
        }
        canvas.drawColor(this.mDimmedColor);
        canvas.restore();
        if (this.mCircleDimmedLayer) {
            canvas.drawCircle(this.mCropViewRect.centerX(), this.mCropViewRect.centerY(), Math.min(this.mCropViewRect.width(), this.mCropViewRect.height()) / 2.0f, this.mDimmedStrokePaint);
        }
    }

    /* access modifiers changed from: protected */
    public void drawCropGrid(@NonNull Canvas canvas) {
        if (this.mShowCropGrid) {
            if (this.mGridPoints == null && !this.mCropViewRect.isEmpty()) {
                this.mGridPoints = new float[((this.mCropGridRowCount * 4) + (this.mCropGridColumnCount * 4))];
                int index = 0;
                for (int i = 0; i < this.mCropGridRowCount; i++) {
                    float[] fArr = this.mGridPoints;
                    int index2 = index + 1;
                    RectF rectF = this.mCropViewRect;
                    fArr[index] = rectF.left;
                    int index3 = index2 + 1;
                    float height = rectF.height() * ((((float) i) + 1.0f) / ((float) (this.mCropGridRowCount + 1)));
                    RectF rectF2 = this.mCropViewRect;
                    fArr[index2] = height + rectF2.top;
                    float[] fArr2 = this.mGridPoints;
                    int index4 = index3 + 1;
                    fArr2[index3] = rectF2.right;
                    index = index4 + 1;
                    fArr2[index4] = (rectF2.height() * ((((float) i) + 1.0f) / ((float) (this.mCropGridRowCount + 1)))) + this.mCropViewRect.top;
                }
                int i2 = 0;
                while (i2 < this.mCropGridColumnCount) {
                    float[] fArr3 = this.mGridPoints;
                    int index5 = index + 1;
                    float width = this.mCropViewRect.width() * ((((float) i2) + 1.0f) / ((float) (this.mCropGridColumnCount + 1)));
                    RectF rectF3 = this.mCropViewRect;
                    fArr3[index] = width + rectF3.left;
                    float[] fArr4 = this.mGridPoints;
                    int index6 = index5 + 1;
                    fArr4[index5] = rectF3.top;
                    int index7 = index6 + 1;
                    float width2 = rectF3.width() * ((((float) i2) + 1.0f) / ((float) (this.mCropGridColumnCount + 1)));
                    RectF rectF4 = this.mCropViewRect;
                    fArr4[index6] = width2 + rectF4.left;
                    this.mGridPoints[index7] = rectF4.bottom;
                    i2++;
                    index = index7 + 1;
                }
            }
            float[] fArr5 = this.mGridPoints;
            if (fArr5 != null) {
                canvas.drawLines(fArr5, this.mCropGridPaint);
            }
        }
        if (this.mShowCropFrame) {
            canvas.drawRect(this.mCropViewRect, this.mCropFramePaint);
        }
        if (this.mFreestyleCropMode != 0) {
            canvas.save();
            this.mTempRect.set(this.mCropViewRect);
            RectF rectF5 = this.mTempRect;
            int i3 = this.mCropRectCornerTouchAreaLineLength;
            rectF5.inset((float) i3, (float) (-i3));
            canvas.clipRect(this.mTempRect, Region.Op.DIFFERENCE);
            this.mTempRect.set(this.mCropViewRect);
            RectF rectF6 = this.mTempRect;
            int i4 = this.mCropRectCornerTouchAreaLineLength;
            rectF6.inset((float) (-i4), (float) i4);
            canvas.clipRect(this.mTempRect, Region.Op.DIFFERENCE);
            canvas.drawRect(this.mCropViewRect, this.mCropFrameCornersPaint);
            canvas.restore();
        }
    }

    /* access modifiers changed from: protected */
    public void processStyledAttributes(@NonNull TypedArray a) {
        this.mCircleDimmedLayer = a.getBoolean(R.styleable.ucrop_UCropView_ucrop_circle_dimmed_layer, false);
        this.mDimmedColor = a.getColor(R.styleable.ucrop_UCropView_ucrop_dimmed_color, getResources().getColor(R.color.ucrop_color_default_dimmed));
        this.mDimmedStrokePaint.setColor(this.mDimmedBorderColor);
        this.mDimmedStrokePaint.setStyle(Paint.Style.STROKE);
        this.mDimmedStrokePaint.setStrokeWidth((float) this.mStrokeWidth);
        initCropFrameStyle(a);
        this.mShowCropFrame = a.getBoolean(R.styleable.ucrop_UCropView_ucrop_show_frame, true);
        initCropGridStyle(a);
        this.mShowCropGrid = a.getBoolean(R.styleable.ucrop_UCropView_ucrop_show_grid, true);
    }

    private void initCropFrameStyle(@NonNull TypedArray a) {
        int cropFrameStrokeSize = a.getDimensionPixelSize(R.styleable.ucrop_UCropView_ucrop_frame_stroke_size, getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_frame_stoke_width));
        int cropFrameColor = a.getColor(R.styleable.ucrop_UCropView_ucrop_frame_color, getResources().getColor(R.color.ucrop_color_default_crop_frame));
        this.mCropFramePaint.setStrokeWidth((float) cropFrameStrokeSize);
        this.mCropFramePaint.setColor(cropFrameColor);
        this.mCropFramePaint.setStyle(Paint.Style.STROKE);
        this.mCropFrameCornersPaint.setStrokeWidth((float) (cropFrameStrokeSize * 3));
        this.mCropFrameCornersPaint.setColor(cropFrameColor);
        this.mCropFrameCornersPaint.setStyle(Paint.Style.STROKE);
    }

    private void initCropGridStyle(@NonNull TypedArray a) {
        int cropGridStrokeSize = a.getDimensionPixelSize(R.styleable.ucrop_UCropView_ucrop_grid_stroke_size, getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_grid_stoke_width));
        int cropGridColor = a.getColor(R.styleable.ucrop_UCropView_ucrop_grid_color, getResources().getColor(R.color.ucrop_color_default_crop_grid));
        this.mCropGridPaint.setStrokeWidth((float) cropGridStrokeSize);
        this.mCropGridPaint.setColor(cropGridColor);
        this.mCropGridRowCount = a.getInt(R.styleable.ucrop_UCropView_ucrop_grid_row_count, 2);
        this.mCropGridColumnCount = a.getInt(R.styleable.ucrop_UCropView_ucrop_grid_column_count, 2);
    }

    private void smoothToCenter() {
        Point centerPoint = new Point((getRight() + getLeft()) / 2, (getTop() + getBottom()) / 2);
        final int offsetY = (int) (((float) centerPoint.y) - this.mCropViewRect.centerY());
        final int offsetX = (int) (((float) centerPoint.x) - this.mCropViewRect.centerX());
        final RectF before = new RectF(this.mCropViewRect);
        Log.d("pisa", "pre" + this.mCropViewRect);
        RectF after = new RectF(this.mCropViewRect);
        after.offset((float) offsetX, (float) offsetY);
        Log.d("pisa", "after" + after);
        ValueAnimator valueAnimator = this.smoothAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        this.smoothAnimator = ofFloat;
        ofFloat.setDuration(1000);
        this.smoothAnimator.setInterpolator(new OvershootInterpolator());
        this.smoothAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float lastAnimationValue = 0.0f;

            public void onAnimationUpdate(ValueAnimator animation) {
                float x = ((float) offsetX) * ((Float) animation.getAnimatedValue()).floatValue();
                float y = ((float) offsetY) * ((Float) animation.getAnimatedValue()).floatValue();
                RectF access$000 = OverlayView.this.mCropViewRect;
                RectF rectF = before;
                access$000.set(new RectF(rectF.left + x, rectF.top + y, rectF.right + x, rectF.bottom + y));
                OverlayView.this.updateGridPoints();
                OverlayView.this.postInvalidate();
                if (OverlayView.this.mCallback != null) {
                    OverlayView.this.mCallback.postTranslate(((float) offsetX) * (((Float) animation.getAnimatedValue()).floatValue() - this.lastAnimationValue), ((float) offsetY) * (((Float) animation.getAnimatedValue()).floatValue() - this.lastAnimationValue));
                }
                this.lastAnimationValue = ((Float) animation.getAnimatedValue()).floatValue();
            }
        });
        this.smoothAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                if (OverlayView.this.mCallback != null) {
                    OverlayView.this.mCallback.onCropRectUpdated(OverlayView.this.mCropViewRect);
                }
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        this.smoothAnimator.start();
    }
}
