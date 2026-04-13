package org.apache.httpcore.message;

import java.util.List;
import java.util.NoSuchElementException;
import org.apache.httpcore.e;
import org.apache.httpcore.g;
import org.apache.httpcore.util.a;
import org.apache.httpcore.util.b;

/* compiled from: BasicListHeaderIterator */
public class k implements g {
    protected final List<e> c;
    protected int d = c(-1);
    protected int f = -1;
    protected String q;

    public k(List<e> headers, String name) {
        this.c = (List) a.g(headers, "Header list");
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

    public e a() {
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
