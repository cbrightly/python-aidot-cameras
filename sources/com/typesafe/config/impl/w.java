package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.h;
import com.typesafe.config.impl.AbstractConfigValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

/* compiled from: ResolveContext */
public final class w {
    private final x a;
    private final h b;
    private final s c;
    private final List<AbstractConfigValue> d;
    private final Set<AbstractConfigValue> e;

    w(x memos, h options, s restrictToChild, List<AbstractConfigValue> resolveStack, Set<AbstractConfigValue> cycleMarkers) {
        this.a = memos;
        this.b = options;
        this.c = restrictToChild;
        this.d = Collections.unmodifiableList(resolveStack);
        this.e = Collections.unmodifiableSet(cycleMarkers);
    }

    private static Set<AbstractConfigValue> e() {
        return Collections.newSetFromMap(new IdentityHashMap());
    }

    w(h options, s restrictToChild) {
        this(new x(), options, restrictToChild, new ArrayList(), e());
        if (f.g()) {
            int b2 = b();
            f.e(b2, "ResolveContext restrict to child " + restrictToChild);
        }
    }

    /* access modifiers changed from: package-private */
    public w a(AbstractConfigValue value) {
        if (f.g()) {
            int b2 = b();
            f.e(b2, "++ Cycle marker " + value + "@" + System.identityHashCode(value));
        }
        if (!this.e.contains(value)) {
            Set<AbstractConfigValue> copy = e();
            copy.addAll(this.e);
            copy.add(value);
            return new w(this.a, this.b, this.c, this.d, copy);
        }
        throw new ConfigException.BugOrBroken("Added cycle marker twice " + value);
    }

    /* access modifiers changed from: package-private */
    public w j(AbstractConfigValue value) {
        if (f.g()) {
            int b2 = b();
            f.e(b2, "-- Cycle marker " + value + "@" + System.identityHashCode(value));
        }
        Set<AbstractConfigValue> copy = e();
        copy.addAll(this.e);
        copy.remove(value);
        return new w(this.a, this.b, this.c, this.d, copy);
    }

    private w d(p key, AbstractConfigValue value) {
        return new w(this.a.b(key, value), this.b, this.c, this.d, this.e);
    }

    /* access modifiers changed from: package-private */
    public h f() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public boolean c() {
        return this.c != null;
    }

    /* access modifiers changed from: package-private */
    public s n() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public w m(s restrictTo) {
        if (restrictTo == this.c) {
            return this;
        }
        return new w(this.a, this.b, restrictTo, this.d, this.e);
    }

    /* access modifiers changed from: package-private */
    public w p() {
        return m((s) null);
    }

    /* access modifiers changed from: package-private */
    public String o() {
        StringBuilder sb = new StringBuilder();
        for (AbstractConfigValue value : this.d) {
            if (value instanceof l) {
                ((l) value).f().toString();
                throw null;
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - ", ".length());
        }
        return sb.toString();
    }

    private w h(AbstractConfigValue value) {
        if (f.g()) {
            int b2 = b();
            f.e(b2, "pushing trace " + value);
        }
        List<AbstractConfigValue> copy = new ArrayList<>(this.d);
        copy.add(value);
        return new w(this.a, this.b, this.c, copy, this.e);
    }

    /* access modifiers changed from: package-private */
    public w g() {
        List<AbstractConfigValue> copy = new ArrayList<>(this.d);
        AbstractConfigValue old = copy.remove(this.d.size() - 1);
        if (f.g()) {
            f.e(b() - 1, "popped trace " + old);
        }
        return new w(this.a, this.b, this.c, copy, this.e);
    }

    /* access modifiers changed from: package-private */
    public int b() {
        if (this.d.size() <= 30) {
            return this.d.size();
        }
        throw new ConfigException.BugOrBroken("resolve getting too deep");
    }

    /* access modifiers changed from: package-private */
    public y<? extends AbstractConfigValue> l(AbstractConfigValue original, z source) {
        if (f.g()) {
            int b2 = b();
            f.e(b2, "resolving " + original + " restrictToChild=" + this.c + " in " + source);
        }
        return h(original).i(original, source).c();
    }

    private y<? extends AbstractConfigValue> i(AbstractConfigValue original, z source) {
        w withMemo;
        p fullKey = new p(original, (s) null);
        p restrictedKey = null;
        AbstractConfigValue cached = this.a.a(fullKey);
        if (cached == null && c()) {
            restrictedKey = new p(original, n());
            cached = this.a.a(restrictedKey);
        }
        if (cached != null) {
            if (f.g()) {
                int b2 = b();
                f.e(b2, "using cached resolution " + cached + " for " + original + " restrictToChild " + n());
            }
            return y.b(this, cached);
        }
        if (f.g()) {
            int b3 = b();
            f.e(b3, "not found in cache, resolving " + original + "@" + System.identityHashCode(original));
        }
        if (this.e.contains(original)) {
            if (f.g()) {
                int b4 = b();
                f.e(b4, "Cycle detected, can't resolve; " + original + "@" + System.identityHashCode(original));
            }
            throw new AbstractConfigValue.NotPossibleToResolve(this);
        }
        ResolveResult<? extends AbstractConfigValue> result = original.resolveSubstitutions(this, source);
        AbstractConfigValue resolved = result.b;
        if (f.g()) {
            int b5 = b();
            f.e(b5, "resolved to " + resolved + "@" + System.identityHashCode(resolved) + " from " + original + "@" + System.identityHashCode(resolved));
        }
        w withMemo2 = result.a;
        if (resolved == null || resolved.resolveStatus() == a0.RESOLVED) {
            if (f.g()) {
                int b6 = b();
                f.e(b6, "caching " + fullKey + " result " + resolved);
            }
            withMemo = withMemo2.d(fullKey, resolved);
        } else if (c()) {
            if (restrictedKey != null) {
                if (f.g()) {
                    int b7 = b();
                    f.e(b7, "caching " + restrictedKey + " result " + resolved);
                }
                withMemo = withMemo2.d(restrictedKey, resolved);
            } else {
                throw new ConfigException.BugOrBroken("restrictedKey should not be null here");
            }
        } else if (f().b()) {
            if (f.g()) {
                int b8 = b();
                f.e(b8, "caching " + fullKey + " result " + resolved);
            }
            withMemo = withMemo2.d(fullKey, resolved);
        } else {
            throw new ConfigException.BugOrBroken("resolveSubstitutions() did not give us a resolved object");
        }
        return y.b(withMemo, resolved);
    }

    static AbstractConfigValue k(AbstractConfigValue value, a root, h options) {
        try {
            return new w(options, (s) null).l(value, new z(root)).b;
        } catch (AbstractConfigValue.NotPossibleToResolve e2) {
            throw new ConfigException.BugOrBroken("NotPossibleToResolve was thrown from an outermost resolve", e2);
        }
    }
}
