package org.apache.commons.math3.util;

/* compiled from: MathArrays */
public class e {

    /* compiled from: MathArrays */
    public enum a {
        INCREASING,
        DECREASING
    }

    public static double[] a(double[] source, int len) {
        double[] output = new double[len];
        System.arraycopy(source, 0, output, 0, c.q(len, source.length));
        return output;
    }
}
