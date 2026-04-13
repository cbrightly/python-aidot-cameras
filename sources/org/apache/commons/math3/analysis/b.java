package org.apache.commons.math3.analysis;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.util.d;

/* compiled from: FunctionUtils */
public class b {

    /* compiled from: FunctionUtils */
    public static final class a implements c {
        final /* synthetic */ a a;
        final /* synthetic */ double b;

        a(a aVar, double d) {
            this.a = aVar;
            this.b = d;
        }

        public double value(double x) {
            return this.a.a(x, this.b);
        }
    }

    public static c a(a f, double fixed) {
        return new a(f, fixed);
    }

    public static double[] b(c f, double min, double max, int n) {
        if (n <= 0) {
            throw new NotStrictlyPositiveException(d.NOT_POSITIVE_NUMBER_OF_SAMPLES, Integer.valueOf(n));
        } else if (min < max) {
            double[] s = new double[n];
            double h = (max - min) / ((double) n);
            for (int i = 0; i < n; i++) {
                s[i] = f.value((((double) i) * h) + min);
            }
            return s;
        } else {
            throw new NumberIsTooLargeException(Double.valueOf(min), Double.valueOf(max), false);
        }
    }
}
