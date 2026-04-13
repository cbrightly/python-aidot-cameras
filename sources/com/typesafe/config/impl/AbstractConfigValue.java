package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.d;
import com.typesafe.config.e;
import com.typesafe.config.f;
import com.typesafe.config.g;
import com.typesafe.config.j;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AbstractConfigValue implements j, q {
    private final f0 origin;

    public interface a {
        AbstractConfigValue a(String str, AbstractConfigValue abstractConfigValue);
    }

    /* access modifiers changed from: protected */
    public abstract AbstractConfigValue newCopy(f fVar);

    AbstractConfigValue(f origin2) {
        this.origin = (f0) origin2;
    }

    public f0 origin() {
        return this.origin;
    }

    public static class NotPossibleToResolve extends Exception {
        private static final long serialVersionUID = 1;
        private final String traceString;

        NotPossibleToResolve(w context) {
            super("was not possible to resolve");
            this.traceString = context.o();
        }

        /* access modifiers changed from: package-private */
        public String traceString() {
            return this.traceString;
        }
    }

    /* access modifiers changed from: package-private */
    public y<? extends AbstractConfigValue> resolveSubstitutions(w context, z source) {
        return y.b(context, this);
    }

    /* access modifiers changed from: package-private */
    public a0 resolveStatus() {
        return a0.RESOLVED;
    }

    protected static List<AbstractConfigValue> replaceChildInList(List<AbstractConfigValue> list, AbstractConfigValue child, AbstractConfigValue replacement) {
        int i = 0;
        while (i < list.size() && list.get(i) != child) {
            i++;
        }
        if (i != list.size()) {
            List<AbstractConfigValue> newStack = new ArrayList<>(list);
            if (replacement != null) {
                newStack.set(i, replacement);
            } else {
                newStack.remove(i);
            }
            if (newStack.isEmpty()) {
                return null;
            }
            return newStack;
        }
        throw new ConfigException.BugOrBroken("tried to replace " + child + " which is not in " + list);
    }

    protected static boolean hasDescendantInList(List<AbstractConfigValue> list, AbstractConfigValue descendant) {
        for (AbstractConfigValue v : list) {
            if (v == descendant) {
                return true;
            }
        }
        for (AbstractConfigValue v2 : list) {
            if ((v2 instanceof n) && ((n) v2).hasDescendant(descendant)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public AbstractConfigValue relativized(s prefix) {
        return this;
    }

    public abstract class b implements a {
        /* access modifiers changed from: package-private */
        public abstract AbstractConfigValue b(String str, AbstractConfigValue abstractConfigValue);

        protected b() {
        }

        public final AbstractConfigValue a(String keyOrNull, AbstractConfigValue v) {
            try {
                return b(keyOrNull, v);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new ConfigException.BugOrBroken("Unexpected exception", e2);
            }
        }
    }

    public AbstractConfigValue toFallbackValue() {
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean ignoresFallbacks() {
        return resolveStatus() == a0.RESOLVED;
    }

    /* access modifiers changed from: protected */
    public AbstractConfigValue withFallbacksIgnored() {
        if (ignoresFallbacks()) {
            return this;
        }
        throw new ConfigException.BugOrBroken("value class doesn't implement forced fallback-ignoring " + this);
    }

    /* access modifiers changed from: protected */
    public final void requireNotIgnoringFallbacks() {
        if (ignoresFallbacks()) {
            throw new ConfigException.BugOrBroken("method should not have been called with ignoresFallbacks=true " + getClass().getSimpleName());
        }
    }

    /* access modifiers changed from: protected */
    public AbstractConfigValue constructDelayedMerge(f origin2, List<AbstractConfigValue> stack) {
        return new c(origin2, stack);
    }

    /* access modifiers changed from: protected */
    public final AbstractConfigValue mergedWithTheUnmergeable(Collection<AbstractConfigValue> stack, k0 fallback) {
        requireNotIgnoringFallbacks();
        List<AbstractConfigValue> newStack = new ArrayList<>();
        newStack.addAll(stack);
        newStack.addAll(fallback.a());
        return constructDelayedMerge(a.mergeOrigins((Collection<? extends AbstractConfigValue>) newStack), newStack);
    }

    private final AbstractConfigValue e(Collection<AbstractConfigValue> stack, AbstractConfigValue fallback) {
        List<AbstractConfigValue> newStack = new ArrayList<>();
        newStack.addAll(stack);
        newStack.add(fallback);
        return constructDelayedMerge(a.mergeOrigins((Collection<? extends AbstractConfigValue>) newStack), newStack);
    }

    /* access modifiers changed from: protected */
    public final AbstractConfigValue mergedWithObject(Collection<AbstractConfigValue> stack, a fallback) {
        requireNotIgnoringFallbacks();
        if (!(this instanceof a)) {
            return mergedWithNonObject(stack, fallback);
        }
        throw new ConfigException.BugOrBroken("Objects must reimplement mergedWithObject");
    }

    /* access modifiers changed from: protected */
    public final AbstractConfigValue mergedWithNonObject(Collection<AbstractConfigValue> stack, AbstractConfigValue fallback) {
        requireNotIgnoringFallbacks();
        if (resolveStatus() == a0.RESOLVED) {
            return withFallbacksIgnored();
        }
        return e(stack, fallback);
    }

    /* access modifiers changed from: protected */
    public AbstractConfigValue mergedWithTheUnmergeable(k0 fallback) {
        requireNotIgnoringFallbacks();
        return mergedWithTheUnmergeable(Collections.singletonList(this), fallback);
    }

    /* access modifiers changed from: protected */
    public AbstractConfigValue mergedWithObject(a fallback) {
        requireNotIgnoringFallbacks();
        return mergedWithObject(Collections.singletonList(this), fallback);
    }

    /* access modifiers changed from: protected */
    public AbstractConfigValue mergedWithNonObject(AbstractConfigValue fallback) {
        requireNotIgnoringFallbacks();
        return mergedWithNonObject(Collections.singletonList(this), fallback);
    }

    public AbstractConfigValue withOrigin(f origin2) {
        if (this.origin == origin2) {
            return this;
        }
        return newCopy(origin2);
    }

    public AbstractConfigValue withFallback(d mergeable) {
        if (ignoresFallbacks()) {
            return this;
        }
        j other = ((q) mergeable).toFallbackValue();
        if (other instanceof k0) {
            return mergedWithTheUnmergeable((k0) other);
        }
        if (other instanceof a) {
            return mergedWithObject((a) other);
        }
        return mergedWithNonObject((AbstractConfigValue) other);
    }

    /* access modifiers changed from: protected */
    public boolean canEqual(Object other) {
        return other instanceof j;
    }

    public boolean equals(Object other) {
        if (!(other instanceof j) || !canEqual(other) || valueType() != ((j) other).valueType() || !g.a(unwrapped(), ((j) other).unwrapped())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        Object o = unwrapped();
        if (o == null) {
            return 0;
        }
        return o.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        render(sb, 0, true, (String) null, g.a());
        return getClass().getSimpleName() + "(" + sb.toString() + ")";
    }

    protected static void indent(StringBuilder sb, int indent, g options) {
        if (options.d()) {
            for (int remaining = indent; remaining > 0; remaining--) {
                sb.append("    ");
            }
        }
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, String atKey, g options) {
        String renderedKey;
        if (atKey != null) {
            if (options.e()) {
                renderedKey = g.f(atKey);
            } else {
                renderedKey = g.g(atKey);
            }
            sb.append(renderedKey);
            if (options.e()) {
                if (options.d()) {
                    sb.append(" : ");
                } else {
                    sb.append(":");
                }
            } else if (!(this instanceof e)) {
                sb.append("=");
            } else if (options.d()) {
                sb.append(' ');
            }
        }
        render(sb, indent, atRoot, options);
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, g options) {
        sb.append(unwrapped().toString());
    }

    public final String render() {
        return render(g.b());
    }

    public final String render(g options) {
        StringBuilder sb = new StringBuilder();
        render(sb, 0, true, (String) null, options);
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public String transformToString() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public c0 atKey(f origin2, String key) {
        return new e0(origin2, Collections.singletonMap(key, this)).toConfig();
    }

    public c0 atKey(String key) {
        return atKey(f0.l("atKey(" + key + ")"), key);
    }

    /* access modifiers changed from: package-private */
    public c0 atPath(f origin2, s path) {
        c0 result = atKey(origin2, path.d());
        for (s parent = path.h(); parent != null; parent = parent.h()) {
            result = result.atKey(origin2, parent.d());
        }
        return result;
    }

    public c0 atPath(String pathExpression) {
        return atPath(f0.l("atPath(" + pathExpression + ")"), s.g(pathExpression));
    }
}
