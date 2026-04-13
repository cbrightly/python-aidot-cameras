package kotlin.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.a;

/* compiled from: AbstractIterator.kt */
public abstract class c<T> implements Iterator<T>, a {
    private q0 c = q0.NotReady;
    private T d;

    /* access modifiers changed from: protected */
    public abstract void b();

    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public boolean hasNext() {
        q0 q0Var = this.c;
        if (q0Var != q0.Failed) {
            switch (b.a[q0Var.ordinal()]) {
                case 1:
                    return false;
                case 2:
                    return true;
                default:
                    return g();
            }
        } else {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    public T next() {
        if (hasNext()) {
            this.c = q0.NotReady;
            return this.d;
        }
        throw new NoSuchElementException();
    }

    private final boolean g() {
        this.c = q0.Failed;
        b();
        return this.c == q0.Ready;
    }

    /* access modifiers changed from: protected */
    public final void f(T value) {
        this.d = value;
        this.c = q0.Ready;
    }

    /* access modifiers changed from: protected */
    public final void e() {
        this.c = q0.Done;
    }
}
