package kotlin.collections;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.sequences.h;
import org.jetbrains.annotations.NotNull;

/* compiled from: Maps.kt */
public class l0 extends k0 {
    @NotNull
    public static final <K, V> Map<K, V> f() {
        b0 b0Var = b0.INSTANCE;
        if (b0Var != null) {
            return b0Var;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
    }

    @NotNull
    public static final <K, V> Map<K, V> h(@NotNull n<? extends K, ? extends V>... pairs) {
        k.e(pairs, "pairs");
        return pairs.length > 0 ? t(pairs, new LinkedHashMap(k0.b(pairs.length))) : f();
    }

    public static final <K, V> V g(@NotNull Map<K, ? extends V> $this$getValue, K key) {
        k.e($this$getValue, "$this$getValue");
        return j0.a($this$getValue, key);
    }

    public static final <K, V> void n(@NotNull Map<? super K, ? super V> $this$putAll, @NotNull n<? extends K, ? extends V>[] pairs) {
        k.e($this$putAll, "$this$putAll");
        k.e(pairs, "pairs");
        for (n<? extends K, ? extends V> nVar : pairs) {
            $this$putAll.put(nVar.component1(), nVar.component2());
        }
    }

    public static final <K, V> void l(@NotNull Map<? super K, ? super V> $this$putAll, @NotNull Iterable<? extends n<? extends K, ? extends V>> pairs) {
        k.e($this$putAll, "$this$putAll");
        k.e(pairs, "pairs");
        for (n nVar : pairs) {
            $this$putAll.put(nVar.component1(), nVar.component2());
        }
    }

    public static final <K, V> void m(@NotNull Map<? super K, ? super V> $this$putAll, @NotNull h<? extends n<? extends K, ? extends V>> pairs) {
        k.e($this$putAll, "$this$putAll");
        k.e(pairs, "pairs");
        for (n nVar : pairs) {
            $this$putAll.put(nVar.component1(), nVar.component2());
        }
    }

    @NotNull
    public static final <K, V> Map<K, V> o(@NotNull Iterable<? extends n<? extends K, ? extends V>> $this$toMap) {
        k.e($this$toMap, "$this$toMap");
        if (!($this$toMap instanceof Collection)) {
            return i(p($this$toMap, new LinkedHashMap()));
        }
        switch (((Collection) $this$toMap).size()) {
            case 0:
                return f();
            case 1:
                return k0.c((n) ($this$toMap instanceof List ? ((List) $this$toMap).get(0) : $this$toMap.iterator().next()));
            default:
                return p($this$toMap, new LinkedHashMap(k0.b(((Collection) $this$toMap).size())));
        }
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M p(@NotNull Iterable<? extends n<? extends K, ? extends V>> $this$toMap, @NotNull M destination) {
        k.e($this$toMap, "$this$toMap");
        k.e(destination, FirebaseAnalytics.Param.DESTINATION);
        l(destination, $this$toMap);
        return destination;
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M t(@NotNull n<? extends K, ? extends V>[] $this$toMap, @NotNull M destination) {
        k.e($this$toMap, "$this$toMap");
        k.e(destination, FirebaseAnalytics.Param.DESTINATION);
        n(destination, $this$toMap);
        return destination;
    }

    @NotNull
    public static final <K, V> Map<K, V> r(@NotNull h<? extends n<? extends K, ? extends V>> $this$toMap) {
        k.e($this$toMap, "$this$toMap");
        return i(s($this$toMap, new LinkedHashMap()));
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M s(@NotNull h<? extends n<? extends K, ? extends V>> $this$toMap, @NotNull M destination) {
        k.e($this$toMap, "$this$toMap");
        k.e(destination, FirebaseAnalytics.Param.DESTINATION);
        m(destination, $this$toMap);
        return destination;
    }

    @NotNull
    public static final <K, V> Map<K, V> q(@NotNull Map<? extends K, ? extends V> $this$toMap) {
        k.e($this$toMap, "$this$toMap");
        switch ($this$toMap.size()) {
            case 0:
                return f();
            case 1:
                return k0.d($this$toMap);
            default:
                return u($this$toMap);
        }
    }

    @NotNull
    public static final <K, V> Map<K, V> u(@NotNull Map<? extends K, ? extends V> $this$toMutableMap) {
        k.e($this$toMutableMap, "$this$toMutableMap");
        return new LinkedHashMap($this$toMutableMap);
    }

    @NotNull
    public static final <K, V> Map<K, V> k(@NotNull Map<? extends K, ? extends V> $this$plus, @NotNull n<? extends K, ? extends V> pair) {
        k.e($this$plus, "$this$plus");
        k.e(pair, "pair");
        if ($this$plus.isEmpty()) {
            return k0.c(pair);
        }
        LinkedHashMap $this$apply = new LinkedHashMap($this$plus);
        $this$apply.put(pair.getFirst(), pair.getSecond());
        return $this$apply;
    }

    @NotNull
    public static final <K, V> Map<K, V> j(@NotNull Map<? extends K, ? extends V> $this$plus, @NotNull Map<? extends K, ? extends V> map) {
        k.e($this$plus, "$this$plus");
        k.e(map, "map");
        LinkedHashMap $this$apply = new LinkedHashMap($this$plus);
        $this$apply.putAll(map);
        return $this$apply;
    }

    @NotNull
    public static final <K, V> Map<K, V> i(@NotNull Map<K, ? extends V> $this$optimizeReadOnlyMap) {
        k.e($this$optimizeReadOnlyMap, "$this$optimizeReadOnlyMap");
        switch ($this$optimizeReadOnlyMap.size()) {
            case 0:
                return f();
            case 1:
                return k0.d($this$optimizeReadOnlyMap);
            default:
                return $this$optimizeReadOnlyMap;
        }
    }
}
