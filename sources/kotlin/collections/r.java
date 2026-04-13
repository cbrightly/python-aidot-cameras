package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Iterables.kt */
public class r extends q {
    @Nullable
    public static final <T> Integer s(@NotNull Iterable<? extends T> $this$collectionSizeOrNull) {
        k.e($this$collectionSizeOrNull, "$this$collectionSizeOrNull");
        if ($this$collectionSizeOrNull instanceof Collection) {
            return Integer.valueOf(((Collection) $this$collectionSizeOrNull).size());
        }
        return null;
    }

    public static final <T> int r(@NotNull Iterable<? extends T> $this$collectionSizeOrDefault, int i) {
        k.e($this$collectionSizeOrDefault, "$this$collectionSizeOrDefault");
        return $this$collectionSizeOrDefault instanceof Collection ? ((Collection) $this$collectionSizeOrDefault).size() : i;
    }

    private static final <T> boolean u(Collection<? extends T> $this$safeToConvertToSet) {
        return $this$safeToConvertToSet.size() > 2 && ($this$safeToConvertToSet instanceof ArrayList);
    }

    @NotNull
    public static final <T> Collection<T> t(@NotNull Iterable<? extends T> $this$convertToSetForSetOperationWith, @NotNull Iterable<? extends T> source) {
        k.e($this$convertToSetForSetOperationWith, "$this$convertToSetForSetOperationWith");
        k.e(source, "source");
        if ($this$convertToSetForSetOperationWith instanceof Set) {
            return (Collection) $this$convertToSetForSetOperationWith;
        }
        if (!($this$convertToSetForSetOperationWith instanceof Collection)) {
            return y.A0($this$convertToSetForSetOperationWith);
        }
        if (!(source instanceof Collection) || ((Collection) source).size() >= 2) {
            return u((Collection) $this$convertToSetForSetOperationWith) ? y.A0($this$convertToSetForSetOperationWith) : (Collection) $this$convertToSetForSetOperationWith;
        }
        return (Collection) $this$convertToSetForSetOperationWith;
    }
}
