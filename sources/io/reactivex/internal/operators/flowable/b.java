package io.reactivex.internal.operators.flowable;

import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.operators.flowable.FlowableCreate;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

/* compiled from: FlowableCreate */
public final class b<T> extends io.reactivex.e<T> {
    final io.reactivex.g<T> d;
    final io.reactivex.a f;

    public b(io.reactivex.g<T> source, io.reactivex.a backpressure) {
        this.d = source;
        this.f = backpressure;
    }

    /* compiled from: FlowableCreate */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[io.reactivex.a.values().length];
            a = iArr;
            try {
                iArr[io.reactivex.a.MISSING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[io.reactivex.a.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[io.reactivex.a.DROP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[io.reactivex.a.LATEST.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void L(org.reactivestreams.b<? super T> t) {
        FlowableCreate.BaseEmitter<T> emitter;
        switch (a.a[this.f.ordinal()]) {
            case 1:
                emitter = new g<>(t);
                break;
            case 2:
                emitter = new e<>(t);
                break;
            case 3:
                emitter = new d<>(t);
                break;
            case 4:
                emitter = new f<>(t);
                break;
            default:
                emitter = new c<>(t, io.reactivex.e.b());
                break;
        }
        t.onSubscribe(emitter);
        try {
            this.d.subscribe(emitter);
        } catch (Throwable ex) {
            io.reactivex.exceptions.a.b(ex);
            emitter.onError(ex);
        }
    }

    /* compiled from: FlowableCreate */
    public static final class i<T> extends AtomicInteger implements io.reactivex.f<T> {
        private static final long serialVersionUID = 4883307006032401862L;
        volatile boolean done;
        final C0298b<T> emitter;
        final io.reactivex.internal.util.b error = new io.reactivex.internal.util.b();
        final io.reactivex.internal.fuseable.f<T> queue = new io.reactivex.internal.queue.c(16);

        i(C0298b<T> emitter2) {
            this.emitter = emitter2;
        }

        public void onNext(T t) {
            if (!this.emitter.isCancelled() && !this.done) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                if (get() != 0 || !compareAndSet(0, 1)) {
                    SimplePlainQueue<T> q = this.queue;
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
            if (this.emitter.isCancelled() || this.done) {
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
            if (!this.emitter.isCancelled() && !this.done) {
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
            FlowableCreate.BaseEmitter<T> e = this.emitter;
            SimplePlainQueue<T> q = this.queue;
            io.reactivex.internal.util.b error2 = this.error;
            int missed = 1;
            while (!e.isCancelled()) {
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

        public long requested() {
            return this.emitter.requested();
        }

        public boolean isCancelled() {
            return this.emitter.isCancelled();
        }

        public io.reactivex.f<T> serialize() {
            return this;
        }

        public String toString() {
            return this.emitter.toString();
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.b$b  reason: collision with other inner class name */
    /* compiled from: FlowableCreate */
    public static abstract class C0298b<T> extends AtomicLong implements io.reactivex.f<T>, org.reactivestreams.c {
        private static final long serialVersionUID = 7326289992464377023L;
        final org.reactivestreams.b<? super T> downstream;
        final io.reactivex.internal.disposables.g serial = new io.reactivex.internal.disposables.g();

        public abstract /* synthetic */ void onNext(T t);

        C0298b(org.reactivestreams.b<? super T> downstream2) {
            this.downstream = downstream2;
        }

        public void onComplete() {
            complete();
        }

        /* access modifiers changed from: protected */
        public void complete() {
            if (!isCancelled()) {
                try {
                    this.downstream.onComplete();
                } finally {
                    this.serial.dispose();
                }
            }
        }

        public final void onError(Throwable e) {
            if (!tryOnError(e)) {
                io.reactivex.plugins.a.q(e);
            }
        }

        public boolean tryOnError(Throwable e) {
            return error(e);
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: protected */
        public boolean error(Throwable e) {
            if (e == null) {
                e = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (isCancelled()) {
                return false;
            }
            try {
                this.downstream.onError(e);
                this.serial.dispose();
                return true;
            } catch (Throwable th) {
                this.serial.dispose();
                throw th;
            }
        }

        public final void cancel() {
            this.serial.dispose();
            onUnsubscribed();
        }

        /* access modifiers changed from: package-private */
        public void onUnsubscribed() {
        }

        public final boolean isCancelled() {
            return this.serial.isDisposed();
        }

        public final void request(long n) {
            if (io.reactivex.internal.subscriptions.f.validate(n)) {
                io.reactivex.internal.util.c.a(this, n);
                onRequested();
            }
        }

        /* access modifiers changed from: package-private */
        public void onRequested() {
        }

        public final void setDisposable(io.reactivex.disposables.b d) {
            this.serial.update(d);
        }

        public final void setCancellable(io.reactivex.functions.d c) {
            setDisposable(new io.reactivex.internal.disposables.a(c));
        }

        public final long requested() {
            return get();
        }

        public final io.reactivex.f<T> serialize() {
            return new i(this);
        }

        public String toString() {
            return String.format("%s{%s}", new Object[]{getClass().getSimpleName(), super.toString()});
        }
    }

    /* compiled from: FlowableCreate */
    public static final class g<T> extends C0298b<T> {
        private static final long serialVersionUID = 3776720187248809713L;

        g(org.reactivestreams.b<? super T> downstream) {
            super(downstream);
        }

        public void onNext(T t) {
            long r;
            if (!isCancelled()) {
                if (t != null) {
                    this.downstream.onNext(t);
                    do {
                        r = get();
                        if (r == 0 || compareAndSet(r, r - 1)) {
                            return;
                        }
                        r = get();
                        return;
                    } while (compareAndSet(r, r - 1));
                    return;
                }
                onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
            }
        }
    }

    /* compiled from: FlowableCreate */
    public static abstract class h<T> extends C0298b<T> {
        private static final long serialVersionUID = 4127754106204442833L;

        /* access modifiers changed from: package-private */
        public abstract void onOverflow();

        h(org.reactivestreams.b<? super T> downstream) {
            super(downstream);
        }

        public final void onNext(T t) {
            if (!isCancelled()) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                } else if (get() != 0) {
                    this.downstream.onNext(t);
                    io.reactivex.internal.util.c.d(this, 1);
                } else {
                    onOverflow();
                }
            }
        }
    }

    /* compiled from: FlowableCreate */
    public static final class d<T> extends h<T> {
        private static final long serialVersionUID = 8360058422307496563L;

        d(org.reactivestreams.b<? super T> downstream) {
            super(downstream);
        }

        /* access modifiers changed from: package-private */
        public void onOverflow() {
        }
    }

    /* compiled from: FlowableCreate */
    public static final class e<T> extends h<T> {
        private static final long serialVersionUID = 338953216916120960L;

        e(org.reactivestreams.b<? super T> downstream) {
            super(downstream);
        }

        /* access modifiers changed from: package-private */
        public void onOverflow() {
            onError(new MissingBackpressureException("create: could not emit value due to lack of requests"));
        }
    }

    /* compiled from: FlowableCreate */
    public static final class c<T> extends C0298b<T> {
        private static final long serialVersionUID = 2427151001689639875L;
        volatile boolean done;
        Throwable error;
        final io.reactivex.internal.queue.c<T> queue;
        final AtomicInteger wip = new AtomicInteger();

        c(org.reactivestreams.b<? super T> actual, int capacityHint) {
            super(actual);
            this.queue = new io.reactivex.internal.queue.c<>(capacityHint);
        }

        public void onNext(T t) {
            if (!this.done && !isCancelled()) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                this.queue.offer(t);
                drain();
            }
        }

        public boolean tryOnError(Throwable e) {
            if (this.done || isCancelled()) {
                return false;
            }
            if (e == null) {
                e = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            this.error = e;
            this.done = true;
            drain();
            return true;
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        /* access modifiers changed from: package-private */
        public void onRequested() {
            drain();
        }

        /* access modifiers changed from: package-private */
        public void onUnsubscribed() {
            if (this.wip.getAndIncrement() == 0) {
                this.queue.clear();
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            if (this.wip.getAndIncrement() == 0) {
                int missed = 1;
                Subscriber<? super T> a = this.downstream;
                SpscLinkedArrayQueue<T> q = this.queue;
                do {
                    long r = get();
                    long e = 0;
                    while (e != r) {
                        if (isCancelled()) {
                            q.clear();
                            return;
                        }
                        boolean d = this.done;
                        T o = q.poll();
                        boolean empty = o == null;
                        if (d && empty) {
                            Throwable ex = this.error;
                            if (ex != null) {
                                error(ex);
                                return;
                            } else {
                                complete();
                                return;
                            }
                        } else if (empty) {
                            break;
                        } else {
                            a.onNext(o);
                            e++;
                        }
                    }
                    if (e == r) {
                        if (isCancelled()) {
                            q.clear();
                            return;
                        }
                        boolean d2 = this.done;
                        boolean empty2 = q.isEmpty();
                        if (d2 && empty2) {
                            Throwable ex2 = this.error;
                            if (ex2 != null) {
                                error(ex2);
                                return;
                            } else {
                                complete();
                                return;
                            }
                        }
                    }
                    if (e != 0) {
                        io.reactivex.internal.util.c.d(this, e);
                    }
                    missed = this.wip.addAndGet(-missed);
                } while (missed != 0);
            }
        }
    }

    /* compiled from: FlowableCreate */
    public static final class f<T> extends C0298b<T> {
        private static final long serialVersionUID = 4023437720691792495L;
        volatile boolean done;
        Throwable error;
        final AtomicReference<T> queue = new AtomicReference<>();
        final AtomicInteger wip = new AtomicInteger();

        f(org.reactivestreams.b<? super T> downstream) {
            super(downstream);
        }

        public void onNext(T t) {
            if (!this.done && !isCancelled()) {
                if (t == null) {
                    onError(new NullPointerException("onNext called with null. Null values are generally not allowed in 2.x operators and sources."));
                    return;
                }
                this.queue.set(t);
                drain();
            }
        }

        public boolean tryOnError(Throwable e) {
            if (this.done || isCancelled()) {
                return false;
            }
            if (e == null) {
                onError(new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources."));
            }
            this.error = e;
            this.done = true;
            drain();
            return true;
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        /* access modifiers changed from: package-private */
        public void onRequested() {
            drain();
        }

        /* access modifiers changed from: package-private */
        public void onUnsubscribed() {
            if (this.wip.getAndIncrement() == 0) {
                this.queue.lazySet((Object) null);
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            boolean empty;
            if (this.wip.getAndIncrement() == 0) {
                int missed = 1;
                Subscriber<? super T> a = this.downstream;
                AtomicReference<T> q = this.queue;
                do {
                    long r = get();
                    long e = 0;
                    while (true) {
                        empty = true;
                        if (e == r) {
                            break;
                        } else if (isCancelled()) {
                            q.lazySet((Object) null);
                            return;
                        } else {
                            boolean d = this.done;
                            T o = q.getAndSet((Object) null);
                            boolean empty2 = o == null;
                            if (d && empty2) {
                                Throwable ex = this.error;
                                if (ex != null) {
                                    error(ex);
                                    return;
                                } else {
                                    complete();
                                    return;
                                }
                            } else if (empty2) {
                                break;
                            } else {
                                a.onNext(o);
                                e++;
                            }
                        }
                    }
                    if (e == r) {
                        if (isCancelled()) {
                            q.lazySet((Object) null);
                            return;
                        }
                        boolean d2 = this.done;
                        if (q.get() != null) {
                            empty = false;
                        }
                        if (d2 && empty) {
                            Throwable ex2 = this.error;
                            if (ex2 != null) {
                                error(ex2);
                                return;
                            } else {
                                complete();
                                return;
                            }
                        }
                    }
                    if (e != 0) {
                        io.reactivex.internal.util.c.d(this, e);
                    }
                    missed = this.wip.addAndGet(-missed);
                } while (missed != 0);
            }
        }
    }
}
