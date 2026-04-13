package io.reactivex.internal.operators.observable;

import io.reactivex.internal.operators.observable.ObservablePublish;
import io.reactivex.o;
import io.reactivex.q;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservablePublish */
public final class v<T> extends io.reactivex.observables.a<T> implements x<T> {
    final o<T> c;
    final AtomicReference<b<T>> d;
    final o<T> f;

    public static <T> io.reactivex.observables.a<T> q0(o<T> source) {
        AtomicReference<ObservablePublish.PublishObserver<T>> curr = new AtomicReference<>();
        return io.reactivex.plugins.a.o(new v(new c<>(curr), source, curr));
    }

    private v(o<T> onSubscribe, o<T> source, AtomicReference<b<T>> current) {
        this.f = onSubscribe;
        this.c = source;
        this.d = current;
    }

    public o<T> b() {
        return this.c;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        this.f.a(observer);
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void n0(io.reactivex.functions.e<? super io.reactivex.disposables.b> r5) {
        /*
            r4 = this;
        L_0x0000:
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.v$b<T>> r0 = r4.d
            java.lang.Object r0 = r0.get()
            io.reactivex.internal.operators.observable.v$b r0 = (io.reactivex.internal.operators.observable.v.b) r0
            if (r0 == 0) goto L_0x0010
            boolean r1 = r0.isDisposed()
            if (r1 == 0) goto L_0x0021
        L_0x0010:
            io.reactivex.internal.operators.observable.v$b r1 = new io.reactivex.internal.operators.observable.v$b
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.v$b<T>> r2 = r4.d
            r1.<init>(r2)
            java.util.concurrent.atomic.AtomicReference<io.reactivex.internal.operators.observable.v$b<T>> r2 = r4.d
            boolean r2 = r2.compareAndSet(r0, r1)
            if (r2 != 0) goto L_0x0020
            goto L_0x0000
        L_0x0020:
            r0 = r1
        L_0x0021:
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.x
            boolean r1 = r1.get()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0034
            java.util.concurrent.atomic.AtomicBoolean r1 = r0.x
            boolean r1 = r1.compareAndSet(r3, r2)
            if (r1 == 0) goto L_0x0034
            goto L_0x0035
        L_0x0034:
            r2 = r3
        L_0x0035:
            r1 = r2
            r5.accept(r0)     // Catch:{ all -> 0x0043 }
            if (r1 == 0) goto L_0x0042
            io.reactivex.o<T> r2 = r4.c
            r2.a(r0)
        L_0x0042:
            return
        L_0x0043:
            r2 = move-exception
            io.reactivex.exceptions.a.b(r2)
            java.lang.RuntimeException r3 = io.reactivex.internal.util.f.d(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.v.n0(io.reactivex.functions.e):void");
    }

    /* compiled from: ObservablePublish */
    public static final class b<T> implements q<T>, io.reactivex.disposables.b {
        static final a[] c = new a[0];
        static final a[] d = new a[0];
        final AtomicReference<b<T>> f;
        final AtomicReference<a<T>[]> q = new AtomicReference<>(c);
        final AtomicBoolean x;
        final AtomicReference<io.reactivex.disposables.b> y = new AtomicReference<>();

        b(AtomicReference<b<T>> current) {
            this.f = current;
            this.x = new AtomicBoolean();
        }

        public void dispose() {
            AtomicReference<a<T>[]> atomicReference = this.q;
            a[] aVarArr = d;
            if (((a[]) atomicReference.getAndSet(aVarArr)) != aVarArr) {
                this.f.compareAndSet(this, (Object) null);
                io.reactivex.internal.disposables.c.dispose(this.y);
            }
        }

        public boolean isDisposed() {
            return this.q.get() == d;
        }

        public void onSubscribe(io.reactivex.disposables.b d2) {
            io.reactivex.internal.disposables.c.setOnce(this.y, d2);
        }

        public void onNext(T t) {
            for (ObservablePublish.InnerDisposable<T> inner : (a[]) this.q.get()) {
                inner.child.onNext(t);
            }
        }

        public void onError(Throwable e) {
            this.f.compareAndSet(this, (Object) null);
            ObservablePublish.InnerDisposable<T>[] a = (a[]) this.q.getAndSet(d);
            if (a.length != 0) {
                for (ObservablePublish.InnerDisposable<T> inner : a) {
                    inner.child.onError(e);
                }
                return;
            }
            io.reactivex.plugins.a.q(e);
        }

        public void onComplete() {
            this.f.compareAndSet(this, (Object) null);
            for (ObservablePublish.InnerDisposable<T> inner : (a[]) this.q.getAndSet(d)) {
                inner.child.onComplete();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean a(a<T> producer) {
            ObservablePublish.InnerDisposable<T>[] c2;
            ObservablePublish.InnerDisposable<T>[] u;
            do {
                c2 = (a[]) this.q.get();
                if (c2 == d) {
                    return false;
                }
                int len = c2.length;
                u = new a[(len + 1)];
                System.arraycopy(c2, 0, u, 0, len);
                u[len] = producer;
            } while (!this.q.compareAndSet(c2, u));
            return true;
        }

        /* access modifiers changed from: package-private */
        public void b(a<T> producer) {
            ObservablePublish.InnerDisposable<T>[] c2;
            ObservablePublish.InnerDisposable<T>[] u;
            do {
                c2 = (a[]) this.q.get();
                int len = c2.length;
                if (len != 0) {
                    int j = -1;
                    int i = 0;
                    while (true) {
                        if (i >= len) {
                            break;
                        } else if (c2[i].equals(producer)) {
                            j = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (j >= 0) {
                        if (len == 1) {
                            u = c;
                        } else {
                            ObservablePublish.InnerDisposable<T>[] u2 = new a[(len - 1)];
                            System.arraycopy(c2, 0, u2, 0, j);
                            System.arraycopy(c2, j + 1, u2, j, (len - j) - 1);
                            u = u2;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.q.compareAndSet(c2, u));
        }
    }

    /* compiled from: ObservablePublish */
    public static final class a<T> extends AtomicReference<Object> implements io.reactivex.disposables.b {
        private static final long serialVersionUID = -1100270633763673112L;
        final q<? super T> child;

        a(q<? super T> child2) {
            this.child = child2;
        }

        public boolean isDisposed() {
            return get() == this;
        }

        public void dispose() {
            Object o = getAndSet(this);
            if (o != null && o != this) {
                ((b) o).b(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void setParent(b<T> p) {
            if (!compareAndSet((Object) null, p)) {
                p.b(this);
            }
        }
    }

    /* compiled from: ObservablePublish */
    public static final class c<T> implements o<T> {
        private final AtomicReference<b<T>> c;

        c(AtomicReference<b<T>> curr) {
            this.c = curr;
        }

        public void a(q<? super T> child) {
            ObservablePublish.InnerDisposable<T> inner = new a<>(child);
            child.onSubscribe(inner);
            while (true) {
                ObservablePublish.PublishObserver<T> r = (b) this.c.get();
                if (r == null || r.isDisposed()) {
                    ObservablePublish.PublishObserver<T> u = new b<>(this.c);
                    if (!this.c.compareAndSet(r, u)) {
                        continue;
                    } else {
                        r = u;
                    }
                }
                if (r.a(inner)) {
                    inner.setParent(r);
                    return;
                }
            }
        }
    }
}
