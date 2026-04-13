package org.hamcrest.internal;

import java.util.Iterator;
import org.hamcrest.d;

/* compiled from: SelfDescribingValueIterator */
public class c<T> implements Iterator<d> {
    private Iterator<T> c;

    public c(Iterator<T> values) {
        this.c = values;
    }

    public boolean hasNext() {
        return this.c.hasNext();
    }

    /* renamed from: b */
    public d next() {
        return new b(this.c.next());
    }

    public void remove() {
        this.c.remove();
    }
}
