package io.reactivex.internal.operators.flowable;

import io.reactivex.e;
import io.reactivex.functions.f;
import io.reactivex.internal.subscriptions.c;
import io.reactivex.internal.subscriptions.d;
import java.util.concurrent.Callable;
import org.reactivestreams.b;

/* compiled from: FlowableScalarXMap */
public final class y {
    public static <T, R> boolean b(org.reactivestreams.a<T> source, b<? super R> subscriber, f<? super T, ? extends org.reactivestreams.a<? extends R>> mapper) {
        if (!(source instanceof Callable)) {
            return false;
        }
        try {
            T t = ((Callable) source).call();
            if (t == null) {
                c.complete(subscriber);
                return true;
            }
            try {
                org.reactivestreams.a aVar = (org.reactivestreams.a) io.reactivex.internal.functions.b.e(mapper.apply(t), "The mapper returned a null Publisher");
                if (aVar instanceof Callable) {
                    try {
                        R u = ((Callable) aVar).call();
                        if (u == null) {
                            c.complete(subscriber);
                            return true;
                        }
                        subscriber.onSubscribe(new d(subscriber, u));
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        c.error(ex, subscriber);
                        return true;
                    }
                } else {
                    aVar.a(subscriber);
                }
                return true;
            } catch (Throwable ex2) {
                io.reactivex.exceptions.a.b(ex2);
                c.error(ex2, subscriber);
                return true;
            }
        } catch (Throwable ex3) {
            io.reactivex.exceptions.a.b(ex3);
            c.error(ex3, subscriber);
            return true;
        }
    }

    public static <T, U> e<U> a(T value, f<? super T, ? extends org.reactivestreams.a<? extends U>> mapper) {
        return io.reactivex.plugins.a.k(new a(value, mapper));
    }

    /* compiled from: FlowableScalarXMap */
    public static final class a<T, R> extends e<R> {
        final T d;
        final f<? super T, ? extends org.reactivestreams.a<? extends R>> f;

        a(T value, f<? super T, ? extends org.reactivestreams.a<? extends R>> mapper) {
            this.d = value;
            this.f = mapper;
        }

        public void L(b<? super R> s) {
            try {
                org.reactivestreams.a aVar = (org.reactivestreams.a) io.reactivex.internal.functions.b.e(this.f.apply(this.d), "The mapper returned a null Publisher");
                if (aVar instanceof Callable) {
                    try {
                        R u = ((Callable) aVar).call();
                        if (u == null) {
                            c.complete(s);
                        } else {
                            s.onSubscribe(new d(s, u));
                        }
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        c.error(ex, s);
                    }
                } else {
                    aVar.a(s);
                }
            } catch (Throwable e) {
                c.error(e, s);
            }
        }
    }
}
