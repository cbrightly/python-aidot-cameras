package kotlin.reflect.jvm.internal.impl.protobuf;

import java.util.Iterator;
import java.util.Map;

/* compiled from: LazyField */
public class j extends k {
    private final o e;

    public o e() {
        return c(this.e);
    }

    public int hashCode() {
        return e().hashCode();
    }

    public boolean equals(Object obj) {
        return e().equals(obj);
    }

    public String toString() {
        return e().toString();
    }

    /* compiled from: LazyField */
    public static class b<K> implements Map.Entry<K, Object> {
        private Map.Entry<K, j> c;

        private b(Map.Entry<K, j> entry) {
            this.c = entry;
        }

        public K getKey() {
            return this.c.getKey();
        }

        public Object getValue() {
            j field = this.c.getValue();
            if (field == null) {
                return null;
            }
            return field.e();
        }

        public Object setValue(Object value) {
            if (value instanceof o) {
                return this.c.getValue().d((o) value);
            }
            throw new IllegalArgumentException("LazyField now only used for MessageSet, and the value of MessageSet must be an instance of MessageLite");
        }
    }

    /* compiled from: LazyField */
    public static class c<K> implements Iterator<Map.Entry<K, Object>> {
        private Iterator<Map.Entry<K, Object>> c;

        public c(Iterator<Map.Entry<K, Object>> iterator) {
            this.c = iterator;
        }

        public boolean hasNext() {
            return this.c.hasNext();
        }

        /* renamed from: b */
        public Map.Entry<K, Object> next() {
            Map.Entry<K, ?> entry = this.c.next();
            if (entry.getValue() instanceof j) {
                return new b(entry);
            }
            return entry;
        }

        public void remove() {
            this.c.remove();
        }
    }
}
