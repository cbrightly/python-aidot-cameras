package kotlin.random;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Random.kt */
public final class e {
    public static final int e(int value) {
        return 31 - Integer.numberOfLeadingZeros(value);
    }

    public static final int f(int $this$takeUpperBits, int bitCount) {
        return ($this$takeUpperBits >>> (32 - bitCount)) & ((-bitCount) >> 31);
    }

    public static final void c(int from, int until) {
        if (!(until > from)) {
            throw new IllegalArgumentException(a(Integer.valueOf(from), Integer.valueOf(until)).toString());
        }
    }

    public static final void d(long from, long until) {
        if (!(until > from)) {
            throw new IllegalArgumentException(a(Long.valueOf(from), Long.valueOf(until)).toString());
        }
    }

    public static final void b(double from, double until) {
        if (!(until > from)) {
            throw new IllegalArgumentException(a(Double.valueOf(from), Double.valueOf(until)).toString());
        }
    }

    @NotNull
    public static final String a(@NotNull Object from, @NotNull Object until) {
        k.e(from, "from");
        k.e(until, "until");
        return "Random range is empty: [" + from + ", " + until + ").";
    }
}
