package kotlin.reflect.jvm.internal.impl.descriptors.deserialization;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassDescriptorFactory.kt */
public interface b {
    @NotNull
    Collection<e> a(@NotNull kotlin.reflect.jvm.internal.impl.name.b bVar);

    boolean b(@NotNull kotlin.reflect.jvm.internal.impl.name.b bVar, @NotNull f fVar);

    @Nullable
    e c(@NotNull a aVar);
}
