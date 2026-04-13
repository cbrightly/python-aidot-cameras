package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.collections.g0;

/* compiled from: ProgressionIterators.kt */
public final class h extends g0 {
    private final int c;
    private boolean d;
    private int f;
    private final int q;

    public h(int first, int last, int step) {
        this.q = step;
        this.c = last;
        boolean z = true;
        if (step <= 0 ? first < last : first > last) {
            z = false;
        }
        this.d = z;
        this.f = z ? first : last;
    }

    public boolean hasNext() {
        return this.d;
    }

    public int nextInt() {
        int value = this.f;
        if (value != this.c) {
            this.f += this.q;
        } else if (this.d) {
            this.d = false;
        } else {
            throw new NoSuchElementException();
        }
        return value;
    }
}
