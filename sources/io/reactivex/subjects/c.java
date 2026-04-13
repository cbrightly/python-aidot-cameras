package io.reactivex.subjects;

import io.reactivex.disposables.b;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.a;
import io.reactivex.internal.util.h;
import io.reactivex.q;

/* compiled from: SerializedSubject */
public final class c<T> extends d<T> implements a.C0309a<Object> {
    final d<T> c;
    boolean d;
    a<Object> f;
    volatile boolean q;

    c(d<T> actual) {
        this.c = actual;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        this.c.a(observer);
    }

    public void onSubscribe(b d2) {
        boolean cancel;
        if (!this.q) {
            synchronized (this) {
                if (this.q) {
                    cancel = true;
                } else if (this.d) {
                    AppendOnlyLinkedArrayList<Object> q2 = this.f;
                    if (q2 == null) {
                        q2 = new a<>(4);
                        this.f = q2;
                    }
                    q2.c(h.disposable(d2));
                    return;
                } else {
                    this.d = true;
                    cancel = false;
                }
            }
        } else {
            cancel = true;
        }
        if (cancel) {
            d2.dispose();
            return;
        }
        this.c.onSubscribe(d2);
        o0();
    }

    public void onNext(T t) {
        if (!this.q) {
            synchronized (this) {
                if (!this.q) {
                    if (this.d) {
                        AppendOnlyLinkedArrayList<Object> q2 = this.f;
                        if (q2 == null) {
                            q2 = new a<>(4);
                            this.f = q2;
                        }
                        q2.c(h.next(t));
                        return;
                    }
                    this.d = true;
                    this.c.onNext(t);
                    o0();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0031, code lost:
        if (r0 == false) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0033, code lost:
        io.reactivex.plugins.a.q(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0036, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0037, code lost:
        r3.c.onError(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onError(java.lang.Throwable r4) {
        /*
            r3 = this;
            boolean r0 = r3.q
            if (r0 == 0) goto L_0x0008
            io.reactivex.plugins.a.q(r4)
            return
        L_0x0008:
            monitor-enter(r3)
            boolean r0 = r3.q     // Catch:{ all -> 0x003d }
            if (r0 == 0) goto L_0x000f
            r0 = 1
            goto L_0x0030
        L_0x000f:
            r0 = 1
            r3.q = r0     // Catch:{ all -> 0x003d }
            boolean r1 = r3.d     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x002c
            io.reactivex.internal.util.a<java.lang.Object> r0 = r3.f     // Catch:{ all -> 0x003d }
            if (r0 != 0) goto L_0x0023
            io.reactivex.internal.util.a r1 = new io.reactivex.internal.util.a     // Catch:{ all -> 0x003d }
            r2 = 4
            r1.<init>(r2)     // Catch:{ all -> 0x003d }
            r0 = r1
            r3.f = r0     // Catch:{ all -> 0x003d }
        L_0x0023:
            java.lang.Object r1 = io.reactivex.internal.util.h.error(r4)     // Catch:{ all -> 0x003d }
            r0.e(r1)     // Catch:{ all -> 0x003d }
            monitor-exit(r3)     // Catch:{ all -> 0x003d }
            return
        L_0x002c:
            r1 = 0
            r3.d = r0     // Catch:{ all -> 0x003d }
            r0 = r1
        L_0x0030:
            monitor-exit(r3)     // Catch:{ all -> 0x003d }
            if (r0 == 0) goto L_0x0037
            io.reactivex.plugins.a.q(r4)
            return
        L_0x0037:
            io.reactivex.subjects.d<T> r1 = r3.c
            r1.onError(r4)
            return
        L_0x003d:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x003d }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.subjects.c.onError(java.lang.Throwable):void");
    }

    public void onComplete() {
        if (!this.q) {
            synchronized (this) {
                if (!this.q) {
                    this.q = true;
                    if (this.d) {
                        AppendOnlyLinkedArrayList<Object> q2 = this.f;
                        if (q2 == null) {
                            q2 = new a<>(4);
                            this.f = q2;
                        }
                        q2.c(h.complete());
                        return;
                    }
                    this.d = true;
                    this.c.onComplete();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void o0() {
        AppendOnlyLinkedArrayList<Object> q2;
        while (true) {
            synchronized (this) {
                q2 = this.f;
                if (q2 == null) {
                    this.d = false;
                    return;
                }
                this.f = null;
            }
            q2.d(this);
        }
        while (true) {
        }
    }

    public boolean test(Object o) {
        return h.acceptFull(o, this.c);
    }
}
