package kotlin.collections.builders;

import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.e;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.b;
import org.jetbrains.annotations.NotNull;

/* compiled from: MapBuilder.kt */
public final class f<V> extends e<V> implements Collection<V>, b {
    @NotNull
    private final c<?, V> c;

    public f(@NotNull c<?, V> backing) {
        k.e(backing, "backing");
        this.c = backing;
    }

    public int a() {
        return this.c.size();
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    public boolean contains(Object element) {
        return this.c.containsValue(element);
    }

    public boolean add(V element) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(@NotNull Collection<? extends V> elements) {
        k.e(elements, "elements");
        throw new UnsupportedOperationException();
    }

    public void clear() {
        this.c.clear();
    }

    @NotNull
    public Iterator<V> iterator() {
        return this.c.N();
    }

    public boolean remove(Object element) {
        return this.c.M(element);
    }

    public boolean removeAll(@NotNull Collection<? extends Object> elements) {
        k.e(elements, "elements");
        this.c.l();
        return super.removeAll(elements);
    }

    public boolean retainAll(@NotNull Collection<? extends Object> elements) {
        k.e(elements, "elements");
        this.c.l();
        return super.retainAll(elements);
    }
}
