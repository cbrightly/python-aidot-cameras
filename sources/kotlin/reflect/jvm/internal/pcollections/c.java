package kotlin.reflect.jvm.internal.pcollections;

/* compiled from: IntTree */
public final class c<V> {
    static final c<Object> a = new c<>();
    private final long b;
    private final V c;
    private final c<V> d;
    private final c<V> e;
    private final int f;

    private c() {
        this.f = 0;
        this.b = 0;
        this.c = null;
        this.d = null;
        this.e = null;
    }

    private c(long key, V value, c<V> left, c<V> right) {
        this.b = key;
        this.c = value;
        this.d = left;
        this.e = right;
        this.f = left.f + 1 + right.f;
    }

    private c<V> e(long newKey) {
        if (this.f == 0 || newKey == this.b) {
            return this;
        }
        return new c(newKey, this.c, this.d, this.e);
    }

    /* access modifiers changed from: package-private */
    public V a(long key) {
        if (this.f == 0) {
            return null;
        }
        long j = this.b;
        if (key < j) {
            return this.d.a(key - j);
        }
        if (key > j) {
            return this.e.a(key - j);
        }
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public c<V> b(long key, V value) {
        if (this.f == 0) {
            return new c(key, value, this, this);
        }
        long j = this.b;
        if (key < j) {
            return d(this.d.b(key - j, value), this.e);
        }
        if (key > j) {
            return d(this.d, this.e.b(key - j, value));
        }
        if (value == this.c) {
            return this;
        }
        return new c(key, value, this.d, this.e);
    }

    private c<V> d(c<V> newLeft, c<V> newRight) {
        if (newLeft == this.d && newRight == this.e) {
            return this;
        }
        return c(this.b, this.c, newLeft, newRight);
    }

    private static <V> c<V> c(long key, V value, c<V> left, c<V> right) {
        c<V> cVar = left;
        c<V> cVar2 = right;
        int i = cVar.f;
        int i2 = cVar2.f;
        if (i + i2 > 1) {
            if (i >= i2 * 5) {
                IntTree<V> ll = cVar.d;
                IntTree<V> lr = cVar.e;
                if (lr.f < ll.f * 2) {
                    long j = cVar.b;
                    return new c(j + key, cVar.c, ll, new c(-j, value, lr.e(lr.b + j), right));
                }
                c<V> cVar3 = lr.d;
                c<V> cVar4 = lr.e;
                long j2 = lr.b;
                long j3 = cVar.b + j2 + key;
                V v = lr.c;
                c cVar5 = new c(-j2, cVar.c, ll, cVar3.e(cVar3.b + j2));
                long j4 = cVar.b;
                long j5 = lr.b;
                c<V> cVar6 = cVar4;
                c<V> cVar7 = cVar3;
                return new c(j3, v, cVar5, new c((-j4) - j5, value, cVar4.e(cVar4.b + j5 + j4), right));
            } else if (i2 >= i * 5) {
                IntTree<V> rl = cVar2.d;
                IntTree<V> rr = cVar2.e;
                if (rl.f < rr.f * 2) {
                    long j6 = cVar2.b;
                    return new c(j6 + key, cVar2.c, new c(-j6, value, left, rl.e(rl.b + j6)), rr);
                }
                IntTree<V> rll = rl.d;
                IntTree<V> rlr = rl.e;
                long j7 = rl.b;
                long j8 = cVar2.b;
                V v2 = rl.c;
                long j9 = j7 + j8 + key;
                c cVar8 = new c((-j8) - j7, value, left, rll.e(rll.b + j7 + j8));
                long j10 = rl.b;
                return new c(j9, v2, cVar8, new c(-j10, cVar2.c, rlr.e(rlr.b + j10), rr));
            }
        }
        return new c(key, value, left, right);
    }
}
