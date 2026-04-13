package kotlin.collections.builders;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MapBuilder.kt */
public final class d<K, V> extends a<Map.Entry<K, V>, K, V> {
    @NotNull
    private final c<K, V> c;

    public d(@NotNull c<K, V> backing) {
        k.e(backing, "backing");
        this.c = backing;
    }

    public int a() {
        return this.c.size();
    }

    public boolean isEmpty() {
        return this.c.isEmpty();
    }

    public boolean e(@NotNull Map.Entry<? extends K, ? extends V> element) {
        k.e(element, "element");
        return this.c.o(element);
    }

    public void clear() {
        this.c.clear();
    }

    /* renamed from: g */
    public boolean add(@NotNull Map.Entry<K, V> element) {
        k.e(element, "element");
        throw new UnsupportedOperationException();
    }

    public boolean addAll(@NotNull Collection<? extends Map.Entry<K, V>> elements) {
        k.e(elements, "elements");
        throw new UnsupportedOperationException();
    }

    public boolean f(@NotNull Map.Entry element) {
        k.e(element, "element");
        return this.c.H(element);
    }

    @NotNull
    public Iterator<Map.Entry<K, V>> iterator() {
        return this.c.s();
    }

    public boolean containsAll(@NotNull Collection<? extends Object> elements) {
        k.e(elements, "elements");
        return this.c.n(elements);
    }

    public boolean removeAll(@NotNull Collection<? extends Object> elements) {
        k.e(elements, "elements");
        this.c.l();
        return super.removeAll(elements);
    }

    public boolean retainAll(@NotNull Collection<? extends Object> elements) {
        k.e(elements, "elements");
        this.c.l();
        return super.retainAll(elements);
    }
}
