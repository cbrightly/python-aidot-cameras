package kotlin.reflect.jvm.internal.pcollections;

/* compiled from: IntTreePMap */
public final class d<V> {
    private static final d<Object> a = new d<>(c.a);
    private final c<V> b;

    public static <V> d<V> a() {
        return a;
    }

    private d(c<V> root) {
        this.b = root;
    }

    private d<V> d(c<V> root) {
        if (root == this.b) {
            return this;
        }
        return new d<>(root);
    }

    public V b(int key) {
        return this.b.a((long) key);
    }

    public d<V> c(int key, V value) {
        return d(this.b.b((long) key, value));
    }
}
