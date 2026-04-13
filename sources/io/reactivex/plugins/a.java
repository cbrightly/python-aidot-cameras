package io.reactivex.plugins;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.b;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.exceptions.UndeliverableException;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.e;
import io.reactivex.functions.f;
import io.reactivex.j;
import io.reactivex.l;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.q;
import io.reactivex.r;
import io.reactivex.s;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;

/* compiled from: RxJavaPlugins */
public final class a {
    static volatile e<? super Throwable> a;
    static volatile f<? super Runnable, ? extends Runnable> b;
    static volatile f<? super Callable<r>, ? extends r> c;
    static volatile f<? super Callable<r>, ? extends r> d;
    static volatile f<? super Callable<r>, ? extends r> e;
    static volatile f<? super Callable<r>, ? extends r> f;
    static volatile f<? super r, ? extends r> g;
    static volatile f<? super r, ? extends r> h;
    static volatile f<? super r, ? extends r> i;
    static volatile f<? super r, ? extends r> j;
    static volatile f<? super io.reactivex.e, ? extends io.reactivex.e> k;
    static volatile f<? super l, ? extends l> l;
    static volatile f<? super io.reactivex.observables.a, ? extends io.reactivex.observables.a> m;
    static volatile f<? super j, ? extends j> n;
    static volatile f<? super s, ? extends s> o;
    static volatile f<? super b, ? extends b> p;
    static volatile io.reactivex.functions.b<? super io.reactivex.e, ? super org.reactivestreams.b, ? extends org.reactivestreams.b> q;
    static volatile io.reactivex.functions.b<? super l, ? super q, ? extends q> r;
    static volatile boolean s;

    public static r e(Callable<r> defaultScheduler) {
        io.reactivex.internal.functions.b.e(defaultScheduler, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> f2 = c;
        if (f2 == null) {
            return d(defaultScheduler);
        }
        return c(f2, defaultScheduler);
    }

    public static r f(Callable<r> defaultScheduler) {
        io.reactivex.internal.functions.b.e(defaultScheduler, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> f2 = e;
        if (f2 == null) {
            return d(defaultScheduler);
        }
        return c(f2, defaultScheduler);
    }

    public static r g(Callable<r> defaultScheduler) {
        io.reactivex.internal.functions.b.e(defaultScheduler, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> f2 = f;
        if (f2 == null) {
            return d(defaultScheduler);
        }
        return c(f2, defaultScheduler);
    }

    public static r h(Callable<r> defaultScheduler) {
        io.reactivex.internal.functions.b.e(defaultScheduler, "Scheduler Callable can't be null");
        Function<? super Callable<Scheduler>, ? extends Scheduler> f2 = d;
        if (f2 == null) {
            return d(defaultScheduler);
        }
        return c(f2, defaultScheduler);
    }

    public static r p(r defaultScheduler) {
        Function<? super Scheduler, ? extends Scheduler> f2 = g;
        if (f2 == null) {
            return defaultScheduler;
        }
        return (r) b(f2, defaultScheduler);
    }

    public static void q(Throwable error) {
        Consumer<? super Throwable> f2 = a;
        if (error == null) {
            error = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
        } else if (!i(error)) {
            error = new UndeliverableException(error);
        }
        if (f2 != null) {
            try {
                f2.accept(error);
                return;
            } catch (Throwable e2) {
                e2.printStackTrace();
                y(e2);
            }
        }
        error.printStackTrace();
        y(error);
    }

    static boolean i(Throwable error) {
        if (!(error instanceof OnErrorNotImplementedException) && !(error instanceof MissingBackpressureException) && !(error instanceof IllegalStateException) && !(error instanceof NullPointerException) && !(error instanceof IllegalArgumentException) && !(error instanceof CompositeException)) {
            return false;
        }
        return true;
    }

    static void y(Throwable error) {
        Thread currentThread = Thread.currentThread();
        currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, error);
    }

    public static r r(r defaultScheduler) {
        Function<? super Scheduler, ? extends Scheduler> f2 = i;
        if (f2 == null) {
            return defaultScheduler;
        }
        return (r) b(f2, defaultScheduler);
    }

    public static r s(r defaultScheduler) {
        Function<? super Scheduler, ? extends Scheduler> f2 = j;
        if (f2 == null) {
            return defaultScheduler;
        }
        return (r) b(f2, defaultScheduler);
    }

    public static Runnable t(Runnable run) {
        io.reactivex.internal.functions.b.e(run, "run is null");
        Function<? super Runnable, ? extends Runnable> f2 = b;
        if (f2 == null) {
            return run;
        }
        return (Runnable) b(f2, run);
    }

    public static r u(r defaultScheduler) {
        Function<? super Scheduler, ? extends Scheduler> f2 = h;
        if (f2 == null) {
            return defaultScheduler;
        }
        return (r) b(f2, defaultScheduler);
    }

    public static void x(e<? super Throwable> handler) {
        if (!s) {
            a = handler;
            return;
        }
        throw new IllegalStateException("Plugins can't be changed anymore");
    }

    public static <T> org.reactivestreams.b<? super T> w(io.reactivex.e<T> source, org.reactivestreams.b<? super T> subscriber) {
        BiFunction<? super Flowable, ? super Subscriber, ? extends Subscriber> f2 = q;
        if (f2 != null) {
            return (org.reactivestreams.b) a(f2, source, subscriber);
        }
        return subscriber;
    }

    public static <T> q<? super T> v(l<T> source, q<? super T> observer) {
        BiFunction<? super Observable, ? super Observer, ? extends Observer> f2 = r;
        if (f2 != null) {
            return (q) a(f2, source, observer);
        }
        return observer;
    }

    public static <T> j<T> l(j<T> source) {
        Function<? super Maybe, ? extends Maybe> f2 = n;
        if (f2 != null) {
            return (j) b(f2, source);
        }
        return source;
    }

    public static <T> io.reactivex.e<T> k(io.reactivex.e<T> source) {
        Function<? super Flowable, ? extends Flowable> f2 = k;
        if (f2 != null) {
            return (io.reactivex.e) b(f2, source);
        }
        return source;
    }

    public static <T> l<T> m(l<T> source) {
        Function<? super Observable, ? extends Observable> f2 = l;
        if (f2 != null) {
            return (l) b(f2, source);
        }
        return source;
    }

    public static <T> io.reactivex.observables.a<T> o(io.reactivex.observables.a<T> source) {
        Function<? super ConnectableObservable, ? extends ConnectableObservable> f2 = m;
        if (f2 != null) {
            return (io.reactivex.observables.a) b(f2, source);
        }
        return source;
    }

    public static <T> s<T> n(s<T> source) {
        Function<? super Single, ? extends Single> f2 = o;
        if (f2 != null) {
            return (s) b(f2, source);
        }
        return source;
    }

    public static b j(b source) {
        Function<? super Completable, ? extends Completable> f2 = p;
        if (f2 != null) {
            return (b) b(f2, source);
        }
        return source;
    }

    static <T, R> R b(f<T, R> f2, T t) {
        try {
            return f2.apply(t);
        } catch (Throwable ex) {
            throw io.reactivex.internal.util.f.d(ex);
        }
    }

    static <T, U, R> R a(io.reactivex.functions.b<T, U, R> f2, T t, U u) {
        try {
            return f2.apply(t, u);
        } catch (Throwable ex) {
            throw io.reactivex.internal.util.f.d(ex);
        }
    }

    static r d(Callable<r> s2) {
        try {
            return (r) io.reactivex.internal.functions.b.e(s2.call(), "Scheduler Callable result can't be null");
        } catch (Throwable ex) {
            throw io.reactivex.internal.util.f.d(ex);
        }
    }

    static r c(f<? super Callable<r>, ? extends r> f2, Callable<r> s2) {
        return (r) io.reactivex.internal.functions.b.e(b(f2, s2), "Scheduler Callable result can't be null");
    }
}
