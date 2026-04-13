package kotlin.reflect.jvm.internal;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: KotlinReflectionInternalError.kt */
public final class y extends Error {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public y(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
