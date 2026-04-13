package kotlin.ranges;

import kotlin.collections.h0;
import kotlin.internal.c;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Progressions.kt */
public class j implements Iterable<Long>, kotlin.jvm.internal.markers.a {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    private final long d;
    private final long f;
    private final long q;

    public j(long start, long endInclusive, long step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        } else if (step != Long.MIN_VALUE) {
            this.d = start;
            this.f = c.d(start, endInclusive, step);
            this.q = step;
        } else {
            throw new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation.");
        }
    }

    public final long a() {
        return this.d;
    }

    public final long b() {
        return this.f;
    }

    @NotNull
    /* renamed from: e */
    public h0 iterator() {
        return new k(this.d, this.f, this.q);
    }

    /* compiled from: Progressions.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
