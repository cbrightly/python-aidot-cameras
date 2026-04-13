package kotlin.reflect.jvm.internal.impl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: collections.kt */
public final class a {
    @NotNull
    public static final <K> Map<K, Integer> d(@NotNull Iterable<? extends K> $this$mapToIndex) {
        k.f($this$mapToIndex, "$this$mapToIndex");
        LinkedHashMap map = new LinkedHashMap();
        int index = 0;
        for (Object k : $this$mapToIndex) {
            map.put(k, Integer.valueOf(index));
            index++;
        }
        return map;
    }

    public static final <T> void a(@NotNull Collection<T> $this$addIfNotNull, @Nullable T t) {
        k.f($this$addIfNotNull, "$this$addIfNotNull");
        if (t != null) {
            $this$addIfNotNull.add(t);
        }
    }

    @NotNull
    public static final <K, V> HashMap<K, V> e(int expectedSize) {
        return new HashMap<>(b(expectedSize));
    }

    @NotNull
    public static final <E> HashSet<E> f(int expectedSize) {
        return new HashSet<>(b(expectedSize));
    }

    @NotNull
    public static final <E> LinkedHashSet<E> g(int expectedSize) {
        return new LinkedHashSet<>(b(expectedSize));
    }

    private static final int b(int expectedSize) {
        if (expectedSize < 3) {
            return 3;
        }
        return (expectedSize / 3) + expectedSize + 1;
    }

    @NotNull
    public static final <T> List<T> c(@NotNull ArrayList<T> $this$compact) {
        k.f($this$compact, "$this$compact");
        switch ($this$compact.size()) {
            case 0:
                return q.g();
            case 1:
                return p.b(y.S($this$compact));
            default:
                ArrayList $this$apply = $this$compact;
                $this$apply.trimToSize();
                return $this$apply;
        }
    }
}
