package io.ktor.http;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: URLParser.kt */
public final class URLParserException extends IllegalStateException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public URLParserException(@NotNull String urlString, @NotNull Throwable cause) {
        super("Fail to parse url: " + urlString, cause);
        k.f(urlString, "urlString");
        k.f(cause, "cause");
    }
}
