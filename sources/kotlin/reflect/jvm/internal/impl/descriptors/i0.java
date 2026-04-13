package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.types.TypeSubstitutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PropertyDescriptor */
public interface i0 extends b, y0 {
    @Nullable
    s M();

    @NotNull
    i0 a();

    i0 c(@NotNull TypeSubstitutor typeSubstitutor);

    @NotNull
    Collection<? extends i0> d();

    @Nullable
    j0 getGetter();

    @Nullable
    k0 getSetter();

    @NotNull
    List<h0> s();

    @Nullable
    s s0();
}
