package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.d;
import org.apache.http.g;
import org.apache.http.util.a;

/* compiled from: BasicHeaderIterator */
public class e implements g {
    protected final d[] c;
    protected int d = c(-1);
    protected String f;

    public e(d[] headers, String name) {
        this.c = (d[]) a.i(headers, "Header array");
        this.f = name;
    }

    /* access modifiers changed from: protected */
    public int c(int pos) {
        int from = pos;
        if (from < -1) {
            return -1;
        }
        int to = this.c.length - 1;
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
        String str = this.f;
        return str == null || str.equalsIgnoreCase(this.c[index].getName());
    }

    public boolean hasNext() {
        return this.d >= 0;
    }

    public d a() {
        int current = this.d;
        if (current >= 0) {
            this.d = c(current);
            return this.c[current];
        }
        throw new NoSuchElementException("Iteration already finished.");
    }

    public final Object next() {
        return a();
    }

    public void remove() {
        throw new UnsupportedOperationException("Removing headers is not supported.");
    }
}
