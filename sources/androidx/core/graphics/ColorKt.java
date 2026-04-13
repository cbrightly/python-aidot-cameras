package androidx.core.graphics;

import android.graphics.Color;
import android.graphics.ColorSpace;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Color.kt */
public final class ColorKt {
    @RequiresApi(26)
    public static final float component1(@NotNull Color $this$component1) {
        k.e($this$component1, "<this>");
        return $this$component1.getComponent(0);
    }

    @RequiresApi(26)
    public static final float component2(@NotNull Color $this$component2) {
        k.e($this$component2, "<this>");
        return $this$component2.getComponent(1);
    }

    @RequiresApi(26)
    public static final float component3(@NotNull Color $this$component3) {
        k.e($this$component3, "<this>");
        return $this$component3.getComponent(2);
    }

    @RequiresApi(26)
    public static final float component4(@NotNull Color $this$component4) {
        k.e($this$component4, "<this>");
        return $this$component4.getComponent(3);
    }

    @RequiresApi(26)
    @NotNull
    public static final Color plus(@NotNull Color $this$plus, @NotNull Color c) {
        k.e($this$plus, "<this>");
        k.e(c, "c");
        Color compositeColors = ColorUtils.compositeColors(c, $this$plus);
        k.d(compositeColors, "compositeColors(c, this)");
        return compositeColors;
    }

    public static final int getAlpha(@ColorInt int $this$alpha) {
        return ($this$alpha >> 24) & 255;
    }

    public static final int getRed(@ColorInt int $this$red) {
        return ($this$red >> 16) & 255;
    }

    public static final int getGreen(@ColorInt int $this$green) {
        return ($this$green >> 8) & 255;
    }

    public static final int getBlue(@ColorInt int $this$blue) {
        return $this$blue & 255;
    }

    public static final int component1(@ColorInt int $this$component1) {
        return ($this$component1 >> 24) & 255;
    }

    public static final int component2(@ColorInt int $this$component2) {
        return ($this$component2 >> 16) & 255;
    }

    public static final int component3(@ColorInt int $this$component3) {
        return ($this$component3 >> 8) & 255;
    }

    public static final int component4(@ColorInt int $this$component4) {
        return $this$component4 & 255;
    }

    @RequiresApi(26)
    public static final float getLuminance(@ColorInt int $this$luminance) {
        return Color.luminance($this$luminance);
    }

    @RequiresApi(26)
    @NotNull
    public static final Color toColor(@ColorInt int $this$toColor) {
        Color valueOf = Color.valueOf($this$toColor);
        k.d(valueOf, "valueOf(this)");
        return valueOf;
    }

    @RequiresApi(26)
    public static final long toColorLong(@ColorInt int $this$toColorLong) {
        return Color.pack($this$toColorLong);
    }

    @RequiresApi(26)
    public static final float component1(long $this$component1) {
        return Color.red($this$component1);
    }

    @RequiresApi(26)
    public static final float component2(long $this$component2) {
        return Color.green($this$component2);
    }

    @RequiresApi(26)
    public static final float component3(long $this$component3) {
        return Color.blue($this$component3);
    }

    @RequiresApi(26)
    public static final float component4(long $this$component4) {
        return Color.alpha($this$component4);
    }

    @RequiresApi(26)
    public static final float getAlpha(long $this$alpha) {
        return Color.alpha($this$alpha);
    }

    @RequiresApi(26)
    public static final float getRed(long $this$red) {
        return Color.red($this$red);
    }

    @RequiresApi(26)
    public static final float getGreen(long $this$green) {
        return Color.green($this$green);
    }

    @RequiresApi(26)
    public static final float getBlue(long $this$blue) {
        return Color.blue($this$blue);
    }

    @RequiresApi(26)
    public static final float getLuminance(long $this$luminance) {
        return Color.luminance($this$luminance);
    }

    @RequiresApi(26)
    @NotNull
    public static final Color toColor(long $this$toColor) {
        Color valueOf = Color.valueOf($this$toColor);
        k.d(valueOf, "valueOf(this)");
        return valueOf;
    }

    @RequiresApi(26)
    @ColorInt
    public static final int toColorInt(long $this$toColorInt) {
        return Color.toArgb($this$toColorInt);
    }

    @RequiresApi(26)
    public static final boolean isSrgb(long $this$isSrgb) {
        return Color.isSrgb($this$isSrgb);
    }

    @RequiresApi(26)
    public static final boolean isWideGamut(long $this$isWideGamut) {
        return Color.isWideGamut($this$isWideGamut);
    }

    @RequiresApi(26)
    @NotNull
    public static final ColorSpace getColorSpace(long $this$colorSpace) {
        ColorSpace colorSpace = Color.colorSpace($this$colorSpace);
        k.d(colorSpace, "colorSpace(this)");
        return colorSpace;
    }

    @RequiresApi(26)
    public static final long convertTo(@ColorInt int $this$convertTo, @NotNull ColorSpace.Named colorSpace) {
        k.e(colorSpace, "colorSpace");
        return Color.convert($this$convertTo, ColorSpace.get(colorSpace));
    }

    @RequiresApi(26)
    public static final long convertTo(@ColorInt int $this$convertTo, @NotNull ColorSpace colorSpace) {
        k.e(colorSpace, "colorSpace");
        return Color.convert($this$convertTo, colorSpace);
    }

    @RequiresApi(26)
    public static final long convertTo(long $this$convertTo, @NotNull ColorSpace.Named colorSpace) {
        k.e(colorSpace, "colorSpace");
        return Color.convert($this$convertTo, ColorSpace.get(colorSpace));
    }

    @RequiresApi(26)
    public static final long convertTo(long $this$convertTo, @NotNull ColorSpace colorSpace) {
        k.e(colorSpace, "colorSpace");
        return Color.convert($this$convertTo, colorSpace);
    }

    @RequiresApi(26)
    @NotNull
    public static final Color convertTo(@NotNull Color $this$convertTo, @NotNull ColorSpace.Named colorSpace) {
        k.e($this$convertTo, "<this>");
        k.e(colorSpace, "colorSpace");
        Color convert = $this$convertTo.convert(ColorSpace.get(colorSpace));
        k.d(convert, "convert(ColorSpace.get(colorSpace))");
        return convert;
    }

    @RequiresApi(26)
    @NotNull
    public static final Color convertTo(@NotNull Color $this$convertTo, @NotNull ColorSpace colorSpace) {
        k.e($this$convertTo, "<this>");
        k.e(colorSpace, "colorSpace");
        Color convert = $this$convertTo.convert(colorSpace);
        k.d(convert, "convert(colorSpace)");
        return convert;
    }

    @ColorInt
    public static final int toColorInt(@NotNull String $this$toColorInt) {
        k.e($this$toColorInt, "<this>");
        return Color.parseColor($this$toColorInt);
    }
}
