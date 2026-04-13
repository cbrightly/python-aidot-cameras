package com.google.android.material.color;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.core.graphics.ColorUtils;
import com.google.android.material.resources.MaterialAttributes;

public class MaterialColors {
    public static final float ALPHA_DISABLED = 0.38f;
    public static final float ALPHA_DISABLED_LOW = 0.12f;
    public static final float ALPHA_FULL = 1.0f;
    public static final float ALPHA_LOW = 0.32f;
    public static final float ALPHA_MEDIUM = 0.54f;

    private MaterialColors() {
    }

    @ColorInt
    public static int getColor(@NonNull View view, @AttrRes int colorAttributeResId) {
        return MaterialAttributes.resolveOrThrow(view, colorAttributeResId);
    }

    @ColorInt
    public static int getColor(Context context, @AttrRes int colorAttributeResId, String errorMessageComponent) {
        return MaterialAttributes.resolveOrThrow(context, colorAttributeResId, errorMessageComponent);
    }

    @ColorInt
    public static int getColor(@NonNull View view, @AttrRes int colorAttributeResId, @ColorInt int defaultValue) {
        return getColor(view.getContext(), colorAttributeResId, defaultValue);
    }

    @ColorInt
    public static int getColor(@NonNull Context context, @AttrRes int colorAttributeResId, @ColorInt int defaultValue) {
        TypedValue typedValue = MaterialAttributes.resolve(context, colorAttributeResId);
        if (typedValue != null) {
            return typedValue.data;
        }
        return defaultValue;
    }

    @ColorInt
    public static int layer(@NonNull View view, @AttrRes int backgroundColorAttributeResId, @AttrRes int overlayColorAttributeResId) {
        return layer(view, backgroundColorAttributeResId, overlayColorAttributeResId, 1.0f);
    }

    @ColorInt
    public static int layer(@NonNull View view, @AttrRes int backgroundColorAttributeResId, @AttrRes int overlayColorAttributeResId, @FloatRange(from = 0.0d, to = 1.0d) float overlayAlpha) {
        return layer(getColor(view, backgroundColorAttributeResId), getColor(view, overlayColorAttributeResId), overlayAlpha);
    }

    @ColorInt
    public static int layer(@ColorInt int backgroundColor, @ColorInt int overlayColor, @FloatRange(from = 0.0d, to = 1.0d) float overlayAlpha) {
        return layer(backgroundColor, ColorUtils.setAlphaComponent(overlayColor, Math.round(((float) Color.alpha(overlayColor)) * overlayAlpha)));
    }

    @ColorInt
    public static int layer(@ColorInt int backgroundColor, @ColorInt int overlayColor) {
        return ColorUtils.compositeColors(overlayColor, backgroundColor);
    }

    @ColorInt
    public static int compositeARGBWithAlpha(@ColorInt int originalARGB, @IntRange(from = 0, to = 255) int alpha) {
        return ColorUtils.setAlphaComponent(originalARGB, (Color.alpha(originalARGB) * alpha) / 255);
    }

    public static boolean isColorLight(@ColorInt int color) {
        return color != 0 && ColorUtils.calculateLuminance(color) > 0.5d;
    }
}
