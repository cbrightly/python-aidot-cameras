package kotlin.jvm.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.jetbrains.annotations.NotNull;

/* compiled from: ArrayIterator.kt */
public final class a<T> implements Iterator<T>, kotlin.jvm.internal.markers.a {
    private int c;
    @NotNull
    private final T[] d;

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public a(@NotNull T[] array) {
        k.e(array, "array");
        this.d = array;
    }

    public boolean hasNext() {
        return this.c < this.d.length;
    }

    public T next() {
        try {
            T[] tArr = this.d;
            int i = this.c;
            this.c = i + 1;
            return tArr[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            this.c--;
            throw new NoSuchElementException(e.getMessage());
        }
    }
}
