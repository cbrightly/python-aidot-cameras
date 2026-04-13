package io.reactivex.subjects;

import io.reactivex.disposables.b;
import io.reactivex.internal.util.a;
import io.reactivex.internal.util.f;
import io.reactivex.internal.util.h;
import io.reactivex.q;
import io.reactivex.subjects.BehaviorSubject;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: BehaviorSubject */
public final class a<T> extends d<T> {
    private static final Object[] c = new Object[0];
    static final C0312a[] d = new C0312a[0];
    static final C0312a[] f = new C0312a[0];
    final AtomicReference<Throwable> a1 = new AtomicReference<>();
    final Lock p0;
    long p1;
    final AtomicReference<Object> q = new AtomicReference<>();
    final AtomicReference<C0312a<T>[]> x = new AtomicReference<>(d);
    final ReadWriteLock y;
    final Lock z;

    public static <T> a<T> p0() {
        return new a<>();
    }

    a() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.y = reentrantReadWriteLock;
        this.z = reentrantReadWriteLock.readLock();
        this.p0 = reentrantReadWriteLock.writeLock();
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        BehaviorSubject.BehaviorDisposable<T> bs = new C0312a<>(observer, this);
        observer.onSubscribe(bs);
        if (!o0(bs)) {
            Throwable ex = this.a1.get();
            if (ex == f.a) {
                observer.onComplete();
            } else {
                observer.onError(ex);
            }
        } else if (bs.z) {
            q0(bs);
        } else {
            bs.a();
        }
    }

    public void onSubscribe(b d2) {
        if (this.a1.get() != null) {
            d2.dispose();
        }
    }

    public void onNext(T t) {
        io.reactivex.internal.functions.b.e(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (this.a1.get() == null) {
            Object o = h.next(t);
            r0(o);
            for (BehaviorSubject.BehaviorDisposable<T> bs : (C0312a[]) this.x.get()) {
                bs.c(o, this.p1);
            }
        }
    }

    public void onError(Throwable t) {
        io.reactivex.internal.functions.b.e(t, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        if (!this.a1.compareAndSet((Object) null, t)) {
            io.reactivex.plugins.a.q(t);
            return;
        }
        Object o = h.error(t);
        for (BehaviorSubject.BehaviorDisposable<T> bs : s0(o)) {
            bs.c(o, this.p1);
        }
    }

    public void onComplete() {
        if (this.a1.compareAndSet((Object) null, f.a)) {
            Object o = h.complete();
            for (BehaviorSubject.BehaviorDisposable<T> bs : s0(o)) {
                bs.c(o, this.p1);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean o0(C0312a<T> rs) {
        BehaviorSubject.BehaviorDisposable<T>[] a;
        BehaviorSubject.BehaviorDisposable<T>[] b;
        do {
            a = (C0312a[]) this.x.get();
            if (a == f) {
                return false;
            }
            int len = a.length;
            b = new C0312a[(len + 1)];
            System.arraycopy(a, 0, b, 0, len);
            b[len] = rs;
        } while (!this.x.compareAndSet(a, b));
        return true;
    }

    /* access modifiers changed from: package-private */
    public void q0(C0312a<T> rs) {
        BehaviorSubject.BehaviorDisposable<T>[] a;
        BehaviorSubject.BehaviorDisposable<T>[] b;
        do {
            a = (C0312a[]) this.x.get();
            int len = a.length;
            if (len != 0) {
                int j = -1;
                int i = 0;
                while (true) {
                    if (i >= len) {
                        break;
                    } else if (a[i] == rs) {
                        j = i;
                        break;
                    } else {
                        i++;
                    }
                }
                if (j >= 0) {
                    if (len == 1) {
                        b = d;
                    } else {
                        BehaviorSubject.BehaviorDisposable<T>[] b2 = new C0312a[(len - 1)];
                        System.arraycopy(a, 0, b2, 0, j);
                        System.arraycopy(a, j + 1, b2, j, (len - j) - 1);
                        b = b2;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.x.compareAndSet(a, b));
    }

    /* access modifiers changed from: package-private */
    public C0312a<T>[] s0(Object terminalValue) {
        AtomicReference<C0312a<T>[]> atomicReference = this.x;
        BehaviorSubject.BehaviorDisposable<T>[] behaviorDisposableArr = f;
        BehaviorSubject.BehaviorDisposable<T>[] a = (C0312a[]) atomicReference.getAndSet(behaviorDisposableArr);
        if (a != behaviorDisposableArr) {
            r0(terminalValue);
        }
        return a;
    }

    /* access modifiers changed from: package-private */
    public void r0(Object o) {
        this.p0.lock();
        this.p1++;
        this.q.lazySet(o);
        this.p0.unlock();
    }

    /* renamed from: io.reactivex.subjects.a$a  reason: collision with other inner class name */
    /* compiled from: BehaviorSubject */
    public static final class C0312a<T> implements b, a.C0309a<Object> {
        final q<? super T> c;
        final a<T> d;
        boolean f;
        long p0;
        boolean q;
        io.reactivex.internal.util.a<Object> x;
        boolean y;
        volatile boolean z;

        C0312a(q<? super T> actual, a<T> state) {
            this.c = actual;
            this.d = state;
        }

        public void dispose() {
            if (!this.z) {
                this.z = true;
                this.d.q0(this);
            }
        }

        public boolean isDisposed() {
            return this.z;
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
            if (r2 == null) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0037, code lost:
            if (test(r2) == false) goto L_0x003a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0039, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x003a, code lost:
            b();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a() {
            /*
                r5 = this;
                boolean r0 = r5.z
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                monitor-enter(r5)
                boolean r0 = r5.z     // Catch:{ all -> 0x003e }
                if (r0 == 0) goto L_0x000c
                monitor-exit(r5)     // Catch:{ all -> 0x003e }
                return
            L_0x000c:
                boolean r0 = r5.f     // Catch:{ all -> 0x003e }
                if (r0 == 0) goto L_0x0012
                monitor-exit(r5)     // Catch:{ all -> 0x003e }
                return
            L_0x0012:
                io.reactivex.subjects.a<T> r0 = r5.d     // Catch:{ all -> 0x003e }
                java.util.concurrent.locks.Lock r1 = r0.z     // Catch:{ all -> 0x003e }
                r1.lock()     // Catch:{ all -> 0x003e }
                long r2 = r0.p1     // Catch:{ all -> 0x003e }
                r5.p0 = r2     // Catch:{ all -> 0x003e }
                java.util.concurrent.atomic.AtomicReference<java.lang.Object> r2 = r0.q     // Catch:{ all -> 0x003e }
                java.lang.Object r2 = r2.get()     // Catch:{ all -> 0x003e }
                r1.unlock()     // Catch:{ all -> 0x003e }
                r3 = 1
                if (r2 == 0) goto L_0x002b
                r4 = r3
                goto L_0x002c
            L_0x002b:
                r4 = 0
            L_0x002c:
                r5.q = r4     // Catch:{ all -> 0x003e }
                r5.f = r3     // Catch:{ all -> 0x003e }
                monitor-exit(r5)     // Catch:{ all -> 0x003e }
                if (r2 == 0) goto L_0x003d
                boolean r0 = r5.test(r2)
                if (r0 == 0) goto L_0x003a
                return
            L_0x003a:
                r5.b()
            L_0x003d:
                return
            L_0x003e:
                r0 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x003e }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.subjects.a.C0312a.a():void");
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0032, code lost:
            r3.y = true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void c(java.lang.Object r4, long r5) {
            /*
                r3 = this;
                boolean r0 = r3.z
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                boolean r0 = r3.y
                if (r0 != 0) goto L_0x0038
                monitor-enter(r3)
                boolean r0 = r3.z     // Catch:{ all -> 0x0035 }
                if (r0 == 0) goto L_0x0010
                monitor-exit(r3)     // Catch:{ all -> 0x0035 }
                return
            L_0x0010:
                long r0 = r3.p0     // Catch:{ all -> 0x0035 }
                int r0 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
                if (r0 != 0) goto L_0x0018
                monitor-exit(r3)     // Catch:{ all -> 0x0035 }
                return
            L_0x0018:
                boolean r0 = r3.q     // Catch:{ all -> 0x0035 }
                if (r0 == 0) goto L_0x002e
                io.reactivex.internal.util.a<java.lang.Object> r0 = r3.x     // Catch:{ all -> 0x0035 }
                if (r0 != 0) goto L_0x0029
                io.reactivex.internal.util.a r1 = new io.reactivex.internal.util.a     // Catch:{ all -> 0x0035 }
                r2 = 4
                r1.<init>(r2)     // Catch:{ all -> 0x0035 }
                r0 = r1
                r3.x = r0     // Catch:{ all -> 0x0035 }
            L_0x0029:
                r0.c(r4)     // Catch:{ all -> 0x0035 }
                monitor-exit(r3)     // Catch:{ all -> 0x0035 }
                return
            L_0x002e:
                r0 = 1
                r3.f = r0     // Catch:{ all -> 0x0035 }
                monitor-exit(r3)     // Catch:{ all -> 0x0035 }
                r3.y = r0
                goto L_0x0038
            L_0x0035:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0035 }
                throw r0
            L_0x0038:
                r3.test(r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.subjects.a.C0312a.c(java.lang.Object, long):void");
        }

        public boolean test(Object o) {
            return this.z || h.accept(o, this.c);
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0013, code lost:
            r0.d(r2);
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b() {
            /*
                r2 = this;
            L_0x0000:
                boolean r0 = r2.z
                if (r0 == 0) goto L_0x0005
                return
            L_0x0005:
                monitor-enter(r2)
                io.reactivex.internal.util.a<java.lang.Object> r0 = r2.x     // Catch:{ all -> 0x0017 }
                if (r0 != 0) goto L_0x000f
                r1 = 0
                r2.q = r1     // Catch:{ all -> 0x0017 }
                monitor-exit(r2)     // Catch:{ all -> 0x0017 }
                return
            L_0x000f:
                r1 = 0
                r2.x = r1     // Catch:{ all -> 0x0017 }
                monitor-exit(r2)     // Catch:{ all -> 0x0017 }
                r0.d(r2)
                goto L_0x0000
            L_0x0017:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0017 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: io.reactivex.subjects.a.C0312a.b():void");
        }
    }
}
