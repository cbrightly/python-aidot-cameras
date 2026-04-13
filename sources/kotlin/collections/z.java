package kotlin.collections;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: Collections.kt */
public final class z implements ListIterator, a {
    @NotNull
    public static final z c = new z();

    public /* synthetic */ void add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public /* synthetic */ void set(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    private z() {
    }

    public boolean hasNext() {
        return false;
    }

    public boolean hasPrevious() {
        return false;
    }

    public int nextIndex() {
        return 0;
    }

    public int previousIndex() {
        return -1;
    }

    @NotNull
    /* renamed from: b */
    public Void next() {
        throw new NoSuchElementException();
    }

    @NotNull
    /* renamed from: e */
    public Void previous() {
        throw new NoSuchElementException();
    }
}
