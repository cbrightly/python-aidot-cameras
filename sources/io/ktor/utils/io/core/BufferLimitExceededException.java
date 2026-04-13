package io.ktor.utils.io.core;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Buffers.kt */
public final class BufferLimitExceededException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BufferLimitExceededException(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
