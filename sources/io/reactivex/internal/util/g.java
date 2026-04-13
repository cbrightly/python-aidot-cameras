package io.reactivex.internal.util;

import io.reactivex.plugins.a;
import io.reactivex.q;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.b;

/* compiled from: HalfSerializer */
public final class g {
    public static <T> void f(b<? super T> subscriber, T value, AtomicInteger wip, b error) {
        if (wip.get() == 0 && wip.compareAndSet(0, 1)) {
            subscriber.onNext(value);
            if (wip.decrementAndGet() != 0) {
                Throwable ex = error.terminate();
                if (ex != null) {
                    subscriber.onError(ex);
                } else {
                    subscriber.onComplete();
                }
            }
        }
    }

    public static void d(b<?> subscriber, Throwable ex, AtomicInteger wip, b error) {
        if (!error.addThrowable(ex)) {
            a.q(ex);
        } else if (wip.getAndIncrement() == 0) {
            subscriber.onError(error.terminate());
        }
    }

    public static void b(b<?> subscriber, AtomicInteger wip, b error) {
        if (wip.getAndIncrement() == 0) {
            Throwable ex = error.terminate();
            if (ex != null) {
                subscriber.onError(ex);
            } else {
                subscriber.onComplete();
            }
        }
    }

    public static <T> void e(q<? super T> observer, T value, AtomicInteger wip, b error) {
        if (wip.get() == 0 && wip.compareAndSet(0, 1)) {
            observer.onNext(value);
            if (wip.decrementAndGet() != 0) {
                Throwable ex = error.terminate();
                if (ex != null) {
                    observer.onError(ex);
                } else {
                    observer.onComplete();
                }
            }
        }
    }

    public static void c(q<?> observer, Throwable ex, AtomicInteger wip, b error) {
        if (!error.addThrowable(ex)) {
            a.q(ex);
        } else if (wip.getAndIncrement() == 0) {
            observer.onError(error.terminate());
        }
    }

    public static void a(q<?> observer, AtomicInteger wip, b error) {
        if (wip.getAndIncrement() == 0) {
            Throwable ex = error.terminate();
            if (ex != null) {
                observer.onError(ex);
            } else {
                observer.onComplete();
            }
        }
    }
}
