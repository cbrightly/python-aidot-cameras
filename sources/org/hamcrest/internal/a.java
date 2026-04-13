package org.hamcrest.internal;

import java.lang.reflect.Array;
import java.util.Iterator;

/* compiled from: ArrayIterator */
public class a implements Iterator<Object> {
    private final Object c;
    private int d = 0;

    public a(Object array) {
        if (array.getClass().isArray()) {
            this.c = array;
            return;
        }
        throw new IllegalArgumentException("not an array");
    }

    public boolean hasNext() {
        return this.d < Array.getLength(this.c);
    }

    public Object next() {
        Object obj = this.c;
        int i = this.d;
        this.d = i + 1;
        return Array.get(obj, i);
    }

    public void remove() {
        throw new UnsupportedOperationException("cannot remove items from an array");
    }
}
