package io.ktor.server.engine;

import io.ktor.application.a;
import io.ktor.application.d;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationEngineEnvironment.kt */
public interface b extends d {
    @NotNull
    a a();

    @NotNull
    List<r> c();

    void start();

    void stop();
}
