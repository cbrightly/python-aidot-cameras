package io.reactivex.internal.operators.observable;

import io.reactivex.internal.functions.b;
import io.reactivex.internal.observers.c;
import io.reactivex.internal.operators.observable.ObservableFromArray;
import io.reactivex.l;
import io.reactivex.q;

/* compiled from: ObservableFromArray */
public final class k<T> extends l<T> {
    final T[] c;

    public k(T[] array) {
        this.c = array;
    }

    public void a0(q<? super T> observer) {
        ObservableFromArray.FromArrayDisposable<T> d = new a<>(observer, this.c);
        observer.onSubscribe(d);
        if (!d.q) {
            d.a();
        }
    }

    /* compiled from: ObservableFromArray */
    public static final class a<T> extends c<T> {
        final q<? super T> c;
        final T[] d;
        int f;
        boolean q;
        volatile boolean x;

        a(q<? super T> actual, T[] array) {
            this.c = actual;
            this.d = array;
        }

        public int requestFusion(int mode) {
            if ((mode & 1) == 0) {
                return 0;
            }
            this.q = true;
            return 1;
        }

        public T poll() {
            int i = this.f;
            T[] a = this.d;
            if (i == a.length) {
                return null;
            }
            this.f = i + 1;
            return b.e(a[i], "The array element is null");
        }

        public boolean isEmpty() {
            return this.f == this.d.length;
        }

        public void clear() {
            this.f = this.d.length;
        }

        public void dispose() {
            this.x = true;
        }

        public boolean isDisposed() {
            return this.x;
        }

        /* access modifiers changed from: package-private */
        public void a() {
            T[] a = this.d;
            int n = a.length;
            for (int i = 0; i < n && !isDisposed(); i++) {
                T value = a[i];
                if (value == null) {
                    q<? super T> qVar = this.c;
                    qVar.onError(new NullPointerException("The element at index " + i + " is null"));
                    return;
                }
                this.c.onNext(value);
            }
            if (isDisposed() == 0) {
                this.c.onComplete();
            }
        }
    }
}
