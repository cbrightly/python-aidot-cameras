package com.google.android.material.progressindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.annotation.StyleRes;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;

public abstract class BaseProgressIndicatorSpec {
    public int hideAnimationBehavior;
    @NonNull
    public int[] indicatorColors = new int[0];
    public int showAnimationBehavior;
    @ColorInt
    public int trackColor;
    @Px
    public int trackCornerRadius;
    @Px
    public int trackThickness;

    /* access modifiers changed from: package-private */
    public abstract void validateSpec();

    protected BaseProgressIndicatorSpec(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        int defaultIndicatorSize = context.getResources().getDimensionPixelSize(R.dimen.mtrl_progress_track_thickness);
        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.BaseProgressIndicator, defStyleAttr, defStyleRes, new int[0]);
        this.trackThickness = MaterialResources.getDimensionPixelSize(context, a, R.styleable.BaseProgressIndicator_trackThickness, defaultIndicatorSize);
        this.trackCornerRadius = Math.min(MaterialResources.getDimensionPixelSize(context, a, R.styleable.BaseProgressIndicator_trackCornerRadius, 0), this.trackThickness / 2);
        this.showAnimationBehavior = a.getInt(R.styleable.BaseProgressIndicator_showAnimationBehavior, 0);
        this.hideAnimationBehavior = a.getInt(R.styleable.BaseProgressIndicator_hideAnimationBehavior, 0);
        loadIndicatorColors(context, a);
        loadTrackColor(context, a);
        a.recycle();
    }

    private void loadIndicatorColors(@NonNull Context context, @NonNull TypedArray typedArray) {
        int i = R.styleable.BaseProgressIndicator_indicatorColor;
        if (!typedArray.hasValue(i)) {
            this.indicatorColors = new int[]{MaterialColors.getColor(context, R.attr.colorPrimary, -1)};
        } else if (typedArray.peekValue(i).type != 1) {
            this.indicatorColors = new int[]{typedArray.getColor(i, -1)};
        } else {
            int[] intArray = context.getResources().getIntArray(typedArray.getResourceId(i, -1));
            this.indicatorColors = intArray;
            if (intArray.length == 0) {
                throw new IllegalArgumentException("indicatorColors cannot be empty when indicatorColor is not used.");
            }
        }
    }

    private void loadTrackColor(@NonNull Context context, @NonNull TypedArray typedArray) {
        int i = R.styleable.BaseProgressIndicator_trackColor;
        if (typedArray.hasValue(i)) {
            this.trackColor = typedArray.getColor(i, -1);
            return;
        }
        this.trackColor = this.indicatorColors[0];
        TypedArray disabledAlphaArray = context.getTheme().obtainStyledAttributes(new int[]{16842803});
        float defaultOpacity = disabledAlphaArray.getFloat(0, 0.2f);
        disabledAlphaArray.recycle();
        this.trackColor = MaterialColors.compositeARGBWithAlpha(this.trackColor, (int) (255.0f * defaultOpacity));
    }

    public boolean isShowAnimationEnabled() {
        return this.showAnimationBehavior != 0;
    }

    public boolean isHideAnimationEnabled() {
        return this.hideAnimationBehavior != 0;
    }
}
