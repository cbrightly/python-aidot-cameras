package kotlin.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: CollectionsJVM.kt */
public class p {
    @NotNull
    public static final <T> List<T> b(T element) {
        List<T> singletonList = Collections.singletonList(element);
        k.d(singletonList, "java.util.Collections.singletonList(element)");
        return singletonList;
    }

    @NotNull
    public static final <T> Object[] a(@NotNull T[] $this$copyToArrayOfAny, boolean isVarargs) {
        Class<Object[]> cls = Object[].class;
        k.e($this$copyToArrayOfAny, "$this$copyToArrayOfAny");
        if (isVarargs && k.a($this$copyToArrayOfAny.getClass(), cls)) {
            return $this$copyToArrayOfAny;
        }
        Object[] copyOf = Arrays.copyOf($this$copyToArrayOfAny, $this$copyToArrayOfAny.length, cls);
        k.d(copyOf, "java.util.Arrays.copyOf(… Array<Any?>::class.java)");
        return copyOf;
    }
}
