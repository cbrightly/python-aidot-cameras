package kotlin;

import kotlin.jvm.internal.k;
import kotlin.text.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: UnsignedUtils.kt */
public final class z {
    @NotNull
    public static final String a(long v, int base) {
        if (v >= 0) {
            String l = Long.toString(v, a.a(base));
            k.d(l, "java.lang.Long.toString(this, checkRadix(radix))");
            return l;
        }
        long quotient = ((v >>> 1) / ((long) base)) << 1;
        long rem = v - (((long) base) * quotient);
        if (rem >= ((long) base)) {
            rem -= (long) base;
            quotient++;
        }
        StringBuilder sb = new StringBuilder();
        String l2 = Long.toString(quotient, a.a(base));
        k.d(l2, "java.lang.Long.toString(this, checkRadix(radix))");
        sb.append(l2);
        String l3 = Long.toString(rem, a.a(base));
        k.d(l3, "java.lang.Long.toString(this, checkRadix(radix))");
        sb.append(l3);
        return sb.toString();
    }
}
