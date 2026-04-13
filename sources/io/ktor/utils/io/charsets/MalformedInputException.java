package io.ktor.utils.io.charsets;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CharsetJVM.kt */
public final class MalformedInputException extends java.nio.charset.MalformedInputException {
    private final String _message;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MalformedInputException(@NotNull String message) {
        super(0);
        k.f(message, "message");
        this._message = message;
    }

    @Nullable
    public String getMessage() {
        return this._message;
    }
}
