package io.reactivex;

import io.reactivex.functions.f;
import io.reactivex.internal.operators.flowable.a0;
import io.reactivex.internal.operators.flowable.b0;
import io.reactivex.internal.operators.flowable.c;
import io.reactivex.internal.operators.flowable.c0;
import io.reactivex.internal.operators.flowable.d;
import io.reactivex.internal.operators.flowable.d0;
import io.reactivex.internal.operators.flowable.g;
import io.reactivex.internal.operators.flowable.h;
import io.reactivex.internal.operators.flowable.i;
import io.reactivex.internal.operators.flowable.j;
import io.reactivex.internal.operators.flowable.l;
import io.reactivex.internal.operators.flowable.m;
import io.reactivex.internal.operators.flowable.n;
import io.reactivex.internal.operators.flowable.o;
import io.reactivex.internal.operators.flowable.p;
import io.reactivex.internal.operators.flowable.q;
import io.reactivex.internal.operators.flowable.r;
import io.reactivex.internal.operators.flowable.s;
import io.reactivex.internal.operators.flowable.u;
import io.reactivex.internal.operators.flowable.x;
import io.reactivex.internal.operators.flowable.y;
import io.reactivex.internal.operators.flowable.z;
import io.reactivex.internal.subscribers.LambdaSubscriber;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.a;
import org.reactivestreams.b;

/* compiled from: Flowable */
public abstract class e<T> implements a<T> {
    static final int c = Math.max(1, Integer.getInteger("rx2.buffer-size", 128).intValue());

    /* access modifiers changed from: protected */
    public abstract void L(b<? super T> bVar);

    public static int b() {
        return c;
    }

    public static <T> e<T> d(g<T> source, a mode) {
        io.reactivex.internal.functions.b.e(source, "source is null");
        io.reactivex.internal.functions.b.e(mode, "mode is null");
        return io.reactivex.plugins.a.k(new io.reactivex.internal.operators.flowable.b(source, mode));
    }

    public static <T> e<T> l() {
        return io.reactivex.plugins.a.k(g.d);
    }

    public static <T> e<T> n(Callable<? extends Throwable> supplier) {
        io.reactivex.internal.functions.b.e(supplier, "supplier is null");
        return io.reactivex.plugins.a.k(new h(supplier));
    }

    public static <T> e<T> m(Throwable throwable) {
        io.reactivex.internal.functions.b.e(throwable, "throwable is null");
        return n(io.reactivex.internal.functions.a.d(throwable));
    }

    public static <T> e<T> r(T... items) {
        io.reactivex.internal.functions.b.e(items, "items is null");
        if (items.length == 0) {
            return l();
        }
        if (items.length == 1) {
            return w(items[0]);
        }
        return io.reactivex.plugins.a.k(new j(items));
    }

    public static <T> e<T> s(a<? extends T> source) {
        if (source instanceof e) {
            return io.reactivex.plugins.a.k((e) source);
        }
        io.reactivex.internal.functions.b.e(source, "source is null");
        return io.reactivex.plugins.a.k(new l(source));
    }

    public static e<Long> t(long initialDelay, long period, TimeUnit unit, r scheduler) {
        io.reactivex.internal.functions.b.e(unit, "unit is null");
        io.reactivex.internal.functions.b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.k(new n(Math.max(0, initialDelay), Math.max(0, period), unit, scheduler));
    }

    public static e<Long> u(long period, TimeUnit unit) {
        return t(period, period, unit, io.reactivex.schedulers.a.a());
    }

    public static e<Long> v(long period, TimeUnit unit, r scheduler) {
        return t(period, period, unit, scheduler);
    }

    public static <T> e<T> w(T item) {
        io.reactivex.internal.functions.b.e(item, "item is null");
        return io.reactivex.plugins.a.k(new o(item));
    }

    public static <T> e<T> y(a<? extends T> source1, a<? extends T> source2) {
        io.reactivex.internal.functions.b.e(source1, "source1 is null");
        io.reactivex.internal.functions.b.e(source2, "source2 is null");
        return r(source1, source2).p(io.reactivex.internal.functions.a.c(), false, 2);
    }

    public static e<Long> R(long delay, TimeUnit unit) {
        return S(delay, unit, io.reactivex.schedulers.a.a());
    }

    public static e<Long> S(long delay, TimeUnit unit, r scheduler) {
        io.reactivex.internal.functions.b.e(unit, "unit is null");
        io.reactivex.internal.functions.b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.k(new c0(Math.max(0, delay), unit, scheduler));
    }

    public static <T1, T2, R> e<R> T(a<? extends T1> source1, a<? extends T2> source2, io.reactivex.functions.b<? super T1, ? super T2, ? extends R> zipper) {
        io.reactivex.internal.functions.b.e(source1, "source1 is null");
        io.reactivex.internal.functions.b.e(source2, "source2 is null");
        return U(io.reactivex.internal.functions.a.f(zipper), false, b(), source1, source2);
    }

    public static <T, R> e<R> U(f<? super Object[], ? extends R> zipper, boolean delayError, int bufferSize, a<? extends T>... sources) {
        if (sources.length == 0) {
            return l();
        }
        io.reactivex.internal.functions.b.e(zipper, "zipper is null");
        io.reactivex.internal.functions.b.f(bufferSize, "bufferSize");
        return io.reactivex.plugins.a.k(new d0(sources, (Iterable) null, zipper, bufferSize, delayError));
    }

    public final <R> e<R> c(i<? super T, ? extends R> composer) {
        return s(((i) io.reactivex.internal.functions.b.e(composer, "composer is null")).b(this));
    }

    public final e<T> e(long timeout, TimeUnit unit) {
        return f(timeout, unit, io.reactivex.schedulers.a.a());
    }

    public final e<T> f(long timeout, TimeUnit unit, r scheduler) {
        io.reactivex.internal.functions.b.e(unit, "unit is null");
        io.reactivex.internal.functions.b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.k(new c(this, timeout, unit, scheduler));
    }

    public final e<T> g(long delay, TimeUnit unit) {
        return h(delay, unit, io.reactivex.schedulers.a.a(), false);
    }

    public final e<T> h(long delay, TimeUnit unit, r scheduler, boolean delayError) {
        io.reactivex.internal.functions.b.e(unit, "unit is null");
        io.reactivex.internal.functions.b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.k(new d(this, Math.max(0, delay), unit, scheduler, delayError));
    }

    public final e<T> i() {
        return j(io.reactivex.internal.functions.a.c());
    }

    public final <K> e<T> j(f<? super T, K> keySelector) {
        io.reactivex.internal.functions.b.e(keySelector, "keySelector is null");
        return io.reactivex.plugins.a.k(new io.reactivex.internal.operators.flowable.e(this, keySelector, io.reactivex.internal.functions.b.d()));
    }

    public final e<T> k(io.reactivex.functions.a onFinally) {
        io.reactivex.internal.functions.b.e(onFinally, "onFinally is null");
        return io.reactivex.plugins.a.k(new io.reactivex.internal.operators.flowable.f(this, onFinally));
    }

    public final <R> e<R> o(f<? super T, ? extends a<? extends R>> mapper) {
        return q(mapper, false, b(), b());
    }

    public final <R> e<R> p(f<? super T, ? extends a<? extends R>> mapper, boolean delayErrors, int maxConcurrency) {
        return q(mapper, delayErrors, maxConcurrency, b());
    }

    public final <R> e<R> q(f<? super T, ? extends a<? extends R>> mapper, boolean delayErrors, int maxConcurrency, int bufferSize) {
        io.reactivex.internal.functions.b.e(mapper, "mapper is null");
        io.reactivex.internal.functions.b.f(maxConcurrency, "maxConcurrency");
        io.reactivex.internal.functions.b.f(bufferSize, "bufferSize");
        if (!(this instanceof io.reactivex.internal.fuseable.e)) {
            return io.reactivex.plugins.a.k(new i(this, mapper, delayErrors, maxConcurrency, bufferSize));
        }
        T v = ((io.reactivex.internal.fuseable.e) this).call();
        if (v == null) {
            return l();
        }
        return y.a(v, mapper);
    }

    public final <R> e<R> x(f<? super T, ? extends R> mapper) {
        io.reactivex.internal.functions.b.e(mapper, "mapper is null");
        return io.reactivex.plugins.a.k(new p(this, mapper));
    }

    public final e<T> z(a<? extends T> other) {
        io.reactivex.internal.functions.b.e(other, "other is null");
        return y(this, other);
    }

    public final e<T> A(r scheduler) {
        return B(scheduler, false, b());
    }

    public final e<T> B(r scheduler, boolean delayError, int bufferSize) {
        io.reactivex.internal.functions.b.e(scheduler, "scheduler is null");
        io.reactivex.internal.functions.b.f(bufferSize, "bufferSize");
        return io.reactivex.plugins.a.k(new q(this, scheduler, delayError, bufferSize));
    }

    public final e<T> C() {
        return D(b(), false, true);
    }

    public final e<T> D(int capacity, boolean delayError, boolean unbounded) {
        io.reactivex.internal.functions.b.f(capacity, "capacity");
        return io.reactivex.plugins.a.k(new r(this, capacity, unbounded, delayError, io.reactivex.internal.functions.a.c));
    }

    public final e<T> E() {
        return io.reactivex.plugins.a.k(new s(this));
    }

    public final e<T> F() {
        return io.reactivex.plugins.a.k(new u(this));
    }

    public final e<T> G(f<? super e<Throwable>, ? extends a<?>> handler) {
        io.reactivex.internal.functions.b.e(handler, "handler is null");
        return io.reactivex.plugins.a.k(new x(this, handler));
    }

    public final io.reactivex.disposables.b H(io.reactivex.functions.e<? super T> onNext) {
        return J(onNext, io.reactivex.internal.functions.a.f, io.reactivex.internal.functions.a.c, m.INSTANCE);
    }

    public final io.reactivex.disposables.b I(io.reactivex.functions.e<? super T> onNext, io.reactivex.functions.e<? super Throwable> onError) {
        return J(onNext, onError, io.reactivex.internal.functions.a.c, m.INSTANCE);
    }

    public final io.reactivex.disposables.b J(io.reactivex.functions.e<? super T> onNext, io.reactivex.functions.e<? super Throwable> onError, io.reactivex.functions.a onComplete, io.reactivex.functions.e<? super org.reactivestreams.c> onSubscribe) {
        io.reactivex.internal.functions.b.e(onNext, "onNext is null");
        io.reactivex.internal.functions.b.e(onError, "onError is null");
        io.reactivex.internal.functions.b.e(onComplete, "onComplete is null");
        io.reactivex.internal.functions.b.e(onSubscribe, "onSubscribe is null");
        LambdaSubscriber<T> ls = new io.reactivex.internal.subscribers.c<>(onNext, onError, onComplete, onSubscribe);
        K(ls);
        return ls;
    }

    public final void a(b<? super T> s) {
        if (s instanceof h) {
            K((h) s);
            return;
        }
        io.reactivex.internal.functions.b.e(s, "s is null");
        K(new io.reactivex.internal.subscribers.d(s));
    }

    public final void K(h<? super T> s) {
        io.reactivex.internal.functions.b.e(s, "s is null");
        try {
            Subscriber<? super T> z = io.reactivex.plugins.a.w(this, s);
            io.reactivex.internal.functions.b.e(z, "The RxJavaPlugins.onSubscribe hook returned a null FlowableSubscriber. Please check the handler provided to RxJavaPlugins.setOnFlowableSubscribe for invalid null returns. Further reading: https://github.com/ReactiveX/RxJava/wiki/Plugins");
            L(z);
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

    public final e<T> M(r scheduler) {
        io.reactivex.internal.functions.b.e(scheduler, "scheduler is null");
        return N(scheduler, !(this instanceof io.reactivex.internal.operators.flowable.b));
    }

    public final e<T> N(r scheduler, boolean requestOn) {
        io.reactivex.internal.functions.b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.k(new z(this, scheduler, requestOn));
    }

    public final <U> e<T> O(a<U> other) {
        io.reactivex.internal.functions.b.e(other, "other is null");
        return io.reactivex.plugins.a.k(new a0(this, other));
    }

    public final e<T> P(long timeout, TimeUnit timeUnit) {
        return Q(timeout, timeUnit, (a) null, io.reactivex.schedulers.a.a());
    }

    private e<T> Q(long timeout, TimeUnit timeUnit, a<? extends T> other, r scheduler) {
        io.reactivex.internal.functions.b.e(timeUnit, "timeUnit is null");
        io.reactivex.internal.functions.b.e(scheduler, "scheduler is null");
        return io.reactivex.plugins.a.k(new b0(this, timeout, timeUnit, scheduler, other));
    }

    public final <U, R> e<R> V(a<? extends U> other, io.reactivex.functions.b<? super T, ? super U, ? extends R> zipper) {
        io.reactivex.internal.functions.b.e(other, "other is null");
        return T(this, other, zipper);
    }
}
