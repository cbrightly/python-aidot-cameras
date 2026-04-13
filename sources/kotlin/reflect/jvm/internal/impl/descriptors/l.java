package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.List;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ConstructorDescriptor */
public interface l extends u {
    boolean W();

    @NotNull
    e X();

    @NotNull
    i b();

    @Nullable
    l c(@NotNull TypeSubstitutor typeSubstitutor);

    @NotNull
    b0 getReturnType();

    @NotNull
    List<t0> getTypeParameters();
}
