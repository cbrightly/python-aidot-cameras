package io.ktor.http;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpHeaders.kt */
public final class UnsafeHeaderException extends IllegalArgumentException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UnsafeHeaderException(@NotNull String header) {
        super("Header " + header + " is controlled by the engine and " + "cannot be set explicitly");
        k.f(header, "header");
    }
}
