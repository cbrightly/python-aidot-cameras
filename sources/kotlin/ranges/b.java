package kotlin.ranges;

import java.util.NoSuchElementException;
import kotlin.collections.o;
import kotlin.jvm.internal.k;

/* compiled from: ProgressionIterators.kt */
public final class b extends o {
    private final int c;
    private boolean d;
    private int f;
    private final int q;

    public b(char first, char last, int step) {
        this.q = step;
        this.c = last;
        boolean z = true;
        int g = k.g(first, last);
        if (step <= 0 ? g < 0 : g > 0) {
            z = false;
        }
        this.d = z;
        this.f = z ? first : last;
    }

    public boolean hasNext() {
        return this.d;
    }

    public char e() {
        int value = this.f;
        if (value != this.c) {
            this.f += this.q;
        } else if (this.d) {
            this.d = false;
        } else {
            throw new NoSuchElementException();
        }
        return (char) value;
    }
}
