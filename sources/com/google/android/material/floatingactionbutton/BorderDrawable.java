package com.google.android.material.floatingactionbutton;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class BorderDrawable extends Drawable {
    private static final float DRAW_STROKE_WIDTH_MULTIPLE = 1.3333f;
    @Nullable
    private ColorStateList borderTint;
    @Dimension
    float borderWidth;
    @ColorInt
    private int bottomInnerStrokeColor;
    @ColorInt
    private int bottomOuterStrokeColor;
    private final RectF boundsRectF = new RectF();
    @ColorInt
    private int currentBorderTintColor;
    private boolean invalidateShader = true;
    @NonNull
    private final Paint paint;
    private final ShapeAppearancePathProvider pathProvider = ShapeAppearancePathProvider.getInstance();
    private final Rect rect = new Rect();
    private final RectF rectF = new RectF();
    private ShapeAppearanceModel shapeAppearanceModel;
    private final Path shapePath = new Path();
    private final BorderState state = new BorderState();
    @ColorInt
    private int topInnerStrokeColor;
    @ColorInt
    private int topOuterStrokeColor;

    BorderDrawable(ShapeAppearanceModel shapeAppearanceModel2) {
        this.shapeAppearanceModel = shapeAppearanceModel2;
        Paint paint2 = new Paint(1);
        this.paint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
    }

    public void setBorderWidth(@Dimension float width) {
        if (this.borderWidth != width) {
            this.borderWidth = width;
            this.paint.setStrokeWidth(DRAW_STROKE_WIDTH_MULTIPLE * width);
            this.invalidateShader = true;
            invalidateSelf();
        }
    }

    /* access modifiers changed from: package-private */
    public void setBorderTint(@Nullable ColorStateList tint) {
        if (tint != null) {
            this.currentBorderTintColor = tint.getColorForState(getState(), this.currentBorderTintColor);
        }
        this.borderTint = tint;
        this.invalidateShader = true;
        invalidateSelf();
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public void setGradientColors(@ColorInt int topOuterStrokeColor2, @ColorInt int topInnerStrokeColor2, @ColorInt int bottomOuterStrokeColor2, @ColorInt int bottomInnerStrokeColor2) {
        this.topOuterStrokeColor = topOuterStrokeColor2;
        this.topInnerStrokeColor = topInnerStrokeColor2;
        this.bottomOuterStrokeColor = bottomOuterStrokeColor2;
        this.bottomInnerStrokeColor = bottomInnerStrokeColor2;
    }

    public void draw(@NonNull Canvas canvas) {
        if (this.invalidateShader) {
            this.paint.setShader(createGradientShader());
            this.invalidateShader = false;
        }
        float halfBorderWidth = this.paint.getStrokeWidth() / 2.0f;
        copyBounds(this.rect);
        this.rectF.set(this.rect);
        float radius = Math.min(this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(getBoundsAsRectF()), this.rectF.width() / 2.0f);
        if (this.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            this.rectF.inset(halfBorderWidth, halfBorderWidth);
            canvas.drawRoundRect(this.rectF, radius, radius, this.paint);
        }
    }

    @TargetApi(21)
    public void getOutline(@NonNull Outline outline) {
        if (this.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            outline.setRoundRect(getBounds(), this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(getBoundsAsRectF()));
            return;
        }
        copyBounds(this.rect);
        this.rectF.set(this.rect);
        this.pathProvider.calculatePath(this.shapeAppearanceModel, 1.0f, this.rectF, this.shapePath);
        if (this.shapePath.isConvex()) {
            outline.setConvexPath(this.shapePath);
        }
    }

    public boolean getPadding(@NonNull Rect padding) {
        if (!this.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            return true;
        }
        int borderWidth2 = Math.round(this.borderWidth);
        padding.set(borderWidth2, borderWidth2, borderWidth2, borderWidth2);
        return true;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public RectF getBoundsAsRectF() {
        this.boundsRectF.set(getBounds());
        return this.boundsRectF;
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel2) {
        this.shapeAppearanceModel = shapeAppearanceModel2;
        invalidateSelf();
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        this.paint.setAlpha(alpha);
        invalidateSelf();
    }

    public int getOpacity() {
        return this.borderWidth > 0.0f ? -3 : -2;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        this.invalidateShader = true;
    }

    public boolean isStateful() {
        ColorStateList colorStateList = this.borderTint;
        return (colorStateList != null && colorStateList.isStateful()) || super.isStateful();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state2) {
        int newColor;
        ColorStateList colorStateList = this.borderTint;
        if (!(colorStateList == null || (newColor = colorStateList.getColorForState(state2, this.currentBorderTintColor)) == this.currentBorderTintColor)) {
            this.invalidateShader = true;
            this.currentBorderTintColor = newColor;
        }
        if (this.invalidateShader != 0) {
            invalidateSelf();
        }
        return this.invalidateShader;
    }

    @NonNull
    private Shader createGradientShader() {
        Rect rect2 = this.rect;
        copyBounds(rect2);
        float borderRatio = this.borderWidth / ((float) rect2.height());
        return new LinearGradient(0.0f, (float) rect2.top, 0.0f, (float) rect2.bottom, new int[]{ColorUtils.compositeColors(this.topOuterStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(this.topInnerStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.topInnerStrokeColor, 0), this.currentBorderTintColor), ColorUtils.compositeColors(ColorUtils.setAlphaComponent(this.bottomInnerStrokeColor, 0), this.currentBorderTintColor), ColorUtils.compositeColors(this.bottomInnerStrokeColor, this.currentBorderTintColor), ColorUtils.compositeColors(this.bottomOuterStrokeColor, this.currentBorderTintColor)}, new float[]{0.0f, borderRatio, 0.5f, 0.5f, 1.0f - borderRatio, 1.0f}, Shader.TileMode.CLAMP);
    }

    @Nullable
    public Drawable.ConstantState getConstantState() {
        return this.state;
    }

    public class BorderState extends Drawable.ConstantState {
        private BorderState() {
        }

        @NonNull
        public Drawable newDrawable() {
            return BorderDrawable.this;
        }

        public int getChangingConfigurations() {
            return 0;
        }
    }
}
