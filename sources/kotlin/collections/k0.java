package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: MapsJVM.kt */
public class k0 extends j0 {
    @NotNull
    public static final <K, V> Map<K, V> c(@NotNull n<? extends K, ? extends V> pair) {
        k.e(pair, "pair");
        Map<K, V> singletonMap = Collections.singletonMap(pair.getFirst(), pair.getSecond());
        k.d(singletonMap, "java.util.Collections.si…(pair.first, pair.second)");
        return singletonMap;
    }

    @NotNull
    public static final <K, V> SortedMap<K, V> e(@NotNull Map<? extends K, ? extends V> $this$toSortedMap, @NotNull Comparator<? super K> comparator) {
        k.e($this$toSortedMap, "$this$toSortedMap");
        k.e(comparator, "comparator");
        TreeMap $this$apply = new TreeMap(comparator);
        $this$apply.putAll($this$toSortedMap);
        return $this$apply;
    }

    @NotNull
    public static final <K, V> Map<K, V> d(@NotNull Map<? extends K, ? extends V> $this$toSingletonMap) {
        k.e($this$toSingletonMap, "$this$toSingletonMap");
        Map.Entry $this$with = $this$toSingletonMap.entrySet().iterator().next();
        Map<K, V> singletonMap = Collections.singletonMap($this$with.getKey(), $this$with.getValue());
        k.d(singletonMap, "with(entries.iterator().…ingletonMap(key, value) }");
        return singletonMap;
    }

    public static final int b(int expectedSize) {
        if (expectedSize < 0) {
            return expectedSize;
        }
        if (expectedSize < 3) {
            return expectedSize + 1;
        }
        if (expectedSize < 1073741824) {
            return (int) ((((float) expectedSize) / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }
}
