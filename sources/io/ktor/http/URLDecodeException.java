package io.ktor.http;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Codecs.kt */
public final class URLDecodeException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public URLDecodeException(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
