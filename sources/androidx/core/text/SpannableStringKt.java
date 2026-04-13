package androidx.core.text;

import android.annotation.SuppressLint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import kotlin.jvm.internal.k;
import kotlin.ranges.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: SpannableString.kt */
public final class SpannableStringKt {
    @NotNull
    public static final Spannable toSpannable(@NotNull CharSequence $this$toSpannable) {
        k.e($this$toSpannable, "<this>");
        SpannableString valueOf = SpannableString.valueOf($this$toSpannable);
        k.d(valueOf, "valueOf(this)");
        return valueOf;
    }

    @SuppressLint({"SyntheticAccessor"})
    public static final void clearSpans(@NotNull Spannable $this$clearSpans) {
        k.e($this$clearSpans, "<this>");
        Spanned $this$getSpans_u24default$iv = $this$clearSpans;
        Object[] $this$forEach$iv = $this$getSpans_u24default$iv.getSpans(0, $this$getSpans_u24default$iv.length(), Object.class);
        k.d($this$forEach$iv, "getSpans(start, end, T::class.java)");
        for (Object element$iv : $this$forEach$iv) {
            $this$clearSpans.removeSpan(element$iv);
        }
    }

    public static final void set(@NotNull Spannable $this$set, int start, int end, @NotNull Object span) {
        k.e($this$set, "<this>");
        k.e(span, "span");
        $this$set.setSpan(span, start, end, 17);
    }

    public static final void set(@NotNull Spannable $this$set, @NotNull i range, @NotNull Object span) {
        k.e($this$set, "<this>");
        k.e(range, "range");
        k.e(span, "span");
        $this$set.setSpan(span, range.getStart().intValue(), range.getEndInclusive().intValue(), 17);
    }
}
