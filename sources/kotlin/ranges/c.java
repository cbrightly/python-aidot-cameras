package kotlin.ranges;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Ranges.kt */
public final class c extends a implements f<Character> {
    @NotNull
    private static final c x = new c((char) 1, (char) 0);
    @NotNull
    public static final a y = new a((DefaultConstructorMarker) null);

    public c(char start, char endInclusive) {
        super(start, endInclusive, 1);
    }

    @NotNull
    /* renamed from: g */
    public Character getStart() {
        return Character.valueOf(a());
    }

    @NotNull
    /* renamed from: f */
    public Character getEndInclusive() {
        return Character.valueOf(b());
    }

    public boolean isEmpty() {
        return k.g(a(), b()) > 0;
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof c) && ((isEmpty() && ((c) other).isEmpty()) || (a() == ((c) other).a() && b() == ((c) other).b()));
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
    }
}
