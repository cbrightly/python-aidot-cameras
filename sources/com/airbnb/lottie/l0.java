package com.airbnb.lottie;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.airbnb.lottie.utils.d;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/* compiled from: LottieTask */
public class l0<T> {
    public static Executor a = Executors.newCachedThreadPool();
    private final Set<g0<T>> b;
    private final Set<g0<Throwable>> c;
    private final Handler d;
    @Nullable
    private volatile k0<T> e;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public l0(Callable<k0<T>> runnable) {
        this(runnable, false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    l0(Callable<k0<T>> runnable, boolean runNow) {
        this.b = new LinkedHashSet(1);
        this.c = new LinkedHashSet(1);
        this.d = new Handler(Looper.getMainLooper());
        this.e = null;
        if (runNow) {
            try {
                k(runnable.call());
            } catch (Throwable e2) {
                k(new k0(e2));
            }
        } else {
            a.execute(new a(runnable));
        }
    }

    /* access modifiers changed from: private */
    public void k(@Nullable k0<T> result) {
        if (this.e == null) {
            this.e = result;
            g();
            return;
        }
        throw new IllegalStateException("A task may only be set once.");
    }

    public synchronized l0<T> c(g0<T> listener) {
        LottieResult<T> result = this.e;
        if (!(result == null || result.b() == null)) {
            listener.onResult(result.b());
        }
        this.b.add(listener);
        return this;
    }

    public synchronized l0<T> j(g0<T> listener) {
        this.b.remove(listener);
        return this;
    }

    public synchronized l0<T> b(g0<Throwable> listener) {
        LottieResult<T> result = this.e;
        if (!(result == null || result.a() == null)) {
            listener.onResult(result.a());
        }
        this.c.add(listener);
        return this;
    }

    public synchronized l0<T> i(g0<Throwable> listener) {
        this.c.remove(listener);
        return this;
    }

    private void g() {
        this.d.post(new x(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ void e() {
        LottieResult<T> result = this.e;
        if (result != null) {
            if (result.b() != null) {
                h(result.b());
            } else {
                f(result.a());
            }
        }
    }

    private synchronized void h(T value) {
        for (LottieListener<T> l : new ArrayList<>(this.b)) {
            l.onResult(value);
        }
    }

    private synchronized void f(Throwable e2) {
        List<LottieListener<Throwable>> listenersCopy = new ArrayList<>(this.c);
        if (listenersCopy.isEmpty()) {
            d.d("Lottie encountered an error but no failure listener was added:", e2);
            return;
        }
        for (LottieListener<Throwable> l : listenersCopy) {
            l.onResult(e2);
        }
    }

    /* compiled from: LottieTask */
    public class a extends FutureTask<k0<T>> {
        a(Callable<k0<T>> callable) {
            super(callable);
        }

        /* access modifiers changed from: protected */
        public void done() {
            if (!isCancelled()) {
                try {
                    l0.this.k((k0) get());
                } catch (InterruptedException | ExecutionException e) {
                    l0.this.k(new k0((Throwable) e));
                }
            }
        }
    }
}
