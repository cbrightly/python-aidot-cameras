package io.reactivex;

import io.reactivex.functions.f;
import io.reactivex.internal.functions.b;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.internal.operators.observable.a0;
import io.reactivex.internal.operators.observable.b0;
import io.reactivex.internal.operators.observable.c;
import io.reactivex.internal.operators.observable.c0;
import io.reactivex.internal.operators.observable.d;
import io.reactivex.internal.operators.observable.d0;
import io.reactivex.internal.operators.observable.e0;
import io.reactivex.internal.operators.observable.f0;
import io.reactivex.internal.operators.observable.g;
import io.reactivex.internal.operators.observable.g0;
import io.reactivex.internal.operators.observable.h;
import io.reactivex.internal.operators.observable.h0;
import io.reactivex.internal.operators.observable.i;
import io.reactivex.internal.operators.observable.i0;
import io.reactivex.internal.operators.observable.j;
import io.reactivex.internal.operators.observable.j0;
import io.reactivex.internal.operators.observable.k;
import io.reactivex.internal.operators.observable.k0;
import io.reactivex.internal.operators.observable.m;
import io.reactivex.internal.operators.observable.n;
import io.reactivex.internal.operators.observable.o;
import io.reactivex.internal.operators.observable.p;
import io.reactivex.internal.operators.observable.q;
import io.reactivex.internal.operators.observable.r;
import io.reactivex.internal.operators.observable.s;
import io.reactivex.internal.operators.observable.t;
import io.reactivex.internal.operators.observable.u;
import io.reactivex.internal.operators.observable.v;
import io.reactivex.internal.operators.observable.z;
import io.reactivex.internal.util.e;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/* compiled from: Observable */
public abstract class l<T> implements o<T> {
    /* access modifiers changed from: protected */
    public abstract void a0(q<? super T> qVar);

    public static int d() {
        return e.b();
    }

    public static <T, R> l<R> f(f<? super Object[], ? extends R> combiner, int bufferSize, o<? extends T>... sources) {
        return g(sources, combiner, bufferSize);
    }

    public static <T, R> l<R> g(o<? extends T>[] sources, f<? super Object[], ? extends R> combiner, int bufferSize) {
        b.e(sources, "sources is null");
        if (sources.length == 0) {
            return q();
        }
        b.e(combiner, "combiner is null");
        b.f(bufferSize, "bufferSize");
        return io.reactivex.plugins.a.m(new io.reactivex.internal.operators.observable.b(sources, (Iterable) null, combiner, bufferSize << 1, false));
    }

    public static <T1, T2, R> l<R> e(o<? extends T1> source1, o<? extends T2> source2, io.reactivex.functions.b<? super T1, ? super T2, ? extends R> combiner) {
        b.e(source1, "source1 is null");
        b.e(source2, "source2 is null");
        return f(io.reactivex.internal.functions.a.f(combiner), d(), source1, source2);
    }

    public static <T> l<T> i(o<? extends o<? extends T>> sources) {
        return j(sources, d());
    }

    public static <T> l<T> j(o<? extends o<? extends T>> sources, int prefetch) {
        b.e(sources, "sources is null");
        b.f(prefetch, "prefetch");
        return io.reactivex.plugins.a.m(new c(sources, io.reactivex.internal.functions.a.c(), prefetch, e.IMMEDIATE));
    }

    public static <T> l<T> k(n<T> source) {
        b.e(source, "source is null");
        return io.reactivex.plugins.a.m(new d(source));
    }

    public static <T> l<T> q() {
        return io.reactivex.plugins.a.m(g.c);
    }

    public static <T> l<T> s(Callable<? extends Throwable> errorSupplier) {
        b.e(errorSupplier, "errorSupplier is null");
        return io.reactivex.plugins.a.m(new h(errorSupplier));
    }

    public static <T> l<T> r(Throwable exception) {
        b.e(exception, "exception is null");
        return s(io.reactivex.internal.functions.a.d(exception));
    }

    public static <T> l<T> y(T... items) {
        b.e(items, "items is null");
        if (items.length == 0) {
            return q();
        }
        if (items.length == 1) {
            return F(items[0]);
        }
        return io.reactivex.plugins.a.m(new k(items));
    }

    public static <T> l<T> z(Iterable<? extends T> source) {
        b.e(source, "source is null");
        return io.reactivex.plugins.a.m(new io.reactivex.internal.operators.observable.l(source));
    }

    public static l<Long> B(long initialDelay, long period, TimeUnit unit, r scheduler) {
        b.e(unit, "unit is null");
        b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.m(new o(Math.max(0, initialDelay), Math.max(0, period), unit, scheduler));
    }

    public static l<Long> C(long period, TimeUnit unit) {
        return B(period, period, unit, io.reactivex.schedulers.a.a());
    }

    public static l<Long> D(long start, long count, long initialDelay, long period, TimeUnit unit) {
        return E(start, count, initialDelay, period, unit, io.reactivex.schedulers.a.a());
    }

    public static l<Long> E(long start, long count, long initialDelay, long period, TimeUnit unit, r scheduler) {
        long j = count;
        long j2 = initialDelay;
        TimeUnit timeUnit = unit;
        r rVar = scheduler;
        if (j < 0) {
            throw new IllegalArgumentException("count >= 0 required but it was " + j);
        } else if (j == 0) {
            return q().m(j2, timeUnit, rVar);
        } else {
            long end = start + (j - 1);
            if (start <= 0 || end >= 0) {
                b.e(timeUnit, "unit is null");
                b.e(rVar, "scheduler is null");
                return io.reactivex.plugins.a.m(new p(start, end, Math.max(0, j2), Math.max(0, period), unit, scheduler));
            }
            throw new IllegalArgumentException("Overflow! start + count is bigger than Long.MAX_VALUE");
        }
    }

    public static <T> l<T> F(T item) {
        b.e(item, "item is null");
        return io.reactivex.plugins.a.m(new q(item));
    }

    public static <T> l<T> H(o<? extends T> source1, o<? extends T> source2) {
        b.e(source1, "source1 is null");
        b.e(source2, "source2 is null");
        return y(source1, source2).w(io.reactivex.internal.functions.a.c(), false, 2);
    }

    public static <T> l<T> I(o<? extends T> source1, o<? extends T> source2) {
        b.e(source1, "source1 is null");
        b.e(source2, "source2 is null");
        return y(source1, source2).w(io.reactivex.internal.functions.a.c(), true, 2);
    }

    public static l<Long> g0(long delay, TimeUnit unit) {
        return h0(delay, unit, io.reactivex.schedulers.a.a());
    }

    public static l<Long> h0(long delay, TimeUnit unit, r scheduler) {
        b.e(unit, "unit is null");
        b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.m(new j0(Math.max(delay, 0), unit, scheduler));
    }

    public static <T> l<T> j0(o<T> source) {
        b.e(source, "source is null");
        if (source instanceof l) {
            return io.reactivex.plugins.a.m((l) source);
        }
        return io.reactivex.plugins.a.m(new m(source));
    }

    public static <T1, T2, R> l<R> k0(o<? extends T1> source1, o<? extends T2> source2, io.reactivex.functions.b<? super T1, ? super T2, ? extends R> zipper) {
        b.e(source1, "source1 is null");
        b.e(source2, "source2 is null");
        return l0(io.reactivex.internal.functions.a.f(zipper), false, d(), source1, source2);
    }

    public static <T, R> l<R> l0(f<? super Object[], ? extends R> zipper, boolean delayError, int bufferSize, o<? extends T>... sources) {
        if (sources.length == 0) {
            return q();
        }
        b.e(zipper, "zipper is null");
        b.f(bufferSize, "bufferSize");
        return io.reactivex.plugins.a.m(new k0(sources, (Iterable) null, zipper, bufferSize, delayError));
    }

    public final <R> l<R> h(p<? super T, ? extends R> composer) {
        return j0(((p) b.e(composer, "composer is null")).a(this));
    }

    public final l<T> l(long delay, TimeUnit unit) {
        return n(delay, unit, io.reactivex.schedulers.a.a(), false);
    }

    public final l<T> m(long delay, TimeUnit unit, r scheduler) {
        return n(delay, unit, scheduler, false);
    }

    public final l<T> n(long delay, TimeUnit unit, r scheduler, boolean delayError) {
        b.e(unit, "unit is null");
        b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.m(new io.reactivex.internal.operators.observable.e(this, delay, unit, scheduler, delayError));
    }

    private l<T> o(io.reactivex.functions.e<? super T> onNext, io.reactivex.functions.e<? super Throwable> onError, io.reactivex.functions.a onComplete, io.reactivex.functions.a onAfterTerminate) {
        b.e(onNext, "onNext is null");
        b.e(onError, "onError is null");
        b.e(onComplete, "onComplete is null");
        b.e(onAfterTerminate, "onAfterTerminate is null");
        return io.reactivex.plugins.a.m(new io.reactivex.internal.operators.observable.f(this, onNext, onError, onComplete, onAfterTerminate));
    }

    public final l<T> p(io.reactivex.functions.e<? super Throwable> onError) {
        io.reactivex.functions.e b = io.reactivex.internal.functions.a.b();
        io.reactivex.functions.a aVar = io.reactivex.internal.functions.a.c;
        return o(b, onError, aVar, aVar);
    }

    public final l<T> t(io.reactivex.functions.h<? super T> predicate) {
        b.e(predicate, "predicate is null");
        return io.reactivex.plugins.a.m(new i(this, predicate));
    }

    public final <R> l<R> u(f<? super T, ? extends o<? extends R>> mapper) {
        return v(mapper, false);
    }

    public final <R> l<R> v(f<? super T, ? extends o<? extends R>> mapper, boolean delayErrors) {
        return w(mapper, delayErrors, Integer.MAX_VALUE);
    }

    public final <R> l<R> w(f<? super T, ? extends o<? extends R>> mapper, boolean delayErrors, int maxConcurrency) {
        return x(mapper, delayErrors, maxConcurrency, d());
    }

    public final <R> l<R> x(f<? super T, ? extends o<? extends R>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        b.e(mapper, "mapper is null");
        b.f(maxConcurrency, "maxConcurrency");
        b.f(bufferSize, "bufferSize");
        if (!(this instanceof io.reactivex.internal.fuseable.e)) {
            return io.reactivex.plugins.a.m(new j(this, mapper, delayErrors, maxConcurrency, bufferSize));
        }
        T v = ((io.reactivex.internal.fuseable.e) this).call();
        if (v == null) {
            return q();
        }
        return b0.a(v, mapper);
    }

    public final b A() {
        return io.reactivex.plugins.a.j(new n(this));
    }

    public final <R> l<R> G(f<? super T, ? extends R> mapper) {
        b.e(mapper, "mapper is null");
        return io.reactivex.plugins.a.m(new r(this, mapper));
    }

    public final l<T> J(r scheduler) {
        return K(scheduler, false, d());
    }

    public final l<T> K(r scheduler, boolean delayError, int bufferSize) {
        b.e(scheduler, "scheduler is null");
        b.f(bufferSize, "bufferSize");
        return io.reactivex.plugins.a.m(new s(this, scheduler, delayError, bufferSize));
    }

    public final l<T> M(f<? super Throwable, ? extends o<? extends T>> resumeFunction) {
        b.e(resumeFunction, "resumeFunction is null");
        return io.reactivex.plugins.a.m(new t(this, resumeFunction, false));
    }

    public final l<T> L(o<? extends T> next) {
        b.e(next, "next is null");
        return M(io.reactivex.internal.functions.a.e(next));
    }

    public final l<T> N(f<? super Throwable, ? extends T> valueSupplier) {
        b.e(valueSupplier, "valueSupplier is null");
        return io.reactivex.plugins.a.m(new u(this, valueSupplier));
    }

    public final io.reactivex.observables.a<T> O() {
        return v.q0(this);
    }

    public final l<T> P(long times) {
        return Q(times, io.reactivex.internal.functions.a.a());
    }

    public final l<T> Q(long times, io.reactivex.functions.h<? super Throwable> predicate) {
        if (times >= 0) {
            b.e(predicate, "predicate is null");
            return io.reactivex.plugins.a.m(new z(this, times, predicate));
        }
        throw new IllegalArgumentException("times >= 0 required but it was " + times);
    }

    public final l<T> R(f<? super l<Throwable>, ? extends o<?>> handler) {
        b.e(handler, "handler is null");
        return io.reactivex.plugins.a.m(new a0(this, handler));
    }

    public final l<T> S() {
        return O().p0();
    }

    public final j<T> T() {
        return io.reactivex.plugins.a.l(new c0(this));
    }

    public final s<T> U() {
        return io.reactivex.plugins.a.n(new d0(this, null));
    }

    public final l<T> V(long count) {
        if (count <= 0) {
            return io.reactivex.plugins.a.m(this);
        }
        return io.reactivex.plugins.a.m(new e0(this, count));
    }

    public final io.reactivex.disposables.b W() {
        return Z(io.reactivex.internal.functions.a.b(), io.reactivex.internal.functions.a.f, io.reactivex.internal.functions.a.c, io.reactivex.internal.functions.a.b());
    }

    public final io.reactivex.disposables.b X(io.reactivex.functions.e<? super T> onNext) {
        return Z(onNext, io.reactivex.internal.functions.a.f, io.reactivex.internal.functions.a.c, io.reactivex.internal.functions.a.b());
    }

    public final io.reactivex.disposables.b Y(io.reactivex.functions.e<? super T> onNext, io.reactivex.functions.e<? super Throwable> onError) {
        return Z(onNext, onError, io.reactivex.internal.functions.a.c, io.reactivex.internal.functions.a.b());
    }

    public final io.reactivex.disposables.b Z(io.reactivex.functions.e<? super T> onNext, io.reactivex.functions.e<? super Throwable> onError, io.reactivex.functions.a onComplete, io.reactivex.functions.e<? super io.reactivex.disposables.b> onSubscribe) {
        b.e(onNext, "onNext is null");
        b.e(onError, "onError is null");
        b.e(onComplete, "onComplete is null");
        b.e(onSubscribe, "onSubscribe is null");
        LambdaObserver<T> ls = new io.reactivex.internal.observers.d<>(onNext, onError, onComplete, onSubscribe);
        a(ls);
        return ls;
    }

    public final void a(q<? super T> observer) {
        b.e(observer, "observer is null");
        try {
            q<? super Object> v = io.reactivex.plugins.a.v(this, observer);
            b.e(v, "The RxJavaPlugins.onSubscribe hook returned a null Observer. Please change the handler provided to RxJavaPlugins.setOnObservableSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
            a0(v);
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable e2) {
            io.reactivex.exceptions.a.b(e2);
            io.reactivex.plugins.a.q(e2);
            NullPointerException npe = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            npe.initCause(e2);
            throw npe;
        }
    }

    public final l<T> b0(r scheduler) {
        b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.m(new f0(this, scheduler));
    }

    public final l<T> c0(long count) {
        if (count >= 0) {
            return io.reactivex.plugins.a.m(new g0(this, count));
        }
        throw new IllegalArgumentException("count >= 0 required but it was " + count);
    }

    public final <U> l<T> d0(o<U> other) {
        b.e(other, "other is null");
        return io.reactivex.plugins.a.m(new h0(this, other));
    }

    public final l<T> e0(long timeout, TimeUnit timeUnit) {
        return f0(timeout, timeUnit, (o) null, io.reactivex.schedulers.a.a());
    }

    private l<T> f0(long timeout, TimeUnit timeUnit, o<? extends T> other, r scheduler) {
        b.e(timeUnit, "timeUnit is null");
        b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.m(new i0(this, timeout, timeUnit, scheduler, other));
    }

    public final e<T> i0(a strategy) {
        Flowable<T> f = new io.reactivex.internal.operators.flowable.k<>(this);
        switch (a.a[strategy.ordinal()]) {
            case 1:
                return f.E();
            case 2:
                return f.F();
            case 3:
                return f;
            case 4:
                return io.reactivex.plugins.a.k(new io.reactivex.internal.operators.flowable.t(f));
            default:
                return f.C();
        }
    }

    /* compiled from: Observable */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[a.values().length];
            a = iArr;
            try {
                iArr[a.DROP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.LATEST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.MISSING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[a.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public final <U, R> l<R> m0(o<? extends U> other, io.reactivex.functions.b<? super T, ? super U, ? extends R> zipper) {
        b.e(other, "other is null");
        return k0(this, other, zipper);
    }
}
