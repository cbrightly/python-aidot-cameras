package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.h;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.d;
import org.reactivestreams.c;

/* compiled from: FlowableDoFinally */
public final class f<T> extends a<T, T> {
    final io.reactivex.functions.a f;

    public f(e<T> source, io.reactivex.functions.a onFinally) {
        super(source);
        this.f = onFinally;
    }

    /* access modifiers changed from: protected */
    public void L(org.reactivestreams.b<? super T> s) {
        if (s instanceof io.reactivex.internal.fuseable.a) {
            this.d.K(new a((io.reactivex.internal.fuseable.a) s, this.f));
        } else {
            this.d.K(new b(s, this.f));
        }
    }

    /* compiled from: FlowableDoFinally */
    public static final class b<T> extends io.reactivex.internal.subscriptions.a<T> implements h<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final org.reactivestreams.b<? super T> downstream;
        final io.reactivex.functions.a onFinally;
        d<T> qs;
        boolean syncFused;
        c upstream;

        b(org.reactivestreams.b<? super T> actual, io.reactivex.functions.a onFinally2) {
            this.downstream = actual;
            this.onFinally = onFinally2;
        }

        public void onSubscribe(c s) {
            if (io.reactivex.internal.subscriptions.f.validate(this.upstream, s)) {
                this.upstream = s;
                if (s instanceof d) {
                    this.qs = (d) s;
                }
                this.downstream.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        public void onError(Throwable t) {
            this.downstream.onError(t);
            runFinally();
        }

        public void onComplete() {
            this.downstream.onComplete();
            runFinally();
        }

        public void cancel() {
            this.upstream.cancel();
            runFinally();
        }

        public void request(long n) {
            this.upstream.request(n);
        }

        public int requestFusion(int mode) {
            QueueSubscription<T> qs2 = this.qs;
            boolean z = false;
            if (qs2 == null || (mode & 4) != 0) {
                return 0;
            }
            int m = qs2.requestFusion(mode);
            if (m != 0) {
                if (m == 1) {
                    z = true;
                }
                this.syncFused = z;
            }
            return m;
        }

        public void clear() {
            this.qs.clear();
        }

        public boolean isEmpty() {
            return this.qs.isEmpty();
        }

        public T poll() {
            T v = this.qs.poll();
            if (v == null && this.syncFused) {
                runFinally();
            }
            return v;
        }

        /* access modifiers changed from: package-private */
        public void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable ex) {
                    io.reactivex.exceptions.a.b(ex);
                    io.reactivex.plugins.a.q(ex);
                }
            }
        }
    }

    /* compiled from: FlowableDoFinally */
    public static final class a<T> extends io.reactivex.internal.subscriptions.a<T> implements io.reactivex.internal.fuseable.a<T> {
        private static final long serialVersionUID = 4109457741734051389L;
        final io.reactivex.internal.fuseable.a<? super T> downstream;
        final io.reactivex.functions.a onFinally;
        d<T> qs;
        boolean syncFused;
        c upstream;

        a(io.reactivex.internal.fuseable.a<? super T> actual, io.reactivex.functions.a onFinally2) {
            this.downstream = actual;
            this.onFinally = onFinally2;
        }

        public void onSubscribe(c s) {
            if (io.reactivex.internal.subscriptions.f.validate(this.upstream, s)) {
                this.upstream = s;
                if (s instanceof d) {
                    this.qs = (d) s;
                }
                this.downstream.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        public boolean tryOnNext(T t) {
            return this.downstream.tryOnNext(t);
        }

        public void onError(Throwable t) {
            this.downstream.onError(t);
            runFinally();
        }

        public void onComplete() {
            this.downstream.onComplete();
            runFinally();
        }

        public void cancel() {
            this.upstream.cancel();
            runFinally();
        }

        public void request(long n) {
            this.upstream.request(n);
        }

        public int requestFusion(int mode) {
            QueueSubscription<T> qs2 = this.qs;
            boolean z = false;
            if (qs2 == null || (mode & 4) != 0) {
                return 0;
            }
            int m = qs2.requestFusion(mode);
            if (m != 0) {
                if (m == 1) {
                    z = true;
                }
                this.syncFused = z;
            }
            return m;
        }

        public void clear() {
            this.qs.clear();
        }

        public boolean isEmpty() {
            return this.qs.isEmpty();
        }

        public T poll() {
            T v = this.qs.poll();
            if (v == null && this.syncFused) {
                runFinally();
            }
            return v;
        }

        /* access modifiers changed from: package-private */
        public void runFinally() {
            if (compareAndSet(0, 1)) {
                try {
                    this.onFinally.run();
                } catch (Throwable ex) {
                    io.reactivex.exceptions.a.b(ex);
                    io.reactivex.plugins.a.q(ex);
                }
            }
        }
    }
}
