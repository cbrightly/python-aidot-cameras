package com.bumptech.glide.load.engine.bitmap_recycle;

import androidx.annotation.Nullable;
import com.bumptech.glide.load.engine.bitmap_recycle.GroupedLinkedMap;
import com.bumptech.glide.load.engine.bitmap_recycle.m;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: GroupedLinkedMap */
public class h<K extends m, V> {
    private final a<K, V> a = new a<>();
    private final Map<K, a<K, V>> b = new HashMap();

    h() {
    }

    public void d(K key, V value) {
        GroupedLinkedMap.LinkedEntry<K, V> entry = (a) this.b.get(key);
        if (entry == null) {
            entry = new a<>(key);
            c(entry);
            this.b.put(key, entry);
        } else {
            key.a();
        }
        entry.a(value);
    }

    @Nullable
    public V a(K key) {
        GroupedLinkedMap.LinkedEntry<K, V> entry = (a) this.b.get(key);
        if (entry == null) {
            entry = new a<>(key);
            this.b.put(key, entry);
        } else {
            key.a();
        }
        b(entry);
        return entry.b();
    }

    @Nullable
    public V f() {
        for (GroupedLinkedMap.LinkedEntry<K, V> last = this.a.d; !last.equals(this.a); last = last.d) {
            V removed = last.b();
            if (removed != null) {
                return removed;
            }
            e(last);
            this.b.remove(last.a);
            ((m) last.a).a();
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GroupedLinkedMap( ");
        boolean hadAtLeastOneItem = false;
        for (GroupedLinkedMap.LinkedEntry<K, V> current = this.a.c; !current.equals(this.a); current = current.c) {
            hadAtLeastOneItem = true;
            sb.append('{');
            sb.append(current.a);
            sb.append(':');
            sb.append(current.c());
            sb.append("}, ");
        }
        if (hadAtLeastOneItem) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(" )");
        return sb.toString();
    }

    private void b(a<K, V> entry) {
        e(entry);
        a<K, V> aVar = this.a;
        entry.d = aVar;
        entry.c = aVar.c;
        g(entry);
    }

    private void c(a<K, V> entry) {
        e(entry);
        a<K, V> aVar = this.a;
        entry.d = aVar.d;
        entry.c = aVar;
        g(entry);
    }

    private static <K, V> void g(a<K, V> entry) {
        entry.c.d = entry;
        entry.d.c = entry;
    }

    private static <K, V> void e(a<K, V> entry) {
        a<K, V> aVar = entry.d;
        aVar.c = entry.c;
        entry.c.d = aVar;
    }

    /* compiled from: GroupedLinkedMap */
    public static class a<K, V> {
        final K a;
        private List<V> b;
        a<K, V> c;
        a<K, V> d;

        a() {
            this((Object) null);
        }

        a(K key) {
            this.d = this;
            this.c = this;
            this.a = key;
        }

        @Nullable
        public V b() {
            int valueSize = c();
            if (valueSize > 0) {
                return this.b.remove(valueSize - 1);
            }
            return null;
        }

        public int c() {
            List<V> list = this.b;
            if (list != null) {
                return list.size();
            }
            return 0;
        }

        public void a(V value) {
            if (this.b == null) {
                this.b = new ArrayList();
            }
            this.b.add(value);
        }
    }
}
