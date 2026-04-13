package kotlin.reflect.full;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.Nullable;

/* compiled from: exceptions.kt */
public final class NoSuchPropertyException extends Exception {
    public NoSuchPropertyException() {
        this((Exception) null, 1, (DefaultConstructorMarker) null);
    }

    public NoSuchPropertyException(@Nullable Exception cause) {
        super(cause);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ NoSuchPropertyException(Exception exc, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : exc);
    }
}
