package io.reactivex.internal.operators.observable;

import io.reactivex.Observer;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.fuseable.g;
import io.reactivex.internal.observers.b;
import io.reactivex.o;
import io.reactivex.q;
import io.reactivex.r;

/* compiled from: ObservableObserveOn */
public final class s<T> extends a<T, T> {
    final r d;
    final boolean f;
    final int q;

    public s(o<T> source, r scheduler, boolean delayError, int bufferSize) {
        super(source);
        this.d = scheduler;
        this.f = delayError;
        this.q = bufferSize;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        r rVar = this.d;
        if (rVar instanceof io.reactivex.internal.schedulers.o) {
            this.c.a(observer);
            return;
        }
        this.c.a(new a(observer, rVar.a(), this.f, this.q));
    }

    /* compiled from: ObservableObserveOn */
    public static final class a<T> extends b<T> implements q<T>, Runnable {
        private static final long serialVersionUID = 6576896619930983584L;
        final int bufferSize;
        final boolean delayError;
        volatile boolean disposed;
        volatile boolean done;
        final q<? super T> downstream;
        Throwable error;
        boolean outputFused;
        g<T> queue;
        int sourceMode;
        io.reactivex.disposables.b upstream;
        final r.c worker;

        a(q<? super T> actual, r.c worker2, boolean delayError2, int bufferSize2) {
            this.downstream = actual;
            this.worker = worker2;
            this.delayError = delayError2;
            this.bufferSize = bufferSize2;
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            if (c.validate(this.upstream, d)) {
                this.upstream = d;
                if (d instanceof io.reactivex.internal.fuseable.b) {
                    QueueDisposable<T> qd = (io.reactivex.internal.fuseable.b) d;
                    int m = qd.requestFusion(7);
                    if (m == 1) {
                        this.sourceMode = m;
                        this.queue = qd;
                        this.done = true;
                        this.downstream.onSubscribe(this);
                        schedule();
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

        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 2) {
                    this.queue.offer(t);
                }
                schedule();
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                io.reactivex.plugins.a.q(t);
                return;
            }
            this.error = t;
            this.done = true;
            schedule();
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                schedule();
            }
        }

        public void dispose() {
            if (!this.disposed) {
                this.disposed = true;
                this.upstream.dispose();
                this.worker.dispose();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        public boolean isDisposed() {
            return this.disposed;
        }

        /* access modifiers changed from: package-private */
        public void schedule() {
            if (getAndIncrement() == 0) {
                this.worker.b(this);
            }
        }

        /* access modifiers changed from: package-private */
        public void drainNormal() {
            int missed = 1;
            SimpleQueue<T> q = this.queue;
            Observer<? super T> a = this.downstream;
            while (!checkTerminated(this.done, q.isEmpty(), a)) {
                while (true) {
                    boolean d = this.done;
                    boolean empty = true;
                    try {
                        T v = q.poll();
                        if (v != null) {
                            empty = false;
                        }
                        if (!checkTerminated(d, empty, a)) {
                            if (empty) {
                                missed = addAndGet(-missed);
                                if (missed == 0) {
                                    return;
                                }
                            } else {
                                a.onNext(v);
                            }
                        } else {
                            return;
                        }
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        this.disposed = true;
                        this.upstream.dispose();
                        q.clear();
                        a.onError(ex);
                        this.worker.dispose();
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void drainFused() {
            int missed = 1;
            while (!this.disposed) {
                boolean d = this.done;
                Throwable ex = this.error;
                if (this.delayError || !d || ex == null) {
                    this.downstream.onNext(null);
                    if (d) {
                        this.disposed = true;
                        Throwable ex2 = this.error;
                        if (ex2 != null) {
                            this.downstream.onError(ex2);
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
                } else {
                    this.disposed = true;
                    this.downstream.onError(this.error);
                    this.worker.dispose();
                    return;
                }
            }
        }

        public void run() {
            if (this.outputFused) {
                drainFused();
            } else {
                drainNormal();
            }
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminated(boolean d, boolean empty, q<? super T> a) {
            if (this.disposed) {
                this.queue.clear();
                return true;
            } else if (!d) {
                return false;
            } else {
                Throwable e = this.error;
                if (this.delayError) {
                    if (!empty) {
                        return false;
                    }
                    this.disposed = true;
                    if (e != null) {
                        a.onError(e);
                    } else {
                        a.onComplete();
                    }
                    this.worker.dispose();
                    return true;
                } else if (e != null) {
                    this.disposed = true;
                    this.queue.clear();
                    a.onError(e);
                    this.worker.dispose();
                    return true;
                } else if (!empty) {
                    return false;
                } else {
                    this.disposed = true;
                    a.onComplete();
                    this.worker.dispose();
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
