package io.reactivex.internal.operators.observable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.ObservableSource;
import io.reactivex.internal.disposables.g;
import io.reactivex.internal.util.f;
import io.reactivex.l;
import io.reactivex.o;
import io.reactivex.q;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableTimeoutTimed */
public final class i0<T> extends a<T, T> {
    final long d;
    final TimeUnit f;
    final r q;
    final o<? extends T> x;

    /* compiled from: ObservableTimeoutTimed */
    public interface d {
        void onTimeout(long j);
    }

    public i0(l<T> source, long timeout, TimeUnit unit, r scheduler, o<? extends T> other) {
        super(source);
        this.d = timeout;
        this.f = unit;
        this.q = scheduler;
        this.x = other;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        if (this.x == null) {
            c cVar = new c(observer, this.d, this.f, this.q.a());
            observer.onSubscribe(cVar);
            cVar.startTimeout(0);
            this.c.a(cVar);
            return;
        }
        b bVar = new b(observer, this.d, this.f, this.q.a(), this.x);
        observer.onSubscribe(bVar);
        bVar.startTimeout(0);
        this.c.a(bVar);
    }

    /* compiled from: ObservableTimeoutTimed */
    public static final class c<T> extends AtomicLong implements q<T>, io.reactivex.disposables.b, d {
        private static final long serialVersionUID = 3764492702657003550L;
        final q<? super T> downstream;
        final g task = new g();
        final long timeout;
        final TimeUnit unit;
        final AtomicReference<io.reactivex.disposables.b> upstream = new AtomicReference<>();
        final r.c worker;

        c(q<? super T> actual, long timeout2, TimeUnit unit2, r.c worker2) {
            this.downstream = actual;
            this.timeout = timeout2;
            this.unit = unit2;
            this.worker = worker2;
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            io.reactivex.internal.disposables.c.setOnce(this.upstream, d);
        }

        public void onNext(T t) {
            long idx = get();
            if (idx != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE && compareAndSet(idx, idx + 1)) {
                ((io.reactivex.disposables.b) this.task.get()).dispose();
                this.downstream.onNext(t);
                startTimeout(1 + idx);
            }
        }

        /* access modifiers changed from: package-private */
        public void startTimeout(long nextIndex) {
            this.task.replace(this.worker.c(new e(nextIndex, this), this.timeout, this.unit));
        }

        public void onError(Throwable t) {
            if (getAndSet(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                this.task.dispose();
                this.downstream.onError(t);
                this.worker.dispose();
                return;
            }
            io.reactivex.plugins.a.q(t);
        }

        public void onComplete() {
            if (getAndSet(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                this.task.dispose();
                this.downstream.onComplete();
                this.worker.dispose();
            }
        }

        public void onTimeout(long idx) {
            if (compareAndSet(idx, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE)) {
                io.reactivex.internal.disposables.c.dispose(this.upstream);
                this.downstream.onError(new TimeoutException(f.c(this.timeout, this.unit)));
                this.worker.dispose();
            }
        }

        public void dispose() {
            io.reactivex.internal.disposables.c.dispose(this.upstream);
            this.worker.dispose();
        }

        public boolean isDisposed() {
            return io.reactivex.internal.disposables.c.isDisposed(this.upstream.get());
        }
    }

    /* compiled from: ObservableTimeoutTimed */
    public static final class e implements Runnable {
        final d c;
        final long d;

        e(long idx, d parent) {
            this.d = idx;
            this.c = parent;
        }

        public void run() {
            this.c.onTimeout(this.d);
        }
    }

    /* compiled from: ObservableTimeoutTimed */
    public static final class b<T> extends AtomicReference<io.reactivex.disposables.b> implements q<T>, io.reactivex.disposables.b, d {
        private static final long serialVersionUID = 3764492702657003550L;
        final q<? super T> downstream;
        o<? extends T> fallback;
        final AtomicLong index = new AtomicLong();
        final g task = new g();
        final long timeout;
        final TimeUnit unit;
        final AtomicReference<io.reactivex.disposables.b> upstream = new AtomicReference<>();
        final r.c worker;

        b(q<? super T> actual, long timeout2, TimeUnit unit2, r.c worker2, o<? extends T> fallback2) {
            this.downstream = actual;
            this.timeout = timeout2;
            this.unit = unit2;
            this.worker = worker2;
            this.fallback = fallback2;
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            io.reactivex.internal.disposables.c.setOnce(this.upstream, d);
        }

        public void onNext(T t) {
            long idx = this.index.get();
            if (idx != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE && this.index.compareAndSet(idx, idx + 1)) {
                ((io.reactivex.disposables.b) this.task.get()).dispose();
                this.downstream.onNext(t);
                startTimeout(1 + idx);
            }
        }

        /* access modifiers changed from: package-private */
        public void startTimeout(long nextIndex) {
            this.task.replace(this.worker.c(new e(nextIndex, this), this.timeout, this.unit));
        }

        public void onError(Throwable t) {
            if (this.index.getAndSet(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                this.task.dispose();
                this.downstream.onError(t);
                this.worker.dispose();
                return;
            }
            io.reactivex.plugins.a.q(t);
        }

        public void onComplete() {
            if (this.index.getAndSet(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                this.task.dispose();
                this.downstream.onComplete();
                this.worker.dispose();
            }
        }

        public void onTimeout(long idx) {
            if (this.index.compareAndSet(idx, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE)) {
                io.reactivex.internal.disposables.c.dispose(this.upstream);
                ObservableSource<? extends T> f = this.fallback;
                this.fallback = null;
                f.a(new a(this.downstream, this));
                this.worker.dispose();
            }
        }

        public void dispose() {
            io.reactivex.internal.disposables.c.dispose(this.upstream);
            io.reactivex.internal.disposables.c.dispose(this);
            this.worker.dispose();
        }

        public boolean isDisposed() {
            return io.reactivex.internal.disposables.c.isDisposed((io.reactivex.disposables.b) get());
        }
    }

    /* compiled from: ObservableTimeoutTimed */
    public static final class a<T> implements q<T> {
        final q<? super T> c;
        final AtomicReference<io.reactivex.disposables.b> d;

        a(q<? super T> actual, AtomicReference<io.reactivex.disposables.b> arbiter) {
            this.c = actual;
            this.d = arbiter;
        }

        public void onSubscribe(io.reactivex.disposables.b d2) {
            io.reactivex.internal.disposables.c.replace(this.d, d2);
        }

        public void onNext(T t) {
            this.c.onNext(t);
        }

        public void onError(Throwable t) {
            this.c.onError(t);
        }

        public void onComplete() {
            this.c.onComplete();
        }
    }
}
