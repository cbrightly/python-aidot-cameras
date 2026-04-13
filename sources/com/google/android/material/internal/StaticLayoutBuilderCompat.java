package com.google.android.material.internal;

import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Preconditions;
import java.lang.reflect.Constructor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public final class StaticLayoutBuilderCompat {
    static final int DEFAULT_HYPHENATION_FREQUENCY = (Build.VERSION.SDK_INT >= 23 ? 1 : 0);
    static final float DEFAULT_LINE_SPACING_ADD = 0.0f;
    static final float DEFAULT_LINE_SPACING_MULTIPLIER = 1.0f;
    private static final String TEXT_DIRS_CLASS = "android.text.TextDirectionHeuristics";
    private static final String TEXT_DIR_CLASS = "android.text.TextDirectionHeuristic";
    private static final String TEXT_DIR_CLASS_LTR = "LTR";
    private static final String TEXT_DIR_CLASS_RTL = "RTL";
    @Nullable
    private static Constructor<StaticLayout> constructor;
    private static boolean initialized;
    @Nullable
    private static Object textDirection;
    private Layout.Alignment alignment;
    @Nullable
    private TextUtils.TruncateAt ellipsize;
    private int end;
    private int hyphenationFrequency;
    private boolean includePad;
    private boolean isRtl;
    private float lineSpacingAdd;
    private float lineSpacingMultiplier;
    private int maxLines;
    private final TextPaint paint;
    private CharSequence source;
    private int start = 0;
    private final int width;

    private StaticLayoutBuilderCompat(CharSequence source2, TextPaint paint2, int width2) {
        this.source = source2;
        this.paint = paint2;
        this.width = width2;
        this.end = source2.length();
        this.alignment = Layout.Alignment.ALIGN_NORMAL;
        this.maxLines = Integer.MAX_VALUE;
        this.lineSpacingAdd = 0.0f;
        this.lineSpacingMultiplier = 1.0f;
        this.hyphenationFrequency = DEFAULT_HYPHENATION_FREQUENCY;
        this.includePad = true;
        this.ellipsize = null;
    }

    @NonNull
    public static StaticLayoutBuilderCompat obtain(@NonNull CharSequence source2, @NonNull TextPaint paint2, @IntRange(from = 0) int width2) {
        return new StaticLayoutBuilderCompat(source2, paint2, width2);
    }

    @NonNull
    public StaticLayoutBuilderCompat setAlignment(@NonNull Layout.Alignment alignment2) {
        this.alignment = alignment2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setIncludePad(boolean includePad2) {
        this.includePad = includePad2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setStart(@IntRange(from = 0) int start2) {
        this.start = start2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setEnd(@IntRange(from = 0) int end2) {
        this.end = end2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setMaxLines(@IntRange(from = 0) int maxLines2) {
        this.maxLines = maxLines2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setLineSpacing(float spacingAdd, float lineSpacingMultiplier2) {
        this.lineSpacingAdd = spacingAdd;
        this.lineSpacingMultiplier = lineSpacingMultiplier2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setHyphenationFrequency(int hyphenationFrequency2) {
        this.hyphenationFrequency = hyphenationFrequency2;
        return this;
    }

    @NonNull
    public StaticLayoutBuilderCompat setEllipsize(@Nullable TextUtils.TruncateAt ellipsize2) {
        this.ellipsize = ellipsize2;
        return this;
    }

    public StaticLayout build() {
        if (this.source == null) {
            this.source = "";
        }
        int availableWidth = Math.max(0, this.width);
        CharSequence textToDraw = this.source;
        if (this.maxLines == 1) {
            textToDraw = TextUtils.ellipsize(this.source, this.paint, (float) availableWidth, this.ellipsize);
        }
        int min = Math.min(textToDraw.length(), this.end);
        this.end = min;
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.isRtl && this.maxLines == 1) {
                this.alignment = Layout.Alignment.ALIGN_OPPOSITE;
            }
            StaticLayout.Builder builder = StaticLayout.Builder.obtain(textToDraw, this.start, min, this.paint, availableWidth);
            builder.setAlignment(this.alignment);
            builder.setIncludePad(this.includePad);
            builder.setTextDirection(this.isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);
            TextUtils.TruncateAt truncateAt = this.ellipsize;
            if (truncateAt != null) {
                builder.setEllipsize(truncateAt);
            }
            builder.setMaxLines(this.maxLines);
            float f = this.lineSpacingAdd;
            if (!(f == 0.0f && this.lineSpacingMultiplier == 1.0f)) {
                builder.setLineSpacing(f, this.lineSpacingMultiplier);
            }
            if (this.maxLines > 1) {
                builder.setHyphenationFrequency(this.hyphenationFrequency);
            }
            return builder.build();
        }
        createConstructorWithReflection();
        try {
            return (StaticLayout) ((Constructor) Preconditions.checkNotNull(constructor)).newInstance(new Object[]{textToDraw, Integer.valueOf(this.start), Integer.valueOf(this.end), this.paint, Integer.valueOf(availableWidth), this.alignment, Preconditions.checkNotNull(textDirection), Float.valueOf(1.0f), Float.valueOf(0.0f), Boolean.valueOf(this.includePad), null, Integer.valueOf(availableWidth), Integer.valueOf(this.maxLines)});
        } catch (Exception cause) {
            throw new StaticLayoutBuilderCompatException(cause);
        }
    }

    private void createConstructorWithReflection() {
        Class cls;
        if (!initialized) {
            try {
                boolean useRtl = this.isRtl && Build.VERSION.SDK_INT >= 23;
                if (Build.VERSION.SDK_INT >= 18) {
                    cls = TextDirectionHeuristic.class;
                    textDirection = useRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
                } else {
                    ClassLoader loader = StaticLayoutBuilderCompat.class.getClassLoader();
                    String textDirClassName = this.isRtl ? TEXT_DIR_CLASS_RTL : TEXT_DIR_CLASS_LTR;
                    Class<?> textDirClass = loader.loadClass(TEXT_DIR_CLASS);
                    Class<?> textDirsClass = loader.loadClass(TEXT_DIRS_CLASS);
                    textDirection = textDirsClass.getField(textDirClassName).get(textDirsClass);
                    cls = textDirClass;
                }
                Class cls2 = Integer.TYPE;
                Class cls3 = Float.TYPE;
                Constructor<StaticLayout> declaredConstructor = StaticLayout.class.getDeclaredConstructor(new Class[]{CharSequence.class, cls2, cls2, TextPaint.class, cls2, Layout.Alignment.class, cls, cls3, cls3, Boolean.TYPE, TextUtils.TruncateAt.class, cls2, cls2});
                constructor = declaredConstructor;
                declaredConstructor.setAccessible(true);
                initialized = true;
            } catch (Exception cause) {
                throw new StaticLayoutBuilderCompatException(cause);
            }
        }
    }

    public StaticLayoutBuilderCompat setIsRtl(boolean isRtl2) {
        this.isRtl = isRtl2;
        return this;
    }

    public static class StaticLayoutBuilderCompatException extends Exception {
        StaticLayoutBuilderCompatException(Throwable cause) {
            super("Error thrown initializing StaticLayout " + cause.getMessage(), cause);
        }
    }
}
