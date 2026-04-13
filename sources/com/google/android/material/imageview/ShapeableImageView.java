package com.google.android.material.imageview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import com.google.android.material.R;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.Shapeable;

public class ShapeableImageView extends AppCompatImageView implements Shapeable {
    private static final int DEF_STYLE_RES = R.style.Widget_MaterialComponents_ShapeableImageView;
    private static final int UNDEFINED_PADDING = Integer.MIN_VALUE;
    private final Paint borderPaint;
    @Dimension
    private int bottomContentPadding;
    private final Paint clearPaint;
    /* access modifiers changed from: private */
    public final RectF destination;
    @Dimension
    private int endContentPadding;
    private boolean hasAdjustedPaddingAfterLayoutDirectionResolved;
    @Dimension
    private int leftContentPadding;
    private Path maskPath;
    private final RectF maskRect;
    private final Path path;
    private final ShapeAppearancePathProvider pathProvider;
    @Dimension
    private int rightContentPadding;
    /* access modifiers changed from: private */
    @Nullable
    public MaterialShapeDrawable shadowDrawable;
    /* access modifiers changed from: private */
    public ShapeAppearanceModel shapeAppearanceModel;
    @Dimension
    private int startContentPadding;
    @Nullable
    private ColorStateList strokeColor;
    @Dimension
    private float strokeWidth;
    @Dimension
    private int topContentPadding;

    public ShapeableImageView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public ShapeableImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ShapeableImageView(android.content.Context r7, @androidx.annotation.Nullable android.util.AttributeSet r8, int r9) {
        /*
            r6 = this;
            int r0 = DEF_STYLE_RES
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r7, r8, r9, r0)
            r6.<init>(r1, r8, r9)
            com.google.android.material.shape.ShapeAppearancePathProvider r1 = com.google.android.material.shape.ShapeAppearancePathProvider.getInstance()
            r6.pathProvider = r1
            android.graphics.Path r1 = new android.graphics.Path
            r1.<init>()
            r6.path = r1
            r1 = 0
            r6.hasAdjustedPaddingAfterLayoutDirectionResolved = r1
            android.content.Context r7 = r6.getContext()
            android.graphics.Paint r2 = new android.graphics.Paint
            r2.<init>()
            r6.clearPaint = r2
            r3 = 1
            r2.setAntiAlias(r3)
            r4 = -1
            r2.setColor(r4)
            android.graphics.PorterDuffXfermode r4 = new android.graphics.PorterDuffXfermode
            android.graphics.PorterDuff$Mode r5 = android.graphics.PorterDuff.Mode.DST_OUT
            r4.<init>(r5)
            r2.setXfermode(r4)
            android.graphics.RectF r2 = new android.graphics.RectF
            r2.<init>()
            r6.destination = r2
            android.graphics.RectF r2 = new android.graphics.RectF
            r2.<init>()
            r6.maskRect = r2
            android.graphics.Path r2 = new android.graphics.Path
            r2.<init>()
            r6.maskPath = r2
            int[] r2 = com.google.android.material.R.styleable.ShapeableImageView
            android.content.res.TypedArray r2 = r7.obtainStyledAttributes(r8, r2, r9, r0)
            int r4 = com.google.android.material.R.styleable.ShapeableImageView_strokeColor
            android.content.res.ColorStateList r4 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r7, (android.content.res.TypedArray) r2, (int) r4)
            r6.strokeColor = r4
            int r4 = com.google.android.material.R.styleable.ShapeableImageView_strokeWidth
            int r4 = r2.getDimensionPixelSize(r4, r1)
            float r4 = (float) r4
            r6.strokeWidth = r4
            int r4 = com.google.android.material.R.styleable.ShapeableImageView_contentPadding
            int r1 = r2.getDimensionPixelSize(r4, r1)
            r6.leftContentPadding = r1
            r6.topContentPadding = r1
            r6.rightContentPadding = r1
            r6.bottomContentPadding = r1
            int r4 = com.google.android.material.R.styleable.ShapeableImageView_contentPaddingLeft
            int r4 = r2.getDimensionPixelSize(r4, r1)
            r6.leftContentPadding = r4
            int r4 = com.google.android.material.R.styleable.ShapeableImageView_contentPaddingTop
            int r4 = r2.getDimensionPixelSize(r4, r1)
            r6.topContentPadding = r4
            int r4 = com.google.android.material.R.styleable.ShapeableImageView_contentPaddingRight
            int r4 = r2.getDimensionPixelSize(r4, r1)
            r6.rightContentPadding = r4
            int r4 = com.google.android.material.R.styleable.ShapeableImageView_contentPaddingBottom
            int r4 = r2.getDimensionPixelSize(r4, r1)
            r6.bottomContentPadding = r4
            int r4 = com.google.android.material.R.styleable.ShapeableImageView_contentPaddingStart
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            int r4 = r2.getDimensionPixelSize(r4, r5)
            r6.startContentPadding = r4
            int r4 = com.google.android.material.R.styleable.ShapeableImageView_contentPaddingEnd
            int r4 = r2.getDimensionPixelSize(r4, r5)
            r6.endContentPadding = r4
            r2.recycle()
            android.graphics.Paint r4 = new android.graphics.Paint
            r4.<init>()
            r6.borderPaint = r4
            android.graphics.Paint$Style r5 = android.graphics.Paint.Style.STROKE
            r4.setStyle(r5)
            r4.setAntiAlias(r3)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r0 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r7, (android.util.AttributeSet) r8, (int) r9, (int) r0)
            com.google.android.material.shape.ShapeAppearanceModel r0 = r0.build()
            r6.shapeAppearanceModel = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            r3 = 21
            if (r0 < r3) goto L_0x00ce
            com.google.android.material.imageview.ShapeableImageView$OutlineProvider r0 = new com.google.android.material.imageview.ShapeableImageView$OutlineProvider
            r0.<init>()
            r6.setOutlineProvider(r0)
        L_0x00ce:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.imageview.ShapeableImageView.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        setLayerType(0, (Paint) null);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setLayerType(2, (Paint) null);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!this.hasAdjustedPaddingAfterLayoutDirectionResolved) {
            int i = Build.VERSION.SDK_INT;
            if (i <= 19 || isLayoutDirectionResolved()) {
                this.hasAdjustedPaddingAfterLayoutDirectionResolved = true;
                if (i < 21 || (!isPaddingRelative() && !isContentPaddingRelative())) {
                    setPadding(super.getPaddingLeft(), super.getPaddingTop(), super.getPaddingRight(), super.getPaddingBottom());
                } else {
                    setPaddingRelative(super.getPaddingStart(), super.getPaddingTop(), super.getPaddingEnd(), super.getPaddingBottom());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.maskPath, this.clearPaint);
        drawStroke(canvas);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        updateShapeMask(width, height);
    }

    public void setContentPadding(@Dimension int left, @Dimension int top, @Dimension int right, @Dimension int bottom) {
        this.startContentPadding = Integer.MIN_VALUE;
        this.endContentPadding = Integer.MIN_VALUE;
        super.setPadding((super.getPaddingLeft() - this.leftContentPadding) + left, (super.getPaddingTop() - this.topContentPadding) + top, (super.getPaddingRight() - this.rightContentPadding) + right, (super.getPaddingBottom() - this.bottomContentPadding) + bottom);
        this.leftContentPadding = left;
        this.topContentPadding = top;
        this.rightContentPadding = right;
        this.bottomContentPadding = bottom;
    }

    @RequiresApi(17)
    public void setContentPaddingRelative(@Dimension int start, @Dimension int top, @Dimension int end, @Dimension int bottom) {
        super.setPaddingRelative((super.getPaddingStart() - getContentPaddingStart()) + start, (super.getPaddingTop() - this.topContentPadding) + top, (super.getPaddingEnd() - getContentPaddingEnd()) + end, (super.getPaddingBottom() - this.bottomContentPadding) + bottom);
        this.leftContentPadding = isRtl() ? end : start;
        this.topContentPadding = top;
        this.rightContentPadding = isRtl() ? start : end;
        this.bottomContentPadding = bottom;
    }

    private boolean isContentPaddingRelative() {
        return (this.startContentPadding == Integer.MIN_VALUE && this.endContentPadding == Integer.MIN_VALUE) ? false : true;
    }

    @Dimension
    public int getContentPaddingBottom() {
        return this.bottomContentPadding;
    }

    @Dimension
    public final int getContentPaddingEnd() {
        int i = this.endContentPadding;
        if (i != Integer.MIN_VALUE) {
            return i;
        }
        return isRtl() ? this.leftContentPadding : this.rightContentPadding;
    }

    @Dimension
    public int getContentPaddingLeft() {
        int i;
        int i2;
        if (isContentPaddingRelative()) {
            if (isRtl() && (i2 = this.endContentPadding) != Integer.MIN_VALUE) {
                return i2;
            }
            if (!isRtl() && (i = this.startContentPadding) != Integer.MIN_VALUE) {
                return i;
            }
        }
        return this.leftContentPadding;
    }

    @Dimension
    public int getContentPaddingRight() {
        int i;
        int i2;
        if (isContentPaddingRelative()) {
            if (isRtl() && (i2 = this.startContentPadding) != Integer.MIN_VALUE) {
                return i2;
            }
            if (!isRtl() && (i = this.endContentPadding) != Integer.MIN_VALUE) {
                return i;
            }
        }
        return this.rightContentPadding;
    }

    @Dimension
    public final int getContentPaddingStart() {
        int i = this.startContentPadding;
        if (i != Integer.MIN_VALUE) {
            return i;
        }
        return isRtl() ? this.rightContentPadding : this.leftContentPadding;
    }

    @Dimension
    public int getContentPaddingTop() {
        return this.topContentPadding;
    }

    private boolean isRtl() {
        return Build.VERSION.SDK_INT >= 17 && getLayoutDirection() == 1;
    }

    public void setPadding(@Dimension int left, @Dimension int top, @Dimension int right, @Dimension int bottom) {
        super.setPadding(getContentPaddingLeft() + left, getContentPaddingTop() + top, getContentPaddingRight() + right, getContentPaddingBottom() + bottom);
    }

    public void setPaddingRelative(@Dimension int start, @Dimension int top, @Dimension int end, @Dimension int bottom) {
        super.setPaddingRelative(getContentPaddingStart() + start, getContentPaddingTop() + top, getContentPaddingEnd() + end, getContentPaddingBottom() + bottom);
    }

    @Dimension
    public int getPaddingBottom() {
        return super.getPaddingBottom() - getContentPaddingBottom();
    }

    @Dimension
    public int getPaddingEnd() {
        return super.getPaddingEnd() - getContentPaddingEnd();
    }

    @Dimension
    public int getPaddingLeft() {
        return super.getPaddingLeft() - getContentPaddingLeft();
    }

    @Dimension
    public int getPaddingRight() {
        return super.getPaddingRight() - getContentPaddingRight();
    }

    @Dimension
    public int getPaddingStart() {
        return super.getPaddingStart() - getContentPaddingStart();
    }

    @Dimension
    public int getPaddingTop() {
        return super.getPaddingTop() - getContentPaddingTop();
    }

    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel2) {
        this.shapeAppearanceModel = shapeAppearanceModel2;
        MaterialShapeDrawable materialShapeDrawable = this.shadowDrawable;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel2);
        }
        updateShapeMask(getWidth(), getHeight());
        invalidate();
        if (Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    private void updateShapeMask(int width, int height) {
        this.destination.set((float) getPaddingLeft(), (float) getPaddingTop(), (float) (width - getPaddingRight()), (float) (height - getPaddingBottom()));
        this.pathProvider.calculatePath(this.shapeAppearanceModel, 1.0f, this.destination, this.path);
        this.maskPath.rewind();
        this.maskPath.addPath(this.path);
        this.maskRect.set(0.0f, 0.0f, (float) width, (float) height);
        this.maskPath.addRect(this.maskRect, Path.Direction.CCW);
    }

    private void drawStroke(Canvas canvas) {
        if (this.strokeColor != null) {
            this.borderPaint.setStrokeWidth(this.strokeWidth);
            int colorForState = this.strokeColor.getColorForState(getDrawableState(), this.strokeColor.getDefaultColor());
            if (this.strokeWidth > 0.0f && colorForState != 0) {
                this.borderPaint.setColor(colorForState);
                canvas.drawPath(this.path, this.borderPaint);
            }
        }
    }

    public void setStrokeColorResource(@ColorRes int strokeColorResourceId) {
        setStrokeColor(AppCompatResources.getColorStateList(getContext(), strokeColorResourceId));
    }

    @Nullable
    public ColorStateList getStrokeColor() {
        return this.strokeColor;
    }

    public void setStrokeWidth(@Dimension float strokeWidth2) {
        if (this.strokeWidth != strokeWidth2) {
            this.strokeWidth = strokeWidth2;
            invalidate();
        }
    }

    public void setStrokeWidthResource(@DimenRes int strokeWidthResourceId) {
        setStrokeWidth((float) getResources().getDimensionPixelSize(strokeWidthResourceId));
    }

    @Dimension
    public float getStrokeWidth() {
        return this.strokeWidth;
    }

    public void setStrokeColor(@Nullable ColorStateList strokeColor2) {
        this.strokeColor = strokeColor2;
        invalidate();
    }

    @TargetApi(21)
    public class OutlineProvider extends ViewOutlineProvider {
        private final Rect rect = new Rect();

        OutlineProvider() {
        }

        public void getOutline(View view, Outline outline) {
            if (ShapeableImageView.this.shapeAppearanceModel != null) {
                if (ShapeableImageView.this.shadowDrawable == null) {
                    MaterialShapeDrawable unused = ShapeableImageView.this.shadowDrawable = new MaterialShapeDrawable(ShapeableImageView.this.shapeAppearanceModel);
                }
                ShapeableImageView.this.destination.round(this.rect);
                ShapeableImageView.this.shadowDrawable.setBounds(this.rect);
                ShapeableImageView.this.shadowDrawable.getOutline(outline);
            }
        }
    }
}
