package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.e0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MutableCollections.kt */
public class v extends u {
    public static final <T> boolean x(@NotNull Collection<? super T> $this$addAll, @NotNull Iterable<? extends T> elements) {
        k.e($this$addAll, "$this$addAll");
        k.e(elements, "elements");
        if (elements instanceof Collection) {
            return $this$addAll.addAll((Collection) elements);
        }
        boolean result = false;
        for (Object item : elements) {
            if ($this$addAll.add(item)) {
                result = true;
            }
        }
        return result;
    }

    public static final <T> boolean y(@NotNull Collection<? super T> $this$addAll, @NotNull T[] elements) {
        k.e($this$addAll, "$this$addAll");
        k.e(elements, "elements");
        return $this$addAll.addAll(k.c(elements));
    }

    public static final <T> boolean A(@NotNull Collection<? super T> $this$removeAll, @NotNull Iterable<? extends T> elements) {
        k.e($this$removeAll, "$this$removeAll");
        k.e(elements, "elements");
        return e0.a($this$removeAll).removeAll(r.t(elements, $this$removeAll));
    }

    public static final <T> boolean E(@NotNull Collection<? super T> $this$retainAll, @NotNull Iterable<? extends T> elements) {
        k.e($this$retainAll, "$this$retainAll");
        k.e(elements, "elements");
        return e0.a($this$retainAll).retainAll(r.t(elements, $this$retainAll));
    }

    public static final <T> boolean D(@NotNull Iterable<? extends T> $this$retainAll, @NotNull l<? super T, Boolean> predicate) {
        k.e($this$retainAll, "$this$retainAll");
        k.e(predicate, "predicate");
        return z($this$retainAll, predicate, false);
    }

    private static final <T> boolean z(Iterable<? extends T> $this$filterInPlace, l<? super T, Boolean> predicate, boolean predicateResultToRemove) {
        boolean result = false;
        Iterator $this$with = $this$filterInPlace.iterator();
        while ($this$with.hasNext()) {
            if (predicate.invoke($this$with.next()).booleanValue() == predicateResultToRemove) {
                $this$with.remove();
                result = true;
            }
        }
        return result;
    }

    public static final <T> T B(@NotNull List<T> $this$removeFirst) {
        k.e($this$removeFirst, "$this$removeFirst");
        if (!$this$removeFirst.isEmpty()) {
            return $this$removeFirst.remove(0);
        }
        throw new NoSuchElementException("List is empty.");
    }

    @Nullable
    public static final <T> T C(@NotNull List<T> $this$removeLastOrNull) {
        k.e($this$removeLastOrNull, "$this$removeLastOrNull");
        if ($this$removeLastOrNull.isEmpty()) {
            return null;
        }
        return $this$removeLastOrNull.remove(q.i($this$removeLastOrNull));
    }
}
