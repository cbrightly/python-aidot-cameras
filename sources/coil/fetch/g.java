package coil.fetch;

import coil.bitmap.c;
import coil.decode.m;
import coil.size.Size;
import kotlin.coroutines.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Fetcher.kt */
public interface g<T> {
    boolean a(@NotNull T t);

    @Nullable
    Object b(@NotNull c cVar, @NotNull T t, @NotNull Size size, @NotNull m mVar, @NotNull d<? super f> dVar);

    @Nullable
    String c(@NotNull T t);

    /* compiled from: Fetcher.kt */
    public static final class a {
        public static <T> boolean a(@NotNull g<T> gVar, @NotNull T data) {
            k.e(gVar, "this");
            k.e(data, "data");
            return true;
        }
    }
}
