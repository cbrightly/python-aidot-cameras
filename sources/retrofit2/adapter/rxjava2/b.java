package retrofit2.adapter.rxjava2;

import io.reactivex.exceptions.CompositeException;
import io.reactivex.l;
import io.reactivex.q;
import retrofit2.Call;
import retrofit2.adapter.rxjava2.CallEnqueueObservable;
import retrofit2.d;
import retrofit2.f;
import retrofit2.s;

/* compiled from: CallEnqueueObservable */
public final class b<T> extends l<s<T>> {
    private final d<T> c;

    b(d<T> originalCall) {
        this.c = originalCall;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super s<T>> observer) {
        Call<T> call = this.c.clone();
        CallEnqueueObservable.CallCallback<T> callback = new a<>(call, observer);
        observer.onSubscribe(callback);
        if (!callback.isDisposed()) {
            call.T(callback);
        }
    }

    /* compiled from: CallEnqueueObservable */
    public static final class a<T> implements io.reactivex.disposables.b, f<T> {
        private final d<?> c;
        private final q<? super s<T>> d;
        private volatile boolean f;
        boolean q = false;

        a(d<?> call, q<? super s<T>> observer) {
            this.c = call;
            this.d = observer;
        }

        public void b(d<T> dVar, s<T> response) {
            if (!this.f) {
                try {
                    this.d.onNext(response);
                    if (!this.f) {
                        this.q = true;
                        this.d.onComplete();
                    }
                } catch (Throwable inner) {
                    io.reactivex.exceptions.a.b(inner);
                    io.reactivex.plugins.a.q(new CompositeException(t, inner));
                }
            }
        }

        public void a(d<T> call, Throwable t) {
            if (!call.isCanceled()) {
                try {
                    this.d.onError(t);
                } catch (Throwable inner) {
                    io.reactivex.exceptions.a.b(inner);
                    io.reactivex.plugins.a.q(new CompositeException(t, inner));
                }
            }
        }

        public void dispose() {
            this.f = true;
            this.c.cancel();
        }

        public boolean isDisposed() {
            return this.f;
        }
    }
}
