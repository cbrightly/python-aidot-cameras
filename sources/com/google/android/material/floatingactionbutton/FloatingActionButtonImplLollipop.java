package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.util.Preconditions;
import com.google.android.material.R;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shadow.ShadowViewDelegate;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(21)
public class FloatingActionButtonImplLollipop extends FloatingActionButtonImpl {
    FloatingActionButtonImplLollipop(FloatingActionButton view, ShadowViewDelegate shadowViewDelegate) {
        super(view, shadowViewDelegate);
    }

    /* access modifiers changed from: package-private */
    public void initializeBackgroundDrawable(ColorStateList backgroundTint, @Nullable PorterDuff.Mode backgroundTintMode, ColorStateList rippleColor, int borderWidth) {
        Drawable rippleContent;
        MaterialShapeDrawable createShapeDrawable = createShapeDrawable();
        this.shapeDrawable = createShapeDrawable;
        createShapeDrawable.setTintList(backgroundTint);
        if (backgroundTintMode != null) {
            this.shapeDrawable.setTintMode(backgroundTintMode);
        }
        this.shapeDrawable.initializeElevationOverlay(this.view.getContext());
        if (borderWidth > 0) {
            this.borderDrawable = createBorderDrawable(borderWidth, backgroundTint);
            rippleContent = new LayerDrawable(new Drawable[]{(Drawable) Preconditions.checkNotNull(this.borderDrawable), (Drawable) Preconditions.checkNotNull(this.shapeDrawable)});
        } else {
            this.borderDrawable = null;
            rippleContent = this.shapeDrawable;
        }
        RippleDrawable rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(rippleColor), rippleContent, (Drawable) null);
        this.rippleDrawable = rippleDrawable;
        this.contentBackground = rippleDrawable;
    }

    /* access modifiers changed from: package-private */
    public void setRippleColor(@Nullable ColorStateList rippleColor) {
        Drawable drawable = this.rippleDrawable;
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable) drawable).setColor(RippleUtils.sanitizeRippleDrawableColor(rippleColor));
        } else {
            super.setRippleColor(rippleColor);
        }
    }

    /* access modifiers changed from: package-private */
    public void onElevationsChanged(float elevation, float hoveredFocusedTranslationZ, float pressedTranslationZ) {
        int i = Build.VERSION.SDK_INT;
        if (i == 21) {
            this.view.refreshDrawableState();
        } else {
            StateListAnimator stateListAnimator = new StateListAnimator();
            stateListAnimator.addState(FloatingActionButtonImpl.PRESSED_ENABLED_STATE_SET, createElevationAnimator(elevation, pressedTranslationZ));
            stateListAnimator.addState(FloatingActionButtonImpl.HOVERED_FOCUSED_ENABLED_STATE_SET, createElevationAnimator(elevation, hoveredFocusedTranslationZ));
            stateListAnimator.addState(FloatingActionButtonImpl.FOCUSED_ENABLED_STATE_SET, createElevationAnimator(elevation, hoveredFocusedTranslationZ));
            stateListAnimator.addState(FloatingActionButtonImpl.HOVERED_ENABLED_STATE_SET, createElevationAnimator(elevation, hoveredFocusedTranslationZ));
            AnimatorSet set = new AnimatorSet();
            List<Animator> animators = new ArrayList<>();
            animators.add(ObjectAnimator.ofFloat(this.view, "elevation", new float[]{elevation}).setDuration(0));
            if (i >= 22 && i <= 24) {
                FloatingActionButton floatingActionButton = this.view;
                animators.add(ObjectAnimator.ofFloat(floatingActionButton, View.TRANSLATION_Z, new float[]{floatingActionButton.getTranslationZ()}).setDuration(100));
            }
            animators.add(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(100));
            set.playSequentially((Animator[]) animators.toArray(new Animator[0]));
            set.setInterpolator(FloatingActionButtonImpl.ELEVATION_ANIM_INTERPOLATOR);
            stateListAnimator.addState(FloatingActionButtonImpl.ENABLED_STATE_SET, set);
            stateListAnimator.addState(FloatingActionButtonImpl.EMPTY_STATE_SET, createElevationAnimator(0.0f, 0.0f));
            this.view.setStateListAnimator(stateListAnimator);
        }
        if (shouldAddPadding()) {
            updatePadding();
        }
    }

    @NonNull
    private Animator createElevationAnimator(float elevation, float translationZ) {
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(this.view, "elevation", new float[]{elevation}).setDuration(0)).with(ObjectAnimator.ofFloat(this.view, View.TRANSLATION_Z, new float[]{translationZ}).setDuration(100));
        set.setInterpolator(FloatingActionButtonImpl.ELEVATION_ANIM_INTERPOLATOR);
        return set;
    }

    public float getElevation() {
        return this.view.getElevation();
    }

    /* access modifiers changed from: package-private */
    public void onCompatShadowChanged() {
        updatePadding();
    }

    /* access modifiers changed from: package-private */
    public boolean shouldAddPadding() {
        return this.shadowViewDelegate.isCompatPaddingEnabled() || !shouldExpandBoundsForA11y();
    }

    /* access modifiers changed from: package-private */
    public void onDrawableStateChanged(int[] state) {
        if (Build.VERSION.SDK_INT != 21) {
            return;
        }
        if (this.view.isEnabled()) {
            this.view.setElevation(this.elevation);
            if (this.view.isPressed()) {
                this.view.setTranslationZ(this.pressedTranslationZ);
            } else if (this.view.isFocused() || this.view.isHovered()) {
                this.view.setTranslationZ(this.hoveredFocusedTranslationZ);
            } else {
                this.view.setTranslationZ(0.0f);
            }
        } else {
            this.view.setElevation(0.0f);
            this.view.setTranslationZ(0.0f);
        }
    }

    /* access modifiers changed from: package-private */
    public void jumpDrawableToCurrentState() {
    }

    /* access modifiers changed from: package-private */
    public void updateFromViewRotation() {
    }

    /* access modifiers changed from: package-private */
    public boolean requirePreDrawListener() {
        return false;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public BorderDrawable createBorderDrawable(int borderWidth, ColorStateList backgroundTint) {
        Context context = this.view.getContext();
        BorderDrawable borderDrawable = new BorderDrawable((ShapeAppearanceModel) Preconditions.checkNotNull(this.shapeAppearance));
        borderDrawable.setGradientColors(ContextCompat.getColor(context, R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, R.color.design_fab_stroke_end_outer_color));
        borderDrawable.setBorderWidth((float) borderWidth);
        borderDrawable.setBorderTint(backgroundTint);
        return borderDrawable;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public MaterialShapeDrawable createShapeDrawable() {
        return new AlwaysStatefulMaterialShapeDrawable((ShapeAppearanceModel) Preconditions.checkNotNull(this.shapeAppearance));
    }

    /* access modifiers changed from: package-private */
    public void getPadding(@NonNull Rect rect) {
        if (this.shadowViewDelegate.isCompatPaddingEnabled()) {
            super.getPadding(rect);
        } else if (!shouldExpandBoundsForA11y()) {
            int minPadding = (this.minTouchTargetSize - this.view.getSizeDimension()) / 2;
            rect.set(minPadding, minPadding, minPadding, minPadding);
        } else {
            rect.set(0, 0, 0, 0);
        }
    }

    public static class AlwaysStatefulMaterialShapeDrawable extends MaterialShapeDrawable {
        AlwaysStatefulMaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
            super(shapeAppearanceModel);
        }

        public boolean isStateful() {
            return true;
        }
    }
}
