package org.apache.httpcore.protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/* compiled from: ChainBuilder */
public final class b<E> {
    private final LinkedList<E> a = new LinkedList<>();
    private final Map<Class<?>, E> b = new HashMap();

    private void e(E e) {
        E previous = this.b.remove(e.getClass());
        if (previous != null) {
            this.a.remove(previous);
        }
        this.b.put(e.getClass(), e);
    }

    public b<E> b(E e) {
        if (e == null) {
            return this;
        }
        e(e);
        this.a.addFirst(e);
        return this;
    }

    public b<E> c(E e) {
        if (e == null) {
            return this;
        }
        e(e);
        this.a.addLast(e);
        return this;
    }

    public b<E> a(E... c) {
        if (c == null) {
            return this;
        }
        for (E e : c) {
            c(e);
        }
        return this;
    }

    public LinkedList<E> d() {
        return new LinkedList<>(this.a);
    }
}
