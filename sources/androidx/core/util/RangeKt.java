package androidx.core.util;

import android.util.Range;
import androidx.annotation.RequiresApi;
import kotlin.jvm.internal.k;
import kotlin.ranges.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: Range.kt */
public final class RangeKt {
    @RequiresApi(21)
    @NotNull
    public static final <T extends Comparable<? super T>> Range<T> rangeTo(@NotNull T $this$rangeTo, @NotNull T that) {
        k.e($this$rangeTo, "<this>");
        k.e(that, "that");
        return new Range<>($this$rangeTo, that);
    }

    @RequiresApi(21)
    @NotNull
    public static final <T extends Comparable<? super T>> Range<T> plus(@NotNull Range<T> $this$plus, @NotNull T value) {
        k.e($this$plus, "<this>");
        k.e(value, "value");
        Range<T> extend = $this$plus.extend(value);
        k.d(extend, "extend(value)");
        return extend;
    }

    @RequiresApi(21)
    @NotNull
    public static final <T extends Comparable<? super T>> Range<T> plus(@NotNull Range<T> $this$plus, @NotNull Range<T> other) {
        k.e($this$plus, "<this>");
        k.e(other, "other");
        Range<T> extend = $this$plus.extend(other);
        k.d(extend, "extend(other)");
        return extend;
    }

    @RequiresApi(21)
    @NotNull
    public static final <T extends Comparable<? super T>> Range<T> and(@NotNull Range<T> $this$and, @NotNull Range<T> other) {
        k.e($this$and, "<this>");
        k.e(other, "other");
        Range<T> intersect = $this$and.intersect(other);
        k.d(intersect, "intersect(other)");
        return intersect;
    }

    @RequiresApi(21)
    @NotNull
    public static final <T extends Comparable<? super T>> f<T> toClosedRange(@NotNull Range<T> $this$toClosedRange) {
        k.e($this$toClosedRange, "<this>");
        return new RangeKt$toClosedRange$1($this$toClosedRange);
    }

    @RequiresApi(21)
    @NotNull
    public static final <T extends Comparable<? super T>> Range<T> toRange(@NotNull f<T> $this$toRange) {
        k.e($this$toRange, "<this>");
        return new Range<>($this$toRange.getStart(), $this$toRange.getEndInclusive());
    }
}
