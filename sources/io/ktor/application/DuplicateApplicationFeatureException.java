package io.ktor.application;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ApplicationFeature.kt */
public final class DuplicateApplicationFeatureException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DuplicateApplicationFeatureException(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
