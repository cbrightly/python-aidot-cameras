package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import kotlin.jvm.internal.b;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Collections.kt */
public final class h<T> implements Collection<T>, a {
    @NotNull
    private final T[] c;
    private final boolean d;

    public boolean add(T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public <T> T[] toArray(T[] tArr) {
        return f.b(this, tArr);
    }

    public h(@NotNull T[] values, boolean isVarargs) {
        k.e(values, "values");
        this.c = values;
        this.d = isVarargs;
    }

    public final /* bridge */ int size() {
        return a();
    }

    public int a() {
        return this.c.length;
    }

    public boolean isEmpty() {
        return this.c.length == 0;
    }

    public boolean contains(Object element) {
        return l.r(this.c, element);
    }

    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        k.e(elements, "elements");
        Collection<? extends Object> collection = elements;
        if (collection.isEmpty()) {
            return true;
        }
        for (Object it : collection) {
            if (!contains(it)) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public Iterator<T> iterator() {
        return b.a(this.c);
    }

    @NotNull
    public final Object[] toArray() {
        return p.a(this.c, this.d);
    }
}
