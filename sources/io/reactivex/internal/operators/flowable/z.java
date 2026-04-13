package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.h;
import io.reactivex.internal.operators.flowable.FlowableSubscribeOn;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.r;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableSubscribeOn */
public final class z<T> extends a<T, T> {
    final r f;
    final boolean q;

    public z(e<T> source, r scheduler, boolean nonScheduledRequests) {
        super(source);
        this.f = scheduler;
        this.q = nonScheduledRequests;
    }

    public void L(b<? super T> s) {
        r.c w = this.f.a();
        FlowableSubscribeOn.SubscribeOnSubscriber<T> sos = new a<>(s, w, this.d, this.q);
        s.onSubscribe(sos);
        w.b(sos);
    }

    /* compiled from: FlowableSubscribeOn */
    public static final class a<T> extends AtomicReference<Thread> implements h<T>, c, Runnable {
        private static final long serialVersionUID = 8094547886072529208L;
        final b<? super T> downstream;
        final boolean nonScheduledRequests;
        final AtomicLong requested = new AtomicLong();
        org.reactivestreams.a<T> source;
        final AtomicReference<c> upstream = new AtomicReference<>();
        final r.c worker;

        a(b<? super T> actual, r.c worker2, org.reactivestreams.a<T> source2, boolean requestOn) {
            this.downstream = actual;
            this.worker = worker2;
            this.source = source2;
            this.nonScheduledRequests = !requestOn;
        }

        public void run() {
            lazySet(Thread.currentThread());
            Publisher<T> src = this.source;
            this.source = null;
            src.a(this);
        }

        public void onSubscribe(c s) {
            if (f.setOnce(this.upstream, s)) {
                long r = this.requested.getAndSet(0);
                if (r != 0) {
                    requestUpstream(r, s);
                }
            }
        }

        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        public void onError(Throwable t) {
            this.downstream.onError(t);
            this.worker.dispose();
        }

        public void onComplete() {
            this.downstream.onComplete();
            this.worker.dispose();
        }

        public void request(long n) {
            if (f.validate(n)) {
                c s = this.upstream.get();
                if (s != null) {
                    requestUpstream(n, s);
                    return;
                }
                io.reactivex.internal.util.c.a(this.requested, n);
                c s2 = this.upstream.get();
                if (s2 != null) {
                    long r = this.requested.getAndSet(0);
                    if (r != 0) {
                        requestUpstream(r, s2);
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void requestUpstream(long n, c s) {
            if (this.nonScheduledRequests || Thread.currentThread() == get()) {
                s.request(n);
            } else {
                this.worker.b(new C0300a(s, n));
            }
        }

        public void cancel() {
            f.cancel(this.upstream);
            this.worker.dispose();
        }

        /* renamed from: io.reactivex.internal.operators.flowable.z$a$a  reason: collision with other inner class name */
        /* compiled from: FlowableSubscribeOn */
        public static final class C0300a implements Runnable {
            final c c;
            final long d;

            C0300a(c s, long n) {
                this.c = s;
                this.d = n;
            }

            public void run() {
                this.c.request(this.d);
            }
        }
    }
}
