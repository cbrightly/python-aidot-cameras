package io.ktor.features;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Errors.kt */
public abstract class ContentTransformationException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ContentTransformationException(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
