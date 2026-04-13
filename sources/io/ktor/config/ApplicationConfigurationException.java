package io.ktor.config;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationConfig.kt */
public final class ApplicationConfigurationException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ApplicationConfigurationException(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
