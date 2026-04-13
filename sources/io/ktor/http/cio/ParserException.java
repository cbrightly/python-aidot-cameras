package io.ktor.http.cio;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpParser.kt */
public final class ParserException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ParserException(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
