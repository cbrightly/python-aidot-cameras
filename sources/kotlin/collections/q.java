package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.comparisons.a;
import kotlin.jvm.internal.k;
import kotlin.ranges.i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Collections.kt */
public class q extends p {
    @NotNull
    public static final <T> Collection<T> d(@NotNull T[] $this$asCollection) {
        k.e($this$asCollection, "$this$asCollection");
        return new h($this$asCollection, false);
    }

    @NotNull
    public static final <T> List<T> g() {
        return a0.INSTANCE;
    }

    @NotNull
    public static final <T> List<T> j(@NotNull T... elements) {
        k.e(elements, "elements");
        return elements.length > 0 ? k.c(elements) : g();
    }

    @NotNull
    public static final <T> List<T> m(@NotNull T... elements) {
        k.e(elements, "elements");
        return elements.length == 0 ? new ArrayList() : new ArrayList(new h(elements, true));
    }

    @NotNull
    public static final <T> ArrayList<T> c(@NotNull T... elements) {
        k.e(elements, "elements");
        return elements.length == 0 ? new ArrayList<>() : new ArrayList<>(new h(elements, true));
    }

    @NotNull
    public static final <T> List<T> k(@Nullable T element) {
        return element != null ? p.b(element) : g();
    }

    @NotNull
    public static final <T> List<T> l(@NotNull T... elements) {
        k.e(elements, "elements");
        return l.s(elements);
    }

    @NotNull
    public static final i h(@NotNull Collection<?> $this$indices) {
        k.e($this$indices, "$this$indices");
        return new i(0, $this$indices.size() - 1);
    }

    public static final <T> int i(@NotNull List<? extends T> $this$lastIndex) {
        k.e($this$lastIndex, "$this$lastIndex");
        return $this$lastIndex.size() - 1;
    }

    @NotNull
    public static final <T> List<T> n(@NotNull List<? extends T> $this$optimizeReadOnlyList) {
        k.e($this$optimizeReadOnlyList, "$this$optimizeReadOnlyList");
        switch ($this$optimizeReadOnlyList.size()) {
            case 0:
                return g();
            case 1:
                return p.b($this$optimizeReadOnlyList.get(0));
            default:
                return $this$optimizeReadOnlyList;
        }
    }

    public static /* synthetic */ int f(List list, Comparable comparable, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = list.size();
        }
        return e(list, comparable, i, i2);
    }

    public static final <T extends Comparable<? super T>> int e(@NotNull List<? extends T> $this$binarySearch, @Nullable T element, int fromIndex, int toIndex) {
        k.e($this$binarySearch, "$this$binarySearch");
        o($this$binarySearch.size(), fromIndex, toIndex);
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = a.c((Comparable) $this$binarySearch.get(mid), element);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp <= 0) {
                return mid;
            } else {
                high = mid - 1;
            }
        }
        return -(low + 1);
    }

    private static final void o(int size, int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex (" + fromIndex + ") is greater than toIndex (" + toIndex + ").");
        } else if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex (" + fromIndex + ") is less than zero.");
        } else if (toIndex > size) {
            throw new IndexOutOfBoundsException("toIndex (" + toIndex + ") is greater than size (" + size + ").");
        }
    }

    public static final void q() {
        throw new ArithmeticException("Index overflow has happened.");
    }

    public static final void p() {
        throw new ArithmeticException("Count overflow has happened.");
    }
}
