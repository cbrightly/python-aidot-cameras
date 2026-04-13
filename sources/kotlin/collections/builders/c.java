package kotlin.collections.builders;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MapBuilder.kt */
public final class c<K, V> implements Map<K, V>, kotlin.jvm.internal.markers.d {
    @NotNull
    private static final a c = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public V[] a1;
    private int[] a2;
    private int d;
    private int f;
    /* access modifiers changed from: private */
    public K[] p0;
    /* access modifiers changed from: private */
    public int[] p1;
    private int p2;
    /* access modifiers changed from: private */
    public int p3;
    private e<K> q;
    private f<V> x;
    private d<K, V> y;
    private boolean z;

    private c(K[] keysArray, V[] valuesArray, int[] presenceArray, int[] hashArray, int maxProbeDistance, int length) {
        this.p0 = keysArray;
        this.a1 = valuesArray;
        this.p1 = presenceArray;
        this.a2 = hashArray;
        this.p2 = maxProbeDistance;
        this.p3 = length;
        this.d = c.d(x());
    }

    public final /* bridge */ Set<Map.Entry<K, V>> entrySet() {
        return w();
    }

    public final /* bridge */ Set<K> keySet() {
        return y();
    }

    public final /* bridge */ int size() {
        return z();
    }

    public final /* bridge */ Collection<V> values() {
        return A();
    }

    public int z() {
        return this.f;
    }

    public c() {
        this(8);
    }

    public c(int initialCapacity) {
        this(b.a(initialCapacity), (V[]) null, new int[initialCapacity], new int[c.c(initialCapacity)], 2, 0);
    }

    @NotNull
    public final Map<K, V> k() {
        l();
        this.z = true;
        return this;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object key) {
        return t(key) >= 0;
    }

    public boolean containsValue(Object value) {
        return u(value) >= 0;
    }

    @Nullable
    public V get(Object key) {
        int index = t(key);
        if (index < 0) {
            return null;
        }
        V[] vArr = this.a1;
        k.c(vArr);
        return vArr[index];
    }

    @Nullable
    public V put(K key, V value) {
        l();
        int index = i(key);
        Object[] valuesArray = j();
        if (index < 0) {
            Object oldValue = valuesArray[(-index) - 1];
            valuesArray[(-index) - 1] = value;
            return oldValue;
        }
        valuesArray[index] = value;
        return null;
    }

    public void putAll(@NotNull Map<? extends K, ? extends V> from) {
        k.e(from, "from");
        l();
        D(from.entrySet());
    }

    @Nullable
    public V remove(Object key) {
        int index = K(key);
        if (index < 0) {
            return null;
        }
        Object[] valuesArray = this.a1;
        k.c(valuesArray);
        Object oldValue = valuesArray[index];
        b.c(valuesArray, index);
        return oldValue;
    }

    public void clear() {
        l();
        int i = this.p3 - 1;
        if (i >= 0) {
            int i2 = 0;
            while (true) {
                int[] iArr = this.p1;
                int hash = iArr[i2];
                if (hash >= 0) {
                    this.a2[hash] = 0;
                    iArr[i2] = -1;
                }
                if (i2 == i) {
                    break;
                }
                i2++;
            }
        }
        b.d(this.p0, 0, this.p3);
        V[] vArr = this.a1;
        if (vArr != null) {
            b.d(vArr, 0, this.p3);
        }
        this.f = 0;
        this.p3 = 0;
    }

    @NotNull
    public Set<K> y() {
        e cur = this.q;
        if (cur != null) {
            return cur;
        }
        e eVar = new e(this);
        this.q = eVar;
        return eVar;
    }

    @NotNull
    public Collection<V> A() {
        f cur = this.x;
        if (cur != null) {
            return cur;
        }
        f fVar = new f(this);
        this.x = fVar;
        return fVar;
    }

    @NotNull
    public Set<Map.Entry<K, V>> w() {
        d cur = this.y;
        if (cur != null) {
            return cur;
        }
        d dVar = new d(this);
        this.y = dVar;
        return dVar;
    }

    public boolean equals(@Nullable Object other) {
        return other == this || ((other instanceof Map) && p((Map) other));
    }

    public int hashCode() {
        int result = 0;
        b it = s();
        while (it.hasNext()) {
            result += it.l();
        }
        return result;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder((size() * 3) + 2);
        sb.append("{");
        int i = 0;
        b it = s();
        while (it.hasNext()) {
            if (i > 0) {
                sb.append(", ");
            }
            it.k(sb);
            i++;
        }
        sb.append("}");
        String sb2 = sb.toString();
        k.d(sb2, "sb.toString()");
        return sb2;
    }

    private final int v() {
        return this.p0.length;
    }

    private final int x() {
        return this.a2.length;
    }

    public final void l() {
        if (this.z) {
            throw new UnsupportedOperationException();
        }
    }

    private final void r(int n) {
        q(this.p3 + n);
    }

    private final void q(int capacity) {
        if (capacity > v()) {
            int newSize = (v() * 3) / 2;
            if (capacity > newSize) {
                newSize = capacity;
            }
            this.p0 = b.b(this.p0, newSize);
            V[] vArr = this.a1;
            this.a1 = vArr != null ? b.b(vArr, newSize) : null;
            int[] copyOf = Arrays.copyOf(this.p1, newSize);
            k.d(copyOf, "java.util.Arrays.copyOf(this, newSize)");
            this.p1 = copyOf;
            int newHashSize = c.c(newSize);
            if (newHashSize > x()) {
                G(newHashSize);
            }
        } else if ((this.p3 + capacity) - size() > v()) {
            G(x());
        }
    }

    /* access modifiers changed from: private */
    public final V[] j() {
        Object[] curValuesArray = this.a1;
        if (curValuesArray != null) {
            return curValuesArray;
        }
        Object[] newValuesArray = b.a(v());
        this.a1 = newValuesArray;
        return newValuesArray;
    }

    private final int B(K key) {
        return ((key != null ? key.hashCode() : 0) * -1640531527) >>> this.d;
    }

    private final void m() {
        int i;
        int i2 = 0;
        int j = 0;
        Object[] valuesArray = this.a1;
        while (true) {
            i = this.p3;
            if (i2 >= i) {
                break;
            }
            if (this.p1[i2] >= 0) {
                K[] kArr = this.p0;
                kArr[j] = kArr[i2];
                if (valuesArray != null) {
                    valuesArray[j] = valuesArray[i2];
                }
                j++;
            }
            i2++;
        }
        b.d(this.p0, j, i);
        if (valuesArray != null) {
            b.d(valuesArray, j, this.p3);
        }
        this.p3 = j;
    }

    private final void G(int newHashSize) {
        if (this.p3 > size()) {
            m();
        }
        if (newHashSize != x()) {
            this.a2 = new int[newHashSize];
            this.d = c.d(newHashSize);
        } else {
            kotlin.collections.k.j(this.a2, 0, 0, x());
        }
        int i = 0;
        while (i < this.p3) {
            int i2 = i + 1;
            if (F(i) != 0) {
                i = i2;
            } else {
                throw new IllegalStateException("This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?");
            }
        }
    }

    private final boolean F(int i) {
        int hash = B(this.p0[i]);
        int probesLeft = this.p2;
        while (true) {
            int[] iArr = this.a2;
            if (iArr[hash] == 0) {
                iArr[hash] = i + 1;
                this.p1[i] = hash;
                return true;
            }
            probesLeft--;
            if (probesLeft < 0) {
                return false;
            }
            hash = hash == 0 ? x() - 1 : hash - 1;
        }
    }

    private final int t(K key) {
        int hash = B(key);
        int probesLeft = this.p2;
        while (true) {
            int index = this.a2[hash];
            if (index == 0) {
                return -1;
            }
            if (index > 0 && k.a(this.p0[index - 1], key)) {
                return index - 1;
            }
            probesLeft--;
            if (probesLeft < 0) {
                return -1;
            }
            hash = hash == 0 ? x() - 1 : hash - 1;
        }
    }

    private final int u(V value) {
        int i = this.p3;
        while (true) {
            i--;
            if (i < 0) {
                return -1;
            }
            if (this.p1[i] >= 0) {
                V[] vArr = this.a1;
                k.c(vArr);
                if (k.a(vArr[i], value)) {
                    return i;
                }
            }
        }
    }

    public final int i(K key) {
        l();
        while (true) {
            int hash = B(key);
            int tentativeMaxProbeDistance = n.e(this.p2 * 2, x() / 2);
            int probeDistance = 0;
            while (true) {
                int index = this.a2[hash];
                if (index <= 0) {
                    if (this.p3 >= v()) {
                        r(1);
                    } else {
                        int putIndex = this.p3;
                        this.p3 = putIndex + 1;
                        this.p0[putIndex] = key;
                        this.p1[putIndex] = hash;
                        this.a2[hash] = putIndex + 1;
                        this.f = size() + 1;
                        if (probeDistance > this.p2) {
                            this.p2 = probeDistance;
                        }
                        return putIndex;
                    }
                } else if (k.a(this.p0[index - 1], key)) {
                    return -index;
                } else {
                    probeDistance++;
                    if (probeDistance > tentativeMaxProbeDistance) {
                        G(x() * 2);
                        break;
                    }
                    hash = hash == 0 ? x() - 1 : hash - 1;
                }
            }
        }
    }

    public final int K(K key) {
        l();
        int index = t(key);
        if (index < 0) {
            return -1;
        }
        L(index);
        return index;
    }

    /* access modifiers changed from: private */
    public final void L(int index) {
        b.c(this.p0, index);
        J(this.p1[index]);
        this.p1[index] = -1;
        this.f = size() - 1;
    }

    private final void J(int removedHash) {
        int hash = removedHash;
        int hole = removedHash;
        int probeDistance = 0;
        int patchAttemptsLeft = n.e(this.p2 * 2, x() / 2);
        do {
            hash = hash == 0 ? x() - 1 : hash - 1;
            probeDistance++;
            if (probeDistance > this.p2) {
                this.a2[hole] = 0;
                return;
            }
            int[] iArr = this.a2;
            int index = iArr[hash];
            if (index == 0) {
                iArr[hole] = 0;
                return;
            }
            if (index < 0) {
                iArr[hole] = -1;
                hole = hash;
                probeDistance = 0;
            } else if (((B(this.p0[index - 1]) - hash) & (x() - 1)) >= probeDistance) {
                this.a2[hole] = index;
                this.p1[index - 1] = hole;
                hole = hash;
                probeDistance = 0;
            }
            patchAttemptsLeft--;
        } while (patchAttemptsLeft >= 0);
        this.a2[hole] = -1;
    }

    public final boolean o(@NotNull Map.Entry<? extends K, ? extends V> entry) {
        k.e(entry, "entry");
        int index = t(entry.getKey());
        if (index < 0) {
            return false;
        }
        V[] vArr = this.a1;
        k.c(vArr);
        return k.a(vArr[index], entry.getValue());
    }

    private final boolean p(Map<?, ?> other) {
        return size() == other.size() && n(other.entrySet());
    }

    public final boolean n(@NotNull Collection<?> m) {
        k.e(m, "m");
        for (Object entry : m) {
            if (entry != null) {
                try {
                    if (!o((Map.Entry) entry)) {
                    }
                } catch (ClassCastException e2) {
                    return false;
                }
            }
            return false;
        }
        return true;
    }

    private final boolean E(Map.Entry<? extends K, ? extends V> entry) {
        int index = i(entry.getKey());
        Object[] valuesArray = j();
        if (index >= 0) {
            valuesArray[index] = entry.getValue();
            return true;
        }
        if (!(!k.a(entry.getValue(), valuesArray[(-index) - 1]))) {
            return false;
        }
        valuesArray[(-index) - 1] = entry.getValue();
        return true;
    }

    private final boolean D(Collection<? extends Map.Entry<? extends K, ? extends V>> from) {
        if (from.isEmpty()) {
            return false;
        }
        r(from.size());
        boolean updated = false;
        for (Map.Entry E : from) {
            if (E(E)) {
                updated = true;
            }
        }
        return updated;
    }

    public final boolean H(@NotNull Map.Entry<? extends K, ? extends V> entry) {
        k.e(entry, "entry");
        l();
        int index = t(entry.getKey());
        if (index < 0) {
            return false;
        }
        V[] vArr = this.a1;
        k.c(vArr);
        if (!k.a(vArr[index], entry.getValue())) {
            return false;
        }
        L(index);
        return true;
    }

    public final boolean M(V element) {
        l();
        int index = u(element);
        if (index < 0) {
            return false;
        }
        L(index);
        return true;
    }

    @NotNull
    public final e<K, V> C() {
        return new e<>(this);
    }

    @NotNull
    public final f<K, V> N() {
        return new f<>(this);
    }

    @NotNull
    public final b<K, V> s() {
        return new b<>(this);
    }

    /* compiled from: MapBuilder.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* access modifiers changed from: private */
        public final int c(int capacity) {
            return Integer.highestOneBit(n.b(capacity, 1) * 3);
        }

        /* access modifiers changed from: private */
        public final int d(int hashSize) {
            return Integer.numberOfLeadingZeros(hashSize) + 1;
        }
    }

    /* compiled from: MapBuilder.kt */
    public static class d<K, V> {
        private int c;
        private int d = -1;
        @NotNull
        private final c<K, V> f;

        public d(@NotNull c<K, V> map) {
            k.e(map, "map");
            this.f = map;
            g();
        }

        @NotNull
        public final c<K, V> f() {
            return this.f;
        }

        public final int b() {
            return this.c;
        }

        public final void h(int i) {
            this.c = i;
        }

        public final int e() {
            return this.d;
        }

        public final void i(int i) {
            this.d = i;
        }

        public final void g() {
            while (this.c < this.f.p3) {
                int[] f2 = this.f.p1;
                int i = this.c;
                if (f2[i] < 0) {
                    this.c = i + 1;
                } else {
                    return;
                }
            }
        }

        public final boolean hasNext() {
            return this.c < this.f.p3;
        }

        public final void remove() {
            this.f.l();
            this.f.L(this.d);
            this.d = -1;
        }
    }

    /* compiled from: MapBuilder.kt */
    public static final class e<K, V> extends d<K, V> implements Iterator<K>, kotlin.jvm.internal.markers.a {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public e(@NotNull c<K, V> map) {
            super(map);
            k.e(map, "map");
        }

        public K next() {
            if (b() < f().p3) {
                int b = b();
                h(b + 1);
                i(b);
                Object result = f().p0[e()];
                g();
                return result;
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: MapBuilder.kt */
    public static final class f<K, V> extends d<K, V> implements Iterator<V>, kotlin.jvm.internal.markers.a {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public f(@NotNull c<K, V> map) {
            super(map);
            k.e(map, "map");
        }

        public V next() {
            if (b() < f().p3) {
                int b = b();
                h(b + 1);
                i(b);
                Object[] g = f().a1;
                k.c(g);
                Object result = g[e()];
                g();
                return result;
            }
            throw new NoSuchElementException();
        }
    }

    /* compiled from: MapBuilder.kt */
    public static final class b<K, V> extends d<K, V> implements Iterator<Map.Entry<K, V>>, kotlin.jvm.internal.markers.a {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public b(@NotNull c<K, V> map) {
            super(map);
            k.e(map, "map");
        }

        @NotNull
        /* renamed from: j */
        public C0317c<K, V> next() {
            if (b() < f().p3) {
                int b = b();
                h(b + 1);
                i(b);
                C0317c result = new C0317c(f(), e());
                g();
                return result;
            }
            throw new NoSuchElementException();
        }

        public final int l() {
            if (b() < f().p3) {
                int b = b();
                h(b + 1);
                i(b);
                Object obj = f().p0[e()];
                int i = 0;
                int hashCode = obj != null ? obj.hashCode() : 0;
                Object[] g = f().a1;
                k.c(g);
                Object obj2 = g[e()];
                if (obj2 != null) {
                    i = obj2.hashCode();
                }
                int result = hashCode ^ i;
                g();
                return result;
            }
            throw new NoSuchElementException();
        }

        public final void k(@NotNull StringBuilder sb) {
            k.e(sb, "sb");
            if (b() < f().p3) {
                int b = b();
                h(b + 1);
                i(b);
                Object key = f().p0[e()];
                if (k.a(key, f())) {
                    sb.append("(this Map)");
                } else {
                    sb.append(key);
                }
                sb.append('=');
                Object[] g = f().a1;
                k.c(g);
                Object value = g[e()];
                if (k.a(value, f())) {
                    sb.append("(this Map)");
                } else {
                    sb.append(value);
                }
                g();
                return;
            }
            throw new NoSuchElementException();
        }
    }

    /* renamed from: kotlin.collections.builders.c$c  reason: collision with other inner class name */
    /* compiled from: MapBuilder.kt */
    public static final class C0317c<K, V> implements Map.Entry<K, V>, kotlin.jvm.internal.markers.a {
        private final c<K, V> c;
        private final int d;

        public C0317c(@NotNull c<K, V> map, int index) {
            k.e(map, "map");
            this.c = map;
            this.d = index;
        }

        public K getKey() {
            return this.c.p0[this.d];
        }

        public V getValue() {
            V[] g = this.c.a1;
            k.c(g);
            return g[this.d];
        }

        public V setValue(V newValue) {
            this.c.l();
            Object[] valuesArray = this.c.j();
            int i = this.d;
            Object oldValue = valuesArray[i];
            valuesArray[i] = newValue;
            return oldValue;
        }

        public boolean equals(@Nullable Object other) {
            return (other instanceof Map.Entry) && k.a(((Map.Entry) other).getKey(), getKey()) && k.a(((Map.Entry) other).getValue(), getValue());
        }

        public int hashCode() {
            Object key = getKey();
            int i = 0;
            int hashCode = key != null ? key.hashCode() : 0;
            Object value = getValue();
            if (value != null) {
                i = value.hashCode();
            }
            return hashCode ^ i;
        }

        @NotNull
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getKey());
            sb.append('=');
            sb.append(getValue());
            return sb.toString();
        }
    }
}
