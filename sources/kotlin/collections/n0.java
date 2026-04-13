package kotlin.collections;

import java.util.Collections;
import java.util.Set;
import kotlin.collections.builders.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SetsJVM.kt */
public class n0 {
    @NotNull
    public static final <T> Set<T> c(T element) {
        Set<T> singleton = Collections.singleton(element);
        k.d(singleton, "java.util.Collections.singleton(element)");
        return singleton;
    }

    @NotNull
    public static final <E> Set<E> b() {
        return new g();
    }

    @NotNull
    public static final <E> Set<E> a(@NotNull Set<E> builder) {
        k.e(builder, "builder");
        return ((g) builder).b();
    }
}
