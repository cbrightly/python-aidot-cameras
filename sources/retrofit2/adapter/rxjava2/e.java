package retrofit2.adapter.rxjava2;

import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.l;
import io.reactivex.q;
import retrofit2.s;

/* compiled from: ResultObservable */
public final class e<T> extends l<d<T>> {
    private final l<s<T>> c;

    e(l<s<T>> upstream) {
        this.c = upstream;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super d<T>> observer) {
        this.c.a(new a(observer));
    }

    /* compiled from: ResultObservable */
    public static class a<R> implements q<s<R>> {
        private final q<? super d<R>> c;

        a(q<? super d<R>> observer) {
            this.c = observer;
        }

        public void onSubscribe(b disposable) {
            this.c.onSubscribe(disposable);
        }

        /* renamed from: a */
        public void onNext(s<R> response) {
            this.c.onNext(d.b(response));
        }

        public void onError(Throwable throwable) {
            try {
                this.c.onNext(d.a(throwable));
                this.c.onComplete();
            } catch (Throwable inner) {
                io.reactivex.exceptions.a.b(inner);
                io.reactivex.plugins.a.q(new CompositeException(t, inner));
            }
        }

        public void onComplete() {
            this.c.onComplete();
        }
    }
}
