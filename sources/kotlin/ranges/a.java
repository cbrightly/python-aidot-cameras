package kotlin.ranges;

import kotlin.collections.o;
import kotlin.internal.c;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

/* compiled from: Progressions.kt */
public class a implements Iterable<Character>, kotlin.jvm.internal.markers.a {
    @NotNull
    public static final C0327a c = new C0327a((DefaultConstructorMarker) null);
    private final char d;
    private final char f;
    private final int q;

    public a(char start, char endInclusive, int step) {
        if (step == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        } else if (step != Integer.MIN_VALUE) {
            this.d = start;
            this.f = (char) c.c(start, endInclusive, step);
            this.q = step;
        } else {
            throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
        }
    }

    public final char a() {
        return this.d;
    }

    public final char b() {
        return this.f;
    }

    @NotNull
    /* renamed from: e */
    public o iterator() {
        return new b(this.d, this.f, this.q);
    }

    /* renamed from: kotlin.ranges.a$a  reason: collision with other inner class name */
    /* compiled from: Progressions.kt */
    public static final class C0327a {
        private C0327a() {
        }

        public /* synthetic */ C0327a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
