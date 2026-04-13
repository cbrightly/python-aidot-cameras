package org.apache.commons.math3.util;

/* compiled from: Precision */
public class g {
    public static final double a = Double.longBitsToDouble(4368491638549381120L);
    public static final double b = Double.longBitsToDouble(4503599627370496L);
    private static final long c = Double.doubleToRawLongBits(0.0d);
    private static final long d = Double.doubleToRawLongBits(-0.0d);
    private static final int e = Float.floatToRawIntBits(0.0f);
    private static final int f = Float.floatToRawIntBits(-0.0f);

    public static boolean a(double x, double y, double eps) {
        return b(x, y, 1) || c.a(y - x) <= eps;
    }

    public static boolean c(double x, double y, double eps) {
        if (b(x, y, 1)) {
            return true;
        }
        if (c.a((x - y) / c.o(c.a(x), c.a(y))) <= eps) {
            return true;
        }
        return false;
    }

    public static boolean b(double x, double y, int maxUlps) {
        boolean isEqual;
        long deltaMinus;
        long deltaPlus;
        int i = maxUlps;
        long xInt = Double.doubleToRawLongBits(x);
        long yInt = Double.doubleToRawLongBits(y);
        if (((xInt ^ yInt) & Long.MIN_VALUE) == 0) {
            isEqual = c.b(xInt - yInt) <= ((long) i);
        } else {
            if (xInt < yInt) {
                deltaPlus = yInt - c;
                deltaMinus = xInt - d;
            } else {
                deltaPlus = xInt - c;
                deltaMinus = yInt - d;
            }
            if (deltaPlus > ((long) i)) {
                isEqual = false;
            } else {
                isEqual = deltaMinus <= ((long) i) - deltaPlus;
            }
        }
        if (!isEqual || Double.isNaN(x) || Double.isNaN(y)) {
            return false;
        }
        return true;
    }
}
