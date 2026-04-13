package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableEmitter;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.operators.observable.ObservableCreate;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import io.reactivex.l;
import io.reactivex.m;
import io.reactivex.n;
import io.reactivex.q;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableCreate */
public final class d<T> extends l<T> {
    final n<T> c;

    public d(n<T> source) {
        this.c = source;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        ObservableCreate.CreateEmitter<T> parent = new a<>(observer);
        observer.onSubscribe(parent);
        try {
            this.c.subscribe(parent);
        } catch (Throwable ex) {
            io.reactivex.exceptions.a.b(ex);
            parent.onError(ex);
        }
    }

    /* compiled from: ObservableCreate */
    public static final class a<T> extends AtomicReference<io.reactivex.disposables.b> implements m<T>, io.reactivex.disposables.b {
        private static final long serialVersionUID = -3434801548987643227L;
        final q<? super T> observer;

        a(q<? super T> observer2) {
            this.observer = observer2;
        }

        public void onNext(T t) {
            if (t == null) {
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            } else if (!isDisposed()) {
                this.observer.onNext(t);
            }
        }

        public void onError(Throwable t) {
            if (!tryOnError(t)) {
                io.reactivex.plugins.a.q(t);
            }
        }

        /* JADX INFO: finally extract failed */
        public boolean tryOnError(Throwable t) {
            if (t == null) {
                t = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (isDisposed()) {
                return false;
            }
            try {
                this.observer.onError(t);
                dispose();
                return true;
            } catch (Throwable th) {
                dispose();
                throw th;
            }
        }

        public void onComplete() {
            if (!isDisposed()) {
                try {
                    this.observer.onComplete();
                } finally {
                    dispose();
                }
            }
        }

        public void setDisposable(io.reactivex.disposables.b d) {
            c.set(this, d);
        }

        public void setCancellable(io.reactivex.functions.d c) {
            setDisposable(new io.reactivex.internal.disposables.a(c));
        }

        public m<T> serialize() {
            return new b(this);
        }

        public void dispose() {
            c.dispose(this);
        }

        public boolean isDisposed() {
            return c.isDisposed((io.reactivex.disposables.b) get());
        }

        public String toString() {
            return String.format("%s{%s}", new Object[]{getClass().getSimpleName(), super.toString()});
        }
    }

    /* compiled from: ObservableCreate */
    public static final class b<T> extends AtomicInteger implements m<T> {
        private static final long serialVersionUID = 4883307006032401862L;
        volatile boolean done;
        final m<T> emitter;
        final io.reactivex.internal.util.b error = new io.reactivex.internal.util.b();
        final io.reactivex.internal.queue.c<T> queue = new io.reactivex.internal.queue.c<>(16);

        b(m<T> emitter2) {
            this.emitter = emitter2;
        }

        public void onNext(T t) {
            if (!this.emitter.isDisposed() && !this.done) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                if (get() != 0 || !compareAndSet(0, 1)) {
                    SimpleQueue<T> q = this.queue;
                    synchronized (q) {
                        q.offer(t);
                    }
                    if (getAndIncrement() != 0) {
                        return;
                    }
                } else {
                    this.emitter.onNext(t);
                    if (decrementAndGet() == 0) {
                        return;
                    }
                }
                drainLoop();
            }
        }

        public void onError(Throwable t) {
            if (!tryOnError(t)) {
                io.reactivex.plugins.a.q(t);
            }
        }

        public boolean tryOnError(Throwable t) {
            if (this.emitter.isDisposed() || this.done) {
                return false;
            }
            if (t == null) {
                t = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (!this.error.addThrowable(t)) {
                return false;
            }
            this.done = true;
            drain();
            return true;
        }

        public void onComplete() {
            if (!this.emitter.isDisposed() && !this.done) {
                this.done = true;
                drain();
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* access modifiers changed from: package-private */
        public void drainLoop() {
            ObservableEmitter<T> e = this.emitter;
            SpscLinkedArrayQueue<T> q = this.queue;
            io.reactivex.internal.util.b error2 = this.error;
            int missed = 1;
            while (!e.isDisposed()) {
                if (error2.get() != null) {
                    q.clear();
                    e.onError(error2.terminate());
                    return;
                }
                boolean d = this.done;
                T v = q.poll();
                boolean empty = v == null;
                if (d && empty) {
                    e.onComplete();
                    return;
                } else if (empty) {
                    missed = addAndGet(-missed);
                    if (missed == 0) {
                        return;
                    }
                } else {
                    e.onNext(v);
                }
            }
            q.clear();
        }

        public void setDisposable(io.reactivex.disposables.b d) {
            this.emitter.setDisposable(d);
        }

        public void setCancellable(io.reactivex.functions.d c) {
            this.emitter.setCancellable(c);
        }

        public boolean isDisposed() {
            return this.emitter.isDisposed();
        }

        public m<T> serialize() {
            return this;
        }

        public String toString() {
            return this.emitter.toString();
        }
    }
}
