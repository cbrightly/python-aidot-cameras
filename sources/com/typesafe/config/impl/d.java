package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.b;
import com.typesafe.config.f;
import com.typesafe.config.g;
import com.typesafe.config.j;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: ConfigDelayedMergeObject */
public final class d extends a implements k0, v {
    private final List<AbstractConfigValue> c;

    /* JADX WARNING: Removed duplicated region for block: B:7:0x001e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    d(com.typesafe.config.f r4, java.util.List<com.typesafe.config.impl.AbstractConfigValue> r5) {
        /*
            r3 = this;
            r3.<init>(r4)
            r3.c = r5
            boolean r0 = r5.isEmpty()
            if (r0 != 0) goto L_0x003f
            r0 = 0
            java.lang.Object r0 = r5.get(r0)
            boolean r0 = r0 instanceof com.typesafe.config.impl.a
            if (r0 == 0) goto L_0x0037
            java.util.Iterator r0 = r5.iterator()
        L_0x0018:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0036
            java.lang.Object r1 = r0.next()
            com.typesafe.config.impl.AbstractConfigValue r1 = (com.typesafe.config.impl.AbstractConfigValue) r1
            boolean r2 = r1 instanceof com.typesafe.config.impl.c
            if (r2 != 0) goto L_0x002d
            boolean r2 = r1 instanceof com.typesafe.config.impl.d
            if (r2 != 0) goto L_0x002d
            goto L_0x0018
        L_0x002d:
            com.typesafe.config.ConfigException$BugOrBroken r0 = new com.typesafe.config.ConfigException$BugOrBroken
            java.lang.String r2 = "placed nested DelayedMerge in a ConfigDelayedMergeObject, should have consolidated stack"
            r0.<init>(r2)
            throw r0
        L_0x0036:
            return
        L_0x0037:
            com.typesafe.config.ConfigException$BugOrBroken r0 = new com.typesafe.config.ConfigException$BugOrBroken
            java.lang.String r1 = "created a delayed merge object not guaranteed to be an object"
            r0.<init>(r1)
            throw r0
        L_0x003f:
            com.typesafe.config.ConfigException$BugOrBroken r0 = new com.typesafe.config.ConfigException$BugOrBroken
            java.lang.String r1 = "creating empty delayed merge object"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.typesafe.config.impl.d.<init>(com.typesafe.config.f, java.util.List):void");
    }

    /* access modifiers changed from: protected */
    /* renamed from: n */
    public d newCopy(a0 status, f origin) {
        if (status == resolveStatus()) {
            return new d(origin, this.c);
        }
        throw new ConfigException.BugOrBroken("attempt to create resolved ConfigDelayedMergeObject");
    }

    /* access modifiers changed from: package-private */
    public y<? extends a> resolveSubstitutions(w context, z source) {
        return c.o(this, this.c, context, source).a();
    }

    public AbstractConfigValue b(w context, int skipping) {
        return c.f(context, this.c, skipping);
    }

    /* access modifiers changed from: package-private */
    public a0 resolveStatus() {
        return a0.UNRESOLVED;
    }

    public AbstractConfigValue replaceChild(AbstractConfigValue child, AbstractConfigValue replacement) {
        List<AbstractConfigValue> newStack = AbstractConfigValue.replaceChildInList(this.c, child, replacement);
        if (newStack == null) {
            return null;
        }
        return new d(origin(), newStack);
    }

    public boolean hasDescendant(AbstractConfigValue descendant) {
        return AbstractConfigValue.hasDescendantInList(this.c, descendant);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: p */
    public d relativized(s prefix) {
        List<AbstractConfigValue> newStack = new ArrayList<>();
        for (AbstractConfigValue o : this.c) {
            newStack.add(o.relativized(prefix));
        }
        return new d(origin(), newStack);
    }

    /* access modifiers changed from: protected */
    public boolean ignoresFallbacks() {
        return c.p(this.c);
    }

    /* access modifiers changed from: protected */
    /* renamed from: k */
    public final d mergedWithTheUnmergeable(k0 fallback) {
        requireNotIgnoringFallbacks();
        return (d) mergedWithTheUnmergeable(this.c, fallback);
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public final d mergedWithObject(a fallback) {
        return mergedWithNonObject(fallback);
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public final d mergedWithNonObject(AbstractConfigValue fallback) {
        requireNotIgnoringFallbacks();
        return (d) mergedWithNonObject(this.c, fallback);
    }

    /* renamed from: q */
    public d withFallback(com.typesafe.config.d mergeable) {
        return (d) super.withFallback(mergeable);
    }

    /* renamed from: r */
    public d withOnlyKey(String key) {
        throw o();
    }

    /* renamed from: u */
    public d withoutKey(String key) {
        throw o();
    }

    /* access modifiers changed from: protected */
    public a withOnlyPathOrNull(s path) {
        throw o();
    }

    /* access modifiers changed from: package-private */
    public a withOnlyPath(s path) {
        throw o();
    }

    /* access modifiers changed from: package-private */
    public a withoutPath(s path) {
        throw o();
    }

    /* renamed from: t */
    public d withValue(String key, j value) {
        throw o();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: s */
    public d withValue(s path, j value) {
        throw o();
    }

    public Collection<AbstractConfigValue> a() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public boolean canEqual(Object other) {
        return other instanceof d;
    }

    public boolean equals(Object other) {
        if (!(other instanceof d) || !canEqual(other)) {
            return false;
        }
        List<AbstractConfigValue> list = this.c;
        if (list == ((d) other).c || list.equals(((d) other).c)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, String atKey, g options) {
        c.n(this.c, sb, indent, atRoot, atKey, options);
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, g options) {
        render(sb, indent, atRoot, (String) null, options);
    }

    private static ConfigException o() {
        return new ConfigException.NotResolved("need to Config#resolve() before using this object, see the API docs for Config#resolve()");
    }

    public Map<String, Object> unwrapped() {
        throw o();
    }

    public AbstractConfigValue get(Object key) {
        throw o();
    }

    public boolean containsKey(Object key) {
        throw o();
    }

    public boolean containsValue(Object value) {
        throw o();
    }

    public Set<Map.Entry<String, j>> entrySet() {
        throw o();
    }

    public boolean isEmpty() {
        throw o();
    }

    public Set<String> keySet() {
        throw o();
    }

    public int size() {
        throw o();
    }

    public Collection<j> values() {
        throw o();
    }

    /* access modifiers changed from: protected */
    public AbstractConfigValue attemptPeekWithPartialResolve(String key) {
        for (AbstractConfigValue layer : this.c) {
            if (layer instanceof a) {
                AbstractConfigValue v = ((a) layer).attemptPeekWithPartialResolve(key);
                if (v != null) {
                    if (v.ignoresFallbacks()) {
                        return v;
                    }
                } else if (layer instanceof k0) {
                    throw new ConfigException.BugOrBroken("should not be reached: unmergeable object returned null value");
                }
            } else if (layer instanceof k0) {
                throw new ConfigException.NotResolved("Key '" + key + "' is not available at '" + origin().a() + "' because value at '" + layer.origin().a() + "' has not been resolved and may turn out to contain or hide '" + key + "'. Be sure to Config#resolve() before using a config object.");
            } else if (layer.resolveStatus() == a0.UNRESOLVED) {
                if (layer instanceof b) {
                    return null;
                }
                throw new ConfigException.BugOrBroken("Expecting a list here, not " + layer);
            } else if (layer.ignoresFallbacks()) {
                return null;
            } else {
                throw new ConfigException.BugOrBroken("resolved non-object should ignore fallbacks");
            }
        }
        throw new ConfigException.BugOrBroken("Delayed merge stack does not contain any unmergeable values");
    }
}
