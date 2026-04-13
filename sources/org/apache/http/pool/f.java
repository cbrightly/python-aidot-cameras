package org.apache.http.pool;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Future;
import org.apache.http.pool.c;
import org.apache.http.util.a;
import org.apache.http.util.b;

/* compiled from: RouteSpecificPool */
public abstract class f<T, C, E extends c<T, C>> {
    private final T a;
    private final Set<E> b = new HashSet();
    private final LinkedList<E> c = new LinkedList<>();
    private final LinkedList<Future<E>> d = new LinkedList<>();

    /* access modifiers changed from: protected */
    public abstract E b(C c2);

    f(T route) {
        this.a = route;
    }

    public int h() {
        return this.b.size();
    }

    public int i() {
        return this.d.size();
    }

    public int e() {
        return this.c.size();
    }

    public int d() {
        return this.c.size() + this.b.size();
    }

    public E f(Object state) {
        if (this.c.isEmpty()) {
            return null;
        }
        if (state != null) {
            Iterator<E> it = this.c.iterator();
            while (it.hasNext()) {
                E entry = (c) it.next();
                if (state.equals(entry.f())) {
                    it.remove();
                    this.b.add(entry);
                    return entry;
                }
            }
        }
        Iterator<E> it2 = this.c.iterator();
        while (it2.hasNext()) {
            E entry2 = (c) it2.next();
            if (entry2.f() == null) {
                it2.remove();
                this.b.add(entry2);
                return entry2;
            }
        }
        return null;
    }

    public E g() {
        if (!this.c.isEmpty()) {
            return (c) this.c.getLast();
        }
        return null;
    }

    public boolean l(E entry) {
        a.i(entry, "Pool entry");
        if (this.c.remove(entry) || this.b.remove(entry)) {
            return true;
        }
        return false;
    }

    public void c(E entry, boolean reusable) {
        a.i(entry, "Pool entry");
        b.b(this.b.remove(entry), "Entry %s has not been leased from this pool", entry);
        if (reusable) {
            this.c.addFirst(entry);
        }
    }

    public E a(C conn) {
        E entry = b(conn);
        this.b.add(entry);
        return entry;
    }

    public void k(Future<E> future) {
        if (future != null) {
            this.d.add(future);
        }
    }

    public Future<E> j() {
        return this.d.poll();
    }

    public void n(Future<E> future) {
        if (future != null) {
            this.d.remove(future);
        }
    }

    public void m() {
        Iterator i$ = this.d.iterator();
        while (i$.hasNext()) {
            ((Future) i$.next()).cancel(true);
        }
        this.d.clear();
        Iterator i$2 = this.c.iterator();
        while (i$2.hasNext()) {
            ((c) i$2.next()).a();
        }
        this.c.clear();
        for (E entry : this.b) {
            entry.a();
        }
        this.b.clear();
    }

    public String toString() {
        return "[route: " + this.a + "][leased: " + this.b.size() + "][available: " + this.c.size() + "][pending: " + this.d.size() + "]";
    }
}
