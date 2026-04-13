package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.reflect.jvm.internal.impl.descriptors.e;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ExternalOverridabilityCondition */
public interface d {

    /* compiled from: ExternalOverridabilityCondition */
    public enum a {
        CONFLICTS_ONLY,
        SUCCESS_ONLY,
        BOTH
    }

    /* compiled from: ExternalOverridabilityCondition */
    public enum b {
        OVERRIDABLE,
        CONFLICT,
        INCOMPATIBLE,
        UNKNOWN
    }

    @NotNull
    a a();

    @NotNull
    b b(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a aVar, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a aVar2, @Nullable e eVar);
}
