package io.ktor.features;

import io.ktor.application.b;
import io.ktor.request.c;
import io.ktor.util.pipeline.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ContentNegotiation.kt */
public interface f {
    @Nullable
    Object convertForReceive(@NotNull d<c, b> dVar, @NotNull kotlin.coroutines.d<Object> dVar2);

    @Nullable
    Object convertForSend(@NotNull d<Object, b> dVar, @NotNull io.ktor.http.c cVar, @NotNull Object obj, @NotNull kotlin.coroutines.d<Object> dVar2);
}
