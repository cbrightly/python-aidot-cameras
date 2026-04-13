package com.google.android.material.elevation;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;

public class ElevationOverlayProvider {
    private static final float FORMULA_MULTIPLIER = 4.5f;
    private static final float FORMULA_OFFSET = 2.0f;
    private final int colorSurface;
    private final float displayDensity;
    private final int elevationOverlayColor;
    private final boolean elevationOverlayEnabled;

    public ElevationOverlayProvider(@NonNull Context context) {
        this.elevationOverlayEnabled = MaterialAttributes.resolveBoolean(context, R.attr.elevationOverlayEnabled, false);
        this.elevationOverlayColor = MaterialColors.getColor(context, R.attr.elevationOverlayColor, 0);
        this.colorSurface = MaterialColors.getColor(context, R.attr.colorSurface, 0);
        this.displayDensity = context.getResources().getDisplayMetrics().density;
    }

    @ColorInt
    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float elevation, @NonNull View overlayView) {
        return compositeOverlayWithThemeSurfaceColorIfNeeded(elevation + getParentAbsoluteElevation(overlayView));
    }

    @ColorInt
    public int compositeOverlayWithThemeSurfaceColorIfNeeded(float elevation) {
        return compositeOverlayIfNeeded(this.colorSurface, elevation);
    }

    @ColorInt
    public int compositeOverlayIfNeeded(@ColorInt int backgroundColor, float elevation, @NonNull View overlayView) {
        return compositeOverlayIfNeeded(backgroundColor, elevation + getParentAbsoluteElevation(overlayView));
    }

    @ColorInt
    public int compositeOverlayIfNeeded(@ColorInt int backgroundColor, float elevation) {
        if (!this.elevationOverlayEnabled || !isThemeSurfaceColor(backgroundColor)) {
            return backgroundColor;
        }
        return compositeOverlay(backgroundColor, elevation);
    }

    @ColorInt
    public int compositeOverlay(@ColorInt int backgroundColor, float elevation, @NonNull View overlayView) {
        return compositeOverlay(backgroundColor, elevation + getParentAbsoluteElevation(overlayView));
    }

    @ColorInt
    public int compositeOverlay(@ColorInt int backgroundColor, float elevation) {
        float overlayAlphaFraction = calculateOverlayAlphaFraction(elevation);
        return ColorUtils.setAlphaComponent(MaterialColors.layer(ColorUtils.setAlphaComponent(backgroundColor, 255), this.elevationOverlayColor, overlayAlphaFraction), Color.alpha(backgroundColor));
    }

    public int calculateOverlayAlpha(float elevation) {
        return Math.round(calculateOverlayAlphaFraction(elevation) * 255.0f);
    }

    public float calculateOverlayAlphaFraction(float elevation) {
        float f = this.displayDensity;
        if (f <= 0.0f || elevation <= 0.0f) {
            return 0.0f;
        }
        return Math.min(((((float) Math.log1p((double) (elevation / f))) * FORMULA_MULTIPLIER) + 2.0f) / 100.0f, 1.0f);
    }

    public boolean isThemeElevationOverlayEnabled() {
        return this.elevationOverlayEnabled;
    }

    @ColorInt
    public int getThemeElevationOverlayColor() {
        return this.elevationOverlayColor;
    }

    @ColorInt
    public int getThemeSurfaceColor() {
        return this.colorSurface;
    }

    public float getParentAbsoluteElevation(@NonNull View overlayView) {
        return ViewUtils.getParentAbsoluteElevation(overlayView);
    }

    private boolean isThemeSurfaceColor(@ColorInt int color) {
        return ColorUtils.setAlphaComponent(color, 255) == this.colorSurface;
    }
}
