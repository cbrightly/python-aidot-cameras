package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.resolve.scopes.receivers.d;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReceiverParameterDescriptor */
public interface l0 extends f0 {
    @Nullable
    l0 c(@NotNull TypeSubstitutor typeSubstitutor);

    @NotNull
    d getValue();
}
