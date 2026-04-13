package kotlin.collections;

import java.util.List;
import kotlin.jvm.internal.k;
import kotlin.ranges.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: ReversedViews.kt */
public class w extends v {
    /* access modifiers changed from: private */
    public static final int I(List<?> $this$reverseElementIndex, int index) {
        int i = q.i($this$reverseElementIndex);
        if (index >= 0 && i >= index) {
            return q.i($this$reverseElementIndex) - index;
        }
        throw new IndexOutOfBoundsException("Element index " + index + " must be in range [" + new i(0, q.i($this$reverseElementIndex)) + "].");
    }

    /* access modifiers changed from: private */
    public static final int J(List<?> $this$reversePositionIndex, int index) {
        int size = $this$reversePositionIndex.size();
        if (index >= 0 && size >= index) {
            return $this$reversePositionIndex.size() - index;
        }
        throw new IndexOutOfBoundsException("Position index " + index + " must be in range [" + new i(0, $this$reversePositionIndex.size()) + "].");
    }

    @NotNull
    public static final <T> List<T> H(@NotNull List<T> $this$asReversed) {
        k.e($this$asReversed, "$this$asReversed");
        return new m0($this$asReversed);
    }
}
