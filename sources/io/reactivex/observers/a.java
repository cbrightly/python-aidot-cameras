package io.reactivex.observers;

import io.reactivex.disposables.b;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.internal.util.h;
import io.reactivex.q;

/* compiled from: SerializedObserver */
public final class a<T> implements q<T>, b {
    final q<? super T> c;
    final boolean d;
    b f;
    boolean q;
    io.reactivex.internal.util.a<Object> x;
    volatile boolean y;

    public a(q<? super T> downstream) {
        this(downstream, false);
    }

    public a(q<? super T> actual, boolean delayError) {
        this.c = actual;
        this.d = delayError;
    }

    public void onSubscribe(b d2) {
        if (c.validate(this.f, d2)) {
            this.f = d2;
            this.c.onSubscribe(this);
        }
    }

    public void dispose() {
        this.f.dispose();
    }

    public boolean isDisposed() {
        return this.f.isDisposed();
    }

    public void onNext(T t) {
        if (!this.y) {
            if (t == null) {
                this.f.dispose();
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                return;
            }
            synchronized (this) {
                if (!this.y) {
                    if (this.q) {
                        AppendOnlyLinkedArrayList<Object> q2 = this.x;
                        if (q2 == null) {
                            q2 = new io.reactivex.internal.util.a<>(4);
                            this.x = q2;
                        }
                        q2.c(h.next(t));
                        return;
                    }
                    this.q = true;
                    this.c.onNext(t);
                    a();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0033, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003a, code lost:
        if (r0 == false) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003c, code lost:
        io.reactivex.plugins.a.q(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0040, code lost:
        r3.c.onError(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0045, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onError(java.lang.Throwable r4) {
        /*
            r3 = this;
            boolean r0 = r3.y
            if (r0 == 0) goto L_0x0008
            io.reactivex.plugins.a.q(r4)
            return
        L_0x0008:
            monitor-enter(r3)
            boolean r0 = r3.y     // Catch:{ all -> 0x0046 }
            if (r0 == 0) goto L_0x000f
            r0 = 1
            goto L_0x0039
        L_0x000f:
            boolean r0 = r3.q     // Catch:{ all -> 0x0046 }
            r1 = 1
            if (r0 == 0) goto L_0x0034
            r3.y = r1     // Catch:{ all -> 0x0046 }
            io.reactivex.internal.util.a<java.lang.Object> r0 = r3.x     // Catch:{ all -> 0x0046 }
            if (r0 != 0) goto L_0x0023
            io.reactivex.internal.util.a r1 = new io.reactivex.internal.util.a     // Catch:{ all -> 0x0046 }
            r2 = 4
            r1.<init>(r2)     // Catch:{ all -> 0x0046 }
            r0 = r1
            r3.x = r0     // Catch:{ all -> 0x0046 }
        L_0x0023:
            java.lang.Object r1 = io.reactivex.internal.util.h.error(r4)     // Catch:{ all -> 0x0046 }
            boolean r2 = r3.d     // Catch:{ all -> 0x0046 }
            if (r2 == 0) goto L_0x002f
            r0.c(r1)     // Catch:{ all -> 0x0046 }
            goto L_0x0032
        L_0x002f:
            r0.e(r1)     // Catch:{ all -> 0x0046 }
        L_0x0032:
            monitor-exit(r3)     // Catch:{ all -> 0x0046 }
            return
        L_0x0034:
            r3.y = r1     // Catch:{ all -> 0x0046 }
            r3.q = r1     // Catch:{ all -> 0x0046 }
            r0 = 0
        L_0x0039:
            monitor-exit(r3)     // Catch:{ all -> 0x0046 }
            if (r0 == 0) goto L_0x0040
            io.reactivex.plugins.a.q(r4)
            return
        L_0x0040:
            io.reactivex.q<? super T> r1 = r3.c
            r1.onError(r4)
            return
        L_0x0046:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0046 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.observers.a.onError(java.lang.Throwable):void");
    }

    public void onComplete() {
        if (!this.y) {
            synchronized (this) {
                if (!this.y) {
                    if (this.q) {
                        AppendOnlyLinkedArrayList<Object> q2 = this.x;
                        if (q2 == null) {
                            q2 = new io.reactivex.internal.util.a<>(4);
                            this.x = q2;
                        }
                        q2.c(h.complete());
                        return;
                    }
                    this.y = true;
                    this.q = true;
                    this.c.onComplete();
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void a() {
        AppendOnlyLinkedArrayList<Object> q2;
        do {
            synchronized (this) {
                q2 = this.x;
                if (q2 == null) {
                    this.q = false;
                    return;
                }
                this.x = null;
            }
        } while (!q2.a(this.c));
    }
}
