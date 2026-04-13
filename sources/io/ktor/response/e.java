package io.ktor.response;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ResponseCookies.kt */
public final class e {
    private final a a;
    private final boolean b;

    public e(@NotNull a response, boolean secureTransport) {
        k.f(response, "response");
        this.a = response;
        this.b = secureTransport;
    }
}
