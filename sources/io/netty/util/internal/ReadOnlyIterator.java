package io.netty.util.internal;

import java.util.Iterator;

public final class ReadOnlyIterator<T> implements Iterator<T> {
    private final Iterator<? extends T> iterator;

    public ReadOnlyIterator(Iterator<? extends T> iterator2) {
        if (iterator2 != null) {
            this.iterator = iterator2;
            return;
        }
        throw new NullPointerException("iterator");
    }

    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    public T next() {
        return this.iterator.next();
    }

    public void remove() {
        throw new UnsupportedOperationException("read-only");
    }
}
