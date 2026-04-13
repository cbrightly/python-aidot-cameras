package io.reactivex.internal.operators.flowable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.functions.e;
import io.reactivex.h;
import io.reactivex.internal.subscriptions.f;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.b;
import org.reactivestreams.c;

/* compiled from: FlowableOnBackpressureDrop */
public final class s<T> extends a<T, T> implements e<T> {
    final e<? super T> f = this;

    public s(io.reactivex.e<T> source) {
        super(source);
    }

    public void accept(T t) {
    }

    /* access modifiers changed from: protected */
    public void L(b<? super T> s) {
        this.d.K(new a(s, this.f));
    }

    /* compiled from: FlowableOnBackpressureDrop */
    public static final class a<T> extends AtomicLong implements h<T>, c {
        private static final long serialVersionUID = -6246093802440953054L;
        boolean done;
        final b<? super T> downstream;
        final e<? super T> onDrop;
        c upstream;

        a(b<? super T> actual, e<? super T> onDrop2) {
            this.downstream = actual;
            this.onDrop = onDrop2;
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
                try {
                    this.onDrop.accept(t);
                } catch (Throwable e) {
                    io.reactivex.exceptions.a.b(e);
                    cancel();
                    onError(e);
                }
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
