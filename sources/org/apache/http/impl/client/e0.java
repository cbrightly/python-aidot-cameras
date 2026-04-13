package org.apache.http.impl.client;

import java.net.URI;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: RedirectLocations */
public class e0 extends AbstractList<Object> {
    private final Set<URI> c = new HashSet();
    private final List<URI> d = new ArrayList();

    public boolean b(URI uri) {
        return this.c.contains(uri);
    }

    public void a(URI uri) {
        this.c.add(uri);
        this.d.add(uri);
    }

    /* renamed from: d */
    public URI get(int index) {
        return this.d.get(index);
    }

    public int size() {
        return this.d.size();
    }

    public Object set(int index, Object element) {
        URI removed = this.d.set(index, (URI) element);
        this.c.remove(removed);
        this.c.add((URI) element);
        if (this.d.size() != this.c.size()) {
            this.c.addAll(this.d);
        }
        return removed;
    }

    public void add(int index, Object element) {
        this.d.add(index, (URI) element);
        this.c.add((URI) element);
    }

    /* renamed from: e */
    public URI remove(int index) {
        URI removed = this.d.remove(index);
        this.c.remove(removed);
        if (this.d.size() != this.c.size()) {
            this.c.addAll(this.d);
        }
        return removed;
    }

    public boolean contains(Object o) {
        return this.c.contains(o);
    }
}
