package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: InvalidModuleException.kt */
public final class InvalidModuleException extends IllegalStateException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public InvalidModuleException(@NotNull String message) {
        super(message);
        k.f(message, "message");
    }
}
