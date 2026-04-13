package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AbstractList.kt */
public abstract class d<E> extends a<E> implements List<E>, kotlin.jvm.internal.markers.a {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);

    public void add(int i, E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public abstract E get(int i);

    public E remove(int i) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public E set(int i, E e) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    protected d() {
    }

    @NotNull
    public Iterator<E> iterator() {
        return new b();
    }

    public int indexOf(Object element) {
        int index$iv = 0;
        for (Object item$iv : this) {
            if (k.a(item$iv, element)) {
                return index$iv;
            }
            index$iv++;
        }
        return -1;
    }

    public int lastIndexOf(Object element) {
        ListIterator iterator$iv = listIterator(size());
        while (iterator$iv.hasPrevious()) {
            if (k.a(iterator$iv.previous(), element)) {
                return iterator$iv.nextIndex();
            }
        }
        return -1;
    }

    @NotNull
    public ListIterator<E> listIterator() {
        return new c(0);
    }

    @NotNull
    public ListIterator<E> listIterator(int index) {
        return new c(index);
    }

    @NotNull
    public List<E> subList(int fromIndex, int toIndex) {
        return new C0318d(this, fromIndex, toIndex);
    }

    /* renamed from: kotlin.collections.d$d  reason: collision with other inner class name */
    /* compiled from: AbstractList.kt */
    public static final class C0318d<E> extends d<E> implements RandomAccess {
        private int d;
        private final d<E> f;
        private final int q;

        public C0318d(@NotNull d<? extends E> list, int fromIndex, int toIndex) {
            k.e(list, "list");
            this.f = list;
            this.q = fromIndex;
            d.c.c(fromIndex, toIndex, list.size());
            this.d = toIndex - fromIndex;
        }

        public E get(int index) {
            d.c.a(index, this.d);
            return this.f.get(this.q + index);
        }

        public int a() {
            return this.d;
        }
    }

    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof List)) {
            return false;
        }
        return c.d(this, (Collection) other);
    }

    public int hashCode() {
        return c.e(this);
    }

    /* compiled from: AbstractList.kt */
    public class b implements Iterator<E>, kotlin.jvm.internal.markers.a {
        private int c;

        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public b() {
        }

        /* access modifiers changed from: protected */
        public final int b() {
            return this.c;
        }

        /* access modifiers changed from: protected */
        public final void e(int i) {
            this.c = i;
        }

        public boolean hasNext() {
            return this.c < d.this.size();
        }

        public E next() {
            if (hasNext()) {
                d dVar = d.this;
                int i = this.c;
                this.c = i + 1;
                return dVar.get(i);
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: AbstractList.kt */
    public class c extends d<E>.b implements ListIterator<E>, kotlin.jvm.internal.markers.a {
        public void add(E e) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public void set(E e) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public c(int index) {
            super();
            d.c.b(index, d.this.size());
            e(index);
        }

        public boolean hasPrevious() {
            return b() > 0;
        }

        public int nextIndex() {
            return b();
        }

        public E previous() {
            if (hasPrevious()) {
                d dVar = d.this;
                e(b() - 1);
                return dVar.get(b());
            }
            throw new NoSuchElementException();
        }

        public int previousIndex() {
            return b() - 1;
        }
    }

    /* compiled from: AbstractList.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final void a(int index, int size) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
            }
        }

        public final void b(int index, int size) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
            }
        }

        public final void c(int fromIndex, int toIndex, int size) {
            if (fromIndex < 0 || toIndex > size) {
                throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ", size: " + size);
            } else if (fromIndex > toIndex) {
                throw new IllegalArgumentException("fromIndex: " + fromIndex + " > toIndex: " + toIndex);
            }
        }

        public final int e(@NotNull Collection<?> c) {
            k.e(c, "c");
            int hashCode = 1;
            Iterator<?> it = c.iterator();
            while (it.hasNext()) {
                Object e = it.next();
                hashCode = (hashCode * 31) + (e != null ? e.hashCode() : 0);
            }
            return hashCode;
        }

        public final boolean d(@NotNull Collection<?> c, @NotNull Collection<?> other) {
            k.e(c, "c");
            k.e(other, "other");
            if (c.size() != other.size()) {
                return false;
            }
            Iterator otherIterator = other.iterator();
            for (Object elem : c) {
                if (true ^ k.a(elem, otherIterator.next())) {
                    return false;
                }
            }
            return true;
        }
    }
}
