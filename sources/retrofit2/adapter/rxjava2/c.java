package retrofit2.adapter.rxjava2;

import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.l;
import io.reactivex.q;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.d;
import retrofit2.s;

/* compiled from: CallExecuteObservable */
public final class c<T> extends l<s<T>> {
    private final d<T> c;

    c(d<T> originalCall) {
        this.c = originalCall;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super s<T>> observer) {
        Call<T> call = this.c.clone();
        a disposable = new a(call);
        observer.onSubscribe(disposable);
        if (!disposable.isDisposed()) {
            try {
                Response<T> response = call.execute();
                if (!disposable.isDisposed()) {
                    observer.onNext(response);
                }
                if (!disposable.isDisposed()) {
                    observer.onComplete();
                }
            } catch (Throwable inner) {
                io.reactivex.exceptions.a.b(inner);
                io.reactivex.plugins.a.q(new CompositeException(t, inner));
            }
        }
    }

    /* compiled from: CallExecuteObservable */
    public static final class a implements b {
        private final d<?> c;
        private volatile boolean d;

        a(d<?> call) {
            this.c = call;
        }

        public void dispose() {
            this.d = true;
            this.c.cancel();
        }

        public boolean isDisposed() {
            return this.d;
        }
    }
}
