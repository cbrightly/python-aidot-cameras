package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.d;
import kotlin.coroutines.g;
import org.jetbrains.annotations.NotNull;

/* compiled from: ContinuationImpl.kt */
public final class c implements d<Object> {
    @NotNull
    public static final c c = new c();

    private c() {
    }

    @NotNull
    public g getContext() {
        throw new IllegalStateException("This continuation is already complete".toString());
    }

    public void resumeWith(@NotNull Object result) {
        throw new IllegalStateException("This continuation is already complete".toString());
    }

    @NotNull
    public String toString() {
        return "This continuation is already complete";
    }
}
