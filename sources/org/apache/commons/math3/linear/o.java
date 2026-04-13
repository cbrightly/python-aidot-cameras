package org.apache.commons.math3.linear;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.commons.math3.util.b;

/* compiled from: RealMatrixFormat */
public class o {
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;
    private final NumberFormat g;

    public o(NumberFormat format) {
        this("{", "}", "{", "}", ",", ",", format);
    }

    public o(String prefix, String suffix, String rowPrefix, String rowSuffix, String rowSeparator, String columnSeparator) {
        this(prefix, suffix, rowPrefix, rowSuffix, rowSeparator, columnSeparator, b.b());
    }

    public o(String prefix, String suffix, String rowPrefix, String rowSuffix, String rowSeparator, String columnSeparator, NumberFormat format) {
        this.a = prefix;
        this.b = suffix;
        this.c = rowPrefix;
        this.d = rowSuffix;
        this.e = rowSeparator;
        this.f = columnSeparator;
        this.g = format;
        format.setGroupingUsed(false);
    }

    public NumberFormat c() {
        return this.g;
    }

    public static o d() {
        return e(Locale.getDefault());
    }

    public static o e(Locale locale) {
        return new o(b.c(locale));
    }

    public String a(m m) {
        return b(m, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public StringBuffer b(m matrix, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        toAppendTo.append(this.a);
        int rows = matrix.getRowDimension();
        for (int i = 0; i < rows; i++) {
            toAppendTo.append(this.c);
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                if (j > 0) {
                    toAppendTo.append(this.f);
                }
                b.a(matrix.getEntry(i, j), this.g, toAppendTo, pos);
            }
            toAppendTo.append(this.d);
            if (i < rows - 1) {
                toAppendTo.append(this.e);
            }
        }
        toAppendTo.append(this.b);
        return toAppendTo;
    }
}
