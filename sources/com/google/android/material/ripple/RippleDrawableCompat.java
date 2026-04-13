package com.google.android.material.ripple;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.graphics.drawable.TintAwareDrawable;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class RippleDrawableCompat extends Drawable implements Shapeable, TintAwareDrawable {
    private RippleDrawableCompatState drawableState;

    public RippleDrawableCompat(ShapeAppearanceModel shapeAppearanceModel) {
        this(new RippleDrawableCompatState(new MaterialShapeDrawable(shapeAppearanceModel)));
    }

    private RippleDrawableCompat(RippleDrawableCompatState state) {
        this.drawableState = state;
    }

    public void setTint(@ColorInt int tintColor) {
        this.drawableState.delegate.setTint(tintColor);
    }

    public void setTintMode(@Nullable PorterDuff.Mode tintMode) {
        this.drawableState.delegate.setTintMode(tintMode);
    }

    public void setTintList(@Nullable ColorStateList tintList) {
        this.drawableState.delegate.setTintList(tintList);
    }

    public void setShapeAppearanceModel(@NonNull ShapeAppearanceModel shapeAppearanceModel) {
        this.drawableState.delegate.setShapeAppearanceModel(shapeAppearanceModel);
    }

    @NonNull
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.drawableState.delegate.getShapeAppearanceModel();
    }

    public boolean isStateful() {
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(@NonNull int[] stateSet) {
        boolean changed = super.onStateChange(stateSet);
        if (this.drawableState.delegate.setState(stateSet)) {
            changed = true;
        }
        boolean shouldDrawRipple = RippleUtils.shouldDrawRippleCompat(stateSet);
        RippleDrawableCompatState rippleDrawableCompatState = this.drawableState;
        if (rippleDrawableCompatState.shouldDrawDelegate == shouldDrawRipple) {
            return changed;
        }
        rippleDrawableCompatState.shouldDrawDelegate = shouldDrawRipple;
        return true;
    }

    public void draw(Canvas canvas) {
        RippleDrawableCompatState rippleDrawableCompatState = this.drawableState;
        if (rippleDrawableCompatState.shouldDrawDelegate) {
            rippleDrawableCompatState.delegate.draw(canvas);
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(@NonNull Rect bounds) {
        super.onBoundsChange(bounds);
        this.drawableState.delegate.setBounds(bounds);
    }

    @Nullable
    public Drawable.ConstantState getConstantState() {
        return this.drawableState;
    }

    @NonNull
    public RippleDrawableCompat mutate() {
        this.drawableState = new RippleDrawableCompatState(this.drawableState);
        return this;
    }

    public void setAlpha(int alpha) {
        this.drawableState.delegate.setAlpha(alpha);
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.drawableState.delegate.setColorFilter(colorFilter);
    }

    public int getOpacity() {
        return this.drawableState.delegate.getOpacity();
    }

    public static final class RippleDrawableCompatState extends Drawable.ConstantState {
        @NonNull
        MaterialShapeDrawable delegate;
        boolean shouldDrawDelegate;

        public RippleDrawableCompatState(MaterialShapeDrawable delegate2) {
            this.delegate = delegate2;
            this.shouldDrawDelegate = false;
        }

        public RippleDrawableCompatState(@NonNull RippleDrawableCompatState orig) {
            this.delegate = (MaterialShapeDrawable) orig.delegate.getConstantState().newDrawable();
            this.shouldDrawDelegate = orig.shouldDrawDelegate;
        }

        @NonNull
        public RippleDrawableCompat newDrawable() {
            return new RippleDrawableCompat(new RippleDrawableCompatState(this));
        }

        public int getChangingConfigurations() {
            return 0;
        }
    }
}
