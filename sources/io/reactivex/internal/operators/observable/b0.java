package io.reactivex.internal.operators.observable;

import io.reactivex.functions.f;
import io.reactivex.internal.disposables.d;
import io.reactivex.internal.operators.observable.ObservableScalarXMap;
import io.reactivex.l;
import io.reactivex.o;
import io.reactivex.q;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: ObservableScalarXMap */
public final class b0 {
    public static <T, R> boolean b(o<T> source, q<? super R> observer, f<? super T, ? extends o<? extends R>> mapper) {
        if (!(source instanceof Callable)) {
            return false;
        }
        try {
            T t = ((Callable) source).call();
            if (t == null) {
                d.complete((q<?>) observer);
                return true;
            }
            try {
                o oVar = (o) io.reactivex.internal.functions.b.e(mapper.apply(t), "The mapper returned a null ObservableSource");
                if (oVar instanceof Callable) {
                    try {
                        R u = ((Callable) oVar).call();
                        if (u == null) {
                            d.complete((q<?>) observer);
                            return true;
                        }
                        ObservableScalarXMap.ScalarDisposable<R> sd = new a<>(observer, u);
                        observer.onSubscribe(sd);
                        sd.run();
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        d.error(ex, (q<?>) observer);
                        return true;
                    }
                } else {
                    oVar.a(observer);
                }
                return true;
            } catch (Throwable ex2) {
                io.reactivex.exceptions.a.b(ex2);
                d.error(ex2, (q<?>) observer);
                return true;
            }
        } catch (Throwable ex3) {
            io.reactivex.exceptions.a.b(ex3);
            d.error(ex3, (q<?>) observer);
            return true;
        }
    }

    public static <T, U> l<U> a(T value, f<? super T, ? extends o<? extends U>> mapper) {
        return io.reactivex.plugins.a.m(new b(value, mapper));
    }

    /* compiled from: ObservableScalarXMap */
    public static final class b<T, R> extends l<R> {
        final T c;
        final f<? super T, ? extends o<? extends R>> d;

        b(T value, f<? super T, ? extends o<? extends R>> mapper) {
            this.c = value;
            this.d = mapper;
        }

        public void a0(q<? super R> observer) {
            try {
                o oVar = (o) io.reactivex.internal.functions.b.e(this.d.apply(this.c), "The mapper returned a null ObservableSource");
                if (oVar instanceof Callable) {
                    try {
                        R u = ((Callable) oVar).call();
                        if (u == null) {
                            d.complete((q<?>) observer);
                            return;
                        }
                        ObservableScalarXMap.ScalarDisposable<R> sd = new a<>(observer, u);
                        observer.onSubscribe(sd);
                        sd.run();
                    } catch (Throwable ex) {
                        io.reactivex.exceptions.a.b(ex);
                        d.error(ex, (q<?>) observer);
                    }
                } else {
                    oVar.a(observer);
                }
            } catch (Throwable e) {
                d.error(e, (q<?>) observer);
            }
        }
    }

    /* compiled from: ObservableScalarXMap */
    public static final class a<T> extends AtomicInteger implements io.reactivex.internal.fuseable.b<T>, Runnable {
        static final int FUSED = 1;
        static final int ON_COMPLETE = 3;
        static final int ON_NEXT = 2;
        static final int START = 0;
        private static final long serialVersionUID = 3880992722410194083L;
        final q<? super T> observer;
        final T value;

        public a(q<? super T> observer2, T value2) {
            this.observer = observer2;
            this.value = value2;
        }

        public boolean offer(T t) {
            throw new UnsupportedOperationException("Should not be called!");
        }

        public boolean offer(T t, T t2) {
            throw new UnsupportedOperationException("Should not be called!");
        }

        public T poll() {
            if (get() != 1) {
                return null;
            }
            lazySet(3);
            return this.value;
        }

        public boolean isEmpty() {
            return get() != 1;
        }

        public void clear() {
            lazySet(3);
        }

        public void dispose() {
            set(3);
        }

        public boolean isDisposed() {
            return get() == 3;
        }

        public int requestFusion(int mode) {
            if ((mode & 1) == 0) {
                return 0;
            }
            lazySet(1);
            return 1;
        }

        public void run() {
            if (get() == 0 && compareAndSet(0, 2)) {
                this.observer.onNext(this.value);
                if (get() == 2) {
                    lazySet(3);
                    this.observer.onComplete();
                }
            }
        }
    }
}
