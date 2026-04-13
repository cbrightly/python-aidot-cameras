package io.reactivex.processors;

import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.a;
import io.reactivex.internal.util.h;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: SerializedProcessor */
public final class d<T> extends a<T> {
    final a<T> d;
    boolean f;
    a<Object> q;
    volatile boolean x;

    d(a<T> actual) {
        this.d = actual;
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> s) {
        this.d.a(s);
    }

    public void onSubscribe(c s) {
        boolean cancel;
        if (!this.x) {
            synchronized (this) {
                if (this.x) {
                    cancel = true;
                } else if (this.f) {
                    AppendOnlyLinkedArrayList<Object> q2 = this.q;
                    if (q2 == null) {
                        q2 = new a<>(4);
                        this.q = q2;
                    }
                    q2.c(h.subscription(s));
                    return;
                } else {
                    this.f = true;
                    cancel = false;
                }
            }
        } else {
            cancel = true;
        }
        if (cancel) {
            s.cancel();
            return;
        }
        this.d.onSubscribe(s);
        X();
    }

    public void onNext(T t) {
        if (!this.x) {
            synchronized (this) {
                if (!this.x) {
                    if (this.f) {
                        AppendOnlyLinkedArrayList<Object> q2 = this.q;
                        if (q2 == null) {
                            q2 = new a<>(4);
                            this.q = q2;
                        }
                        q2.c(h.next(t));
                        return;
                    }
                    this.f = true;
                    this.d.onNext(t);
                    X();
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
        r3.d.onError(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onError(java.lang.Throwable r4) {
        /*
            r3 = this;
            boolean r0 = r3.x
            if (r0 == 0) goto L_0x0008
            io.reactivex.plugins.a.q(r4)
            return
        L_0x0008:
            monitor-enter(r3)
            boolean r0 = r3.x     // Catch:{ all -> 0x003d }
            if (r0 == 0) goto L_0x000f
            r0 = 1
            goto L_0x0030
        L_0x000f:
            r0 = 1
            r3.x = r0     // Catch:{ all -> 0x003d }
            boolean r1 = r3.f     // Catch:{ all -> 0x003d }
            if (r1 == 0) goto L_0x002c
            io.reactivex.internal.util.a<java.lang.Object> r0 = r3.q     // Catch:{ all -> 0x003d }
            if (r0 != 0) goto L_0x0023
            io.reactivex.internal.util.a r1 = new io.reactivex.internal.util.a     // Catch:{ all -> 0x003d }
            r2 = 4
            r1.<init>(r2)     // Catch:{ all -> 0x003d }
            r0 = r1
            r3.q = r0     // Catch:{ all -> 0x003d }
        L_0x0023:
            java.lang.Object r1 = io.reactivex.internal.util.h.error(r4)     // Catch:{ all -> 0x003d }
            r0.e(r1)     // Catch:{ all -> 0x003d }
            monitor-exit(r3)     // Catch:{ all -> 0x003d }
            return
        L_0x002c:
            r1 = 0
            r3.f = r0     // Catch:{ all -> 0x003d }
            r0 = r1
        L_0x0030:
            monitor-exit(r3)     // Catch:{ all -> 0x003d }
            if (r0 == 0) goto L_0x0037
            io.reactivex.plugins.a.q(r4)
            return
        L_0x0037:
            io.reactivex.processors.a<T> r1 = r3.d
            r1.onError(r4)
            return
        L_0x003d:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x003d }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.processors.d.onError(java.lang.Throwable):void");
    }

    public void onComplete() {
        if (!this.x) {
            synchronized (this) {
                if (!this.x) {
                    this.x = true;
                    if (this.f) {
                        AppendOnlyLinkedArrayList<Object> q2 = this.q;
                        if (q2 == null) {
                            q2 = new a<>(4);
                            this.q = q2;
                        }
                        q2.c(h.complete());
                        return;
                    }
                    this.f = true;
                    this.d.onComplete();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void X() {
        AppendOnlyLinkedArrayList<Object> q2;
        while (true) {
            synchronized (this) {
                q2 = this.q;
                if (q2 == null) {
                    this.f = false;
                    return;
                }
                this.q = null;
            }
            q2.b(this.d);
        }
        while (true) {
        }
    }
}
