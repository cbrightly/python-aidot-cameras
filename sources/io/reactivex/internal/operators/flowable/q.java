package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.e;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.h;
import io.reactivex.internal.fuseable.ConditionalSubscriber;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.fuseable.d;
import io.reactivex.internal.fuseable.g;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.r;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;

/* compiled from: FlowableObserveOn */
public final class q<T> extends a<T, T> {
    final r f;
    final boolean q;
    final int x;

    public q(e<T> source, r scheduler, boolean delayError, int prefetch) {
        super(source);
        this.f = scheduler;
        this.q = delayError;
        this.x = prefetch;
    }

    public void L(org.reactivestreams.b<? super T> s) {
        r.c worker = this.f.a();
        if (s instanceof io.reactivex.internal.fuseable.a) {
            this.d.K(new b((io.reactivex.internal.fuseable.a) s, worker, this.q, this.x));
        } else {
            this.d.K(new c(s, worker, this.q, this.x));
        }
    }

    /* compiled from: FlowableObserveOn */
    public static abstract class a<T> extends io.reactivex.internal.subscriptions.a<T> implements h<T>, Runnable {
        private static final long serialVersionUID = -8241002408341274697L;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final int limit;
        boolean outputFused;
        final int prefetch;
        long produced;
        g<T> queue;
        final AtomicLong requested = new AtomicLong();
        int sourceMode;
        org.reactivestreams.c upstream;
        final r.c worker;

        public abstract /* synthetic */ void onSubscribe(org.reactivestreams.c cVar);

        public abstract /* synthetic */ T poll();

        /* access modifiers changed from: package-private */
        public abstract void runAsync();

        /* access modifiers changed from: package-private */
        public abstract void runBackfused();

        /* access modifiers changed from: package-private */
        public abstract void runSync();

        a(r.c worker2, boolean delayError2, int prefetch2) {
            this.worker = worker2;
            this.delayError = delayError2;
            this.prefetch = prefetch2;
            this.limit = prefetch2 - (prefetch2 >> 2);
        }

        public final void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode == 2) {
                    trySchedule();
                    return;
                }
                if (!this.queue.offer(t)) {
                    this.upstream.cancel();
                    this.error = new MissingBackpressureException("Queue is full?!");
                    this.done = true;
                }
                trySchedule();
            }
        }

        public final void onError(Throwable t) {
            if (this.done) {
                io.reactivex.plugins.a.q(t);
                return;
            }
            this.error = t;
            this.done = true;
            trySchedule();
        }

        public final void onComplete() {
            if (!this.done) {
                this.done = true;
                trySchedule();
            }
        }

        public final void request(long n) {
            if (f.validate(n)) {
                io.reactivex.internal.util.c.a(this.requested, n);
                trySchedule();
            }
        }

        public final void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                this.worker.dispose();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public final void trySchedule() {
            if (getAndIncrement() == 0) {
                this.worker.b(this);
            }
        }

        public final void run() {
            if (this.outputFused) {
                runBackfused();
            } else if (this.sourceMode == 1) {
                runSync();
            } else {
                runAsync();
            }
        }

        /* access modifiers changed from: package-private */
        public final boolean checkTerminated(boolean d, boolean empty, org.reactivestreams.b<?> a) {
            if (this.cancelled) {
                clear();
                return true;
            } else if (!d) {
                return false;
            } else {
                if (!this.delayError) {
                    Throwable e = this.error;
                    if (e != null) {
                        this.cancelled = true;
                        clear();
                        a.onError(e);
                        this.worker.dispose();
                        return true;
                    } else if (!empty) {
                        return false;
                    } else {
                        this.cancelled = true;
                        a.onComplete();
                        this.worker.dispose();
                        return true;
                    }
                } else if (!empty) {
                    return false;
                } else {
                    this.cancelled = true;
                    Throwable e2 = this.error;
                    if (e2 != null) {
                        a.onError(e2);
                    } else {
                        a.onComplete();
                    }
                    this.worker.dispose();
                    return true;
                }
            }
        }

        public final int requestFusion(int requestedMode) {
            if ((requestedMode & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        public final void clear() {
            this.queue.clear();
        }

        public final boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }

    /* compiled from: FlowableObserveOn */
    public static final class c<T> extends a<T> implements h<T> {
        private static final long serialVersionUID = -4547113800637756442L;
        final org.reactivestreams.b<? super T> downstream;

        c(org.reactivestreams.b<? super T> actual, r.c worker, boolean delayError, int prefetch) {
            super(worker, delayError, prefetch);
            this.downstream = actual;
        }

        public void onSubscribe(org.reactivestreams.c s) {
            if (f.validate(this.upstream, s)) {
                this.upstream = s;
                if (s instanceof d) {
                    QueueSubscription<T> f = (d) s;
                    int m = f.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = 1;
                        this.queue = f;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        return;
                    } else if (m == 2) {
                        this.sourceMode = 2;
                        this.queue = f;
                        this.downstream.onSubscribe(this);
                        s.request((long) this.prefetch);
                        return;
                    }
                }
                this.queue = new io.reactivex.internal.queue.b(this.prefetch);
                this.downstream.onSubscribe(this);
                s.request((long) this.prefetch);
            }
        }

        /* access modifiers changed from: package-private */
        public void runSync() {
            int missed = 1;
            Subscriber<? super T> a = this.downstream;
            SimpleQueue<T> q = this.queue;
            long e = this.produced;
            while (true) {
                long r = this.requested.get();
                while (e != r) {
                    try {
                        T v = q.poll();
                        if (!this.cancelled) {
                            if (v == null) {
                                this.cancelled = true;
                                a.onComplete();
                                this.worker.dispose();
                                return;
                            }
                            a.onNext(v);
                            e++;
                        } else {
                            return;
                        }
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        this.cancelled = true;
                        this.upstream.cancel();
                        a.onError(ex);
                        this.worker.dispose();
                        return;
                    }
                }
                if (!this.cancelled) {
                    if (q.isEmpty()) {
                        this.cancelled = true;
                        a.onComplete();
                        this.worker.dispose();
                        return;
                    }
                    int w = get();
                    if (missed == w) {
                        this.produced = e;
                        missed = addAndGet(-missed);
                        if (missed == 0) {
                            return;
                        }
                    } else {
                        missed = w;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void runAsync() {
            int missed = 1;
            Subscriber<? super T> a = this.downstream;
            SimpleQueue<T> q = this.queue;
            long e = this.produced;
            while (true) {
                long r = this.requested.get();
                while (e != r) {
                    boolean d = this.done;
                    boolean empty = true;
                    try {
                        T v = q.poll();
                        if (v != null) {
                            empty = false;
                        }
                        if (!checkTerminated(d, empty, a)) {
                            if (empty) {
                                break;
                            }
                            a.onNext(v);
                            e++;
                            if (e == ((long) this.limit)) {
                                if (r != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                                    r = this.requested.addAndGet(-e);
                                }
                                this.upstream.request(e);
                                e = 0;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        this.cancelled = true;
                        this.upstream.cancel();
                        q.clear();
                        a.onError(ex);
                        this.worker.dispose();
                        return;
                    }
                }
                if (e != r || !checkTerminated(this.done, q.isEmpty(), a)) {
                    int w = get();
                    if (missed == w) {
                        this.produced = e;
                        missed = addAndGet(-missed);
                        if (missed == 0) {
                            return;
                        }
                    } else {
                        missed = w;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void runBackfused() {
            int missed = 1;
            while (!this.cancelled) {
                boolean d = this.done;
                this.downstream.onNext(null);
                if (d) {
                    this.cancelled = true;
                    Throwable e = this.error;
                    if (e != null) {
                        this.downstream.onError(e);
                    } else {
                        this.downstream.onComplete();
                    }
                    this.worker.dispose();
                    return;
                }
                missed = addAndGet(-missed);
                if (missed == 0) {
                    return;
                }
            }
        }

        public T poll() {
            T v = this.queue.poll();
            if (!(v == null || this.sourceMode == 1)) {
                long p = this.produced + 1;
                if (p == ((long) this.limit)) {
                    this.produced = 0;
                    this.upstream.request(p);
                } else {
                    this.produced = p;
                }
            }
            return v;
        }
    }

    /* compiled from: FlowableObserveOn */
    public static final class b<T> extends a<T> {
        private static final long serialVersionUID = 644624475404284533L;
        long consumed;
        final io.reactivex.internal.fuseable.a<? super T> downstream;

        b(io.reactivex.internal.fuseable.a<? super T> actual, r.c worker, boolean delayError, int prefetch) {
            super(worker, delayError, prefetch);
            this.downstream = actual;
        }

        public void onSubscribe(org.reactivestreams.c s) {
            if (f.validate(this.upstream, s)) {
                this.upstream = s;
                if (s instanceof d) {
                    QueueSubscription<T> f = (d) s;
                    int m = f.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = 1;
                        this.queue = f;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        return;
                    } else if (m == 2) {
                        this.sourceMode = 2;
                        this.queue = f;
                        this.downstream.onSubscribe(this);
                        s.request((long) this.prefetch);
                        return;
                    }
                }
                this.queue = new io.reactivex.internal.queue.b(this.prefetch);
                this.downstream.onSubscribe(this);
                s.request((long) this.prefetch);
            }
        }

        /* access modifiers changed from: package-private */
        public void runSync() {
            int missed = 1;
            ConditionalSubscriber<? super T> a = this.downstream;
            SimpleQueue<T> q = this.queue;
            long e = this.produced;
            while (true) {
                long r = this.requested.get();
                while (e != r) {
                    try {
                        T v = q.poll();
                        if (!this.cancelled) {
                            if (v == null) {
                                this.cancelled = true;
                                a.onComplete();
                                this.worker.dispose();
                                return;
                            } else if (a.tryOnNext(v)) {
                                e++;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        this.cancelled = true;
                        this.upstream.cancel();
                        a.onError(ex);
                        this.worker.dispose();
                        return;
                    }
                }
                if (!this.cancelled) {
                    if (q.isEmpty()) {
                        this.cancelled = true;
                        a.onComplete();
                        this.worker.dispose();
                        return;
                    }
                    int w = get();
                    if (missed == w) {
                        this.produced = e;
                        missed = addAndGet(-missed);
                        if (missed == 0) {
                            return;
                        }
                    } else {
                        missed = w;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void runAsync() {
            int missed = 1;
            ConditionalSubscriber<? super T> a = this.downstream;
            SimpleQueue<T> q = this.queue;
            long emitted = this.produced;
            long polled = this.consumed;
            while (true) {
                long r = this.requested.get();
                while (emitted != r) {
                    boolean d = this.done;
                    boolean empty = true;
                    try {
                        T v = q.poll();
                        if (v != null) {
                            empty = false;
                        }
                        if (!checkTerminated(d, empty, a)) {
                            if (empty) {
                                break;
                            }
                            if (a.tryOnNext(v)) {
                                emitted++;
                            }
                            polled++;
                            if (polled == ((long) this.limit)) {
                                this.upstream.request(polled);
                                polled = 0;
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        this.cancelled = true;
                        this.upstream.cancel();
                        q.clear();
                        a.onError(ex);
                        this.worker.dispose();
                        return;
                    }
                }
                if (emitted != r || !checkTerminated(this.done, q.isEmpty(), a)) {
                    int w = get();
                    if (missed == w) {
                        this.produced = emitted;
                        this.consumed = polled;
                        missed = addAndGet(-missed);
                        if (missed == 0) {
                            return;
                        }
                    } else {
                        missed = w;
                    }
                } else {
                    return;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void runBackfused() {
            int missed = 1;
            while (!this.cancelled) {
                boolean d = this.done;
                this.downstream.onNext(null);
                if (d) {
                    this.cancelled = true;
                    Throwable e = this.error;
                    if (e != null) {
                        this.downstream.onError(e);
                    } else {
                        this.downstream.onComplete();
                    }
                    this.worker.dispose();
                    return;
                }
                missed = addAndGet(-missed);
                if (missed == 0) {
                    return;
                }
            }
        }

        public T poll() {
            T v = this.queue.poll();
            if (!(v == null || this.sourceMode == 1)) {
                long p = this.consumed + 1;
                if (p == ((long) this.limit)) {
                    this.consumed = 0;
                    this.upstream.request(p);
                } else {
                    this.consumed = p;
                }
            }
            return v;
        }
    }
}
