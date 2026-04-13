package io.ktor.application;

import io.ktor.config.a;
import kotlin.coroutines.g;
import org.jetbrains.annotations.NotNull;
import org.slf4j.b;

/* compiled from: ApplicationEnvironment.kt */
public interface d {
    @NotNull
    e b();

    @NotNull
    String d();

    @NotNull
    g e();

    @NotNull
    b f();

    @NotNull
    a getConfig();
}
