package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.e;
import io.reactivex.h;
import io.reactivex.internal.subscriptions.f;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableOnBackpressureLatest */
public final class u<T> extends a<T, T> {
    public u(e<T> source) {
        super(source);
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> s) {
        this.d.K(new a(s));
    }

    /* compiled from: FlowableOnBackpressureLatest */
    public static final class a<T> extends AtomicInteger implements h<T>, c {
        private static final long serialVersionUID = 163080509307634843L;
        volatile boolean cancelled;
        final AtomicReference<T> current = new AtomicReference<>();
        volatile boolean done;
        final b<? super T> downstream;
        Throwable error;
        final AtomicLong requested = new AtomicLong();
        c upstream;

        a(b<? super T> downstream2) {
            this.downstream = downstream2;
        }

        public void onSubscribe(c s) {
            if (f.validate(this.upstream, s)) {
                this.upstream = s;
                this.downstream.onSubscribe(this);
                s.request(DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
            }
        }

        public void onNext(T t) {
            this.current.lazySet(t);
            drain();
        }

        public void onError(Throwable t) {
            this.error = t;
            this.done = true;
            drain();
        }

        public void onComplete() {
            this.done = true;
            drain();
        }

        public void request(long n) {
            if (f.validate(n)) {
                io.reactivex.internal.util.c.a(this.requested, n);
                drain();
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.upstream.cancel();
                if (getAndIncrement() == 0) {
                    this.current.lazySet((Object) null);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void drain() {
            boolean z;
            if (getAndIncrement() == 0) {
                Subscriber<? super T> a = this.downstream;
                int missed = 1;
                AtomicLong r = this.requested;
                AtomicReference<T> q = this.current;
                do {
                    long e = 0;
                    while (true) {
                        z = true;
                        if (e == r.get()) {
                            break;
                        }
                        boolean d = this.done;
                        T v = q.getAndSet((Object) null);
                        boolean empty = v == null;
                        if (!checkTerminated(d, empty, a, q)) {
                            if (empty) {
                                break;
                            }
                            a.onNext(v);
                            e++;
                        } else {
                            return;
                        }
                    }
                    if (e == r.get()) {
                        boolean z2 = this.done;
                        if (q.get() != null) {
                            z = false;
                        }
                        if (checkTerminated(z2, z, a, q)) {
                            return;
                        }
                    }
                    if (e != 0) {
                        io.reactivex.internal.util.c.d(r, e);
                    }
                    missed = addAndGet(-missed);
                } while (missed != 0);
            }
        }

        /* access modifiers changed from: package-private */
        public boolean checkTerminated(boolean d, boolean empty, b<?> a, AtomicReference<T> q) {
            if (this.cancelled) {
                q.lazySet((Object) null);
                return true;
            } else if (!d) {
                return false;
            } else {
                Throwable e = this.error;
                if (e != null) {
                    q.lazySet((Object) null);
                    a.onError(e);
                    return true;
                } else if (!empty) {
                    return false;
                } else {
                    a.onComplete();
                    return true;
                }
            }
        }
    }
}
