package com.typesafe.config.impl;

import com.typesafe.config.f;
import com.typesafe.config.j;
import com.typesafe.config.k;
import java.io.Serializable;

/* compiled from: ConfigLong */
public final class i extends k implements Serializable {
    private static final long serialVersionUID = 2;
    private final long value;

    i(f origin, long value2, String originalText) {
        super(origin, originalText);
        this.value = value2;
    }

    public k valueType() {
        return k.NUMBER;
    }

    public Long unwrapped() {
        return Long.valueOf(this.value);
    }

    /* access modifiers changed from: package-private */
    public String transformToString() {
        String s = super.transformToString();
        if (s == null) {
            return Long.toString(this.value);
        }
        return s;
    }

    /* access modifiers changed from: protected */
    public long longValue() {
        return this.value;
    }

    /* access modifiers changed from: protected */
    public double doubleValue() {
        return (double) this.value;
    }

    /* access modifiers changed from: protected */
    public i newCopy(f origin) {
        return new i(origin, this.value, this.originalText);
    }

    private Object writeReplace() {
        return new b0((j) this);
    }
}
