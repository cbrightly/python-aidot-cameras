package androidx.collection;

import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: ArrayMap.kt */
public final class ArrayMapKt {
    @NotNull
    public static final <K, V> ArrayMap<K, V> arrayMapOf() {
        return new ArrayMap<>();
    }

    @NotNull
    public static final <K, V> ArrayMap<K, V> arrayMapOf(@NotNull n<? extends K, ? extends V>... pairs) {
        k.f(pairs, "pairs");
        ArrayMap map = new ArrayMap(pairs.length);
        for (n pair : pairs) {
            map.put(pair.getFirst(), pair.getSecond());
        }
        return map;
    }
}
