package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.e;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.h;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.f;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableOnBackpressureBuffer */
public final class r<T> extends a<T, T> {
    final int f;
    final boolean q;
    final boolean x;
    final io.reactivex.functions.a y;

    public r(e<T> source, int bufferSize, boolean unbounded, boolean delayError, io.reactivex.functions.a onOverflow) {
        super(source);
        this.f = bufferSize;
        this.q = unbounded;
        this.x = delayError;
        this.y = onOverflow;
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> s) {
        this.d.K(new a(s, this.f, this.q, this.x, this.y));
    }

    /* compiled from: FlowableOnBackpressureBuffer */
    public static final class a<T> extends io.reactivex.internal.subscriptions.a<T> implements h<T> {
        private static final long serialVersionUID = -2514538129242366402L;
        volatile boolean cancelled;
        final boolean delayError;
        volatile boolean done;
        final b<? super T> downstream;
        Throwable error;
        final io.reactivex.functions.a onOverflow;
        boolean outputFused;
        final f<T> queue;
        final AtomicLong requested = new AtomicLong();
        c upstream;

        a(b<? super T> actual, int bufferSize, boolean unbounded, boolean delayError2, io.reactivex.functions.a onOverflow2) {
            SimplePlainQueue<T> q;
            this.downstream = actual;
            this.onOverflow = onOverflow2;
            this.delayError = delayError2;
            if (unbounded) {
                q = new io.reactivex.internal.queue.c<>(bufferSize);
            } else {
                q = new io.reactivex.internal.queue.b<>(bufferSize);
            }
            this.queue = q;
        }

        public void onSubscribe(c s) {
            if (io.reactivex.internal.subscriptions.f.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
                s.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
            }
        }

        public void onNext(T t) {
            if (!this.queue.offer(t)) {
                this.upstream.cancel();
                MissingBackpressureException ex = new MissingBackpressureException("Buffer is full");
                try {
                    this.onOverflow.run();
                } catch (Throwable e) {
                    io.reactivex.exceptions.a.b(e);
                    ex.initCause(e);
                }
                onError(ex);
            } else if (this.outputFused) {
                this.downstream.onNext(null);
            } else {
                drain();
            }
        }

        public void onError(Throwable t) {
            this.error = t;
            this.done = true;
            if (this.outputFused) {
                this.downstream.onError(t);
            } else {
                drain();
            }
        }

        public void onComplete() {
            this.done = true;
            if (this.outputFused) {
                this.downstream.onComplete();
            } else {
                drain();
            }
        }

        public void request(long n) {
            if (!this.outputFused && io.reactivex.internal.subscriptions.f.validate(n)) {
                io.reactivex.internal.util.c.a(this.requested, n);
                drain();
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                int missed = 1;
                SimplePlainQueue<T> q = this.queue;
                Subscriber<? super T> a = this.downstream;
                while (!checkTerminated(this.done, q.isEmpty(), a)) {
                    long r = this.requested.get();
                    long e = 0;
                    while (e != r) {
                        boolean d = this.done;
                        T v = q.poll();
                        boolean empty = v == null;
                        if (!checkTerminated(d, empty, a)) {
                            if (empty) {
                                break;
                            }
                            a.onNext(v);
                            e++;
                        } else {
                            return;
                        }
                    }
                    if (e != r || !checkTerminated(this.done, q.isEmpty(), a)) {
                        if (!(e == 0 || r == DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE)) {
                            this.requested.addAndGet(-e);
                        }
                        missed = addAndGet(-missed);
                        if (missed == 0) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminated(boolean d, boolean empty, b<? super T> a) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            } else if (!d) {
                return false;
            } else {
                if (!this.delayError) {
                    Throwable e = this.error;
                    if (e != null) {
                        this.queue.clear();
                        a.onError(e);
                        return true;
                    } else if (!empty) {
                        return false;
                    } else {
                        a.onComplete();
                        return true;
                    }
                } else if (!empty) {
                    return false;
                } else {
                    Throwable e2 = this.error;
                    if (e2 != null) {
                        a.onError(e2);
                    } else {
                        a.onComplete();
                    }
                    return true;
                }
            }
        }

        public int requestFusion(int mode) {
            if ((mode & 2) == 0) {
                return 0;
            }
            this.outputFused = true;
            return 2;
        }

        public T poll() {
            return this.queue.poll();
        }

        public void clear() {
            this.queue.clear();
        }

        public boolean isEmpty() {
            return this.queue.isEmpty();
        }
    }
}
