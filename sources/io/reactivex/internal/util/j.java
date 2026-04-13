package io.reactivex.internal.util;

/* compiled from: Pow2 */
public final class j {
    public static int a(int value) {
        return 1 << (32 - Integer.numberOfLeadingZeros(value - 1));
    }
}
