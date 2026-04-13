package io.ktor.request;

import io.ktor.application.b;
import io.ktor.http.e0;
import io.ktor.http.o;
import io.ktor.http.y;
import io.ktor.utils.io.i;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationRequest.kt */
public interface d {
    @NotNull
    b a();

    @NotNull
    e0 b();

    @NotNull
    i c();

    @NotNull
    b d();

    @NotNull
    y e();

    @NotNull
    o getHeaders();
}
