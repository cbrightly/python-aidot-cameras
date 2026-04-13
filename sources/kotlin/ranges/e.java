package kotlin.ranges;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Ranges.kt */
public final class e implements f {
    private final float c;
    private final float d;

    @NotNull
    /* renamed from: b */
    public Float getStart() {
        return Float.valueOf(this.c);
    }

    @NotNull
    /* renamed from: a */
    public Float getEndInclusive() {
        return Float.valueOf(this.d);
    }

    public boolean c() {
        return this.c > this.d;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof e) && ((c() && ((e) other).c()) || (this.c == ((e) other).c && this.d == ((e) other).d));
    }

    public int hashCode() {
        if (c()) {
            return -1;
        }
        return (Float.valueOf(this.c).hashCode() * 31) + Float.valueOf(this.d).hashCode();
    }

    @NotNull
    public String toString() {
        return this.c + ".." + this.d;
    }
}
