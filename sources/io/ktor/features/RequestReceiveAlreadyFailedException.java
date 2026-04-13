package io.ktor.features;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: DoubleReceive.kt */
public final class RequestReceiveAlreadyFailedException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public RequestReceiveAlreadyFailedException(@NotNull Throwable cause) {
        super("Request body consumption was failed", cause, false, true);
        k.f(cause, "cause");
    }
}
