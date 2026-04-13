package kotlin.reflect;

import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KCallable.kt */
public interface b<R> extends a {
    R call(@NotNull Object... objArr);

    R callBy(@NotNull Map<j, ? extends Object> map);

    @NotNull
    String getName();

    @NotNull
    List<j> getParameters();

    @NotNull
    n getReturnType();

    @NotNull
    List<o> getTypeParameters();

    @Nullable
    s getVisibility();

    boolean isAbstract();

    boolean isFinal();

    boolean isOpen();

    boolean isSuspend();
}
