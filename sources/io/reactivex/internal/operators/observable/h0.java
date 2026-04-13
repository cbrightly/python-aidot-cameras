package io.reactivex.internal.operators.observable;

import io.reactivex.disposables.b;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.operators.observable.ObservableTakeUntil;
import io.reactivex.internal.util.g;
import io.reactivex.o;
import io.reactivex.q;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableTakeUntil */
public final class h0<T, U> extends a<T, T> {
    final o<? extends U> d;

    public h0(o<T> source, o<? extends U> other) {
        super(source);
        this.d = other;
    }

    public void a0(q<? super T> child) {
        ObservableTakeUntil.TakeUntilMainObserver<T, U> parent = new a<>(child);
        child.onSubscribe(parent);
        this.d.a(parent.otherObserver);
        this.c.a(parent);
    }

    /* compiled from: ObservableTakeUntil */
    public static final class a<T, U> extends AtomicInteger implements q<T>, b {
        private static final long serialVersionUID = 1418547743690811973L;
        final q<? super T> downstream;
        final io.reactivex.internal.util.b error = new io.reactivex.internal.util.b();
        final a<T, U>.defpackage.a otherObserver = new C0305a();
        final AtomicReference<b> upstream = new AtomicReference<>();

        a(q<? super T> downstream2) {
            this.downstream = downstream2;
        }

        public void dispose() {
            c.dispose(this.upstream);
            c.dispose(this.otherObserver);
        }

        public boolean isDisposed() {
            return c.isDisposed(this.upstream.get());
        }

        public void onSubscribe(b d) {
            c.setOnce(this.upstream, d);
        }

        public void onNext(T t) {
            g.e(this.downstream, t, this, this.error);
        }

        public void onError(Throwable e) {
            c.dispose(this.otherObserver);
            g.c(this.downstream, e, this, this.error);
        }

        public void onComplete() {
            c.dispose(this.otherObserver);
            g.a(this.downstream, this, this.error);
        }

        /* access modifiers changed from: package-private */
        public void otherError(Throwable e) {
            c.dispose(this.upstream);
            g.c(this.downstream, e, this, this.error);
        }

        /* access modifiers changed from: package-private */
        public void otherComplete() {
            c.dispose(this.upstream);
            g.a(this.downstream, this, this.error);
        }

        /* renamed from: io.reactivex.internal.operators.observable.h0$a$a  reason: collision with other inner class name */
        /* compiled from: ObservableTakeUntil */
        public final class C0305a extends AtomicReference<b> implements q<U> {
            private static final long serialVersionUID = -8693423678067375039L;

            C0305a() {
            }

            public void onSubscribe(b d) {
                c.setOnce(this, d);
            }

            public void onNext(U u) {
                c.dispose(this);
                a.this.otherComplete();
            }

            public void onError(Throwable e) {
                a.this.otherError(e);
            }

            public void onComplete() {
                a.this.otherComplete();
            }
        }
    }
}
