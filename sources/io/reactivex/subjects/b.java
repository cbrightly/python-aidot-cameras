package io.reactivex.subjects;

import io.reactivex.q;
import io.reactivex.subjects.PublishSubject;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: PublishSubject */
public final class b<T> extends d<T> {
    static final a[] c = new a[0];
    static final a[] d = new a[0];
    final AtomicReference<a<T>[]> f = new AtomicReference<>(d);
    Throwable q;

    public static <T> b<T> p0() {
        return new b<>();
    }

    b() {
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> t) {
        PublishSubject.PublishDisposable<T> ps = new a<>(t, this);
        t.onSubscribe(ps);
        if (!o0(ps)) {
            Throwable ex = this.q;
            if (ex != null) {
                t.onError(ex);
            } else {
                t.onComplete();
            }
        } else if (ps.isDisposed()) {
            q0(ps);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean o0(a<T> ps) {
        PublishSubject.PublishDisposable<T>[] a2;
        PublishSubject.PublishDisposable<T>[] b;
        do {
            a2 = (a[]) this.f.get();
            if (a2 == c) {
                return false;
            }
            int n = a2.length;
            b = new a[(n + 1)];
            System.arraycopy(a2, 0, b, 0, n);
            b[n] = ps;
        } while (!this.f.compareAndSet(a2, b));
        return true;
    }

    /* access modifiers changed from: package-private */
    public void q0(a<T> ps) {
        PublishSubject.PublishDisposable<T>[] a2;
        PublishSubject.PublishDisposable<T>[] b;
        do {
            a2 = (a[]) this.f.get();
            if (a2 != c && a2 != d) {
                int n = a2.length;
                int j = -1;
                int i = 0;
                while (true) {
                    if (i >= n) {
                        break;
                    } else if (a2[i] == ps) {
                        j = i;
                        break;
                    } else {
                        i++;
                    }
                }
                if (j >= 0) {
                    if (n == 1) {
                        b = d;
                    } else {
                        PublishSubject.PublishDisposable<T>[] b2 = new a[(n - 1)];
                        System.arraycopy(a2, 0, b2, 0, j);
                        System.arraycopy(a2, j + 1, b2, j, (n - j) - 1);
                        b = b2;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.f.compareAndSet(a2, b));
    }

    public void onSubscribe(io.reactivex.disposables.b d2) {
        if (this.f.get() == c) {
            d2.dispose();
        }
    }

    public void onNext(T t) {
        io.reactivex.internal.functions.b.e(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        for (PublishSubject.PublishDisposable<T> pd : (a[]) this.f.get()) {
            pd.onNext(t);
        }
    }

    public void onError(Throwable t) {
        io.reactivex.internal.functions.b.e(t, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        a<T>[] aVarArr = this.f.get();
        a<T>[] aVarArr2 = c;
        if (aVarArr == aVarArr2) {
            io.reactivex.plugins.a.q(t);
            return;
        }
        this.q = t;
        for (PublishSubject.PublishDisposable<T> pd : (a[]) this.f.getAndSet(aVarArr2)) {
            pd.onError(t);
        }
    }

    public void onComplete() {
        a<T>[] aVarArr = this.f.get();
        a<T>[] aVarArr2 = c;
        if (aVarArr != aVarArr2) {
            for (PublishSubject.PublishDisposable<T> pd : (a[]) this.f.getAndSet(aVarArr2)) {
                pd.onComplete();
            }
        }
    }

    /* compiled from: PublishSubject */
    public static final class a<T> extends AtomicBoolean implements io.reactivex.disposables.b {
        private static final long serialVersionUID = 3562861878281475070L;
        final q<? super T> downstream;
        final b<T> parent;

        a(q<? super T> actual, b<T> parent2) {
            this.downstream = actual;
            this.parent = parent2;
        }

        public void onNext(T t) {
            if (!get()) {
                this.downstream.onNext(t);
            }
        }

        public void onError(Throwable t) {
            if (get()) {
                io.reactivex.plugins.a.q(t);
            } else {
                this.downstream.onError(t);
            }
        }

        public void onComplete() {
            if (!get()) {
                this.downstream.onComplete();
            }
        }

        public void dispose() {
            if (compareAndSet(false, true)) {
                this.parent.q0(this);
            }
        }

        public boolean isDisposed() {
            return get();
        }
    }
}
