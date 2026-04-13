package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Iterables.kt */
public final class e0<T> implements Iterable<d0<? extends T>>, a {
    private final kotlin.jvm.functions.a<Iterator<T>> c;

    public e0(@NotNull kotlin.jvm.functions.a<? extends Iterator<? extends T>> iteratorFactory) {
        k.e(iteratorFactory, "iteratorFactory");
        this.c = iteratorFactory;
    }

    @NotNull
    public Iterator<d0<T>> iterator() {
        return new f0(this.c.invoke());
    }
}
