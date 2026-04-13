package org.apache.commons.math3.linear;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.Locale;
import org.apache.commons.math3.util.b;

/* compiled from: RealVectorFormat */
public class s {
    private final String a;
    private final String b;
    private final String c;
    private final String d;
    private final String e;
    private final String f;
    private final NumberFormat g;

    public s(NumberFormat format) {
        this("{", "}", "; ", format);
    }

    public s(String prefix, String suffix, String separator, NumberFormat format) {
        this.a = prefix;
        this.b = suffix;
        this.c = separator;
        this.d = prefix.trim();
        this.e = suffix.trim();
        this.f = separator.trim();
        this.g = format;
    }

    public static s c() {
        return d(Locale.getDefault());
    }

    public static s d(Locale locale) {
        return new s(b.c(locale));
    }

    public String a(q v) {
        return b(v, new StringBuffer(), new FieldPosition(0)).toString();
    }

    public StringBuffer b(q vector, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        toAppendTo.append(this.a);
        for (int i = 0; i < vector.getDimension(); i++) {
            if (i > 0) {
                toAppendTo.append(this.c);
            }
            b.a(vector.getEntry(i), this.g, toAppendTo, pos);
        }
        toAppendTo.append(this.b);
        return toAppendTo;
    }
}
