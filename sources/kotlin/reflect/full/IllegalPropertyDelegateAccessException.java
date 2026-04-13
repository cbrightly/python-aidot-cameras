package kotlin.reflect.full;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: exceptions.kt */
public final class IllegalPropertyDelegateAccessException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public IllegalPropertyDelegateAccessException(@NotNull IllegalAccessException cause) {
        super("Cannot obtain the delegate of a non-accessible property. Use \"isAccessible = true\" to make the property accessible", cause);
        k.f(cause, "cause");
    }
}
