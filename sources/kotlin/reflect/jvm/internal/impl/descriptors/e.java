package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.h;
import kotlin.reflect.jvm.internal.impl.types.i0;
import kotlin.reflect.jvm.internal.impl.types.z0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ClassDescriptor */
public interface e extends g, i {
    @Nullable
    d B();

    boolean D0();

    @NotNull
    l0 F0();

    @NotNull
    h P();

    @NotNull
    h R();

    boolean V();

    @NotNull
    e a();

    @NotNull
    m b();

    @NotNull
    Collection<d> f();

    @NotNull
    h g0();

    @NotNull
    a1 getVisibility();

    @NotNull
    f h();

    @Nullable
    e h0();

    boolean isInline();

    @NotNull
    i0 m();

    @NotNull
    h m0(@NotNull z0 z0Var);

    @NotNull
    List<t0> o();

    @NotNull
    w p();

    @NotNull
    Collection<e> v();
}
