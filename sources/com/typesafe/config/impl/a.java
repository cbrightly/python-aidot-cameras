package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigOrigin;
import com.typesafe.config.d;
import com.typesafe.config.e;
import com.typesafe.config.f;
import com.typesafe.config.g;
import com.typesafe.config.j;
import com.typesafe.config.k;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* compiled from: AbstractConfigObject */
public abstract class a extends AbstractConfigValue implements e, n {
    private final c0 config = new c0(this);

    /* access modifiers changed from: package-private */
    public abstract AbstractConfigValue attemptPeekWithPartialResolve(String str);

    public abstract AbstractConfigValue get(Object obj);

    /* access modifiers changed from: protected */
    public abstract a mergedWithObject(a aVar);

    /* access modifiers changed from: protected */
    public abstract a newCopy(a0 a0Var, f fVar);

    /* access modifiers changed from: package-private */
    public abstract a relativized(s sVar);

    /* access modifiers changed from: protected */
    public abstract void render(StringBuilder sb, int i, boolean z, g gVar);

    /* access modifiers changed from: package-private */
    public abstract y<? extends a> resolveSubstitutions(w wVar, z zVar);

    public abstract a withOnlyKey(String str);

    /* access modifiers changed from: package-private */
    public abstract a withOnlyPath(s sVar);

    /* access modifiers changed from: protected */
    public abstract a withOnlyPathOrNull(s sVar);

    /* access modifiers changed from: package-private */
    public abstract a withValue(s sVar, j jVar);

    public abstract a withValue(String str, j jVar);

    public abstract a withoutKey(String str);

    /* access modifiers changed from: package-private */
    public abstract a withoutPath(s sVar);

    protected a(f origin) {
        super(origin);
    }

    public c0 toConfig() {
        return this.config;
    }

    public a toFallbackValue() {
        return this;
    }

    /* access modifiers changed from: protected */
    public final AbstractConfigValue peekAssumingResolved(String key, s originalPath) {
        try {
            return attemptPeekWithPartialResolve(key);
        } catch (ConfigException.NotResolved e) {
            throw f.c(originalPath, e);
        }
    }

    /* access modifiers changed from: protected */
    public AbstractConfigValue peekPath(s path) {
        return f(this, path);
    }

    private static AbstractConfigValue f(a self, s path) {
        try {
            s next = path.j();
            AbstractConfigValue v = self.attemptPeekWithPartialResolve(path.b());
            if (next == null) {
                return v;
            }
            if (v instanceof a) {
                return f((a) v, next);
            }
            return null;
        } catch (ConfigException.NotResolved e) {
            throw f.c(path, e);
        }
    }

    public k valueType() {
        return k.OBJECT;
    }

    /* access modifiers changed from: protected */
    public a newCopy(f origin) {
        return newCopy(resolveStatus(), origin);
    }

    /* access modifiers changed from: protected */
    public a constructDelayedMerge(f origin, List<AbstractConfigValue> stack) {
        return new d(origin, stack);
    }

    public a withFallback(d mergeable) {
        return (a) super.withFallback(mergeable);
    }

    static f mergeOrigins(Collection<? extends AbstractConfigValue> stack) {
        if (!stack.isEmpty()) {
            List<ConfigOrigin> origins = new ArrayList<>();
            f firstOrigin = null;
            int numMerged = 0;
            for (AbstractConfigValue v : stack) {
                if (firstOrigin == null) {
                    firstOrigin = v.origin();
                }
                if (!(v instanceof a) || ((a) v).resolveStatus() != a0.RESOLVED || !((e) v).isEmpty()) {
                    origins.add(v.origin());
                    numMerged++;
                }
            }
            if (numMerged == 0) {
                origins.add(firstOrigin);
            }
            return f0.i(origins);
        }
        throw new ConfigException.BugOrBroken("can't merge origins on empty list");
    }

    static f mergeOrigins(a... stack) {
        return mergeOrigins((Collection<? extends AbstractConfigValue>) Arrays.asList(stack));
    }

    private static UnsupportedOperationException g(String method) {
        return new UnsupportedOperationException("ConfigObject is immutable, you can't call Map." + method);
    }

    public void clear() {
        throw g("clear");
    }

    public j put(String arg0, j arg1) {
        throw g("put");
    }

    public void putAll(Map<? extends String, ? extends j> map) {
        throw g("putAll");
    }

    public j remove(Object arg0) {
        throw g("remove");
    }

    public a withOrigin(f origin) {
        return (a) super.withOrigin(origin);
    }
}
