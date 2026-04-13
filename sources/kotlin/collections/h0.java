package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Iterators.kt */
public abstract class h0 implements Iterator<Long>, a {
    public abstract long nextLong();

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @NotNull
    public final Long next() {
        return Long.valueOf(nextLong());
    }
}
