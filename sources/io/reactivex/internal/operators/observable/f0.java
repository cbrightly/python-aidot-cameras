package io.reactivex.internal.operators.observable;

import io.reactivex.internal.disposables.c;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import io.reactivex.o;
import io.reactivex.q;
import io.reactivex.r;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableSubscribeOn */
public final class f0<T> extends a<T, T> {
    final r d;

    public f0(o<T> source, r scheduler) {
        super(source);
        this.d = scheduler;
    }

    public void a0(q<? super T> observer) {
        ObservableSubscribeOn.SubscribeOnObserver<T> parent = new a<>(observer);
        observer.onSubscribe(parent);
        parent.setDisposable(this.d.b(new b(parent)));
    }

    /* compiled from: ObservableSubscribeOn */
    public static final class a<T> extends AtomicReference<io.reactivex.disposables.b> implements q<T>, io.reactivex.disposables.b {
        private static final long serialVersionUID = 8094547886072529208L;
        final q<? super T> downstream;
        final AtomicReference<io.reactivex.disposables.b> upstream = new AtomicReference<>();

        a(q<? super T> downstream2) {
            this.downstream = downstream2;
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            c.setOnce(this.upstream, d);
        }

        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        public void onError(Throwable t) {
            this.downstream.onError(t);
        }

        public void onComplete() {
            this.downstream.onComplete();
        }

        public void dispose() {
            c.dispose(this.upstream);
            c.dispose(this);
        }

        public boolean isDisposed() {
            return c.isDisposed((io.reactivex.disposables.b) get());
        }

        /* access modifiers changed from: package-private */
        public void setDisposable(io.reactivex.disposables.b d) {
            c.setOnce(this, d);
        }
    }

    /* compiled from: ObservableSubscribeOn */
    public final class b implements Runnable {
        private final a<T> c;

        b(a<T> parent) {
            this.c = parent;
        }

        public void run() {
            f0.this.c.a(this.c);
        }
    }
}
