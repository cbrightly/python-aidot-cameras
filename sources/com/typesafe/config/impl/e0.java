package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.d;
import com.typesafe.config.e;
import com.typesafe.config.f;
import com.typesafe.config.g;
import com.typesafe.config.impl.AbstractConfigValue;
import com.typesafe.config.j;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: SimpleConfigObject */
public final class e0 extends a implements Serializable {
    private static final e0 c = empty(f0.l("empty config"));
    private static final long serialVersionUID = 2;
    private final boolean ignoresFallbacks;
    private final boolean resolved;
    private final Map<String, AbstractConfigValue> value;

    e0(f origin, Map<String, AbstractConfigValue> value2, a0 status, boolean ignoresFallbacks2) {
        super(origin);
        if (value2 != null) {
            this.value = value2;
            this.resolved = status == a0.RESOLVED;
            this.ignoresFallbacks = ignoresFallbacks2;
            if (status != a0.fromValues(value2.values())) {
                throw new ConfigException.BugOrBroken("Wrong resolved status on " + this);
            }
            return;
        }
        throw new ConfigException.BugOrBroken("creating config object with null map");
    }

    e0(f origin, Map<String, AbstractConfigValue> value2) {
        this(origin, value2, a0.fromValues(value2.values()), false);
    }

    public e0 withOnlyKey(String key) {
        return withOnlyPath(s.f(key));
    }

    public e0 withoutKey(String key) {
        return withoutPath(s.f(key));
    }

    /* access modifiers changed from: protected */
    public e0 withOnlyPathOrNull(s path) {
        String key = path.b();
        s next = path.j();
        AbstractConfigValue v = this.value.get(key);
        if (next != null) {
            if (v == null || !(v instanceof a)) {
                v = null;
            } else {
                v = ((a) v).withOnlyPathOrNull(next);
            }
        }
        if (v == null) {
            return null;
        }
        return new e0(origin(), Collections.singletonMap(key, v), v.resolveStatus(), this.ignoresFallbacks);
    }

    /* access modifiers changed from: package-private */
    public e0 withOnlyPath(s path) {
        e0 o = withOnlyPathOrNull(path);
        if (o == null) {
            return new e0(origin(), Collections.emptyMap(), a0.RESOLVED, this.ignoresFallbacks);
        }
        return o;
    }

    /* access modifiers changed from: package-private */
    public e0 withoutPath(s path) {
        String key = path.b();
        s next = path.j();
        AbstractConfigValue v = this.value.get(key);
        if (v != null && next != null && (v instanceof a)) {
            AbstractConfigValue v2 = ((a) v).withoutPath(next);
            Map<String, AbstractConfigValue> updated = new HashMap<>(this.value);
            updated.put(key, v2);
            return new e0(origin(), updated, a0.fromValues(updated.values()), this.ignoresFallbacks);
        } else if (next != null || v == null) {
            return this;
        } else {
            Map<String, AbstractConfigValue> smaller = new HashMap<>(this.value.size() - 1);
            for (Map.Entry<String, AbstractConfigValue> old : this.value.entrySet()) {
                if (!old.getKey().equals(key)) {
                    smaller.put(old.getKey(), old.getValue());
                }
            }
            return new e0(origin(), smaller, a0.fromValues(smaller.values()), this.ignoresFallbacks);
        }
    }

    public e0 withValue(String key, j v) {
        Map<String, AbstractConfigValue> newMap;
        if (v != null) {
            if (this.value.isEmpty()) {
                newMap = Collections.singletonMap(key, (AbstractConfigValue) v);
            } else {
                newMap = new HashMap<>(this.value);
                newMap.put(key, (AbstractConfigValue) v);
            }
            return new e0(origin(), newMap, a0.fromValues(newMap.values()), this.ignoresFallbacks);
        }
        throw new ConfigException.BugOrBroken("Trying to store null ConfigValue in a ConfigObject");
    }

    /* access modifiers changed from: package-private */
    public e0 withValue(s path, j v) {
        String key = path.b();
        s next = path.j();
        if (next == null) {
            return withValue(key, v);
        }
        AbstractConfigValue child = this.value.get(key);
        if (child != null && (child instanceof a)) {
            return withValue(key, (j) ((a) child).withValue(next, v));
        }
        return withValue(key, (j) ((AbstractConfigValue) v).atPath(f0.l("withValue(" + next.k() + ")"), next).root());
    }

    /* access modifiers changed from: protected */
    public AbstractConfigValue attemptPeekWithPartialResolve(String key) {
        return this.value.get(key);
    }

    private e0 o(a0 newStatus, f newOrigin, boolean newIgnoresFallbacks) {
        return new e0(newOrigin, this.value, newStatus, newIgnoresFallbacks);
    }

    /* access modifiers changed from: protected */
    public e0 newCopy(a0 newStatus, f newOrigin) {
        return o(newStatus, newOrigin, this.ignoresFallbacks);
    }

    /* access modifiers changed from: protected */
    public e0 withFallbacksIgnored() {
        if (this.ignoresFallbacks) {
            return this;
        }
        return o(resolveStatus(), origin(), true);
    }

    /* access modifiers changed from: package-private */
    public a0 resolveStatus() {
        return a0.fromBoolean(this.resolved);
    }

    public e0 replaceChild(AbstractConfigValue child, AbstractConfigValue replacement) {
        HashMap<String, AbstractConfigValue> newChildren = new HashMap<>(this.value);
        for (Map.Entry<String, AbstractConfigValue> old : newChildren.entrySet()) {
            if (old.getValue() == child) {
                if (replacement != null) {
                    old.setValue(replacement);
                } else {
                    newChildren.remove(old.getKey());
                }
                return new e0(origin(), newChildren, a0.fromValues(newChildren.values()), this.ignoresFallbacks);
            }
        }
        throw new ConfigException.BugOrBroken("SimpleConfigObject.replaceChild did not find " + child + " in " + this);
    }

    public boolean hasDescendant(AbstractConfigValue descendant) {
        for (AbstractConfigValue child : this.value.values()) {
            if (child == descendant) {
                return true;
            }
        }
        for (AbstractConfigValue child2 : this.value.values()) {
            if ((child2 instanceof n) && ((n) child2).hasDescendant(descendant)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean ignoresFallbacks() {
        return this.ignoresFallbacks;
    }

    public Map<String, Object> unwrapped() {
        Map<String, Object> m = new HashMap<>();
        for (Map.Entry<String, AbstractConfigValue> e : this.value.entrySet()) {
            m.put(e.getKey(), e.getValue().unwrapped());
        }
        return m;
    }

    /* access modifiers changed from: protected */
    public e0 mergedWithObject(a abstractFallback) {
        AbstractConfigValue kept;
        requireNotIgnoringFallbacks();
        if (abstractFallback instanceof e0) {
            e0 fallback = (e0) abstractFallback;
            boolean changed = false;
            boolean allResolved = true;
            Map<String, AbstractConfigValue> merged = new HashMap<>();
            Set<String> allKeys = new HashSet<>();
            allKeys.addAll(keySet());
            allKeys.addAll(fallback.keySet());
            for (String key : allKeys) {
                AbstractConfigValue first = this.value.get(key);
                AbstractConfigValue second = fallback.value.get(key);
                if (first == null) {
                    kept = second;
                } else if (second == null) {
                    kept = first;
                } else {
                    kept = first.withFallback((d) second);
                }
                merged.put(key, kept);
                if (first != kept) {
                    changed = true;
                }
                if (kept.resolveStatus() == a0.UNRESOLVED) {
                    allResolved = false;
                }
            }
            a0 newResolveStatus = a0.fromBoolean(allResolved);
            boolean newIgnoresFallbacks = fallback.ignoresFallbacks();
            if (changed) {
                return new e0(a.mergeOrigins(this, fallback), merged, newResolveStatus, newIgnoresFallbacks);
            } else if (newResolveStatus == resolveStatus() && newIgnoresFallbacks == ignoresFallbacks()) {
                return this;
            } else {
                return o(newResolveStatus, origin(), newIgnoresFallbacks);
            }
        } else {
            throw new ConfigException.BugOrBroken("should not be reached (merging non-SimpleConfigObject)");
        }
    }

    private e0 k(AbstractConfigValue.b modifier) {
        try {
            return n(modifier);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new ConfigException.BugOrBroken("unexpected checked exception", e2);
        }
    }

    private e0 n(AbstractConfigValue.a modifier) {
        Map<String, AbstractConfigValue> changes = null;
        for (String k : keySet()) {
            AbstractConfigValue v = this.value.get(k);
            AbstractConfigValue modified = modifier.a(k, v);
            if (modified != v) {
                if (changes == null) {
                    changes = new HashMap<>();
                }
                changes.put(k, modified);
            }
        }
        if (changes == null) {
            return this;
        }
        Map<String, AbstractConfigValue> modified2 = new HashMap<>();
        boolean sawUnresolved = false;
        for (String k2 : keySet()) {
            if (changes.containsKey(k2)) {
                AbstractConfigValue newValue = changes.get(k2);
                if (newValue != null) {
                    modified2.put(k2, newValue);
                    if (newValue.resolveStatus() == a0.UNRESOLVED) {
                        sawUnresolved = true;
                    }
                }
            } else {
                AbstractConfigValue newValue2 = this.value.get(k2);
                modified2.put(k2, newValue2);
                if (newValue2.resolveStatus() == a0.UNRESOLVED) {
                    sawUnresolved = true;
                }
            }
        }
        return new e0(origin(), modified2, sawUnresolved ? a0.UNRESOLVED : a0.RESOLVED, ignoresFallbacks());
    }

    /* compiled from: SimpleConfigObject */
    public static final class c implements AbstractConfigValue.a {
        final s a;
        w b;
        final z c;

        c(w context, z source) {
            this.b = context;
            this.c = source;
            this.a = context.n();
        }

        public AbstractConfigValue a(String key, AbstractConfigValue v) {
            s remainder;
            if (!this.b.c()) {
                ResolveResult<? extends AbstractConfigValue> result = this.b.p().l(v, this.c);
                this.b = result.a.p().m(this.a);
                return result.b;
            } else if (!key.equals(this.b.n().b()) || (remainder = this.b.n().j()) == null) {
                return v;
            } else {
                ResolveResult<? extends AbstractConfigValue> result2 = this.b.m(remainder).l(v, this.c);
                this.b = result2.a.p().m(this.a);
                return result2.b;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public y<? extends a> resolveSubstitutions(w context, z source) {
        if (resolveStatus() == a0.RESOLVED) {
            return y.b(context, this);
        }
        try {
            c modifier = new c(context, source.e(this));
            return y.b(modifier.b, n(modifier)).a();
        } catch (AbstractConfigValue.NotPossibleToResolve e) {
            throw e;
        } catch (RuntimeException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ConfigException.BugOrBroken("unexpected checked exception", e3);
        }
    }

    /* compiled from: SimpleConfigObject */
    public class a extends AbstractConfigValue.b {
        final /* synthetic */ s b;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(s sVar) {
            super();
            this.b = sVar;
        }

        public AbstractConfigValue b(String key, AbstractConfigValue v) {
            return v.relativized(this.b);
        }
    }

    /* access modifiers changed from: package-private */
    public e0 relativized(s prefix) {
        return k(new a(prefix));
    }

    /* compiled from: SimpleConfigObject */
    public static final class b implements Comparator<String>, Serializable {
        private static final long serialVersionUID = 1;

        private b() {
        }

        /* synthetic */ b(a x0) {
            this();
        }

        private static boolean a(String s) {
            int length = s.length();
            if (length == 0) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (!Character.isDigit(s.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        public int compare(String a, String b) {
            boolean aDigits = a(a);
            boolean bDigits = a(b);
            if (aDigits && bDigits) {
                return Integer.compare(Integer.parseInt(a), Integer.parseInt(b));
            }
            if (aDigits) {
                return -1;
            }
            if (bDigits) {
                return 1;
            }
            return a.compareTo(b);
        }
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, g options) {
        int innerIndent;
        int separatorCount;
        StringBuilder sb2 = sb;
        int i = indent;
        g gVar = options;
        if (isEmpty()) {
            sb2.append("{}");
        } else {
            boolean outerBraces = options.e() || !atRoot;
            if (outerBraces) {
                int innerIndent2 = i + 1;
                sb2.append("{");
                if (options.d()) {
                    sb2.append(10);
                }
                innerIndent = innerIndent2;
            } else {
                innerIndent = indent;
            }
            String[] keys = (String[]) keySet().toArray(new String[size()]);
            Arrays.sort(keys, new b((a) null));
            int length = keys.length;
            int separatorCount2 = 0;
            int i2 = 0;
            while (i2 < length) {
                String k = keys[i2];
                AbstractConfigValue v = this.value.get(k);
                if (options.f()) {
                    String[] lines = v.origin().a().split("\n");
                    int length2 = lines.length;
                    int i3 = 0;
                    while (i3 < length2) {
                        String l = lines[i3];
                        String[] lines2 = lines;
                        AbstractConfigValue.indent(sb2, i + 1, gVar);
                        sb2.append('#');
                        if (!l.isEmpty()) {
                            sb2.append(' ');
                        }
                        sb2.append(l);
                        sb2.append("\n");
                        i3++;
                        lines = lines2;
                    }
                }
                if (options.c()) {
                    for (String comment : v.origin().d()) {
                        AbstractConfigValue.indent(sb2, innerIndent, gVar);
                        sb2.append("#");
                        if (!comment.startsWith(" ")) {
                            sb2.append(' ');
                        }
                        sb2.append(comment);
                        sb2.append("\n");
                    }
                }
                AbstractConfigValue.indent(sb2, innerIndent, gVar);
                String str = k;
                int i4 = i2;
                v.render(sb, innerIndent, false, k, options);
                if (options.d()) {
                    if (options.e()) {
                        sb2.append(",");
                        separatorCount = 2;
                    } else {
                        separatorCount = 1;
                    }
                    sb2.append(10);
                } else {
                    sb2.append(",");
                    separatorCount = 1;
                }
                separatorCount2 = separatorCount;
                i2 = i4 + 1;
            }
            sb2.setLength(sb.length() - separatorCount2);
            if (outerBraces) {
                if (options.d()) {
                    sb2.append(10);
                    if (outerBraces) {
                        AbstractConfigValue.indent(sb2, i, gVar);
                    }
                }
                sb2.append("}");
            }
        }
        if (atRoot && options.d()) {
            sb2.append(10);
        }
    }

    public AbstractConfigValue get(Object key) {
        return this.value.get(key);
    }

    private static boolean h(Map<String, j> a2, Map<String, j> b2) {
        if (a2 == b2) {
            return true;
        }
        Set<String> aKeys = a2.keySet();
        if (!aKeys.equals(b2.keySet())) {
            return false;
        }
        for (String key : aKeys) {
            if (!a2.get(key).equals(b2.get(key))) {
                return false;
            }
        }
        return true;
    }

    private static int i(Map<String, j> m) {
        List<String> keys = new ArrayList<>();
        keys.addAll(m.keySet());
        Collections.sort(keys);
        int valuesHash = 0;
        for (String k : keys) {
            valuesHash += m.get(k).hashCode();
        }
        return ((keys.hashCode() + 41) * 41) + valuesHash;
    }

    /* access modifiers changed from: protected */
    public boolean canEqual(Object other) {
        return other instanceof e;
    }

    public boolean equals(Object other) {
        if (!(other instanceof e) || !canEqual(other) || !h(this, (e) other)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return i(this);
    }

    public boolean containsKey(Object key) {
        return this.value.containsKey(key);
    }

    public Set<String> keySet() {
        return this.value.keySet();
    }

    public boolean containsValue(Object v) {
        return this.value.containsValue(v);
    }

    public Set<Map.Entry<String, j>> entrySet() {
        HashSet<Map.Entry<String, ConfigValue>> entries = new HashSet<>();
        for (Map.Entry<String, AbstractConfigValue> e : this.value.entrySet()) {
            entries.add(new AbstractMap.SimpleImmutableEntry(e.getKey(), e.getValue()));
        }
        return entries;
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    public int size() {
        return this.value.size();
    }

    public Collection<j> values() {
        return new HashSet(this.value.values());
    }

    static final e0 empty() {
        return c;
    }

    static final e0 empty(f origin) {
        if (origin == null) {
            return empty();
        }
        return new e0(origin, Collections.emptyMap());
    }

    static final e0 emptyMissing(f baseOrigin) {
        return new e0(f0.l(baseOrigin.a() + " (not found)"), Collections.emptyMap());
    }

    private Object writeReplace() {
        return new b0((j) this);
    }
}
