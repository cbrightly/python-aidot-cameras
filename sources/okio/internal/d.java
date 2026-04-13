package okio.internal;

import kotlin.jvm.internal.k;
import okio.a0;
import org.jetbrains.annotations.NotNull;

/* compiled from: -SegmentedByteString.kt */
public final class d {
    public static final int a(@NotNull int[] $this$binarySearch, int value, int fromIndex, int toIndex) {
        k.e($this$binarySearch, "$this$binarySearch");
        int left = fromIndex;
        int right = toIndex - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            int midVal = $this$binarySearch[mid];
            if (midVal < value) {
                left = mid + 1;
            } else if (midVal <= value) {
                return mid;
            } else {
                right = mid - 1;
            }
        }
        return (-left) - 1;
    }

    public static final int b(@NotNull a0 $this$segment, int pos) {
        k.e($this$segment, "$this$segment");
        int i = a($this$segment.getDirectory$okio(), pos + 1, 0, $this$segment.getSegments$okio().length);
        return i >= 0 ? i : ~i;
    }
}
