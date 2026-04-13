package kotlin.reflect.full;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: exceptions.kt */
public final class IllegalCallableAccessException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public IllegalCallableAccessException(@NotNull IllegalAccessException cause) {
        super(cause);
        k.f(cause, "cause");
    }
}
