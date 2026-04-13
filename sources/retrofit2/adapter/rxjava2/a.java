package retrofit2.adapter.rxjava2;

import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.l;
import io.reactivex.q;
import retrofit2.s;

/* compiled from: BodyObservable */
public final class a<T> extends l<T> {
    private final l<s<T>> c;

    a(l<s<T>> upstream) {
        this.c = upstream;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        this.c.a(new C0488a(observer));
    }

    /* renamed from: retrofit2.adapter.rxjava2.a$a  reason: collision with other inner class name */
    /* compiled from: BodyObservable */
    public static class C0488a<R> implements q<s<R>> {
        private final q<? super R> c;
        private boolean d;

        C0488a(q<? super R> observer) {
            this.c = observer;
        }

        public void onSubscribe(b disposable) {
            this.c.onSubscribe(disposable);
        }

        /* renamed from: a */
        public void onNext(s<R> response) {
            if (response.e()) {
                this.c.onNext(response.a());
                return;
            }
            this.d = true;
            Throwable t = new HttpException(response);
            try {
                this.c.onError(t);
            } catch (Throwable inner) {
                io.reactivex.exceptions.a.b(inner);
                io.reactivex.plugins.a.q(new CompositeException(t, inner));
            }
        }

        public void onComplete() {
            if (!this.d) {
                this.c.onComplete();
            }
        }

        public void onError(Throwable throwable) {
            if (!this.d) {
                this.c.onError(throwable);
                return;
            }
            Throwable broken = new AssertionError("This should never happen! Report as a bug with the full stacktrace.");
            broken.initCause(throwable);
            io.reactivex.plugins.a.q(broken);
        }
    }
}
