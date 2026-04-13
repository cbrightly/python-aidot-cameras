package io.ktor.util.pipeline;

import java.util.List;
import kotlin.coroutines.d;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: PipelineContext.kt */
public final class e {
    @NotNull
    public static final <TSubject, TContext> f<TSubject> a(@NotNull TContext context, @NotNull List<? extends q<? super d<TSubject, TContext>, ? super TSubject, ? super d<? super x>, ? extends Object>> interceptors, @NotNull TSubject subject) {
        k.f(context, "context");
        k.f(interceptors, "interceptors");
        k.f(subject, "subject");
        return new l(subject, context, interceptors);
    }
}
