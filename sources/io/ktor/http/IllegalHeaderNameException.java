package io.ktor.http;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpHeaders.kt */
public final class IllegalHeaderNameException extends IllegalArgumentException {
    @NotNull
    private final String headerName;
    private final int position;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public IllegalHeaderNameException(@NotNull String headerName2, int position2) {
        super("Header name '" + headerName2 + "' contains illegal character '" + headerName2.charAt(position2) + '\'' + " (code " + (headerName2.charAt(position2) & 255) + ')');
        k.f(headerName2, "headerName");
        this.headerName = headerName2;
        this.position = position2;
    }

    @NotNull
    public final String getHeaderName() {
        return this.headerName;
    }

    public final int getPosition() {
        return this.position;
    }
}
