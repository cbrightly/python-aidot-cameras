package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MutableCollectionsJVM.kt */
public class u extends t {
    public static final <T extends Comparable<? super T>> void v(@NotNull List<T> $this$sort) {
        k.e($this$sort, "$this$sort");
        if ($this$sort.size() > 1) {
            Collections.sort($this$sort);
        }
    }

    public static final <T> void w(@NotNull List<T> $this$sortWith, @NotNull Comparator<? super T> comparator) {
        k.e($this$sortWith, "$this$sortWith");
        k.e(comparator, "comparator");
        if ($this$sortWith.size() > 1) {
            Collections.sort($this$sortWith, comparator);
        }
    }
}
