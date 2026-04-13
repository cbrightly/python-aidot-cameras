package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.f;
import com.typesafe.config.g;
import com.typesafe.config.impl.AbstractConfigValue;
import com.typesafe.config.impl.z;
import com.typesafe.config.k;
import java.util.Collection;
import java.util.Collections;

/* compiled from: ConfigReference */
public final class l extends AbstractConfigValue implements k0 {
    private final g0 c;
    private final int d;

    private l(f origin, g0 expr, int prefixLength) {
        super(origin);
        this.d = prefixLength;
    }

    private ConfigException.NotResolved h() {
        return new ConfigException.NotResolved("need to Config#resolve(), see the API docs for Config#resolve(); substitution not resolved: " + this);
    }

    public k valueType() {
        throw h();
    }

    public Object unwrapped() {
        throw h();
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public l newCopy(f newOrigin) {
        return new l(newOrigin, this.c, this.d);
    }

    /* access modifiers changed from: protected */
    public boolean ignoresFallbacks() {
        return false;
    }

    public Collection<l> a() {
        return Collections.singleton(this);
    }

    /* access modifiers changed from: package-private */
    public y<? extends AbstractConfigValue> resolveSubstitutions(w context, z source) {
        AbstractConfigValue v;
        w newContext = context.a(this);
        try {
            z.b resultWithPath = source.d(newContext, this.c, this.d);
            y<? extends AbstractConfigValue> yVar = resultWithPath.a;
            w newContext2 = yVar.a;
            if (yVar.b != null) {
                if (f.g()) {
                    int b = newContext2.b();
                    f.e(b, "recursively resolving " + resultWithPath + " which was the resolution of " + this.c + " against " + source);
                }
                z recursiveResolveSource = new z((a) resultWithPath.b.b(), resultWithPath.b);
                if (f.g()) {
                    int b2 = newContext2.b();
                    f.e(b2, "will recursively resolve against " + recursiveResolveSource);
                }
                ResolveResult<? extends AbstractConfigValue> result = newContext2.l(resultWithPath.a.b, recursiveResolveSource);
                v = result.b;
                newContext2 = result.a;
            } else {
                v = null;
            }
            if (v != null) {
                return y.b(newContext2.j(this), v);
            }
            this.c.a();
            throw null;
        } catch (AbstractConfigValue.NotPossibleToResolve e) {
            if (f.g()) {
                int b3 = newContext.b();
                f.e(b3, "not possible to resolve " + this.c + ", cycle involved: " + e.traceString());
            }
            this.c.a();
            throw null;
        }
    }

    /* access modifiers changed from: package-private */
    public a0 resolveStatus() {
        return a0.UNRESOLVED;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: i */
    public l relativized(s prefix) {
        this.c.b();
        throw null;
    }

    /* access modifiers changed from: protected */
    public boolean canEqual(Object other) {
        return other instanceof l;
    }

    public boolean equals(Object other) {
        if (!(other instanceof l) || !canEqual(other)) {
            return false;
        }
        this.c.equals(((l) other).c);
        throw null;
    }

    public int hashCode() {
        this.c.hashCode();
        throw null;
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, g options) {
        this.c.toString();
        throw null;
    }

    /* access modifiers changed from: package-private */
    public g0 f() {
        return this.c;
    }
}
