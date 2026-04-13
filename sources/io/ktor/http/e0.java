package io.ktor.http;

import org.jetbrains.annotations.NotNull;

/* compiled from: RequestConnectionPoint.kt */
public interface e0 {
    int a();

    @NotNull
    String b();

    @NotNull
    String c();

    @NotNull
    u getMethod();

    @NotNull
    String getUri();
}
