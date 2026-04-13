package io.reactivex.processors;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.processors.PublishProcessor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.c;

/* compiled from: PublishProcessor */
public final class b<T> extends a<T> {
    static final a[] d = new a[0];
    static final a[] f = new a[0];
    final AtomicReference<a<T>[]> q = new AtomicReference<>(f);
    Throwable x;

    public static <T> b<T> Y() {
        return new b<>();
    }

    b() {
    }

    /* access modifiers changed from: protected */
    public void L(org.reactivestreams.b<? super T> t) {
        PublishProcessor.PublishSubscription<T> ps = new a<>(t, this);
        t.onSubscribe(ps);
        if (!X(ps)) {
            Throwable ex = this.x;
            if (ex != null) {
                t.onError(ex);
            } else {
                t.onComplete();
            }
        } else if (ps.isCancelled()) {
            Z(ps);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean X(a<T> ps) {
        PublishProcessor.PublishSubscription<T>[] a2;
        PublishProcessor.PublishSubscription<T>[] b;
        do {
            a2 = (a[]) this.q.get();
            if (a2 == d) {
                return false;
            }
            int n = a2.length;
            b = new a[(n + 1)];
            System.arraycopy(a2, 0, b, 0, n);
            b[n] = ps;
        } while (!this.q.compareAndSet(a2, b));
        return true;
    }

    /* access modifiers changed from: package-private */
    public void Z(a<T> ps) {
        PublishProcessor.PublishSubscription<T>[] a2;
        PublishProcessor.PublishSubscription<T>[] b;
        do {
            a2 = (a[]) this.q.get();
            if (a2 != d && a2 != f) {
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
                        b = f;
                    } else {
                        PublishProcessor.PublishSubscription<T>[] b2 = new a[(n - 1)];
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
        } while (!this.q.compareAndSet(a2, b));
    }

    public void onSubscribe(c s) {
        if (this.q.get() == d) {
            s.cancel();
        } else {
            s.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
        }
    }

    public void onNext(T t) {
        io.reactivex.internal.functions.b.e(t, "onNext called with null. Null values are generally not allowed in 2.x operators and sources.");
        for (PublishProcessor.PublishSubscription<T> s : (a[]) this.q.get()) {
            s.onNext(t);
        }
    }

    public void onError(Throwable t) {
        io.reactivex.internal.functions.b.e(t, "onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        a<T>[] aVarArr = this.q.get();
        a<T>[] aVarArr2 = d;
        if (aVarArr == aVarArr2) {
            io.reactivex.plugins.a.q(t);
            return;
        }
        this.x = t;
        for (PublishProcessor.PublishSubscription<T> s : (a[]) this.q.getAndSet(aVarArr2)) {
            s.onError(t);
        }
    }

    public void onComplete() {
        a<T>[] aVarArr = this.q.get();
        a<T>[] aVarArr2 = d;
        if (aVarArr != aVarArr2) {
            for (PublishProcessor.PublishSubscription<T> s : (a[]) this.q.getAndSet(aVarArr2)) {
                s.onComplete();
            }
        }
    }

    /* compiled from: PublishProcessor */
    public static final class a<T> extends AtomicLong implements c {
        private static final long serialVersionUID = 3562861878281475070L;
        final org.reactivestreams.b<? super T> downstream;
        final b<T> parent;

        a(org.reactivestreams.b<? super T> actual, b<T> parent2) {
            this.downstream = actual;
            this.parent = parent2;
        }

        public void onNext(T t) {
            long r = get();
            if (r != Long.MIN_VALUE) {
                if (r != 0) {
                    this.downstream.onNext(t);
                    io.reactivex.internal.util.c.e(this, 1);
                    return;
                }
                cancel();
                this.downstream.onError(new MissingBackpressureException("Could not emit value due to lack of requests"));
            }
        }

        public void onError(Throwable t) {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onError(t);
            } else {
                io.reactivex.plugins.a.q(t);
            }
        }

        public void onComplete() {
            if (get() != Long.MIN_VALUE) {
                this.downstream.onComplete();
            }
        }

        public void request(long n) {
            if (f.validate(n)) {
                io.reactivex.internal.util.c.b(this, n);
            }
        }

        public void cancel() {
            if (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.Z(this);
            }
        }

        public boolean isCancelled() {
            return get() == Long.MIN_VALUE;
        }

        /* access modifiers changed from: package-private */
        public boolean isFull() {
            return get() == 0;
        }
    }
}
