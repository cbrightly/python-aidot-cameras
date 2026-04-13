package org.apache.commons.math3.util;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.Locale;

/* compiled from: CompositeFormat */
public class b {
    public static NumberFormat b() {
        return c(Locale.getDefault());
    }

    public static NumberFormat c(Locale locale) {
        NumberFormat nf = NumberFormat.getInstance(locale);
        nf.setMaximumFractionDigits(10);
        return nf;
    }

    public static StringBuffer a(double value, NumberFormat format, StringBuffer toAppendTo, FieldPosition pos) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            toAppendTo.append('(');
            toAppendTo.append(value);
            toAppendTo.append(')');
        } else {
            format.format(value, toAppendTo, pos);
        }
        return toAppendTo;
    }
}
