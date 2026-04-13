package com.typesafe.config.impl;

import com.typesafe.config.f;
import com.typesafe.config.j;
import com.typesafe.config.k;
import java.io.Serializable;

/* compiled from: ConfigDouble */
public final class e extends k implements Serializable {
    private static final long serialVersionUID = 2;
    private final double value;

    e(f origin, double value2, String originalText) {
        super(origin, originalText);
        this.value = value2;
    }

    public k valueType() {
        return k.NUMBER;
    }

    public Double unwrapped() {
        return Double.valueOf(this.value);
    }

    /* access modifiers changed from: package-private */
    public String transformToString() {
        String s = super.transformToString();
        if (s == null) {
            return Double.toString(this.value);
        }
        return s;
    }

    /* access modifiers changed from: protected */
    public long longValue() {
        return (long) this.value;
    }

    /* access modifiers changed from: protected */
    public double doubleValue() {
        return this.value;
    }

    /* access modifiers changed from: protected */
    public e newCopy(f origin) {
        return new e(origin, this.value, this.originalText);
    }

    private Object writeReplace() {
        return new b0((j) this);
    }
}
