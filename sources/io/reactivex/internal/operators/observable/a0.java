package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.functions.f;
import io.reactivex.internal.disposables.c;
import io.reactivex.internal.disposables.d;
import io.reactivex.internal.operators.observable.ObservableRetryWhen;
import io.reactivex.internal.util.g;
import io.reactivex.l;
import io.reactivex.o;
import io.reactivex.q;
import io.reactivex.subjects.Subject;
import io.reactivex.subjects.b;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: ObservableRetryWhen */
public final class a0<T> extends a<T, T> {
    final f<? super l<Throwable>, ? extends o<?>> d;

    public a0(o<T> source, f<? super l<Throwable>, ? extends o<?>> handler) {
        super(source);
        this.d = handler;
    }

    /* access modifiers changed from: protected */
    public void a0(q<? super T> observer) {
        Subject<Throwable> signaller = b.p0().n0();
        try {
            ObservableSource<?> other = (o) io.reactivex.internal.functions.b.e(this.d.apply(signaller), "The handler returned a null ObservableSource");
            ObservableRetryWhen.RepeatWhenObserver<T> parent = new a<>(observer, signaller, this.c);
            observer.onSubscribe(parent);
            other.a(parent.inner);
            parent.subscribeNext();
        } catch (Throwable ex) {
            io.reactivex.exceptions.a.b(ex);
            d.error(ex, (q<?>) observer);
        }
    }

    /* compiled from: ObservableRetryWhen */
    public static final class a<T> extends AtomicInteger implements q<T>, io.reactivex.disposables.b {
        private static final long serialVersionUID = 802743776666017014L;
        volatile boolean active;
        final q<? super T> downstream;
        final io.reactivex.internal.util.b error = new io.reactivex.internal.util.b();
        final a<T>.defpackage.a inner = new C0301a();
        final io.reactivex.subjects.d<Throwable> signaller;
        final o<T> source;
        final AtomicReference<io.reactivex.disposables.b> upstream = new AtomicReference<>();
        final AtomicInteger wip = new AtomicInteger();

        a(q<? super T> actual, io.reactivex.subjects.d<Throwable> signaller2, o<T> source2) {
            this.downstream = actual;
            this.signaller = signaller2;
            this.source = source2;
        }

        public void onSubscribe(io.reactivex.disposables.b d) {
            c.replace(this.upstream, d);
        }

        public void onNext(T t) {
            g.e(this.downstream, t, this, this.error);
        }

        public void onError(Throwable e) {
            c.replace(this.upstream, (io.reactivex.disposables.b) null);
            this.active = false;
            this.signaller.onNext(e);
        }

        public void onComplete() {
            c.dispose(this.inner);
            g.a(this.downstream, this, this.error);
        }

        public boolean isDisposed() {
            return c.isDisposed(this.upstream.get());
        }

        public void dispose() {
            c.dispose(this.upstream);
            c.dispose(this.inner);
        }

        /* access modifiers changed from: package-private */
        public void innerNext() {
            subscribeNext();
        }

        /* access modifiers changed from: package-private */
        public void innerError(Throwable ex) {
            c.dispose(this.upstream);
            g.c(this.downstream, ex, this, this.error);
        }

        /* access modifiers changed from: package-private */
        public void innerComplete() {
            c.dispose(this.upstream);
            g.a(this.downstream, this, this.error);
        }

        /* access modifiers changed from: package-private */
        public void subscribeNext() {
            if (this.wip.getAndIncrement() == 0) {
                while (!isDisposed()) {
                    if (!this.active) {
                        this.active = true;
                        this.source.a(this);
                    }
                    if (this.wip.decrementAndGet() == 0) {
                        return;
                    }
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.observable.a0$a$a  reason: collision with other inner class name */
        /* compiled from: ObservableRetryWhen */
        public final class C0301a extends AtomicReference<io.reactivex.disposables.b> implements q<Object> {
            private static final long serialVersionUID = 3254781284376480842L;

            C0301a() {
            }

            public void onSubscribe(io.reactivex.disposables.b d) {
                c.setOnce(this, d);
            }

            public void onNext(Object t) {
                a.this.innerNext();
            }

            public void onError(Throwable e) {
                a.this.innerError(e);
            }

            public void onComplete() {
                a.this.innerComplete();
            }
        }
    }
}
