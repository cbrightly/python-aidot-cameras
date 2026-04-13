package androidx.core.text;

import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.ColorInt;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: SpannableStringBuilder.kt */
public final class SpannableStringBuilderKt {
    @NotNull
    public static final SpannedString buildSpannedString(@NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e(builderAction, "builderAction");
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builderAction.invoke(builder);
        return new SpannedString(builder);
    }

    @NotNull
    public static final SpannableStringBuilder inSpans(@NotNull SpannableStringBuilder $this$inSpans, @NotNull Object[] spans, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$inSpans, "<this>");
        k.e(spans, "spans");
        k.e(builderAction, "builderAction");
        int start = $this$inSpans.length();
        builderAction.invoke($this$inSpans);
        int length = spans.length;
        int i = 0;
        while (i < length) {
            Object span = spans[i];
            i++;
            $this$inSpans.setSpan(span, start, $this$inSpans.length(), 17);
        }
        return $this$inSpans;
    }

    @NotNull
    public static final SpannableStringBuilder inSpans(@NotNull SpannableStringBuilder $this$inSpans, @NotNull Object span, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$inSpans, "<this>");
        k.e(span, "span");
        k.e(builderAction, "builderAction");
        int start = $this$inSpans.length();
        builderAction.invoke($this$inSpans);
        $this$inSpans.setSpan(span, start, $this$inSpans.length(), 17);
        return $this$inSpans;
    }

    @NotNull
    public static final SpannableStringBuilder bold(@NotNull SpannableStringBuilder $this$bold, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$bold, "<this>");
        k.e(builderAction, "builderAction");
        Object span$iv = new StyleSpan(1);
        SpannableStringBuilder $this$inSpans$iv = $this$bold;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    @NotNull
    public static final SpannableStringBuilder italic(@NotNull SpannableStringBuilder $this$italic, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$italic, "<this>");
        k.e(builderAction, "builderAction");
        Object span$iv = new StyleSpan(2);
        SpannableStringBuilder $this$inSpans$iv = $this$italic;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    @NotNull
    public static final SpannableStringBuilder underline(@NotNull SpannableStringBuilder $this$underline, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$underline, "<this>");
        k.e(builderAction, "builderAction");
        Object span$iv = new UnderlineSpan();
        SpannableStringBuilder $this$inSpans$iv = $this$underline;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    @NotNull
    public static final SpannableStringBuilder color(@NotNull SpannableStringBuilder $this$color, @ColorInt int color, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$color, "<this>");
        k.e(builderAction, "builderAction");
        Object span$iv = new ForegroundColorSpan(color);
        SpannableStringBuilder $this$inSpans$iv = $this$color;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    @NotNull
    public static final SpannableStringBuilder backgroundColor(@NotNull SpannableStringBuilder $this$backgroundColor, @ColorInt int color, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$backgroundColor, "<this>");
        k.e(builderAction, "builderAction");
        Object span$iv = new BackgroundColorSpan(color);
        SpannableStringBuilder $this$inSpans$iv = $this$backgroundColor;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    @NotNull
    public static final SpannableStringBuilder strikeThrough(@NotNull SpannableStringBuilder $this$strikeThrough, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$strikeThrough, "<this>");
        k.e(builderAction, "builderAction");
        Object span$iv = new StrikethroughSpan();
        SpannableStringBuilder $this$inSpans$iv = $this$strikeThrough;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    @NotNull
    public static final SpannableStringBuilder scale(@NotNull SpannableStringBuilder $this$scale, float proportion, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$scale, "<this>");
        k.e(builderAction, "builderAction");
        Object span$iv = new RelativeSizeSpan(proportion);
        SpannableStringBuilder $this$inSpans$iv = $this$scale;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    @NotNull
    public static final SpannableStringBuilder superscript(@NotNull SpannableStringBuilder $this$superscript, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$superscript, "<this>");
        k.e(builderAction, "builderAction");
        Object span$iv = new SuperscriptSpan();
        SpannableStringBuilder $this$inSpans$iv = $this$superscript;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }

    @NotNull
    public static final SpannableStringBuilder subscript(@NotNull SpannableStringBuilder $this$subscript, @NotNull l<? super SpannableStringBuilder, x> builderAction) {
        k.e($this$subscript, "<this>");
        k.e(builderAction, "builderAction");
        Object span$iv = new SubscriptSpan();
        SpannableStringBuilder $this$inSpans$iv = $this$subscript;
        int start$iv = $this$inSpans$iv.length();
        builderAction.invoke($this$inSpans$iv);
        $this$inSpans$iv.setSpan(span$iv, start$iv, $this$inSpans$iv.length(), 17);
        return $this$inSpans$iv;
    }
}
