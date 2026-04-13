package org.glassfish.grizzly.http.server.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Enumerator<E> implements Enumeration<E> {
    private Iterator<E> iterator;

    public Enumerator(Collection<E> collection) {
        this(collection.iterator());
    }

    public Enumerator(Collection<E> collection, boolean clone) {
        this(collection.iterator(), clone);
    }

    public Enumerator(Iterable<E> iterable) {
        this(iterable.iterator());
    }

    public Enumerator(Iterable<E> iterable, boolean clone) {
        this(iterable.iterator(), clone);
    }

    public Enumerator(Iterator<E> iterator2) {
        this.iterator = null;
        this.iterator = iterator2;
    }

    public Enumerator(Iterator<E> iterator2, boolean clone) {
        this.iterator = null;
        if (!clone) {
            this.iterator = iterator2;
            return;
        }
        List<E> list = new ArrayList<>();
        while (iterator2.hasNext()) {
            list.add(iterator2.next());
        }
        this.iterator = list.iterator();
    }

    public Enumerator(Map<?, E> map) {
        this(map.values().iterator());
    }

    public Enumerator(Map<?, E> map, boolean clone) {
        this(map.values().iterator(), clone);
    }

    public boolean hasMoreElements() {
        return this.iterator.hasNext();
    }

    public E nextElement() {
        return this.iterator.next();
    }
}
