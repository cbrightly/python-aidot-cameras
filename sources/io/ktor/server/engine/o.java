package io.ktor.server.engine;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.io.IOException;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.g;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlinx.coroutines.j0;
import kotlinx.coroutines.n0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.b;

/* compiled from: DefaultUncaughtExceptionHandler.kt */
public final class o implements j0 {
    private final kotlin.jvm.functions.a<b> c;

    public o(@NotNull kotlin.jvm.functions.a<? extends b> logger) {
        k.f(logger, "logger");
        this.c = logger;
    }

    public <R> R fold(R initial, @NotNull p<? super R, ? super g.b, ? extends R> operation) {
        k.f(operation, "operation");
        return j0.a.a(this, initial, operation);
    }

    @Nullable
    public <E extends g.b> E get(@NotNull g.c<E> key) {
        k.f(key, CacheEntity.KEY);
        return j0.a.b(this, key);
    }

    @NotNull
    public g minusKey(@NotNull g.c<?> key) {
        k.f(key, CacheEntity.KEY);
        return j0.a.c(this, key);
    }

    @NotNull
    public g plus(@NotNull g context) {
        k.f(context, "context");
        return j0.a.d(this, context);
    }

    /* compiled from: DefaultUncaughtExceptionHandler.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<b> {
        final /* synthetic */ b $logger;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(b bVar) {
            super(0);
            this.$logger = bVar;
        }

        @NotNull
        public final b invoke() {
            return this.$logger;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public o(@NotNull b logger) {
        this((kotlin.jvm.functions.a<? extends b>) new a(logger));
        k.f(logger, "logger");
    }

    @NotNull
    public g.c<?> getKey() {
        return j0.e;
    }

    public void handleException(@NotNull g context, @NotNull Throwable exception) {
        k.f(context, "context");
        k.f(exception, "exception");
        if (!(exception instanceof CancellationException) && !(exception instanceof IOException)) {
            Object coroutineName = (n0) context.get(n0.c);
            if (coroutineName == null) {
                coroutineName = context.toString();
            }
            this.c.invoke().error("Unhandled exception caught for " + coroutineName, exception);
        }
    }
}
