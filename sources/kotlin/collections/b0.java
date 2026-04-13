package kotlin.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Maps.kt */
public final class b0 implements Map, Serializable, a {
    @NotNull
    public static final b0 INSTANCE = new b0();
    private static final long serialVersionUID = 8246714829545688274L;

    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public /* synthetic */ Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Void put(Object obj, Void voidR) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public void putAll(Map map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public Object remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    private b0() {
    }

    public final /* bridge */ boolean containsValue(Object obj) {
        if (obj instanceof Void) {
            return containsValue((Void) obj);
        }
        return false;
    }

    public final /* bridge */ Set<Map.Entry> entrySet() {
        return getEntries();
    }

    public final /* bridge */ Object get(Object obj) {
        return get(obj);
    }

    public final /* bridge */ Set<Object> keySet() {
        return getKeys();
    }

    public final /* bridge */ int size() {
        return getSize();
    }

    public final /* bridge */ Collection values() {
        return getValues();
    }

    public boolean equals(@Nullable Object other) {
        return (other instanceof Map) && ((Map) other).isEmpty();
    }

    public int hashCode() {
        return 0;
    }

    @NotNull
    public String toString() {
        return "{}";
    }

    public int getSize() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean containsKey(@Nullable Object key) {
        return false;
    }

    public boolean containsValue(@NotNull Void value) {
        k.e(value, "value");
        return false;
    }

    @Nullable
    public Void get(@Nullable Object key) {
        return null;
    }

    @NotNull
    public Set<Map.Entry> getEntries() {
        return c0.INSTANCE;
    }

    @NotNull
    public Set<Object> getKeys() {
        return c0.INSTANCE;
    }

    @NotNull
    public Collection getValues() {
        return a0.INSTANCE;
    }

    private final Object readResolve() {
        return INSTANCE;
    }
}
