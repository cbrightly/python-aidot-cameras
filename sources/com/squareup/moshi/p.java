package com.squareup.moshi;

import com.squareup.moshi.LinkedHashTreeMap;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: LinkedHashTreeMap */
public final class p<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Comparator<Comparable> c = new a();
    Comparator<? super K> comparator;
    private p<K, V>.d entrySet;
    final g<K, V> header;
    private p<K, V>.e keySet;
    int modCount;
    int size;
    g<K, V>[] table;
    int threshold;

    static {
        Class<p> cls = p.class;
    }

    /* compiled from: LinkedHashTreeMap */
    public class a implements Comparator<Comparable> {
        a() {
        }

        /* renamed from: a */
        public int compare(Comparable a, Comparable b) {
            return a.compareTo(b);
        }
    }

    p() {
        this((Comparator) null);
    }

    p(Comparator<? super K> comparator2) {
        this.size = 0;
        this.modCount = 0;
        this.comparator = comparator2 != null ? comparator2 : c;
        this.header = new g<>();
        g<K, V>[] gVarArr = new g[16];
        this.table = gVarArr;
        this.threshold = (gVarArr.length / 2) + (gVarArr.length / 4);
    }

    public int size() {
        return this.size;
    }

    public V get(Object key) {
        LinkedHashTreeMap.Node<K, V> node = findByObject(key);
        if (node != null) {
            return node.p0;
        }
        return null;
    }

    public boolean containsKey(Object key) {
        return findByObject(key) != null;
    }

    public V put(K key, V value) {
        if (key != null) {
            LinkedHashTreeMap.Node<K, V> created = find(key, true);
            V result = created.p0;
            created.p0 = value;
            return result;
        }
        throw new NullPointerException("key == null");
    }

    public void clear() {
        Arrays.fill(this.table, (Object) null);
        this.size = 0;
        this.modCount++;
        LinkedHashTreeMap.Node<K, V> header2 = this.header;
        LinkedHashTreeMap.Node<K, V> e2 = header2.q;
        while (e2 != header2) {
            LinkedHashTreeMap.Node<K, V> next = e2.q;
            e2.x = null;
            e2.q = null;
            e2 = next;
        }
        header2.x = header2;
        header2.q = header2;
    }

    public V remove(Object key) {
        LinkedHashTreeMap.Node<K, V> node = removeInternalByKey(key);
        if (node != null) {
            return node.p0;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public g<K, V> find(K key, boolean create) {
        int comparison;
        LinkedHashTreeMap.Node<K, V> nearest;
        LinkedHashTreeMap.Node<K, V> created;
        int i;
        Comparable<Object> comparable = key;
        Comparator<? super K> comparator2 = this.comparator;
        LinkedHashTreeMap.Node<K, V>[] table2 = this.table;
        int hash = h(key.hashCode());
        int index = hash & (table2.length - 1);
        LinkedHashTreeMap.Node<K, V> nearest2 = table2[index];
        if (nearest2 != null) {
            Comparable<Object> comparableKey = comparator2 == c ? comparable : null;
            while (true) {
                if (comparableKey != null) {
                    i = comparableKey.compareTo(nearest2.y);
                } else {
                    i = comparator2.compare(comparable, nearest2.y);
                }
                int comparison2 = i;
                if (comparison2 == 0) {
                    return nearest2;
                }
                LinkedHashTreeMap.Node<K, V> child = comparison2 < 0 ? nearest2.d : nearest2.f;
                if (child == null) {
                    nearest = nearest2;
                    comparison = comparison2;
                    break;
                }
                nearest2 = child;
            }
        } else {
            nearest = nearest2;
            comparison = 0;
        }
        if (!create) {
            return null;
        }
        LinkedHashTreeMap.Node<K, V> header2 = this.header;
        if (nearest != null) {
            created = new g<>(nearest, key, hash, header2, header2.x);
            if (comparison < 0) {
                nearest.d = created;
            } else {
                nearest.f = created;
            }
            c(nearest, true);
        } else if (comparator2 != c || (comparable instanceof Comparable)) {
            created = new g<>(nearest, key, hash, header2, header2.x);
            table2[index] = created;
        } else {
            throw new ClassCastException(key.getClass().getName() + " is not Comparable");
        }
        int i2 = this.size;
        this.size = i2 + 1;
        if (i2 > this.threshold) {
            a();
        }
        this.modCount++;
        return created;
    }

    /* access modifiers changed from: package-private */
    public g<K, V> findByObject(Object key) {
        if (key == null) {
            return null;
        }
        try {
            return find(key, false);
        } catch (ClassCastException e2) {
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public g<K, V> findByEntry(Map.Entry<?, ?> entry) {
        LinkedHashTreeMap.Node<K, V> mine = findByObject(entry.getKey());
        if (mine != null && b(mine.p0, entry.getValue())) {
            return mine;
        }
        return null;
    }

    private boolean b(Object a2, Object b2) {
        return a2 == b2 || (a2 != null && a2.equals(b2));
    }

    private static int h(int h) {
        int h2 = h ^ ((h >>> 20) ^ (h >>> 12));
        return ((h2 >>> 7) ^ h2) ^ (h2 >>> 4);
    }

    /* access modifiers changed from: package-private */
    public void removeInternal(g<K, V> node, boolean unlink) {
        if (unlink) {
            g<K, V> gVar = node.x;
            gVar.q = node.q;
            node.q.x = gVar;
            node.x = null;
            node.q = null;
        }
        LinkedHashTreeMap.Node<K, V> left = node.d;
        LinkedHashTreeMap.Node<K, V> right = node.f;
        LinkedHashTreeMap.Node<K, V> originalParent = node.c;
        if (left == null || right == null) {
            if (left != null) {
                e(node, left);
                node.d = null;
            } else if (right != null) {
                e(node, right);
                node.f = null;
            } else {
                e(node, (g<K, V>) null);
            }
            c(originalParent, false);
            this.size--;
            this.modCount++;
            return;
        }
        LinkedHashTreeMap.Node<K, V> adjacent = left.a1 > right.a1 ? left.b() : right.a();
        removeInternal(adjacent, false);
        int leftHeight = 0;
        LinkedHashTreeMap.Node<K, V> left2 = node.d;
        if (left2 != null) {
            leftHeight = left2.a1;
            adjacent.d = left2;
            left2.c = adjacent;
            node.d = null;
        }
        int rightHeight = 0;
        LinkedHashTreeMap.Node<K, V> right2 = node.f;
        if (right2 != null) {
            rightHeight = right2.a1;
            adjacent.f = right2;
            right2.c = adjacent;
            node.f = null;
        }
        adjacent.a1 = Math.max(leftHeight, rightHeight) + 1;
        e(node, adjacent);
    }

    /* access modifiers changed from: package-private */
    public g<K, V> removeInternalByKey(Object key) {
        LinkedHashTreeMap.Node<K, V> node = findByObject(key);
        if (node != null) {
            removeInternal(node, true);
        }
        return node;
    }

    private void e(g<K, V> node, g<K, V> replacement) {
        LinkedHashTreeMap.Node<K, V> parent = node.c;
        node.c = null;
        if (replacement != null) {
            replacement.c = parent;
        }
        if (parent == null) {
            int i = node.z;
            g<K, V>[] gVarArr = this.table;
            gVarArr[i & (gVarArr.length - 1)] = replacement;
        } else if (parent.d == node) {
            parent.d = replacement;
        } else if (parent.f == node) {
            parent.f = replacement;
        } else {
            throw new AssertionError();
        }
    }

    private void c(g<K, V> unbalanced, boolean insert) {
        for (g<K, V> gVar = unbalanced; gVar != null; gVar = gVar.c) {
            LinkedHashTreeMap.Node<K, V> left = gVar.d;
            LinkedHashTreeMap.Node<K, V> right = gVar.f;
            int rightLeftHeight = 0;
            int leftHeight = left != null ? left.a1 : 0;
            int rightHeight = right != null ? right.a1 : 0;
            int delta = leftHeight - rightHeight;
            if (delta == -2) {
                LinkedHashTreeMap.Node<K, V> rightLeft = right.d;
                LinkedHashTreeMap.Node<K, V> rightRight = right.f;
                int rightRightHeight = rightRight != null ? rightRight.a1 : 0;
                if (rightLeft != null) {
                    rightLeftHeight = rightLeft.a1;
                }
                int rightDelta = rightLeftHeight - rightRightHeight;
                if (rightDelta == -1 || (rightDelta == 0 && !insert)) {
                    f(gVar);
                } else if (rightDelta == 1) {
                    g(right);
                    f(gVar);
                } else {
                    throw new AssertionError();
                }
                if (insert) {
                    return;
                }
            } else if (delta == 2) {
                LinkedHashTreeMap.Node<K, V> leftLeft = left.d;
                LinkedHashTreeMap.Node<K, V> leftRight = left.f;
                int leftRightHeight = leftRight != null ? leftRight.a1 : 0;
                if (leftLeft != null) {
                    rightLeftHeight = leftLeft.a1;
                }
                int leftDelta = rightLeftHeight - leftRightHeight;
                if (leftDelta == 1 || (leftDelta == 0 && !insert)) {
                    g(gVar);
                } else if (leftDelta == -1) {
                    f(left);
                    g(gVar);
                } else {
                    throw new AssertionError();
                }
                if (insert) {
                    return;
                }
            } else if (delta == 0) {
                gVar.a1 = leftHeight + 1;
                if (insert) {
                    return;
                }
            } else if (delta == -1 || delta == 1) {
                gVar.a1 = Math.max(leftHeight, rightHeight) + 1;
                if (!insert) {
                    return;
                }
            } else {
                throw new AssertionError();
            }
        }
    }

    private void f(g<K, V> root) {
        LinkedHashTreeMap.Node<K, V> left = root.d;
        LinkedHashTreeMap.Node<K, V> pivot = root.f;
        LinkedHashTreeMap.Node<K, V> pivotLeft = pivot.d;
        LinkedHashTreeMap.Node<K, V> pivotRight = pivot.f;
        root.f = pivotLeft;
        if (pivotLeft != null) {
            pivotLeft.c = root;
        }
        e(root, pivot);
        pivot.d = root;
        root.c = pivot;
        int i = 0;
        int max = Math.max(left != null ? left.a1 : 0, pivotLeft != null ? pivotLeft.a1 : 0) + 1;
        root.a1 = max;
        if (pivotRight != null) {
            i = pivotRight.a1;
        }
        pivot.a1 = Math.max(max, i) + 1;
    }

    private void g(g<K, V> root) {
        LinkedHashTreeMap.Node<K, V> pivot = root.d;
        LinkedHashTreeMap.Node<K, V> right = root.f;
        LinkedHashTreeMap.Node<K, V> pivotLeft = pivot.d;
        LinkedHashTreeMap.Node<K, V> pivotRight = pivot.f;
        root.d = pivotRight;
        if (pivotRight != null) {
            pivotRight.c = root;
        }
        e(root, pivot);
        pivot.f = root;
        root.c = pivot;
        int i = 0;
        int max = Math.max(right != null ? right.a1 : 0, pivotRight != null ? pivotRight.a1 : 0) + 1;
        root.a1 = max;
        if (pivotLeft != null) {
            i = pivotLeft.a1;
        }
        pivot.a1 = Math.max(max, i) + 1;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        LinkedHashTreeMap<K, V>.EntrySet result = this.entrySet;
        if (result != null) {
            return result;
        }
        p<K, V>.d dVar = new d();
        this.entrySet = dVar;
        return dVar;
    }

    public Set<K> keySet() {
        LinkedHashTreeMap<K, V>.KeySet result = this.keySet;
        if (result != null) {
            return result;
        }
        p<K, V>.e eVar = new e();
        this.keySet = eVar;
        return eVar;
    }

    /* compiled from: LinkedHashTreeMap */
    public static final class g<K, V> implements Map.Entry<K, V> {
        int a1;
        g<K, V> c;
        g<K, V> d;
        g<K, V> f;
        V p0;
        g<K, V> q;
        g<K, V> x;
        final K y;
        final int z;

        g() {
            this.y = null;
            this.z = -1;
            this.x = this;
            this.q = this;
        }

        g(g<K, V> parent, K key, int hash, g<K, V> next, g<K, V> prev) {
            this.c = parent;
            this.y = key;
            this.z = hash;
            this.a1 = 1;
            this.q = next;
            this.x = prev;
            prev.q = this;
            next.x = this;
        }

        public K getKey() {
            return this.y;
        }

        public V getValue() {
            return this.p0;
        }

        public V setValue(V value) {
            V oldValue = this.p0;
            this.p0 = value;
            return oldValue;
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0032 A[ORIG_RETURN, RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean equals(java.lang.Object r5) {
            /*
                r4 = this;
                boolean r0 = r5 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 == 0) goto L_0x0036
                r0 = r5
                java.util.Map$Entry r0 = (java.util.Map.Entry) r0
                K r2 = r4.y
                if (r2 != 0) goto L_0x0013
                java.lang.Object r2 = r0.getKey()
                if (r2 != 0) goto L_0x0034
                goto L_0x001d
            L_0x0013:
                java.lang.Object r3 = r0.getKey()
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x0034
            L_0x001d:
                V r2 = r4.p0
                if (r2 != 0) goto L_0x0028
                java.lang.Object r2 = r0.getValue()
                if (r2 != 0) goto L_0x0034
                goto L_0x0032
            L_0x0028:
                java.lang.Object r3 = r0.getValue()
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x0034
            L_0x0032:
                r1 = 1
                goto L_0x0035
            L_0x0034:
            L_0x0035:
                return r1
            L_0x0036:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.squareup.moshi.p.g.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            K k = this.y;
            int i = 0;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.p0;
            if (v != null) {
                i = v.hashCode();
            }
            return hashCode ^ i;
        }

        public String toString() {
            return this.y + "=" + this.p0;
        }

        public g<K, V> a() {
            g<K, V> gVar = this;
            g<K, V> gVar2 = gVar.d;
            while (gVar2 != null) {
                gVar = gVar2;
                gVar2 = gVar.d;
            }
            return gVar;
        }

        public g<K, V> b() {
            g<K, V> gVar = this;
            g<K, V> gVar2 = gVar.f;
            while (gVar2 != null) {
                gVar = gVar2;
                gVar2 = gVar.f;
            }
            return gVar;
        }
    }

    private void a() {
        g<K, V>[] doubleCapacity = doubleCapacity(this.table);
        this.table = doubleCapacity;
        this.threshold = (doubleCapacity.length / 2) + (doubleCapacity.length / 4);
    }

    static <K, V> g<K, V>[] doubleCapacity(g<K, V>[] oldTable) {
        int oldCapacity = oldTable.length;
        g<K, V>[] gVarArr = new g[(oldCapacity * 2)];
        LinkedHashTreeMap.AvlIterator<K, V> iterator = new c<>();
        LinkedHashTreeMap.AvlBuilder<K, V> leftBuilder = new b<>();
        LinkedHashTreeMap.AvlBuilder<K, V> rightBuilder = new b<>();
        for (int i = 0; i < oldCapacity; i++) {
            LinkedHashTreeMap.Node<K, V> root = oldTable[i];
            if (root != null) {
                iterator.b(root);
                int leftSize = 0;
                int rightSize = 0;
                while (true) {
                    LinkedHashTreeMap.Node<K, V> a2 = iterator.a();
                    LinkedHashTreeMap.Node<K, V> node = a2;
                    if (a2 == null) {
                        break;
                    } else if ((node.z & oldCapacity) == 0) {
                        leftSize++;
                    } else {
                        rightSize++;
                    }
                }
                leftBuilder.b(leftSize);
                rightBuilder.b(rightSize);
                iterator.b(root);
                while (true) {
                    LinkedHashTreeMap.Node<K, V> a3 = iterator.a();
                    LinkedHashTreeMap.Node<K, V> node2 = a3;
                    if (a3 == null) {
                        break;
                    } else if ((node2.z & oldCapacity) == 0) {
                        leftBuilder.a(node2);
                    } else {
                        rightBuilder.a(node2);
                    }
                }
                g<K, V> gVar = null;
                gVarArr[i] = leftSize > 0 ? leftBuilder.c() : null;
                int i2 = i + oldCapacity;
                if (rightSize > 0) {
                    gVar = rightBuilder.c();
                }
                gVarArr[i2] = gVar;
            }
        }
        return gVarArr;
    }

    /* compiled from: LinkedHashTreeMap */
    public static class c<K, V> {
        private g<K, V> a;

        c() {
        }

        /* access modifiers changed from: package-private */
        public void b(g<K, V> root) {
            g<K, V> gVar = null;
            for (g<K, V> gVar2 = root; gVar2 != null; gVar2 = gVar2.d) {
                gVar2.c = gVar;
                gVar = gVar2;
            }
            this.a = gVar;
        }

        public g<K, V> a() {
            LinkedHashTreeMap.Node<K, V> stackTop = this.a;
            if (stackTop == null) {
                return null;
            }
            LinkedHashTreeMap.Node<K, V> result = stackTop;
            LinkedHashTreeMap.Node<K, V> stackTop2 = result.c;
            result.c = null;
            for (LinkedHashTreeMap.Node<K, V> n = result.f; n != null; n = n.d) {
                n.c = stackTop2;
                stackTop2 = n;
            }
            this.a = stackTop2;
            return result;
        }
    }

    /* compiled from: LinkedHashTreeMap */
    public static final class b<K, V> {
        private g<K, V> a;
        private int b;
        private int c;
        private int d;

        b() {
        }

        /* access modifiers changed from: package-private */
        public void b(int targetSize) {
            this.b = ((Integer.highestOneBit(targetSize) * 2) - 1) - targetSize;
            this.d = 0;
            this.c = 0;
            this.a = null;
        }

        /* access modifiers changed from: package-private */
        public void a(g<K, V> node) {
            node.f = null;
            node.c = null;
            node.d = null;
            node.a1 = 1;
            int i = this.b;
            if (i > 0) {
                int i2 = this.d;
                if ((i2 & 1) == 0) {
                    this.d = i2 + 1;
                    this.b = i - 1;
                    this.c++;
                }
            }
            node.c = this.a;
            this.a = node;
            int i3 = this.d + 1;
            this.d = i3;
            int i4 = this.b;
            if (i4 > 0 && (i3 & 1) == 0) {
                this.d = i3 + 1;
                this.b = i4 - 1;
                this.c++;
            }
            for (int scale = 4; (this.d & (scale - 1)) == scale - 1; scale *= 2) {
                int i5 = this.c;
                if (i5 == 0) {
                    LinkedHashTreeMap.Node<K, V> right = this.a;
                    LinkedHashTreeMap.Node<K, V> center = right.c;
                    LinkedHashTreeMap.Node<K, V> left = center.c;
                    center.c = left.c;
                    this.a = center;
                    center.d = left;
                    center.f = right;
                    center.a1 = right.a1 + 1;
                    left.c = center;
                    right.c = center;
                } else if (i5 == 1) {
                    LinkedHashTreeMap.Node<K, V> right2 = this.a;
                    LinkedHashTreeMap.Node<K, V> center2 = right2.c;
                    this.a = center2;
                    center2.f = right2;
                    center2.a1 = right2.a1 + 1;
                    right2.c = center2;
                    this.c = 0;
                } else if (i5 == 2) {
                    this.c = 0;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public g<K, V> c() {
            LinkedHashTreeMap.Node<K, V> stackTop = this.a;
            if (stackTop.c == null) {
                return stackTop;
            }
            throw new IllegalStateException();
        }
    }

    /* compiled from: LinkedHashTreeMap */
    public abstract class f<T> implements Iterator<T> {
        g<K, V> c;
        g<K, V> d = null;
        int f;

        f() {
            this.c = p.this.header.q;
            this.f = p.this.modCount;
        }

        public final boolean hasNext() {
            return this.c != p.this.header;
        }

        /* access modifiers changed from: package-private */
        public final g<K, V> b() {
            LinkedHashTreeMap.Node<K, V> e = this.c;
            p pVar = p.this;
            if (e == pVar.header) {
                throw new NoSuchElementException();
            } else if (pVar.modCount == this.f) {
                this.c = e.q;
                this.d = e;
                return e;
            } else {
                throw new ConcurrentModificationException();
            }
        }

        public final void remove() {
            g<K, V> gVar = this.d;
            if (gVar != null) {
                p.this.removeInternal(gVar, true);
                this.d = null;
                this.f = p.this.modCount;
                return;
            }
            throw new IllegalStateException();
        }
    }

    /* compiled from: LinkedHashTreeMap */
    public final class d extends AbstractSet<Map.Entry<K, V>> {
        d() {
        }

        public int size() {
            return p.this.size;
        }

        /* compiled from: LinkedHashTreeMap */
        public class a extends p<K, V>.f<Map.Entry<K, V>> {
            a() {
                super();
            }

            /* renamed from: c */
            public Map.Entry<K, V> next() {
                return b();
            }
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return new a();
        }

        public boolean contains(Object o) {
            return (o instanceof Map.Entry) && p.this.findByEntry((Map.Entry) o) != null;
        }

        public boolean remove(Object o) {
            LinkedHashTreeMap.Node<K, V> node;
            if (!(o instanceof Map.Entry) || (node = p.this.findByEntry((Map.Entry) o)) == null) {
                return false;
            }
            p.this.removeInternal(node, true);
            return true;
        }

        public void clear() {
            p.this.clear();
        }
    }

    /* compiled from: LinkedHashTreeMap */
    public final class e extends AbstractSet<K> {
        e() {
        }

        public int size() {
            return p.this.size;
        }

        /* compiled from: LinkedHashTreeMap */
        public class a extends p<K, V>.f<K> {
            a() {
                super();
            }

            public K next() {
                return b().y;
            }
        }

        public Iterator<K> iterator() {
            return new a();
        }

        public boolean contains(Object o) {
            return p.this.containsKey(o);
        }

        public boolean remove(Object key) {
            return p.this.removeInternalByKey(key) != null;
        }

        public void clear() {
            p.this.clear();
        }
    }

    private Object writeReplace() {
        return new LinkedHashMap(this);
    }
}
