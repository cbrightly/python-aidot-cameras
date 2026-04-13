package org.spongycastle.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionStore<T> implements Store<T>, Iterable<T> {
    private Collection<T> c;

    public Collection<T> a(Selector<T> selector) {
        if (selector == null) {
            return new ArrayList(this.c);
        }
        List<T> col = new ArrayList<>();
        for (T obj : this.c) {
            if (selector.P0(obj)) {
                col.add(obj);
            }
        }
        return col;
    }

    public Iterator<T> iterator() {
        return a((Selector) null).iterator();
    }
}
