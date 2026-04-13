package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.impl.AbstractConfigValue;

/* compiled from: ResolveResult */
public final class y<V extends AbstractConfigValue> {
    public final w a;
    public final V b;

    private y(w context, V value) {
        this.a = context;
        this.b = value;
    }

    static <V extends AbstractConfigValue> y<V> b(w context, V value) {
        return new y<>(context, value);
    }

    /* access modifiers changed from: package-private */
    public y<a> a() {
        if (this.b instanceof a) {
            return this;
        }
        throw new ConfigException.BugOrBroken("Expecting a resolve result to be an object, but it was " + this.b);
    }

    /* access modifiers changed from: package-private */
    public y<V> c() {
        return b(this.a.g(), this.b);
    }

    public String toString() {
        return "ResolveResult(" + this.b + ")";
    }
}
