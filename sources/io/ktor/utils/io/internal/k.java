package io.ktor.utils.io.internal;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: RingBufferCapacity.kt */
public final class k {
    private static final AtomicIntegerFieldUpdater<k> a;
    private static final AtomicIntegerFieldUpdater<k> b;
    private static final AtomicIntegerFieldUpdater<k> c;
    public static final a d = new a((DefaultConstructorMarker) null);
    public volatile int availableForRead;
    public volatile int availableForWrite;
    private final int e;
    public volatile int pendingToFlush;

    public k(int totalCapacity) {
        this.e = totalCapacity;
        this.availableForWrite = totalCapacity;
    }

    public final void i() {
        this.availableForRead = 0;
        this.availableForWrite = this.e;
        this.pendingToFlush = 0;
    }

    public final boolean l(int n) {
        int remaining;
        AtomicIntegerFieldUpdater AvailableForRead = a;
        do {
            remaining = this.availableForRead;
            if (remaining < n) {
                return false;
            }
        } while (!AvailableForRead.compareAndSet(this, remaining, remaining - n));
        return true;
    }

    public final int k(int n) {
        int remaining;
        int delta;
        AtomicIntegerFieldUpdater AvailableForRead = a;
        do {
            remaining = this.availableForRead;
            delta = Math.min(n, remaining);
            if (delta == 0) {
                return 0;
            }
        } while (!AvailableForRead.compareAndSet(this, remaining, remaining - delta));
        return delta;
    }

    public final int m(int n) {
        int remaining;
        AtomicIntegerFieldUpdater AvailableForWrite = b;
        do {
            remaining = this.availableForWrite;
            if (remaining < n) {
                return 0;
            }
        } while (!AvailableForWrite.compareAndSet(this, remaining, 0));
        return remaining;
    }

    public final int n(int n) {
        int remaining;
        int delta;
        AtomicIntegerFieldUpdater AvailableForWrite = b;
        do {
            remaining = this.availableForWrite;
            delta = Math.min(n, remaining);
            if (delta == 0) {
                return 0;
            }
        } while (!AvailableForWrite.compareAndSet(this, remaining, remaining - delta));
        return delta;
    }

    public final void a(int n) {
        int remaining;
        int update;
        int totalCapacity = this.e;
        AtomicIntegerFieldUpdater AvailableForWrite = b;
        do {
            remaining = this.availableForWrite;
            update = remaining + n;
            if (update > totalCapacity) {
                c(remaining, update, n);
                throw null;
            }
        } while (!AvailableForWrite.compareAndSet(this, remaining, update));
    }

    private final Void c(int remaining, int update, int n) {
        throw new IllegalArgumentException("Completed read overflow: " + remaining + " + " + n + " = " + update + " > " + this.e);
    }

    public final void d(int n) {
        int pending;
        int update;
        int totalCapacity = this.e;
        AtomicIntegerFieldUpdater PendingToFlush = c;
        do {
            pending = this.pendingToFlush;
            update = pending + n;
            if (update > totalCapacity) {
                b(pending, n);
                throw null;
            }
        } while (!PendingToFlush.compareAndSet(this, pending, update));
    }

    private final Void b(int pending, int n) {
        throw new IllegalArgumentException("Complete write overflow: " + pending + " + " + n + " > " + this.e);
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x001a A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:9:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean e() {
        /*
            r6 = this;
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater<io.ktor.utils.io.internal.k> r0 = a
            java.util.concurrent.atomic.AtomicIntegerFieldUpdater<io.ktor.utils.io.internal.k> r1 = c
            r2 = 0
            int r1 = r1.getAndSet(r6, r2)
        L_0x0009:
            int r3 = r6.availableForRead
            int r4 = r3 + r1
            if (r3 == r4) goto L_0x0018
            boolean r5 = r0.compareAndSet(r6, r3, r4)
            if (r5 == 0) goto L_0x0017
            goto L_0x0018
        L_0x0017:
            goto L_0x0009
        L_0x0018:
            if (r4 <= 0) goto L_0x001b
            r2 = 1
        L_0x001b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.ktor.utils.io.internal.k.e():boolean");
    }

    public final boolean j() {
        int remaining;
        AtomicIntegerFieldUpdater AvailableForWrite = b;
        do {
            remaining = this.availableForWrite;
            if (this.pendingToFlush > 0 || this.availableForRead > 0 || remaining != this.e) {
                return false;
            }
        } while (!AvailableForWrite.compareAndSet(this, remaining, 0));
        return true;
    }

    public final void f() {
        b.getAndSet(this, 0);
    }

    public final boolean g() {
        return this.availableForWrite == this.e;
    }

    public final boolean h() {
        return this.availableForWrite == 0;
    }

    /* compiled from: RingBufferCapacity.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    static {
        Class<k> cls = k.class;
        AtomicIntegerFieldUpdater<k> newUpdater = AtomicIntegerFieldUpdater.newUpdater(cls, h.INSTANCE.getName());
        kotlin.jvm.internal.k.b(newUpdater, "AtomicIntegerFieldUpdate…wner::class.java, p.name)");
        a = newUpdater;
        AtomicIntegerFieldUpdater<k> newUpdater2 = AtomicIntegerFieldUpdater.newUpdater(cls, i.INSTANCE.getName());
        kotlin.jvm.internal.k.b(newUpdater2, "AtomicIntegerFieldUpdate…wner::class.java, p.name)");
        b = newUpdater2;
        AtomicIntegerFieldUpdater<k> newUpdater3 = AtomicIntegerFieldUpdater.newUpdater(cls, j.INSTANCE.getName());
        kotlin.jvm.internal.k.b(newUpdater3, "AtomicIntegerFieldUpdate…wner::class.java, p.name)");
        c = newUpdater3;
    }
}
