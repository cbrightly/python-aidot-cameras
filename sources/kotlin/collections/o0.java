package kotlin.collections;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Sets.kt */
public class o0 extends n0 {
    @NotNull
    public static final <T> Set<T> d() {
        return c0.INSTANCE;
    }

    @NotNull
    public static final <T> Set<T> g(@NotNull T... elements) {
        k.e(elements, "elements");
        return elements.length > 0 ? l.g0(elements) : d();
    }

    @NotNull
    public static final <T> LinkedHashSet<T> e(@NotNull T... elements) {
        k.e(elements, "elements");
        return (LinkedHashSet) l.N(elements, new LinkedHashSet(k0.b(elements.length)));
    }

    @NotNull
    public static final <T> Set<T> f(@NotNull Set<? extends T> $this$optimizeReadOnlySet) {
        k.e($this$optimizeReadOnlySet, "$this$optimizeReadOnlySet");
        switch ($this$optimizeReadOnlySet.size()) {
            case 0:
                return d();
            case 1:
                return n0.c($this$optimizeReadOnlySet.iterator().next());
            default:
                return $this$optimizeReadOnlySet;
        }
    }
}
