package com.typesafe.config.impl;

import com.meituan.robust.Constants;
import com.typesafe.config.ConfigException;
import com.typesafe.config.impl.ResolveSource;

/* compiled from: ResolveSource */
public final class z {
    final a a;
    final a<n> b;

    z(a root, a<n> pathFromRoot) {
        this.a = root;
        this.b = pathFromRoot;
    }

    z(a root) {
        this.a = root;
        this.b = null;
    }

    private a j(n value) {
        if (value instanceof a) {
            return (a) value;
        }
        return e0.empty();
    }

    private static b a(a obj, w context, s path) {
        if (f.g()) {
            f.f("*** finding '" + path + "' in " + obj);
        }
        s restriction = context.n();
        ResolveResult<? extends AbstractConfigValue> partiallyResolved = context.m(path).l(obj, new z(obj));
        w newContext = partiallyResolved.a.m(restriction);
        V v = partiallyResolved.b;
        if (v instanceof a) {
            c pair = b((a) v, path);
            return new b(y.b(newContext, pair.a), pair.b);
        }
        throw new ConfigException.BugOrBroken("resolved object to non-object " + obj + " to " + partiallyResolved);
    }

    private static c b(a obj, s path) {
        try {
            return c(obj, path, (a<n>) null);
        } catch (ConfigException.NotResolved e) {
            throw f.c(path, e);
        }
    }

    private static c c(a obj, s path, a<n> parents) {
        String key = path.b();
        s next = path.j();
        if (f.g()) {
            f.f("*** looking up '" + key + "' in " + obj);
        }
        AbstractConfigValue v = obj.attemptPeekWithPartialResolve(key);
        ResolveSource.Node<Container> newParents = parents == null ? new a<>(obj) : parents.c(obj);
        if (next == null) {
            return new c(v, newParents);
        }
        if (v instanceof a) {
            return c((a) v, next, newParents);
        }
        return new c((AbstractConfigValue) null, newParents);
    }

    /* access modifiers changed from: package-private */
    public b d(w context, g0 subst, int prefixLength) {
        if (f.g()) {
            int b2 = context.b();
            f.e(b2, "searching for " + subst);
        }
        if (f.g()) {
            int b3 = context.b();
            f.e(b3, subst + " - looking up relative to file it occurred in");
        }
        b result = a(this.a, context, subst.b());
        if (result.a.b == null) {
            s unprefixed = subst.b().l(prefixLength);
            if (prefixLength > 0) {
                if (f.g()) {
                    int b4 = result.a.a.b();
                    f.e(b4, unprefixed + " - looking up relative to parent file");
                }
                result = a(this.a, result.a.a, unprefixed);
            }
            y<? extends AbstractConfigValue> yVar = result.a;
            if (yVar.b == null && yVar.a.f().c()) {
                if (f.g()) {
                    int b5 = result.a.a.b();
                    f.e(b5, unprefixed + " - looking up in system environment");
                }
                result = a(f.b(), context, unprefixed);
            }
        }
        if (f.g()) {
            int b6 = result.a.a.b();
            f.e(b6, "resolved to " + result);
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public z e(n parent) {
        if (parent != null) {
            if (f.g()) {
                StringBuilder sb = new StringBuilder();
                sb.append("pushing parent ");
                sb.append(parent);
                sb.append(" ==root ");
                sb.append(parent == this.a);
                sb.append(" onto ");
                sb.append(this);
                f.f(sb.toString());
            }
            a<n> aVar = this.b;
            if (aVar == null) {
                a aVar2 = this.a;
                if (parent == aVar2) {
                    return new z(aVar2, new a(parent));
                }
                if (f.g() && this.a.hasDescendant((AbstractConfigValue) parent)) {
                    f.f("***** BUG ***** tried to push parent " + parent + " without having a path to it in " + this);
                }
                return this;
            }
            n parentParent = aVar.a();
            if (f.g() && parentParent != null && !parentParent.hasDescendant((AbstractConfigValue) parent)) {
                f.f("***** BUG ***** trying to push non-child of " + parentParent + ", non-child was " + parent);
            }
            return new z(this.a, this.b.c(parent));
        }
        throw new ConfigException.BugOrBroken("can't push null parent");
    }

    /* access modifiers changed from: package-private */
    public z i() {
        if (this.b == null) {
            return this;
        }
        return new z(this.a);
    }

    private static a<n> f(a<n> list, n old, AbstractConfigValue replacement) {
        n child = list.a();
        if (child == old) {
            n parent = list.e() == null ? null : list.e().a();
            if (replacement == null || !(replacement instanceof n)) {
                if (parent == null) {
                    return null;
                }
                return f(list.e(), parent, parent.replaceChild((AbstractConfigValue) old, (AbstractConfigValue) null));
            } else if (parent == null) {
                return new a<>((n) replacement);
            } else {
                ResolveSource.Node<Container> newTail = f(list.e(), parent, parent.replaceChild((AbstractConfigValue) old, replacement));
                if (newTail != null) {
                    return newTail.c((n) replacement);
                }
                return new a<>((n) replacement);
            }
        } else {
            throw new ConfigException.BugOrBroken("Can only replace() the top node we're resolving; had " + child + " on top and tried to replace " + old + " overall list was " + list);
        }
    }

    /* access modifiers changed from: package-private */
    public z g(n old, n replacement) {
        if (f.g()) {
            f.f("replaceCurrentParent old " + old + "@" + System.identityHashCode(old) + " replacement " + replacement + "@" + System.identityHashCode(old) + " in " + this);
        }
        if (old == replacement) {
            return this;
        }
        a<n> aVar = this.b;
        if (aVar != null) {
            ResolveSource.Node<Container> newPath = f(aVar, old, (AbstractConfigValue) replacement);
            if (f.g()) {
                f.f("replaced " + old + " with " + replacement + " in " + this);
                StringBuilder sb = new StringBuilder();
                sb.append("path was: ");
                sb.append(this.b);
                sb.append(" is now ");
                sb.append(newPath);
                f.f(sb.toString());
            }
            if (newPath != null) {
                return new z((a) newPath.b(), newPath);
            }
            return new z(e0.empty());
        } else if (old == this.a) {
            return new z(j(replacement));
        } else {
            throw new ConfigException.BugOrBroken("attempt to replace root " + this.a + " with " + replacement);
        }
    }

    /* access modifiers changed from: package-private */
    public z h(AbstractConfigValue old, AbstractConfigValue replacement) {
        if (f.g()) {
            f.f("replaceWithinCurrentParent old " + old + "@" + System.identityHashCode(old) + " replacement " + replacement + "@" + System.identityHashCode(old) + " in " + this);
        }
        if (old == replacement) {
            return this;
        }
        a<n> aVar = this.b;
        if (aVar != null) {
            n parent = aVar.a();
            AbstractConfigValue newParent = parent.replaceChild(old, replacement);
            return g(parent, newParent instanceof n ? (n) newParent : null);
        } else if (old == this.a && (replacement instanceof n)) {
            return new z(j((n) replacement));
        } else {
            throw new ConfigException.BugOrBroken("replace in parent not possible " + old + " with " + replacement + " in " + this);
        }
    }

    public String toString() {
        return "ResolveSource(root=" + this.a + ", pathFromRoot=" + this.b + ")";
    }

    /* compiled from: ResolveSource */
    public static final class a<T> {
        final T a;
        final a<T> b;

        a(T value, a<T> next) {
            this.a = value;
            this.b = next;
        }

        a(T value) {
            this(value, (a) null);
        }

        /* access modifiers changed from: package-private */
        public a<T> c(T value) {
            return new a<>(value, this);
        }

        /* access modifiers changed from: package-private */
        public T a() {
            return this.a;
        }

        /* access modifiers changed from: package-private */
        public a<T> e() {
            return this.b;
        }

        /* access modifiers changed from: package-private */
        public T b() {
            a aVar = this;
            while (aVar.b != null) {
                aVar = aVar.b;
            }
            return aVar.a;
        }

        /* access modifiers changed from: package-private */
        public a<T> d() {
            if (this.b == null) {
                return this;
            }
            ResolveSource.Node<T> reversed = new a<>(this.a);
            for (ResolveSource.Node<T> i = this.b; i != null; i = i.b) {
                reversed = reversed.c(i.a);
            }
            return reversed;
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(Constants.ARRAY_TYPE);
            for (ResolveSource.Node<T> toAppendValue = d(); toAppendValue != null; toAppendValue = toAppendValue.b) {
                sb.append(toAppendValue.a.toString());
                if (toAppendValue.b != null) {
                    sb.append(" <= ");
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }

    /* compiled from: ResolveSource */
    public static final class c {
        final AbstractConfigValue a;
        final a<n> b;

        c(AbstractConfigValue value, a<n> pathFromRoot) {
            this.a = value;
            this.b = pathFromRoot;
        }

        public String toString() {
            return "ValueWithPath(value=" + this.a + ", pathFromRoot=" + this.b + ")";
        }
    }

    /* compiled from: ResolveSource */
    public static final class b {
        final y<? extends AbstractConfigValue> a;
        final a<n> b;

        b(y<? extends AbstractConfigValue> result, a<n> pathFromRoot) {
            this.a = result;
            this.b = pathFromRoot;
        }

        public String toString() {
            return "ResultWithPath(result=" + this.a + ", pathFromRoot=" + this.b + ")";
        }
    }
}
