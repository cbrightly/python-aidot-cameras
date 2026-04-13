package io.ktor.http;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpHeaders.kt */
public final class IllegalHeaderValueException extends IllegalArgumentException {
    @NotNull
    private final String headerValue;
    private final int position;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public IllegalHeaderValueException(@NotNull String headerValue2, int position2) {
        super("Header value '" + headerValue2 + "' contains illegal character '" + headerValue2.charAt(position2) + '\'' + " (code " + (headerValue2.charAt(position2) & 255) + ')');
        k.f(headerValue2, "headerValue");
        this.headerValue = headerValue2;
        this.position = position2;
    }

    @NotNull
    public final String getHeaderValue() {
        return this.headerValue;
    }

    public final int getPosition() {
        return this.position;
    }
}
