package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.f;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.fuseable.g;
import io.reactivex.internal.operators.observable.ObservableConcatMap;
import io.reactivex.internal.util.e;
import io.reactivex.o;
import io.reactivex.q;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableConcatMap */
public final class c<T, U> extends a<T, U> {
    final f<? super T, ? extends o<? extends U>> d;
    final int f;
    final e q;

    public c(o<T> source, f<? super T, ? extends o<? extends U>> mapper, int bufferSize, e delayErrors) {
        super(source);
        this.d = mapper;
        this.q = delayErrors;
        this.f = Math.max(8, bufferSize);
    }

    public void a0(q<? super U> observer) {
        if (!b0.b(this.c, observer, this.d)) {
            if (this.q == e.IMMEDIATE) {
                this.c.a(new b(new io.reactivex.observers.a<>(observer), this.d, this.f));
            } else {
                this.c.a(new a(observer, this.d, this.f, this.q == e.END));
            }
        }
    }

    /* compiled from: ObservableConcatMap */
    public static final class b<T, U> extends AtomicInteger implements q<T>, io.reactivex.disposables.b {
        private static final long serialVersionUID = 8828587559905699186L;
        volatile boolean active;
        final int bufferSize;
        volatile boolean disposed;
        volatile boolean done;
        final q<? super U> downstream;
        int fusionMode;
        final a<U> inner;
        final f<? super T, ? extends o<? extends U>> mapper;
        g<T> queue;
        io.reactivex.disposables.b upstream;

        b(q<? super U> actual, f<? super T, ? extends o<? extends U>> mapper2, int bufferSize2) {
            this.downstream = actual;
            this.mapper = mapper2;
            this.bufferSize = bufferSize2;
            this.inner = new a<>(actual, this);
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            if (io.reactivex.internal.disposables.c.validate(this.upstream, d)) {
                this.upstream = d;
                if (d instanceof io.reactivex.internal.fuseable.b) {
                    QueueDisposable<T> qd = (io.reactivex.internal.fuseable.b) d;
                    int m = qd.requestFusion(3);
                    if (m == 1) {
                        this.fusionMode = m;
                        this.queue = qd;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        drain();
                        return;
                    } else if (m == 2) {
                        this.fusionMode = m;
                        this.queue = qd;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new io.reactivex.internal.queue.c(this.bufferSize);
                this.downstream.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.fusionMode == 0) {
                    this.queue.offer(t);
                }
                drain();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                io.reactivex.plugins.a.q(t);
                return;
            }
            this.done = true;
            dispose();
            this.downstream.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                drain();
            }
        }

        /* access modifiers changed from: package-private */
        public void innerComplete() {
            this.active = false;
            drain();
        }

        public boolean isDisposed() {
            return this.disposed;
        }

        public void dispose() {
            this.disposed = true;
            this.inner.dispose();
            this.upstream.dispose();
            if (getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                while (!this.disposed) {
                    if (!this.active) {
                        boolean d = this.done;
                        try {
                            T t = this.queue.poll();
                            boolean empty = t == null;
                            if (d && empty) {
                                this.disposed = true;
                                this.downstream.onComplete();
                                return;
                            } else if (!empty) {
                                try {
                                    ObservableSource<? extends U> o = (o) io.reactivex.internal.functions.b.e(this.mapper.apply(t), "The mapper returned a null ObservableSource");
                                    this.active = true;
                                    o.a(this.inner);
                                } catch (Throwable ex) {
                                    io.reactivex.exceptions.a.b(ex);
                                    dispose();
                                    this.queue.clear();
                                    this.downstream.onError(ex);
                                    return;
                                }
                            }
                        } catch (Throwable ex2) {
                            io.reactivex.exceptions.a.b(ex2);
                            dispose();
                            this.queue.clear();
                            this.downstream.onError(ex2);
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                this.queue.clear();
            }
        }

        /* compiled from: ObservableConcatMap */
        public static final class a<U> extends AtomicReference<io.reactivex.disposables.b> implements q<U> {
            private static final long serialVersionUID = -7449079488798789337L;
            final q<? super U> downstream;
            final b<?, ?> parent;

            a(q<? super U> actual, b<?, ?> parent2) {
                this.downstream = actual;
                this.parent = parent2;
            }

            public void onSubscribe(io.reactivex.disposables.b d) {
                io.reactivex.internal.disposables.c.replace(this, d);
            }

            public void onNext(U t) {
                this.downstream.onNext(t);
            }

            public void onError(Throwable t) {
                this.parent.dispose();
                this.downstream.onError(t);
            }

            public void onComplete() {
                this.parent.innerComplete();
            }

            /* access modifiers changed from: package-private */
            public void dispose() {
                io.reactivex.internal.disposables.c.dispose(this);
            }
        }
    }

    /* compiled from: ObservableConcatMap */
    public static final class a<T, R> extends AtomicInteger implements q<T>, io.reactivex.disposables.b {
        private static final long serialVersionUID = -6951100001833242599L;
        volatile boolean active;
        final int bufferSize;
        volatile boolean cancelled;
        volatile boolean done;
        final q<? super R> downstream;
        final io.reactivex.internal.util.b error = new io.reactivex.internal.util.b();
        final f<? super T, ? extends o<? extends R>> mapper;
        final C0303a<R> observer;
        g<T> queue;
        int sourceMode;
        final boolean tillTheEnd;
        io.reactivex.disposables.b upstream;

        a(q<? super R> actual, f<? super T, ? extends o<? extends R>> mapper2, int bufferSize2, boolean tillTheEnd2) {
            this.downstream = actual;
            this.mapper = mapper2;
            this.bufferSize = bufferSize2;
            this.tillTheEnd = tillTheEnd2;
            this.observer = new C0303a<>(actual, this);
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            if (io.reactivex.internal.disposables.c.validate(this.upstream, d)) {
                this.upstream = d;
                if (d instanceof io.reactivex.internal.fuseable.b) {
                    QueueDisposable<T> qd = (io.reactivex.internal.fuseable.b) d;
                    int m = qd.requestFusion(3);
                    if (m == 1) {
                        this.sourceMode = m;
                        this.queue = qd;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        drain();
                        return;
                    } else if (m == 2) {
                        this.sourceMode = m;
                        this.queue = qd;
                        this.downstream.onSubscribe(this);
                        return;
                    }
                }
                this.queue = new io.reactivex.internal.queue.c(this.bufferSize);
                this.downstream.onSubscribe(this);
            }
        }

        public void onNext(T value) {
            if (this.sourceMode == 0) {
                this.queue.offer(value);
            }
            drain();
        }

        public void onError(Throwable e) {
            if (this.error.addThrowable(e)) {
                this.done = true;
                drain();
                return;
            }
            io.reactivex.plugins.a.q(e);
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        public void dispose() {
            this.cancelled = true;
            this.upstream.dispose();
            this.observer.dispose();
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                Observer<? super R> actual = this.downstream;
                SimpleQueue<T> queue2 = this.queue;
                io.reactivex.internal.util.b error2 = this.error;
                while (true) {
                    if (!this.active) {
                        if (this.cancelled) {
                            queue2.clear();
                            return;
                        } else if (this.tillTheEnd || ((Throwable) error2.get()) == null) {
                            boolean d = this.done;
                            try {
                                T v = queue2.poll();
                                boolean empty = v == null;
                                if (d && empty) {
                                    this.cancelled = true;
                                    Throwable ex = error2.terminate();
                                    if (ex != null) {
                                        actual.onError(ex);
                                        return;
                                    } else {
                                        actual.onComplete();
                                        return;
                                    }
                                } else if (!empty) {
                                    try {
                                        o oVar = (o) io.reactivex.internal.functions.b.e(this.mapper.apply(v), "The mapper returned a null ObservableSource");
                                        if (oVar instanceof Callable) {
                                            try {
                                                R w = ((Callable) oVar).call();
                                                if (w != null && !this.cancelled) {
                                                    actual.onNext(w);
                                                }
                                            } catch (Throwable ex2) {
                                                io.reactivex.exceptions.a.b(ex2);
                                                error2.addThrowable(ex2);
                                            }
                                        } else {
                                            this.active = true;
                                            oVar.a(this.observer);
                                        }
                                    } catch (Throwable ex3) {
                                        io.reactivex.exceptions.a.b(ex3);
                                        this.cancelled = true;
                                        this.upstream.dispose();
                                        queue2.clear();
                                        error2.addThrowable(ex3);
                                        actual.onError(error2.terminate());
                                        return;
                                    }
                                }
                            } catch (Throwable ex4) {
                                io.reactivex.exceptions.a.b(ex4);
                                this.cancelled = true;
                                this.upstream.dispose();
                                error2.addThrowable(ex4);
                                actual.onError(error2.terminate());
                                return;
                            }
                        } else {
                            queue2.clear();
                            this.cancelled = true;
                            actual.onError(error2.terminate());
                            return;
                        }
                    }
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.c$a$a  reason: collision with other inner class name */
        /* compiled from: ObservableConcatMap */
        public static final class C0303a<R> extends AtomicReference<io.reactivex.disposables.b> implements q<R> {
            private static final long serialVersionUID = 2620149119579502636L;
            final q<? super R> downstream;
            final a<?, R> parent;

            C0303a(q<? super R> actual, a<?, R> parent2) {
                this.downstream = actual;
                this.parent = parent2;
            }

            public void onSubscribe(io.reactivex.disposables.b d) {
                io.reactivex.internal.disposables.c.replace(this, d);
            }

            public void onNext(R value) {
                this.downstream.onNext(value);
            }

            public void onError(Throwable e) {
                ObservableConcatMap.ConcatMapDelayErrorObserver<?, R> p = this.parent;
                if (p.error.addThrowable(e)) {
                    if (!p.tillTheEnd) {
                        p.upstream.dispose();
                    }
                    p.active = false;
                    p.drain();
                    return;
                }
                io.reactivex.plugins.a.q(e);
            }

            public void onComplete() {
                ObservableConcatMap.ConcatMapDelayErrorObserver<?, R> p = this.parent;
                p.active = false;
                p.drain();
            }

            /* access modifiers changed from: package-private */
            public void dispose() {
                io.reactivex.internal.disposables.c.dispose(this);
            }
        }
    }
}
