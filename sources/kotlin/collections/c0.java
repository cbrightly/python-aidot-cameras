package kotlin.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Sets.kt */
public final class c0 implements Set, Serializable, a {
    @NotNull
    public static final c0 INSTANCE = new c0();
    private static final long serialVersionUID = 3406603774387020532L;

    public /* synthetic */ boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean add(Void voidR) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Object[] toArray() {
        return f.a(this);
    }

    public <T> T[] toArray(T[] tArr) {
        return f.b(this, tArr);
    }

    private c0() {
    }

    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof Void) {
            return contains((Void) obj);
        }
        return false;
    }

    public final /* bridge */ int size() {
        return getSize();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof Set) && ((Set) other).isEmpty();
    }

    public int hashCode() {
        return 0;
    }

    @NotNull
    public String toString() {
        return "[]";
    }

    public int getSize() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean contains(@NotNull Void element) {
        k.e(element, "element");
        return false;
    }

    public boolean containsAll(@NotNull Collection elements) {
        k.e(elements, "elements");
        return elements.isEmpty();
    }

    @NotNull
    public Iterator iterator() {
        return z.c;
    }

    private final Object readResolve() {
        return INSTANCE;
    }
}
