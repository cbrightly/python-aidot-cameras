package io.ktor.application;

import io.ktor.http.y;
import io.ktor.request.d;
import io.ktor.response.a;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationCall.kt */
public interface b {
    @NotNull
    a a();

    @NotNull
    a b();

    @NotNull
    io.ktor.util.b getAttributes();

    @NotNull
    y getParameters();

    @NotNull
    d getRequest();
}
