package io.reactivex.internal.util;

/* compiled from: OpenHashSet */
public final class i<T> {
    final float a;
    int b;
    int c;
    int d;
    T[] e;

    public i() {
        this(16, 0.75f);
    }

    public i(int capacity, float loadFactor) {
        this.a = loadFactor;
        int c2 = j.a(capacity);
        this.b = c2 - 1;
        this.d = (int) (((float) c2) * loadFactor);
        this.e = new Object[c2];
    }

    public boolean a(T value) {
        T curr;
        T[] a2 = this.e;
        int m = this.b;
        int pos = c(value.hashCode()) & m;
        T curr2 = a2[pos];
        if (curr2 != null) {
            if (curr2.equals(value)) {
                return false;
            }
            do {
                pos = (pos + 1) & m;
                curr = a2[pos];
                if (curr == null) {
                }
            } while (!curr.equals(value));
            return false;
        }
        a2[pos] = value;
        int i = this.c + 1;
        this.c = i;
        if (i >= this.d) {
            d();
        }
        return true;
    }

    public boolean e(T value) {
        T curr;
        T[] a2 = this.e;
        int m = this.b;
        int pos = c(value.hashCode()) & m;
        T curr2 = a2[pos];
        if (curr2 == null) {
            return false;
        }
        if (curr2.equals(value)) {
            return f(pos, a2, m);
        }
        do {
            pos = (pos + 1) & m;
            curr = a2[pos];
            if (curr == null) {
                return false;
            }
        } while (!curr.equals(value));
        return f(pos, a2, m);
    }

    /* access modifiers changed from: package-private */
    public boolean f(int pos, T[] a2, int m) {
        T curr;
        this.c--;
        while (true) {
            int last = pos;
            pos = (pos + 1) & m;
            while (true) {
                curr = a2[pos];
                if (curr == null) {
                    a2[last] = null;
                    return true;
                }
                int slot = c(curr.hashCode()) & m;
                if (last <= pos) {
                    if (last >= slot || slot > pos) {
                        break;
                    }
                    pos = (pos + 1) & m;
                } else {
                    if (last >= slot && slot > pos) {
                        break;
                    }
                    pos = (pos + 1) & m;
                }
            }
            a2[last] = curr;
        }
    }

    /* access modifiers changed from: package-private */
    public void d() {
        T[] a2 = this.e;
        int i = a2.length;
        int newCap = i << 1;
        int m = newCap - 1;
        T[] b2 = new Object[newCap];
        int pos = this.c;
        while (true) {
            int j = pos - 1;
            if (pos != 0) {
                do {
                    i--;
                } while (a2[i] == null);
                int pos2 = c(a2[i].hashCode()) & m;
                if (b2[pos2] != null) {
                    do {
                        pos2 = (pos2 + 1) & m;
                    } while (b2[pos2] != null);
                }
                b2[pos2] = a2[i];
                pos = j;
            } else {
                this.b = m;
                this.d = (int) (((float) newCap) * this.a);
                this.e = b2;
                return;
            }
        }
    }

    static int c(int x) {
        int h = -1640531527 * x;
        return (h >>> 16) ^ h;
    }

    public Object[] b() {
        return this.e;
    }
}
