package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.f;
import io.reactivex.internal.util.j;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

/* compiled from: SpscLinkedArrayQueue */
public final class c<T> implements f<T> {
    static final int c = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
    private static final Object d = new Object();
    AtomicReferenceArray<Object> a1;
    final AtomicLong f = new AtomicLong();
    final int p0;
    final AtomicLong p1 = new AtomicLong();
    int q;
    long x;
    final int y;
    AtomicReferenceArray<Object> z;

    public c(int bufferSize) {
        int p2capacity = j.a(Math.max(8, bufferSize));
        int mask = p2capacity - 1;
        AtomicReferenceArray<Object> buffer = new AtomicReferenceArray<>(p2capacity + 1);
        this.z = buffer;
        this.y = mask;
        a(p2capacity);
        this.a1 = buffer;
        this.p0 = mask;
        this.x = (long) (mask - 1);
        o(0);
    }

    public boolean offer(T e) {
        if (e != null) {
            AtomicReferenceArray<Object> buffer = this.z;
            long index = e();
            int mask = this.y;
            int offset = c(index, mask);
            if (index < this.x) {
                return p(buffer, e, index, offset);
            }
            int lookAheadStep = this.q;
            if (g(buffer, c(((long) lookAheadStep) + index, mask)) == null) {
                this.x = (((long) lookAheadStep) + index) - 1;
                return p(buffer, e, index, offset);
            } else if (g(buffer, c(1 + index, mask)) == null) {
                return p(buffer, e, index, offset);
            } else {
                k(buffer, index, offset, e, (long) mask);
                return true;
            }
        } else {
            throw new NullPointerException("Null is not a valid element");
        }
    }

    private boolean p(AtomicReferenceArray<Object> buffer, T e, long index, int offset) {
        m(buffer, offset, e);
        o(1 + index);
        return true;
    }

    private void k(AtomicReferenceArray<Object> oldBuffer, long currIndex, int offset, T e, long mask) {
        AtomicReferenceArray<Object> newBuffer = new AtomicReferenceArray<>(oldBuffer.length());
        this.z = newBuffer;
        this.x = (currIndex + mask) - 1;
        m(newBuffer, offset, e);
        n(oldBuffer, newBuffer);
        m(oldBuffer, offset, d);
        o(1 + currIndex);
    }

    private void n(AtomicReferenceArray<Object> curr, AtomicReferenceArray<Object> next) {
        m(curr, b(curr.length() - 1), next);
    }

    private AtomicReferenceArray<Object> h(AtomicReferenceArray<Object> curr, int nextIndex) {
        int nextOffset = b(nextIndex);
        AtomicReferenceArray<Object> nextBuffer = (AtomicReferenceArray) g(curr, nextOffset);
        m(curr, nextOffset, (Object) null);
        return nextBuffer;
    }

    public T poll() {
        AtomicReferenceArray<Object> buffer = this.a1;
        long index = d();
        int mask = this.p0;
        int offset = c(index, mask);
        Object e = g(buffer, offset);
        boolean isNextBuffer = e == d;
        if (e != null && !isNextBuffer) {
            m(buffer, offset, (Object) null);
            l(1 + index);
            return e;
        } else if (isNextBuffer) {
            return j(h(buffer, mask + 1), index, mask);
        } else {
            return null;
        }
    }

    private T j(AtomicReferenceArray<Object> nextBuffer, long index, int mask) {
        this.a1 = nextBuffer;
        int offsetInNew = c(index, mask);
        T n = g(nextBuffer, offsetInNew);
        if (n != null) {
            m(nextBuffer, offsetInNew, (Object) null);
            l(1 + index);
        }
        return n;
    }

    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    public boolean isEmpty() {
        return i() == f();
    }

    private void a(int capacity) {
        this.q = Math.min(capacity / 4, c);
    }

    private long i() {
        return this.f.get();
    }

    private long f() {
        return this.p1.get();
    }

    private long e() {
        return this.f.get();
    }

    private long d() {
        return this.p1.get();
    }

    private void o(long v) {
        this.f.lazySet(v);
    }

    private void l(long v) {
        this.p1.lazySet(v);
    }

    private static int c(long index, int mask) {
        return b(((int) index) & mask);
    }

    private static int b(int index) {
        return index;
    }

    private static void m(AtomicReferenceArray<Object> buffer, int offset, Object e) {
        buffer.lazySet(offset, e);
    }

    private static <E> Object g(AtomicReferenceArray<Object> buffer, int offset) {
        return buffer.get(offset);
    }
}
