package kotlin.collections;

import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MapWithDefault.kt */
public class j0 {
    public static final <K, V> V a(@NotNull Map<K, ? extends V> $this$getOrImplicitDefault, K key) {
        k.e($this$getOrImplicitDefault, "$this$getOrImplicitDefault");
        if ($this$getOrImplicitDefault instanceof i0) {
            return ((i0) $this$getOrImplicitDefault).d(key);
        }
        Map $this$getOrElseNullable$iv = $this$getOrImplicitDefault;
        Object value$iv = $this$getOrElseNullable$iv.get(key);
        if (value$iv != null || $this$getOrElseNullable$iv.containsKey(key)) {
            return value$iv;
        }
        throw new NoSuchElementException("Key " + key + " is missing in the map.");
    }
}
