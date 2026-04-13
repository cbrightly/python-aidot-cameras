package com.squareup.moshi;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* compiled from: JsonValueWriter */
public final class n extends o {
    @Nullable
    private String a2;
    Object[] p1 = new Object[32];

    n() {
        x(6);
    }

    public o a() {
        if (!this.p0) {
            int i = this.c;
            int i2 = this.a1;
            if (i == i2 && this.d[i - 1] == 1) {
                this.a1 = ~i2;
                return this;
            }
            i();
            List<Object> list = new ArrayList<>();
            u0(list);
            Object[] objArr = this.p1;
            int i3 = this.c;
            objArr[i3] = list;
            this.q[i3] = 0;
            x(1);
            return this;
        }
        throw new IllegalStateException("Array cannot be used as a map key in JSON at path " + getPath());
    }

    public o j() {
        if (u() == 1) {
            int i = this.c;
            int i2 = this.a1;
            if (i == (~i2)) {
                this.a1 = ~i2;
                return this;
            }
            int i3 = i - 1;
            this.c = i3;
            this.p1[i3] = null;
            int[] iArr = this.q;
            int i4 = i3 - 1;
            iArr[i4] = iArr[i4] + 1;
            return this;
        }
        throw new IllegalStateException("Nesting problem.");
    }

    public o g() {
        if (!this.p0) {
            int i = this.c;
            int i2 = this.a1;
            if (i == i2 && this.d[i - 1] == 3) {
                this.a1 = ~i2;
                return this;
            }
            i();
            Map<String, Object> map = new p<>();
            u0(map);
            this.p1[this.c] = map;
            x(3);
            return this;
        }
        throw new IllegalStateException("Object cannot be used as a map key in JSON at path " + getPath());
    }

    public o m() {
        if (u() != 3) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.a2 == null) {
            int i = this.c;
            int i2 = this.a1;
            if (i == (~i2)) {
                this.a1 = ~i2;
                return this;
            }
            this.p0 = false;
            int i3 = i - 1;
            this.c = i3;
            this.p1[i3] = null;
            this.f[i3] = null;
            int[] iArr = this.q;
            int i4 = i3 - 1;
            iArr[i4] = iArr[i4] + 1;
            return this;
        } else {
            throw new IllegalStateException("Dangling name: " + this.a2);
        }
    }

    public o r(String name) {
        if (name == null) {
            throw new NullPointerException("name == null");
        } else if (this.c == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        } else if (u() == 3 && this.a2 == null && !this.p0) {
            this.a2 = name;
            this.f[this.c - 1] = name;
            return this;
        } else {
            throw new IllegalStateException("Nesting problem.");
        }
    }

    public o W(@Nullable String value) {
        if (this.p0) {
            this.p0 = false;
            return r(value);
        }
        u0(value);
        int[] iArr = this.q;
        int i = this.c - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    public o s() {
        if (!this.p0) {
            u0((Object) null);
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return this;
        }
        throw new IllegalStateException("null cannot be used as a map key in JSON at path " + getPath());
    }

    public o o0(boolean value) {
        if (!this.p0) {
            u0(Boolean.valueOf(value));
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return this;
        }
        throw new IllegalStateException("Boolean cannot be used as a map key in JSON at path " + getPath());
    }

    public o J(double value) {
        if (!this.y && (Double.isNaN(value) || value == Double.NEGATIVE_INFINITY || value == Double.POSITIVE_INFINITY)) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        } else if (this.p0) {
            this.p0 = false;
            return r(Double.toString(value));
        } else {
            u0(Double.valueOf(value));
            int[] iArr = this.q;
            int i = this.c - 1;
            iArr[i] = iArr[i] + 1;
            return this;
        }
    }

    public o P(long value) {
        if (this.p0) {
            this.p0 = false;
            return r(Long.toString(value));
        }
        u0(Long.valueOf(value));
        int[] iArr = this.q;
        int i = this.c - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    public o T(@Nullable Number value) {
        if ((value instanceof Byte) || (value instanceof Short) || (value instanceof Integer) || (value instanceof Long)) {
            return P(value.longValue());
        }
        if ((value instanceof Float) || (value instanceof Double)) {
            return J(value.doubleValue());
        }
        if (value == null) {
            return s();
        }
        BigDecimal bigDecimalValue = value instanceof BigDecimal ? (BigDecimal) value : new BigDecimal(value.toString());
        if (this.p0) {
            this.p0 = false;
            return r(bigDecimalValue.toString());
        }
        u0(bigDecimalValue);
        int[] iArr = this.q;
        int i = this.c - 1;
        iArr[i] = iArr[i] + 1;
        return this;
    }

    public void close() {
        int size = this.c;
        if (size > 1 || (size == 1 && this.d[size - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.c = 0;
    }

    public void flush() {
        if (this.c == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
    }

    private n u0(@Nullable Object newTop) {
        String str;
        Object replaced;
        int scope = u();
        int i = this.c;
        if (i == 1) {
            if (scope == 6) {
                this.d[i - 1] = 7;
                this.p1[i - 1] = newTop;
            } else {
                throw new IllegalStateException("JSON must have only one top-level value.");
            }
        } else if (scope != 3 || (str = this.a2) == null) {
            if (scope == 1) {
                ((List) this.p1[i - 1]).add(newTop);
            } else if (scope == 9) {
                throw new IllegalStateException("Sink from valueSink() was not closed");
            } else {
                throw new IllegalStateException("Nesting problem.");
            }
        } else if ((newTop != null || this.z) && (replaced = ((Map) this.p1[i - 1]).put(str, newTop)) != null) {
            throw new IllegalArgumentException("Map key '" + this.a2 + "' has multiple values at path " + getPath() + ": " + replaced + " and " + newTop);
        } else {
            this.a2 = null;
        }
        return this;
    }
}
