package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import androidx.annotation.AnyRes;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.RequiresApi;
import androidx.annotation.StyleableRes;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: TypedArray.kt */
public final class TypedArrayKt {
    private static final void checkAttribute(TypedArray $this$checkAttribute, @StyleableRes int index) {
        if (!$this$checkAttribute.hasValue(index)) {
            throw new IllegalArgumentException("Attribute not defined in set.");
        }
    }

    public static final boolean getBooleanOrThrow(@NotNull TypedArray $this$getBooleanOrThrow, @StyleableRes int index) {
        k.e($this$getBooleanOrThrow, "<this>");
        checkAttribute($this$getBooleanOrThrow, index);
        return $this$getBooleanOrThrow.getBoolean(index, false);
    }

    @ColorInt
    public static final int getColorOrThrow(@NotNull TypedArray $this$getColorOrThrow, @StyleableRes int index) {
        k.e($this$getColorOrThrow, "<this>");
        checkAttribute($this$getColorOrThrow, index);
        return $this$getColorOrThrow.getColor(index, 0);
    }

    @NotNull
    public static final ColorStateList getColorStateListOrThrow(@NotNull TypedArray $this$getColorStateListOrThrow, @StyleableRes int index) {
        k.e($this$getColorStateListOrThrow, "<this>");
        checkAttribute($this$getColorStateListOrThrow, index);
        ColorStateList colorStateList = $this$getColorStateListOrThrow.getColorStateList(index);
        if (colorStateList != null) {
            return colorStateList;
        }
        throw new IllegalStateException("Attribute value was not a color or color state list.".toString());
    }

    public static final float getDimensionOrThrow(@NotNull TypedArray $this$getDimensionOrThrow, @StyleableRes int index) {
        k.e($this$getDimensionOrThrow, "<this>");
        checkAttribute($this$getDimensionOrThrow, index);
        return $this$getDimensionOrThrow.getDimension(index, 0.0f);
    }

    @Dimension
    public static final int getDimensionPixelOffsetOrThrow(@NotNull TypedArray $this$getDimensionPixelOffsetOrThrow, @StyleableRes int index) {
        k.e($this$getDimensionPixelOffsetOrThrow, "<this>");
        checkAttribute($this$getDimensionPixelOffsetOrThrow, index);
        return $this$getDimensionPixelOffsetOrThrow.getDimensionPixelOffset(index, 0);
    }

    @Dimension
    public static final int getDimensionPixelSizeOrThrow(@NotNull TypedArray $this$getDimensionPixelSizeOrThrow, @StyleableRes int index) {
        k.e($this$getDimensionPixelSizeOrThrow, "<this>");
        checkAttribute($this$getDimensionPixelSizeOrThrow, index);
        return $this$getDimensionPixelSizeOrThrow.getDimensionPixelSize(index, 0);
    }

    @NotNull
    public static final Drawable getDrawableOrThrow(@NotNull TypedArray $this$getDrawableOrThrow, @StyleableRes int index) {
        k.e($this$getDrawableOrThrow, "<this>");
        checkAttribute($this$getDrawableOrThrow, index);
        Drawable drawable = $this$getDrawableOrThrow.getDrawable(index);
        k.c(drawable);
        return drawable;
    }

    public static final float getFloatOrThrow(@NotNull TypedArray $this$getFloatOrThrow, @StyleableRes int index) {
        k.e($this$getFloatOrThrow, "<this>");
        checkAttribute($this$getFloatOrThrow, index);
        return $this$getFloatOrThrow.getFloat(index, 0.0f);
    }

    @RequiresApi(26)
    @NotNull
    public static final Typeface getFontOrThrow(@NotNull TypedArray $this$getFontOrThrow, @StyleableRes int index) {
        k.e($this$getFontOrThrow, "<this>");
        checkAttribute($this$getFontOrThrow, index);
        Typeface font = $this$getFontOrThrow.getFont(index);
        k.c(font);
        return font;
    }

    public static final int getIntOrThrow(@NotNull TypedArray $this$getIntOrThrow, @StyleableRes int index) {
        k.e($this$getIntOrThrow, "<this>");
        checkAttribute($this$getIntOrThrow, index);
        return $this$getIntOrThrow.getInt(index, 0);
    }

    public static final int getIntegerOrThrow(@NotNull TypedArray $this$getIntegerOrThrow, @StyleableRes int index) {
        k.e($this$getIntegerOrThrow, "<this>");
        checkAttribute($this$getIntegerOrThrow, index);
        return $this$getIntegerOrThrow.getInteger(index, 0);
    }

    @AnyRes
    public static final int getResourceIdOrThrow(@NotNull TypedArray $this$getResourceIdOrThrow, @StyleableRes int index) {
        k.e($this$getResourceIdOrThrow, "<this>");
        checkAttribute($this$getResourceIdOrThrow, index);
        return $this$getResourceIdOrThrow.getResourceId(index, 0);
    }

    @NotNull
    public static final String getStringOrThrow(@NotNull TypedArray $this$getStringOrThrow, @StyleableRes int index) {
        k.e($this$getStringOrThrow, "<this>");
        checkAttribute($this$getStringOrThrow, index);
        String string = $this$getStringOrThrow.getString(index);
        if (string != null) {
            return string;
        }
        throw new IllegalStateException("Attribute value could not be coerced to String.".toString());
    }

    @NotNull
    public static final CharSequence getTextOrThrow(@NotNull TypedArray $this$getTextOrThrow, @StyleableRes int index) {
        k.e($this$getTextOrThrow, "<this>");
        checkAttribute($this$getTextOrThrow, index);
        CharSequence text = $this$getTextOrThrow.getText(index);
        if (text != null) {
            return text;
        }
        throw new IllegalStateException("Attribute value could not be coerced to CharSequence.".toString());
    }

    @NotNull
    public static final CharSequence[] getTextArrayOrThrow(@NotNull TypedArray $this$getTextArrayOrThrow, @StyleableRes int index) {
        k.e($this$getTextArrayOrThrow, "<this>");
        checkAttribute($this$getTextArrayOrThrow, index);
        CharSequence[] textArray = $this$getTextArrayOrThrow.getTextArray(index);
        k.d(textArray, "getTextArray(index)");
        return textArray;
    }

    public static final <R> R use(@NotNull TypedArray $this$use, @NotNull l<? super TypedArray, ? extends R> block) {
        k.e($this$use, "<this>");
        k.e(block, "block");
        R invoke = block.invoke($this$use);
        R r = invoke;
        $this$use.recycle();
        return invoke;
    }
}
