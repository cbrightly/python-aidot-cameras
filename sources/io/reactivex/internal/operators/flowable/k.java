package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.l;
import io.reactivex.q;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableFromObservable */
public final class k<T> extends e<T> {
    private final l<T> d;

    public k(l<T> upstream) {
        this.d = upstream;
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> s) {
        this.d.a(new a(s));
    }

    /* compiled from: FlowableFromObservable */
    public static final class a<T> implements q<T>, c {
        final b<? super T> c;
        io.reactivex.disposables.b d;

        a(b<? super T> s) {
            this.c = s;
        }

        public void onComplete() {
            this.c.onComplete();
        }

        public void onError(Throwable e) {
            this.c.onError(e);
        }

        public void onNext(T value) {
            this.c.onNext(value);
        }

        public void onSubscribe(io.reactivex.disposables.b d2) {
            this.d = d2;
            this.c.onSubscribe(this);
        }

        public void cancel() {
            this.d.dispose();
        }

        public void request(long n) {
        }
    }
}
