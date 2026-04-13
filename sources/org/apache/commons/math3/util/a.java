package org.apache.commons.math3.util;

/* compiled from: ArithmeticUtils */
public final class a {
    public static boolean a(long n) {
        return n > 0 && ((n - 1) & n) == 0;
    }
}
