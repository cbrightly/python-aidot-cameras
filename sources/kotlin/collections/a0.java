package kotlin.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Collections.kt */
public final class a0 implements List, Serializable, RandomAccess, a {
    @NotNull
    public static final a0 INSTANCE = new a0();
    private static final long serialVersionUID = -7390468764508069838L;

    public /* synthetic */ void add(int i, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void add(int i, Void voidR) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public /* synthetic */ boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean add(Void voidR) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Void remove(int i) {
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

    public /* synthetic */ Object set(int i, Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Void set(int i, Void voidR) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Object[] toArray() {
        return f.a(this);
    }

    public <T> T[] toArray(T[] tArr) {
        return f.b(this, tArr);
    }

    private a0() {
    }

    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof Void) {
            return contains((Void) obj);
        }
        return false;
    }

    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof Void) {
            return indexOf((Void) obj);
        }
        return -1;
    }

    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof Void) {
            return lastIndexOf((Void) obj);
        }
        return -1;
    }

    public final /* bridge */ int size() {
        return getSize();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof List) && ((List) other).isEmpty();
    }

    public int hashCode() {
        return 1;
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
    public Void get(int index) {
        throw new IndexOutOfBoundsException("Empty list doesn't contain element at index " + index + '.');
    }

    public int indexOf(@NotNull Void element) {
        k.e(element, "element");
        return -1;
    }

    public int lastIndexOf(@NotNull Void element) {
        k.e(element, "element");
        return -1;
    }

    @NotNull
    public Iterator iterator() {
        return z.c;
    }

    @NotNull
    public ListIterator listIterator() {
        return z.c;
    }

    @NotNull
    public ListIterator listIterator(int index) {
        if (index == 0) {
            return z.c;
        }
        throw new IndexOutOfBoundsException("Index: " + index);
    }

    @NotNull
    public List subList(int fromIndex, int toIndex) {
        if (fromIndex == 0 && toIndex == 0) {
            return this;
        }
        throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex);
    }

    private final Object readResolve() {
        return INSTANCE;
    }
}
