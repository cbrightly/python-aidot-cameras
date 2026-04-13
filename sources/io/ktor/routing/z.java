package io.ktor.routing;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import kotlin.collections.q;
import kotlin.collections.y;

/* compiled from: RoutingResolveTrace.kt */
public final class z<E> {
    private final ArrayList<E> a = new ArrayList<>();

    public final boolean a() {
        return this.a.isEmpty();
    }

    public final void d(E element) {
        this.a.add(element);
    }

    public final E c() {
        if (!this.a.isEmpty()) {
            ArrayList<E> arrayList = this.a;
            return arrayList.remove(q.i(arrayList));
        }
        throw new NoSuchElementException("Unable to pop an element from empty stack");
    }

    public final E b() {
        if (!this.a.isEmpty()) {
            return y.d0(this.a);
        }
        throw new NoSuchElementException("Unable to peek an element into empty stack");
    }
}
