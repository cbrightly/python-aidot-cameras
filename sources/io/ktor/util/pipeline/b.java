package io.ktor.util.pipeline;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: PipelinePhase.kt */
public final class b extends Throwable {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public b(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
