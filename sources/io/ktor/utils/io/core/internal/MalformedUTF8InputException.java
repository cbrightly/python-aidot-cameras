package io.ktor.utils.io.core.internal;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: UTF8.kt */
public final class MalformedUTF8InputException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MalformedUTF8InputException(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
