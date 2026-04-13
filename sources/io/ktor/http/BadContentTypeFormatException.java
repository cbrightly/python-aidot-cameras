package io.ktor.http;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ContentTypes.kt */
public final class BadContentTypeFormatException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public BadContentTypeFormatException(@NotNull String value) {
        super("Bad Content-Type format: " + value);
        k.f(value, "value");
    }
}
