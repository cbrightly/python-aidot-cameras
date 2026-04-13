package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.e;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.h;
import io.reactivex.internal.operators.flowable.FlowableDebounceTimed;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.r;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: FlowableDebounceTimed */
public final class c<T> extends a<T, T> {
    final long f;
    final TimeUnit q;
    final r x;

    public c(e<T> source, long timeout, TimeUnit unit, r scheduler) {
        super(source);
        this.f = timeout;
        this.q = unit;
        this.x = scheduler;
    }

    /* access modifiers changed from: protected */
    public void L(org.reactivestreams.b<? super T> s) {
        this.d.K(new b(new io.reactivex.subscribers.b(s), this.f, this.q, this.x.a()));
    }

    /* compiled from: FlowableDebounceTimed */
    public static final class b<T> extends AtomicLong implements h<T>, org.reactivestreams.c {
        private static final long serialVersionUID = -9102637559663639004L;
        boolean done;
        final org.reactivestreams.b<? super T> downstream;
        volatile long index;
        final long timeout;
        io.reactivex.disposables.b timer;
        final TimeUnit unit;
        org.reactivestreams.c upstream;
        final r.c worker;

        b(org.reactivestreams.b<? super T> actual, long timeout2, TimeUnit unit2, r.c worker2) {
            this.downstream = actual;
            this.timeout = timeout2;
            this.unit = unit2;
            this.worker = worker2;
        }

        public void onSubscribe(org.reactivestreams.c s) {
            if (f.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
                s.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long idx = this.index + 1;
                this.index = idx;
                io.reactivex.disposables.b d = this.timer;
                if (d != null) {
                    d.dispose();
                }
                FlowableDebounceTimed.DebounceEmitter<T> de = new a<>(t, idx, this);
                this.timer = de;
                de.setResource(this.worker.c(de, this.timeout, this.unit));
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                io.reactivex.plugins.a.q(t);
                return;
            }
            this.done = true;
            io.reactivex.disposables.b d = this.timer;
            if (d != null) {
                d.dispose();
            }
            this.downstream.onError(t);
            this.worker.dispose();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                FlowableDebounceTimed.DebounceEmitter<T> d = this.timer;
                if (d != null) {
                    d.dispose();
                }
                FlowableDebounceTimed.DebounceEmitter<T> de = (a) d;
                if (de != null) {
                    de.emit();
                }
                this.downstream.onComplete();
                this.worker.dispose();
            }
        }

        public void request(long n) {
            if (f.validate(n)) {
                io.reactivex.internal.util.c.a(this, n);
            }
        }

        public void cancel() {
            this.upstream.cancel();
            this.worker.dispose();
        }

        /* access modifiers changed from: package-private */
        public void emit(long idx, T t, a<T> emitter) {
            if (idx != this.index) {
                return;
            }
            if (get() != 0) {
                this.downstream.onNext(t);
                io.reactivex.internal.util.c.d(this, 1);
                emitter.dispose();
                return;
            }
            cancel();
            this.downstream.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
        }
    }

    /* compiled from: FlowableDebounceTimed */
    public static final class a<T> extends AtomicReference<io.reactivex.disposables.b> implements Runnable, io.reactivex.disposables.b {
        private static final long serialVersionUID = 6812032969491025141L;
        final long idx;
        final AtomicBoolean once = new AtomicBoolean();
        final b<T> parent;
        final T value;

        a(T value2, long idx2, b<T> parent2) {
            this.value = value2;
            this.idx = idx2;
            this.parent = parent2;
        }

        public void run() {
            emit();
        }

        /* access modifiers changed from: package-private */
        public void emit() {
            if (this.once.compareAndSet(false, true)) {
                this.parent.emit(this.idx, this.value, this);
            }
        }

        public void dispose() {
            io.reactivex.internal.disposables.c.dispose(this);
        }

        public boolean isDisposed() {
            return get() == io.reactivex.internal.disposables.c.DISPOSED;
        }

        public void setResource(io.reactivex.disposables.b d) {
            io.reactivex.internal.disposables.c.replace(this, d);
        }
    }
}
