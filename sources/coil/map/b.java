package coil.map;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Mapper.kt */
public interface b<T, V> {
    boolean a(@NotNull T t);

    @NotNull
    V map(@NotNull T t);

    /* compiled from: Mapper.kt */
    public static final class a {
        public static <T, V> boolean a(@NotNull b<T, V> bVar, @NotNull T data) {
            k.e(bVar, "this");
            k.e(data, "data");
            return true;
        }
    }
}
