package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.ParseException;
import org.apache.http.g;
import org.apache.http.util.a;
import org.apache.http.z;

/* compiled from: BasicTokenIterator */
public class p implements z {
    protected final g c;
    protected String d;
    protected String f;
    protected int q = c(-1);

    public p(g headerIterator) {
        this.c = (g) a.i(headerIterator, "Header iterator");
    }

    public boolean hasNext() {
        return this.f != null;
    }

    public String nextToken() {
        if (this.f != null) {
            String result = this.f;
            this.q = c(this.q);
            return result;
        }
        throw new NoSuchElementException("Iteration already finished.");
    }

    public final Object next() {
        return nextToken();
    }

    public final void remove() {
        throw new UnsupportedOperationException("Removing tokens is not supported.");
    }

    /* access modifiers changed from: protected */
    public int c(int pos) {
        int from;
        int from2 = pos;
        if (from2 >= 0) {
            from = e(from2);
        } else if (!this.c.hasNext()) {
            return -1;
        } else {
            this.d = this.c.a().getValue();
            from = 0;
        }
        int start = f(from);
        if (start < 0) {
            this.f = null;
            return -1;
        }
        int end = d(start);
        this.f = b(this.d, start, end);
        return end;
    }

    /* access modifiers changed from: protected */
    public String b(String value, int start, int end) {
        return value.substring(start, end);
    }

    /* access modifiers changed from: protected */
    public int f(int pos) {
        int from = a.g(pos, "Search position");
        boolean found = false;
        while (!found) {
            String str = this.d;
            if (str == null) {
                break;
            }
            int to = str.length();
            while (!found && from < to) {
                char ch = this.d.charAt(from);
                if (i(ch) || j(ch)) {
                    from++;
                } else if (h(this.d.charAt(from))) {
                    found = true;
                } else {
                    throw new ParseException("Invalid character before token (pos " + from + "): " + this.d);
                }
            }
            if (!found) {
                if (this.c.hasNext()) {
                    this.d = this.c.a().getValue();
                    from = 0;
                } else {
                    this.d = null;
                }
            }
        }
        if (found) {
            return from;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public int e(int pos) {
        int from = a.g(pos, "Search position");
        boolean found = false;
        int to = this.d.length();
        while (!found && from < to) {
            char ch = this.d.charAt(from);
            if (i(ch)) {
                found = true;
            } else if (j(ch)) {
                from++;
            } else if (h(ch)) {
                throw new ParseException("Tokens without separator (pos " + from + "): " + this.d);
            } else {
                throw new ParseException("Invalid character after token (pos " + from + "): " + this.d);
            }
        }
        return from;
    }

    /* access modifiers changed from: protected */
    public int d(int from) {
        a.g(from, "Search position");
        int to = this.d.length();
        int end = from + 1;
        while (end < to && h(this.d.charAt(end))) {
            end++;
        }
        return end;
    }

    /* access modifiers changed from: protected */
    public boolean i(char ch) {
        return ch == ',';
    }

    /* access modifiers changed from: protected */
    public boolean j(char ch) {
        return ch == 9 || Character.isSpaceChar(ch);
    }

    /* access modifiers changed from: protected */
    public boolean h(char ch) {
        if (Character.isLetterOrDigit(ch)) {
            return true;
        }
        if (!Character.isISOControl(ch) && !g(ch)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean g(char ch) {
        return " ,;=()<>@:\\\"/[]?{}\t".indexOf(ch) >= 0;
    }
}
