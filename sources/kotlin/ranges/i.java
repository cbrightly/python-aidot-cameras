package kotlin.ranges;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Ranges.kt */
public final class i extends g implements f<Integer> {
    /* access modifiers changed from: private */
    @NotNull
    public static final i x = new i(1, 0);
    @NotNull
    public static final a y = new a((DefaultConstructorMarker) null);

    public i(int start, int endInclusive) {
        super(start, endInclusive, 1);
    }

    @NotNull
    /* renamed from: k */
    public Integer getStart() {
        return Integer.valueOf(a());
    }

    @NotNull
    /* renamed from: i */
    public Integer getEndInclusive() {
        return Integer.valueOf(b());
    }

    public boolean h(int value) {
        return a() <= value && value <= b();
    }

    public boolean isEmpty() {
        return a() > b();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof i) && ((isEmpty() && ((i) other).isEmpty()) || (a() == ((i) other).a() && b() == ((i) other).b()));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (a() * 31) + b();
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

        @NotNull
        public final i a() {
            return i.x;
        }
    }
}
