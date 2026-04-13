package io.ktor.util.pipeline;

import kotlinx.coroutines.o0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PipelineContext.kt */
public interface d<TSubject, TContext> extends o0 {
    @Nullable
    Object E(@NotNull TSubject tsubject, @NotNull kotlin.coroutines.d<? super TSubject> dVar);

    @NotNull
    TContext getContext();

    @Nullable
    Object j(@NotNull kotlin.coroutines.d<? super TSubject> dVar);

    @NotNull
    TSubject l();

    void o();
}
