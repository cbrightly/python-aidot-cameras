package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.collections.h0;

/* compiled from: ProgressionIterators.kt */
public final class k extends h0 {
    private final long c;
    private boolean d;
    private long f;
    private final long q;

    public k(long first, long last, long step) {
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

    public long nextLong() {
        long value = this.f;
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
