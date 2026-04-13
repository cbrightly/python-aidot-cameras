package kotlin.ranges;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Ranges.kt */
public final class d implements f {
    private final double c;
    private final double d;

    @NotNull
    /* renamed from: b */
    public Double getStart() {
        return Double.valueOf(this.c);
    }

    @NotNull
    /* renamed from: a */
    public Double getEndInclusive() {
        return Double.valueOf(this.d);
    }

    public boolean c() {
        return this.c > this.d;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof d) && ((c() && ((d) other).c()) || (this.c == ((d) other).c && this.d == ((d) other).d));
    }

    public int hashCode() {
        if (c()) {
            return -1;
        }
        return (Double.valueOf(this.c).hashCode() * 31) + Double.valueOf(this.d).hashCode();
    }

    @NotNull
    public String toString() {
        return this.c + ".." + this.d;
    }
}
