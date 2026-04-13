package kotlin.reflect.jvm.internal.pcollections;

import org.jetbrains.annotations.NotNull;

/* compiled from: HashPMap */
public final class b<K, V> {
    private static final b<Object, Object> a = new b<>(d.a(), 0);
    private final d<a<e<K, V>>> b;
    private final int c;

    private static /* synthetic */ void a(int i) {
        Object[] objArr = new Object[2];
        objArr[0] = "kotlin/reflect/jvm/internal/pcollections/HashPMap";
        switch (i) {
            case 1:
                objArr[1] = "minus";
                break;
            default:
                objArr[1] = "empty";
                break;
        }
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
    }

    @NotNull
    public static <K, V> b<K, V> b() {
        b<Object, Object> bVar = a;
        if (bVar == null) {
            a(0);
        }
        return bVar;
    }

    private b(d<a<e<K, V>>> intMap, int size) {
        this.b = intMap;
        this.c = size;
    }

    public V c(Object key) {
        ConsPStack<MapEntry<K, V>> entries = d(key.hashCode());
        while (entries != null && entries.size() > 0) {
            MapEntry<K, V> entry = (e) entries.d;
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entries = entries.f;
        }
        return null;
    }

    @NotNull
    public b<K, V> f(K key, V value) {
        ConsPStack<MapEntry<K, V>> entries = d(key.hashCode());
        int size0 = entries.size();
        int i = e(entries, key);
        if (i != -1) {
            entries = entries.e(i);
        }
        ConsPStack<MapEntry<K, V>> entries2 = entries.g(new e(key, value));
        return new b<>(this.b.c(key.hashCode(), entries2), (this.c - size0) + entries2.size());
    }

    private a<e<K, V>> d(int hash) {
        ConsPStack<MapEntry<K, V>> entries = (a) this.b.b(hash);
        if (entries == null) {
            return a.b();
        }
        return entries;
    }

    private static <K, V> int e(a<e<K, V>> aVar, Object key) {
        int i = 0;
        a<E> aVar2 = aVar;
        while (aVar2 != null && aVar2.size() > 0) {
            if (((e) aVar2.d).key.equals(key)) {
                return i;
            }
            i++;
            aVar2 = aVar2.f;
        }
        return -1;
    }
}
