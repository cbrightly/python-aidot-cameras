package kotlin.ranges;

import kotlin.collections.g0;
import kotlin.internal.c;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Progressions.kt */
public class g implements Iterable<Integer>, kotlin.jvm.internal.markers.a {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    private final int d;
    private final int f;
    private final int q;

    public g(int start, int endInclusive, int step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        } else if (step != Integer.MIN_VALUE) {
            this.d = start;
            this.f = c.c(start, endInclusive, step);
            this.q = step;
        } else {
            throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
        }
    }

    public final int a() {
        return this.d;
    }

    public final int b() {
        return this.f;
    }

    public final int e() {
        return this.q;
    }

    @NotNull
    /* renamed from: f */
    public g0 iterator() {
        return new h(this.d, this.f, this.q);
    }

    public boolean isEmpty() {
        if (this.q > 0) {
            if (this.d > this.f) {
                return true;
            }
        } else if (this.d < this.f) {
            return true;
        }
        return false;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof g) && ((isEmpty() && ((g) other).isEmpty()) || (this.d == ((g) other).d && this.f == ((g) other).f && this.q == ((g) other).q));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (((this.d * 31) + this.f) * 31) + this.q;
    }

    @NotNull
    public String toString() {
        int i;
        StringBuilder sb;
        if (this.q > 0) {
            sb = new StringBuilder();
            sb.append(this.d);
            sb.append("..");
            sb.append(this.f);
            sb.append(" step ");
            i = this.q;
        } else {
            sb = new StringBuilder();
            sb.append(this.d);
            sb.append(" downTo ");
            sb.append(this.f);
            sb.append(" step ");
            i = -this.q;
        }
        sb.append(i);
        return sb.toString();
    }

    /* compiled from: Progressions.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final g a(int rangeStart, int rangeEnd, int step) {
            return new g(rangeStart, rangeEnd, step);
        }
    }
}
