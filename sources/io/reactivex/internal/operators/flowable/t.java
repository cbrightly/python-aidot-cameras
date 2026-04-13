package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.e;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.h;
import io.reactivex.internal.subscriptions.f;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableOnBackpressureError */
public final class t<T> extends a<T, T> {
    public t(e<T> source) {
        super(source);
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> s) {
        this.d.K(new a(s));
    }

    /* compiled from: FlowableOnBackpressureError */
    public static final class a<T> extends AtomicLong implements h<T>, c {
        private static final long serialVersionUID = -3176480756392482682L;
        boolean done;
        final b<? super T> downstream;
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
            if (!this.done) {
                if (get() != 0) {
                    this.downstream.onNext(t);
                    io.reactivex.internal.util.c.d(this, 1);
                    return;
                }
                onError(new MissingBackpressureException("could not emit value due to lack of requests"));
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                io.reactivex.plugins.a.q(t);
                return;
            }
            this.done = true;
            this.downstream.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.downstream.onComplete();
            }
        }

        public void request(long n) {
            if (f.validate(n)) {
                io.reactivex.internal.util.c.a(this, n);
            }
        }

        public void cancel() {
            this.upstream.cancel();
        }
    }
}
