package kotlin.ranges;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Ranges.kt */
public final class l extends j implements f<Long> {
    @NotNull
    private static final l x = new l(1, 0);
    @NotNull
    public static final a y = new a((DefaultConstructorMarker) null);

    public l(long start, long endInclusive) {
        super(start, endInclusive, 1);
    }

    @NotNull
    /* renamed from: g */
    public Long getStart() {
        return Long.valueOf(a());
    }

    @NotNull
    /* renamed from: f */
    public Long getEndInclusive() {
        return Long.valueOf(b());
    }

    public boolean isEmpty() {
        return a() > b();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof l) && ((isEmpty() && ((l) other).isEmpty()) || (a() == ((l) other).a() && b() == ((l) other).b()));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (int) ((((long) 31) * (a() ^ (a() >>> 32))) + (b() ^ (b() >>> 32)));
    }

    @NotNull
    public String toString() {
        return a() + ".." + b();
    }

    /* compiled from: Ranges.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
