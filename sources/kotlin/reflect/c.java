package kotlin.reflect;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KClass.kt */
public interface c<T> extends e, a, d {
    @NotNull
    Collection<f<T>> f();

    @NotNull
    Collection<b<?>> g();

    @NotNull
    Collection<c<?>> i();

    @Nullable
    T j();

    boolean k(@Nullable Object obj);

    @Nullable
    String l();
}
