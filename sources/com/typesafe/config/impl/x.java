package com.typesafe.config.impl;

import java.util.HashMap;
import java.util.Map;

/* compiled from: ResolveMemos */
public final class x {
    private final Map<p, AbstractConfigValue> a;

    private x(Map<p, AbstractConfigValue> memos) {
        this.a = memos;
    }

    x() {
        this(new HashMap());
    }

    /* access modifiers changed from: package-private */
    public AbstractConfigValue a(p key) {
        return this.a.get(key);
    }

    /* access modifiers changed from: package-private */
    public x b(p key, AbstractConfigValue value) {
        Map<MemoKey, AbstractConfigValue> copy = new HashMap<>(this.a);
        copy.put(key, value);
        return new x(copy);
    }
}
