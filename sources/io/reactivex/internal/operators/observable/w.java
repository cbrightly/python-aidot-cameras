package io.reactivex.internal.operators.observable;

import io.reactivex.functions.e;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.disposables.f;
import io.reactivex.internal.operators.observable.ObservablePublishAlt;
import io.reactivex.o;
import io.reactivex.q;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservablePublishAlt */
public final class w<T> extends io.reactivex.observables.a<T> implements f {
    final o<T> c;
    final AtomicReference<b<T>> d = new AtomicReference<>();

    public w(o<T> source) {
        this.c = source;
    }

    public void n0(e<? super io.reactivex.disposables.b> connection) {
        ObservablePublishAlt.PublishConnection<T> conn;
        while (true) {
            conn = (b) this.d.get();
            if (conn != null && !conn.isDisposed()) {
                break;
            }
            ObservablePublishAlt.PublishConnection<T> fresh = new b<>(this.d);
            if (this.d.compareAndSet(conn, fresh)) {
                conn = fresh;
                break;
            }
        }
        boolean z = true;
        if (conn.connect.get() || !conn.connect.compareAndSet(false, true)) {
            z = false;
        }
        boolean doConnect = z;
        try {
            connection.accept(conn);
            if (doConnect) {
                this.c.a(conn);
            }
        } catch (Throwable ex) {
            io.reactivex.exceptions.a.b(ex);
            throw io.reactivex.internal.util.f.d(ex);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a0(io.reactivex.q<? super T> r4) {
        /*
            r3 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.w$b<T>> r0 = r3.d
            java.lang.Object r0 = r0.get()
            io.reactivex.internal.operators.observable.w$b r0 = (io.reactivex.internal.operators.observable.w.b) r0
            if (r0 != 0) goto L_0x001b
            io.reactivex.internal.operators.observable.w$b r1 = new io.reactivex.internal.operators.observable.w$b
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.w$b<T>> r2 = r3.d
            r1.<init>(r2)
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.w$b<T>> r2 = r3.d
            boolean r2 = r2.compareAndSet(r0, r1)
            if (r2 != 0) goto L_0x001a
            goto L_0x0000
        L_0x001a:
            r0 = r1
        L_0x001b:
            io.reactivex.internal.operators.observable.w$a r1 = new io.reactivex.internal.operators.observable.w$a
            r1.<init>(r4, r0)
            r4.onSubscribe(r1)
            boolean r2 = r0.add(r1)
            if (r2 == 0) goto L_0x0033
            boolean r2 = r1.isDisposed()
            if (r2 == 0) goto L_0x0032
            r0.remove(r1)
        L_0x0032:
            return
        L_0x0033:
            java.lang.Throwable r2 = r0.error
            if (r2 == 0) goto L_0x003b
            r4.onError(r2)
            goto L_0x003e
        L_0x003b:
            r4.onComplete()
        L_0x003e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.w.a0(io.reactivex.q):void");
    }

    public void c(io.reactivex.disposables.b connection) {
        this.d.compareAndSet((b) connection, (Object) null);
    }

    /* compiled from: ObservablePublishAlt */
    public static final class b<T> extends AtomicReference<a<T>[]> implements q<T>, io.reactivex.disposables.b {
        static final a[] EMPTY = new a[0];
        static final a[] TERMINATED = new a[0];
        private static final long serialVersionUID = -3251430252873581268L;
        final AtomicBoolean connect = new AtomicBoolean();
        final AtomicReference<b<T>> current;
        Throwable error;
        final AtomicReference<io.reactivex.disposables.b> upstream;

        public b(AtomicReference<b<T>> current2) {
            this.current = current2;
            this.upstream = new AtomicReference<>();
            lazySet(EMPTY);
        }

        public void dispose() {
            getAndSet(TERMINATED);
            this.current.compareAndSet(this, (Object) null);
            c.dispose(this.upstream);
        }

        public boolean isDisposed() {
            return get() == TERMINATED;
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            c.setOnce(this.upstream, d);
        }

        public void onNext(T t) {
            for (ObservablePublishAlt.InnerDisposable<T> inner : (a[]) get()) {
                inner.downstream.onNext(t);
            }
        }

        public void onError(Throwable e) {
            this.error = e;
            this.upstream.lazySet(c.DISPOSED);
            for (ObservablePublishAlt.InnerDisposable<T> inner : (a[]) getAndSet(TERMINATED)) {
                inner.downstream.onError(e);
            }
        }

        public void onComplete() {
            this.upstream.lazySet(c.DISPOSED);
            for (ObservablePublishAlt.InnerDisposable<T> inner : (a[]) getAndSet(TERMINATED)) {
                inner.downstream.onComplete();
            }
        }

        public boolean add(a<T> inner) {
            ObservablePublishAlt.InnerDisposable<T>[] a;
            ObservablePublishAlt.InnerDisposable<T>[] b;
            do {
                a = (a[]) get();
                if (a == TERMINATED) {
                    return false;
                }
                int n = a.length;
                b = new a[(n + 1)];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = inner;
            } while (!compareAndSet(a, b));
            return true;
        }

        public void remove(a<T> inner) {
            ObservablePublishAlt.InnerDisposable<T>[] a;
            ObservablePublishAlt.InnerDisposable<T>[] b;
            do {
                a = (a[]) get();
                int n = a.length;
                if (n != 0) {
                    int j = -1;
                    int i = 0;
                    while (true) {
                        if (i >= n) {
                            break;
                        } else if (a[i] == inner) {
                            j = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (j >= 0) {
                        b = EMPTY;
                        if (n != 1) {
                            b = new a[(n - 1)];
                            System.arraycopy(a, 0, b, 0, j);
                            System.arraycopy(a, j + 1, b, j, (n - j) - 1);
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!compareAndSet(a, b));
        }
    }

    /* compiled from: ObservablePublishAlt */
    public static final class a<T> extends AtomicReference<b<T>> implements io.reactivex.disposables.b {
        private static final long serialVersionUID = 7463222674719692880L;
        final q<? super T> downstream;

        public a(q<? super T> downstream2, b<T> parent) {
            this.downstream = downstream2;
            lazySet(parent);
        }

        public void dispose() {
            ObservablePublishAlt.PublishConnection<T> p = (b) getAndSet((Object) null);
            if (p != null) {
                p.remove(this);
            }
        }

        public boolean isDisposed() {
            return get() == null;
        }
    }
}
