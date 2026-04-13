package io.ktor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.r;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DelegatingMutableSet.kt */
public class m<From, To> implements Set<To>, e {
    private final int c;
    /* access modifiers changed from: private */
    public final Set<From> d;
    /* access modifiers changed from: private */
    public final l<From, To> f;
    private final l<To, From> q;

    public Object[] toArray() {
        return f.a(this);
    }

    public <T> T[] toArray(T[] tArr) {
        return f.b(this, tArr);
    }

    public m(@NotNull Set<From> delegate, @NotNull l<? super From, ? extends To> convertTo, @NotNull l<? super To, ? extends From> convert) {
        k.f(delegate, "delegate");
        k.f(convertTo, "convertTo");
        k.f(convert, "convert");
        this.d = delegate;
        this.f = convertTo;
        this.q = convert;
        this.c = delegate.size();
    }

    public final /* bridge */ int size() {
        return g();
    }

    @NotNull
    public Collection<From> e(@NotNull Collection<? extends To> $this$convert) {
        k.f($this$convert, "$this$convert");
        Iterable<Object> $this$mapTo$iv$iv = $this$convert;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Object it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(this.q.invoke(it));
        }
        return destination$iv$iv;
    }

    @NotNull
    public Collection<To> f(@NotNull Collection<? extends From> $this$convertTo) {
        k.f($this$convertTo, "$this$convertTo");
        Iterable<Object> $this$mapTo$iv$iv = $this$convertTo;
        Collection destination$iv$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Object it : $this$mapTo$iv$iv) {
            destination$iv$iv.add(this.f.invoke(it));
        }
        return destination$iv$iv;
    }

    public int g() {
        return this.c;
    }

    public boolean add(To element) {
        return this.d.add(this.q.invoke(element));
    }

    public boolean addAll(@NotNull Collection<? extends To> elements) {
        k.f(elements, "elements");
        return this.d.addAll(e(elements));
    }

    public void clear() {
        this.d.clear();
    }

    public boolean remove(Object element) {
        return this.d.remove(this.q.invoke(element));
    }

    public boolean removeAll(@NotNull Collection<? extends Object> elements) {
        k.f(elements, "elements");
        return this.d.removeAll(e(elements));
    }

    public boolean retainAll(@NotNull Collection<? extends Object> elements) {
        k.f(elements, "elements");
        return this.d.retainAll(e(elements));
    }

    public boolean contains(Object element) {
        return this.d.contains(this.q.invoke(element));
    }

    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        k.f(elements, "elements");
        return this.d.containsAll(e(elements));
    }

    public boolean isEmpty() {
        return this.d.isEmpty();
    }

    /* compiled from: DelegatingMutableSet.kt */
    public static final class a implements Iterator<To>, kotlin.jvm.internal.markers.a {
        @NotNull
        private final Iterator<From> c;
        final /* synthetic */ m d;

        a(m $outer) {
            this.d = $outer;
            this.c = $outer.d.iterator();
        }

        public boolean hasNext() {
            return this.c.hasNext();
        }

        public To next() {
            return this.d.f.invoke(this.c.next());
        }

        public void remove() {
            this.c.remove();
        }
    }

    @NotNull
    public Iterator<To> iterator() {
        return new a(this);
    }

    public int hashCode() {
        return this.d.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (other == null || !(other instanceof Set)) {
            return false;
        }
        Collection elements = f(this.d);
        if (!((Set) other).containsAll(elements) || !elements.containsAll((Collection) other)) {
            return false;
        }
        return true;
    }

    @NotNull
    public String toString() {
        return f(this.d).toString();
    }
}
