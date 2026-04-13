package com.typesafe.config.impl;

import com.tencent.bugly.Bugly;
import com.typesafe.config.f;
import com.typesafe.config.j;
import com.typesafe.config.k;
import java.io.Serializable;

/* compiled from: ConfigBoolean */
public final class b extends AbstractConfigValue implements Serializable {
    private static final long serialVersionUID = 2;
    private final boolean value;

    b(f origin, boolean value2) {
        super(origin);
        this.value = value2;
    }

    public k valueType() {
        return k.BOOLEAN;
    }

    public Boolean unwrapped() {
        return Boolean.valueOf(this.value);
    }

    /* access modifiers changed from: package-private */
    public String transformToString() {
        return this.value ? "true" : Bugly.SDK_IS_DEV;
    }

    /* access modifiers changed from: protected */
    public b newCopy(f origin) {
        return new b(origin, this.value);
    }

    private Object writeReplace() {
        return new b0((j) this);
    }
}
