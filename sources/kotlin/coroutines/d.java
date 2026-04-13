package kotlin.coroutines;

import org.jetbrains.annotations.NotNull;

/* compiled from: Continuation.kt */
public interface d<T> {
    @NotNull
    g getContext();

    void resumeWith(@NotNull Object obj);
}
