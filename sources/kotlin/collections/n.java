package kotlin.collections;

import java.util.Iterator;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Iterators.kt */
public abstract class n implements Iterator<Boolean>, a {
    public abstract boolean nextBoolean();

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @NotNull
    public final Boolean next() {
        return Boolean.valueOf(nextBoolean());
    }
}
