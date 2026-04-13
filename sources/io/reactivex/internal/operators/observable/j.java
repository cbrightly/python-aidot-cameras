package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.f;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.fuseable.QueueDisposable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.fuseable.g;
import io.reactivex.internal.operators.observable.ObservableFlatMap;
import io.reactivex.o;
import io.reactivex.q;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableFlatMap */
public final class j<T, U> extends a<T, U> {
    final f<? super T, ? extends o<? extends U>> d;
    final boolean f;
    final int q;
    final int x;

    public j(o<T> source, f<? super T, ? extends o<? extends U>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        super(source);
        this.d = mapper;
        this.f = delayErrors;
        this.q = maxConcurrency;
        this.x = bufferSize;
    }

    public void a0(q<? super U> t) {
        if (!b0.b(this.c, t, this.d)) {
            this.c.a(new b(t, this.d, this.f, this.q, this.x));
        }
    }

    /* compiled from: ObservableFlatMap */
    public static final class b<T, U> extends AtomicInteger implements io.reactivex.disposables.b, q<T> {
        static final a<?, ?>[] CANCELLED = new a[0];
        static final a<?, ?>[] EMPTY = new a[0];
        private static final long serialVersionUID = -2117620485640801370L;
        final int bufferSize;
        volatile boolean cancelled;
        final boolean delayErrors;
        volatile boolean done;
        final q<? super U> downstream;
        final io.reactivex.internal.util.b errors = new io.reactivex.internal.util.b();
        long lastId;
        int lastIndex;
        final f<? super T, ? extends o<? extends U>> mapper;
        final int maxConcurrency;
        final AtomicReference<a<?, ?>[]> observers;
        volatile io.reactivex.internal.fuseable.f<U> queue;
        Queue<o<? extends U>> sources;
        long uniqueId;
        io.reactivex.disposables.b upstream;
        int wip;

        b(q<? super U> actual, f<? super T, ? extends o<? extends U>> mapper2, boolean delayErrors2, int maxConcurrency2, int bufferSize2) {
            this.downstream = actual;
            this.mapper = mapper2;
            this.delayErrors = delayErrors2;
            this.maxConcurrency = maxConcurrency2;
            this.bufferSize = bufferSize2;
            if (maxConcurrency2 != Integer.MAX_VALUE) {
                this.sources = new ArrayDeque(maxConcurrency2);
            }
            this.observers = new AtomicReference<>(EMPTY);
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            if (c.validate(this.upstream, d)) {
                this.upstream = d;
                this.downstream.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    ObservableSource<? extends U> p = (o) io.reactivex.internal.functions.b.e(this.mapper.apply(t), "The mapper returned a null ObservableSource");
                    if (this.maxConcurrency != Integer.MAX_VALUE) {
                        synchronized (this) {
                            int i = this.wip;
                            if (i == this.maxConcurrency) {
                                this.sources.offer(p);
                                return;
                            }
                            this.wip = i + 1;
                        }
                    }
                    subscribeInner(p);
                } catch (Throwable e) {
                    io.reactivex.exceptions.a.b(e);
                    this.upstream.dispose();
                    onError(e);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void subscribeInner(ObservableSource<? extends U> p) {
            while (p instanceof Callable) {
                if (tryEmitScalar((Callable) p) && this.maxConcurrency != Integer.MAX_VALUE) {
                    boolean empty = false;
                    synchronized (this) {
                        p = (o) this.sources.poll();
                        if (p == null) {
                            this.wip--;
                            empty = true;
                        }
                    }
                    if (empty) {
                        drain();
                        return;
                    }
                } else {
                    return;
                }
            }
            long j = this.uniqueId;
            this.uniqueId = 1 + j;
            ObservableFlatMap.InnerObserver<T, U> inner = new a<>(this, j);
            if (addInner(inner)) {
                p.a(inner);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean addInner(a<T, U> inner) {
            ObservableFlatMap.InnerObserver<?, ?>[] a;
            ObservableFlatMap.InnerObserver<?, ?>[] b;
            do {
                a = (a[]) this.observers.get();
                if (a == CANCELLED) {
                    inner.dispose();
                    return false;
                }
                int n = a.length;
                b = new a[(n + 1)];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = inner;
            } while (!this.observers.compareAndSet(a, b));
            return true;
        }

        /* access modifiers changed from: package-private */
        public void removeInner(a<T, U> inner) {
            ObservableFlatMap.InnerObserver<?, ?>[] a;
            ObservableFlatMap.InnerObserver<?, ?>[] b;
            do {
                a = (a[]) this.observers.get();
                int n = a.length;
                if (n != 0) {
                    int j = -1;
                    int i = 0;
                    while (true) {
                        if (i >= n) {
                            break;
                        } else if (a[i] == inner) {
                            j = i;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (j >= 0) {
                        if (n == 1) {
                            b = EMPTY;
                        } else {
                            ObservableFlatMap.InnerObserver<?, ?>[] b2 = new a[(n - 1)];
                            System.arraycopy(a, 0, b2, 0, j);
                            System.arraycopy(a, j + 1, b2, j, (n - j) - 1);
                            b = b2;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            } while (!this.observers.compareAndSet(a, b));
        }

        /* access modifiers changed from: package-private */
        public boolean tryEmitScalar(Callable<? extends U> value) {
            try {
                U u = value.call();
                if (u == null) {
                    return true;
                }
                if (get() != 0 || !compareAndSet(0, 1)) {
                    SimplePlainQueue<U> q = this.queue;
                    if (q == null) {
                        if (this.maxConcurrency == Integer.MAX_VALUE) {
                            q = new io.reactivex.internal.queue.c<>(this.bufferSize);
                        } else {
                            q = new io.reactivex.internal.queue.b<>(this.maxConcurrency);
                        }
                        this.queue = q;
                    }
                    if (!q.offer(u)) {
                        onError(new IllegalStateException("Scalar queue full?!"));
                        return true;
                    } else if (getAndIncrement() != 0) {
                        return false;
                    }
                } else {
                    this.downstream.onNext(u);
                    if (decrementAndGet() == 0) {
                        return true;
                    }
                }
                drainLoop();
                return true;
            } catch (Throwable ex) {
                io.reactivex.exceptions.a.b(ex);
                this.errors.addThrowable(ex);
                drain();
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public void tryEmit(U value, a<T, U> inner) {
            if (get() != 0 || !compareAndSet(0, 1)) {
                SimpleQueue<U> q = inner.queue;
                if (q == null) {
                    q = new io.reactivex.internal.queue.c<>(this.bufferSize);
                    inner.queue = q;
                }
                q.offer(value);
                if (getAndIncrement() != 0) {
                    return;
                }
            } else {
                this.downstream.onNext(value);
                if (decrementAndGet() == 0) {
                    return;
                }
            }
            drainLoop();
        }

        public void onError(Throwable t) {
            if (this.done) {
                io.reactivex.plugins.a.q(t);
            } else if (this.errors.addThrowable(t)) {
                this.done = true;
                drain();
            } else {
                io.reactivex.plugins.a.q(t);
            }
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                drain();
            }
        }

        public void dispose() {
            Throwable ex;
            if (!this.cancelled) {
                this.cancelled = true;
                if (disposeAll() && (ex = this.errors.terminate()) != null && ex != io.reactivex.internal.util.f.a) {
                    io.reactivex.plugins.a.q(ex);
                }
            }
        }

        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (getAndIncrement() == 0) {
                drainLoop();
            }
        }

        /* access modifiers changed from: package-private */
        public void drainLoop() {
            Observer<? super U> child = this.downstream;
            int missed = 1;
            while (!checkTerminate()) {
                SimplePlainQueue<U> svq = this.queue;
                if (svq != null) {
                    while (!checkTerminate()) {
                        U o = svq.poll();
                        if (o != null) {
                            child.onNext(o);
                        }
                    }
                    return;
                }
                boolean d = this.done;
                io.reactivex.internal.fuseable.f<U> fVar = this.queue;
                ObservableFlatMap.InnerObserver<T, U>[] inner = (a[]) this.observers.get();
                int n = inner.length;
                int nSources = 0;
                if (this.maxConcurrency != Integer.MAX_VALUE) {
                    synchronized (this) {
                        nSources = this.sources.size();
                    }
                }
                if (!d || !((fVar == null || fVar.isEmpty()) && n == 0 && nSources == 0)) {
                    int innerCompleted = 0;
                    if (n != 0) {
                        long startId = this.lastId;
                        int index = this.lastIndex;
                        if (n <= index || inner[index].id != startId) {
                            if (n <= index) {
                                index = 0;
                            }
                            int j = index;
                            int i = 0;
                            while (true) {
                                if (i >= n) {
                                    io.reactivex.internal.fuseable.f<U> fVar2 = fVar;
                                    break;
                                }
                                boolean d2 = d;
                                io.reactivex.internal.fuseable.f<U> fVar3 = fVar;
                                if (inner[j].id == startId) {
                                    break;
                                }
                                j++;
                                if (j == n) {
                                    j = 0;
                                }
                                i++;
                                d = d2;
                                fVar = fVar3;
                            }
                            index = j;
                            this.lastIndex = j;
                            this.lastId = inner[j].id;
                        } else {
                            boolean z = d;
                            io.reactivex.internal.fuseable.f<U> fVar4 = fVar;
                        }
                        int i2 = 0;
                        int j2 = index;
                        int innerCompleted2 = 0;
                        while (i2 < n) {
                            if (!checkTerminate()) {
                                ObservableFlatMap.InnerObserver<T, U> is = inner[j2];
                                SimpleQueue<U> q = is.queue;
                                if (q != null) {
                                    while (true) {
                                        try {
                                            U o2 = q.poll();
                                            if (o2 == null) {
                                                break;
                                            }
                                            child.onNext(o2);
                                            if (checkTerminate()) {
                                                return;
                                            }
                                        } catch (Throwable th) {
                                            Throwable ex = th;
                                            io.reactivex.exceptions.a.b(ex);
                                            is.dispose();
                                            this.errors.addThrowable(ex);
                                            if (!checkTerminate()) {
                                                removeInner(is);
                                                innerCompleted2++;
                                                j2++;
                                                if (j2 == n) {
                                                    j2 = 0;
                                                }
                                            } else {
                                                return;
                                            }
                                        }
                                    }
                                }
                                boolean innerDone = is.done;
                                SimpleQueue<U> innerQueue = is.queue;
                                if (innerDone && (innerQueue == null || innerQueue.isEmpty())) {
                                    removeInner(is);
                                    if (!checkTerminate()) {
                                        innerCompleted2++;
                                    } else {
                                        return;
                                    }
                                }
                                j2++;
                                if (j2 == n) {
                                    j2 = 0;
                                }
                                i2++;
                            } else {
                                return;
                            }
                        }
                        this.lastIndex = j2;
                        this.lastId = inner[j2].id;
                        innerCompleted = innerCompleted2;
                    } else {
                        io.reactivex.internal.fuseable.f<U> fVar5 = fVar;
                    }
                    if (innerCompleted == 0) {
                        missed = addAndGet(-missed);
                        if (missed == 0) {
                            return;
                        }
                    } else if (this.maxConcurrency != Integer.MAX_VALUE) {
                        while (true) {
                            int innerCompleted3 = innerCompleted - 1;
                            if (innerCompleted == 0) {
                                continue;
                                break;
                            }
                            synchronized (this) {
                                ObservableSource<? extends U> p = (o) this.sources.poll();
                                if (p == null) {
                                    this.wip--;
                                } else {
                                    subscribeInner(p);
                                }
                            }
                            innerCompleted = innerCompleted3;
                        }
                        while (true) {
                        }
                    } else {
                        continue;
                    }
                } else {
                    Throwable ex2 = this.errors.terminate();
                    if (ex2 == io.reactivex.internal.util.f.a) {
                        return;
                    }
                    if (ex2 == null) {
                        child.onComplete();
                        return;
                    } else {
                        child.onError(ex2);
                        return;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminate() {
            if (this.cancelled) {
                return true;
            }
            Throwable e = (Throwable) this.errors.get();
            if (this.delayErrors || e == null) {
                return false;
            }
            disposeAll();
            Throwable e2 = this.errors.terminate();
            if (e2 != io.reactivex.internal.util.f.a) {
                this.downstream.onError(e2);
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean disposeAll() {
            ObservableFlatMap.InnerObserver<?, ?>[] a;
            this.upstream.dispose();
            ObservableFlatMap.InnerObserver<?, ?>[] a2 = (a[]) this.observers.get();
            ObservableFlatMap.InnerObserver<?, ?>[] innerObserverArr = CANCELLED;
            if (a2 == innerObserverArr || (a = (a[]) this.observers.getAndSet(innerObserverArr)) == innerObserverArr) {
                return false;
            }
            for (ObservableFlatMap.InnerObserver<?, ?> inner : a) {
                inner.dispose();
            }
            return true;
        }
    }

    /* compiled from: ObservableFlatMap */
    public static final class a<T, U> extends AtomicReference<io.reactivex.disposables.b> implements q<U> {
        private static final long serialVersionUID = -4606175640614850599L;
        volatile boolean done;
        int fusionMode;
        final long id;
        final b<T, U> parent;
        volatile g<U> queue;

        a(b<T, U> parent2, long id2) {
            this.id = id2;
            this.parent = parent2;
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            if (c.setOnce(this, d) && (d instanceof io.reactivex.internal.fuseable.b)) {
                QueueDisposable<U> qd = (io.reactivex.internal.fuseable.b) d;
                int m = qd.requestFusion(7);
                if (m == 1) {
                    this.fusionMode = m;
                    this.queue = qd;
                    this.done = true;
                    this.parent.drain();
                } else if (m == 2) {
                    this.fusionMode = m;
                    this.queue = qd;
                }
            }
        }

        public void onNext(U t) {
            if (this.fusionMode == 0) {
                this.parent.tryEmit(t, this);
            } else {
                this.parent.drain();
            }
        }

        public void onError(Throwable t) {
            if (this.parent.errors.addThrowable(t)) {
                b<T, U> bVar = this.parent;
                if (!bVar.delayErrors) {
                    bVar.disposeAll();
                }
                this.done = true;
                this.parent.drain();
                return;
            }
            io.reactivex.plugins.a.q(t);
        }

        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        public void dispose() {
            c.dispose(this);
        }
    }
}
