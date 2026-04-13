package kotlin.collections;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: _Sets.kt */
public class p0 extends o0 {
    @NotNull
    public static final <T> Set<T> h(@NotNull Set<? extends T> $this$minus, T element) {
        boolean z;
        k.e($this$minus, "$this$minus");
        LinkedHashSet result = new LinkedHashSet(k0.b($this$minus.size()));
        boolean removed = false;
        for (Object next : $this$minus) {
            Object it = next;
            if (removed || !k.a(it, element)) {
                z = true;
            } else {
                removed = true;
                z = false;
            }
            if (z) {
                result.add(next);
            }
        }
        return result;
    }

    @NotNull
    public static final <T> Set<T> j(@NotNull Set<? extends T> $this$plus, T element) {
        k.e($this$plus, "$this$plus");
        LinkedHashSet result = new LinkedHashSet(k0.b($this$plus.size() + 1));
        result.addAll($this$plus);
        result.add(element);
        return result;
    }

    @NotNull
    public static final <T> Set<T> i(@NotNull Set<? extends T> $this$plus, @NotNull Iterable<? extends T> elements) {
        int i;
        k.e($this$plus, "$this$plus");
        k.e(elements, "elements");
        Integer s = r.s(elements);
        if (s != null) {
            i = $this$plus.size() + s.intValue();
        } else {
            i = $this$plus.size() * 2;
        }
        LinkedHashSet result = new LinkedHashSet(k0.b(i));
        result.addAll($this$plus);
        v.x(result, elements);
        return result;
    }
}
