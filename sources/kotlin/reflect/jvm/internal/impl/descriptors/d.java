package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassConstructorDescriptor.kt */
public interface d extends l {
    @NotNull
    d a();

    @Nullable
    d c(@NotNull TypeSubstitutor typeSubstitutor);
}
