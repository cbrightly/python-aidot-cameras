package zendesk.storage.android;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Storage.kt */
public interface c {
    <T> void a(@NotNull String str, @Nullable T t, @NotNull Class<T> cls);

    @Nullable
    <T> T b(@NotNull String str, @NotNull Class<T> cls);

    void clear();
}
