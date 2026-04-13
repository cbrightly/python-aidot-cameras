package io.ktor.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.v;
import kotlin.jvm.internal.f;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.e;
import org.jetbrains.annotations.NotNull;

/* compiled from: CaseInsensitiveSet.kt */
public final class g implements Set<String>, e {
    private final f<Boolean> c;

    public Object[] toArray() {
        return f.a(this);
    }

    public <T> T[] toArray(T[] tArr) {
        return f.b(this, tArr);
    }

    public g() {
        this.c = new f<>();
    }

    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof String) {
            return b((String) obj);
        }
        return false;
    }

    public final /* bridge */ boolean remove(Object obj) {
        if (obj instanceof String) {
            return f((String) obj);
        }
        return false;
    }

    public final /* bridge */ int size() {
        return e();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public g(@NotNull Iterable<String> initial) {
        this();
        k.f(initial, "initial");
        v.x(this, initial);
    }

    /* renamed from: a */
    public boolean add(@NotNull String element) {
        k.f(element, "element");
        if (this.c.containsKey(element)) {
            return false;
        }
        this.c.put(element, true);
        return true;
    }

    public int e() {
        return this.c.size();
    }

    public boolean f(@NotNull String element) {
        k.f(element, "element");
        return k.a(this.c.remove(element), true);
    }

    public boolean addAll(@NotNull Collection<? extends String> elements) {
        k.f(elements, "elements");
        boolean added = false;
        for (String element : elements) {
            if (add(element)) {
                added = true;
            }
        }
        return added;
    }

    public void clear() {
        this.c.clear();
    }

    public boolean removeAll(@NotNull Collection<? extends Object> elements) {
        k.f(elements, "elements");
        return this.c.keySet().removeAll(elements);
    }

    public boolean retainAll(@NotNull Collection<? extends Object> elements) {
        k.f(elements, "elements");
        return this.c.keySet().retainAll(elements);
    }

    public boolean b(@NotNull String element) {
        k.f(element, "element");
        return this.c.containsKey(element);
    }

    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        k.f(elements, "elements");
        return this.c.keySet().containsAll(elements);
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    @NotNull
    public Iterator<String> iterator() {
        return this.c.keySet().iterator();
    }
}
