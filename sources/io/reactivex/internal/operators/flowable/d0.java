package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.e;
import io.reactivex.functions.f;
import io.reactivex.h;
import io.reactivex.internal.fuseable.QueueSubscription;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.fuseable.d;
import io.reactivex.internal.fuseable.g;
import io.reactivex.internal.operators.flowable.FlowableZip;
import io.reactivex.internal.subscriptions.c;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/* compiled from: FlowableZip */
public final class d0<T, R> extends e<R> {
    final org.reactivestreams.a<? extends T>[] d;
    final Iterable<? extends org.reactivestreams.a<? extends T>> f;
    final f<? super Object[], ? extends R> q;
    final int x;
    final boolean y;

    public d0(org.reactivestreams.a<? extends T>[] sources, Iterable<? extends org.reactivestreams.a<? extends T>> sourcesIterable, f<? super Object[], ? extends R> zipper, int bufferSize, boolean delayError) {
        this.d = sources;
        this.f = sourcesIterable;
        this.q = zipper;
        this.x = bufferSize;
        this.y = delayError;
    }

    public void L(org.reactivestreams.b<? super R> s) {
        int count;
        Publisher<? extends T>[] sources = this.d;
        int count2 = 0;
        if (sources == null) {
            sources = new org.reactivestreams.a[8];
            Iterator<? extends org.reactivestreams.a<? extends T>> it = this.f.iterator();
            while (it.hasNext()) {
                Publisher<? extends T> p = (org.reactivestreams.a) it.next();
                if (count2 == sources.length) {
                    Publisher<? extends T>[] b2 = new org.reactivestreams.a[((count2 >> 2) + count2)];
                    System.arraycopy(sources, 0, b2, 0, count2);
                    sources = b2;
                }
                sources[count2] = p;
                count2++;
            }
            count = count2;
        } else {
            count = sources.length;
        }
        if (count == 0) {
            c.complete(s);
            return;
        }
        FlowableZip.ZipCoordinator<T, R> coordinator = new a<>(s, this.q, count, this.x, this.y);
        s.onSubscribe(coordinator);
        coordinator.subscribe(sources, count);
    }

    /* compiled from: FlowableZip */
    public static final class a<T, R> extends AtomicInteger implements org.reactivestreams.c {
        private static final long serialVersionUID = -2434867452883857743L;
        volatile boolean cancelled;
        final Object[] current;
        final boolean delayErrors;
        final org.reactivestreams.b<? super R> downstream;
        final io.reactivex.internal.util.b errors;
        final AtomicLong requested;
        final b<T, R>[] subscribers;
        final f<? super Object[], ? extends R> zipper;

        a(org.reactivestreams.b<? super R> actual, f<? super Object[], ? extends R> zipper2, int n, int prefetch, boolean delayErrors2) {
            this.downstream = actual;
            this.zipper = zipper2;
            this.delayErrors = delayErrors2;
            FlowableZip.ZipSubscriber<T, R>[] a = new b[n];
            for (int i = 0; i < n; i++) {
                a[i] = new b<>(this, prefetch);
            }
            this.current = new Object[n];
            this.subscribers = a;
            this.requested = new AtomicLong();
            this.errors = new io.reactivex.internal.util.b();
        }

        /* access modifiers changed from: package-private */
        public void subscribe(org.reactivestreams.a<? extends T>[] sources, int n) {
            FlowableZip.ZipSubscriber<T, R>[] a = this.subscribers;
            int i = 0;
            while (i < n && !this.cancelled) {
                if (this.delayErrors || this.errors.get() == null) {
                    sources[i].a(a[i]);
                    i++;
                } else {
                    return;
                }
            }
        }

        public void request(long n) {
            if (io.reactivex.internal.subscriptions.f.validate(n)) {
                io.reactivex.internal.util.c.a(this.requested, n);
                drain();
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                cancelAll();
            }
        }

        /* access modifiers changed from: package-private */
        public void error(b<T, R> inner, Throwable e) {
            if (this.errors.addThrowable(e)) {
                inner.done = true;
                drain();
                return;
            }
            io.reactivex.plugins.a.q(e);
        }

        /* access modifiers changed from: package-private */
        public void cancelAll() {
            for (FlowableZip.ZipSubscriber<T, R> s : this.subscribers) {
                s.cancel();
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            T t;
            if (getAndIncrement() == 0) {
                Subscriber<? super R> a = this.downstream;
                FlowableZip.ZipSubscriber<T, R>[] qs = this.subscribers;
                int n = qs.length;
                Object[] values = this.current;
                int missed = 1;
                do {
                    long r = this.requested.get();
                    long e = 0;
                    while (true) {
                        if (r == e) {
                            t = null;
                            break;
                        } else if (!this.cancelled) {
                            if (this.delayErrors || this.errors.get() == null) {
                                boolean empty = false;
                                for (int j = 0; j < n; j++) {
                                    FlowableZip.ZipSubscriber<T, R> inner = qs[j];
                                    if (values[j] == null) {
                                        try {
                                            boolean d = inner.done;
                                            SimpleQueue<T> q = inner.queue;
                                            T v = q != null ? q.poll() : null;
                                            boolean sourceEmpty = v == null;
                                            if (!d || !sourceEmpty) {
                                                if (!sourceEmpty) {
                                                    values[j] = v;
                                                } else {
                                                    empty = true;
                                                }
                                            } else {
                                                cancelAll();
                                                if (((Throwable) this.errors.get()) != null) {
                                                    boolean z = d;
                                                    a.onError(this.errors.terminate());
                                                    return;
                                                }
                                                a.onComplete();
                                                return;
                                            }
                                        } catch (Throwable ex) {
                                            io.reactivex.exceptions.a.b(ex);
                                            this.errors.addThrowable(ex);
                                            if (!this.delayErrors) {
                                                cancelAll();
                                                a.onError(this.errors.terminate());
                                                return;
                                            }
                                            empty = true;
                                        }
                                    }
                                }
                                if (empty) {
                                    t = null;
                                    break;
                                }
                                try {
                                    a.onNext(io.reactivex.internal.functions.b.e(this.zipper.apply(values.clone()), "The zipper returned a null value"));
                                    e++;
                                    Arrays.fill(values, (Object) null);
                                } catch (Throwable ex2) {
                                    io.reactivex.exceptions.a.b(ex2);
                                    cancelAll();
                                    this.errors.addThrowable(ex2);
                                    a.onError(this.errors.terminate());
                                    return;
                                }
                            } else {
                                cancelAll();
                                a.onError(this.errors.terminate());
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                    if (r == e) {
                        if (!this.cancelled) {
                            if (this.delayErrors || this.errors.get() == null) {
                                int j2 = 0;
                                while (j2 < n) {
                                    FlowableZip.ZipSubscriber<T, R> inner2 = qs[j2];
                                    if (values[j2] == null) {
                                        try {
                                            boolean d2 = inner2.done;
                                            SimpleQueue<T> q2 = inner2.queue;
                                            T v2 = q2 != null ? q2.poll() : t;
                                            boolean empty2 = v2 == null;
                                            if (!d2 || !empty2) {
                                                if (!empty2) {
                                                    values[j2] = v2;
                                                }
                                            } else {
                                                cancelAll();
                                                if (((Throwable) this.errors.get()) != null) {
                                                    boolean z2 = d2;
                                                    a.onError(this.errors.terminate());
                                                    return;
                                                }
                                                a.onComplete();
                                                return;
                                            }
                                        } catch (Throwable ex3) {
                                            io.reactivex.exceptions.a.b(ex3);
                                            this.errors.addThrowable(ex3);
                                            if (!this.delayErrors) {
                                                cancelAll();
                                                a.onError(this.errors.terminate());
                                                return;
                                            }
                                        }
                                    }
                                    j2++;
                                    t = null;
                                }
                            } else {
                                cancelAll();
                                a.onError(this.errors.terminate());
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                    if (e != 0) {
                        for (FlowableZip.ZipSubscriber<T, R> inner3 : qs) {
                            inner3.request(e);
                        }
                        if (r != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                            this.requested.addAndGet(-e);
                        }
                    }
                    missed = addAndGet(-missed);
                } while (missed != 0);
            }
        }
    }

    /* compiled from: FlowableZip */
    public static final class b<T, R> extends AtomicReference<org.reactivestreams.c> implements h<T>, org.reactivestreams.c {
        private static final long serialVersionUID = -4627193790118206028L;
        volatile boolean done;
        final int limit;
        final a<T, R> parent;
        final int prefetch;
        long produced;
        g<T> queue;
        int sourceMode;

        b(a<T, R> parent2, int prefetch2) {
            this.parent = parent2;
            this.prefetch = prefetch2;
            this.limit = prefetch2 - (prefetch2 >> 2);
        }

        public void onSubscribe(org.reactivestreams.c s) {
            if (io.reactivex.internal.subscriptions.f.setOnce(this, s)) {
                if (s instanceof d) {
                    QueueSubscription<T> f = (d) s;
                    int m = f.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = m;
                        this.queue = f;
                        this.done = true;
                        this.parent.drain();
                        return;
                    } else if (m == 2) {
                        this.sourceMode = m;
                        this.queue = f;
                        s.request((long) this.prefetch);
                        return;
                    }
                }
                this.queue = new io.reactivex.internal.queue.b(this.prefetch);
                s.request((long) this.prefetch);
            }
        }

        public void onNext(T t) {
            if (this.sourceMode != 2) {
                this.queue.offer(t);
            }
            this.parent.drain();
        }

        public void onError(Throwable t) {
            this.parent.error(this, t);
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        public void cancel() {
            io.reactivex.internal.subscriptions.f.cancel(this);
        }

        public void request(long n) {
            if (this.sourceMode != 1) {
                long p = this.produced + n;
                if (p >= ((long) this.limit)) {
                    this.produced = 0;
                    ((org.reactivestreams.c) get()).request(p);
                    return;
                }
                this.produced = p;
            }
        }
    }
}
