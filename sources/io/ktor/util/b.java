package io.ktor.util;

import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Attributes.kt */
public interface b {
    @NotNull
    <T> T a(@NotNull a<T> aVar);

    <T> void b(@NotNull a<T> aVar, @NotNull T t);

    @NotNull
    List<a<?>> c();

    <T> void d(@NotNull a<T> aVar);

    @Nullable
    <T> T e(@NotNull a<T> aVar);

    @NotNull
    <T> T f(@NotNull a<T> aVar, @NotNull kotlin.jvm.functions.a<? extends T> aVar2);

    /* compiled from: Attributes.kt */
    public static final class a {
        @NotNull
        public static <T> T a(b $this, @NotNull a<T> key) {
            k.f(key, CacheEntity.KEY);
            T e = $this.e(key);
            if (e != null) {
                return e;
            }
            throw new IllegalStateException("No instance for key " + key);
        }
    }
}
