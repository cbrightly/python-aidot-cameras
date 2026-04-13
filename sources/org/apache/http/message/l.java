package org.apache.http.message;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.http.d;
import org.apache.http.g;
import org.apache.http.util.a;
import org.apache.http.util.b;

/* compiled from: BasicListHeaderIterator */
public class l implements g {
    protected final List<d> c;
    protected int d = c(-1);
    protected int f = -1;
    protected String q;

    public l(List<d> headers, String name) {
        this.c = (List) a.i(headers, "Header list");
        this.q = name;
    }

    /* access modifiers changed from: protected */
    public int c(int pos) {
        int from = pos;
        if (from < -1) {
            return -1;
        }
        int to = this.c.size() - 1;
        boolean found = false;
        while (!found && from < to) {
            from++;
            found = b(from);
        }
        if (found) {
            return from;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public boolean b(int index) {
        if (this.q == null) {
            return true;
        }
        return this.q.equalsIgnoreCase(this.c.get(index).getName());
    }

    public boolean hasNext() {
        return this.d >= 0;
    }

    public d a() {
        int current = this.d;
        if (current >= 0) {
            this.f = current;
            this.d = c(current);
            return this.c.get(current);
        }
        throw new NoSuchElementException("Iteration already finished.");
    }

    public final Object next() {
        return a();
    }

    public void remove() {
        b.a(this.f >= 0, "No header to remove");
        this.c.remove(this.f);
        this.f = -1;
        this.d--;
    }
}
