package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.h;
import io.reactivex.internal.disposables.g;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;

/* compiled from: FlowableTimeoutTimed */
public final class b0<T> extends a<T, T> {
    final long f;
    final TimeUnit q;
    final r x;
    final org.reactivestreams.a<? extends T> y;

    /* compiled from: FlowableTimeoutTimed */
    public interface d {
        void onTimeout(long j);
    }

    public b0(io.reactivex.e<T> source, long timeout, TimeUnit unit, r scheduler, org.reactivestreams.a<? extends T> other) {
        super(source);
        this.f = timeout;
        this.q = unit;
        this.x = scheduler;
        this.y = other;
    }

    /* access modifiers changed from: protected */
    public void L(org.reactivestreams.b<? super T> s) {
        if (this.y == null) {
            c cVar = new c(s, this.f, this.q, this.x.a());
            s.onSubscribe(cVar);
            cVar.startTimeout(0);
            this.d.K(cVar);
            return;
        }
        b bVar = new b(s, this.f, this.q, this.x.a(), this.y);
        s.onSubscribe(bVar);
        bVar.startTimeout(0);
        this.d.K(bVar);
    }

    /* compiled from: FlowableTimeoutTimed */
    public static final class c<T> extends AtomicLong implements h<T>, org.reactivestreams.c, d {
        private static final long serialVersionUID = 3764492702657003550L;
        final org.reactivestreams.b<? super T> downstream;
        final AtomicLong requested = new AtomicLong();
        final g task = new g();
        final long timeout;
        final TimeUnit unit;
        final AtomicReference<org.reactivestreams.c> upstream = new AtomicReference<>();
        final r.c worker;

        c(org.reactivestreams.b<? super T> actual, long timeout2, TimeUnit unit2, r.c worker2) {
            this.downstream = actual;
            this.timeout = timeout2;
            this.unit = unit2;
            this.worker = worker2;
        }

        public void onSubscribe(org.reactivestreams.c s) {
            f.deferredSetOnce(this.upstream, this.requested, s);
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
                f.cancel(this.upstream);
                this.downstream.onError(new TimeoutException(io.reactivex.internal.util.f.c(this.timeout, this.unit)));
                this.worker.dispose();
            }
        }

        public void request(long n) {
            f.deferredRequest(this.upstream, this.requested, n);
        }

        public void cancel() {
            f.cancel(this.upstream);
            this.worker.dispose();
        }
    }

    /* compiled from: FlowableTimeoutTimed */
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

    /* compiled from: FlowableTimeoutTimed */
    public static final class b<T> extends io.reactivex.internal.subscriptions.e implements h<T>, d {
        private static final long serialVersionUID = 3764492702657003550L;
        long consumed;
        final org.reactivestreams.b<? super T> downstream;
        org.reactivestreams.a<? extends T> fallback;
        final AtomicLong index = new AtomicLong();
        final g task = new g();
        final long timeout;
        final TimeUnit unit;
        final AtomicReference<org.reactivestreams.c> upstream = new AtomicReference<>();
        final r.c worker;

        b(org.reactivestreams.b<? super T> actual, long timeout2, TimeUnit unit2, r.c worker2, org.reactivestreams.a<? extends T> fallback2) {
            super(true);
            this.downstream = actual;
            this.timeout = timeout2;
            this.unit = unit2;
            this.worker = worker2;
            this.fallback = fallback2;
        }

        public void onSubscribe(org.reactivestreams.c s) {
            if (f.setOnce(this.upstream, s)) {
                setSubscription(s);
            }
        }

        public void onNext(T t) {
            long idx = this.index.get();
            if (idx != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE && this.index.compareAndSet(idx, idx + 1)) {
                ((io.reactivex.disposables.b) this.task.get()).dispose();
                this.consumed++;
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
                f.cancel(this.upstream);
                long c = this.consumed;
                if (c != 0) {
                    produced(c);
                }
                Publisher<? extends T> f = this.fallback;
                this.fallback = null;
                f.a(new a(this.downstream, this));
                this.worker.dispose();
            }
        }

        public void cancel() {
            super.cancel();
            this.worker.dispose();
        }
    }

    /* compiled from: FlowableTimeoutTimed */
    public static final class a<T> implements h<T> {
        final org.reactivestreams.b<? super T> c;
        final io.reactivex.internal.subscriptions.e d;

        a(org.reactivestreams.b<? super T> actual, io.reactivex.internal.subscriptions.e arbiter) {
            this.c = actual;
            this.d = arbiter;
        }

        public void onSubscribe(org.reactivestreams.c s) {
            this.d.setSubscription(s);
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
