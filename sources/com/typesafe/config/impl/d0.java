package com.typesafe.config.impl;

import com.meituan.robust.Constants;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.f;
import com.typesafe.config.g;
import com.typesafe.config.impl.AbstractConfigValue;
import com.typesafe.config.j;
import com.typesafe.config.k;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* compiled from: SimpleConfigList */
public final class d0 extends AbstractConfigValue implements com.typesafe.config.b, n, Serializable {
    private static final long serialVersionUID = 2;
    private final boolean resolved;
    private final List<AbstractConfigValue> value;

    d0(f origin, List<AbstractConfigValue> value2) {
        this(origin, value2, a0.fromValues(value2));
    }

    d0(f origin, List<AbstractConfigValue> value2, a0 status) {
        super(origin);
        this.value = value2;
        this.resolved = status == a0.RESOLVED;
        if (status != a0.fromValues(value2)) {
            throw new ConfigException.BugOrBroken("SimpleConfigList created with wrong resolve status: " + this);
        }
    }

    public k valueType() {
        return k.LIST;
    }

    public List<Object> unwrapped() {
        List<Object> list = new ArrayList<>();
        for (AbstractConfigValue v : this.value) {
            list.add(v.unwrapped());
        }
        return list;
    }

    /* access modifiers changed from: package-private */
    public a0 resolveStatus() {
        return a0.fromBoolean(this.resolved);
    }

    public d0 replaceChild(AbstractConfigValue child, AbstractConfigValue replacement) {
        List<AbstractConfigValue> newList = AbstractConfigValue.replaceChildInList(this.value, child, replacement);
        if (newList == null) {
            return null;
        }
        return new d0(origin(), newList);
    }

    public boolean hasDescendant(AbstractConfigValue descendant) {
        return AbstractConfigValue.hasDescendantInList(this.value, descendant);
    }

    private d0 f(AbstractConfigValue.b modifier, a0 newResolveStatus) {
        try {
            return g(modifier, newResolveStatus);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new ConfigException.BugOrBroken("unexpected checked exception", e2);
        }
    }

    private d0 g(AbstractConfigValue.a modifier, a0 newResolveStatus) {
        List<AbstractConfigValue> changed = null;
        int i = 0;
        for (AbstractConfigValue v : this.value) {
            AbstractConfigValue modified = modifier.a((String) null, v);
            if (changed == null && modified != v) {
                changed = new ArrayList<>();
                for (int j = 0; j < i; j++) {
                    changed.add(this.value.get(j));
                }
            }
            if (!(changed == null || modified == null)) {
                changed.add(modified);
            }
            i++;
        }
        if (changed == null) {
            return this;
        }
        if (newResolveStatus != null) {
            return new d0(origin(), changed, newResolveStatus);
        }
        return new d0(origin(), changed);
    }

    /* compiled from: SimpleConfigList */
    public static class d implements AbstractConfigValue.a {
        w a;
        final z b;

        d(w context, z source) {
            this.a = context;
            this.b = source;
        }

        public AbstractConfigValue a(String key, AbstractConfigValue v) {
            ResolveResult<? extends AbstractConfigValue> result = this.a.l(v, this.b);
            this.a = result.a;
            return result.b;
        }
    }

    /* access modifiers changed from: package-private */
    public y<? extends d0> resolveSubstitutions(w context, z source) {
        if (this.resolved) {
            return y.b(context, this);
        }
        if (context.c()) {
            return y.b(context, this);
        }
        try {
            d modifier = new d(context, source.e(this));
            return y.b(modifier.a, g(modifier, context.f().b() ? null : a0.RESOLVED));
        } catch (AbstractConfigValue.NotPossibleToResolve e) {
            throw e;
        } catch (RuntimeException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new ConfigException.BugOrBroken("unexpected checked exception", e3);
        }
    }

    /* compiled from: SimpleConfigList */
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
    public d0 relativized(s prefix) {
        return f(new a(prefix), resolveStatus());
    }

    /* access modifiers changed from: protected */
    public boolean canEqual(Object other) {
        return other instanceof d0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof d0) || !canEqual(other)) {
            return false;
        }
        List<AbstractConfigValue> list = this.value;
        if (list == ((d0) other).value || list.equals(((d0) other).value)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    /* access modifiers changed from: protected */
    public void render(StringBuilder sb, int indent, boolean atRoot, g options) {
        if (this.value.isEmpty()) {
            sb.append("[]");
            return;
        }
        sb.append(Constants.ARRAY_TYPE);
        if (options.d()) {
            sb.append(10);
        }
        for (AbstractConfigValue v : this.value) {
            if (options.f()) {
                for (String l : v.origin().a().split("\n")) {
                    AbstractConfigValue.indent(sb, indent + 1, options);
                    sb.append('#');
                    if (!l.isEmpty()) {
                        sb.append(' ');
                    }
                    sb.append(l);
                    sb.append("\n");
                }
            }
            if (options.c()) {
                for (String comment : v.origin().d()) {
                    AbstractConfigValue.indent(sb, indent + 1, options);
                    sb.append("# ");
                    sb.append(comment);
                    sb.append("\n");
                }
            }
            AbstractConfigValue.indent(sb, indent + 1, options);
            v.render(sb, indent + 1, atRoot, options);
            sb.append(",");
            if (options.d()) {
                sb.append(10);
            }
        }
        sb.setLength(sb.length() - 1);
        if (options.d()) {
            sb.setLength(sb.length() - 1);
            sb.append(10);
            AbstractConfigValue.indent(sb, indent, options);
        }
        sb.append("]");
    }

    public boolean contains(Object o) {
        return this.value.contains(o);
    }

    public boolean containsAll(Collection<?> c2) {
        return this.value.containsAll(c2);
    }

    public AbstractConfigValue get(int index) {
        return this.value.get(index);
    }

    public int indexOf(Object o) {
        return this.value.indexOf(o);
    }

    public boolean isEmpty() {
        return this.value.isEmpty();
    }

    public Iterator<j> iterator() {
        return new b(this.value.iterator());
    }

    /* compiled from: SimpleConfigList */
    public class b implements Iterator<j> {
        final /* synthetic */ Iterator c;

        b(Iterator it) {
            this.c = it;
        }

        public boolean hasNext() {
            return this.c.hasNext();
        }

        /* renamed from: b */
        public j next() {
            return (j) this.c.next();
        }

        public void remove() {
            throw d0.h("iterator().remove");
        }
    }

    public int lastIndexOf(Object o) {
        return this.value.lastIndexOf(o);
    }

    /* compiled from: SimpleConfigList */
    public static final class c implements ListIterator<j> {
        final /* synthetic */ ListIterator c;

        c(ListIterator listIterator) {
            this.c = listIterator;
        }

        public boolean hasNext() {
            return this.c.hasNext();
        }

        /* renamed from: c */
        public j next() {
            return (j) this.c.next();
        }

        public void remove() {
            throw d0.h("listIterator().remove");
        }

        /* renamed from: b */
        public void add(j arg0) {
            throw d0.h("listIterator().add");
        }

        public boolean hasPrevious() {
            return this.c.hasPrevious();
        }

        public int nextIndex() {
            return this.c.nextIndex();
        }

        /* renamed from: d */
        public j previous() {
            return (j) this.c.previous();
        }

        public int previousIndex() {
            return this.c.previousIndex();
        }

        /* renamed from: e */
        public void set(j arg0) {
            throw d0.h("listIterator().set");
        }
    }

    private static ListIterator<j> i(ListIterator<AbstractConfigValue> i) {
        return new c(i);
    }

    public ListIterator<j> listIterator() {
        return i(this.value.listIterator());
    }

    public ListIterator<j> listIterator(int index) {
        return i(this.value.listIterator(index));
    }

    public int size() {
        return this.value.size();
    }

    public List<j> subList(int fromIndex, int toIndex) {
        List<ConfigValue> list = new ArrayList<>();
        for (AbstractConfigValue v : this.value.subList(fromIndex, toIndex)) {
            list.add(v);
        }
        return list;
    }

    public Object[] toArray() {
        return this.value.toArray();
    }

    public <T> T[] toArray(T[] a2) {
        return this.value.toArray(a2);
    }

    /* access modifiers changed from: private */
    public static UnsupportedOperationException h(String method) {
        return new UnsupportedOperationException("ConfigList is immutable, you can't call List.'" + method + "'");
    }

    public boolean add(j e) {
        throw h("add");
    }

    public void add(int index, j element) {
        throw h("add");
    }

    public boolean addAll(Collection<? extends j> collection) {
        throw h("addAll");
    }

    public boolean addAll(int index, Collection<? extends j> collection) {
        throw h("addAll");
    }

    public void clear() {
        throw h("clear");
    }

    public boolean remove(Object o) {
        throw h("remove");
    }

    public j remove(int index) {
        throw h("remove");
    }

    public boolean removeAll(Collection<?> collection) {
        throw h("removeAll");
    }

    public boolean retainAll(Collection<?> collection) {
        throw h("retainAll");
    }

    public j set(int index, j element) {
        throw h("set");
    }

    /* access modifiers changed from: protected */
    public d0 newCopy(f newOrigin) {
        return new d0(newOrigin, this.value);
    }

    /* access modifiers changed from: package-private */
    public final d0 concatenate(d0 other) {
        f combinedOrigin = f0.h(origin(), other.origin());
        List<AbstractConfigValue> combined = new ArrayList<>(this.value.size() + other.value.size());
        combined.addAll(this.value);
        combined.addAll(other.value);
        return new d0(combinedOrigin, combined);
    }

    private Object writeReplace() {
        return new b0((j) this);
    }

    public d0 withOrigin(f origin) {
        return (d0) super.withOrigin(origin);
    }
}
