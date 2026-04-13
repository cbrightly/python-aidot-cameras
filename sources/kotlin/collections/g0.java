package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Iterators.kt */
public abstract class g0 implements Iterator<Integer>, a {
    public abstract int nextInt();

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @NotNull
    public final Integer next() {
        return Integer.valueOf(nextInt());
    }
}
