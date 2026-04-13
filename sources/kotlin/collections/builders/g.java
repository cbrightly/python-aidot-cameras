package kotlin.collections.builders;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.e;
import org.jetbrains.annotations.NotNull;

/* compiled from: SetBuilder.kt */
public final class g<E> extends kotlin.collections.g<E> implements Set<E>, e {
    private final c<E, ?> c;

    public g(@NotNull c<E, ?> backing) {
        k.e(backing, "backing");
        this.c = backing;
    }

    public g() {
        this(new c());
    }

    @NotNull
    public final Set<E> b() {
        this.c.k();
        return this;
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
        return this.c.i(element) >= 0;
    }

    public boolean remove(Object element) {
        return this.c.K(element) >= 0;
    }

    @NotNull
    public Iterator<E> iterator() {
        return this.c.C();
    }

    public boolean addAll(@NotNull Collection<? extends E> elements) {
        k.e(elements, "elements");
        this.c.l();
        return super.addAll(elements);
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
