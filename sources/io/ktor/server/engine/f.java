package io.ktor.server.engine;

import io.ktor.server.engine.a;
import io.ktor.server.engine.a.C0270a;
import kotlin.jvm.functions.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: EmbeddedServer.kt */
public interface f<TEngine extends a, TConfiguration extends a.C0270a> {
    @NotNull
    TEngine a(@NotNull b bVar, @NotNull l<? super TConfiguration, x> lVar);
}
