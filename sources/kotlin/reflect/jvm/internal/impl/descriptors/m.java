package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.reflect.jvm.internal.impl.descriptors.annotations.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeclarationDescriptor */
public interface m extends z, a {
    @NotNull
    m a();

    @Nullable
    m b();

    <R, D> R w(o<R, D> oVar, D d);
}
