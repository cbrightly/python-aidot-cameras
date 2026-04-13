package com.typesafe.config.impl;

import com.typesafe.config.ConfigException;
import com.typesafe.config.d;
import com.typesafe.config.f;
import com.typesafe.config.g;
import com.typesafe.config.k;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/* compiled from: ConfigDelayedMerge */
public final class c extends AbstractConfigValue implements k0, v {
    private final List<AbstractConfigValue> c;

    /* JADX WARNING: Removed duplicated region for block: B:5:0x0015  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    c(com.typesafe.config.f r4, java.util.List<com.typesafe.config.impl.AbstractConfigValue> r5) {
        /*
            r3 = this;
            r3.<init>(r4)
            r3.c = r5
            boolean r0 = r5.isEmpty()
            if (r0 != 0) goto L_0x002e
            java.util.Iterator r0 = r5.iterator()
        L_0x000f:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x002d
            java.lang.Object r1 = r0.next()
            com.typesafe.config.impl.AbstractConfigValue r1 = (com.typesafe.config.impl.AbstractConfigValue) r1
            boolean r2 = r1 instanceof com.typesafe.config.impl.c
            if (r2 != 0) goto L_0x0024
            boolean r2 = r1 instanceof com.typesafe.config.impl.d
            if (r2 != 0) goto L_0x0024
            goto L_0x000f
        L_0x0024:
            com.typesafe.config.ConfigException$BugOrBroken r0 = new com.typesafe.config.ConfigException$BugOrBroken
            java.lang.String r2 = "placed nested DelayedMerge in a ConfigDelayedMerge, should have consolidated stack"
            r0.<init>(r2)
            throw r0
        L_0x002d:
            return
        L_0x002e:
            com.typesafe.config.ConfigException$BugOrBroken r0 = new com.typesafe.config.ConfigException$BugOrBroken
            java.lang.String r1 = "creating empty delayed merge value"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.typesafe.config.impl.c.<init>(com.typesafe.config.f, java.util.List):void");
    }

    public k valueType() {
        throw new ConfigException.NotResolved("called valueType() on value with unresolved substitutions, need to Config#resolve() first, see API docs");
    }

    public Object unwrapped() {
        throw new ConfigException.NotResolved("called unwrapped() on value with unresolved substitutions, need to Config#resolve() first, see API docs");
    }

    /* access modifiers changed from: package-private */
    public y<? extends AbstractConfigValue> resolveSubstitutions(w context, z source) {
        return o(this, this.c, context, source);
    }

    static y<? extends AbstractConfigValue> o(v replaceable, List<AbstractConfigValue> stack, w context, z source) {
        z sourceForEnd;
        if (f.g()) {
            int b = context.b();
            f.e(b, "delayed merge stack has " + stack.size() + " items:");
            int count = 0;
            for (AbstractConfigValue v : stack) {
                f.e(context.b() + 1, count + ": " + v);
                count++;
            }
        }
        w newContext = context;
        int count2 = 0;
        AbstractConfigValue merged = null;
        for (AbstractConfigValue end : stack) {
            if (!(end instanceof v)) {
                if (end instanceof k0) {
                    AbstractConfigValue remainder = replaceable.b(context, count2 + 1);
                    if (f.g()) {
                        int b2 = newContext.b();
                        f.e(b2, "remainder portion: " + remainder);
                    }
                    if (f.g()) {
                        f.e(newContext.b(), "building sourceForEnd");
                    }
                    z sourceForEnd2 = source.h((AbstractConfigValue) replaceable, remainder);
                    if (f.g()) {
                        int b3 = newContext.b();
                        f.e(b3, "  sourceForEnd before reset parents but after replace: " + sourceForEnd2);
                    }
                    sourceForEnd = sourceForEnd2.i();
                } else {
                    if (f.g()) {
                        f.e(newContext.b(), "will resolve end against the original source with parent pushed");
                    }
                    sourceForEnd = source.e(replaceable);
                }
                if (f.g()) {
                    int b4 = newContext.b();
                    f.e(b4, "sourceForEnd      =" + sourceForEnd);
                }
                if (f.g()) {
                    int b5 = newContext.b();
                    StringBuilder sb = new StringBuilder();
                    sb.append("Resolving highest-priority item in delayed merge ");
                    sb.append(end);
                    sb.append(" against ");
                    sb.append(sourceForEnd);
                    sb.append(" endWasRemoved=");
                    sb.append(source != sourceForEnd);
                    f.e(b5, sb.toString());
                }
                ResolveResult<? extends AbstractConfigValue> result = newContext.l(end, sourceForEnd);
                AbstractConfigValue resolvedEnd = result.b;
                newContext = result.a;
                if (resolvedEnd != null) {
                    if (merged == null) {
                        merged = resolvedEnd;
                    } else {
                        if (f.g()) {
                            f.e(newContext.b() + 1, "merging " + merged + " with fallback " + resolvedEnd);
                        }
                        merged = merged.withFallback((d) resolvedEnd);
                    }
                }
                count2++;
                if (f.g()) {
                    int b6 = newContext.b();
                    f.e(b6, "stack merged, yielding: " + merged);
                }
            } else {
                throw new ConfigException.BugOrBroken("A delayed merge should not contain another one: " + replaceable);
            }
        }
        return y.b(newContext, merged);
    }

    public AbstractConfigValue b(w context, int skipping) {
        return f(context, this.c, skipping);
    }

    static AbstractConfigValue f(w context, List<AbstractConfigValue> stack, int skipping) {
        List<AbstractConfigValue> subStack = stack.subList(skipping, stack.size());
        if (!subStack.isEmpty()) {
            AbstractConfigValue merged = null;
            for (AbstractConfigValue v : subStack) {
                if (merged == null) {
                    merged = v;
                } else {
                    merged = merged.withFallback((d) v);
                }
            }
            return merged;
        } else if (!f.g()) {
            return null;
        } else {
            f.e(context.b(), "Nothing else in the merge stack, replacing with null");
            return null;
        }
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
        return new c(origin(), newStack);
    }

    public boolean hasDescendant(AbstractConfigValue descendant) {
        return AbstractConfigValue.hasDescendantInList(this.c, descendant);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: k */
    public c relativized(s prefix) {
        List<AbstractConfigValue> newStack = new ArrayList<>();
        for (AbstractConfigValue o : this.c) {
            newStack.add(o.relativized(prefix));
        }
        return new c(origin(), newStack);
    }

    static boolean p(List<AbstractConfigValue> stack) {
        return stack.get(stack.size() - 1).ignoresFallbacks();
    }

    /* access modifiers changed from: protected */
    public boolean ignoresFallbacks() {
        return p(this.c);
    }

    /* access modifiers changed from: protected */
    public AbstractConfigValue newCopy(f newOrigin) {
        return new c(newOrigin, this.c);
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public final c mergedWithTheUnmergeable(k0 fallback) {
        return (c) mergedWithTheUnmergeable(this.c, fallback);
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public final c mergedWithObject(a fallback) {
        return (c) mergedWithObject(this.c, fallback);
    }

    /* access modifiers changed from: protected */
    /* renamed from: g */
    public c mergedWithNonObject(AbstractConfigValue fallback) {
        return (c) mergedWithNonObject(this.c, fallback);
    }

    public Collection<AbstractConfigValue> a() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public boolean canEqual(Object other) {
        return other instanceof c;
    }

    public boolean equals(Object other) {
        if (!(other instanceof c) || !canEqual(other)) {
            return false;
        }
        List<AbstractConfigValue> list = this.c;
        if (list == ((c) other).c || list.equals(((c) other).c)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, String atKey, g options) {
        n(this.c, sb, indent, atRoot, atKey, options);
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, g options) {
        render(sb, indent, atRoot, (String) null, options);
    }

    static void n(List<AbstractConfigValue> stack, StringBuilder sb, int indent, boolean atRoot, String atKey, g options) {
        boolean commentMerge = options.c();
        if (commentMerge) {
            sb.append("# unresolved merge of " + stack.size() + " values follows (\n");
            if (atKey == null) {
                AbstractConfigValue.indent(sb, indent, options);
                sb.append("# this unresolved merge will not be parseable because it's at the root of the object\n");
                AbstractConfigValue.indent(sb, indent, options);
                sb.append("# the HOCON format has no way to list multiple root objects in a single file\n");
            }
        }
        List<AbstractConfigValue> reversed = new ArrayList<>();
        reversed.addAll(stack);
        Collections.reverse(reversed);
        int i = 0;
        for (AbstractConfigValue v : reversed) {
            if (commentMerge) {
                AbstractConfigValue.indent(sb, indent, options);
                if (atKey != null) {
                    sb.append("#     unmerged value " + i + " for key " + g.f(atKey) + " from ");
                } else {
                    sb.append("#     unmerged value " + i + " from ");
                }
                i++;
                sb.append(v.origin().a());
                sb.append("\n");
                for (String comment : v.origin().d()) {
                    AbstractConfigValue.indent(sb, indent, options);
                    sb.append("# ");
                    sb.append(comment);
                    sb.append("\n");
                }
            }
            AbstractConfigValue.indent(sb, indent, options);
            if (atKey != null) {
                sb.append(g.f(atKey));
                if (options.d()) {
                    sb.append(" : ");
                } else {
                    sb.append(":");
                }
            }
            v.render(sb, indent, atRoot, options);
            sb.append(",");
            if (options.d()) {
                sb.append(10);
            }
        }
        sb.setLength(sb.length() - 1);
        if (options.d()) {
            sb.setLength(sb.length() - 1);
            sb.append("\n");
        }
        if (commentMerge) {
            AbstractConfigValue.indent(sb, indent, options);
            sb.append("# ) end of unresolved merge\n");
        }
    }
}
