package io.reactivex.android.plugins;

import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.functions.f;
import io.reactivex.r;
import java.util.concurrent.Callable;

/* compiled from: RxAndroidPlugins */
public final class a {
    private static volatile f<Callable<r>, r> a;
    private static volatile f<r, r> b;

    public static r d(Callable<r> scheduler) {
        if (scheduler != null) {
            Function<Callable<Scheduler>, Scheduler> f = a;
            if (f == null) {
                return c(scheduler);
            }
            return b(f, scheduler);
        }
        throw new NullPointerException("scheduler == null");
    }

    public static r e(r scheduler) {
        if (scheduler != null) {
            Function<Scheduler, Scheduler> f = b;
            if (f == null) {
                return scheduler;
            }
            return (r) a(f, scheduler);
        }
        throw new NullPointerException("scheduler == null");
    }

    static r c(Callable<r> s) {
        try {
            r scheduler = s.call();
            if (scheduler != null) {
                return scheduler;
            }
            throw new NullPointerException("Scheduler Callable returned null");
        } catch (Throwable ex) {
            throw io.reactivex.exceptions.a.a(ex);
        }
    }

    /* JADX WARNING: type inference failed for: r3v0, types: [io.reactivex.functions.f, io.reactivex.functions.f<java.util.concurrent.Callable<io.reactivex.r>, io.reactivex.r>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static io.reactivex.r b(io.reactivex.functions.f<java.util.concurrent.Callable<io.reactivex.r>, io.reactivex.r> r3, java.util.concurrent.Callable<io.reactivex.r> r4) {
        /*
            java.lang.Object r0 = a(r3, r4)
            io.reactivex.r r0 = (io.reactivex.r) r0
            if (r0 == 0) goto L_0x0009
            return r0
        L_0x0009:
            java.lang.NullPointerException r1 = new java.lang.NullPointerException
            java.lang.String r2 = "Scheduler Callable returned null"
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.android.plugins.a.b(io.reactivex.functions.f, java.util.concurrent.Callable):io.reactivex.r");
    }

    static <T, R> R a(f<T, R> f, T t) {
        try {
            return f.apply(t);
        } catch (Throwable ex) {
            throw io.reactivex.exceptions.a.a(ex);
        }
    }
}
