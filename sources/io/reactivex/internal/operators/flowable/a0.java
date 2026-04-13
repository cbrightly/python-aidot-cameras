package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.e;
import io.reactivex.h;
import io.reactivex.internal.operators.flowable.FlowableTakeUntil;
import io.reactivex.internal.subscriptions.f;
import io.reactivex.internal.util.g;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableTakeUntil */
public final class a0<T, U> extends a<T, T> {
    final org.reactivestreams.a<? extends U> f;

    public a0(e<T> source, org.reactivestreams.a<? extends U> other) {
        super(source);
        this.f = other;
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> child) {
        FlowableTakeUntil.TakeUntilMainSubscriber<T> parent = new a<>(child);
        child.onSubscribe(parent);
        this.f.a(parent.other);
        this.d.K(parent);
    }

    /* compiled from: FlowableTakeUntil */
    public static final class a<T> extends AtomicInteger implements h<T>, c {
        private static final long serialVersionUID = -4945480365982832967L;
        final b<? super T> downstream;
        final io.reactivex.internal.util.b error = new io.reactivex.internal.util.b();
        final a<T>.defpackage.a other = new C0297a();
        final AtomicLong requested = new AtomicLong();
        final AtomicReference<c> upstream = new AtomicReference<>();

        a(b<? super T> downstream2) {
            this.downstream = downstream2;
        }

        public void onSubscribe(c s) {
            f.deferredSetOnce(this.upstream, this.requested, s);
        }

        public void onNext(T t) {
            g.f(this.downstream, t, this, this.error);
        }

        public void onError(Throwable t) {
            f.cancel(this.other);
            g.d(this.downstream, t, this, this.error);
        }

        public void onComplete() {
            f.cancel(this.other);
            g.b(this.downstream, this, this.error);
        }

        public void request(long n) {
            f.deferredRequest(this.upstream, this.requested, n);
        }

        public void cancel() {
            f.cancel(this.upstream);
            f.cancel(this.other);
        }

        /* renamed from: io.reactivex.internal.operators.flowable.a0$a$a  reason: collision with other inner class name */
        /* compiled from: FlowableTakeUntil */
        public final class C0297a extends AtomicReference<c> implements h<Object> {
            private static final long serialVersionUID = -3592821756711087922L;

            C0297a() {
            }

            public void onSubscribe(c s) {
                f.setOnce(this, s, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE);
            }

            public void onNext(Object t) {
                f.cancel(this);
                onComplete();
            }

            public void onError(Throwable t) {
                f.cancel(a.this.upstream);
                a aVar = a.this;
                g.d(aVar.downstream, t, aVar, aVar.error);
            }

            public void onComplete() {
                f.cancel(a.this.upstream);
                a aVar = a.this;
                g.b(aVar.downstream, aVar, aVar.error);
            }
        }
    }
}
