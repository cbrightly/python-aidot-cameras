package kotlin.reflect.jvm.internal.impl.protobuf;

import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import kotlin.reflect.jvm.internal.impl.protobuf.g;

/* compiled from: SmallSortedMap */
public class t<K extends Comparable<K>, V> extends AbstractMap<K, V> {
    private final int c;
    /* access modifiers changed from: private */
    public List<t<K, V>.c> d;
    /* access modifiers changed from: private */
    public Map<K, V> f;
    private boolean q;
    private volatile t<K, V>.e x;

    /* synthetic */ t(int x0, a x1) {
        this(x0);
    }

    /* compiled from: SmallSortedMap */
    public static final class a extends t<FieldDescriptorType, Object> {
        a(int x0) {
            super(x0, (a) null);
        }

        public /* bridge */ /* synthetic */ Object put(Object x0, Object x1) {
            return t.super.p((g.b) x0, x1);
        }

        public void n() {
            if (!m()) {
                for (int i = 0; i < j(); i++) {
                    Map.Entry<FieldDescriptorType, Object> entry = i(i);
                    if (((g.b) entry.getKey()).r()) {
                        entry.setValue(Collections.unmodifiableList((List) entry.getValue()));
                    }
                }
                for (Map.Entry<FieldDescriptorType, Object> entry2 : k()) {
                    if (((g.b) entry2.getKey()).r()) {
                        entry2.setValue(Collections.unmodifiableList((List) entry2.getValue()));
                    }
                }
            }
            t.super.n();
        }
    }

    static <FieldDescriptorType extends g.b<FieldDescriptorType>> t<FieldDescriptorType, Object> o(int arraySize) {
        return new a(arraySize);
    }

    private t(int arraySize) {
        this.c = arraySize;
        this.d = Collections.emptyList();
        this.f = Collections.emptyMap();
    }

    public void n() {
        if (!this.q) {
            this.f = this.f.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(this.f);
            this.q = true;
        }
    }

    public boolean m() {
        return this.q;
    }

    public int j() {
        return this.d.size();
    }

    public Map.Entry<K, V> i(int index) {
        return this.d.get(index);
    }

    public Iterable<Map.Entry<K, V>> k() {
        return this.f.isEmpty() ? b.b() : this.f.entrySet();
    }

    public int size() {
        return this.d.size() + this.f.size();
    }

    public boolean containsKey(Object o) {
        K key = (Comparable) o;
        return f(key) >= 0 || this.f.containsKey(key);
    }

    public V get(Object o) {
        K key = (Comparable) o;
        int index = f(key);
        if (index >= 0) {
            return this.d.get(index).getValue();
        }
        return this.f.get(key);
    }

    public V p(K key, V value) {
        g();
        int index = f(key);
        if (index >= 0) {
            return this.d.get(index).setValue(value);
        }
        h();
        int insertionPoint = -(index + 1);
        if (insertionPoint >= this.c) {
            return l().put(key, value);
        }
        int size = this.d.size();
        int i = this.c;
        if (size == i) {
            SmallSortedMap<K, V>.Entry lastEntryInArray = (c) this.d.remove(i - 1);
            l().put(lastEntryInArray.getKey(), lastEntryInArray.getValue());
        }
        this.d.add(insertionPoint, new c(key, value));
        return null;
    }

    public void clear() {
        g();
        if (!this.d.isEmpty()) {
            this.d.clear();
        }
        if (!this.f.isEmpty()) {
            this.f.clear();
        }
    }

    public V remove(Object o) {
        g();
        K key = (Comparable) o;
        int index = f(key);
        if (index >= 0) {
            return q(index);
        }
        if (this.f.isEmpty()) {
            return null;
        }
        return this.f.remove(key);
    }

    /* access modifiers changed from: private */
    public V q(int index) {
        g();
        V removed = this.d.remove(index).getValue();
        if (!this.f.isEmpty()) {
            Iterator<Map.Entry<K, V>> iterator = l().entrySet().iterator();
            this.d.add(new c(this, iterator.next()));
            iterator.remove();
        }
        return removed;
    }

    private int f(K key) {
        int left = 0;
        int right = this.d.size() - 1;
        if (right >= 0) {
            int cmp = key.compareTo(this.d.get(right).getKey());
            if (cmp > 0) {
                return -(right + 2);
            }
            if (cmp == 0) {
                return right;
            }
        }
        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp2 = key.compareTo(this.d.get(mid).getKey());
            if (cmp2 < 0) {
                right = mid - 1;
            } else if (cmp2 <= 0) {
                return mid;
            } else {
                left = mid + 1;
            }
        }
        return -(left + 1);
    }

    public Set<Map.Entry<K, V>> entrySet() {
        if (this.x == null) {
            this.x = new e(this, (a) null);
        }
        return this.x;
    }

    /* access modifiers changed from: private */
    public void g() {
        if (this.q) {
            throw new UnsupportedOperationException();
        }
    }

    private SortedMap<K, V> l() {
        g();
        if (this.f.isEmpty() && !(this.f instanceof TreeMap)) {
            this.f = new TreeMap();
        }
        return (SortedMap) this.f;
    }

    private void h() {
        g();
        if (this.d.isEmpty() && !(this.d instanceof ArrayList)) {
            this.d = new ArrayList(this.c);
        }
    }

    /* compiled from: SmallSortedMap */
    public class c implements Comparable<t<K, V>.c>, Map.Entry<K, V> {
        private final K c;
        private V d;

        c(t tVar, Map.Entry<K, V> copy) {
            this((Comparable) copy.getKey(), copy.getValue());
        }

        c(K key, V value) {
            this.c = key;
            this.d = value;
        }

        /* renamed from: c */
        public K getKey() {
            return this.c;
        }

        public V getValue() {
            return this.d;
        }

        /* renamed from: a */
        public int compareTo(t<K, V>.c other) {
            return getKey().compareTo(other.getKey());
        }

        public V setValue(V newValue) {
            t.this.g();
            V oldValue = this.d;
            this.d = newValue;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> other = (Map.Entry) o;
            if (!b(this.c, other.getKey()) || !b(this.d, other.getValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            K k = this.c;
            int i = 0;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.d;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            String valueOf = String.valueOf(String.valueOf(this.c));
            String valueOf2 = String.valueOf(String.valueOf(this.d));
            StringBuilder sb = new StringBuilder(valueOf.length() + 1 + valueOf2.length());
            sb.append(valueOf);
            sb.append("=");
            sb.append(valueOf2);
            return sb.toString();
        }

        private boolean b(Object o1, Object o2) {
            if (o1 == null) {
                return o2 == null;
            }
            return o1.equals(o2);
        }
    }

    /* compiled from: SmallSortedMap */
    public class e extends AbstractSet<Map.Entry<K, V>> {
        private e() {
        }

        /* synthetic */ e(t x0, a x1) {
            this();
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new d(t.this, (a) null);
        }

        public int size() {
            return t.this.size();
        }

        public boolean contains(Object o) {
            Map.Entry<K, V> entry = (Map.Entry) o;
            V existing = t.this.get(entry.getKey());
            V value = entry.getValue();
            return existing == value || (existing != null && existing.equals(value));
        }

        /* renamed from: a */
        public boolean add(Map.Entry<K, V> entry) {
            if (contains(entry)) {
                return false;
            }
            t.this.p((Comparable) entry.getKey(), entry.getValue());
            return true;
        }

        public boolean remove(Object o) {
            Map.Entry<K, V> entry = (Map.Entry) o;
            if (!contains(entry)) {
                return false;
            }
            t.this.remove(entry.getKey());
            return true;
        }

        public void clear() {
            t.this.clear();
        }
    }

    /* compiled from: SmallSortedMap */
    public class d implements Iterator<Map.Entry<K, V>> {
        private int c;
        private boolean d;
        private Iterator<Map.Entry<K, V>> f;

        private d() {
            this.c = -1;
        }

        /* synthetic */ d(t x0, a x1) {
            this();
        }

        public boolean hasNext() {
            return this.c + 1 < t.this.d.size() || b().hasNext();
        }

        /* renamed from: c */
        public Map.Entry<K, V> next() {
            this.d = true;
            int i = this.c + 1;
            this.c = i;
            if (i < t.this.d.size()) {
                return (Map.Entry) t.this.d.get(this.c);
            }
            return (Map.Entry) b().next();
        }

        public void remove() {
            if (this.d) {
                this.d = false;
                t.this.g();
                if (this.c < t.this.d.size()) {
                    t tVar = t.this;
                    int i = this.c;
                    this.c = i - 1;
                    Object unused = tVar.q(i);
                    return;
                }
                b().remove();
                return;
            }
            throw new IllegalStateException("remove() was called before next()");
        }

        private Iterator<Map.Entry<K, V>> b() {
            if (this.f == null) {
                this.f = t.this.f.entrySet().iterator();
            }
            return this.f;
        }
    }

    /* compiled from: SmallSortedMap */
    public static class b {
        /* access modifiers changed from: private */
        public static final Iterator<Object> a = new a();
        private static final Iterable<Object> b = new C0400b();

        /* compiled from: SmallSortedMap */
        public static final class a implements Iterator<Object> {
            a() {
            }

            public boolean hasNext() {
                return false;
            }

            public Object next() {
                throw new NoSuchElementException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.protobuf.t$b$b  reason: collision with other inner class name */
        /* compiled from: SmallSortedMap */
        public static final class C0400b implements Iterable<Object> {
            C0400b() {
            }

            public Iterator<Object> iterator() {
                return b.a;
            }
        }

        static <T> Iterable<T> b() {
            return b;
        }
    }
}
