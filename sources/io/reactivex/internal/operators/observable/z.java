package io.reactivex.internal.operators.observable;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import io.reactivex.disposables.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.functions.h;
import io.reactivex.internal.disposables.g;
import io.reactivex.l;
import io.reactivex.o;
import io.reactivex.q;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: ObservableRetryPredicate */
public final class z<T> extends a<T, T> {
    final h<? super Throwable> d;
    final long f;

    public z(l<T> source, long count, h<? super Throwable> predicate) {
        super(source);
        this.d = predicate;
        this.f = count;
    }

    public void a0(q<? super T> observer) {
        g sa = new g();
        observer.onSubscribe(sa);
        new a<>(observer, this.f, this.d, sa, this.c).subscribeNext();
    }

    /* compiled from: ObservableRetryPredicate */
    public static final class a<T> extends AtomicInteger implements q<T> {
        private static final long serialVersionUID = -7098360935104053232L;
        final q<? super T> downstream;
        final h<? super Throwable> predicate;
        long remaining;
        final o<? extends T> source;
        final g upstream;

        a(q<? super T> actual, long count, h<? super Throwable> predicate2, g sa, o<? extends T> source2) {
            this.downstream = actual;
            this.upstream = sa;
            this.source = source2;
            this.predicate = predicate2;
            this.remaining = count;
        }

        public void onSubscribe(b d) {
            this.upstream.replace(d);
        }

        public void onNext(T t) {
            this.downstream.onNext(t);
        }

        public void onError(Throwable t) {
            long r = this.remaining;
            if (r != DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE) {
                this.remaining = r - 1;
            }
            if (r == 0) {
                this.downstream.onError(t);
                return;
            }
            try {
                if (!this.predicate.test(t)) {
                    this.downstream.onError(t);
                } else {
                    subscribeNext();
                }
            } catch (Throwable e) {
                io.reactivex.exceptions.a.b(e);
                this.downstream.onError(new CompositeException(t, e));
            }
        }

        public void onComplete() {
            this.downstream.onComplete();
        }

        /* access modifiers changed from: package-private */
        public void subscribeNext() {
            if (getAndIncrement() == 0) {
                int missed = 1;
                while (!this.upstream.isDisposed()) {
                    this.source.a(this);
                    missed = addAndGet(-missed);
                    if (missed == 0) {
                        return;
                    }
                }
            }
        }
    }
}
