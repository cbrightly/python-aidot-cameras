package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.types.b0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ValueParameterDescriptor.kt */
public interface w0 extends f0, x0 {
    @NotNull
    w0 T(@NotNull a aVar, @NotNull f fVar, int i);

    @NotNull
    w0 a();

    @NotNull
    a b();

    @NotNull
    Collection<w0> d();

    int getIndex();

    boolean k0();

    boolean n0();

    @Nullable
    b0 r0();

    boolean v0();
}
