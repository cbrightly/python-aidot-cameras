package kotlin.collections.builders;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.g;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MapBuilder.kt */
public final class e<E> extends g<E> implements Set<E>, kotlin.jvm.internal.markers.e {
    private final c<E, ?> c;

    public e(@NotNull c<E, ?> backing) {
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
        return this.c.containsKey(element);
    }

    public void clear() {
        this.c.clear();
    }

    public boolean add(E element) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(@NotNull Collection<? extends E> elements) {
        k.e(elements, "elements");
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object element) {
        return this.c.K(element) >= 0;
    }

    @NotNull
    public Iterator<E> iterator() {
        return this.c.C();
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
