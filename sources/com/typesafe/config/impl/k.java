package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.f;
import com.typesafe.config.j;
import java.io.Serializable;

/* compiled from: ConfigNumber */
public abstract class k extends AbstractConfigValue implements Serializable {
    private static final long serialVersionUID = 2;
    protected final String originalText;

    /* access modifiers changed from: protected */
    public abstract double doubleValue();

    /* access modifiers changed from: protected */
    public abstract long longValue();

    public abstract Number unwrapped();

    public abstract /* synthetic */ com.typesafe.config.k valueType();

    protected k(f origin, String originalText2) {
        super(origin);
        this.originalText = originalText2;
    }

    /* access modifiers changed from: package-private */
    public String transformToString() {
        return this.originalText;
    }

    /* access modifiers changed from: package-private */
    public int intValueRangeChecked(String path) {
        long l = longValue();
        if (l >= -2147483648L && l <= 2147483647L) {
            return (int) l;
        }
        f0 origin = origin();
        throw new ConfigException.WrongType(origin, path, "32-bit integer", "out-of-range value " + l);
    }

    private boolean f() {
        return ((double) longValue()) == doubleValue();
    }

    /* access modifiers changed from: protected */
    public boolean canEqual(Object other) {
        return other instanceof k;
    }

    public boolean equals(Object other) {
        if (!(other instanceof k) || !canEqual(other)) {
            return false;
        }
        k n = (k) other;
        if (f()) {
            if (!n.f() || longValue() != n.longValue()) {
                return false;
            }
            return true;
        } else if (n.f() || doubleValue() != n.doubleValue()) {
            return false;
        } else {
            return true;
        }
    }

    public int hashCode() {
        long asLong;
        if (f()) {
            asLong = longValue();
        } else {
            asLong = Double.doubleToLongBits(doubleValue());
        }
        return (int) ((asLong >>> 32) ^ asLong);
    }

    static k newNumber(f origin, long number, String originalText2) {
        if (number > 2147483647L || number < -2147483648L) {
            return new i(origin, number, originalText2);
        }
        return new h(origin, (int) number, originalText2);
    }

    static k newNumber(f origin, double number, String originalText2) {
        long asLong = (long) number;
        if (((double) asLong) == number) {
            return newNumber(origin, asLong, originalText2);
        }
        return new e(origin, number, originalText2);
    }

    private Object writeReplace() {
        return new b0((j) this);
    }
}
