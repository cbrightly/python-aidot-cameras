package zendesk.storage.android;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Serializer.kt */
public interface b {
    @Nullable
    <T> T a(@NotNull String str, @NotNull Class<T> cls);

    @NotNull
    <T> String b(T t, @NotNull Class<T> cls);
}
