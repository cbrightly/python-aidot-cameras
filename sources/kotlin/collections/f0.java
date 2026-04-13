package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Iterators.kt */
public final class f0<T> implements Iterator<d0<? extends T>>, a {
    private int c;
    private final Iterator<T> d;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public f0(@NotNull Iterator<? extends T> iterator) {
        k.e(iterator, "iterator");
        this.d = iterator;
    }

    public final boolean hasNext() {
        return this.d.hasNext();
    }

    @NotNull
    /* renamed from: b */
    public final d0<T> next() {
        int i = this.c;
        this.c = i + 1;
        if (i < 0) {
            q.q();
        }
        return new d0<>(i, this.d.next());
    }
}
