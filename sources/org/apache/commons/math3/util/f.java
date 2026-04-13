package org.apache.commons.math3.util;

import java.util.Arrays;
import org.apache.commons.math3.exception.NullArgumentException;

/* compiled from: MathUtils */
public final class f {
    public static int c(double value) {
        return new Double(value).hashCode();
    }

    public static boolean b(double x, double y) {
        return new Double(x).equals(new Double(y));
    }

    public static int d(double[] value) {
        return Arrays.hashCode(value);
    }

    public static void a(Object o) {
        if (o == null) {
            throw new NullArgumentException();
        }
    }
}
