package io.ktor.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlinx.coroutines.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: DispatcherWithShutdown.kt */
public final class n extends i0 {
    private i0 c;
    private final g<ExecutorService> d = i.b(b.INSTANCE);
    private volatile a shutdownPhase = a.None;

    /* compiled from: DispatcherWithShutdown.kt */
    public enum a {
        None,
        Graceful,
        Completed
    }

    public n(@NotNull i0 delegate) {
        k.f(delegate, "delegate");
        this.c = delegate;
    }

    /* compiled from: DispatcherWithShutdown.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<ExecutorService> {
        public static final b INSTANCE = new b();

        b() {
            super(0);
        }

        public final ExecutorService invoke() {
            return Executors.newCachedThreadPool();
        }
    }

    public final void W() {
        this.shutdownPhase = a.Completed;
        if (this.d.isInitialized()) {
            this.d.getValue().shutdown();
        }
    }

    public boolean isDispatchNeeded(@NotNull kotlin.coroutines.g context) {
        k.f(context, "context");
        switch (o.a[this.shutdownPhase.ordinal()]) {
            case 1:
                i0 i0Var = this.c;
                if (i0Var != null) {
                    return i0Var.isDispatchNeeded(context);
                }
                return true;
            default:
                return true;
        }
    }

    public void dispatch(@NotNull kotlin.coroutines.g context, @NotNull Runnable block) {
        k.f(context, "context");
        k.f(block, "block");
        switch (o.b[this.shutdownPhase.ordinal()]) {
            case 1:
                try {
                    i0 i0Var = this.c;
                    if (i0Var != null) {
                        i0Var.dispatch(context, block);
                        return;
                    } else {
                        dispatch(context, block);
                        return;
                    }
                } catch (RejectedExecutionException rejected) {
                    if (this.shutdownPhase != a.None) {
                        dispatch(context, block);
                        return;
                    }
                    throw rejected;
                }
            case 2:
                try {
                    this.d.getValue().submit(block);
                    return;
                } catch (RejectedExecutionException e) {
                    this.shutdownPhase = a.Completed;
                    dispatch(context, block);
                    return;
                }
            case 3:
                block.run();
                return;
            default:
                return;
        }
    }
}
