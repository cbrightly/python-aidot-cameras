package io.reactivex.internal.operators.observable;

import io.reactivex.functions.e;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.disposables.f;
import io.reactivex.l;
import io.reactivex.q;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableRefCount */
public final class y<T> extends l<T> {
    final io.reactivex.observables.a<T> c;
    final int d;
    final long f;
    final TimeUnit q;
    final r x;
    a y;

    public y(io.reactivex.observables.a<T> source) {
        this(source, 1, 0, TimeUnit.NANOSECONDS, (r) null);
    }

    public y(io.reactivex.observables.a<T> source, int n, long timeout, TimeUnit unit, r scheduler) {
        this.c = source;
        this.d = n;
        this.f = timeout;
        this.q = unit;
        this.x = scheduler;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        a conn;
        io.reactivex.disposables.b bVar;
        boolean connect = false;
        synchronized (this) {
            conn = this.y;
            if (conn == null) {
                conn = new a(this);
                this.y = conn;
            }
            long c2 = conn.subscriberCount;
            if (c2 == 0 && (bVar = conn.timer) != null) {
                bVar.dispose();
            }
            conn.subscriberCount = c2 + 1;
            if (!conn.connected && 1 + c2 == ((long) this.d)) {
                connect = true;
                conn.connected = true;
            }
        }
        this.c.a(new b(observer, this, conn));
        if (connect) {
            this.c.n0(conn);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x003e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void n0(io.reactivex.internal.operators.observable.y.a r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            io.reactivex.internal.operators.observable.y$a r0 = r6.y     // Catch:{ all -> 0x003f }
            if (r0 == 0) goto L_0x003d
            if (r0 == r7) goto L_0x0008
            goto L_0x003d
        L_0x0008:
            long r0 = r7.subscriberCount     // Catch:{ all -> 0x003f }
            r2 = 1
            long r0 = r0 - r2
            r7.subscriberCount = r0     // Catch:{ all -> 0x003f }
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L_0x003b
            boolean r4 = r7.connected     // Catch:{ all -> 0x003f }
            if (r4 != 0) goto L_0x001a
            goto L_0x003b
        L_0x001a:
            long r4 = r6.f     // Catch:{ all -> 0x003f }
            int r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r2 != 0) goto L_0x0025
            r6.r0(r7)     // Catch:{ all -> 0x003f }
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            return
        L_0x0025:
            io.reactivex.internal.disposables.g r2 = new io.reactivex.internal.disposables.g     // Catch:{ all -> 0x003f }
            r2.<init>()     // Catch:{ all -> 0x003f }
            r7.timer = r2     // Catch:{ all -> 0x003f }
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            io.reactivex.r r0 = r6.x
            long r3 = r6.f
            java.util.concurrent.TimeUnit r1 = r6.q
            io.reactivex.disposables.b r0 = r0.c(r7, r3, r1)
            r2.replace(r0)
            return
        L_0x003b:
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            return
        L_0x003d:
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            return
        L_0x003f:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.y.n0(io.reactivex.internal.operators.observable.y$a):void");
    }

    /* access modifiers changed from: package-private */
    public void q0(a rc) {
        synchronized (this) {
            if (this.c instanceof x) {
                a aVar = this.y;
                if (aVar != null && aVar == rc) {
                    this.y = null;
                    o0(rc);
                }
                long j = rc.subscriberCount - 1;
                rc.subscriberCount = j;
                if (j == 0) {
                    p0(rc);
                }
            } else {
                a aVar2 = this.y;
                if (aVar2 != null && aVar2 == rc) {
                    o0(rc);
                    long j2 = rc.subscriberCount - 1;
                    rc.subscriberCount = j2;
                    if (j2 == 0) {
                        this.y = null;
                        p0(rc);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void o0(a rc) {
        io.reactivex.disposables.b bVar = rc.timer;
        if (bVar != null) {
            bVar.dispose();
            rc.timer = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void p0(a rc) {
        io.reactivex.observables.a<T> aVar = this.c;
        if (aVar instanceof io.reactivex.disposables.b) {
            ((io.reactivex.disposables.b) aVar).dispose();
        } else if (aVar instanceof f) {
            ((f) aVar).c((io.reactivex.disposables.b) rc.get());
        }
    }

    /* access modifiers changed from: package-private */
    public void r0(a rc) {
        synchronized (this) {
            if (rc.subscriberCount == 0 && rc == this.y) {
                this.y = null;
                io.reactivex.disposables.b connectionObject = (io.reactivex.disposables.b) rc.get();
                c.dispose(rc);
                io.reactivex.observables.a<T> aVar = this.c;
                if (aVar instanceof io.reactivex.disposables.b) {
                    ((io.reactivex.disposables.b) aVar).dispose();
                } else if (aVar instanceof f) {
                    if (connectionObject == null) {
                        rc.disconnectedEarly = true;
                    } else {
                        ((f) aVar).c(connectionObject);
                    }
                }
            }
        }
    }

    /* compiled from: ObservableRefCount */
    public static final class a extends AtomicReference<io.reactivex.disposables.b> implements Runnable, e<io.reactivex.disposables.b> {
        private static final long serialVersionUID = -4552101107598366241L;
        boolean connected;
        boolean disconnectedEarly;
        final y<?> parent;
        long subscriberCount;
        io.reactivex.disposables.b timer;

        a(y<?> parent2) {
            this.parent = parent2;
        }

        public void run() {
            this.parent.r0(this);
        }

        public void accept(io.reactivex.disposables.b t) {
            c.replace(this, t);
            synchronized (this.parent) {
                if (this.disconnectedEarly) {
                    ((f) this.parent.c).c(t);
                }
            }
        }
    }

    /* compiled from: ObservableRefCount */
    public static final class b<T> extends AtomicBoolean implements q<T>, io.reactivex.disposables.b {
        private static final long serialVersionUID = -7419642935409022375L;
        final a connection;
        final q<? super T> downstream;
        final y<T> parent;
        io.reactivex.disposables.b upstream;

        b(q<? super T> downstream2, y<T> parent2, a connection2) {
            this.downstream = downstream2;
            this.parent = parent2;
            this.connection = connection2;
        }

        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        public void onError(Throwable t) {
            if (compareAndSet(false, true)) {
                this.parent.q0(this.connection);
                this.downstream.onError(t);
                return;
            }
            io.reactivex.plugins.a.q(t);
        }

        public void onComplete() {
            if (compareAndSet(false, true)) {
                this.parent.q0(this.connection);
                this.downstream.onComplete();
            }
        }

        public void dispose() {
            this.upstream.dispose();
            if (compareAndSet(false, true)) {
                this.parent.n0(this.connection);
            }
        }

        public boolean isDisposed() {
            return this.upstream.isDisposed();
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            if (c.validate(this.upstream, d)) {
                this.upstream = d;
                this.downstream.onSubscribe(this);
            }
        }
    }
}
